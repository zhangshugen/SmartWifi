package com.smartwifi.part.home.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;

import com.smartwifi.R;
import com.smartwifi.bean.HomeBean;
import com.smartwifi.bean.HomeHeaderBean;
import com.smartwifi.bean.ItemHomeHeaderBean;
import com.smartwifi.databinding.FragmentHomeBinding;
import com.smartwifi.databinding.ItemHomeHeaderBinding;
import com.smartwifi.part.home.viewmodel.HomeFragmentViewModel;
import com.smartwifi.widget.databindingadapter.BaseBindingItemPresenter;
import com.smartwifi.widget.databindingadapter.BaseDataBindingDecorator;
import com.smartwifi.widget.databindingadapter.BindingViewHolder;
import com.smartwifi.widget.databindingadapter.SingleTypeBindingAdapter;
import com.smartwifi.widget.mvvm.factory.CreateViewModel;
import com.smartwifi.widget.mvvm.view.BaseMVVMFragment;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/14
 * @Describe
 */

@CreateViewModel(HomeFragmentViewModel.class)
public class HomeFragment extends BaseMVVMFragment<HomeFragmentViewModel, FragmentHomeBinding> implements BaseBindingItemPresenter<HomeBean> {
    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView() {
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        SingleTypeBindingAdapter adapter = new SingleTypeBindingAdapter(mActivity, HomeBean.getHomeBeanList(), R.layout.item_home);
        adapter.setItemPresenter(this);
        adapter.addSingleHeaderConfig(1,R.layout.item_home_header, HomeHeaderBean.getHomeHeaderBeanList());
        adapter.setHeadDecorator(new BaseDataBindingDecorator<HomeHeaderBean>() {
            @Override
            public void decorator(BindingViewHolder holder, int position, int viewType, HomeHeaderBean data) {
                ItemHomeHeaderBinding binding =   (ItemHomeHeaderBinding) holder.getBinding();
                binding.recyclerViewHead.setLayoutManager(new GridLayoutManager(getContext(), 5));
                SingleTypeBindingAdapter adapter = new SingleTypeBindingAdapter(mActivity, ItemHomeHeaderBean.getItemHomeHeaderBeanList(), R.layout.item_home_header_service);
                binding.recyclerViewHead.setAdapter(adapter);

            }
        });
        mBinding.recyclerView.setAdapter(adapter);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    public void onItemClick(int position, HomeBean itemData) {

        switch (itemData.name) {
           /* case "内控任务":
                break;
            case "发起监督":
                IntentController.toProcessStartActivity(mActivity,1);
                break;
            case "巡查监督":
                break;
            case "质安监督":
                IntentController.toDirectListActivity(mActivity);
                break;
            case "整改记录":
                break;
            case "负面清单":
                IntentController.toNegativeListActivity(mActivity);
                break;
            case "履约监督":
                IntentController.toPerformanceHomeActivity(mActivity);
                break;*/
        }
    }


}
