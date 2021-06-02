package com.tp.udde.domain.dto;

import com.tp.udde.domain.Meter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MeterUserDto {

   private String name;
   private Integer numberMeter;

}
