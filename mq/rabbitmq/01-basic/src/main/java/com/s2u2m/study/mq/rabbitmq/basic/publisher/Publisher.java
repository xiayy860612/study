package com.s2u2m.study.mq.rabbitmq.basic.publisher;

import com.rabbitmq.client.*;
import com.s2u2m.study.mq.rabbitmq.basic.RabbitMqConfig;

import java.io.*;
import java.util.*;
import java.util.concurrent.TimeoutException;

/**
 * - enable AE for missing msg, priority: AE > mandatory
 * - publish confirm
 */
public class Publisher {
    private Channel channel;
    private final SortedMap<Long, Msg> nackMsg = new TreeMap<>();

    private static class Msg {
        private String queue;
        private Long deliveryTag;
        private String msg;
        private Integer sendAmount = 0;
    }

    public Publisher(Connection connection) throws IOException {
        channel = connection.createChannel();
        enablePublishConfirm(channel);

        declareAe(channel);
        declareExAndQueue(channel);
    }

    private void enablePublishConfirm(Channel channel) throws IOException {
        channel.confirmSelect();
        channel.addConfirmListener(this::ackCallback, this::nackCallback);
    }

    private void ackCallback(long deliveryTag, boolean multiple) {
        Collection<Msg> msgs;
        if (multiple) {
            msgs = nackMsg.headMap(deliveryTag).values();
            nackMsg.headMap(deliveryTag).clear();
        } else {
            msgs = Collections.singleton(nackMsg.get(deliveryTag));
            nackMsg.remove(deliveryTag);
        }

        for (Msg msg : msgs) {
            System.out.printf("Send [%s][%s] Success\n", msg.deliveryTag, msg.msg);
        }
    }

    private void nackCallback(long deliveryTag, boolean multiple) throws IOException {
        Collection<Msg> msgs;
        if (multiple) {
            msgs = nackMsg.headMap(deliveryTag).values();
            nackMsg.headMap(deliveryTag).clear();
        } else {
            msgs = Collections.singleton(nackMsg.get(deliveryTag));
            nackMsg.remove(deliveryTag);
        }

        // re-send
        for (Msg msg : msgs) {
            if (msg.sendAmount > 3) {
                System.out.printf("Re-Send touch max time, [%s][%s]\n", msg.deliveryTag, msg.msg);
                continue;
            }
            System.out.printf("Re-Send [%s][%s]\n", msg.deliveryTag, msg.msg);
            sendMsg(msg);
        }
    }

    private void declareExAndQueue(Channel channel) throws IOException {
        Map<String, Object> args = new HashMap<>();
        args.put("alternate-exchange", RabbitMqConfig.BASIC_DEMO_AE);
        channel.exchangeDeclare(RabbitMqConfig.BASIC_DEMO_EX,
                BuiltinExchangeType.DIRECT, true, false, args);

        channel.queueDeclare(
                RabbitMqConfig.BASIC_DEMO_QUEUE,
                true, false, false, null);
        channel.queueBind(RabbitMqConfig.BASIC_DEMO_QUEUE, RabbitMqConfig.BASIC_DEMO_EX,
                RabbitMqConfig.BASIC_DEMO_QUEUE);

        channel.queueDeclare(
                RabbitMqConfig.BASIC_DEMO_QUEUE_2,
                true, false, false, null);
        channel.queueBind(RabbitMqConfig.BASIC_DEMO_QUEUE_2, RabbitMqConfig.BASIC_DEMO_EX,
                RabbitMqConfig.BASIC_DEMO_QUEUE_2);
    }

    private void declareAe(Channel channel) throws IOException {
        channel.exchangeDeclare(RabbitMqConfig.BASIC_DEMO_AE,
                BuiltinExchangeType.FANOUT, true, false, null);

        channel.queueDeclare(RabbitMqConfig.BASIC_DEMO_AE,
                true, false, false, null);

        channel.queueBind(RabbitMqConfig.BASIC_DEMO_AE, RabbitMqConfig.BASIC_DEMO_AE, "");
    }

    public void sendMsg(Msg msg) throws IOException {
        if (msg.queue.equals("")) {
            msg.queue = RabbitMqConfig.BASIC_DEMO_QUEUE;
        }
        msg.deliveryTag = channel.getNextPublishSeqNo();
        msg.sendAmount += 1;
        nackMsg.put(msg.deliveryTag, msg);
        channel.basicPublish(RabbitMqConfig.BASIC_DEMO_EX, msg.queue,
                false, null, msg.msg.getBytes());
    }

    public static void main(String[] args) {
        ConnectionFactory factory = RabbitMqConfig.connectionFactory();
        try (Connection connection = factory.newConnection()) {
            var publisher = new Publisher(connection);

            try (InputStreamReader input = new InputStreamReader(System.in);
                 BufferedReader reader = new BufferedReader(input)) {
                do {
                    System.out.println("Input Queue and Msg: ");
                    String str = reader.readLine();
                    String[] inputs = str.split(" ");
                    if (inputs.length < 2) {
                        System.out.println("Invalid Parameters");
                        continue;
                    }

                    String queue = inputs[0];
                    String msg = inputs[1];
                    if (msg.equals("")) {
                        break;
                    }

                    if (queue.equals("")) {
                        queue = RabbitMqConfig.BASIC_DEMO_QUEUE;
                    }

                    Msg message = new Msg();
                    message.sendAmount = 0;
                    message.msg = msg;
                    message.queue = queue;
                    publisher.sendMsg(message);
                    System.out.println("Send!!!");
                } while (true);
            }
        } catch (TimeoutException | IOException e) {
            e.printStackTrace();
        }
    }
}
