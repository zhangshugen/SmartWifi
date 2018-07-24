package com.smartwifi.base;

import com.smartwifi.R;
import com.smartwifi.databinding.FragmentBaseListBinding;
import com.smartwifi.view.recyclerView.RefreshRecyclerNetConfig;
import com.smartwifi.view.recyclerView.pagemanagestrategy.PageManageBuilder;
import com.smartwifi.widget.databindingadapter.SingleTypeBindingAdapter;
import com.smartwifi.widget.mvvm.view.BaseMVVMFragment;
import com.smartwifi.widget.mvvm.viewmodel.BaseViewModel;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/9
 * @Describe
 */

public abstract class BaseListFragment< VM extends BaseViewModel> extends BaseMVVMFragment<VM, FragmentBaseListBinding> {
    protected SingleTypeBindingAdapter mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_base_list;
    }

    @Override
    public void initView() {

        mAdapter = new SingleTypeBindingAdapter(mActivity, null, getItemLayout());
        PageManageBuilder builder = new PageManageBuilder(mActivity)
                .setAdapter(mAdapter);
        getBuilder(builder);
        mBinding.recyclerView.create(builder);
        mBinding.recyclerView.setLoadMoreAdapter(mAdapter);
        mBinding.recyclerView.setRefreshRecyclerNetConfig(getRefreshRecyclerNetConfig());
    }

    /**
     * 获取到retrofit访问api的配置
     */
    protected abstract RefreshRecyclerNetConfig getRefreshRecyclerNetConfig();

    /**
     * 设置recyclerView的配置类
     */
    protected void getBuilder(PageManageBuilder builder) {

    }

    /**
     * 获取到adapter的itemLayout
     */
    protected abstract int getItemLayout();

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

    }
}
