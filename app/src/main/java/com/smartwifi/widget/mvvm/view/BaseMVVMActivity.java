package com.smartwifi.widget.mvvm.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;

import com.smartwifi.R;
import com.smartwifi.widget.SwipeWindowHelper.SwipeWindowHelper;
import com.smartwifi.widget.SystemBarTintManagerHelper;
import com.smartwifi.widget.mvvm.ActivityLiftCycle;
import com.smartwifi.widget.mvvm.factory.ViewModelFactory;
import com.smartwifi.widget.mvvm.factory.ViewModelFactoryImpl;
import com.smartwifi.widget.mvvm.proxy.BaseViewModelProxy;
import com.smartwifi.widget.mvvm.proxy.ViewModelProxyImpl;
import com.smartwifi.widget.mvvm.viewmodel.BaseViewModel;

import butterknife.ButterKnife;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/9
 * @Describe MVVM的基类, Proxy管理 View与ViewModel的生命周期
 */

public abstract class BaseMVVMActivity<VM extends BaseViewModel, D extends ViewDataBinding> extends AppCompatActivity implements BaseViewModelProxy<VM>, ActivityLiftCycle, BaseMVVMView {
    private static final String VIEW_MODEL_SAVE_KEY = "view_model_save_key";
    /**
     * 创建被代理对象,传入默认Presenter的工厂
     */
    private ViewModelProxyImpl<VM> mProxy;
    protected D mBinding;
    protected VM mViewModel;


    @Override
    public void setViewModelFactory(ViewModelFactory factory) {
        mProxy.setViewModelFactory(factory);
    }


    @Override
    public ViewModelFactory getViewModelFactory() {
        return mProxy.getViewModelFactory();
    }

    @Override
    public VM getViewModel() {
        return mProxy.getViewModel();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initStateBar();
        if (isUseProxy()) {
            mProxy = new ViewModelProxyImpl<VM>(ViewModelFactoryImpl.<VM>createViewModelFactory(getClass()));
            mViewModel = getViewModel();
            mProxy.onCreate(savedInstanceState);
            mProxy.setActivity(this);
            mProxy.onAttachViewModelView(this);
        }

        if (getLayoutId() <= 0) {
            throw new NullPointerException("layout Id is null ");
        }
        if (isUseDataBinding()) {
            mBinding = DataBindingUtil.setContentView(this, getLayoutId());
            if (isUseProxy()) {
                mProxy.setViewBinding(mBinding);
            }
        } else {
            setContentView(getLayoutId());
            ButterKnife.bind(this);
        }

        AppActivityManager.getAppActivityManager().addActivity(this);
        initView();
        initData();
        initEvent();
    }

    private void initStateBar() {
        if (isNeedLoadStatusBar()) {
            SystemBarTintManagerHelper.getInsatance().initStateBar(this, getStateBarColor());
        }
    }

    private void loadStateBar() {

    }

    protected boolean isNeedLoadStatusBar() {
        return true;
    }

    private SwipeWindowHelper mSwipeWindowHelper;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (!supportSlideBack()) {
            return super.dispatchTouchEvent(ev);
        }

        if (mSwipeWindowHelper == null) {
            mSwipeWindowHelper = new SwipeWindowHelper(getWindow());
        }
        try {
            return mSwipeWindowHelper.processTouchEvent(ev) || super.dispatchTouchEvent(ev);
        } catch (Exception _e) {
            _e.printStackTrace();
        }
        return false;
    }

    /**
     * 是否支持滑动返回
     *
     * @return
     */
    protected boolean supportSlideBack() {
        return true;
    }

    public boolean isUseDataBinding() {
        return true;
    }

    public boolean isUseProxy() {
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isUseProxy())
            mProxy.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isUseProxy())
            mProxy.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isUseProxy())
            mProxy.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (isUseProxy())
            mProxy.onStart();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (isUseProxy())
            outState.putBundle(VIEW_MODEL_SAVE_KEY, mProxy.onSaveInstanceState());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isUseProxy())
            mProxy.onDestroy();
        AppActivityManager.getAppActivityManager().finishActivity(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (isUseProxy())
            mProxy.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (isUseProxy()) {
            if (mProxy.onKeyDown(keyCode, event)) {
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        if (isUseProxy()) {
            if (!mProxy.onBackPressed()) {
                super.onBackPressed();
            }
        }
        super.onBackPressed();

    }

    public int getStateBarColor() {
        return R.color.blue_3b;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:// 点击返回图标事件
                onBackPressed();
                break;
            default:
                menuItemSelected(item);
                break;

        }
        return super.onOptionsItemSelected(item);
    }
    public void menuItemSelected(MenuItem item) {

    }
}
