package com.tp.udde.service;



import com.tp.udde.exception.UserException;
import com.tp.udde.domain.dto.MeterUserDto;
import com.tp.udde.projections.MeterUser;
import com.tp.udde.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.tp.udde.domain.User;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;


    public Page<User> getAll(Integer size, Integer page) {
        Pageable pageable = PageRequest.of(size,page);
        return userRepository.findAll(pageable);
    }

    public User login(String username, String password) throws UserException {
        User user = userRepository.getByUsername(username, password);;
        return Optional.ofNullable(user).orElseThrow(() -> new UserException("User not exists"));
    }

    public void deleteById(Integer id) {
        userRepository.delete(getById(id));
    }

    public User add(User user) {
        return userRepository.save(user);
    }

    public User getById(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }

    public User update(Integer id, User user) {
        Optional<User> userFind = userRepository.findById(id);
        if (userFind.isPresent()) {
            user.setId(id);
            return userRepository.save(user);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    // meter of user
    public MeterUser meterOfUser(Integer idUser){
        return  userRepository.getMeterUser(idUser);
    }
}