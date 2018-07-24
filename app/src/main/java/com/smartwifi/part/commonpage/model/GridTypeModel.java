package com.smartwifi.part.commonpage.model;

import com.smartwifi.bean.CommonGridBean;
import com.smartwifi.part.commonpage.contract.GridTypeContract;

import io.reactivex.Observable;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/11
 * @Describe
 */

public class GridTypeModel extends GridTypeContract.Model {
    @Override
    public Observable<CommonGridBean> getGridBean() {
        return null;
    }
}
