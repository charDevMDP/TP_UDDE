package com.tp.udde.controller.web;

import com.tp.udde.controller.InvoiceController;
import com.tp.udde.controller.MeasurementController;
import com.tp.udde.controller.UserController;
import com.tp.udde.controller.web.ClientController;
import com.tp.udde.domain.*;
import com.tp.udde.domain.dto.MeterUserDto;
import com.tp.udde.domain.enums.InvoiceStatus;
import com.tp.udde.domain.enums.UserType;
import com.tp.udde.exception.ClientNotExists;
import com.tp.udde.projections.Consumption;
import com.tp.udde.projections.MeterUser;
import com.tp.udde.service.InvoiceService;
import com.tp.udde.service.MeasurementService;
import com.tp.udde.service.UserService;
import org.hibernate.type.LocalDateType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class ClientControllerTest {

    private User createNewUser(Integer id, String name, String username, String surname, String dni, String password, String email, UserType userType){
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setUsername(username);
        user.setSurname(surname);
        user.setDni(dni);
        user.setPassword(password);
        user.setEmail(email);
        user.setUserType(userType);
        return user;
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

    private MeterUser createMeterUser(){
        return new MeterUser() {
            @Override
            public String getName() {
                return "Carlos";
            }

            @Override
            public Integer getNumberMeter() {
                return 1;
            }
        };
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

    private Consumption createConsumptionNull(){
        return new Consumption() {
            @Override
            public Float getTotalKwh() {
                return null;
            }

            @Override
            public Float getPriceTotal() {
                return null;
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

    private Invoice createInvoice(){
        return Invoice.builder()
                .id(1)
                .meters(createMeter())
                .idUser(1)
                .dateInvoice(new Date())
                .dateInitial(new Date())
                .dateEnd(new Date())
                .consumerKw(1.25F)
                .number(1)
                .invoiceStatus(InvoiceStatus.OWED)
                .total(25.0F)
                .build();
    }

    UserController userController;
    UserService userService;

    MeasurementController measurementController;
    MeasurementService measurementService;
    InvoiceController invoiceController;
    InvoiceService invoiceService;

    ClientController clientController;

    @BeforeEach
    public void setUp(){
        userController = mock(UserController.class);
        userService = mock(UserService.class);

        invoiceController = mock(InvoiceController.class);
        invoiceService = mock(InvoiceService.class);

        measurementController = mock(MeasurementController.class);
        measurementService = mock(MeasurementService.class);

        clientController = new ClientController(userController,invoiceController,measurementController);
    }

    @Test
    public void getClientOKTest() throws ClientNotExists {
        //given
        User user = createUser();
        when(userService.getById(anyInt())).thenReturn(user);
        when(userController.getById(anyInt())).thenReturn(user);

        //when
        ResponseEntity<User> responseEntity = clientController.getById(1);

        //then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(user.getName(),responseEntity.getBody().getName());
    }

    @Test
    public void getMeterUserOKTest() {
        //given
        User user = createUser();
        MeterUser meterUser = createMeterUser();
        when(userService.meterOfUser(anyInt())).thenReturn(meterUser);
        when(userController.meterofuser(anyInt())).thenReturn(meterUser);

        //when
        ResponseEntity<MeterUser> responseEntity = clientController.meterUser(1);

        //then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(meterUser.getNumberMeter(),responseEntity.getBody().getNumberMeter());
    }

    @Test
    public void getMeterUserErrorTest(){
        when(userController.meterofuser(anyInt())).thenReturn(null);

        ResponseEntity<MeterUser> responseEntity = clientController.meterUser(1);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    // 2 test invoices por fechas
    @Test
    public void getInvoiceBetweenDatesOKTest() throws ClientNotExists, ParseException {
        //given
        LocalDate localDate = LocalDate.of(2020,05,9);
        Pageable pageable = PageRequest.of(1,10);
        Invoice invoice = createInvoice();
        Page<Invoice> PageInvoice = new PageImpl(List.of(invoice));
        User user = createUser();
        when(userController.getById(1)).thenReturn(user);
        when(invoiceService.getInvoiceBetweenDates(eq(pageable),anyInt(),any(LocalDate.class),any(LocalDate.class))).thenReturn(PageInvoice);
        when(invoiceController.getInvoiceBetweenDates(eq(pageable),anyInt(),any(LocalDate.class),any(LocalDate.class))).thenReturn(PageInvoice);

        //when
        ResponseEntity<List<Invoice>> response = clientController.getInvoiceBetweenDates(1,localDate,localDate,pageable);

        //then
        assertEquals(HttpStatus.OK.value(),response.getStatusCodeValue());
        assertEquals(1, Objects.requireNonNull(response.getBody()).size());
        assertEquals(PageInvoice.getContent(),response.getBody());
    }

    // no facturas
    @Test
    public void getInvoiceBetweenDatesNOTFOUNDTest() throws ClientNotExists, ParseException {
        //given
        LocalDate localDate = LocalDate.of(2020,05,9);
        Pageable pageable = PageRequest.of(1,10);
        User user = createUser();
        when(userController.getById(1)).thenReturn(user);
        when(invoiceService.getInvoiceBetweenDates(eq(pageable),anyInt(),any(LocalDate.class),any(LocalDate.class))).thenReturn(null);
        when(invoiceController.getInvoiceBetweenDates(eq(pageable),anyInt(),any(LocalDate.class),any(LocalDate.class))).thenReturn(null);

        //when
        ResponseEntity<List<Invoice>> response = clientController.getInvoiceBetweenDates(1,localDate,localDate,pageable);

        //then
        assertEquals(HttpStatus.NOT_FOUND.value(),response.getStatusCodeValue());

    }

    // no cliente
    @Test
    public void getInvoiceBetweenDatesNOTFOUNDCLIENTTest() throws ClientNotExists, ParseException {
        //given
        LocalDate localDate = LocalDate.of(2020,05,9);
        Pageable pageable = PageRequest.of(1,10);
        when(userController.getById(1)).thenReturn(null);

        //when
        ResponseEntity<List<Invoice>> response = clientController.getInvoiceBetweenDates(1,localDate,localDate,pageable);

        //then
        assertEquals(HttpStatus.NOT_FOUND.value(),response.getStatusCodeValue());
    }



    // 3 test invoices adeudadas
    @Test
    public void getInvoicesOwedOKTest() throws ClientNotExists, ParseException {
        //given
        Pageable pageable = PageRequest.of(1,10);
        Invoice invoice = createInvoice();
        Page<Invoice> PageInvoice = new PageImpl(List.of(invoice));
        User user = createUser();
        when(userController.getById(1)).thenReturn(user);
        when(invoiceService.getInvoicesOwed(eq(pageable),anyInt())).thenReturn(PageInvoice);
        when(invoiceController.getInvoicesOwed(eq(pageable),anyInt())).thenReturn(PageInvoice);

        //when
        ResponseEntity<List<Invoice>> response = clientController.getInvoicesOwed(1,pageable);

        //then
        assertEquals(HttpStatus.OK.value(),response.getStatusCodeValue());
        assertEquals(1, Objects.requireNonNull(response.getBody()).size());
        assertEquals(PageInvoice.getContent(),response.getBody());
    }

    // no facturas
    @Test
    public void getInvoicesOwedNOTFOUNDTest() throws ClientNotExists, ParseException {
        //given
        Pageable pageable = PageRequest.of(1,10);
        User user = createUser();
        when(userController.getById(1)).thenReturn(user);
        when(invoiceService.getInvoicesOwed(eq(pageable),anyInt())).thenReturn(null);
        when(invoiceController.getInvoicesOwed(eq(pageable),anyInt())).thenReturn(null);

        //when
        ResponseEntity<List<Invoice>> response = clientController.getInvoicesOwed(1,pageable);

        //then
        assertEquals(HttpStatus.NOT_FOUND.value(),response.getStatusCodeValue());

    }

    // no clientes
    @Test
    public void getInvoicesOwedNOTFOUNDCLIENTTest() throws ClientNotExists, ParseException {
        //given
        Pageable pageable = PageRequest.of(1,10);
        when(userController.getById(1)).thenReturn(null);

        //when
        ResponseEntity<List<Invoice>> response = clientController.getInvoicesOwed(1,pageable);

        //then
        assertEquals(HttpStatus.NOT_FOUND.value(),response.getStatusCodeValue());
    }



    // 4 test consumos por fechas
    @Test
    public void getConsumptionOKTest() throws ClientNotExists {
        //given
        User user = createUser();
        MeterUser meterUser = createMeterUser();
        Consumption consumption = createConsumption();
        LocalDate localDate = LocalDate.of(2020,05,9);
        when(userController.getById(anyInt())).thenReturn(user);
        when(userController.meterofuser(anyInt())).thenReturn(meterUser);
        when(measurementService.getConsumption(anyInt(),any(LocalDate.class),any(LocalDate.class))).thenReturn(consumption);
        when(measurementController.getConsumption(anyInt(),any(LocalDate.class),any(LocalDate.class))).thenReturn(consumption);

        //when
        ResponseEntity<Consumption> responseEntity = clientController.getConsumption(meterUser.getNumberMeter(),localDate,localDate);

        //then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(consumption.getTotalKwh(),responseEntity.getBody().getTotalKwh());
    }

    // sin consumos
    @Test
    public void getConsumptionNOTCONTENTTest() throws ClientNotExists {
        //given
        User user = createUser();
        MeterUser meterUser = createMeterUser();
        Consumption consumption = createConsumptionNull();
        LocalDate localDate = LocalDate.of(2020,05,9);
        when(userController.getById(anyInt())).thenReturn(user);
        when(userController.meterofuser(anyInt())).thenReturn(meterUser);
        when(measurementService.getConsumption(anyInt(),any(LocalDate.class),any(LocalDate.class))).thenReturn(consumption);
        when(measurementController.getConsumption(anyInt(),any(LocalDate.class),any(LocalDate.class))).thenReturn(consumption);

        //when
        ResponseEntity<Consumption> responseEntity = clientController.getConsumption(meterUser.getNumberMeter(),localDate,localDate);

        //then
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

    // no cliente
    @Test
    public void getConsumptionNOTFOUNDCLIENTTest() throws ClientNotExists {
        //given
        MeterUser meterUser = createMeterUser();
        LocalDate localDate = LocalDate.of(2020,05,9);
        when(userController.getById(anyInt())).thenReturn(null);

        //when
        ResponseEntity<Consumption> responseEntity = clientController.getConsumption(meterUser.getNumberMeter(),localDate,localDate);

        //then
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }


    // 5 mediciones por fechas
    @Test
    public void getMeasurmentsBetweenDatesOKTest() throws ClientNotExists {
        //given
        LocalDate localDate = LocalDate.of(2021,06,12);
        Pageable pageable = PageRequest.of(1,10);
        MeterUser meterUser = createMeterUser();
        Measurement measurement = createMeasurement();
        Page<Measurement> PageMeasurement = new PageImpl(List.of(measurement));
        User user = createUser();
        when(userController.getById(anyInt())).thenReturn(user);
        when(userController.meterofuser(anyInt())).thenReturn(meterUser);
        when(measurementService.getMeasurementByDates(eq(pageable),anyInt(),any(LocalDate.class),any(LocalDate.class))).thenReturn(PageMeasurement);
        when(measurementController.getMeasurementBetweenDates(eq(pageable),anyInt(),any(LocalDate.class),any(LocalDate.class))).thenReturn(PageMeasurement);

        //when
        ResponseEntity<List<Measurement>> response = clientController.getMeasurementBetweenDates(1,localDate,localDate,pageable);

        //then
        assertEquals(HttpStatus.OK.value(),response.getStatusCodeValue());
        assertEquals(1, Objects.requireNonNull(response.getBody()).size());
        assertEquals(PageMeasurement.getContent(),response.getBody());
    }


    // sin mediciones
    @Test
    public void getMeasurmentsBetweenDatesNOTFOUNDTest() throws ClientNotExists {
        //given
        LocalDate localDate = LocalDate.of(2021,06,12);
        Pageable pageable = PageRequest.of(1,10);
        MeterUser meterUser = createMeterUser();
        User user = createUser();
        when(userController.getById(anyInt())).thenReturn(user);
        when(userController.meterofuser(anyInt())).thenReturn(meterUser);
        when(measurementService.getMeasurementByDates(eq(pageable),anyInt(),any(LocalDate.class),any(LocalDate.class))).thenReturn(null);
        when(measurementController.getMeasurementBetweenDates(eq(pageable),anyInt(),any(LocalDate.class),any(LocalDate.class))).thenReturn(null);

        //when
        ResponseEntity<List<Measurement>> response = clientController.getMeasurementBetweenDates(1,localDate,localDate,pageable);

        //then
        assertEquals(HttpStatus.NOT_FOUND.value(),response.getStatusCodeValue());
    }

    @Test
    public void getMeasurmentsBetweenDatesNOTFOUNDTCLIENTest() throws ClientNotExists {
        //given
        LocalDate localDate = LocalDate.of(2021,06,12);
        Pageable pageable = PageRequest.of(1,10);
        when(userController.getById(anyInt())).thenReturn(null);

        //when
        ResponseEntity<List<Measurement>> response = clientController.getMeasurementBetweenDates(1,localDate,localDate,pageable);

        //then
        assertEquals(HttpStatus.NOT_FOUND.value(),response.getStatusCodeValue());
    }

}
