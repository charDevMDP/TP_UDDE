package com.tp.udde.controller;

import com.tp.udde.domain.User;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.tp.udde.service.UserService;

@Controller
public class UserController {

    @Autowired
    UserService userService;
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


    public User login(String surname, String password) {
        return userService.login(surname, password);
    }

}