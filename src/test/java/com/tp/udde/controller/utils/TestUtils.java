package com.tp.udde.controller.utils;

import com.tp.udde.domain.Address;
import com.tp.udde.domain.City;
import com.tp.udde.domain.Rate;
import com.tp.udde.domain.User;
import com.tp.udde.domain.enums.UserType;

import java.util.Arrays;
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
                        .city(geCityId(1))
                        .name("Padre")
                        .department(1)
                        .user(getUserId(1))
                        .number(1533)
                        .build(),
                Address.builder().id(2)
                        .city(geCityId(2))
                        .name("Padre")
                        .department(2)
                        .user(getUserId(2))
                        .number(5555)
                        .build()
        );
    }

    public static Address getAddress(){
        return getAddresses().get(1);
    }



}
