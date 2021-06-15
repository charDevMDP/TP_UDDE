package com.tp.udde.controller;

import com.tp.udde.domain.Meter;
import com.tp.udde.domain.User;
import com.tp.udde.domain.dto.UserDto;
import com.tp.udde.domain.enums.UserType;
import com.tp.udde.exception.ClientNotExists;
import com.tp.udde.exception.InvalidLoginException;
import com.tp.udde.exception.UserException;
import com.tp.udde.exception.ValidationException;
import com.tp.udde.projections.MeterUser;
import com.tp.udde.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.lang.reflect.Type;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    private User createUser(){
        return User.builder()
                .id(1)
                .name("Carlos")
                .username("charles")
                .surname("Nuñez")
                .dni("123")
                .password("123")
                .email("char@char.com")
                .userType(UserType.CLIENT)
                .build();
    }

    private MeterUser createMeterUser(){
        return new MeterUser() {
            @Override
            public String getName() {
                return "Carlos";
            }

            @Override
            public Integer getNumberMeter() {
                return 1;
            }
        };
    }

    private UserDto createUserDto(){
        return UserDto.builder()
                .id(1)
                .username("charles")
                .userType("CLIENT")
                .build();
    }

    ModelMapper modelMapper;
    UserService userService;
    UserController userController;

    @BeforeEach
    public void setUp(){
        modelMapper = mock(ModelMapper.class);
        userService = mock(UserService.class);

        userController = new UserController(modelMapper,userService);
    }


    @Test
    public void getByIdOKTest() throws ClientNotExists {

        User user = createUser();
        when(userService.getById(anyInt())).thenReturn(user);

        User response = userController.getById(1);

        assertEquals(user,response);
    }

    @Test
    public void getByIdTHROWTest() throws ClientNotExists {

        when(userService.getById(anyInt())).thenThrow(new ClientNotExists());

        assertThrows(ClientNotExists.class, () -> { userController.getById(1); });
    }

    @Test
    public void getAllOkTest(){

        User user = createUser();
        Pageable pageable = PageRequest.of(1,10);
        Page<User> PageUser = new PageImpl(List.of(user));

        when(userService.getAll(pageable)).thenReturn(PageUser);

        Page<User> response =  userController.getUsers(pageable);

        assertEquals(response,PageUser);
    }

    @Test
    public void meterOfUserOKTest(){
        MeterUser meterUser = createMeterUser();
        when(userService.meterOfUser(anyInt())).thenReturn(meterUser);

        MeterUser response = userController.meterofuser(anyInt());

        assertEquals(response,meterUser);
    }

    @Test
    public void LoginOKTest() throws UserException, ValidationException, InvalidLoginException {
        UserDto userDto = createUserDto();
        User user = createUser();
        when(userService.login(anyString(),anyString())).thenReturn(user);

        UserDto response = userController.login(anyString(),anyString());
        ModelMapper modelM = modelMapper.map(user, (Type) UserDto.class);

        assertEquals(response,modelM);

    }

    @Test
    public void LoginTHROWTest() throws UserException, ValidationException, InvalidLoginException {

        when(userService.login(anyString(),anyString())).thenThrow(new UserException());

        assertThrows(UserException.class, () -> { userController.login(anyString(),anyString());});

    }


}
