package com.tp.udde.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tp.udde.controller.web.LoginController;
import com.tp.udde.domain.dto.LoginRequestDto;
import com.tp.udde.domain.dto.UserDto;
import com.tp.udde.exception.InvalidLoginException;
import com.tp.udde.exception.UserException;
import com.tp.udde.exception.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LoginControllerTest {

    private UserDto createUserDto(){
        return UserDto.builder()
                .id(1)
                .username("Char")
                .userType("CLIENT")
                .build();
    }

    private LoginRequestDto loginRequestDto;

    UserController userController;
    ObjectMapper objectMapper;

    LoginController loginController;

    @BeforeEach
    public void setUp(){
        userController = mock(UserController.class);
        objectMapper = mock(ObjectMapper.class);
        loginRequestDto = LoginRequestDto
                .builder().username("char").password("1234").build();

        loginController = new LoginController(userController,objectMapper);
    }

    @Test
    public void clientLoginOK() throws ValidationException, InvalidLoginException, UserException {
        //given
        UserDto userDto = createUserDto();
        when(userController.login(anyString(),anyString())).thenReturn(userDto);

        //when
        ResponseEntity<UserDto> response = loginController.login(loginRequestDto);

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
