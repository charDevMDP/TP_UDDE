package com.tp.udde.projections;

import com.tp.udde.domain.City;
public interface InvoiceOwedAddressClient {

    String getName();
    String getSurname();
    String getDni();
    String getNameAddress();
    int getNumberAddress();
    int getDepartment();
    float getConsumerKw();
    int getNumberMeter();
    float getTotalPrice();
}
