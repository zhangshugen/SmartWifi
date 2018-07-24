package com.smartwifi.manager.retrofit;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.smartwifi.http.ApiServer;
import com.smartwifi.widget.retrofithelper.interceptor.NetworkInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2018/7/19.
 */

public class RetrofitOtherUrlManager extends RetrofitManager {
    public RetrofitOtherUrlManager(String url) {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        NetworkInterceptor networkInterceptor = new NetworkInterceptor();
        networkInterceptor.setLevel(NetworkInterceptor.Level.BODY);
        httpClientBuilder.addInterceptor(networkInterceptor);
        httpClientBuilder.connectTimeout(12, TimeUnit.SECONDS);
        httpClientBuilder.retryOnConnectionFailure(true);
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(url)
                .client(httpClientBuilder.build())
                .build();
        apiService = retrofit.create(ApiServer.class);
    }


}
