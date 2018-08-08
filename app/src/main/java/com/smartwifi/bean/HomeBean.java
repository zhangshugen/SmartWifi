package com.smartwifi.bean;

import com.smartwifi.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/30
 * @Describe
 */

public class HomeBean {

    public String name;
    public String title;
    public int url;

    public HomeBean(String name, int url,String title) {
        this.name = name;
        this.url = url;
        this.title=title;

    }

    public static List<HomeBean> getHomeBeanList() {
        List<HomeBean> beans = new ArrayList<>();

        beans.add(new HomeBean("长沙高新区官网", R.mipmap.test,"中方保持中立？若美国反击朝鲜导弹打击是标题是标题是标题是标题…中方保持中立？若美国反击朝鲜导弹打击是标题是标题是标题是标题…"));
        beans.add(new HomeBean("长沙高新区官网", R.mipmap.test,"若美国反击朝鲜导弹打击是标题是标题是标题是标题"));
        beans.add(new HomeBean("长沙高新区官网", R.mipmap.test,"中方保持中立"));
        beans.add(new HomeBean("长沙高新区官网", R.mipmap.test,"美国反击朝鲜导弹打击是"));
        beans.add(new HomeBean("长沙高新区官网", R.mipmap.test,"若美国反击朝鲜导弹打击是标题是标题是"));
        beans.add(new HomeBean("长沙高新区官网", R.mipmap.test,"标题"));
        return beans;
    }

}
