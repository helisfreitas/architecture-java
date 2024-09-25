package br.helis.architecture.notifications.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


import br.helis.architecture.notifications.entity.Outbox;

public interface OutboxRepository extends CrudRepository<Outbox, Long>{

    @Query("SELECT o FROM Outbox o WHERE o.processed = false")
    List<Outbox> findUnprocessedEvents();

}
