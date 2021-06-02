package com.tp.udde.controller.web;


import com.tp.udde.controller.UserController;
import com.tp.udde.domain.User;
import com.tp.udde.domain.dto.LoginRequestDto;
import com.tp.udde.domain.dto.LoginResponseDto;
import com.tp.udde.domain.dto.UserDto;
import com.tp.udde.exception.InvalidLoginException;
import com.tp.udde.exception.UserException;
import com.tp.udde.exception.ValidationException;
import com.tp.udde.session.SessionManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping(value = "/")
public class LoginController {


   private final UserController userController ;
   private final SessionManager sessionManager;


    @Autowired
    public LoginController(UserController userController, SessionManager sessionManager) {
        this.userController = userController;
        this.sessionManager = sessionManager;
    }


    @PostMapping(value = "login")
    public ResponseEntity login(@RequestBody LoginRequestDto loginRequestDto) throws UserException, ValidationException, InvalidLoginException {
        log.info(loginRequestDto.toString());
        UserDto dto = userController.login(loginRequestDto.getSurname(), loginRequestDto.getPassword(),sessionManager);
        return ResponseEntity.ok(LoginResponseDto.builder().token(this.sessionManager.generateToken(dto)).build());
    }


    @PostMapping(value = "logout")
    public ResponseEntity logout(Authentication auth) {
        this.sessionManager.removeSession(auth);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "api/userDetails")
    public ResponseEntity<User> userDetails(@RequestBody Authentication token) {
        return ResponseEntity.ok((User) token.getPrincipal());
    }


    @GetMapping(value = "apis")
    public List<User> getUsers() {
        return this.userController.getUsers();
    }

}
