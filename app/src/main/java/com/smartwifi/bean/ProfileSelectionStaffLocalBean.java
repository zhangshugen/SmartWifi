package com.smartwifi.bean;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/16
 * @Describe
 */

public class ProfileSelectionStaffLocalBean implements Serializable, Comparable<ProfileSelectionStaffLocalBean> {

    public String companyName;
    public String sort;
    public String userName;
    public int position;
    public String job;
    public String phone;
    public String chooseLevel;

    public List<ProfileSelectionStaffLocalBean> dataList = new ArrayList<>();
    // 上一级名称
    public String superiorUnit = "";
    //区分是哪个View
    public String viewCategoryTag;

    @Override
    public int compareTo(@NonNull ProfileSelectionStaffLocalBean o) {
        return sort.compareTo(o.sort);
    }

    @Override
    public boolean equals(Object obj) {
        ProfileSelectionStaffLocalBean bean = (ProfileSelectionStaffLocalBean) obj;
        return companyName.equals(bean.companyName);
    }

    @Override
    public int hashCode() {
        return companyName.hashCode();
    }
}
