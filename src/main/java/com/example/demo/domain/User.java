package com.example.demo.domain;


import com.example.demo.domain.Enum.genderEnum;
import com.example.demo.domain.converter.LocalDateConverter;
import com.example.demo.util.SecurityUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Table(name = "users")
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @NotBlank(message = "email không được để trống")
    String email;
    @Enumerated(EnumType.STRING)
    genderEnum gender;

    //@Min(value = 6,message = "mật khẩu phải trên 6 ký tự")
    String password;
    String firstname;
    String latename;

    @Column(name = "birthday")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy") // Định dạng JSON trả về
    @DateTimeFormat(pattern = "dd/MM/yyyy") // Định dạng dữ liệu từ request
    private LocalDate birthday;


    Instant createdAt;
    // (lam ve role sau)
    //Long role_id;
    Boolean active;

    @OneToMany(mappedBy = "user1")
    @JsonIgnore
    List<Message> sentMessages;

    @OneToMany(mappedBy = "user2")
    @JsonIgnore
    List<Message> receivedMessages;


    @PrePersist
    public void handleBeforeCreate(){
        this.createdAt= Instant.now();
        this.active=true;
    }

}
