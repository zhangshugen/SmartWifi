package com.smartwifi.bean;

/**
 * Created by Mloong on 2018/8/3.
 */

public class BannerInfo {
/*    |title   |String    |轮播图title   |
            |imageUrl  |string | 图片的url                      |
            |desc    |string | 图片的描述                      |
            |linkUrl   |String    |跳转地址(可以本地跳转,http跳转)   |
            |isIntent   |string | 是否需要跳转                      |*/

    public String title;
    public String imgUrl;
    public String desc;
    public String linkUrl;
    public String isIntent;


    public BannerInfo(String imgUrl) {
        this.imgUrl=imgUrl;

    }


}
