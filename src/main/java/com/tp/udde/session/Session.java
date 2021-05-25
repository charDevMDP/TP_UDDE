package com.tp.udde.session;

import com.tp.udde.domain.User;
import org.springframework.security.core.Authentication;

import java.util.Date;

public class Session {

    private String token ;
    private User loggedUser;
    private Date lastAction;

    public Session(String token , User loggedUser, Date lastAction) {
        this.token  = token ;
        this.loggedUser = loggedUser;
        this.lastAction = lastAction;
    }

    public String getToken() {
        return token ;
    }

    public void setToken(String token) {
        this.token  = token ;
    }

    public User getLoggedUser() { return loggedUser; }

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }

    public Date getLastAction() {
        return lastAction;
    }

    public void setLastAction(Date lastAction) {
        this.lastAction = lastAction;
    }
}
