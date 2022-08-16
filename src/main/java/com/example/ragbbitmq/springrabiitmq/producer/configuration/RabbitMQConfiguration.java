package com.example.ragbbitmq.springrabiitmq.producer.configuration;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {
    public static final String ROUTING_A = "routing.A";
    public static final String ROUTING_B = "routing.B";

    @Bean
    Queue queueA() {
        return new Queue("queue.A", false);
    }

    @Bean
    Queue queueB() {
        return new Queue("queue.B", false);
    }

    @Bean
    Queue allQueue() {
        return new Queue("queue.all", false);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange("exchange.topic");
    }

    @Bean
    Binding binding(Queue queueA, TopicExchange exchange) {
        return BindingBuilder.bind(queueA)
                .to(exchange)
                .with(ROUTING_A);
    }

    @Bean
    Binding bindingB(Queue queueB, TopicExchange exchange) {
        return BindingBuilder.bind(queueB)
                .to(exchange)
                .with(ROUTING_B);
    }

    @Bean
    Binding bindingAll(Queue allQueue, TopicExchange exchange) {
        return BindingBuilder.bind(allQueue)
                .to(exchange)
                .with("routing.*");
    }

    @Bean
    MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory factory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(factory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }
}
