package com.smartwifi.widget.retrofithelper.interceptor;

import com.smartwifi.widget.retrofithelper.listener.DownloadListener;
import com.smartwifi.widget.retrofithelper.responsebody.DownloadResponseBody;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/15
 * @Describe
 */

public class DownloadInterceptor  implements Interceptor {

    private DownloadListener downloadListener;

    public DownloadInterceptor(DownloadListener downloadListener) {
        this.downloadListener = downloadListener;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        return response.newBuilder().body(
                new DownloadResponseBody(response.body(), downloadListener)).build();
    }
}
