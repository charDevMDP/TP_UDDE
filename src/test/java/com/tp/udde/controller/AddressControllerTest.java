package com.tp.udde.controller;

import com.tp.udde.domain.Address;
import com.tp.udde.domain.City;
import com.tp.udde.domain.User;
import com.tp.udde.domain.enums.UserType;
import com.tp.udde.exception.ClientNotExists;
import com.tp.udde.service.AddressService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class AddressControllerTest {


    private User createUser(){
        return User.builder()
                .id(1)
                .name("Carlos")
                .username("Char")
                .surname("Nuñez")
                .dni("123")
                .password("123")
                .email("char@char.com")
                .userType(UserType.CLIENT)
                .build();
    }

    private City createCity(){
        return  City.builder()
                .id(1)
                .name("mardel")
                .build();
    }

    private Address createAddress(){
        return Address.builder()
                .id(1)
                .city(createCity())
                .user(createUser())
                .name("siempreviva")
                .number(123)
                .department(0)
                .build();
    }

    AddressService addressService;
    AddressController addressController;

    @BeforeEach
    public void setUp(){
        addressService = mock(AddressService.class);
        addressController = new AddressController(addressService);
    }


    @Test
    public void getByIdOKTest() throws ClientNotExists {

        Address address = createAddress();

        when(addressService.getById(anyInt())).thenReturn(address);

        Address response = addressController.getById(1);

        assertEquals(address,response);
    }

    @Test
    public void getAllOkTest(){

        Address address = createAddress();
        Pageable pageable = PageRequest.of(1,10);
        Page<Address> PageAddress = new PageImpl(List.of(address));

        when(addressService.getAll(pageable)).thenReturn(PageAddress);

        Page<Address> response =  addressController.getAll(pageable);

        assertEquals(response,PageAddress);
    }

    @Test
    public void updateAddressOKTest(){
        Address address = createAddress();
        when(addressService.update(anyInt(),eq(address))).thenReturn(address);

        Address response = addressController.updateAddressById(1,address);

        assertEquals(address,response);
    }


    @Test
    public void deleteByIdOKTest(){
        doNothing().when(addressService).deleteById(anyInt());
        addressController.deleteByIdAddress(anyInt());

        verify(addressService,times(1)).deleteById(anyInt());

    }


}
