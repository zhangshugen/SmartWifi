package com.smartwifi.manager.retrofit;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.smartwifi.http.ApiServer;
import com.smartwifi.http.Url;
import com.smartwifi.widget.retrofithelper.interceptor.NetworkInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/13
 * @Describe
 */

public class RetrofitStringManager extends RetrofitManager {

    private static class SingleHttpMethods {
        private static final RetrofitStringManager INSTANCE = new RetrofitStringManager();
    }

    //获取单例
    public static RetrofitStringManager getInstance() {
        return RetrofitStringManager.SingleHttpMethods.INSTANCE;
    }

    private RetrofitStringManager() {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        NetworkInterceptor networkInterceptor = new NetworkInterceptor();
        networkInterceptor.setLevel(NetworkInterceptor.Level.BODY);
        //    httpClientBuilder.addInterceptor(networkInterceptor);
        httpClientBuilder.connectTimeout(12, TimeUnit.SECONDS);
        httpClientBuilder.retryOnConnectionFailure(true);
        retrofit = new Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(Url.BASE_URL)
                .client(httpClientBuilder.build())
                .build();
        apiService = retrofit.create(ApiServer.class);
    }

}
