package com.smartwifi.widget.retrofithelper.rxsubscriber;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

import com.orhanobut.logger.Logger;
import com.smartwifi.bean.ServerErrorMessageBean;
import com.smartwifi.widget.dialogfragment.LoadingDialogFragment;
import com.smartwifi.widget.mvvm.DisposableLifeCycleListener;
import com.smartwifi.widget.mvvm.view.AppActivityManager;
import com.smartwifi.widget.retrofithelper.rxexception.DefaultErrorBundle;
import com.smartwifi.widget.retrofithelper.rxexception.ErrorManager;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Administrator on 2018/7/17
 */
public abstract class ProgressObserver<T> implements Observer<T> {
    private DisposableLifeCycleListener lifeCycleListener;
    private String loadMsg;
    private boolean isShowLoading;
    private FragmentActivity appCompatActivity;
    private LoadingDialogFragment fragment;

    //  private IStateView iStateView;

    public ProgressObserver(FragmentActivity appCompatActivity, String loadMsg, boolean isShowLoading, DisposableLifeCycleListener lifeCycleListener) {
        //   this.iStateView = iStateView;
        this.appCompatActivity = appCompatActivity;
        this.loadMsg = loadMsg;
        this.isShowLoading = isShowLoading;
        this.lifeCycleListener = lifeCycleListener;
    }

    public ProgressObserver(String loadMsg, boolean isShowLoading, DisposableLifeCycleListener lifeCycleListener) {
        this(AppActivityManager.getAppActivityManager().currentActivity(), loadMsg, isShowLoading, lifeCycleListener);
    }

    public ProgressObserver(boolean isShowLoading, DisposableLifeCycleListener lifeCycleListener) {
        this(AppActivityManager.getAppActivityManager().currentActivity(), "加载中,请稍后...", isShowLoading, lifeCycleListener);
    }

    public ProgressObserver(DisposableLifeCycleListener lifeCycleListener) {
        this(AppActivityManager.getAppActivityManager().currentActivity(), "加载中,请稍后...", true, lifeCycleListener);
    }

    public ProgressObserver(AppCompatActivity appCompatActivity, DisposableLifeCycleListener lifeCycleListener) {
        this(appCompatActivity, "加载中,请稍后...", true, lifeCycleListener);

    }

    public ProgressObserver(AppCompatActivity appCompatActivity, boolean isShowLoading, DisposableLifeCycleListener lifeCycleListener) {
        this(appCompatActivity, "加载中,请稍后...", isShowLoading, lifeCycleListener);
    }

    @Override
    public void onError(Throwable e) {
        //iStateView.hideProgressDialog();
        if (isShowLoading && fragment != null && appCompatActivity != null) {
            try {
                fragment.dismiss();
            } catch (Exception ee) {
                ee.printStackTrace();
            } finally {
                appCompatActivity = null;
                fragment = null;
                ServerErrorMessageBean bean = ErrorManager.handleError(new DefaultErrorBundle((Exception) e));
                _onError(bean.message, bean.code);
            }
        } else {
            ServerErrorMessageBean bean = ErrorManager.handleError(new DefaultErrorBundle((Exception) e));
            _onError(bean.message, bean.code);
        }


    }

    @Override
    public void onComplete() {
        Logger.d("onComplete");
        if (isShowLoading && fragment != null && appCompatActivity != null) {
            try {
                fragment.dismiss();
            } catch (Exception ee) {
                ee.printStackTrace();
            } finally {
                appCompatActivity = null;
                fragment = null;
                _onCompleted();
            }
        } else {
            _onCompleted();
        }

    }

    @Override
    public void onSubscribe(Disposable d) {
        if (lifeCycleListener != null) {
            if (lifeCycleListener.isStopRxJava()) {
                lifeCycleListener.addRxStop(d);
            } else {
                lifeCycleListener.addRxDestroy(d);
            }
        }
        if (isShowLoading && appCompatActivity != null) {
            showLoadingDialog(d);
        } else {
            _onSubscribe(d);
        }
    }


    private void showLoadingDialog(Disposable d) {
        try {
            fragment = new LoadingDialogFragment();
            fragment.setLoadingText(loadMsg);
            fragment.show(appCompatActivity.getSupportFragmentManager());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            _onSubscribe(d);
        }
    }

    @Override
    public void onNext(T t) {
        _onNext(t);
    }

    public void _onSubscribe(Disposable d) {

    }

    public void _onError(String errorMessage, int errorCode) {
    }

    public abstract void _onNext(T t);

    public void _onCompleted() {

    }
}
