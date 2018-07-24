package com.smartwifi.part.commonpage.model;

import com.smartwifi.bean.EditProfileBean;
import com.smartwifi.manager.retrofit.RetrofitJsonManager;
import com.smartwifi.part.commonpage.contract.EditProfileContract;
import com.smartwifi.rxjava.RxJavaHttpHelper;
import com.smartwifi.widget.retrofithelper.rxschedulers.RxSchedulersHelper;

import io.reactivex.Observable;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/9
 * @Describe
 */

public class EditProfileModel extends EditProfileContract.Model {
    @Override
    public Observable<EditProfileBean> getProfileParamsData() {
        return RetrofitJsonManager.getInstance().getApiService().getEditProfileParams("LLGSJK/backconfig.ashx?STYPE=GETMB&Identity=%E5%B9%B4%E5%BA%A6%E4%BB%BB%E5%8A%A1%E5%88%9B%E5%BB%BA")
                .compose(RxSchedulersHelper.applyIoTransformer()).compose(RxJavaHttpHelper.<EditProfileBean>handleResult());
    }
}
