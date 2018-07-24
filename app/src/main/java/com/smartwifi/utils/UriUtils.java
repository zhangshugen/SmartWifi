package com.smartwifi.utils;

import android.net.Uri;
import android.support.annotation.DrawableRes;

/**
 * Created by apanda on 2018/7/9.
 */

public class UriUtils {

    public static Uri getUriFromResource(@DrawableRes int res) {
        return Uri.parse("android.resource://cn.natrip.android.civilizedcommunity/" + res);
    }

}
