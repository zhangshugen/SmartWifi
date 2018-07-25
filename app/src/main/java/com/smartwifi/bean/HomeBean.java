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
    public int unReadCount;
    public int url;

    public HomeBean(String name, int url, int unRead) {
        this.name = name;
        this.url = url;
        this.unReadCount = unRead;
    }

    public static List<HomeBean> getHomeBeanList() {
        List<HomeBean> beans = new ArrayList<>();

        beans.add(new HomeBean("测试",R.mipmap.ic_neikong,0));
        beans.add(new HomeBean("内控任务", R.mipmap.ic_neikong, 0));
        beans.add(new HomeBean("发起监督", R.mipmap.ic_faqi, 0));
//        beans.add(new HomeBean("巡查监督", R.mipmap.ic_xuncha,1));
        beans.add(new HomeBean("质安监督", R.mipmap.ic_zhian, 3));
        //     beans.add(new HomeBean("整改记录", R.mipmap.ic_zhenggai, 1));
        beans.add(new HomeBean("负面清单", R.mipmap.ic_fumian, 2));
        beans.add(new HomeBean("履约监督", R.mipmap.ic_lvyue, 5));
        return beans;
    }

}
