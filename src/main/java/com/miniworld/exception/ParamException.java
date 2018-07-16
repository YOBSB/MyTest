package com.miniworld.exception;

public class ParamException extends RuntimeException{
    public ParamException(String msg){
        super(msg);
    }

    public ParamException(String msg,Throwable cause){
        super(msg,cause);
    }
}
