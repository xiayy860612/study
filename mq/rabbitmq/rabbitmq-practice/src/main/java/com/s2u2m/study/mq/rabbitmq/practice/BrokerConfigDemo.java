package com.s2u2m.study.mq.rabbitmq.practice;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class BrokerConfigDemo {

    public void init() throws IOException, TimeoutException {
        var factory = RabbitMQConfig.connectionFactory();
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            // AE声明
            channel.exchangeDeclare(RabbitMQConfig.AE, BuiltinExchangeType.FANOUT,
                false, false, true, null);
            channel.queueDeclare(RabbitMQConfig.AE_QUEUE,
                true, false, false, null);
            channel.queueBind(RabbitMQConfig.AE_QUEUE, RabbitMQConfig.AE, "");

            // Exchange和Queue声明
            Map<String, Object> args = new HashMap<>();
            args.put("alternate-exchange", RabbitMQConfig.AE);
            channel.exchangeDeclare(RabbitMQConfig.EX, BuiltinExchangeType.DIRECT,
                true, false, false, args);
            channel.queueDeclare(RabbitMQConfig.EX_QUEUE,
                true, false, false, null);
            channel.queueBind(RabbitMQConfig.EX_QUEUE, RabbitMQConfig.EX, RabbitMQConfig.EX_QUEUE_ROUTE_KEY);
        }
    }

    public static void main(String[] args) throws IOException, TimeoutException {
        new BrokerConfigDemo().init();
    }

}
