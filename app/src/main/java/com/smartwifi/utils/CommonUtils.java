package com.smartwifi.utils;

import android.content.res.Resources;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

import com.smartwifi.bean.ProfileSelectionStaffLocalBean;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/19
 * @Describe
 */

public class CommonUtils {
    /*
    * 适配nestedScrollView 嵌套混乱 滑动不流畅
    * */
    public static void setManagerConfig(RecyclerView.LayoutManager manager, RecyclerView recyclerView) {
        if (manager instanceof LinearLayoutManager) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) manager;
            linearLayoutManager.setSmoothScrollbarEnabled(true);
        } else if (manager instanceof GridLayoutManager) {
            GridLayoutManager linearLayoutManager = (GridLayoutManager) manager;
            linearLayoutManager.setSmoothScrollbarEnabled(true);
        }
        manager.setAutoMeasureEnabled(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
    }

    public static void handlerList(List<ProfileSelectionStaffLocalBean> profileList1) {
        for (int i = 0; i < profileList1.size() - 1; i++) {
            if (TextUtils.isEmpty(profileList1.get(i).companyName))
                break;
            for (int j = profileList1.size() - 1; j > i; j--) {

                if (profileList1.get(j).companyName.equals(profileList1.get(i).companyName)) {
                    profileList1.remove(j);
                }
            }
        }

    }

    public static void clearListOfIndex(List data, int index) {
        for (int i = data.size() - 1; i >= index; i--) {
            data.remove(i);
        }
    }

    public static void setIndicator(final TabLayout tabs, final int leftDip, final int rightDip) {
        tabs.post(new Runnable() {
            @Override
            public void run() {
                Class<?> tabLayout = tabs.getClass();
                Field tabStrip = null;
                try {
                    tabStrip = tabLayout.getDeclaredField("mTabStrip");
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }

                tabStrip.setAccessible(true);
                LinearLayout llTab = null;
                try {
                    llTab = (LinearLayout) tabStrip.get(tabs);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

                int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
                int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());


                for (int i = 0; i < llTab.getChildCount(); i++) {
                    View child = llTab.getChildAt(i);
                    child.setPadding(0, 0, 0, 0);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
                    params.leftMargin = left;
                    params.rightMargin = right;
                    child.setLayoutParams(params);
                    child.invalidate();
                }
            }

        });


    }
    public  static  String    intToIp(int i) {
        return (i & 0xFF ) + "." +
                ((i >> 8 ) & 0xFF) + "." +
                ((i >> 16 ) & 0xFF) + "." +
                ( i >> 24 & 0xFF) ;
    }
}
