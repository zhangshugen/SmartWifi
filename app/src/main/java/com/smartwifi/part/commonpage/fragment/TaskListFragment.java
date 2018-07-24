package com.smartwifi.part.commonpage.fragment;

import android.os.Bundle;

import com.smartwifi.R;
import com.smartwifi.base.BaseListFragment;
import com.smartwifi.bean.TaskListBean;
import com.smartwifi.manager.retrofit.RetrofitJsonManager;
import com.smartwifi.part.commonpage.contract.TaskListContract;
import com.smartwifi.part.commonpage.viewmodel.TaskListViewModel;
import com.smartwifi.view.recyclerView.RefreshRecyclerNetConfig;
import com.smartwifi.widget.mvvm.factory.CreateViewModel;

import java.util.Map;

import io.reactivex.Observable;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/18
 * @Describe
 */
@CreateViewModel(TaskListViewModel.class)
public class TaskListFragment extends BaseListFragment<TaskListViewModel> implements TaskListContract.View {

    private int type;
    private String phone;

    @Override
    protected RefreshRecyclerNetConfig getRefreshRecyclerNetConfig() {
        return new RefreshRecyclerNetConfig() {
            @Override
            public Observable getNetObservable(Map<String, Object> map) {
                map.put("DoType", type);
                map.put("Identity", phone);
                return RetrofitJsonManager.getInstance().getApiService().getTaskList(map);
            }
        };
    }

    @Override
    protected int getItemLayout() {
        return R.layout.item_task_list;
    }

    @Override
    public void initView() {
        super.initView();
        type = getArguments().getInt("type");
        //  Identity=15675136495&PageSize=10&PageNum=1&DoType=0
        phone = "15111196466";
        mBinding.recyclerView.firstLoad();
        mAdapter.setItemPresenter(getViewModel());
        mAdapter.setItemDecorator(getViewModel());
    }

    public static TaskListFragment getFragment(int type) {
        TaskListFragment fragment = new TaskListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void notifyAdapter(TaskListBean bean, int itemPosition) {
        mAdapter.refresh(itemPosition);
    }

    public void setPhone(String phone) {
        this.phone = phone;
        mBinding.recyclerView.refresh();
    }
}
