package com.tp.udde.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.tp.udde.domain.Invoice;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {

    // lab.2
    @Query(value = "SELECT * FROM INVOICES WHERE date_invoice BETWEEN ?2 AND ?3 AND id_user = ?1", nativeQuery = true)
    List<Invoice> findInvoiceBetweenDates(Integer user, LocalDate startDate, LocalDate endDate);

    // lab.3
    @Query(value = "SELECT * FROM INVOICES WHERE invoice_status = 'OWED' AND id_user = ?1", nativeQuery = true)
    List<Invoice> getInvoicesOwed(Integer userId);
}