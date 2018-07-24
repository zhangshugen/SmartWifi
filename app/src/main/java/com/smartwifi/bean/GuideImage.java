package com.smartwifi.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import com.smartwifi.BR;
public class GuideImage extends BaseObservable {
    /*  {"orgId":"8a8ab0b246dc81120146dc8180ba0017","orgName":"默认机构",
              "orgImg":"http://wifi.hktfi.com/upload/plug-in/accordion/hktorgimg/20180615170002vdfQ9EsL.jpg","id":"8a2bf9ef6232cf37016232d34ddd000c"}*/
    public String orgId;

    public String orgName;

    public void setOrgImg(String orgImg) {
        this.orgImg = orgImg;
        notifyPropertyChanged(BR.orgImg);
    }

    @Bindable
    public String orgImg;
    public String id;


}
