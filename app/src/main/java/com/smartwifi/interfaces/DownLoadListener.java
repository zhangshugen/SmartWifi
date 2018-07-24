package com.smartwifi.interfaces;

import java.io.File;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/12
 * @Describe
 */

public abstract class DownLoadListener {
    public void onPaused() {
    }

    public void onRunning() {
    }

    public void onPending() {
    }

    public abstract void onSuccess(File file);

    public abstract void onFailed();
}
