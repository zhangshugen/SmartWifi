package com.smartwifi.view;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.smartwifi.R;
import com.smartwifi.base.BaseCustomView;
import com.smartwifi.base.BaseFragmentAdapter;
import com.smartwifi.base.BaseViewPagerAdapter;
import com.smartwifi.databinding.ViewTabViewPageBinding;
import com.smartwifi.utils.CommonUtils;

import java.util.List;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/9
 * @Describe
 */

public class TabViewPageLayout extends BaseCustomView<ViewTabViewPageBinding> {

    private BaseViewPagerAdapter mAdapter;
    private TabLayout tabLayout;
    private BaseFragmentAdapter adapter;

    public TabViewPageLayout(Context context) {
        super(context);
    }

    public TabViewPageLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void initView(Context context, View rootView, AttributeSet attrs) {

    }

    @Override
    protected void initView(Context context, AttributeSet attrs) {
        tabLayout = new TabLayout(context, attrs);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mBinding.llContent.addView(tabLayout, 0, params);
    }

    @Override
    public void initData(Context context) {

    }

    @Override
    public void initEvent(Context context) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.view_tab_view_page;
    }

    public TabLayout getTabLayout() {
        return tabLayout;
    }

    public ViewPager getViewPager() {
        return mBinding.viewPage;
    }

    public void setCurrentItem(int currentItem) {
        mBinding.viewPage.setCurrentItem(currentItem);
    }


    public void setTabLayoutVisibility(boolean f) {
        if (tabLayout != null)
            tabLayout.setVisibility(f ? VISIBLE : GONE);
    }

    public void setViewPageData(final int layoutId, final List<String> titleList, List dataList) {
        mAdapter = new BaseViewPagerAdapter(dataList) {
            @Override
            public View getRootView() {
                return View.inflate(mContext, layoutId, null);
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titleList.get(position);
            }
        };
        mBinding.viewPage.setAdapter(mAdapter);
        tabLayout.setupWithViewPager(mBinding.viewPage);
    }

    public void setFragmentViewPageData(FragmentActivity appCompatActivity, List<String> titleList, List dataList) {
        adapter = new BaseFragmentAdapter(appCompatActivity.getSupportFragmentManager(), titleList, dataList);
        mBinding.viewPage.setAdapter(adapter);
        tabLayout.setupWithViewPager(mBinding.viewPage);
    }

    public void setFragmentViewPageData(FragmentActivity appCompatActivity, String[] titles, List dataList) {
        adapter = new BaseFragmentAdapter(appCompatActivity.getSupportFragmentManager(), titles, dataList);
        mBinding.viewPage.setAdapter(adapter);
        tabLayout.setupWithViewPager(mBinding.viewPage);
    }

    public void setIndicator(int l, int r) {
        CommonUtils.setIndicator(getTabLayout(), l, r);
    }

    public void setTabLayoutModel(int model) {
        getTabLayout().setTabMode(model);
    }

    public void setTabGravity(int gravityFill) {
        getTabLayout().setTabGravity(gravityFill);
    }


    public void setPageAdapterItemDecorator(BaseViewPagerAdapter.BaseViewPageItemDecorator decorator) {
        if (mAdapter != null)
            mAdapter.setItemDecorator(decorator);
    }

    public void setPageAdapterItemPresenter(BaseViewPagerAdapter.BaseViewPageItemPresenter presenter) {
        if (mAdapter != null)
            mAdapter.setItemPresenter(presenter);
    }

    public void notifyPageAdapterDataSetChanged() {
        if (mAdapter != null)
            mAdapter.notifyDataSetChanged();

    }

    public void notifyFragmentAdapterDataSetChanged() {
        if (adapter != null)
            adapter.notifyDataSetChanged();

    }
}
