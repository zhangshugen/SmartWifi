package com.smartwifi.part.direct.fragment;

import android.os.Bundle;

import com.smartwifi.R;
import com.smartwifi.base.BaseListFragment;
import com.smartwifi.bean.LaunchDirectBean;
import com.smartwifi.manager.retrofit.RetrofitJsonManager;
import com.smartwifi.part.direct.contract.DirectListContract;
import com.smartwifi.part.direct.viewmodel.DirectListViewModel;
import com.smartwifi.utils.IntentController;
import com.smartwifi.view.recyclerView.RefreshRecyclerNetConfig;
import com.smartwifi.widget.databindingadapter.BaseBindingItemPresenter;
import com.smartwifi.widget.mvvm.factory.CreateViewModel;

import java.util.Map;

import io.reactivex.Observable;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/18
 * @Describe
 */
@CreateViewModel(DirectListViewModel.class)
public class DirectListFragment extends BaseListFragment<DirectListViewModel> implements DirectListContract.View, BaseBindingItemPresenter<LaunchDirectBean> {

    private int type;

    @Override
    protected RefreshRecyclerNetConfig getRefreshRecyclerNetConfig() {
        return new RefreshRecyclerNetConfig() {
            @Override
            public Observable getNetObservable(Map<String, Object> map) {
             /*   map.put("Type", type);
                map.put("UserId", SpUtil.getString(CONSTUtils.USER, CONSTUtils.USER_ID, ""));*/
                return RetrofitJsonManager.getInstance().getApiService().getEditProfileTypeCategory("");
            }
        };
    }

    @Override
    protected int getItemLayout() {
        return R.layout.item_direct_list;
    }

    @Override
    public void initView() {
        super.initView();
        type = getArguments().getInt("type");
   //     mBinding.recyclerView.firstLoad();
       mAdapter.refresh(LaunchDirectBean.getDirectListBean());
        mAdapter.setItemPresenter(this);
        mAdapter.setItemDecorator(getViewModel());
        mBinding.recyclerView.setRefreshLoaderMore(false,false);
    }

    public static DirectListFragment getFragment(int type) {
        DirectListFragment fragment = new DirectListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void onItemClick(int position, LaunchDirectBean itemData) {
        IntentController.toLaunchDirectActivity(mActivity,itemData,type);
    }
}
