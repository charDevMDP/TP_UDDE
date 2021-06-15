package com.tp.udde.controller;

import com.tp.udde.domain.*;
import com.tp.udde.domain.enums.UserType;
import com.tp.udde.service.MetersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;

public class MeterControllerTest {

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

    MetersService meterService;

    MeterController meterController;

    @BeforeEach
    public void setUp(){
        meterService = mock(MetersService.class);
        meterController = new MeterController(meterService);
    }


    @Test
    public void getAllOkTest(){
        Meter meter = createMeter();
        Pageable pageable = PageRequest.of(1,10);
        Page<Meter> PageMeter = new PageImpl(List.of(meter));

        when(meterService.getAll(pageable)).thenReturn(PageMeter);

        Page<Meter> response =  meterController.getAllMeter(pageable);

        assertEquals(response,PageMeter);
    }


    @Test
    public void getByIdOKTest(){

        Meter meter = createMeter();
        when(meterService.getById(anyInt())).thenReturn(meter);

        Meter response = meterController.getByIdMeter(1);

        assertEquals(meter,response);

    }

    @Test
    public void addMeterOKTest(){
        Meter meter = createMeter();
        when(meterService.add(meter)).thenReturn(meter);

        Meter response = meterController.addMeter(meter);

        assertEquals(meter,response);

    }


    @Test
    public void replaceMeterOKTest(){
        Meter meter = createMeter();
        when(meterService.update(anyInt(),eq(meter))).thenReturn(meter);

        Meter response = meterController.replaceMeter(1,meter);

        assertEquals(meter,response);
    }



    @Test
    public void deleteByIdOKTest(){
        meterController.deleteByIdMeter(anyInt());

       verify(meterService,times(1)).deleteById(anyInt());
    }

    @Test
    public void getByMeterNumberAndPassOKTest(){
        Meter meter = createMeter();
        when(meterService.getByMeterNumberAndPass(anyString(),anyString())).thenReturn(meter);

        Meter response = meterController.getByMeterNumberAndPass("123","123");

        assertEquals(meter,response);
    }

}
