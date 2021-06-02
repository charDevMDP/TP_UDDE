package com.tp.udde.service;



import com.tp.udde.exception.UserException;
import com.tp.udde.domain.dto.MeterUserDto;
import com.tp.udde.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.tp.udde.domain.User;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;


    public List<User> getAll() {
        List<User> users = (List<User>) userRepository.findAll();
        return users;
    }

    public User login(String surname, String password) throws UserException {
        User user = userRepository.getByUsername(surname, password);;
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
    public MeterUserDto meterOfUser(Integer idUser){
        return  userRepository.getMeterUser(idUser);
    }
}