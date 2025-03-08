package com.example.demo.domain.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResReportDTO {
    Long id;
    Long id_sender;
    Long id_receiver;
    String status;
    String reson;
    String created_at;


}
