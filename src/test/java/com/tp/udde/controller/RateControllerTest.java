package com.tp.udde.controller;

import com.tp.udde.domain.Meter;
import com.tp.udde.domain.Rate;
import com.tp.udde.service.RateService;
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

public class RateControllerTest {

    private Rate createRate(){
        return Rate.builder()
                .id(1)
                .type("qwe")
                .price(1.25F)
                .build();
    }

    RateService rateService;
    RateController rateController;

    @BeforeEach
    public void setUp(){
        rateService = mock(RateService.class);
        rateController = new RateController(rateService);
    }


    @Test
    public void getAllOkTest(){
        Rate rate = createRate();
        Pageable pageable = PageRequest.of(1,10);
        Page<Rate> PageMeter = new PageImpl(List.of(rate));

        when(rateService.getAll(pageable)).thenReturn(PageMeter);

        Page<Rate> response =  rateController.getRates(pageable);

        assertEquals(response,PageMeter);
    }


    @Test
    public void addRateOKTest(){
        Rate rate = createRate();
        when(rateService.add(rate)).thenReturn(rate);

        Rate response = rateController.addRate(rate);

        assertEquals(rate,response);

    }


    @Test
    public void deleteByIdOKTest(){
        doNothing().when(rateService).deleteByIdRate(anyInt());
       rateController.deleteByIdRate(1);

        verify(rateService,times(1)).deleteByIdRate(anyInt());

    }


    @Test
    public void replaceRateOKTest(){
        Rate rate = createRate();
        when(rateService.update(anyInt(),eq(rate))).thenReturn(rate);

        Rate response = rateController.replaceRate(1,rate);

        assertEquals(rate.getId(),response.getId());
    }





}
