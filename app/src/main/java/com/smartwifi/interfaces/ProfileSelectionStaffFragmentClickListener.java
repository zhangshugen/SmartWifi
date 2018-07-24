package com.smartwifi.interfaces;

import com.smartwifi.bean.ProfileSelectionStaffLocalBean;

import java.util.List;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/16
 * @Describe
 */

public interface ProfileSelectionStaffFragmentClickListener {
    void  fragmentClickListener(List<ProfileSelectionStaffLocalBean> list, ProfileSelectionStaffLocalBean itemData);
    boolean isAddFragment(int tag);
}
