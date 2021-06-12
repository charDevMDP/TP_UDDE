package com.tp.udde.repository;

import com.tp.udde.projections.InvoiceOwedAddressClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.tp.udde.domain.Invoice;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {


    // lab.2
    @Query(value = "SELECT * FROM INVOICES WHERE date_invoice BETWEEN ?2 AND ?3 AND id_user = ?1", nativeQuery = true)
    Page<Invoice> findInvoiceBetweenDates(Pageable pageable, Integer user, LocalDate startDate, LocalDate endDate);

    // lab.3
    @Query(value = "SELECT * FROM INVOICES WHERE invoice_status = 'OWED' AND id_user = ?1", nativeQuery = true)
    Page<Invoice> getInvoicesOwed(Pageable pageable, Integer userId);

    // lab.4
    @Query(value = "SELECT us.name AS Name, us.surname AS Surname, us.dni AS Dni, ad.name AS NameAddress,"+
            "ad.number AS NumberAddress, ad.department AS Department, inv.consumer_kw AS consumerKW, inv.number AS NumberMeter, "+
            "inv.total AS TotalPrice FROM invoices inv INNER JOIN meters me ON me.id = inv.id_meters INNER JOIN addresses ad ON "+
            "ad.id = me.id_address INNER JOIN users us ON us.id = ad.id_user WHERE inv.invoice_status = 'OWED' AND us.id = ?1", nativeQuery = true)
    Page<InvoiceOwedAddressClient> getInvoicesOwedClient(Pageable pageable, Integer userId);
}