package br.helis.architecture.notifications.service;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.amqp.AmqpConnectException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import br.helis.architecture.notifications.entity.Outbox;
import br.helis.architecture.notifications.repository.OutboxRepository;

@Service
public class OutboxRelayService {

    private final OutboxRepository outboxRepository;

    private final RabbitTemplate rabbitTemplate;

    public OutboxRelayService(OutboxRepository outboxRepository, RabbitTemplate rabbitTemplate) {
        this.outboxRepository = outboxRepository;
        this.rabbitTemplate = rabbitTemplate;
    }
    
    @Retryable(
        retryFor = { AmqpConnectException.class }, 
        maxAttempts = 2,
        backoff = @Backoff(delay = 2000),
        listeners = "customRetryListener"
    )
    @Scheduled(fixedDelay = 6, timeUnit = TimeUnit.SECONDS)
    public synchronized void relayOutboxMessages() {
        List<Outbox> events = outboxRepository.findUnprocessedEvents();
        for (Outbox event : events) {
            rabbitTemplate.convertAndSend("outbox.exchange", "outbox.routingKey", event.getPayload());

            event.setProcessed(true);

            outboxRepository.save(event);
        }
    }
}
