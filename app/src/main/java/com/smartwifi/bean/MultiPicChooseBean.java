package com.smartwifi.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.net.Uri;

import com.smartwifi.BR;
import com.smartwifi.R;
import com.smartwifi.utils.UriUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2018/7/11 0011.
 */

public class MultiPicChooseBean extends BaseObservable {


    @Bindable
    public Uri imgUrl;
    @Bindable
    public String imgUrlString;

    @Bindable
    public int type; //0  添加图片的按钮   1  图片

    @Bindable
    public String title;

    @Bindable
    public boolean visiable;
    @Bindable
    public boolean isNotShowDelete;

    public void setType(int type) {
        this.type = type;
        notifyPropertyChanged(BR.type);
    }

    public void setImgUrl(Uri imgUrl) {
        this.imgUrl = imgUrl;
        notifyPropertyChanged(BR.imgUrl);
    }

    public void setTitle(String _title) {
        title = _title;
        notifyPropertyChanged(BR.title);
    }

    public void setVisiable(boolean _visiable) {
        visiable = _visiable;
        notifyPropertyChanged(BR.visiable);
    }

    public MultiPicChooseBean(Uri _imgUrl, int _type, String _title) {
        imgUrl = _imgUrl;
        type = _type;
        title = _title;
    }

    public MultiPicChooseBean(String _imgUrl, int _type, String _title) {
        imgUrlString = _imgUrl;
        type = _type;
        title = _title;
        isNotShowDelete = true;
    }

    public MultiPicChooseBean() {
    }

    public static List<MultiPicChooseBean> getAddImageBean() {
        List<MultiPicChooseBean> multiPicChooseBeans = new ArrayList<>();
        MultiPicChooseBean choosePojo = new MultiPicChooseBean(UriUtils.getUriFromResource(R.mipmap.ic_photo_camera_black_24dp), 0, "添加图片");
        choosePojo.setVisiable(true);
        multiPicChooseBeans.add(choosePojo);
        return multiPicChooseBeans;
    }


}
