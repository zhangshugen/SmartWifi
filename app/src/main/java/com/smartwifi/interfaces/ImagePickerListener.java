package com.smartwifi.interfaces;

import android.net.Uri;

import java.util.List;

/**
 * Created by apanda on 2018/7/9.
 */

public abstract class ImagePickerListener {
    public void getMultiUrlImages(List<Uri> imgs) {
    }

    public void getMultiUrlStringImages(List<String> imgs) {
    }

    public void getSingleUrlImages(Uri uri) {
    }

    public void getSingleUrlImages(Uri uri, int tag) {
        getSingleUrlImages(uri);
    }

    public void getSingleUrlImages(String path, int tag) {
        getSingleUrlImages(path);
    }
    public void getSingleUrlImages(String uri) {
    }
}
