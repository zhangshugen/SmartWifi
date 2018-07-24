package com.smartwifi.widget.retrofithelper.rxexception;

/**
 * Created by Administrator on 2018/7/9.
 */
public class ServerException extends Exception {

    private String message;
    private   int code;

    public ServerException(int code, String message) {
        super(message);
        this.code=code;
        this.message=message;
    }


    public int getCode() {
        return code;
    }

}
