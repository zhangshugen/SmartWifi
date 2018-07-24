package com.smartwifi.part.splash.contract;

import android.databinding.ViewDataBinding;

import com.smartwifi.bean.GuideImage;
import com.smartwifi.widget.mvvm.model.BaseModel;
import com.smartwifi.widget.mvvm.view.BaseMVVMView;
import com.smartwifi.widget.mvvm.viewmodel.BaseViewModel;

import io.reactivex.Observable;

public class GuideContract {
    public abstract static class Model extends BaseModel {

        public abstract Observable<GuideImage> getImageList();
    }

    public interface View extends BaseMVVMView {
       void  returnImageData(GuideImage data);
    }

    public abstract static class ViewModel<V extends BaseMVVMView, D extends ViewDataBinding, M extends BaseModel> extends BaseViewModel<V, D, M> {
        public abstract void getImageList();
    }
}
