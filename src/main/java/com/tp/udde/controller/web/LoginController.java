package com.tp.udde.controller.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tp.udde.controller.UserController;
import com.tp.udde.domain.User;
import com.tp.udde.domain.dto.LoginRequestDto;
import com.tp.udde.domain.dto.LoginResponseDto;
import com.tp.udde.domain.dto.UserDto;
import com.tp.udde.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import static com.tp.udde.utils.Constants.JWT_SECRET;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "/")
public class LoginController {

   private final ObjectMapper objectMapper;
   private final ModelMapper modelMapper;
   private final UserController userController ;


    @Autowired
    public LoginController(ObjectMapper objectMapper, ModelMapper modelMapper, UserController userController) {
        this.objectMapper = objectMapper;
        this.modelMapper = modelMapper;
        this.userController = userController;
    }

    @PostMapping(value = "login")
    public ResponseEntity<LoginResponseDto>login(@RequestBody LoginRequestDto loginRequestDto) {
        log.info(loginRequestDto.toString());
        User user = userController.login(loginRequestDto.getSurname(), loginRequestDto.getPassword());
        if (user!=null){
            UserDto dto = modelMapper.map(user, UserDto.class);
            return ResponseEntity.ok(LoginResponseDto.builder().token(this.generateToken(dto)).build());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping(value = "/userDetails")
    public ResponseEntity<User> userDetails(Authentication auth) {
        return ResponseEntity.ok((User) auth.getPrincipal());
    }

    private String generateToken(UserDto userDto) {
        try {
            List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList("CLIENT");
            String token = Jwts
                    .builder()
                    .setId("JWT")
                    .setSubject(userDto.getSurname())
                    .claim("user", objectMapper.writeValueAsString(userDto))
                    .claim("authorities",grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + 1000))
                    .signWith(SignatureAlgorithm.HS512, JWT_SECRET.getBytes()).compact();
            return  token;
        } catch(Exception e) {
            return "dummy";
        }
    }
}
