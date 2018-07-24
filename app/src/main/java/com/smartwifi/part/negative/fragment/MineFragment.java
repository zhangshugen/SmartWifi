package com.smartwifi.part.negative.fragment;

import com.smartwifi.R;
import com.smartwifi.databinding.FragmentMineBinding;
import com.smartwifi.part.home.viewmodel.HomeFragmentViewModel;
import com.smartwifi.part.negative.viewmodel.MineFragmentViewModel;
import com.smartwifi.widget.mvvm.factory.CreateViewModel;
import com.smartwifi.widget.mvvm.view.BaseMVVMFragment;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/9
 * @Describe
 */

@CreateViewModel(HomeFragmentViewModel.class)
public class MineFragment extends BaseMVVMFragment<MineFragmentViewModel,FragmentMineBinding> {
    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

    }
}
