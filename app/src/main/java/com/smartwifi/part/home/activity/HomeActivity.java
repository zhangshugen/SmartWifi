package com.smartwifi.part.home.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.smartwifi.R;
import com.smartwifi.base.BaseFragmentAdapter;
import com.smartwifi.databinding.ActivityHomeBinding;
import com.smartwifi.part.MainView;
import com.smartwifi.part.MainViewModel;
import com.smartwifi.part.home.fragment.CompanyFragment;
import com.smartwifi.part.home.fragment.HomeFragment;
import com.smartwifi.part.home.fragment.MineFragment;
import com.smartwifi.part.home.fragment.WifiFragment;
import com.smartwifi.widget.mvvm.factory.CreateViewModel;
import com.smartwifi.widget.mvvm.view.BaseMVVMActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/17
 * @Describe
 */
@CreateViewModel(MainViewModel.class)
public class HomeActivity extends BaseMVVMActivity<MainViewModel, ActivityHomeBinding> implements MainView {


    private List<Fragment> mFragmentList = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_home;
    }


    @Override
    public void initView() {

        //底部条目
        HomeFragment homeFragment = new HomeFragment();
        WifiFragment wifiFragment = new WifiFragment();
        CompanyFragment companyFragments = new CompanyFragment();
        MineFragment mineFragments = new MineFragment();
        mFragmentList.add(homeFragment);
        mFragmentList.add(wifiFragment);
        mFragmentList.add(companyFragments);
        mFragmentList.add(mineFragments);

        BaseFragmentAdapter adapter = new BaseFragmentAdapter(getSupportFragmentManager(), mFragmentList);
        mBinding.vpContent.setAdapter(adapter);
        mBinding.bbl.setViewPager(mBinding.vpContent);

    }


    @Override
    public void initData() {
    }

    @Override
    public void initEvent() {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    }

    @Override
    public int getStateBarColor() {
        return R.color.blue_1e;
    }
}
