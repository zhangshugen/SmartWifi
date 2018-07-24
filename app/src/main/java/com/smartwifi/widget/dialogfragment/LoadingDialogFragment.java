package com.smartwifi.widget.dialogfragment;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.smartwifi.R;
import com.smartwifi.bean.LoadingDialogConfigBean;
import com.smartwifi.databinding.DialogNetLoadingBinding;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/9
 * @Describe 访问网络或者数据库 loading处理
 */

public class LoadingDialogFragment extends BaseDialogFragment<DialogNetLoadingBinding> {

    private LoadingDialogConfigBean bean;
    private String loadingText="加载中,请稍后";

    @Override
    public void initView(Context context, View rootView, AttributeSet attrs) {

    }

    @Override
    public void initData(Context context) {
        bean = new LoadingDialogConfigBean();
        bean.text = loadingText;
        mBinding.setData(bean);
    }

    @Override
    public void initEvent(Context context) {

    }

    @Override
    public boolean isUseBlur() {
        return true;
    }




    @Override
    public int getLayoutRes() {
        return R.layout.dialog_net_loading;
    }

    @Override
    public int setDisplayAnimation() {
        return TYPE_LEFT;
    }

    @Override
    public boolean getCancelOutside() {
        return true;
    }

    @Override
    public boolean isHeightMatchParent() {
        return true;
    }

    public void setLoadingText(String text) {
        loadingText = text;
    }

    public void setCancelOutside(boolean f) {
        //bean.setCancel(f);
        setCancelable(f);
    }

    public void setUseBlur(boolean f) {
        bean.setUseBlur(f);
    }


}
