package com.smartwifi.widget.retrofithelper.listener;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/15
 * @Describe
 */

public interface DownloadListener {
    void onStartDownload();

    void onProgress(int progress);

    void onFinishDownload();

    void onFail(String errorInfo);
}
