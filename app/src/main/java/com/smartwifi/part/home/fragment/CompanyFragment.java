package com.smartwifi.part.home.fragment;

import android.support.v7.widget.LinearLayoutManager;

import com.smartwifi.R;
import com.smartwifi.bean.CompanyBean;
import com.smartwifi.databinding.FragmentCompanyBinding;
import com.smartwifi.part.home.viewmodel.CompanyFragmentViewModel;
import com.smartwifi.utils.IntentController;
import com.smartwifi.widget.databindingadapter.BaseBindingItemPresenter;
import com.smartwifi.widget.databindingadapter.SingleTypeBindingAdapter;
import com.smartwifi.widget.mvvm.factory.CreateViewModel;
import com.smartwifi.widget.mvvm.view.BaseMVVMFragment;

/**
 * @Author zhangshurong
 * Created by wificityios on 2018/7/10.
 */

@CreateViewModel(CompanyFragmentViewModel.class)
public class CompanyFragment extends BaseMVVMFragment<CompanyFragmentViewModel,FragmentCompanyBinding>implements BaseBindingItemPresenter<CompanyBean> {
    @Override
    public int getLayoutId() {
        return R.layout.fragment_company;
    }

    @Override
    public void initView() {
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        SingleTypeBindingAdapter adapter = new SingleTypeBindingAdapter(mActivity, CompanyBean.getCompanyBeanList(), R.layout.item_company);
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
    public void onItemClick(int position, CompanyBean itemData) {
        switch (itemData.name) {
            case "内控任务":
                break;
            case "企业广场":
                IntentController.toProcessStartActivity(mActivity,1);
                break;
            case "发起监督":
                IntentController.toDirectListActivity(mActivity);
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
                break;
        }
    }
}
