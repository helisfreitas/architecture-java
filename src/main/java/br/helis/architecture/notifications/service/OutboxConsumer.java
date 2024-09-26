package br.helis.architecture.notifications.service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class OutboxConsumer {

    protected final Log logger = LogFactory.getLog(getClass());
    
    private final RabbitTemplate rabbitTemplate;


    public OutboxConsumer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Scheduled(fixedRate = 5, timeUnit = TimeUnit.SECONDS)
    public void receiveMessage(){

        try {
            Optional<Object> outbox = Optional.ofNullable(rabbitTemplate.receiveAndConvert("outbox.queue"));

            outbox.ifPresent(e -> logger.info("Saida: " + e));
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        
    }

    
}
