package com.s2u2m.study.mq.rabbitmq.basic;

import com.rabbitmq.client.ConnectionFactory;

public class RabbitMqConfig {
    public static final String HOST = "localhost";
    public static final String USERNAME = "rabbitmq";
    public static final String PASSWORD = "rabbitmq";
    public static final String VHOST = "rabbitmq-study";

    public static final String BASIC_DEMO_EX = "basic-ex";
    public static final String BASIC_DEMO_AE = "basic-alternate-exchange";
    public static final String BASIC_DEMO_QUEUE = "basic-queue";



    public static ConnectionFactory connectionFactory() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(RabbitMqConfig.HOST);
        factory.setUsername(RabbitMqConfig.USERNAME);
        factory.setPassword(RabbitMqConfig.PASSWORD);
        factory.setVirtualHost(RabbitMqConfig.VHOST);
        return factory;
    }
}
