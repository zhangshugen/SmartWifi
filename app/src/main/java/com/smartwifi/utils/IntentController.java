package com.smartwifi.utils;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

import com.smartwifi.R;
import com.smartwifi.bean.LaunchDirectBean;
import com.smartwifi.bean.PerformanceDealtListBean;
import com.smartwifi.part.direct.activity.DirectListActivity;
import com.smartwifi.part.direct.activity.LaunchDirectActivity;
import com.smartwifi.part.negative.activity.NegativeDetailsListActivity;
import com.smartwifi.part.negative.activity.NegativeListActivity;
import com.smartwifi.part.performance.activity.PerformanceDealtListActivity;
import com.smartwifi.part.performance.activity.PerformanceHomeActivity;
import com.smartwifi.part.performance.activity.PerformanceMatterDetailsActivity;
import com.smartwifi.part.process.ProcessStartActivity;
import com.smartwifi.part.splash.activity.GuideActivity;
import com.smartwifi.part.splash.activity.WelcomeActivity;
import com.smartwifi.part.user.activity.LoginActivity;
import com.smartwifi.part.user.activity.VerfcationActivity;
import com.smartwifi.widget.BigImagePagerActivity;

import java.util.ArrayList;

/**
 * @CreateTime 2018/7/9
 * @Describe
 */

public class IntentController {

    public static void toBigImageActivity(FragmentActivity activity, ArrayList<String> list, int position, int type) {
        Intent intent = new Intent(activity, BigImagePagerActivity.class);
        intent.putStringArrayListExtra(BigImagePagerActivity.INTENT_IMG_URLS, list);
        intent.putExtra(BigImagePagerActivity.INTENT_POSITION, position);
        intent.putExtra(BigImagePagerActivity.INTENT_IAG_TYPE, type);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
    }

    public static void toBigImageNetActivity(FragmentActivity activity, String path, int position) {
        ArrayList<String> strings = new ArrayList<>();
        strings.add(path);
        toBigImageActivity(activity, strings, position, BigImagePagerActivity.TYPE_NET_IMAGE);
    }

    public static void toBigImageNetActivity(FragmentActivity activity, ArrayList<String> path, int position) {

        toBigImageActivity(activity, path, position, BigImagePagerActivity.TYPE_NET_IMAGE);
    }

    public static void toBigImageFileActivity(FragmentActivity activity, String path, int position) {
        ArrayList<String> strings = new ArrayList<>();
        strings.add(path);
        toBigImageActivity(activity, strings, position, BigImagePagerActivity.TYPE_FILE_IMAGE);
    }

    public static void toBigImageFileActivity(FragmentActivity activity, ArrayList<String> path, int position) {

        toBigImageActivity(activity, path, position, BigImagePagerActivity.TYPE_FILE_IMAGE);
    }

    public static void toPerformanceHomeActivity(FragmentActivity mActivity) {
        Intent intent = new Intent(mActivity, PerformanceHomeActivity.class);
        mActivity.startActivity(intent);
    }

    public static void toPerformanceDealtListActivity(FragmentActivity activity, int type) {
        Intent intent = new Intent(activity, PerformanceDealtListActivity.class);
        intent.putExtra("type", type);
        activity.startActivity(intent);
    }

    public static void toPerformanceMatterDetailsActivity(FragmentActivity activity, int type, PerformanceDealtListBean bean) {
        Intent intent = new Intent(activity, PerformanceMatterDetailsActivity.class);
        intent.putExtra("activityType", type);
        intent.putExtra("data", bean);
        activity.startActivity(intent);
    }

    public static void toNegativeDetailsListActivity(FragmentActivity activity, int position, String title) {
        Intent intent = new Intent(activity, NegativeDetailsListActivity.class);
        intent.putExtra("position", position);
        intent.putExtra("title", title);
        activity.startActivity(intent);
    }

    public static void toNegativeListActivity(FragmentActivity activity) {
        Intent intent = new Intent(activity, NegativeListActivity.class);
        activity.startActivity(intent);
    }

    public static void toDirectListActivity(FragmentActivity activity) {
        Intent intent = new Intent(activity, DirectListActivity.class);
        activity.startActivity(intent);
    }

    public static void toLaunchDirectActivity(FragmentActivity activity, LaunchDirectBean itemData, int activityType) {
        Intent intent = new Intent(activity, LaunchDirectActivity.class);
        if (itemData != null)
            intent.putExtra("data", itemData);
        intent.putExtra("activityType", activityType);
        activity.startActivity(intent);
    }

    public static void toProcessStartActivity(FragmentActivity activity, int type) {
        Intent intent = new Intent(activity, ProcessStartActivity.class);
        intent.putExtra("type", type);
        activity.startActivity(intent);
    }

    public static void toWelcomeActivity(AppCompatActivity appCompatActivity) {
        appCompatActivity.startActivity(new Intent(appCompatActivity, WelcomeActivity.class));
        appCompatActivity.finish();
    }

    public static void toLoginActivity(AppCompatActivity appCompatActivity) {
        appCompatActivity.startActivity(new Intent(appCompatActivity, LoginActivity.class));
        appCompatActivity.finish();
    }

    public static void toVerfcationActivity(AppCompatActivity appCompatActivity,String phone) {
        Intent intent=new Intent(appCompatActivity,VerfcationActivity.class);
        intent.putExtra("phone",phone);
        appCompatActivity.startActivity(intent);
    }

    public static void toHomeActivity(AppCompatActivity appCompatActivity) {
        appCompatActivity.startActivity(new Intent(appCompatActivity, com.smartwifi.part.home.activity.HomeActivity.class));
        appCompatActivity.finish();
    }

    public static void toGuideActivity(AppCompatActivity appCompatActivity) {
        appCompatActivity.startActivity(new Intent(appCompatActivity, GuideActivity.class));
        appCompatActivity.finish();
    }


}
