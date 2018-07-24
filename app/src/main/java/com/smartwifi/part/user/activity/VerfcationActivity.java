package com.smartwifi.part.user.activity;

import android.os.CountDownTimer;
import android.text.TextUtils;

import com.smartwifi.R;
import com.smartwifi.databinding.ActivityVerfcationBinding;
import com.smartwifi.part.user.contract.VerfcationContract;
import com.smartwifi.part.user.viewmodel.VerfcationViewModel;
import com.smartwifi.utils.CountUtils;
import com.smartwifi.utils.IntentController;
import com.smartwifi.utils.ToastUtils;
import com.smartwifi.widget.mvvm.factory.CreateViewModel;
import com.smartwifi.widget.mvvm.view.AppActivityManager;
import com.smartwifi.widget.mvvm.view.BaseMVVMActivity;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.tencent.qq.QQ;

/**
 * Created by wificityios on 2018/7/18.
 * 验证码接口
 * http://wifi.hktfi.com/ws/user/login.do?phone=17373197603&verifyCode=5129&loginType=sj
 * 上网认证
 * http://192.168.99.254:2060/wifidog/auth?token=123&mod=1&authway=app&ot=0
 */

@CreateViewModel(VerfcationViewModel.class)
public class VerfcationActivity extends BaseMVVMActivity<VerfcationViewModel, ActivityVerfcationBinding> implements VerfcationContract.View {

    private String phone;

    @Override
    public int getLayoutId() {
        return R.layout.activity_verfcation;
    }

    @Override
    public void initView() {
        mBinding.setPresenter(this);
        phone = getIntent().getStringExtra("phone");
        mBinding.tvPhone.setText(phone);
        CountUtils.getCount(mBinding.tvCount);

    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    public int getStateBarColor() {
        return R.color.black;
    }

    public void onTvClick() {
        mViewModel.getVerifyingCodeData(phone);
        CountUtils.getCount(mBinding.tvCount);
    }

    public void onButtonClick() {
        if (TextUtils.isEmpty(phone))
            return;
        mViewModel.confirmLogin(phone);
    }

    @Override
    public void loginSuccess() {
        IntentController.toHomeActivity(VerfcationActivity.this);
        AppActivityManager.getAppActivityManager().finishActivity(LoginActivity.class);
    }
}


