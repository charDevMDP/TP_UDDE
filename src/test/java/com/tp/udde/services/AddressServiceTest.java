package com.tp.udde.services;

import com.tp.udde.domain.Address;
import com.tp.udde.domain.City;
import com.tp.udde.domain.User;
import com.tp.udde.domain.enums.UserType;
import com.tp.udde.exception.ClientNotExists;
import com.tp.udde.repository.AddressRepository;
import com.tp.udde.repository.UserRepository;
import com.tp.udde.service.AddressService;
import com.tp.udde.service.UserService;
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

public class AddressServiceTest {


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
    AddressRepository addressRepository;


    @BeforeEach
    public void setUp(){
        addressRepository = mock(AddressRepository.class);
        addressService = new AddressService(addressRepository);
    }


    @Test
    public void getByIdOKTest() throws ClientNotExists {

        Address address = createAddress();

        when(addressRepository.findById(anyInt())).thenReturn(java.util.Optional.ofNullable(address));

        Address response = addressService.getById(1);

        assertEquals(address,response);
    }


    @Test
    public void getAllOkTest(){

        Address address = createAddress();
        Pageable pageable = PageRequest.of(1,10);
        Page<Address> PageAddress = new PageImpl(List.of(address));

        when(addressRepository.getAll(pageable)).thenReturn(PageAddress);

        Page<Address> response =  addressService.getAll(pageable);

        assertEquals(response,PageAddress);
    }

    /*
    @Test
    public void updateAddressOKTest(){
        Address address = createAddress();
        when(addressRepository.findById(anyInt())).thenReturn(java.util.Optional.ofNullable(address));

        Address response = addressService.update(1,address);

        assertEquals(address,response);
    }




    @Test
    public void deleteByIdOKTest(){
        doNothing().when(addressRepository).deleteById(anyInt());
        addressService.deleteById(anyInt());

        verify(addressRepository,times(1)).deleteById(anyInt());

    }
     */




}
