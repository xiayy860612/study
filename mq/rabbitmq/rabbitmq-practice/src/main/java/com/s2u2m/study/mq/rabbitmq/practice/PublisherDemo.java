package com.s2u2m.study.mq.rabbitmq.practice;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.Collection;
import java.util.Collections;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class PublisherDemo {

    private SortedMap<Long, Msg> map = new TreeMap<>();
    private Channel channel;

    public PublisherDemo(Channel channel) throws IOException {
        this.channel = channel;
        // 切换到发送者确认机制的模式, 并监听回调结果
        channel.confirmSelect();
        channel.addConfirmListener(this::ackCallback, this::nackCallback);
    }

    public void send(String routeKey, String content) throws IOException {
        // 记录下一个消息的唯一ID
        long uid = channel.getNextPublishSeqNo();
        Msg msg = new Msg(routeKey, content);
        map.put(uid, msg);

        AMQP.BasicProperties properties = new AMQP.BasicProperties().builder()
            // 设置发送的消息的持久化
            .deliveryMode(2)
            .build();
        channel.basicPublish(RabbitMQConfig.EX,
            routeKey, false, properties,
            content.getBytes());
    }

    private void nackCallback(long deliveryTag, boolean multiple) {
        System.out.printf("Send[%s] failed %s\n", deliveryTag, multiple ? "with multiple" : "");
        if (multiple) {
            Collection<Msg> msgs = map.headMap(deliveryTag).values();
        } else {
            Msg msg = map.get(deliveryTag);
        }

        // process will failed msg
    }

    private void ackCallback(long deliveryTag, boolean multiple) {
        System.out.printf("Send[%s] success %s\n", deliveryTag, multiple ? "with multiple" : "");
        if (multiple) {
            map.headMap(deliveryTag).clear();
        } else {
            map.remove(deliveryTag);
        }
    }

    private static class Msg {
        private String routeKey;
        private String content;

        public Msg(String routeKey, String content) {
            this.routeKey = routeKey;
            this.content = content;
        }
    }

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = RabbitMQConfig.connectionFactory();
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel();
             InputStreamReader reader = new InputStreamReader(System.in);
             BufferedReader bufferedReader = new BufferedReader(reader)) {
            PublisherDemo demo = new PublisherDemo(channel);
            while (true) {
                String input = bufferedReader.readLine();
                if (input.trim().length() == 0) {
                    break;
                }

                String[] params = input.split(" ");
                demo.send(params[0], params[1]);
            }
        }
    }
}
