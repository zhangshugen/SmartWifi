package com.smartwifi.part.user.contract;

import android.databinding.ViewDataBinding;

import com.smartwifi.bean.VerifyngCodeBean;
import com.smartwifi.widget.mvvm.model.BaseModel;
import com.smartwifi.widget.mvvm.view.BaseMVVMView;
import com.smartwifi.widget.mvvm.viewmodel.BaseViewModel;

import io.reactivex.Observable;

/**
 * Created by wificityios on 2018/7/12.
 */

public class LoginContract {
    public abstract static class Model extends BaseModel {

        public abstract Observable<VerifyngCodeBean> getVerifyingCodeData(String phone);

        public abstract Observable<Object> postAuthIpToServer(String ip);

        public abstract Observable<Object> getThirdLoginData(String type,String openid,String nickname);
    }

    public interface View extends BaseMVVMView {
        public abstract void thirdLoginSuccess();
    }

    public abstract static class ViewModel<V extends BaseMVVMView, D extends ViewDataBinding, M extends BaseModel> extends BaseViewModel<V, D, M> {
        public abstract void getVerifyingCodeData(String phone);

        public abstract void postAuthIpToServer();

        public abstract void getThirdLoginData(String type,String openid,String nickname);
    }
}
