package com.tp.udde.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.tp.udde.domain.Brand;
import com.tp.udde.repository.BrandRepository;

@Service
public class BrandService {

    @Autowired
    private BrandRepository brandRepository;

    public List<Brand> getAll() {
        List<Brand> brands = brandRepository.findAll();
        return brands;
    }

    public void deleteById(Integer id) {
        brandRepository.delete(getById(id));
    }

    public Brand add(Brand brand) {
        return brandRepository.save(brand);
    }

    public Brand getById(Integer id) {
        return brandRepository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }

    public Brand update(Integer id, Brand brand) {
        Optional<Brand> brandFind = brandRepository.findById(id);
        if (brandFind.isPresent()) {
            brand.setId(id);
            return brandRepository.save(brand);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}