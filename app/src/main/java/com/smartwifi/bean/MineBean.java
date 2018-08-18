package com.smartwifi.bean;

import com.smartwifi.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wificityios on 2018/8/9.
 */

public class MineBean {
    public String name;
    public int url;
    //未读计数
    public int unReadCount;

    public MineBean(String name,int url,int unRead){
        this.name=name;
        this.url=url;
        this.unReadCount=unRead;
    }

    public static List<MineBean> getMineBeanList(){
        List<MineBean> beans=new ArrayList<>();

        beans.add(new MineBean("更换号码", R.mipmap.ic_neikong,0));
        beans.add(new MineBean("绑定账号", R.mipmap.ic_neikong,0));
        beans.add(new MineBean("推荐好友", R.mipmap.ic_neikong,0));
        beans.add(new MineBean("检查更新", R.mipmap.ic_neikong,0));
        beans.add(new MineBean("服务协议", R.mipmap.ic_neikong,0));
        beans.add(new MineBean("关于我们", R.mipmap.ic_neikong,0));
        beans.add(new MineBean("退出登录", R.mipmap.ic_neikong,0));

        return beans;
    }
}
