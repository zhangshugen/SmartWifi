package com.smartwifi.part.process.fragment;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;

import com.smartwifi.R;
import com.smartwifi.bean.ProcessStartBean;
import com.smartwifi.bean.ProcessStartSwitchFragmentEvent;
import com.smartwifi.databinding.FragmentProcessStartBinding;
import com.smartwifi.event.ProcessStartSureEvent;
import com.smartwifi.utils.IntentController;
import com.smartwifi.utils.ToastUtils;
import com.smartwifi.widget.databindingadapter.BaseBindingItemPresenter;
import com.smartwifi.widget.databindingadapter.SingleTypeBindingAdapter;
import com.smartwifi.widget.mvvm.view.BaseMVVMFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;


/**
 * @Author zhangshurong
 * @CreateTime 2018/7/9
 * @Describe
 */

public class ProcessStartFragment extends BaseMVVMFragment implements BaseBindingItemPresenter<ProcessStartBean> {

    private SingleTypeBindingAdapter mAdapter;
    private List<ProcessStartBean> dataList;
    private FragmentProcessStartBinding binding;
    private ProcessStartBean previousItemDate;
    private int projectPosition;
    private int type;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_process_start;
    }

    @Override
    public void initView() {
        binding = DataBindingUtil.bind(mRootView);
        previousItemDate = (ProcessStartBean) getArguments().getSerializable("itemData");
        type = getArguments().getInt("type");
        projectPosition = previousItemDate.projectPosition;
        mAdapter = new SingleTypeBindingAdapter(mActivity, dataList, R.layout.item_process_start);
        mAdapter.setItemPresenter(this);
        binding.recyclerView.setAdapter(mAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
    }

    @Override
    public void initData() {
        mAdapter.refresh(ProcessStartBean.getProcessStartBeanList(projectPosition));
    }


    @Override
    public void initEvent() {

    }


    public void setDataList(List<ProcessStartBean> dataList) {
        this.dataList = dataList;
    }

    @Override
    public void onItemClick(int position, ProcessStartBean itemData) {

        if (itemData.isLast) {
            ToastUtils.showShort("已到最后一层");
        } else {
            projectPosition = ++previousItemDate.projectPosition;
            itemData.title = TextUtils.isEmpty(previousItemDate.title) ? itemData.title : previousItemDate.title + "-" + itemData.title;
            EventBus.getDefault().post(new ProcessStartSwitchFragmentEvent(itemData, projectPosition));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAdapter.getListData().clear();
    }

    public void setItemDataIntentPosition(int itemDataIntentPosition) {
        previousItemDate.projectPosition = previousItemDate.projectPosition - 1;
    }

    public void sure(ProcessStartBean itemData) {
        String title = TextUtils.isEmpty(previousItemDate.title) ? itemData.title : previousItemDate.title + "-" + itemData.title;
        EventBus.getDefault().postSticky(new ProcessStartSureEvent(title));
        if (type == 1)
            // 如果是首页进来选择工序,发布监督页面没有被打开, 如果是从发布监督页面重新选择工序,那么就发event
            IntentController.toLaunchDirectActivity(mActivity, null, 0);
        mActivity.finish();
    }

    @Override
    public boolean isUseProxy() {
        return false;
    }

    @Override
    protected boolean isUseDataBinding() {
        return false;
    }

    public void setListData() {
        List<ProcessStartBean> list = mAdapter.getListData();
        List<ProcessStartBean> list1 = new ArrayList<>();
        for (ProcessStartBean b :
                list) {
            b.projectPosition = b.projectPosition - 1;
            list1.add(b);
        }
        mAdapter.refresh(list1);
    }
}
