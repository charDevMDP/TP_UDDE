package com.tp.udde.controller.web;

import com.tp.udde.controller.UserController;
import com.tp.udde.domain.dto.MeterUserDto;
import com.tp.udde.projections.MeterUser;
import com.tp.udde.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;


@Slf4j
@RestController
@RequestMapping(value = "/client")
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

}
