package com.smartwifi.http;

import com.smartwifi.bean.BannerInfo;
import com.smartwifi.bean.BaseListData;
import com.smartwifi.bean.BaseRequestData;
import com.smartwifi.bean.EditProfileBean;
import com.smartwifi.bean.EditProfileCategoryBean;
import com.smartwifi.bean.GuideImage;
import com.smartwifi.bean.ProfileSelectionStaffBean;
import com.smartwifi.bean.TaskListBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/10
 * @Describe
 */

public interface ApiServer {

    @GET
    Observable<BaseRequestData<EditProfileBean>> getEditProfileParams(@retrofit2.http.Url String url);

    @GET("sample/ashx/mobile/backconfig.ashx?STYPE=TB_PHRASE")
    Observable<BaseRequestData<EditProfileCategoryBean>> getEditProfileTypeCategory(@Query("Identity") String Identity);

    @POST()
    Observable<ResponseBody> uploadFileWithRequestBody(@Url String url, @Body MultipartBody multipartBody);

    @GET("sample/ashx/mobile/backconfig.ashx?STYPE=GETRY")
    Observable<BaseRequestData<BaseListData<ProfileSelectionStaffBean>>> getStaffData();

    @FormUrlEncoded
    @POST("sample/ashx/mobile/TaskProcessIf.ashx?Stype=getUserTodoOrDoneTaskList")
    Observable<BaseRequestData<BaseListData<TaskListBean>>> getTaskList(@FieldMap Map<String, Object> map);



    @GET("ws/wifi/findImgByOrgId.do?orgId=8a8ab0b246dc81120146dc8180ba0017")
    Observable<BaseRequestData<GuideImage>> getWelcomeImage();
    //Observable<BaseRequestData<List<GuideImage>>> getWelcomeImage();

    @GET("ws/user/verifyPhone.do")
    Observable<BaseRequestData<Object>> getVerifyingCode(@Query("phone") String phone);

    @GET("/ws/user/login.do?loginType=sj")
    Observable<BaseRequestData<Object>> getConfirmLogin(@Query("phone") String phone,@Query("verifyCode") String code);

    @GET()
    Observable<Object> authWifi(@Url String url);


    @GET("ws/user/login.do")
    Observable<BaseRequestData<Object>> getThirdLogin(@Query("loginType") String type,@Query("openid") String openid,@Query("nickname")String nickname);

    @GET("ws/wifi/findLbtByOrgId.do?orgId=8a8ab0b246dc81120146dc8180ba0017")
    Observable<BaseRequestData<List<BannerInfo>>> getBannerInfo(@QueryMap Map<String, Object> map);
}



