package com.smartwifi.bean;

import com.smartwifi.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wificityios on 2018/8/1.
 */

public class ItemHomeHeaderBean {
    public String name;
    public int url;

    public ItemHomeHeaderBean(String name, int url) {
        this.name = name;
        this.url = url;

    }

    public static List<ItemHomeHeaderBean> getItemHomeHeaderBeanList() {
        List<ItemHomeHeaderBean> beans = new ArrayList<>();

        beans.add(new ItemHomeHeaderBean("全景高新", R.mipmap.art_collection));
        beans.add(new ItemHomeHeaderBean("高新头条", R.mipmap.art_collection));
        beans.add(new ItemHomeHeaderBean("麓谷新闻", R.mipmap.art_collection));
        beans.add(new ItemHomeHeaderBean("媒体聚焦",R.mipmap.art_collection));
        beans.add(new ItemHomeHeaderBean("通知公告", R.mipmap.art_collection));
        beans.add(new ItemHomeHeaderBean("部门动态", R.mipmap.art_collection));
//        beans.add(new ItemHomeHeaderBean("巡查监督", R.mipmap.ic_xuncha));
        beans.add(new ItemHomeHeaderBean("政府采购", R.mipmap.art_collection));
        //     beans.add(new ItemHomeHeaderBean("整改记录", R.mipmap.ic_zhenggai));
        beans.add(new ItemHomeHeaderBean("党政廉政", R.mipmap.art_collection));
        beans.add(new ItemHomeHeaderBean("法规公文", R.mipmap.art_collection));
        beans.add(new ItemHomeHeaderBean("征地拆迁", R.mipmap.art_collection));
        return beans;
    }

}
