package com.tp.udde.controller.utils;

import com.tp.udde.domain.*;
import com.tp.udde.domain.enums.UserType;
import com.tp.udde.projections.InvoiceOwedAddressClient;
import com.tp.udde.projections.UserMeasurementConsumption;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class TestUtils {

    public static List<User> getUsers(){
        return  Arrays.asList(
                User.builder().id(1)
                        .name("david")
                        .surname("Nava")
                        .dni("1234567")
                        .userType(UserType.CLIENT)
                        .email("hola@gmail.com")
                        .password("1234")
                .build(),
                User.builder().id(2)
                        .name("Carlos")
                        .surname("Nani")
                        .dni("123456988")
                        .userType(UserType.BACKOFFICE)
                        .email("hola@gmail.com")
                        .password("1234")
                        .build()
        );
    }

    public static User getUser(){ return getUsers().get(1); }

    public static User getUserId(Integer id){ return getUsers().get(id); }

    public static List<Rate> getRates(){
        return Arrays.asList(
                Rate.builder().id(1)
                        .type("qwe")
                        .price(1.25F)
                        .build(),
                Rate.builder().id(2)
                        .type("222")
                        .price(1.5F)
                        .build(),
                Rate.builder().id(3)
                        .type("202")
                        .price(5.4F)
                        .build()
        );
    }

    public static Rate getRate(){ return getRates().get(1); }

    public static List<City> getCity(){
        return Arrays.asList(
                City.builder().id(1)
                        .name("Mar del Plata")
                        .build(),
                City.builder().id(2)
                        .name("Córdoba")
                        .build());
    }

    public static City geCity(){
        return getCity().get(1);
    }

    public static City geCityId(Integer id){
        return getCity().get(id);
    }


    public static List<Address> getAddresses(){
        return Arrays.asList(
                Address.builder().id(1)
                        .name("Padre")
                        .department(1)
                        .number(1533)
                        .build(),
                Address.builder().id(2)
                        .name("Padre")
                        .department(2)
                        .number(5555)
                        .build()
        );
    }

    public static Address getAddress(){
        return getAddresses().get(1);
    }


    public static List<Meter> getMeters(){
        return Arrays.asList(
                Meter.builder().id(1)
                        .number(123)
                        .password("1234")
                        .build(),
                Meter.builder().id(2)
                        .number(1232)
                        .password("123")
                        .build()
        );
    }

    public static Meter getMeter(){
        return getMeters().get(1);
    }

    public static Model getModel(){
        return getModels().get(0);
    }

    public static List<Model> getModels(){
        return Arrays.asList(
                Model.builder().id(1)
                        .brand(getBrand())
                        .name("modelName")
                        .type("Abc")
                        .type("type")
                        .build(),
                Model.builder().id(2)
                        .brand(getBrand())
                        .name("modelName2")
                        .type("Abc2")
                        .type("type2")
                        .build()

        );
    }



    public static Brand getBrand() {
        return Brand.builder()
                .id(1)
                .name("brandName")
                .type("type1")
                .build();
    }


    public static InvoiceOwedAddressClient getInvoicesProjection(){
        return new InvoiceOwedAddressClient() {
            @Override
            public String getName() {
                return null;
            }

            @Override
            public String getSurname() {
                return null;
            }

            @Override
            public String getDni() {
                return null;
            }

            @Override
            public String getNameAddress() {
                return null;
            }

            @Override
            public int getNumberAddress() {
                return 0;
            }

            @Override
            public int getDepartment() {
                return 0;
            }

            @Override
            public float getConsumerKw() {
                return 0;
            }

            @Override
            public int getNumberMeter() {
                return 0;
            }

            @Override
            public float getTotalPrice() {
                return 0;
            }
        };
    }

    public static Measurement getMeasurement() {
        return Measurement.builder()
                .id(1)
                .kwh(100F)
                .build();
    }

    public static UserMeasurementConsumption getUserMeasurementConsumption() {
        return new UserMeasurementConsumption() {
            @Override
            public int getId() {
                return 0;
            }

            @Override
            public String getName() {
                return null;
            }

            @Override
            public String getSurname() {
                return null;
            }

            @Override
            public float getConsumption() {
                return 0;
            }

            @Override
            public String getNameAddress() {
                return null;
            }

            @Override
            public int getNumberAddress() {
                return 0;
            }

            @Override
            public int getDepartmentAddress() {
                return 0;
            }
        };

    }


}
