package com.s2u2m.study.mq.rabbitmq.practice;

import com.rabbitmq.client.ConnectionFactory;

public class RabbitMQConfig {
    public static final String HOST = "localhost";
    public static final String USERNAME = "rabbitmq";
    public static final String PASSWORD = "rabbitmq";
    public static final String VHOST = "rabbitmq-study";

    public static final String AE = "demo-ae";
    public static final String AE_QUEUE = "demo-ae-queue";
    public static final String EX = "demo-ex";
    public static final String EX_QUEUE = "demo-ex-queue";
    public static final String EX_QUEUE_ROUTE_KEY = "demo-ex-queue-rk";

    public static ConnectionFactory connectionFactory() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(RabbitMQConfig.HOST);
        factory.setUsername(RabbitMQConfig.USERNAME);
        factory.setPassword(RabbitMQConfig.PASSWORD);
        factory.setVirtualHost(RabbitMQConfig.VHOST);
        return factory;
    }
}
