package com.tp.udde.controller.web;

import com.tp.udde.controller.UserController;
import com.tp.udde.domain.User;
import com.tp.udde.projections.MeterUser;
import com.tp.udde.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping(value = "/")
public class ClientController {

    private final UserController userController ;
    private final UserService userService;

    @Autowired
    public ClientController(UserController userController, UserService userService) {
        this.userService = userService;
        this.userController = userController;
    }

    @GetMapping("/meterUser")
    public ResponseEntity<MeterUser> meterUser(@RequestParam Integer idUser) {
        MeterUser meterUser = userController.meterofuser(idUser);
        if(meterUser!=null){
            return ResponseEntity.ok(meterUser);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PreAuthorize(value = "hasRole('CLIENT')")
    @GetMapping(value = "/client/apis")
    public List<User> getUsers() {
        return this.userController.getUsers();
    }

}
