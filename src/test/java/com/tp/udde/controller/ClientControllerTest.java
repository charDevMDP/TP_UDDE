package com.tp.udde.controller;

import com.tp.udde.controller.web.ClientController;
import com.tp.udde.domain.User;
import com.tp.udde.domain.enums.UserType;
import com.tp.udde.exception.ClientNotExists;
import com.tp.udde.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ClientControllerTest {

    private User createNewUser(Integer id, String name, String username, String surname, String dni, String password, String email, UserType userType){
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setUsername(username);
        user.setSurname(surname);
        user.setDni(dni);
        user.setPassword(password);
        user.setEmail(email);
        user.setUserType(userType);
        return user;
    }


    private User createUser(){
        return User.builder()
                .id(1)
                .name("David")
                .username("Muñoz")
                .surname("Cucamonga")
                .dni("123")
                .password("123")
                .email("sadfs@asdfg.com")
                .userType(UserType.CLIENT)
                .build();
    }

    UserController userController;
    UserService userService;
    ClientController clientController;
    MeasurementController measurementController;
    InvoiceController invoiceController;

    @BeforeEach
    public void setUp(){
        userController = mock(UserController.class);
        userService = mock(UserService.class);
        invoiceController = mock(InvoiceController.class);
        measurementController = mock(MeasurementController.class);

        clientController = new ClientController(userController,invoiceController,measurementController);
    }

    @Test
    public void getClientOKTest() throws ClientNotExists {
        //given
        User user = createUser();
        when(userService.getById(anyInt())).thenReturn(user);
        when(userController.getById(anyInt())).thenReturn(user);

        //when
        ResponseEntity<User> responseEntity = clientController.getById(1);

        //then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(user.getName(),responseEntity.getBody().getName());
    }

}
