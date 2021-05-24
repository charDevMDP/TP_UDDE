package com.tp.udde.domain;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.tp.udde.domain.enums.InvoiceStatus;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name="id_meters",foreignKey = @ForeignKey(name = "id_meters_invoices"))
    private Meters meters;

    @Column(name = "id_user", nullable = false)
    private Integer idUser;

    @Column(name = "date_invoice", nullable = false)
    private Date dateInvoice;

    @Column(name = "date_initial", nullable = false)
    private Date dateInitial;

    @Column(name = "date_end", nullable = false)
    private Date dateEnd;

    @Column(name = "consumer_kw", nullable = false)
    private float consumerKw;

    @Column(name = "number", nullable = false)
    private int number;

    @Column(name = "invoice_status", columnDefinition = "varchar(20) default 'OWED'")
    private InvoiceStatus invoiceStatus;

    @Column(name = "total", nullable = false)
    private float total;

}