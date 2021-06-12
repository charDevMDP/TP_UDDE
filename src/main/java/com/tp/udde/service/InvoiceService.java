package com.tp.udde.service;

import com.tp.udde.projections.InvoiceOwedAddressClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.tp.udde.domain.Invoice;
import com.tp.udde.repository.InvoiceRepository;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    public List<Invoice> getAll() {
        List<Invoice> invoices = invoiceRepository.findAll();
        return invoices;
    }

    public void deleteById(Integer id) {
        invoiceRepository.delete(getById(id));
    }

    public Invoice add(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    public Invoice getById(Integer id) {
        return invoiceRepository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }

    public Invoice update(Integer id, Invoice invoice) {
        Optional<Invoice> invoiceFind = invoiceRepository.findById(id);
        if (invoiceFind.isPresent()) {
            invoice.setId(id);
            return invoiceRepository.save(invoice);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    // lab.2
    public Page<Invoice> getInvoiceBetweenDates(Pageable pageable,Integer userId, LocalDate startDate, LocalDate endDate){
        return invoiceRepository.findInvoiceBetweenDates(pageable,userId, startDate,endDate);
    }

    // lab.3
    public Page<Invoice> getInvoicesOwed(Pageable pageable,Integer userId){

        return invoiceRepository.getInvoicesOwed(pageable,userId);
    }

    // lab.4
    public Page<InvoiceOwedAddressClient> getInvoicesOwedClient(Pageable pageable,Integer userId){
        return invoiceRepository.getInvoicesOwedClient(pageable,userId);
    }

}