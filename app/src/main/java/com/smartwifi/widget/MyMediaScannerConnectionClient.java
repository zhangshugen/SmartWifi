package com.smartwifi.widget;

import android.media.MediaScannerConnection;
import android.net.Uri;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/27
 * @Describe
 */

public class MyMediaScannerConnectionClient implements MediaScannerConnection.MediaScannerConnectionClient {

    private MediaScannerConnection mScanner;

    private String mScanPath;

    public MyMediaScannerConnectionClient(String scanPath) {
        mScanPath = scanPath;
    }

    public void setScanner(MediaScannerConnection con) {
        mScanner = con;
    }

    @Override
    public void onMediaScannerConnected() {
        mScanner.scanFile(mScanPath, "image/*");
    }

    @Override
    public void onScanCompleted(String path, Uri uri) {
        mScanner.disconnect();
    }
}
