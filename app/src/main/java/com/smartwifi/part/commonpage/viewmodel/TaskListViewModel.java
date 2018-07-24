package com.smartwifi.part.commonpage.viewmodel;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;

import com.orhanobut.logger.Logger;
import com.smartwifi.R;
import com.smartwifi.bean.TaskListBean;
import com.smartwifi.databinding.FragmentBaseListBinding;
import com.smartwifi.databinding.ItemTaskListBinding;
import com.smartwifi.part.commonpage.contract.TaskListContract;
import com.smartwifi.part.commonpage.model.TaskListModel;
import com.smartwifi.widget.BottomMenuDialog;
import com.smartwifi.widget.databindingadapter.BaseBindingItemPresenter;
import com.smartwifi.widget.databindingadapter.BaseDataBindingDecorator;
import com.smartwifi.widget.databindingadapter.BindingViewHolder;
import com.smartwifi.widget.databindingadapter.SingleTypeBindingAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/18
 * @Describe
 */

public class TaskListViewModel extends TaskListContract.ViewModel<TaskListContract.View, FragmentBaseListBinding, TaskListModel> implements BaseBindingItemPresenter<TaskListBean>, BaseDataBindingDecorator<TaskListBean> {
    private BottomMenuDialog bottomMenuDialog;
    private TaskListBean requestApprovalBean;

    @Override
    public void onItemClick(int position, TaskListBean itemData) {

    }

    @Override
    public void openItem(TaskListBean bean, int itemPosition) {
        bean.setOpen(!bean.isOpen);
        mView.notifyAdapter(bean, itemPosition);
    }

    @Override
    public void requestApproval(TaskListBean bean, int itemPosition) {
        this.requestApprovalBean = bean;
        bottomMenuDialog = new BottomMenuDialog.Builder(mActivity).addMenu("短信提醒", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Logger.d(requestApprovalBean.SC);
            }
        }).addMenu("电话提醒", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Logger.d(requestApprovalBean.BAK0);

            }
        }).setCancelListener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create();
        bottomMenuDialog.show();
    }

    @Override
    public void decorator(BindingViewHolder holder, int position, int viewType, TaskListBean data) {
        ItemTaskListBinding binding = (ItemTaskListBinding) holder.getBinding();
        binding.tvTitle.setText("发货速度快会发的是会计法哈迪斯发神经肯定会福建安徽速度快就会发的啥空间发哈迪斯发哈速度快结婚发的啥空间发哈肯定就是就");
        if (!data.isOpen) {
            binding.tvTitle.setSingleLine(true);
            binding.tvTitle.setEllipsize(TextUtils.TruncateAt.valueOf("END"));
        } else {
            binding.tvTitle.setSingleLine(false);
        }
        binding.tvTaskStatus.setTextColor(Color.parseColor(data.STATUSCOLOR));
        List<String> list = new ArrayList<>();
        list.add(data.BAK5);
        list.add(data.PERCENT + "%");
        SingleTypeBindingAdapter adapter = new SingleTypeBindingAdapter(mActivity, list, R.layout.item_task_list_tag);
        binding.recyclerViewTag.setAdapter(adapter);
        binding.recyclerViewTag.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false));
    }


}
