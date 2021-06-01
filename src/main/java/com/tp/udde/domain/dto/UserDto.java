package com.tp.udde.domain.dto;

import com.tp.udde.domain.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    Integer id;
    String surname;
    String userType;
}
