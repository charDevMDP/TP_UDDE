package com.tp.udde.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.tp.udde.domain.City;
import com.tp.udde.repository.CityRepository;

@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;

    public List<City> getAll() {
        List<City> citys = cityRepository.findAll();
        return citys;
    }

    public void deleteById(Integer id) {
        cityRepository.delete(getById(id));
    }

    public City add(City city) {
        return cityRepository.save(city);
    }

    public City getById(Integer id) {
        return cityRepository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }

    public City update(Integer id, City city) {
        Optional<City> cityFind = cityRepository.findById(id);
        if (cityFind.isPresent()) {
            city.setId(id);
            return cityRepository.save(city);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}