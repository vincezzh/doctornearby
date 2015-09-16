package com.akhaltech.rest;

public class UnknownResourceException extends RuntimeException {

    public UnknownResourceException(String msg) {
        super(msg);
    }
}
