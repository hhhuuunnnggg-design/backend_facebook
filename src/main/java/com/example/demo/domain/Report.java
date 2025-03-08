package com.example.demo.domain;

import com.example.demo.domain.Enum.reportEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Entity
@Getter
@Setter
@Table(name = "report")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @NotNull(message = "Người gửi không được để trống")
    @ManyToOne
    @JoinColumn(name = "sentder_id")
    User user_sent_report;

    @NotNull(message = "Người gửi không được để trống")
    @ManyToOne
    @JoinColumn(name = "receiver_id")
    User user_received_report;


    String reason;
    reportEnum status;
    Instant createdAt;

    @PrePersist
    public void handleBeforeCreate(){
        this.createdAt= Instant.now();
    }

}
