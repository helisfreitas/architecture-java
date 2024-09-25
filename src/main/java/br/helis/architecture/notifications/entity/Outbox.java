package br.helis.architecture.notifications.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
public class Outbox {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank
    private String payload;

    @NotNull
    @PastOrPresent
    private LocalDateTime createdAt;

    @Setter
    private boolean processed;

    public Outbox(String payload) {
        this.payload = payload;
        this.createdAt = LocalDateTime.now();
    }

}