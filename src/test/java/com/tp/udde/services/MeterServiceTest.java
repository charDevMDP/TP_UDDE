package com.tp.udde.services;

import com.tp.udde.controller.MeterController;
import com.tp.udde.domain.*;
import com.tp.udde.domain.enums.UserType;
import com.tp.udde.repository.MeterRepository;
import com.tp.udde.service.MetersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class MeterServiceTest {


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

    private Brand createBrand(){
        return Brand.builder()
                .id(1)
                .name("qawe")
                .type("asd")
                .build();
    }

    private Rate createRate(){
        return Rate.builder()
                .id(1)
                .type("qwe")
                .price(1.25F)
                .build();
    }

    private Model createModel(){
        return Model.builder()
                .id(1)
                .brand(createBrand())
                .name("asd")
                .type("asd")
                .build();
    }

    private Meter createMeter(){
        return Meter.builder()
                .id(1)
                .address(createAddress())
                .model(createModel())
                .rate(createRate())
                .number(123)
                .password("123")
                .build();
    }

    MeterRepository meterRepository;
    MetersService meterService;


    @BeforeEach
    public void setUp(){
        meterRepository = mock(MeterRepository.class);
        meterService = new MetersService(meterRepository);
    }


    @Test
    public void getAllOkTest(){
        Meter meter = createMeter();
        Pageable pageable = PageRequest.of(1,10);
        Page<Meter> PageMeter = new PageImpl(List.of(meter));

        when(meterRepository.getMeters(pageable)).thenReturn(PageMeter);

        Page<Meter> response =  meterService.getAll(pageable);

        assertEquals(response,PageMeter);
    }



    @Test
    public void getByIdOKTest(){

        Meter meter = createMeter();
        when(meterRepository.findById(anyInt())).thenReturn(java.util.Optional.ofNullable(meter));

        Meter response = meterService.getById(1);

        assertEquals(meter,response);

    }

    @Test
    public void addMeterOKTest(){
        Meter meter = createMeter();
        when(meterRepository.save(meter)).thenReturn(meter);

        Meter response = meterService.add(meter);

        assertEquals(meter,response);

    }

/*
    @Test
    public void replaceMeterOKTest(){
        Meter meter = createMeter();
        when(meterRepository.findById(anyInt())).thenReturn(java.util.Optional.ofNullable(meter));

        Meter response = meterService.update(1,meter);

        assertEquals(meter,response);
    }



    @Test
    public void deleteByIdOKTest(){
        meterService.deleteById(anyInt());

        verify(meterRepository,times(1)).deleteById(1);
    }

 */

    @Test
    public void getByMeterNumberAndPassOKTest(){
        Meter meter = createMeter();
        when(meterRepository.getByMeterNumberAndPass(anyString(),anyString())).thenReturn(meter);

        Meter response = meterService.getByMeterNumberAndPass("123","123");

        assertEquals(meter,response);
    }


}
