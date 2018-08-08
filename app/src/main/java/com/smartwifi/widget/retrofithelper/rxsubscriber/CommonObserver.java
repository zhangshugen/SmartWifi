package com.smartwifi.widget.retrofithelper.rxsubscriber;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Mloong on 2018/8/3.
 */

public abstract class CommonObserver<T> implements Observer<T> {


    @Override
    public void onSubscribe(Disposable d) {
    }

    @Override
    public void onNext(T t) {
    }

    @Override
    public void onError(Throwable e) {
    }

    @Override
    public void onComplete() {
    }


}
