package com.ivangeorgiev.domuapi.core.exceptions;

public class InternalServerErrorException extends RuntimeException{
    public InternalServerErrorException(String message){
        super(message);
    }
}
