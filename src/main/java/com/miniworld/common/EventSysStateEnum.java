package com.miniworld.common;


public enum EventSysStateEnum {
    /**
     * 服务级错误
     */
    SUCCESS(EventSysStateConst.SUCCESS,"操作成功"),
    FAIL(EventSysStateConst.FAIL,"操作失败"),
    SYSTEM_EXCEPTION(EventSysStateConst.SYSTEM_EXCEPTION, "系统错误"),
    ;

    private String code;

    private String message;

    EventSysStateEnum(String code, String message){
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
