package com.example.demo.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.Instant;


@Getter
@Setter
@Table(name = "messages")
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    User user1;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    User user2;

    String content;
    Instant sending_time;


    @PrePersist
    public void handleBeforeCreate(){
        this.sending_time= Instant.now();

    }
}
