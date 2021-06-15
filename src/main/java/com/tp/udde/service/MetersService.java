package com.tp.udde.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.tp.udde.domain.Meter;
import com.tp.udde.repository.MeterRepository;

@Service
public class MetersService {

    @Autowired
    private MeterRepository meterRepository;

    public MetersService(MeterRepository meterRepository) {
        this.meterRepository = meterRepository;
    }

    public Page<Meter> getAll(Pageable pageable) {
        return meterRepository.getMeters(pageable);
    }

    public void deleteById(Integer id) {
        meterRepository.delete(getById(id));
    }

    public Meter add(Meter meters) {
        return meterRepository.save(meters);
    }

    public Meter getById(Integer id) {
        return meterRepository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }

    public Meter getByMeterNumberAndPass(String  number, String password) {
        return this.meterRepository.getByMeterNumberAndPass(number,password);
    }


    public Meter update(Integer id, Meter meters) {
        Optional<Meter> measurerFind = meterRepository.findById(id);
        if (measurerFind.isPresent()) {
            meters.setId(id);
            return meterRepository.save(meters);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }


}