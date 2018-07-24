package com.smartwifi.part.performance.activity;

import android.content.Intent;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;

import com.tencent.smtt.sdk.QbSdk;
import com.smartwifi.R;
import com.smartwifi.bean.PerformanceHomeBean;
import com.smartwifi.databinding.ActivityPerformanceHomeBinding;
import com.smartwifi.interfaces.DownLoadListener;
import com.smartwifi.manager.DownloadManage;
import com.smartwifi.part.performance.contract.PerformanceHomeContract;
import com.smartwifi.part.performance.viewmodel.PerformanceHomeViewModel;
import com.smartwifi.utils.IntentController;
import com.smartwifi.utils.ToolbarUtils;
import com.smartwifi.widget.databindingadapter.BaseBindingItemPresenter;
import com.smartwifi.widget.databindingadapter.MultiTypeBindingAdapter;
import com.smartwifi.widget.mvvm.factory.CreateViewModel;
import com.smartwifi.widget.mvvm.view.BaseMVVMActivity;

import java.io.File;
import java.util.List;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/9
 * @Describe
 */
@CreateViewModel(PerformanceHomeViewModel.class)
public class PerformanceHomeActivity extends BaseMVVMActivity<PerformanceHomeViewModel, ActivityPerformanceHomeBinding> implements PerformanceHomeContract.View, BaseBindingItemPresenter<PerformanceHomeBean> {


    @Override
    public int getLayoutId() {
        return R.layout.activity_performance_home;
    }


    @Override
    public void initView() {
        ToolbarUtils.initToolBar(mBinding.toolbar, this);
        List<PerformanceHomeBean> performanceHomeBeanList = PerformanceHomeBean.getPerformanceHomeBeanList();
        MultiTypeBindingAdapter adapter = new MultiTypeBindingAdapter<PerformanceHomeBean>(this, performanceHomeBeanList) {
            @Override
            public int getMyItemViewType(int position, ArrayMap multiTypeMap, PerformanceHomeBean data) {
                return data.type;
            }
        };
        adapter.addItemViewType(1, R.layout.item_performance_home);
        adapter.addItemViewType(0, R.layout.item_performance_home_title);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    }

    @Override
    public void onItemClick(int position, PerformanceHomeBean itemData) {

        switch (itemData.name) {

            case "代办事项":
                IntentController.toPerformanceDealtListActivity(this, 0);
                break;
            case "已办事项":
                IntentController.toPerformanceDealtListActivity(this, 1);
                break;
            case "项目人员违约情况":
            case "特殊岗位违约情况":
            case "履约预扣分":
            case "人员变动详情":
            case "人员变动统计":
            case "1到4月总预扣分":
                DownloadManage.getInstance().downloadFile(this, itemData.url, itemData.name, new DownLoadListener() {
                    @Override
                    public void onSuccess(File file) {
                        QbSdk.openFileReader(PerformanceHomeActivity.this, file.getAbsolutePath(), null, null);
                    }

                    @Override
                    public void onFailed() {
                    }
                });

                break;
        }
    }
}
