package com.smartwifi.view;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;

import com.smartwifi.R;
import com.smartwifi.bean.CommonEditProfileBean;
import com.smartwifi.constant.Constant;
import com.smartwifi.databinding.ViewProfileChooseBinding;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/9
 * @Describe
 */

public abstract class EditProfileChooseView extends EditProfileView<ViewProfileChooseBinding> {

    private AlertDialog chooseDialog;
    protected String content;

    public EditProfileChooseView(Context context) {
        super(context);
    }

    int normal = 0;

    public EditProfileChooseView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void initData(Context context) {

    }

    @Override
    public void initEvent(Context context) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.view_profile_choose;
    }

    @Override
    protected void initView(Context context, AttributeSet attrs) {

    }

    @Override
    public void init(CommonEditProfileBean info) {
        super.init(info);
        mBinding.setInfo(info);
        mBinding.setView(this);
        //content = info.getDEFTABLE();
    }

    public void clickChooseView() {
        if (mInfo.getDEFTABLE().equals(Constant.TYPE_FALSE)) return;

        chooseViewAction();
    }

    public abstract void chooseViewAction();


    public String getContent() {
        return content;
    }
}
