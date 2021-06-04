package com.tp.udde.controller;

import com.tp.udde.domain.User;

import com.tp.udde.domain.dto.UserDto;
import com.tp.udde.exception.InvalidLoginException;
import com.tp.udde.exception.UserException;
import com.tp.udde.exception.ValidationException;
import com.tp.udde.projections.MeterUser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.tp.udde.service.UserService;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;


@Controller
public class UserController {

    private final ModelMapper modelMapper;
    private final UserService userService;

    @Autowired
    public UserController(ModelMapper modelMapper, UserService userService) {
        this.modelMapper = modelMapper;
        this.userService = userService;
    }
    /*
    // agrego uno
    @PostMapping
    public User addUser(@RequestBody User user) {
        return userService.add(user);
    }

    // traigo todos
    @GetMapping
    public List<User> getAll() {
        return userService.getAll();
    }

    // traigo uno
    @GetMapping("/{id}")
    public User getById(@PathVariable Integer id) {
        return userService.getById(id);
    }

    // actualizo
    @PutMapping("/{id}")
    public User replaceUser(@PathVariable Integer id, @RequestBody User user) {
        return userService.update(id, user);
    }

    // elimino uno
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id) {
        userService.deleteById(id);
    }*/


    public UserDto login(String username, String password) throws UserException, ValidationException, InvalidLoginException {
        if ((username != null) && (password != null)) {
            User user = userService.login(username, password);

                return  modelMapper.map(user, (Type) UserDto.class);
        } else {
            return (UserDto) Optional.ofNullable(null).orElseThrow(() -> new ValidationException("Username and password must have a value"));
        }
    }

    public List<User> getUsers()
    {
        return userService.getAll();
    }

    public MeterUser meterofuser(Integer idUser) {
        return userService.meterOfUser(idUser);
    }
}