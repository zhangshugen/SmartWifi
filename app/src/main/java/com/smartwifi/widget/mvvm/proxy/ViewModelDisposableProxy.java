package com.smartwifi.widget.mvvm.proxy;

import com.smartwifi.widget.mvvm.DisposableLifeCycleListener;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/13
 * @Describe
 */

public class ViewModelDisposableProxy implements DisposableLifeCycleListener {

    private CompositeDisposable disposablesStop;// 管理Stop取消订阅者者
    private CompositeDisposable disposablesDestroy;// 管理Destroy取消订阅者者

    public ViewModelDisposableProxy() {
        disposablesDestroy = new CompositeDisposable();
    }


    public void onStart() {
        if (disposablesStop == null)
            disposablesStop = new CompositeDisposable();

    }

    @Override
    public boolean addRxStop(Disposable disposable) {
        if (disposablesStop == null) return false;
        disposablesStop.add(disposable);
        return true;
    }

    @Override
    public boolean addRxDestroy(Disposable disposable) {
        if (disposablesDestroy == null) return false;
        disposablesDestroy.add(disposable);
        return true;
    }

    @Override
    public void remove(Disposable disposable) {
        if (disposablesStop != null)
            disposablesDestroy.remove(disposable);

        if (disposablesDestroy != null)
            disposablesDestroy.remove(disposable);
    }

    public void onStop(boolean isStopCancel) {
        if (isStopCancel && disposablesStop != null) {
            disposablesStop.clear();
            disposablesStop = null;
        }

    }

    public void onDestroy() {
        if (disposablesDestroy != null) {
            disposablesDestroy.clear();
            disposablesDestroy = null;
        }

    }

    @Override
    public boolean isStopRxJava() {
        return false;
    }
}
