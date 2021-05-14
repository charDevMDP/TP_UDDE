package com.tp.udde.domain;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity(name = "measurements")
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Data
@Builder
public class Measurement {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true)
    private Integer id;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "id_invoice")
    private int idInvoice;

    @Column(name = "kwh", nullable = false)
    private float kwh;

}