package com.smartwifi.part.performance.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import com.smartwifi.R;
import com.smartwifi.databinding.ActivityPerformanceDealtListBinding;
import com.smartwifi.part.performance.contract.PerformanceDealtListContract;
import com.smartwifi.part.performance.fragment.PerformanceDealtListFragment;
import com.smartwifi.part.performance.viewmodel.PerformanceDealtListViewModel;
import com.smartwifi.utils.ToolbarUtils;
import com.smartwifi.widget.mvvm.factory.CreateViewModel;
import com.smartwifi.widget.mvvm.view.BaseMVVMActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/9
 * @Describe
 */
@CreateViewModel(PerformanceDealtListViewModel.class)
public class PerformanceDealtListActivity extends BaseMVVMActivity<PerformanceDealtListViewModel, ActivityPerformanceDealtListBinding> implements PerformanceDealtListContract.View {
    String[] titles = new String[]{"全部", "申诉", "人员新增", "人员变更", "人员撤场", "项目停复工"};

    @Override
    public int getLayoutId() {
        return R.layout.activity_performance_dealt_list;
    }


    @Override
    public void initView() {
        ToolbarUtils.initToolBar(mBinding.toolbar,this);
        int type = getIntent().getIntExtra("type", 0);
        mBinding.tvTitle.setText(type == 0 ? "待办事项" : "已办事项");
        List<Fragment> fragmentList = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            fragmentList.add(PerformanceDealtListFragment.getFragment(type, i));
        }
        mBinding.tabLayout.setFragmentViewPageData(this, titles, fragmentList);
        mBinding.tabLayout.setTabLayoutModel(TabLayout.MODE_SCROLLABLE);

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
}
