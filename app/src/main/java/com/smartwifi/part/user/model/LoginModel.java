package com.smartwifi.part.user.model;

import com.smartwifi.bean.VerifyngCodeBean;
import com.smartwifi.manager.retrofit.RetrofitJsonManager;
import com.smartwifi.manager.retrofit.RetrofitOtherUrlManager;
import com.smartwifi.part.user.contract.LoginContract;
import com.smartwifi.rxjava.RxJavaHttpHelper;
import com.smartwifi.widget.retrofithelper.rxschedulers.RxSchedulersHelper;

import io.reactivex.Observable;

/**
 * Created by wificityios on 2018/7/12.
 */

public class LoginModel extends LoginContract.Model {

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
    public Observable<Object>  postAuthIpToServer(String ip) {
        RetrofitOtherUrlManager manager = new RetrofitOtherUrlManager("http://"+ip+":2060/");
       return manager.apiService.authWifi("wifidog/auth?token=123&mod=1&authway=app&ot=0").compose(RxSchedulersHelper.applyIoTransformer()) ;
    }

    @Override
    public Observable<Object> getThirdLoginData(String type, String openid, String nickname) {
        return RetrofitJsonManager.getInstance().apiService.getThirdLogin(type,openid,nickname)
                // 预处理
                .compose(RxJavaHttpHelper.handleResult())
                // 子线程和主线程切换
                .compose(RxSchedulersHelper.applyIoTransformer());
    }


}
