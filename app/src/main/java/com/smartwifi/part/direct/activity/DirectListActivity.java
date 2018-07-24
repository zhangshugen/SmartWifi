package com.smartwifi.part.direct.activity;

import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;

import com.smartwifi.R;
import com.smartwifi.part.direct.fragment.DirectListFragment;
import com.smartwifi.utils.ToolbarUtils;
import com.smartwifi.view.MultiFunctionButtonView;
import com.smartwifi.view.TabViewPageLayout;
import com.smartwifi.widget.mvvm.view.BaseMVVMActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/29
 * @Describe
 */

public class DirectListActivity extends BaseMVVMActivity {
    @BindView(R.id.tab_layout)
    TabViewPageLayout tabLayout;
    @BindView(R.id.mb_action)
    MultiFunctionButtonView mbAction;
    String[] titles = new String[]{"待办事项", "已办事项"};
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    public int getLayoutId() {
        return R.layout.layout_activity_direct_list;
    }

    @Override
    public void initView() {
        ToolbarUtils.initToolBar(toolbar, this);
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(DirectListFragment.getFragment(2));
        fragmentList.add(DirectListFragment.getFragment(1));
        tabLayout.setFragmentViewPageData(this, titles, fragmentList);
        tabLayout.setIndicator(50, 50);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    public boolean isUseDataBinding()    {
        return false;
    }

    @Override
    public boolean isUseProxy() {
        return false;
    }


}
