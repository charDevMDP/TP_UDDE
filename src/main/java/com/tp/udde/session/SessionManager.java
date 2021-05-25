package com.tp.udde.session;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tp.udde.domain.User;
import com.tp.udde.domain.dto.UserDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

import static com.tp.udde.utils.Constants.JWT_SECRET;


@Component
public class SessionManager {

    private final ObjectMapper objectMapper;
    private final ModelMapper modelMapper;
    Map<String, Session> sessionMap;
    int sessionExpiration = 600000;


    public SessionManager(ObjectMapper objectMapper, ModelMapper modelMapper) {
        this.objectMapper = objectMapper;
        this.modelMapper = modelMapper;
        sessionMap = new Hashtable<>();
    }

    public String createSession(User user) {
        UserDto dto = modelMapper.map(user, UserDto.class);
        String token  = generateToken(dto);
        this.sessionMap.put(token , new Session(token , user, new Date(System.currentTimeMillis())));
        return token;
    }

    public String createSession(User user, String token) {
        sessionMap.put(token, new Session(token, user, new Date(System.currentTimeMillis())));
        return token;
    }

    public Session getSession(String token) {
        if (token == null) {
            return null;
        }
        Session session = sessionMap.get(token);
        if (session != null) {
            session.setLastAction(new Date(System.currentTimeMillis()));
        }
        return session;
    }

    public boolean userIsLogged(User user){
        for (Session session : sessionMap.values()){
            if(session.getLoggedUser().getId() == user.getId())
            {
                return true;
            }
        }
        return false;
    }

    public void removeSession(Authentication auth) {
        sessionMap.remove(auth);
    }

    public String generateToken(UserDto userDto) {
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

    public User getCurrentUser(String token) {
        return getSession(token).getLoggedUser();
    }
}

