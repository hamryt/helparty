package com.dlab.helparty.application;

public class PasswordWrongException extends RuntimeException {
    PasswordWrongException(){
        super("Password is wrong");
    }
}
