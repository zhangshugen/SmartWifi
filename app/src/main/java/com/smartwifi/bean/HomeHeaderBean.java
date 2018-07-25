package com.smartwifi.bean;

/**
 * Created by wificityios on 2018/7/25.
 */

public class HomeHeaderBean {

    public String name;

    public HomeHeaderBean(String name) {
        this.name = name;

    }

    public static HomeHeaderBean getHomeHeaderBeanList() {

        return new HomeHeaderBean("头部");
    }

}
