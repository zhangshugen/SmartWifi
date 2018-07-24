package com.smartwifi.part.performance.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.smartwifi.R;
import com.smartwifi.bean.PerformanceDealtListBean;
import com.smartwifi.databinding.ActivityPerformanceMatterDetailsBinding;
import com.smartwifi.databinding.ItemApprovalProcessBinding;
import com.smartwifi.part.performance.viewmodel.PerformanceMatterDetailsViewModel;
import com.smartwifi.utils.ToastUtils;
import com.smartwifi.utils.ToolbarUtils;
import com.smartwifi.utils.UIUtils;
import com.smartwifi.widget.databindingadapter.BaseDataBindingDecorator;
import com.smartwifi.widget.databindingadapter.BindingViewHolder;
import com.smartwifi.widget.databindingadapter.SingleTypeBindingAdapter;
import com.smartwifi.widget.dialogfragment.LoadingDialogFragment;
import com.smartwifi.widget.mvvm.factory.CreateViewModel;
import com.smartwifi.widget.mvvm.view.BaseMVVMActivity;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/30
 * @Describe
 */
@CreateViewModel(PerformanceMatterDetailsViewModel.class)
public class PerformanceMatterDetailsActivity extends BaseMVVMActivity<PerformanceMatterDetailsViewModel, ActivityPerformanceMatterDetailsBinding> {
    @Override
    public int getLayoutId() {
        return R.layout.activity_performance_matter_details;
    }
    /**
     * @Describe   0 待办   1已办
     */

    @Override
    public void initView() {
        ToolbarUtils.initToolBar(mBinding.toolbar,this);
        final PerformanceDealtListBean bean = (PerformanceDealtListBean) getIntent().getSerializableExtra("data");
        int activityType = getIntent().getIntExtra("activityType", 0);
        mBinding.setData(bean);
        mBinding.setView(this);
        mBinding.tvTitle.setText(activityType == 0 ? "审核界面" : "查看详情");
        mBinding.setActivityType(activityType);
        SingleTypeBindingAdapter adapter = new SingleTypeBindingAdapter(this, bean.approvalProcess, R.layout.item_approval_process);
        adapter.setItemDecorator(new BaseDataBindingDecorator() {
            @Override
            public void decorator(BindingViewHolder holder, int position, int viewType, Object data) {
                ItemApprovalProcessBinding b = (ItemApprovalProcessBinding) holder.getBinding();
                b.view.setVisibility(position == bean.approvalProcess.size() -1? View.GONE : View.VISIBLE);
            }
        });
        mBinding.recyclerViewStage.setAdapter(adapter);
        mBinding.recyclerViewStage.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

    }

    public void success() {
        final LoadingDialogFragment fragment = new LoadingDialogFragment();
        fragment.show(getSupportFragmentManager());
        UIUtils.postDelayed(new Runnable() {
            @Override
            public void run() {
                fragment.dismiss();
                ToastUtils.showShort("操作成功");
                PerformanceMatterDetailsActivity.this.finish();
            }
        }, 2000);
    }

    public void fail() {
        final LoadingDialogFragment fragment = new LoadingDialogFragment();
        fragment.show(getSupportFragmentManager());
          UIUtils.postDelayed(new Runnable() {
              @Override
              public void run() {
                  fragment.dismiss();
                  ToastUtils.showShort("操作成功");
                  PerformanceMatterDetailsActivity.this.finish();
              }
          }, 2000);
    }
}
