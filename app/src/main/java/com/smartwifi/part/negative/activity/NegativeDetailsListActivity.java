package com.smartwifi.part.negative.activity;

import android.support.v7.widget.LinearLayoutManager;

import com.smartwifi.R;
import com.smartwifi.bean.NegativeDetailsListBean;
import com.smartwifi.databinding.ActivityNegativeDetailsListBinding;
import com.smartwifi.part.negative.viewmodel.NegativeDetailsListViewModel;
import com.smartwifi.utils.ToolbarUtils;
import com.smartwifi.widget.databindingadapter.SingleTypeBindingAdapter;
import com.smartwifi.widget.mvvm.factory.CreateViewModel;
import com.smartwifi.widget.mvvm.view.BaseMVVMActivity;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/31
 * @Describe
 */
@CreateViewModel(NegativeDetailsListViewModel.class)
public class NegativeDetailsListActivity extends BaseMVVMActivity<NegativeDetailsListViewModel,ActivityNegativeDetailsListBinding> {
    @Override
    public int getLayoutId() {
        return R.layout.activity_negative_details_list;
    }

    @Override
    public void initView() {
        String title = getIntent().getStringExtra("title");
        mBinding.setTitle(title);
        ToolbarUtils.initToolBar(mBinding.toolbar,this);
        SingleTypeBindingAdapter adapter = new SingleTypeBindingAdapter(this, NegativeDetailsListBean.getNegativeDetailsListData(),R.layout.item_negative_details_list);
        mBinding.recyclerView.setAdapter(adapter);
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

    }
}
