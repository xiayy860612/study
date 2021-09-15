package com.s2u2m.study.mq.rabbitmq.practice;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class ConsumerDemo  {

    public ConsumerDemo(Channel channel) throws IOException {
        channel.basicQos(3, false);
        channel.basicQos(10, true);
        channel.basicConsume(RabbitMQConfig.EX_QUEUE, false, new Consumer(channel));
        channel.basicConsume(RabbitMQConfig.EX_QUEUE, false, new Consumer(channel));
        channel.basicConsume(RabbitMQConfig.EX_QUEUE, false, new Consumer(channel));
    }

    private static class Consumer extends DefaultConsumer {
        public Consumer(Channel channel) {
            super(channel);
        }

        @Override
        public void handleDelivery(
            String consumerTag, Envelope envelope,
            AMQP.BasicProperties properties, byte[] body) throws IOException {
            System.out.printf("Thread[%s] Consumer[%s] Received Msg[%s](%s)\n",
                Thread.currentThread().getId(), consumerTag, envelope.getDeliveryTag(), new String(body));
            this.getChannel().basicAck(envelope.getDeliveryTag(), false);
        }
    }

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory factory = RabbitMQConfig.connectionFactory();
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            ConsumerDemo demo = new ConsumerDemo(channel);

            Thread.sleep(10000);
        }
    }
}
