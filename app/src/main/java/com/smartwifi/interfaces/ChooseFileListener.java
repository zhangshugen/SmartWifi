package com.smartwifi.interfaces;

import com.smartwifi.bean.ProfileFileBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/11
 * @Describe
 */

public abstract class ChooseFileListener {
    public void onFile(ProfileFileBean profileFileBean,String tag) {
        List<ProfileFileBean> beans = new ArrayList<>();
        beans.add(profileFileBean);
        onFile(profileFileBean,tag);
    }

    public abstract void onFile(List<ProfileFileBean> profileFileBean,String tag);


    public abstract void onOpenFile(String viewTag);
}
