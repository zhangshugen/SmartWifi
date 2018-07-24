package com.smartwifi.view;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.util.AttributeSet;

import com.smartwifi.base.BaseCustomView;
import com.smartwifi.bean.CommonEditProfileBean;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/9
 * @Describe
 */

public abstract class EditProfileView<D extends ViewDataBinding> extends BaseCustomView<D> {
    protected CommonEditProfileBean mInfo;

    public EditProfileView(Context context) {
        super(context);
    }

    public EditProfileView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void init(CommonEditProfileBean info) {
        this.mInfo = info;
    }

    public void onDestroy() {
    }

}
