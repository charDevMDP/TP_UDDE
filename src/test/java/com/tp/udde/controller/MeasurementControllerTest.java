package com.tp.udde.controller;

import com.tp.udde.domain.*;
import com.tp.udde.domain.dto.MeasurementDto;
import com.tp.udde.domain.enums.UserType;
import com.tp.udde.projections.Consumption;
import com.tp.udde.service.MeasurementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class MeasurementControllerTest {

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

    private Consumption createConsumption(){
        return new Consumption() {
            @Override
            public Float getTotalKwh() {
                return 4.50F;
            }

            @Override
            public Float getPriceTotal() {
                return 5.5F;
            }
        };
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

    private  MeasurementDto createMeasurementDto(){
        return MeasurementDto.builder()
                .serialNumber("123")
                .value(1.50F)
                .date("2020-05-05 00:00:00")
                .password("123")
                .build();
    }

    MeasurementService measurementService;
    MeasurementController measurementController;

    @BeforeEach
    public void setUp(){
        measurementService = mock(MeasurementService.class);
        measurementController = new MeasurementController(measurementService);
    }


    @Test
    public void addMeasurementOKTest(){
        MeasurementDto measurementDto = createMeasurementDto();
        Measurement measurement = createMeasurement();
        when(measurementService.add(any(Measurement.class))).thenReturn(measurement);

        Measurement response = measurementController.addMeasurement(measurementDto);

        assertEquals(measurement,response);

    }


    @Test
    public void getConsumptionOKTest(){
        Consumption consumption = createConsumption();
        LocalDate localDate = LocalDate.of(2020,05,9);
        when(measurementService.getConsumption(anyInt(),any(LocalDate.class),any(LocalDate.class))).thenReturn(consumption);

        Consumption response = measurementController.getConsumption(1,localDate,localDate);

        assertEquals(response,consumption);
    }



    @Test
    public void getMeasurementBetweenDatesOKTest(){
        Measurement measurement = createMeasurement();
        Meter meter = createMeter();
        MetersForMeasurement metersForMeasurement = new MetersForMeasurement();
        metersForMeasurement.setMeasurement(measurement);
        metersForMeasurement.setMeters(meter);
        Pageable pageable = PageRequest.of(1,10);
        LocalDate localDate = LocalDate.of(2020,05,9);
        Page<Measurement> PageMeasurement = new PageImpl(List.of(measurement));

        when(measurementService.getMeasurementByDates(eq(pageable),eq(meter.getId()),any(LocalDate.class),any(LocalDate.class))).thenReturn(PageMeasurement);

        Page<Measurement> response = measurementController.getMeasurementBetweenDates(pageable,meter.getId(),localDate,localDate);

        assertEquals(response,PageMeasurement);

    }

    @Test
    public void getMeasurementBetweenDatesForAddressOKTest(){
        Measurement measurement = createMeasurement();
        Pageable pageable = PageRequest.of(1,10);
        LocalDate localDate = LocalDate.of(2020,05,9);
        Page<Measurement> PageMeasurement = new PageImpl(List.of(measurement));

        when(measurementService.getMeasurementForDateForAddress(eq(pageable),anyInt(),any(),any())).thenReturn(PageMeasurement);

        Page<Measurement> response = measurementController.getMeasurementForDateForAddress(pageable,1,localDate,localDate);

        assertEquals(response,PageMeasurement);

    }


}
