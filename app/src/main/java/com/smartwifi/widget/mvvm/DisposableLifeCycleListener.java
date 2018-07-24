package com.smartwifi.widget.mvvm;

import io.reactivex.disposables.Disposable;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/13
 * @Describe
 */

public interface DisposableLifeCycleListener {
    boolean addRxStop(Disposable disposable);

    boolean addRxDestroy(Disposable disposable);

    void remove(Disposable disposable);

    boolean isStopRxJava();
}
