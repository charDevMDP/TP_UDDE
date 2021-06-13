package com.tp.udde.controller.web;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.tp.udde.controller.UserController;
import com.tp.udde.domain.User;
import com.tp.udde.domain.dto.LoginRequestDto;
import com.tp.udde.domain.dto.LoginResponseDto;
import com.tp.udde.domain.dto.UserDto;
import com.tp.udde.exception.InvalidLoginException;
import com.tp.udde.exception.UserException;
import com.tp.udde.exception.ValidationException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.tp.udde.utils.Constants.JWT_SECRET;


@Slf4j
@RestController
@RequestMapping(value = "/auth")
public class LoginController {


   private final UserController userController ;
   private final ObjectMapper objectMapper;


    @Autowired
    public LoginController(UserController userController, ObjectMapper objectMapper) {
        this.userController = userController;
        this.objectMapper = objectMapper;
    }

    // lab.1 login
    @PostMapping(value = "login")
    public ResponseEntity login(@RequestBody LoginRequestDto loginRequestDto) throws UserException, ValidationException, InvalidLoginException {
        log.info(loginRequestDto.toString());
        UserDto dto = userController.login(loginRequestDto.getUsername(), loginRequestDto.getPassword());
        return ResponseEntity.ok(LoginResponseDto.builder().token(this.generateToken(dto)).build());
    }

    /*
    @GetMapping(value = "api/userDetails")
    public ResponseEntity<User> userDetails(@RequestBody Authentication token) {
        return ResponseEntity.ok((User) token.getPrincipal());
    }
    */


    public String generateToken(UserDto userDto) {
        try {
            List<GrantedAuthority> grantedAuthorities;
            if(userDto.getUserType() == "CLIENT")
            {
                grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList("CLIENT");
            }
            else {
                grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList("BACKOFFICE");
            }
            String token = Jwts
                    .builder()
                    .setId("JWT")
                    .setSubject(userDto.getUsername())
                    .claim("user", objectMapper.writeValueAsString(userDto))
                    .claim("authorities",grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + 1000000000))
                    .signWith(SignatureAlgorithm.HS512, JWT_SECRET.getBytes()).compact();
            return  token;
        } catch(Exception e) {
            return "dummy";
        }
    }

}
