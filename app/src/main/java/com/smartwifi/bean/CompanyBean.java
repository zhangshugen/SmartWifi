package com.smartwifi.bean;


import com.smartwifi.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wificityios on 2018/7/10.
 */

public class CompanyBean {

    //名字
    public String name;
    //未读计数
    public int unReadCount;
    public int url;

    public CompanyBean(String name,int url,int unRead){
        this.name=name;
        this.unReadCount=unRead;
        this.url=url;
    }

    public static List<CompanyBean> getCompanyBeanList(){
        List<CompanyBean> beans= new ArrayList<>();

        beans.add(new CompanyBean("企业广场", R.mipmap.ic_neikong,0));
        beans.add(new CompanyBean("内控任务", R.mipmap.ic_neikong, 0));
        beans.add(new CompanyBean("发起监督", R.mipmap.ic_faqi, 0));
//       beans.add(new CompanyBean("巡查监督", R.mipmap.ic_xuncha,1));
        beans.add(new CompanyBean("质安监督", R.mipmap.ic_zhian, 3));
        //    beans.add(new CompanyBean("整改记录", R.mipmap.ic_zhenggai, 1));
        beans.add(new CompanyBean("负面清单", R.mipmap.ic_fumian, 2));
        beans.add(new CompanyBean("履约监督", R.mipmap.ic_lvyue, 5));

        return beans;
    }
}
