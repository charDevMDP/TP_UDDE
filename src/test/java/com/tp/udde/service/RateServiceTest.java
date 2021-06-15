package com.tp.udde.service;

import com.tp.udde.controller.utils.TestUtils;
import com.tp.udde.domain.Rate;
import com.tp.udde.repository.RateRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RateServiceTest {

    private RateService rateService;
    private RateRepository rateRepository;


    @BeforeEach
    public void setUp() {
        this.rateRepository = mock(RateRepository.class);
        this.rateService = new RateService(this.rateRepository);
    }


    @Test
    public void testAddOK() {
        Rate spected = TestUtils.getRate();
        when(this.rateRepository.save(any())).thenReturn(spected);
        Rate actual = this.rateService.add(spected);

        assertEquals(actual, spected);
        verify(this.rateRepository, times(1)).save(any());

    }


    @Test
    public void testUpdateOK() {
        Rate spected = TestUtils.getRate();
        when(this.rateRepository.findById(any())).thenReturn(Optional.of(spected));
        when(this.rateRepository.save(any())).thenReturn(spected);
        Rate actual = this.rateService.update(1, spected);

        assertEquals(spected, actual);
        verify(this.rateRepository, times(1)).findById(any());
        verify(this.rateRepository, times(1)).save(any());

    }


    @Test
    public void testUpdateResponseStatus() {
        Rate spected = TestUtils.getRate();
        when(this.rateRepository.findById(any())).thenReturn(Optional.empty());

        Assertions.assertThrows(ResponseStatusException.class, () -> {
            this.rateService.update(1, spected);
        });


    }


    @Test
    public void getByIdOK() {
        Rate spected = TestUtils.getRate();
        when(this.rateRepository.findById(any())).thenReturn(Optional.of(spected));
        Rate actual = this.rateService.getById(1);

        assertEquals(spected, actual);
        verify(this.rateRepository, times(1)).findById(any());
    }


    @Test
    public void getByIdHttpClientError() {
        Rate spected = TestUtils.getRate();
        when(this.rateRepository.findById(any())).thenThrow(HttpClientErrorException.class);


        Assertions.assertThrows(HttpClientErrorException.class, () -> {
            Rate actual = this.rateService.getById(1);
        });
    }


    @Test
    public void deleteByIdOK() {
        Rate spected = TestUtils.getRate();
        when(this.rateRepository.findById(any())).thenReturn(Optional.of(spected));
        this.rateService.deleteByIdRate(1);

        verify(this.rateRepository, times(1)).delete(any());

    }

    @Test
    public void deleteByIdHttpClientError() {

        when(this.rateRepository.findById(any())).thenThrow(HttpClientErrorException.class);

        Assertions.assertThrows(HttpClientErrorException.class, () -> {
            this.rateService.deleteByIdRate(1);
        });


    }


    @Test
    public void testGetAllOk() {
        Pageable pageable = PageRequest.of(1, 10);
        Page<Rate> pageExpected = new PageImpl(TestUtils.getRates());

        when(this.rateRepository.getAll(any())).thenReturn(pageExpected);

        Page<Rate> actualPage = this.rateService.getAll(pageable);

        assertEquals(pageExpected, actualPage);
        verify(this.rateRepository, times(1)).getAll(any());

    }

}