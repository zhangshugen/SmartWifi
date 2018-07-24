package com.smartwifi.widget.retrofithelper.interceptor;

import com.smartwifi.widget.retrofithelper.utils.NetUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/19
 * @Describe
 */

public class CacheProvideOfflineInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        /**
         * 未联网获取缓存数据
         */
        if (!NetUtils.isNetworkConnected()) {
            //在20秒缓存有效，此处测试用，实际根据需求设置具体缓存有效时间
            CacheControl cacheControl = new CacheControl.Builder()
                    .maxStale(120, TimeUnit.SECONDS)
                    .build();

            request = request.newBuilder()
                    .cacheControl(cacheControl)
                    .build();
        }

        return chain.proceed(request);
    }
}
