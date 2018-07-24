package com.smartwifi.part.splash.model;
import com.smartwifi.bean.GuideImage;
import com.smartwifi.manager.retrofit.RetrofitJsonManager;
import com.smartwifi.part.splash.contract.GuideContract;
import com.smartwifi.rxjava.RxJavaHttpHelper;
import com.smartwifi.widget.retrofithelper.rxschedulers.RxSchedulersHelper;

import io.reactivex.Observable;

public class GuideModel extends GuideContract.Model {

    @Override
    public Observable<GuideImage> getImageList() {
        //调用api 获取数据
        return RetrofitJsonManager.getInstance().apiService.getWelcomeImage()
                // 预处理
                .compose(RxJavaHttpHelper.<GuideImage>handleResult())
                // 子线程和主线程切换
                .compose(RxSchedulersHelper.applyIoTransformer());
    }
}
