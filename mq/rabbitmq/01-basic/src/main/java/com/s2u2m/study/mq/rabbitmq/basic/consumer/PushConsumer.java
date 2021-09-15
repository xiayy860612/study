package com.s2u2m.study.mq.rabbitmq.basic.consumer;

import com.rabbitmq.client.*;
import com.s2u2m.study.mq.rabbitmq.basic.RabbitMqConfig;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class PushConsumer extends DefaultConsumer {

    public PushConsumer(Channel channel) throws IOException {
        super(channel);
    }

    @Override
    public void handleDelivery(
            String consumerTag, Envelope envelope,
            AMQP.BasicProperties properties, byte[] body) throws IOException {

        System.out.printf("Thread[%s] Consumer[%s] Received %s\n",
                Thread.currentThread().getId(), consumerTag, new String(body));
        this.getChannel().basicAck(envelope.getDeliveryTag(), false);
    }

    public static void main(String[] args) {
        ConnectionFactory factory = RabbitMqConfig.connectionFactory();
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            System.out.println(Thread.currentThread().getId());
            channel.basicQos(3, false);
            channel.basicQos(10, true);
            channel.basicConsume(RabbitMqConfig.BASIC_DEMO_QUEUE, false, new PushConsumer(channel));

            Thread.sleep(10000);
        } catch (TimeoutException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
