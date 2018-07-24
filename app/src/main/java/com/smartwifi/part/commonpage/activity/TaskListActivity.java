package com.smartwifi.part.commonpage.activity;

import android.view.View;

import com.smartwifi.R;
import com.smartwifi.base.BaseApplication;
import com.smartwifi.databinding.ActivityTaskListBinding;
import com.smartwifi.part.commonpage.fragment.TaskListFragment;
import com.smartwifi.part.commonpage.viewmodel.TaskListViewModel;
import com.smartwifi.utils.ToolbarUtils;
import com.smartwifi.widget.mvvm.view.BaseMVVMActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/18
 * @Describe
 */
public class TaskListActivity extends BaseMVVMActivity<TaskListViewModel, ActivityTaskListBinding> {
    String[] titles = new String[]{"基本信息", "任务动态"};
    private List<TaskListFragment> fragments;

    @Override
    public int getLayoutId() {
        return R.layout.activity_task_list;
    }

    @Override
    public void initView() {

    }

    @Override
    public boolean isUseProxy() {
        return false;
    }

    @Override
    public void initData() {
        ToolbarUtils.initToolBar(mBinding.toolbar, this);
        fragments = new ArrayList<>();
        fragments.add(TaskListFragment.getFragment(0));
        fragments.add(TaskListFragment.getFragment(1));
        mBinding.tabLayout.setFragmentViewPageData(this, titles, fragments);
        mBinding.tabLayout.setIndicator(60, 60);
        mBinding.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = mBinding.et.getText().toString();
                fragments.get(0).setPhone(s);
                fragments.get(1).setPhone(s);
            }
        });
    }

    @Override
    public void initEvent() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fragments.clear();
        fragments = null;
      /*  RefWatcher refWatcher = BaseApplication.getRefWatcher(this);
        refWatcher.watch(this);*/
    }
}
