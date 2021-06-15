package com.tp.udde.controller.web;

import com.tp.udde.controller.MeasurementController;
import com.tp.udde.controller.MeterController;
import com.tp.udde.controller.MetersForMeasurementController;
import com.tp.udde.domain.*;
import com.tp.udde.domain.dto.MeasurementDto;
import com.tp.udde.domain.enums.UserType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;

import java.util.Date;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


public class BroadcastControllerTest {

    private MeasurementDto createMeasurementDto(){
        return MeasurementDto.builder()
                .serialNumber("1")
                .value(50F)
                .date("2020-05-02")
                .password("123")
                .build();
    }

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

    MeasurementController measurementController;
    MeterController meterController;
    MetersForMeasurementController metersForMeasurementController;

    BroadcastController broadcastController;

    @BeforeEach
    public void setUp(){
        measurementController = mock(MeasurementController.class);
        meterController = mock(MeterController.class);
        metersForMeasurementController = mock(MetersForMeasurementController.class);

        broadcastController = new BroadcastController(measurementController,meterController,metersForMeasurementController);

    }

    @Test
    public void addMeasurement(){
        MeasurementDto measurementDto = createMeasurementDto();
        Meter meter = createMeter();
        Measurement measurement = createMeasurement();
        when(meterController.getByMeterNumberAndPass(measurementDto.getSerialNumber(),measurementDto.getPassword())).thenReturn(meter);
        when(measurementController.addMeasurement(measurementDto)).thenReturn(measurement);

        broadcastController.addMeasurement(measurementDto);

        verify(meterController, times(1)).getByMeterNumberAndPass(any(),any());

    }
}
