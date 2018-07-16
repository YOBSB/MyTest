package com.miniworld.common;

import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * 封装消息返回的json对象，所有返回结果都使用这个类
 * @param <T>
 */
@JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)
public class ReMessage<T> {
    private int code; //服务器操作结果， 0 代表成功，非0代表失败

    private String msg; //返回具体的错误信息

    private T data; //成功时返回的数据


    public ReMessage(){}

    public ReMessage(int code,String msg){
        this.code=code;
        this.msg=msg;
    }

    public ReMessage(int code,String msg,T data){
        this.code=code;
        this.msg=msg;
        this.data=data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Return Message：[code=" + code + ", data=" + data + ", msg=" + msg + "]";
    }
}
