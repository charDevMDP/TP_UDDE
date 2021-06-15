package com.tp.udde.controller.web;

import com.tp.udde.controller.MetersForMeasurementController;
import com.tp.udde.domain.*;
import com.tp.udde.domain.enums.UserType;
import com.tp.udde.service.MetersForMeasurementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class MeterForMeasurementTest {


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

    private Measurement createMeasurement(){
        return Measurement.builder()
                .id(1)
                .date(new Date())
                .idInvoice(1)
                .kwh(5.5F)
                .build();
    }

    private MetersForMeasurement createMFM(){
        return MetersForMeasurement
                .builder()
                .id(1)
                .measurement(createMeasurement())
                .meters(createMeter())
                .build();
    }

    MetersForMeasurementService metersForMeasurementService;

    MetersForMeasurementController metersForMeasurementController;

    @BeforeEach
    public void setUp(){
        metersForMeasurementService = mock(MetersForMeasurementService.class);

        metersForMeasurementController = new MetersForMeasurementController(metersForMeasurementService);
    }

    @Test
    public void addMFMOKTest(){
        Meter meter = createMeter();
        Measurement measurement = createMeasurement();
        MetersForMeasurement metersForMeasurement = new MetersForMeasurement();
        metersForMeasurement.setMeasurement(measurement);
        metersForMeasurement.setMeters(meter);

        when(metersForMeasurementService.add(metersForMeasurement)).thenReturn(metersForMeasurement);

        MetersForMeasurement response = metersForMeasurementController.addMeterForMeasurement(meter,measurement);

        assertEquals(metersForMeasurement,response);

    }



}

