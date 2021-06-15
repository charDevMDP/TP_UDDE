package com.tp.udde.services;

import com.tp.udde.controller.InvoiceController;
import com.tp.udde.domain.*;
import com.tp.udde.domain.enums.InvoiceStatus;
import com.tp.udde.domain.enums.UserType;
import com.tp.udde.projections.InvoiceOwedAddressClient;
import com.tp.udde.repository.InvoiceRepository;
import com.tp.udde.service.InvoiceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class InvoiceServiceTest {


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

    private InvoiceOwedAddressClient createInvoiceOwedAddressClient(){
        return new InvoiceOwedAddressClient() {
            @Override
            public String getName() {
                return "char";
            }

            @Override
            public String getSurname() {
                return "nuñez";
            }

            @Override
            public String getDni() {
                return "123123123";
            }

            @Override
            public String getNameAddress() {
                return "rau";
            }

            @Override
            public int getNumberAddress() {
                return 123;
            }

            @Override
            public int getDepartment() {
                return 1;
            }

            @Override
            public float getConsumerKw() {
                return 5.50F;
            }

            @Override
            public int getNumberMeter() {
                return 1;
            }

            @Override
            public float getTotalPrice() {
                return 100.00F;
            }
        };
    }

    InvoiceService invoiceService;
    InvoiceRepository invoiceRepository;

    @BeforeEach
    public void setUp(){
        invoiceRepository = mock(InvoiceRepository.class);
        invoiceService = new InvoiceService(invoiceRepository);
    }

    @Test
    public void getInvoiceBetweenDateOKTest(){

        Invoice invoice = createInvoice();
        Meter meter = createMeter();
        Pageable pageable = PageRequest.of(1,10);
        LocalDate localDate = LocalDate.of(2020,05,9);
        Page<Invoice> PageInvoice = new PageImpl(List.of(invoice));

        when(invoiceRepository.findInvoiceBetweenDates(eq(pageable),anyInt(),any(LocalDate.class),any(LocalDate.class))).thenReturn(PageInvoice);

        Page<Invoice> response = invoiceService.getInvoiceBetweenDates(pageable,meter.getId(),localDate,localDate);

        assertEquals(response,PageInvoice);
    }

    @Test
    public void getInvoicesOwedOKTest(){
        Invoice invoice = createInvoice();
        Pageable pageable = PageRequest.of(1,10);
        Page<Invoice> PageInvoice = new PageImpl(List.of(invoice));

        when(invoiceRepository.getInvoicesOwed(eq(pageable),anyInt())).thenReturn(PageInvoice);

        Page<Invoice> response = invoiceService.getInvoicesOwed(pageable,1);

        assertEquals(response,PageInvoice);
    }

    @Test
    public void getInvoicesOwedClient(){
        InvoiceOwedAddressClient invoiceOwedAddressClient = createInvoiceOwedAddressClient();
        Pageable pageable = PageRequest.of(1,10);
        Page<InvoiceOwedAddressClient> PageInvoice = new PageImpl(List.of(invoiceOwedAddressClient));

        when(invoiceRepository.getInvoicesOwedClient(eq(pageable),anyInt())).thenReturn(PageInvoice);

        Page<InvoiceOwedAddressClient> response = invoiceService.getInvoicesOwedClient(pageable,1);

        assertEquals(response,PageInvoice);

    }

}
