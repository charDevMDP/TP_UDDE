package com.tp.udde.controller.web;

import com.tp.udde.controller.*;
import com.tp.udde.controller.utils.TestUtils;
import com.tp.udde.domain.*;
import com.tp.udde.exception.ClientNotExists;
import com.tp.udde.exception.NonExistentException;
import com.tp.udde.projections.InvoiceOwedAddressClient;
import com.tp.udde.projections.UserMeasurementConsumption;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class BackOfficeControllerTest {

    UserController userController ;
    RateController rateController;
    AddressController addressController;
    MeterController meterController;
    InvoiceController invoiceController;
    MeasurementController measurementController;
    BackOfficeController backOfficeController;


    @BeforeEach
    public void setUp(){
        userController = mock(UserController.class);
        rateController = mock(RateController.class);
        addressController = mock(AddressController.class);
        meterController = mock(MeterController.class);
        invoiceController = mock(InvoiceController.class);
        measurementController = mock(MeasurementController.class);
        backOfficeController = new BackOfficeController(userController,rateController,addressController,meterController,invoiceController,measurementController);
    }


    @Test
    public void getUsersOkTest() throws ClientNotExists {
        //given
        User user = new User();
        Pageable pageable = PageRequest.of(0,10);
        Page<User> PageUser = new PageImpl(List.of(user));
        when(userController.getUsers(pageable)).thenReturn(PageUser);
        //when
        ResponseEntity<List<User>> response = backOfficeController.getUsers(pageable);
        //then
        assertEquals(HttpStatus.OK.value(),response.getStatusCodeValue());
        assertEquals(1, Objects.requireNonNull(response.getBody()).size());
        assertEquals(PageUser.getContent(),response.getBody());
    }

    @Test
    public void getRateOkTest(){
        //given
        Rate rate = TestUtils.getRate();
        Pageable pageable = PageRequest.of(0,10);
        Page<Rate> PageUser = new PageImpl(List.of(rate));
        when(rateController.getRates(pageable)).thenReturn(PageUser);
        //when
        ResponseEntity<List<Rate>> response = backOfficeController.getRates(pageable);
        //then
        assertEquals(HttpStatus.OK.value(),response.getStatusCodeValue());
        assertEquals(1, Objects.requireNonNull(response.getBody()).size());
        assertEquals(PageUser.getContent(),response.getBody());
    }

    @Test
    public void posRateOkTest(){
        Rate rate = TestUtils.getRate();
        Rate rate1 = new Rate();
        when(rateController.addRate(rate)).thenReturn(rate1);
        Rate response = backOfficeController.addRate(rate);

    }

    @Test
    public void deleteReteOkTest() throws NonExistentException {
        Rate rate = TestUtils.getRate();
        ResponseEntity<String> response = backOfficeController.deleteById(rate.getId());
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
    }

    @Test
    public void putReteOkTest() {
        Rate rate = TestUtils.getRate();
        when(rateController.replaceRate(any(),eq(rate))).thenReturn(rate);
        Rate rate1 = backOfficeController.replaceRate(1,rate);
        assertEquals(rate,rate1);
    }


    @Test
    public void getAddressOkTest() {
        //given
        Address address = TestUtils.getAddress();
        Pageable pageable = PageRequest.of(0,10);
        Page<Address> PageAddress = new PageImpl(List.of(address));
        when(addressController.getAll(pageable)).thenReturn(PageAddress);
        //when
        ResponseEntity<List<Address>> response = backOfficeController.getAllAddress(pageable);
        //then
        assertEquals(HttpStatus.OK.value(),response.getStatusCodeValue());
        assertEquals(1, Objects.requireNonNull(response.getBody()).size());
        assertEquals(PageAddress.getContent(),response.getBody());
    }

    @Test
    public void getAddressIdOkTest() {
        //given
        Address address = TestUtils.getAddress();
        Address address1 = new Address();
        when(addressController.getById(address.getId())).thenReturn(address1);
        //when
        ResponseEntity<Address> response = backOfficeController.getById(1);
        //then
        assertEquals(HttpStatus.OK.value(),response.getStatusCodeValue());
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void postAddressTest(){
        Address address = TestUtils.getAddress();
        Address address1 = new Address();
        when(addressController.addAddress(address)).thenReturn(address1);
        Address response = backOfficeController.addAddress(address);

    }

    @Test
    public void deleteAddressTest() throws NonExistentException{
        Address address = TestUtils.getAddress();
        ResponseEntity<String> response = backOfficeController.deleteByIdAddress(address.getId());
        assertEquals(HttpStatus.ACCEPTED,response.getStatusCode());
    }

    @Test
    public void putAddressOkTest(){
        Address address = TestUtils.getAddress();
        when(addressController.updateAddressById(1,address)).thenReturn(address);
        Address address1 = backOfficeController.replaceAddress(1,address);
        assertEquals(address1,address);
    }

    @Test
    public void getMeterOkTest() {
        //given
        Meter meter = TestUtils.getMeter();
        Pageable pageable = PageRequest.of(0,10);
        Page<Meter> PageMeter = new PageImpl(List.of(meter));
        when(meterController.getAllMeter(pageable)).thenReturn(PageMeter);
        //when
        ResponseEntity<List<Meter>> response = backOfficeController.getAllMeter(pageable);
        //then
        assertEquals(HttpStatus.OK.value(),response.getStatusCodeValue());
        assertEquals(1, Objects.requireNonNull(response.getBody()).size());
        assertEquals(PageMeter.getContent(),response.getBody());
    }

    @Test
    public void getMeterIdOkTest() {
        Meter meter = new Meter();
        when(meterController.getByIdMeter(1)).thenReturn(meter);
        Meter response = backOfficeController.getByIdMeter(1);
        assertEquals(meter,response);
    }

    @Test
    public void postMeterOkTest(){
        Meter meter = TestUtils.getMeter();
        when(meterController.addMeter(meter)).thenReturn(meter);
        Meter response = backOfficeController.addMeter(meter);
        assertEquals(meter,response);
    }

    @Test
    public void putMeterOkTest(){
        Meter meter = TestUtils.getMeter();
        when(meterController.replaceMeter(any(),eq(meter))).thenReturn(meter);
        Meter response = backOfficeController.replaceMeter(1,meter);
        assertEquals(meter,response);
    }

    @Test
    public void deleteMeterTest(){
        Meter meter = TestUtils.getMeter();
        backOfficeController.deleteByIdMeter(meter.getId());
    }

    @Test
    public void posMeterOkTest(){
        Meter meter = TestUtils.getMeter();
        Meter meter1 = new Meter();
        when(meterController.addMeter(meter)).thenReturn(meter1);
        Meter response = backOfficeController.addMeter(meter1);
    }


    @Test
    public void getTestInvoicesOwed(){
        //given
        InvoiceOwedAddressClient invoiceOwedAddressClient = TestUtils.getInvoicesProjection();
        Pageable pageable = PageRequest.of(0,10);
        Page<InvoiceOwedAddressClient> pageInvoiceOwed = new PageImpl(List.of(invoiceOwedAddressClient));
        when(invoiceController.getInvoicesOwedClient(pageable,1)).thenReturn(pageInvoiceOwed);
        //when
        ResponseEntity<List<InvoiceOwedAddressClient>> response = backOfficeController.getInvoicesOwed(1,pageable);
        //then
        assertEquals(HttpStatus.OK.value(),response.getStatusCodeValue());
        assertEquals(1, Objects.requireNonNull(response.getBody()).size());
    }

    @Test
    public void getTestMeasurementForDateForAddress(){
        LocalDate localDate = LocalDate.of(2021,06,15);
        LocalDate localDateEnd = LocalDate.of(2021,06,01);
        Pageable pageable = PageRequest.of(0,10);
        Measurement measurement = TestUtils.getMeasurement();
        Page<Measurement> pageMeasurement = new PageImpl(List.of(measurement));
        when(measurementController.getMeasurementForDateForAddress(pageable,1,localDate,localDateEnd)).thenReturn(pageMeasurement);
        ResponseEntity<List<Measurement>> response = backOfficeController.getMeasurementForDateForAddress(1,localDate,localDateEnd,pageable);
        assertEquals(HttpStatus.OK.value(),response.getStatusCodeValue());
        assertEquals(1, Objects.requireNonNull(response.getBody()).size());
    }


    @Test
    public void getTestUserForDateForConsumption(){
        LocalDate localDate = LocalDate.of(2021,06,15);
        LocalDate localDateEnd = LocalDate.of(2021,06,01);
        Pageable pageable = PageRequest.of(0,10);
        UserMeasurementConsumption userMeasurement = TestUtils.getUserMeasurementConsumption();
        Page<UserMeasurementConsumption> userMeasurementConsumptions = new PageImpl(List.of(userMeasurement));
        when(measurementController.getUserForDateForConsumption(pageable,localDate,localDateEnd)).thenReturn(userMeasurementConsumptions);
        ResponseEntity<List<UserMeasurementConsumption>> response = backOfficeController.getUserForDateForConsumption(localDate,localDateEnd,pageable);
        assertEquals(HttpStatus.OK.value(),response.getStatusCodeValue());
        assertEquals(1, Objects.requireNonNull(response.getBody()).size());
    }


}
