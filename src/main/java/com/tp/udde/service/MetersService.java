package com.tp.udde.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.tp.udde.domain.Meters;
import com.tp.udde.repository.MetersRepository;

@Service
public class MetersService {

    @Autowired
    private MetersRepository metersRepository;

    public List<Meters> getAll() {
        List<Meters> meters = metersRepository.findAll();
        return meters;
    }

    public void deleteById(Integer id) {
        metersRepository.delete(getById(id));
    }

    public Meters add(Meters meters) {
        return metersRepository.save(meters);
    }

    public Meters getById(Integer id) {
        return metersRepository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }

    public Meters update(Integer id, Meters meters) {
        Optional<Meters> measurerFind = metersRepository.findById(id);
        if (measurerFind.isPresent()) {
            meters.setId(id);
            return metersRepository.save(meters);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}