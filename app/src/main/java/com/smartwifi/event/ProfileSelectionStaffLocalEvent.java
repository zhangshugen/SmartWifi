package com.smartwifi.event;

import com.smartwifi.bean.ProfileSelectionStaffLocalBean;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/17
 * @Describe
 */

public class ProfileSelectionStaffLocalEvent {
    public ProfileSelectionStaffLocalBean mProfileSelectionStaffLocalBean;
    public String mViewTag;
    public String showName;

    public ProfileSelectionStaffLocalEvent(ProfileSelectionStaffLocalBean itemData, String mViewTag,String showName) {
        this.mViewTag = mViewTag;
        this.mProfileSelectionStaffLocalBean = itemData;
        this.showName = showName;
    }
}
