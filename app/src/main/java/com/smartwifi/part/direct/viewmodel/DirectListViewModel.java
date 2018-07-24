package com.smartwifi.part.direct.viewmodel;


import com.smartwifi.bean.LaunchDirectBean;
import com.smartwifi.databinding.FragmentBaseListBinding;
import com.smartwifi.part.direct.contract.DirectListContract;
import com.smartwifi.part.direct.model.DirectListModel;
import com.smartwifi.widget.databindingadapter.BaseBindingItemPresenter;
import com.smartwifi.widget.databindingadapter.BaseDataBindingDecorator;
import com.smartwifi.widget.databindingadapter.BindingViewHolder;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/9
 * @Describe
 */

public class DirectListViewModel extends DirectListContract.ViewModel <DirectListContract.View, FragmentBaseListBinding, DirectListModel> implements BaseBindingItemPresenter<LaunchDirectBean>,BaseDataBindingDecorator<LaunchDirectBean> {
    @Override
    public void decorator(BindingViewHolder holder, int position, int viewType, LaunchDirectBean data) {

    }

    @Override
    public void onItemClick(int position, LaunchDirectBean itemData) {

    }
}
