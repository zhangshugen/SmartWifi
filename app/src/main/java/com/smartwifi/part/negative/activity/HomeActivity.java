package com.smartwifi.part.negative.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.smartwifi.R;
import com.smartwifi.base.BaseFragmentAdapter;
import com.smartwifi.databinding.ActivityHomeBinding;
import com.smartwifi.part.MainView;
import com.smartwifi.part.MainViewModel;
import com.smartwifi.part.home.fragment.HomeFragment;
import com.smartwifi.part.negative.fragment.MineFragment;
import com.smartwifi.widget.mvvm.factory.CreateViewModel;
import com.smartwifi.widget.mvvm.view.BaseMVVMActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/27
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

        HomeFragment homeFragment = new HomeFragment();
        MineFragment mineFragment = new MineFragment();
        mFragmentList.add(homeFragment);
        mFragmentList.add(mineFragment);
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
}
