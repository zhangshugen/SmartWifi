package com.smartwifi.bean;

import android.databinding.BaseObservable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/29
 * @Describe
 */

public class DirectListBean extends BaseObservable implements Serializable{
    public static List<DirectListBean> getDirectListBean() {
        List<DirectListBean> listBeans = new ArrayList<>();
        listBeans.add(new DirectListBean());
        listBeans.add(new DirectListBean());
        listBeans.add(new DirectListBean());
        listBeans.add(new DirectListBean());
        listBeans.add(new DirectListBean());
        listBeans.add(new DirectListBean());
        listBeans.add(new DirectListBean());
        return listBeans;
    }
}
