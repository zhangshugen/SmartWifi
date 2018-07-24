package com.smartwifi.part.negative.activity;

import android.support.v7.widget.LinearLayoutManager;

import com.smartwifi.R;
import com.smartwifi.bean.NegativeListBean;
import com.smartwifi.databinding.ActivityNegativeListBinding;
import com.smartwifi.part.negative.viewmodel.NegativeListViewModel;
import com.smartwifi.utils.IntentController;
import com.smartwifi.utils.ToolbarUtils;
import com.smartwifi.widget.databindingadapter.BaseBindingItemPresenter;
import com.smartwifi.widget.databindingadapter.SingleTypeBindingAdapter;
import com.smartwifi.widget.mvvm.factory.CreateViewModel;
import com.smartwifi.widget.mvvm.view.BaseMVVMActivity;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/9
 * @Describe
 */
@CreateViewModel(NegativeListViewModel.class)
public class NegativeListActivity extends BaseMVVMActivity<NegativeListViewModel,ActivityNegativeListBinding> implements BaseBindingItemPresenter<NegativeListBean> {
    @Override
    public int getLayoutId() {
        return R.layout.activity_negative_list;
    }

    @Override
    public void initView() {
        ToolbarUtils.initToolBar(mBinding.toolbar,this);
        SingleTypeBindingAdapter adapter = new SingleTypeBindingAdapter(this, NegativeListBean.getNegativeListData(),R.layout.item_negative_list);
        mBinding.recyclerView.setAdapter(adapter);
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setItemPresenter(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    public void onItemClick(int position, NegativeListBean itemData) {
        IntentController.toNegativeDetailsListActivity(this,position,itemData.title);
    }
}
