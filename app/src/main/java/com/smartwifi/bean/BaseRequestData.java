package com.smartwifi.bean;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/9
 * @Describe
 */

public class BaseRequestData<T> {
    public boolean success;
    public T obj;
    public String msg;
}
