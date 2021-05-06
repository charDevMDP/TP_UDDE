package com.tp.udde.domain;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity(name = "invoices")
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Data
@Builder
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name="id_address")
    private Address address;

    @Column(name = "date_invoice")
    private Date dateInvoice;

    @Column(name = "date_initial")
    private Date dateInitial;

    @Column(name = "date_end")
    private Date dateEnd;

    @Column(name = "consumer_kw")
    private float consumerKw;

    @Column(name = "number")
    private int number;

    @Column(name = "total")
    private float total;

}