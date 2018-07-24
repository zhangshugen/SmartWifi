package com.smartwifi.widget.mvvm;

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.KeyEvent;

import com.smartwifi.widget.mvvm.view.BaseMVVMView;

/**
 * Created by Administrator on 2018/2/7 0007.
 */

public interface UILifeCycle<V extends BaseMVVMView,D extends ViewDataBinding> {

    void onResume(V mvvmView);

    Boolean onKeyDown(int keyCode, KeyEvent event);

    void onStart();

    void onPause();

    void onDestroy();

    void onStop();

    Boolean onBackPressed();

    void onActivityResult(int requestCode, int resultCode, Intent data);

    void onCreate(Bundle savedInstanceState);
    void setViewBinding(D binding);
}
