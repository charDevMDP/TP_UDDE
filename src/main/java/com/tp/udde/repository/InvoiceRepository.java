package com.tp.udde.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.tp.udde.domain.Invoice;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {

    @Query(value = "SELECT * FROM INVOICES WHERE dateInvoice BETWEEN ?2 AND ?3 AND id_user = ?1", nativeQuery = true)
    List<Invoice> findInvoiceBetweenDates(Integer userId, LocalDate startDate, LocalDate endDate);

    @Query(value = "SELECT * FROM INVOICES WHERE invoice_status = owed AND id_user = ?1", nativeQuery = true)
    List<Invoice> getInvoicesOwed(Integer userId);
}