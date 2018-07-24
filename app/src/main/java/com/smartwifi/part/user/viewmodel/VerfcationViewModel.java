package com.smartwifi.part.user.viewmodel;

import com.smartwifi.bean.VerifyngCodeBean;
import com.smartwifi.databinding.ActivityVerfcationBinding;
import com.smartwifi.part.user.activity.VerfcationActivity;
import com.smartwifi.part.user.contract.VerfcationContract;
import com.smartwifi.part.user.model.VerfcationModel;
import com.smartwifi.utils.IntentController;
import com.smartwifi.utils.ToastUtils;
import com.smartwifi.widget.retrofithelper.rxsubscriber.ProgressObserver;

/**
 * Created by wificityios on 2018/7/18.
 */

public class VerfcationViewModel extends VerfcationContract.ViewModel<VerfcationContract.View,ActivityVerfcationBinding,VerfcationModel> {
    @Override
    public void getVerifyingCodeData(String phone) {
        mModel.getVerifyingCodeData(phone).subscribe(new ProgressObserver<VerifyngCodeBean>(false,this) {
            @Override
            public void _onNext(VerifyngCodeBean verifyngCodeBean) {
            }

        });
    }

    @Override
    public void confirmLogin(String phone) {
        String code = mBinding.editText.getInputContent();
        mModel.getConfirmLoginData(phone,code).subscribe(new ProgressObserver<Object>(false,this) {
            @Override
            public void _onNext(Object o) {
                mView.loginSuccess();
                ToastUtils.show("登录成功",1);
            }

            @Override
            public void _onError(String errorMessage, int errorCode) {
                super._onError(errorMessage, errorCode);
                ToastUtils.showShort(errorMessage);
            }
        });


    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
