package com.smartwifi.base;

import android.databinding.ViewDataBinding;
import android.view.View;

import com.smartwifi.widget.mvvm.view.BaseMVVMActivity;
import com.smartwifi.widget.mvvm.viewmodel.BaseViewModel;
import com.smartwifi.widget.pagemanage.PageManager;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/16
 * @Describe
 */

public abstract class BasePageManageActivity<VM extends BaseViewModel, D extends ViewDataBinding> extends BaseMVVMActivity<VM, D> {


    protected PageManager mPageManage;

    @Override
    public void initView() {
        initPageManage();
    }

    private PageManager initPageManage() {
        if (getPageManagerView() == null) return null;
        if (mPageManage == null) {
            mPageManage = PageManager.init(getPageManagerView(), getRetryRunnable());
        }
        mPageManage.showLoading();
        return mPageManage;
    }

    private Runnable getRetryRunnable() {
        return new Runnable() {
            @Override
            public void run() {
                // requestNetPageManager(sNetRequestConfig);
                requestNetData();
            }
        };
    }

    /**
     * 此方法一定要复写,表示页面第一次加载数据与加载失败时点击重试
     */
    protected abstract void requestNetData();

    /**
     * @return 显示loading error emtry 的范围
     */
    protected abstract View getPageManagerView();

}
