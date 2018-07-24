package com.smartwifi.widget.mvvm.proxy;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.smartwifi.widget.mvvm.view.BaseMVVMView;

/**
 * Created by Administrator on 2018/2/7 0007.
 */

public interface BaseViewModelProxyMethod<V extends BaseMVVMView> {
    void onAttachView(V view);


    void onDetachView();

    void onDestroyViewModel();

    void onCreateViewModel(@Nullable Bundle savedState);

    void onSaveInstanceState(Bundle bundle);
}
