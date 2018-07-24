package com.smartwifi.part.commonpage.contract;

import android.databinding.ViewDataBinding;

import com.smartwifi.bean.EditProfileBean;
import com.smartwifi.interfaces.BasePageManageView;
import com.smartwifi.part.commonpage.activity.EditProfileActivity;
import com.smartwifi.widget.mvvm.model.BaseModel;
import com.smartwifi.widget.mvvm.view.BaseMVVMView;
import com.smartwifi.widget.mvvm.viewmodel.BaseViewModel;

import rx.Observable;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/9
 * @Describe
 */

public class EditProfileContract {
    public abstract static class Model extends BaseModel {


        public abstract io.reactivex.Observable<EditProfileBean> getProfileParamsData();
    }

    public interface View<E> extends BasePageManageView<E> {


    }

    public abstract static class ViewModel<V extends BaseMVVMView, D extends ViewDataBinding, M extends BaseModel> extends BaseViewModel<V, D, M> {

        public abstract void getProfileParamsData(EditProfileActivity editProfileActivity);
    }

}
