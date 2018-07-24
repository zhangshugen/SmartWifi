package com.smartwifi.bean;

import android.databinding.BaseObservable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/10
 * @Describe
 */

public class ProcessStartBean extends BaseObservable implements Serializable {
    public String title;
    public String name;
    public String id;
    public int type;
    public String desc;
    public boolean isLast;

    public ProcessStartBean(String name, String desc) {
        this.name = name;
        this.desc = desc;
        this.title = name;
        this.isLast = false;
    }
    public ProcessStartBean(String name, String desc,boolean isLast) {
        this.name = name;
        this.desc = desc;
        this.title = name;
        this.isLast = isLast;
    }

    public ProcessStartBean() {

    }

    public int projectPosition;
    public int intentPosition;
    public List<String> nextIntentConditionList;
    public String nextIntentConditionString;


    public static List<ProcessStartBean> getProcessStartBeanList(int intentPosition) {

        List<ProcessStartBean> processStartBeanList = new ArrayList<>();
        if (intentPosition == 0) {
            processStartBeanList.add(new ProcessStartBean("龙琅高速", ""));
            processStartBeanList.add(new ProcessStartBean("长益高速", ""));
            processStartBeanList.add(new ProcessStartBean("永吉高速", ""));
        } else if (intentPosition == 1) {
            processStartBeanList.add(new ProcessStartBean("第一监理处", ""));
            processStartBeanList.add(new ProcessStartBean("第二监理处", ""));
            processStartBeanList.add(new ProcessStartBean("第三监理处", ""));
        } else if (intentPosition == 2) {
            processStartBeanList.add(new ProcessStartBean("路基土石方工程", "K29+820-K31+000"));
            processStartBeanList.add(new ProcessStartBean("路基土石方工程", "AK0+000-AK1+537.381"));
            processStartBeanList.add(new ProcessStartBean("路基土石方工程", "BK0+000-BKO+392.798"));
            processStartBeanList.add(new ProcessStartBean("路基土石方工程", "AK0+000-AK1+537.381"));
            processStartBeanList.add(new ProcessStartBean("路基土石方工程", "BK0+000-BKO+392.798"));
            processStartBeanList.add(new ProcessStartBean("路基土石方工程", "BK0+000-BKO+392.798"));
        } else if (intentPosition == 3) {
            processStartBeanList.add(new ProcessStartBean("防护工程", "",true));
            processStartBeanList.add(new ProcessStartBean("路基土石方工程", "",true));
            processStartBeanList.add(new ProcessStartBean("排水工程", "",true));
            processStartBeanList.add(new ProcessStartBean("通函工程", "",true));
            processStartBeanList.add(new ProcessStartBean("路基土石方工程", "",true));
            processStartBeanList.add(new ProcessStartBean("排水工程", "",true));
        }
        return processStartBeanList;
    }
}
