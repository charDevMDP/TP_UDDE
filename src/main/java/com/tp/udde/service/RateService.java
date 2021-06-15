package com.tp.udde.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import org.springframework.stereotype.Service;
import com.tp.udde.domain.Rate;
import com.tp.udde.repository.RateRepository;

@Service
public class RateService {


    private RateRepository rateRepository;

    @Autowired
    public RateService(RateRepository rateRepository) {
        this.rateRepository = rateRepository;
    }


    public Page<Rate> getAll(Pageable pageable) {
        return rateRepository.getAll(pageable);
    }

    public void deleteByIdRate(Integer id) {
        rateRepository.delete(getById(id));
    }

    public Rate add(Rate rate) { return rateRepository.save(rate);}

    public Rate getById(Integer id) {
        return rateRepository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }

    public Rate update(Integer id, Rate rate) {
        Optional<Rate> rateFind = rateRepository.findById(id);
        if (rateFind.isPresent()) {
            rate.setId(id);
            return rateRepository.save(rate);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}