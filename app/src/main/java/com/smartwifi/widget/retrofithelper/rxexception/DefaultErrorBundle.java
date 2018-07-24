package com.smartwifi.widget.retrofithelper.rxexception;

import android.text.TextUtils;

/**
 * Created by Administrator on 2018/7/17 0024.
 */
public class DefaultErrorBundle implements ErrorBundle {
    private static final String DEFAULT_ERROR_MSG = "Unknown error";
    private Exception e;

    public DefaultErrorBundle(Exception e) {
        this.e=e;
    }

    @Override
    public Exception getException() {
        return e;
    }

    @Override
    public String getMessage() {
        String message = e.getMessage();
        return message ==null|| TextUtils.isEmpty(message)?DEFAULT_ERROR_MSG:message;
    }
}
