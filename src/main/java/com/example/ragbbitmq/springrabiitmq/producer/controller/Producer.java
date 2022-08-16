package com.example.ragbbitmq.springrabiitmq.producer.controller;

import com.example.ragbbitmq.springrabiitmq.producer.model.Message;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Producer {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private TopicExchange exchange;

    @PostMapping("/post")
    public String send(@RequestBody Message message) {
        rabbitTemplate.convertAndSend(exchange.getName(), "routing.A", message);
        return "Message sent successfully";
    }

    @GetMapping("/hello")
    public ResponseEntity<String> helloWorld() {
        return ResponseEntity.ok("Hola Docker");
    }
}
