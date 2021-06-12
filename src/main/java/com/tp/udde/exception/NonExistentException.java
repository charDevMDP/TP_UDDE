package com.tp.udde.exception;

public class NonExistentException extends Throwable{

    public NonExistentException() {
        super("The selected parameter does not exist");
    }
}
