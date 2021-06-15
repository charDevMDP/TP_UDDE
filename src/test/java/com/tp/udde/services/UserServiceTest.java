package com.tp.udde.services;

import com.tp.udde.controller.UserController;
import com.tp.udde.domain.User;
import com.tp.udde.domain.dto.UserDto;
import com.tp.udde.domain.enums.UserType;
import com.tp.udde.exception.ClientNotExists;
import com.tp.udde.exception.InvalidLoginException;
import com.tp.udde.exception.UserException;
import com.tp.udde.exception.ValidationException;
import com.tp.udde.projections.MeterUser;
import com.tp.udde.repository.UserRepository;
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
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserServiceTest {

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


    UserRepository userRepository;
    UserService userService;

    @BeforeEach
    public void setUp(){
        userRepository = mock(UserRepository.class);

        userService = new UserService(userRepository);
    }



    @Test
    public void getByIdOKTest() throws ClientNotExists {

        User user = createUser();
        when(userRepository.findById(anyInt())).thenReturn(java.util.Optional.ofNullable(user));

        User response = userService.getById(1);

        assertEquals(user,response);
    }

    /*
    @Test
    public void getAllOkTest(){

        User user = createUser();
        Pageable pageable = PageRequest.of(1,10);
        Page<User> PageUser = new PageImpl(List.of(user));

        when(userRepository.findAll(pageable)).thenReturn(PageUser);

        Page<User> response =  userService.getAll(pageable);

        assertEquals(response,PageUser);
    }

     */

    @Test
    public void meterOfUserOKTest(){
        MeterUser meterUser = createMeterUser();
        when(userRepository.getMeterUser(anyInt())).thenReturn(meterUser);

        MeterUser response = userService.meterOfUser(anyInt());

        assertEquals(response,meterUser);
    }

    @Test
    public void LoginOKTest() throws UserException, ValidationException, InvalidLoginException {
        UserDto userDto = createUserDto();
        User user = createUser();
        when(userRepository.getByUsernameAndPassword(anyString(),anyString())).thenReturn(user);

        User response = userService.login(anyString(),anyString());

        assertEquals(response,response);

    }



}
