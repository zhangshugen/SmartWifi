package com.smartwifi.utils;

import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.MenuRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.smartwifi.R;


/**
 * Created by Administrator on 2018/7/9 0007.
 */

public class ToolbarUtils {


    /**
     * 显示右侧菜单， 不现实导航按钮
     *
     * @param toolbar
     * @param showNavi
     * @param menu_layout
     */
    public static void initToolBar(Toolbar toolbar, boolean showNavi, @MenuRes int menu_layout, AppCompatActivity appCompatActivity) {
        initToolBar(toolbar, "", -1, showNavi, appCompatActivity);
    }

    /**
     * 显示右侧菜单， 显示导航按钮
     *
     * @param toolbar
     * @param menu_layout
     */
    public static void initToolBar(Toolbar toolbar, @MenuRes int menu_layout, AppCompatActivity appCompatActivity) {
        initToolBar(toolbar, "", -1, true, appCompatActivity);
    }

    /**
     * 显示右侧菜单， 显示导航按钮
     *
     * @param toolbar
     * @param menu_layout
     */
    public static void initToolBar(Toolbar toolbar, @MenuRes int menu_layout, @ColorRes int color, AppCompatActivity appCompatActivity) {
        initToolBar(toolbar, "", color, true, appCompatActivity);
    }

    /**
     * 初始化 Toolbar
     */
    public static void initToolBar(Toolbar toolbar, String title, @ColorRes int title_color, boolean showNavi, AppCompatActivity appCompatActivity) {
        toolbar.setTitle(title);
        if (title_color != -1) {
            toolbar.setTitleTextColor(UIUtils.getColor(title_color));
        }

        appCompatActivity.setSupportActionBar(toolbar);
        if (showNavi) {
            toolbar.setNavigationIcon(R.mipmap.ic_left_arrow);
        }
        appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }


    /**
     * 初始化 Toolbar
     */
    public static void initToolBarWhiteNav(Toolbar toolbar, AppCompatActivity appCompatActivity) {
        toolbar.setTitle("");
        appCompatActivity.setSupportActionBar(toolbar);

       // toolbar.setNavigationIcon(R.mipmap.ic_ggqjz_back_white);
        appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }


    /**
     * 初始化 Toolbar
     */
    public static void initToolBar(Toolbar toolbar, String title, @ColorRes int title_color, AppCompatActivity appCompatActivity) {
        initToolBar(toolbar, title, title_color, true, appCompatActivity);

    }


    public static void initToolBar(Toolbar toolbar, String title, AppCompatActivity appCompatActivity) {
        initToolBar(toolbar, title, -1, true, appCompatActivity);
    }

    public static void initToolBar(Toolbar toolbar, AppCompatActivity appCompatActivity) {
        initToolBar(toolbar, "", -1, true, appCompatActivity);
    }

    public static void initToolBar(Toolbar toolbar, Bundle _bundle, AppCompatActivity appCompatActivity) {
        initToolBar(toolbar, "", -1, true, appCompatActivity);
    }

    public static void initToolBarByIcon(Toolbar toolbar, AppCompatActivity appCompatActivity, @DrawableRes int res) {
        toolbar.setTitle("");
        appCompatActivity.setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(res);
        appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);


    }
}
