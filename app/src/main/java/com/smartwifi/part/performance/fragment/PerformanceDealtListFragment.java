package com.smartwifi.part.performance.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.smartwifi.R;
import com.smartwifi.bean.PerformanceDealtListBean;
import com.smartwifi.databinding.FragmentHomeBinding;
import com.smartwifi.part.performance.viewmodel.PerformanceDealtListFragmentViewModel;
import com.smartwifi.utils.IntentController;
import com.smartwifi.widget.databindingadapter.BaseBindingItemPresenter;
import com.smartwifi.widget.databindingadapter.SingleTypeBindingAdapter;
import com.smartwifi.widget.mvvm.factory.CreateViewModel;
import com.smartwifi.widget.mvvm.view.BaseMVVMFragment;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/9
 * @Describe
 */

@CreateViewModel(PerformanceDealtListFragmentViewModel.class)
public class PerformanceDealtListFragment extends BaseMVVMFragment<PerformanceDealtListFragmentViewModel, FragmentHomeBinding> implements BaseBindingItemPresenter<PerformanceDealtListBean> {

    private int activityType;
    private int fragmentType;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView() {
        activityType = getArguments().getInt("activityType");
        fragmentType = getArguments().getInt("fragmentType");
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        SingleTypeBindingAdapter adapter = new SingleTypeBindingAdapter(mActivity, PerformanceDealtListBean.getPerformanceDealtListBeanList(fragmentType), R.layout.item_performance_dealt);
        adapter.setItemPresenter(this);
        mBinding.recyclerView.setAdapter(adapter);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    public void onItemClick(int position, PerformanceDealtListBean itemData) {
        IntentController.toPerformanceMatterDetailsActivity(mActivity,activityType,itemData);
    }

    public static PerformanceDealtListFragment getFragment(int activityType, int fragmentType) {
        PerformanceDealtListFragment fragment = new PerformanceDealtListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("activityType", activityType);
        bundle.putInt("fragmentType", fragmentType);
        fragment.setArguments(bundle);
        return fragment;
    }

}
