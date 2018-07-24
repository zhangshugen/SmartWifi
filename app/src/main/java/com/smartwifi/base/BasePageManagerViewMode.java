package com.smartwifi.base;

import android.databinding.ViewDataBinding;

import com.smartwifi.interfaces.BasePageManageView;
import com.smartwifi.widget.mvvm.model.BaseModel;
import com.smartwifi.widget.mvvm.viewmodel.BaseViewModel;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/11
 * @Describe
 */

public class BasePageManagerViewMode<V extends BasePageManageView, D extends ViewDataBinding, M extends BaseModel> extends BaseViewModel<V, D, M> {
    
}
