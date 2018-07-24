package com.smartwifi.widget.dialogfragment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;

import com.orhanobut.logger.Logger;
import com.smartwifi.R;
import com.smartwifi.base.BaseViewPagerAdapter;
import com.smartwifi.bean.CommonEditProfileBean;
import com.smartwifi.bean.ProfileSelectionStaffBean;
import com.smartwifi.bean.ProfileSelectionStaffLocalBean;
import com.smartwifi.constant.Constant;
import com.smartwifi.databinding.FragmentProfileSelectionStaffBinding;
import com.smartwifi.databinding.PageSelectionStaffBinding;
import com.smartwifi.event.ProfileSelectionStaffLocalEvent;
import com.smartwifi.part.commonpage.fragment.ProfileSelectionStaffFragment;
import com.smartwifi.utils.CommonUtils;
import com.smartwifi.widget.databindingadapter.BaseBindingItemPresenter;
import com.smartwifi.widget.databindingadapter.SingleTypeBindingAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/16
 * @Describe
 */

public class ProfileSelectionStaffDialogFragment extends BaseDialogFragment<FragmentProfileSelectionStaffBinding> implements BaseBindingItemPresenter<ProfileSelectionStaffLocalBean>, BaseViewPagerAdapter.BaseViewPageItemDecorator<List<ProfileSelectionStaffLocalBean>, PageSelectionStaffBinding> {
    private String firstTitleString;
    private List<String> titleList;
    private List<ProfileSelectionStaffLocalBean> mData;
    private List<List<ProfileSelectionStaffLocalBean>> pageAdapterData;
    private List<ProfileSelectionStaffBean> staffBeanList;
    private List<ProfileSelectionStaffFragment> fragments;
    private List<Integer> tagList;
    private String mViewTag;
    private CommonEditProfileBean mActivityNetInfo;

    @Override
    public void initView(Context context, View rootView, AttributeSet attrs) {

    }

    @Override
    public void initData(Context context) {
        titleList = new ArrayList<>();
        tagList = new ArrayList<>();
        titleList.add(firstTitleString);
        fragments = new ArrayList<>();
        tagList.add(0);
        pageAdapterData = new ArrayList<>();
   /*     ProfileSelectionStaffFragment fragment = getStaffFragment(mData, staffBeanList);
        fragments.add(fragment);
        mBinding.tabLayout.setFragmentViewPageData(mActivity, titleList, fragments);*/
        handlerData(mData);
        pageAdapterData.add(mData);
        mBinding.tabLayout.setViewPageData(R.layout.page_selection_staff, titleList, pageAdapterData);
        mBinding.tabLayout.setPageAdapterItemDecorator(this);
        mBinding.tabLayout.setTabLayoutModel(TabLayout.MODE_SCROLLABLE);
        mBinding.tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
    }

    private void handlerData(List<ProfileSelectionStaffLocalBean> data) {

    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @NonNull
    private ProfileSelectionStaffFragment getStaffFragment(List<ProfileSelectionStaffLocalBean> data, List<ProfileSelectionStaffBean> staffBeanList) {
        ProfileSelectionStaffFragment fragment = ProfileSelectionStaffFragment.getFragment(data, staffBeanList);
        return fragment;
    }

    @Override
    public void initEvent(Context context) {

    }

    public void setFirstTitleString(String firstTitleString) {
        this.firstTitleString = firstTitleString;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_profile_selection_staff;
    }

    @Override
    public void show(FragmentManager fragmentManager) {
        super.show(fragmentManager);

    }


    @Override
    public int getGravity() {
        return Gravity.BOTTOM;
    }


    public void setData(List<ProfileSelectionStaffLocalBean> list, List<ProfileSelectionStaffBean> staffBeanList, String tag, CommonEditProfileBean mInfo) {
        this.mData = list;
        this.staffBeanList = staffBeanList;
        this.mViewTag = tag;
        this.mActivityNetInfo = mInfo;
    }

    @Override
    public void decorator(PageSelectionStaffBinding holder, int position, int viewType, List<ProfileSelectionStaffLocalBean> data) {
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        SingleTypeBindingAdapter adapter = new SingleTypeBindingAdapter(mActivity, data, R.layout.item_selection_staff);
        holder.recyclerView.setAdapter(adapter);
        adapter.setItemPresenter(ProfileSelectionStaffDialogFragment.this);
    }


    @NonNull
    private ProfileSelectionStaffLocalBean getBean(ProfileSelectionStaffBean bean, int i) {
        ProfileSelectionStaffLocalBean localBean1 = new ProfileSelectionStaffLocalBean();
        localBean1.position = i + 1;
        localBean1.job = bean.getZC();
        localBean1.userName = bean.getXM();
        localBean1.phone = bean.getIDENTITY();
        return localBean1;
    }

    private ProfileSelectionStaffLocalBean setStaffBean(ProfileSelectionStaffBean bean, int i, ProfileSelectionStaffLocalBean itemData, List<ProfileSelectionStaffLocalBean> list) {
        ProfileSelectionStaffLocalBean localBean = getBean(bean, i);

        if (i == 0 && bean.getNWNAME().equals(itemData.companyName)) {
            localBean.chooseLevel = mActivityNetInfo.getBAK1();
            setStaffBean2(localBean, bean);
        } else if (i == 1 && bean.getBMNAME().equals(itemData.companyName)) {
            setStaffBean3(localBean, bean);
        } else if (i == 2 && bean.getXJBMNAME().equals(itemData.companyName)) {
            setStaffBean4(localBean, bean);
        } else {
            localBean = null;
        }
        if (localBean != null)
            list.add(localBean);
        return localBean;
    }

    private void setStaffBean2(ProfileSelectionStaffLocalBean localBean, ProfileSelectionStaffBean netLocalBean) {
        localBean.companyName = netLocalBean.getBMNAME();
        localBean.sort = netLocalBean.getBM();
        localBean.superiorUnit = netLocalBean.getNWNAME();
    }

    private void setStaffBean3(ProfileSelectionStaffLocalBean localBean, ProfileSelectionStaffBean bean) {
        localBean.superiorUnit = bean.getBMNAME();

        localBean.companyName = bean.getXJBMNAME();
        localBean.sort = bean.getXJBM();
    }

    private void setStaffBean4(ProfileSelectionStaffLocalBean localBean, ProfileSelectionStaffBean bean) {
        localBean.superiorUnit = bean.getXJBMNAME();
        localBean.companyName = bean.getXM();
        localBean.sort = bean.getPX();
    }


    public void fragmentClickListener(List<ProfileSelectionStaffLocalBean> list, final ProfileSelectionStaffLocalBean itemData) {
        //fragments.add(getStaffFragment(list, staffBeanList));
        Collections.sort(list);
        CommonUtils.handlerList(list);

        pageAdapterData.add(list);
        mBinding.tabLayout.notifyPageAdapterDataSetChanged();
        mBinding.tabLayout.post(new Runnable() {
            @Override
            public void run() {
                mBinding.tabLayout.setCurrentItem(itemData.position + 1);

            }
        });
    }

    public boolean isAddFragment(int tag, String companyName) {
        if (tagList.contains(tag) && titleList.contains(companyName)) {
            mBinding.tabLayout.setCurrentItem(tag);
            return true;
        }
        if (tagList.contains(tag)) {
            CommonUtils.clearListOfIndex(tagList, tag);
            CommonUtils.clearListOfIndex(titleList, tag);
            CommonUtils.clearListOfIndex(pageAdapterData, tag);
        }
        tagList.add(tag);
        titleList.add(companyName);
        return false;
    }

    @Override
    public void onItemClick(int position, ProfileSelectionStaffLocalBean itemData) {
        // itemData.position 是表示点进去第几级  如:内设机构 0 -> 厅办公室1->人员3
        // 如果选择等级 在处级单位,  那么需要提交数据 主要应用于选择配合处室
        if (itemData.chooseLevel == Constant.COMMON_EDIT_PROFILE_CHOOSE_LEVEL.TYPE_CHUJI && itemData.position >= 1) {
            submitDataToServer(itemData, itemData.companyName);
            return;
        }
        if (itemData.chooseLevel == Constant.COMMON_EDIT_PROFILE_CHOOSE_LEVEL.TYPE_REN &&TextUtils.isEmpty(itemData.companyName)) {
            // 如果没有单位名称了 说明 position 位于人员了
            submitDataToServer(itemData, itemData.userName);
            return;
        }
        boolean addFragment = isAddFragment(itemData.position + 1, itemData.companyName);
        if (addFragment) return;
        List<ProfileSelectionStaffLocalBean> list = new ArrayList<>();
        for (int i = 0; i < staffBeanList.size(); i++) {
            ProfileSelectionStaffBean bean = staffBeanList.get(i);
            setStaffBean(bean, itemData.position, itemData, list);
        }
        if (list.size() == 0) return;
        fragmentClickListener(list, itemData);
    }

    private void submitDataToServer(ProfileSelectionStaffLocalBean itemData, String showName) {
        //todo  提交数据
        EventBus.getDefault().post(new ProfileSelectionStaffLocalEvent(itemData, mViewTag, showName));
        Logger.d(itemData.userName);
        dismiss();
    }
}
