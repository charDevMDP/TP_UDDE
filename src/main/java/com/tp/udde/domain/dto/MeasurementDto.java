package com.tp.udde.domain.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MeasurementDto {
    String serialNumber;
    float value;
    String date;
    String password;
}
