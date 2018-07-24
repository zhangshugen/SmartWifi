package com.smartwifi.part.commonpage.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;

import com.smartwifi.R;
import com.smartwifi.base.BaseListFragment;
import com.smartwifi.bean.ProfileSelectionStaffBean;
import com.smartwifi.bean.ProfileSelectionStaffLocalBean;
import com.smartwifi.interfaces.ProfileSelectionStaffFragmentClickListener;
import com.smartwifi.part.commonpage.viewmodel.ProfileSelectionStaffFragmentViewModel;
import com.smartwifi.view.recyclerView.RefreshRecyclerNetConfig;
import com.smartwifi.widget.databindingadapter.BaseBindingItemPresenter;
import com.smartwifi.widget.mvvm.factory.CreateViewModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/16
 * @Describe
 */
@CreateViewModel(ProfileSelectionStaffFragmentViewModel.class)
public class ProfileSelectionStaffFragment extends BaseListFragment<ProfileSelectionStaffFragmentViewModel> implements BaseBindingItemPresenter<ProfileSelectionStaffLocalBean> {

    private List<ProfileSelectionStaffLocalBean> profileSelectionStaffLocalBeans;
    private List<ProfileSelectionStaffBean> netListBean;
    private ProfileSelectionStaffFragmentClickListener profileSelectionStaffFragmentClickListener;

    @Override
    protected RefreshRecyclerNetConfig getRefreshRecyclerNetConfig() {
        return null;
    }

    @Override
    protected int getItemLayout() {
        return R.layout.item_selection_staff;
    }

    @Override
    public void initData() {
        super.initData();
        profileSelectionStaffLocalBeans = (List<ProfileSelectionStaffLocalBean>) getArguments().getSerializable("list");
        netListBean = (List<ProfileSelectionStaffBean>) getArguments().getSerializable("netListBean");
        mAdapter.refresh(profileSelectionStaffLocalBeans);
        mAdapter.setItemPresenter(this);
        mBinding.recyclerView.setAdapter(mAdapter, new LinearLayoutManager(mActivity));
    }

    public static ProfileSelectionStaffFragment getFragment(List<ProfileSelectionStaffLocalBean> data, List<ProfileSelectionStaffBean> netListBean) {
        ProfileSelectionStaffFragment fragment = new ProfileSelectionStaffFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("list", (Serializable) data);
        bundle.putSerializable("netListBean", (Serializable) netListBean);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void onItemClick(int position, ProfileSelectionStaffLocalBean itemData) {
        if (profileSelectionStaffFragmentClickListener == null) return;
        boolean addFragment = profileSelectionStaffFragmentClickListener.isAddFragment(itemData.position);
        if (addFragment) return;
        List<ProfileSelectionStaffLocalBean> list = new ArrayList<>();
        for (int i = 0; i < netListBean.size(); i++) {
            ProfileSelectionStaffBean bean = netListBean.get(i);
            ProfileSelectionStaffLocalBean localBean = setStaffBean(bean, itemData.position);
            list.add(localBean);
        }
        profileSelectionStaffFragmentClickListener.fragmentClickListener(list, itemData);
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

    private ProfileSelectionStaffLocalBean setStaffBean(ProfileSelectionStaffBean bean, int i) {
        ProfileSelectionStaffLocalBean localBean = getBean(bean, i);
        if (i == 0) {
            setStaffBean2(localBean, bean);
        } else if (i == 1) {
            setStaffBean3(localBean, bean);
        } else if (i == 2) {
            setStaffBean4(localBean, bean);
        } else {
            return null;
        }
        return localBean;
    }

    private void setStaffBean2(ProfileSelectionStaffLocalBean localBean, ProfileSelectionStaffBean netLocalBean) {
        localBean.companyName = netLocalBean.getBMNAME();
        localBean.sort = netLocalBean.getBM();
    }

    private void setStaffBean3(ProfileSelectionStaffLocalBean localBean, ProfileSelectionStaffBean bean) {
        localBean.companyName = bean.getXJBMNAME();
        localBean.sort = bean.getXJBM();
    }

    private void setStaffBean4(ProfileSelectionStaffLocalBean localBean, ProfileSelectionStaffBean bean) {
        localBean.companyName = bean.getXM();
        localBean.sort = bean.getPX();
    }

    public void setProfileSelectionStaffFragmentClickListener(ProfileSelectionStaffFragmentClickListener listener) {
        profileSelectionStaffFragmentClickListener = listener;
    }
}
