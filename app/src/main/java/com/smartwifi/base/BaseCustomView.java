
package com.smartwifi.base;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.smartwifi.widget.mvvm.DisposableLifeCycleListener;
import com.smartwifi.widget.mvvm.ViewInitializeLifeCycle;

import butterknife.ButterKnife;

/**
 * 自定义组合控件的基类 Created by
 */
public abstract class BaseCustomView<D extends ViewDataBinding> extends FrameLayout implements ViewInitializeLifeCycle {
    protected Context mContext;
    protected View mRootView;
    protected D mBinding;
    protected DisposableLifeCycleListener lifeCycleListener;
    public BaseCustomView(Context context) {
        this(context, null);
    }

    public BaseCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        if (getLayoutId() == 0) throw new NullPointerException("BaseCustomView layoutId is null");
        mRootView = View.inflate(context, getLayoutId(), null);
        if (isUseDataBinding()) {
            mBinding = (D) DataBindingUtil.bind(mRootView);
            initView(context, attrs);
        } else {
            initView(context, mRootView, attrs);
            ButterKnife.bind(this, mRootView);
        }
        addView(mRootView);
        initData(context);
        initEvent(context);
    }

    /**
     *
     * */
    protected boolean isUseDataBinding() {
        return true;
    }

    protected abstract int getLayoutId();

    protected abstract void initView(Context context, AttributeSet attrs);

    @Override
    public void initView(Context context, View rootView, AttributeSet attrs) {

    }
    public void setDisposableLifeCycleListener(DisposableLifeCycleListener lifeCycleListener){
        this.lifeCycleListener=lifeCycleListener;
    }

}
