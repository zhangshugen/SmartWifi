package com.smartwifi.utils;

import android.databinding.BindingAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.ImageView;

import com.smartwifi.R;
import com.smartwifi.bean.GuideImage;
import com.smartwifi.manager.BannerManager;
import com.smartwifi.widget.giide.GlideImageLoader;
import com.smartwifi.widget.loopviewpage.AutoSwitchView;

import java.io.File;

/**
 * Created by Administrator on 2018/7/9
 */

public class DataBindingUtils {

    @BindingAdapter("adapter")
    public static void setAdapter(RecyclerView rv, RecyclerView.Adapter adapter) {
        rv.setAdapter(adapter);
    }

    @BindingAdapter("layoutManager")
    public static void setLayoutManager(RecyclerView rv, RecyclerView.LayoutManager layoutManager) {
        rv.setLayoutManager(layoutManager);
    }

    @BindingAdapter("onDisplayImage")
    public static void onDisplayImage(ImageView iv, String url) {
        if (!TextUtils.isEmpty(url)) {
            if (url.startsWith("http")) {
                GlideImageLoader.onDisplayImage(iv.getContext(), iv, url);
            } else {
                GlideImageLoader.onDisplayImage(iv.getContext(), iv, new File(url));
            }
        }
    }

    @BindingAdapter("onDisplayImage")
    public static void onDisplayImage(ImageView iv, int url) {
        iv.setImageResource(url);
     }

    @BindingAdapter("onImageRes")
    public static void onImageRes(ImageView iv, int url) {
        iv.setImageResource(url);
     }

    @BindingAdapter("onDisplayRound")
    public static void onDisplayRound(ImageView iv, String url) {
        if (!TextUtils.isEmpty(url)) {
            GlideImageLoader.displayRound(iv.getContext(), iv, url);
        } else {
            GlideImageLoader.displayRound(iv.getContext(), iv, R.mipmap.ic_launcher_round);
        }
    }

    @BindingAdapter("initBanner")
    public static void initBanner(AutoSwitchView view, int type) {
        BannerManager.getInstance().setBannerDataWithType((AppCompatActivity) view.getContext(), type, view);
    }
}
