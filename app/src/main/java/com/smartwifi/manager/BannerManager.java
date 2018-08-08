package com.smartwifi.manager;

import android.support.v7.app.AppCompatActivity;

import com.smartwifi.bean.BannerInfo;
import com.smartwifi.manager.retrofit.RetrofitJsonManager;
import com.smartwifi.rxjava.RxJavaHttpHelper;
import com.smartwifi.widget.loopviewpage.AutoLoopSwitchBaseView;
import com.smartwifi.widget.loopviewpage.AutoSwitchAdapter;
import com.smartwifi.widget.retrofithelper.rxsubscriber.CommonObserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;

/**
 * Created by Mloong on 2018/8/3.
 */

public class BannerManager {
    private static class BannerManagerSingleton {
        private static final BannerManager INSTANCE = new BannerManager();

    }

    public static BannerManager getInstance() {
        return BannerManager.BannerManagerSingleton.INSTANCE;
    }


    public void setBannerDataWithType(final AppCompatActivity act, Map<String, Object> map, final AutoLoopSwitchBaseView viewPager) {
        if (map == null) {
            map = new HashMap<>();
        }
        requestData(act, map, viewPager, null);
    }


    public void setBannerDataWithType(final AppCompatActivity act, int type, final AutoLoopSwitchBaseView viewPager) {
        List<BannerInfo> bannerInfos = new ArrayList<>();
        bannerInfos.add(new BannerInfo("http://img.zcool.cn/community/0117e2571b8b246ac72538120dd8a4.jpg@1280w_1l_2o_100sh.jpg"));
        bannerInfos.add(new BannerInfo("http://img.zcool.cn/community/011a5859ac137ea8012028a92fc78a.jpg@1280w_1l_2o_100sh.jpg"));
        bannerInfos.add(new BannerInfo("http://img.zcool.cn/community/0117e2571b8b246ac72538120dd8a4.jpg@1280w_1l_2o_100sh.jpg"));
        setBannerView(act, viewPager, bannerInfos);
        if (true) return;
        Map<String, Object> map = new HashMap<>();
        map.put("type", type);
        requestData(act, map, viewPager, null);
    }

    public void setBannerDataWithType(final AppCompatActivity act, int type, Observer<List<BannerInfo>> observer) {
        Map<String, Object> map = new HashMap<>();
        map.put("type", type);
        requestData(act, map, null, observer);
    }

    public void setBannerDataWithType(final AppCompatActivity act, Map<String, Object> map, Observer<List<BannerInfo>> observer) {
        if (map == null) {
            map = new HashMap<>();
        }
        requestData(act, map, null, observer);
    }

    private void requestData(final AppCompatActivity act, Map<String, Object> map, final AutoLoopSwitchBaseView viewPager, Observer<List<BannerInfo>> observer) {
       /* Observable compose = RetrofitJsonManager.getInstance().apiService.getBannerInfo(map).compose(RxJavaHttpHelper.<List<BannerInfo>>applyTransformer());
        if (observer != null) {
            compose.subscribe(observer);
            return;
        }
        compose.subscribe(new CommonObserver<List<BannerInfo>>() {
            @Override
            public void onNext(List<BannerInfo> bannerInfos) {
                super.onNext(bannerInfos);
                setBannerView(act, viewPager, bannerInfos);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                List<BannerInfo> infoList = new ArrayList<>();
                BannerInfo info = new BannerInfo();
                info.image = "";
                infoList.add(info);
                setBannerView(act, viewPager, infoList);
            }
        });*/
    }

    private void setBannerView(AppCompatActivity act, AutoLoopSwitchBaseView viewPager, List<BannerInfo> bannerInfos) {
        AutoSwitchAdapter mAdapter = new AutoSwitchAdapter(act, bannerInfos);
        viewPager.setAdapter(mAdapter);
    }
}
