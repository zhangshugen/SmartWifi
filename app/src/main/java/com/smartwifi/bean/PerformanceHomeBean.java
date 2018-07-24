package com.smartwifi.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/9
 * @Describe
 */

public class PerformanceHomeBean {
    public PerformanceHomeBean(int type, String name, int unReadCount) {
        this.type = type;
        this.name = name;
        this.unReadCount = unReadCount;
    }  public PerformanceHomeBean(int type, String name, int unReadCount,String url) {
        this.type = type;
        this.name = name;
        this.unReadCount = unReadCount;
        this.url = url;
    }

    public int type;
    public String name;
    public String url;
    public int unReadCount;

    public static List<PerformanceHomeBean> getPerformanceHomeBeanList() {
        List<PerformanceHomeBean> list = new ArrayList<>();
        list.add(new PerformanceHomeBean(0,"我的任务",0));
        list.add(new PerformanceHomeBean(1,"代办事项",2));
        list.add(new PerformanceHomeBean(1,"已办事项",2));
        list.add(new PerformanceHomeBean(0,"报警信息",0));
        list.add(new PerformanceHomeBean(1,"项目人员违约情况",2,"http://218.76.40.74:8075/WebReport/ReportServer?reportlet=/xmrywy.cpt"));
        list.add(new PerformanceHomeBean(1,"特殊岗位违约情况",2,"http://218.76.40.74:8075/WebReport/ReportServer?reportlet=/tswy.cpt"));
        list.add(new PerformanceHomeBean(1,"履约预扣分",2,"http://218.76.40.74:8075/WebReport/ReportServer?reportlet=/ly/xmryykf.cpt"));
        list.add(new PerformanceHomeBean(1,"人员变动详情",2,"http://218.76.40.74:8075/WebReport/ReportServer?reportlet=/ly/ly1.cpt"));
        list.add(new PerformanceHomeBean(1,"人员变动统计",2,"http://218.76.40.74:8075/WebReport/ReportServer?reportlet=/ly/ly2.cpt"));
        list.add(new PerformanceHomeBean(1,"1到4月总预扣分",2,"http://218.76.40.74:8075/WebReport/ReportServer?reportlet=/ly/zkf.cpt"));
        return list;
    }
}
