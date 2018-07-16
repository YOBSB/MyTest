package com.miniworld.exception;

import com.miniworld.common.EventSysStateEnum;

public class EventSysException extends RuntimeException{

    public EventSysException(String msg){
        super(msg);
    }

    public EventSysException(String msg,Throwable cause){
        super(msg,cause);
    }
}
