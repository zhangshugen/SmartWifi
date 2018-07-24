package com.smartwifi.part.commonpage.activity;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.smartwifi.R;
import com.smartwifi.base.BasePageManageActivity;
import com.smartwifi.bean.BaseListData;
import com.smartwifi.bean.CommonGridBean;
import com.smartwifi.bean.CommonGridItemBean;
import com.smartwifi.databinding.ActivityGridTypeBinding;
import com.smartwifi.databinding.ItemGridTypeBinding;
import com.smartwifi.part.commonpage.contract.GridTypeContract;
import com.smartwifi.part.commonpage.viewmodel.GridTypeViewModel;
import com.smartwifi.utils.DisplayUtils;
import com.smartwifi.view.recyclerView.itemDecoration.GridSpacingItemDecoration;
import com.smartwifi.widget.databindingadapter.BaseDataBindingDecorator;
import com.smartwifi.widget.databindingadapter.BindingViewHolder;
import com.smartwifi.widget.databindingadapter.SingleTypeBindingAdapter;
import com.smartwifi.widget.mvvm.factory.CreateViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/11
 * @Describe
 */
@CreateViewModel(GridTypeViewModel.class)
public class GridTypeActivity extends BasePageManageActivity<GridTypeViewModel, ActivityGridTypeBinding> implements GridTypeContract.View<BaseListData<CommonGridBean>>, BaseDataBindingDecorator<CommonGridBean> {

    private SingleTypeBindingAdapter adapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_grid_type;
    }

    @Override
    public void initData() {
        mPageManage.showContent();
        List<CommonGridBean> list = new ArrayList<>();
        CommonGridBean bean = new CommonGridBean();
        bean.title = "我是胖胖啊";
        bean.list = new ArrayList<>();
        CommonGridItemBean bean1 = new CommonGridItemBean();
        bean1.title = "我是胖胖啊";
        bean1.src = R.mipmap.ic_launcher;
        bean.list.add(bean1);
        bean.list.add(bean1);
        bean.list.add(bean1);
        bean.list.add(bean1);
        bean.list.add(bean1);
        bean.list.add(bean1);
        bean.list.add(bean1);
        list.add(bean);
        list.add(bean);
        list.add(bean);
        list.add(bean);
        list.add(bean);
        list.add(bean);
        list.add(bean);
        list.add(bean);
        list.add(bean);
        list.add(bean);
        adapter = new SingleTypeBindingAdapter(this, list, R.layout.item_grid_type);
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mBinding.recyclerView.setAdapter(adapter);
        adapter.setItemDecorator(this);

    }

    @Override
    public void initEvent() {

    }

    @Override
    public void showError(String message, int code) {
        mPageManage.showError(message);
    }

    @Override
    public void showLoading(String message) {
        mPageManage.showLoading(message);

    }

    @Override
    public void showContent(BaseListData<CommonGridBean> data) {
        adapter.refresh(data.ListData);
        mPageManage.showContent();

    }

    @Override
    public void showEmpty(String empty) {
        mPageManage.showEmpty(empty);

    }

    @Override
    protected void requestNetData() {
        mViewModel.getGridBean();
    }

    @Override
    protected View getPageManagerView() {
        return mBinding.recyclerView;
    }

    @Override
    public void decorator(BindingViewHolder holder, int position, int viewType, CommonGridBean data) {
        ItemGridTypeBinding binding = (ItemGridTypeBinding) holder.getBinding();
        binding.recyclerView.setLayoutManager(new GridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false));
        binding.recyclerView.addItemDecoration(new GridSpacingItemDecoration(4, DisplayUtils.px2dip(8), false));
        adapter = new SingleTypeBindingAdapter(this, data.list, R.layout.item_category_grid_type);
        binding.recyclerView.setAdapter(adapter);
    }
}
