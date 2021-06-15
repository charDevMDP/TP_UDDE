package com.tp.udde.service;

import com.tp.udde.controller.utils.TestUtils;
import com.tp.udde.domain.Model;
import com.tp.udde.repository.ModelRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ModelServiceTest {
    private ModelService modelService;
    private ModelRepository modelRepository;




    @BeforeEach
    public void setUp(){
        this.modelRepository = mock(ModelRepository.class);
        this.modelService = new ModelService(this.modelRepository);
    }


    @Test
    public void testAddOK(){
        Model spected = TestUtils.getModel();
        when(this.modelRepository.save(any())).thenReturn(spected);
        Model actual = this.modelService.add(spected);

        assertEquals(actual, spected);
        verify(this.modelRepository, times(1)).save(any());

    }


    @Test
    public void testUpdateOK(){
        Model spected = TestUtils.getModel();
        when(this.modelRepository.findById(any())).thenReturn(Optional.of(spected));
        when(this.modelRepository.save(any())).thenReturn(spected);
        Model actual = this.modelService.update(1,spected);

        assertEquals(spected, actual);
        verify(this.modelRepository, times(1)).findById(any());
        verify(this.modelRepository, times(1)).save(any());

    }


    @Test
    public void testUpdateResponseStatus(){
        Model spected = TestUtils.getModel();
        when(this.modelRepository.findById(any())).thenReturn(Optional.empty());

        Assertions.assertThrows(ResponseStatusException.class, ()->{
            this.modelService.update(1, spected);
        });


    }


    @Test
    public void getAllOk(){
        List<Model> spected = TestUtils.getModels();
        when(this.modelRepository.findAll()).thenReturn(spected);

        List<Model> actual = this.modelService.getAll();

        assertEquals(actual, spected);
        verify(this.modelRepository,times(1)).findAll();
    }


    @Test
    public void getByIdOK(){
        Model spected = TestUtils.getModel();
        when(this.modelRepository.findById(any())).thenReturn(Optional.of(spected));
        Model actual = this.modelService.getById(1);

        assertEquals(spected, actual);
        verify(this.modelRepository, times(1)).findById(any());
    }



    @Test
    public void getByIdHttpClientError(){
        Model spected = TestUtils.getModel();
        when(this.modelRepository.findById(any())).thenThrow(HttpClientErrorException.class);


        Assertions.assertThrows(HttpClientErrorException.class, ()->{
            Model actual = this.modelService.getById(1);
        });
    }

    @Test
    public void deleteByIdOK(){
        Model spected = TestUtils.getModel();
        when(this.modelRepository.findById(any())).thenReturn(Optional.of(spected));
        this.modelService.deleteById(1);

        verify(this.modelRepository, times(1)).delete(any());

    }

    @Test
    public void deleteByIdHttpClientError(){

        when(this.modelRepository.findById(any())).thenThrow(HttpClientErrorException.class);

        Assertions.assertThrows(HttpClientErrorException.class, () -> {
            this.modelService.deleteById(1);
        });

    }







}