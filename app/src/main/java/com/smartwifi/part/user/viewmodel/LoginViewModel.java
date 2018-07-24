package com.smartwifi.part.user.viewmodel;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.smartwifi.bean.VerifyngCodeBean;
import com.smartwifi.databinding.ActivityLoginBinding;
import com.smartwifi.part.user.contract.LoginContract;
import com.smartwifi.part.user.model.LoginModel;
import com.smartwifi.utils.CommonUtils;
import com.smartwifi.utils.ToastUtils;
import com.smartwifi.utils.UIUtils;
import com.smartwifi.widget.retrofithelper.rxsubscriber.ProgressObserver;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by wificityios on 2018/7/12
 */

public class LoginViewModel extends LoginContract.ViewModel<LoginContract.View,ActivityLoginBinding,LoginModel> {
    @Override
    public void getVerifyingCodeData(String phone) {
        mModel.getVerifyingCodeData(phone).subscribe(new ProgressObserver<VerifyngCodeBean>(false,this) {
            @Override
            public void _onNext(VerifyngCodeBean verifyngCodeBean) {
            }

        });
    }

    @Override
    public void getThirdLoginData(String type,String openid,String nickname) {
        mModel.getThirdLoginData(type,openid,nickname).subscribe(new ProgressObserver<Object>(false,this) {
            @Override
            public void _onNext(Object o) {

                ToastUtils.show("登录成功",1);
                mView.thirdLoginSuccess();
            }
        });

    }


    @Override
    public void postAuthIpToServer() {
        WifiManager mWifi = (WifiManager) UIUtils.getContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (mWifi.isWifiEnabled()) {
            WifiInfo wifiInfo = mWifi.getConnectionInfo();
            String netName = wifiInfo.getSSID(); //获取被连接网络的名称
            String netMac = wifiInfo.getBSSID(); //获取被连接网络的mac地址
            int ipAdd = wifiInfo.getIpAddress(); //获取32位整型IP地址
            //把整型地址转换成“*.*.*.*”地址
            String ip = CommonUtils.intToIp(ipAdd);//192.168.3.58
            Log.v("data", netName + "======---netMac:" + netMac + "---ip:" + ip);
            mModel.postAuthIpToServer(ip).subscribe(new Observer<Object>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(Object o) {

                }

                @Override
                public void onError(Throwable e) {
                
                }

                @Override
                public void onComplete() {

                }
            });
        }

    }
}
