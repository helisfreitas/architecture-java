package br.helis.architecture.notifications.service;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import br.helis.architecture.notifications.entity.Outbox;
import br.helis.architecture.notifications.repository.OutboxRepository;

@Service
public class OutboxRelayService {

    private final OutboxRepository outboxRepository;

    private final RabbitTemplate rabbitTemplate;

    public OutboxRelayService (OutboxRepository outboxRepository, RabbitTemplate rabbitTemplate) {
        this.outboxRepository = outboxRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Scheduled(fixedRate = 5, timeUnit = TimeUnit.SECONDS)  // Runs every 5 seconds
    public void relayOutboxMessages() {
        List<Outbox> events = outboxRepository.findUnprocessedEvents();

        for (Outbox event : events) {
            try {
                rabbitTemplate.convertAndSend("outbox.exchange", "outbox.routingKey", event.getPayload());

                event.setProcessed(true);

                outboxRepository.save(event);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
