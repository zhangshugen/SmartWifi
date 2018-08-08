package com.smartwifi.manager.retrofit;

import com.smartwifi.http.ApiServer;

import retrofit2.Retrofit;

/**
 *
 * @CreateTime 2018/7/9
 * @Describe
 */

public class RetrofitManager {
    public ApiServer apiService;

    public ApiServer getApiService() {
        return apiService;
    }

    public Retrofit retrofit;
}
