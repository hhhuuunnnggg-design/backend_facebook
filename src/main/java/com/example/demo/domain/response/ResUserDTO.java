package com.example.demo.domain.response;


import java.time.Instant;
import java.time.LocalDate;

import com.example.demo.domain.Enum.genderEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResUserDTO {
    long id;
    String email;
    genderEnum gender;
    String password;
    String fullname;
    LocalDate birthday;
    Instant createdAt;
    Boolean active;


}
