package com.smartwifi.part.home.fragment;

import com.smartwifi.R;
import com.smartwifi.databinding.FragmentWifiBinding;
import com.smartwifi.part.home.viewmodel.WifiFragmentViewModel;
import com.smartwifi.widget.mvvm.factory.CreateViewModel;
import com.smartwifi.widget.mvvm.view.BaseMVVMFragment;

/**
 * Created by wificityios on 2018/7/27.
 */

@CreateViewModel(WifiFragmentViewModel.class)
public class WifiFragment extends BaseMVVMFragment<WifiFragmentViewModel,FragmentWifiBinding> {
    @Override
    public int getLayoutId() {
        return R.layout.fragment_wifi;
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
