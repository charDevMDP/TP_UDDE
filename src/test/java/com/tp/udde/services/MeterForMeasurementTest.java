package com.tp.udde.services;

import com.tp.udde.controller.MetersForMeasurementController;
import com.tp.udde.domain.*;
import com.tp.udde.domain.enums.UserType;
import com.tp.udde.repository.MetersForMeasurementRepository;
import com.tp.udde.service.MetersForMeasurementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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

    MetersForMeasurementRepository metersForMeasurementRepository;
    MetersForMeasurementService metersForMeasurementService;


    @BeforeEach
    public void setUp(){
        metersForMeasurementRepository = mock(MetersForMeasurementRepository.class);

        metersForMeasurementService = new MetersForMeasurementService(metersForMeasurementRepository);
    }

    @Test
    public void addMFMOKTest(){
        Meter meter = createMeter();
        Measurement measurement = createMeasurement();
        MetersForMeasurement metersForMeasurement = new MetersForMeasurement();
        metersForMeasurement.setMeasurement(measurement);
        metersForMeasurement.setMeters(meter);

        when(metersForMeasurementRepository.save(metersForMeasurement)).thenReturn(metersForMeasurement);

        MetersForMeasurement response = metersForMeasurementService.add(metersForMeasurement);

        assertEquals(metersForMeasurement,response);

    }


}
