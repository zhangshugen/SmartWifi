package com.smartwifi.part.user.model;

import com.smartwifi.bean.VerifyngCodeBean;
import com.smartwifi.manager.retrofit.RetrofitJsonManager;
import com.smartwifi.part.user.contract.VerfcationContract;
import com.smartwifi.rxjava.RxJavaHttpHelper;
import com.smartwifi.widget.retrofithelper.rxschedulers.RxSchedulersHelper;

import io.reactivex.Observable;


/**
 * Created by wificityios on 2018/7/18.
 */

public class VerfcationModel extends VerfcationContract.Model {
    @Override
    public Observable<VerifyngCodeBean> getVerifyingCodeData(String phone) {
        //调用api 获取数据
        return RetrofitJsonManager.getInstance().apiService.getVerifyingCode(phone)
                // 预处理
                .compose(RxJavaHttpHelper.handleResult())
                // 子线程和主线程切换
                .compose(RxSchedulersHelper.applyIoTransformer());
    }

    @Override
    public Observable<Object> getConfirmLoginData(String phone,String code) {
        //调用api 获取数据
        return RetrofitJsonManager.getInstance().apiService.getConfirmLogin(phone,code)
                // 预处理
                .compose(RxJavaHttpHelper.handleResult())
                // 子线程和主线程切换
                .compose(RxSchedulersHelper.applyIoTransformer());
    }
}
