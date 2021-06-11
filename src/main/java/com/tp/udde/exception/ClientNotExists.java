package com.tp.udde.exception;

public class ClientNotExists extends Throwable {

    public ClientNotExists() {
        super("El cliente no existe");
    }
}
