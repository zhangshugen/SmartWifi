package com.smartwifi.part.user.activity;


import android.Manifest;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;

import com.smartwifi.R;
import com.smartwifi.databinding.ActivityLoginBinding;
import com.smartwifi.part.user.contract.LoginContract;
import com.smartwifi.part.user.viewmodel.LoginViewModel;
import com.smartwifi.utils.IntentController;
import com.smartwifi.utils.OtherEntryUtils;
import com.smartwifi.utils.ToastUtils;
import com.smartwifi.widget.mvvm.factory.CreateViewModel;
import com.smartwifi.widget.mvvm.view.BaseMVVMActivity;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * Created by wificityios on 2018/7/12
 */

@CreateViewModel(LoginViewModel.class)
public class LoginActivity extends BaseMVVMActivity<LoginViewModel,ActivityLoginBinding> implements LoginContract.View{

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        mBinding.setPresenter(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

    }


    public void onButtonClick(){
       /* OtherEntryUtils.showShare(
                getApplicationContext(),
                "跟我一起WIFI免费上网",
                "智慧无线，极速免费上网，APP免费下载，动心了有没有？快来下载使用吧",
                "http://www.hktfi.com/UI/Api/default/Login_index$3001.html",
                "http://7sby7r.com1.z0.glb.clouddn.com/CYSJ_02.jpg");//需要后面选一张网络图片*/

        mViewModel.postAuthIpToServer();
        String editText = mBinding.edit.getText().toString();
        String telRegex = "[1]\\d{10}";
        if (TextUtils.isEmpty(editText)|| !editText.matches(telRegex)) {
            ToastUtils.show("请填写正确的手机号码",1);
            return;
        }else{
            mViewModel.getVerifyingCodeData(editText);
            IntentController.toVerfcationActivity(LoginActivity.this,editText);
        }
    }

    @Override
    public int getStateBarColor() {
        return R.color.black;
    }


    public void onIvWXClick(){
        OtherEntryUtils.loginWithWeChat(mViewModel);

    }

    public void onIvQQClick(){
        OtherEntryUtils.loginWithQQ(mViewModel);
    }


    @Override
    public void thirdLoginSuccess() {
        IntentController.toHomeActivity(LoginActivity.this);
    }
}



