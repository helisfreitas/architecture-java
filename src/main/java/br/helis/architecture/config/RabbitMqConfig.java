package br.helis.architecture.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    @Bean
    public TopicExchange outboxExchange() {
        return new TopicExchange("outbox.exchange");
    }

    @Bean
    public Queue outboxQueue() {
        return new Queue("outbox.queue", true);  // Durable queue
    }

    @Bean
    public Binding binding(Queue outboxQueue, TopicExchange outboxExchange) {
        return BindingBuilder.bind(outboxQueue).to(outboxExchange).with("outbox.routingKey");
    }
}
