package com.smartwifi.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/9
 * @Describe
 */

public class BaseFragmentAdapter extends FragmentPagerAdapter {

    private List<String> titleList;
    private List<Fragment> fragmentList;

    public BaseFragmentAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        this(fm, new ArrayList<String>(), fragmentList);
    }

    public BaseFragmentAdapter(FragmentManager fm, List<String> titleList, List<Fragment> fragmentList) {
        super(fm);
        this.titleList = titleList;
        this.fragmentList = fragmentList;
    }

    public BaseFragmentAdapter(FragmentManager fm, String[] titleList, List<Fragment> fragmentList) {
        super(fm);
        this.titleList = Arrays.asList(titleList);
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (titleList == null || titleList.size() == 0)
            return super.getPageTitle(position);
        return titleList.get(position);
    }
}
