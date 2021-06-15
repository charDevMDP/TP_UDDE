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
import com.tp.udde.domain.Address;
import com.tp.udde.repository.AddressRepository;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public Page<Address> getAll(Pageable pageable) {
      return addressRepository.getAll(pageable);
    }

    public void deleteById(Integer id) {
        addressRepository.delete(getById(id));
    }

    public Address add(Address address) {
        return addressRepository.save(address);
    }

    public Address getById(Integer id) {
        return addressRepository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }

    public Address update(Integer id, Address address) {
        Optional<Address> addressFind = addressRepository.findById(id);
        if (addressFind.isPresent()) {
            address.setId(id);
            return addressRepository.save(address);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}