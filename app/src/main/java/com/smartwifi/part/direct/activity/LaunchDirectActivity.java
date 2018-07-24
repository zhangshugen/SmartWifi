package com.smartwifi.part.direct.activity;


import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.smartwifi.R;
import com.smartwifi.bean.LaunchDirectBean;
import com.smartwifi.databinding.ActivityLaunchDirectBinding;
import com.smartwifi.event.ProcessStartSureEvent;
import com.smartwifi.part.direct.contract.LaunchDirectContract;
import com.smartwifi.part.direct.viewmodel.LaunchDirectViewModel;
import com.smartwifi.utils.DateUtils;
import com.smartwifi.utils.DialogUtils;
import com.smartwifi.utils.IntentController;
import com.smartwifi.utils.ToastUtils;
import com.smartwifi.utils.ToolbarUtils;
import com.smartwifi.utils.UIUtils;
import com.smartwifi.widget.dialogfragment.LoadingDialogFragment;
import com.smartwifi.widget.mvvm.factory.CreateViewModel;
import com.smartwifi.widget.mvvm.view.BaseMVVMActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Date;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/9
 * @Describe
 */
@CreateViewModel(LaunchDirectViewModel.class)
public class LaunchDirectActivity extends BaseMVVMActivity<LaunchDirectViewModel, ActivityLaunchDirectBinding> implements LaunchDirectContract.View {

    private AlertDialog questionDialog;
    private AlertDialog importantDialog;
    String[] questionArr = new String[]{"质量安全管理行为", "质量安全管理行为", "质量安全管理行为", "质量安全管理行为"};
    String[] importantArr = new String[]{"重要问题", "一般问题", "小问题"};
    private LaunchDirectBean data;
    private TimePickerView pvTime;
    private int activityType;

    @Override
    public int getLayoutId() {
        return R.layout.activity_launch_direct;
    }

    /**
     * @Describe activityType ==0 发起监督     1 查看监督详情的已办任务  2 查看监督详情的待办任务
     */

    @Override
    public void initView() {
        data = (LaunchDirectBean) getIntent().getSerializableExtra("data");
        activityType = getIntent().getIntExtra("activityType", 0);
        mBinding.tvTitle.setText(activityType == 0 ? "发起监督" : "监督详情");
        if (data == null)
            data = new LaunchDirectBean();
        mBinding.setData(data);
        mBinding.setView(this);
        mBinding.setActivityType(activityType);
        ToolbarUtils.initToolBar(mBinding.toolbar, this);
        if (activityType != 0) {
            mBinding.mcView.setImageList(data.file);
        }
        EventBus.getDefault().register(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onProcessStartSureEvent(ProcessStartSureEvent event) {
        data.setLocationDetails(event.title);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void success() {
        final LoadingDialogFragment fragment = new LoadingDialogFragment();
        fragment.show(getSupportFragmentManager());
        UIUtils.postDelayed(new Runnable() {
            @Override
            public void run() {
                fragment.dismiss();
                ToastUtils.showShort("操作成功");
                LaunchDirectActivity.this.finish();
            }
        }, 2000);
    }

    @Override
    public void fail() {
        LaunchDirectActivity.this.finish();
    }

    @Override
    public void chooseQuestionType() {
        if (activityType != 0) return;
        if (questionDialog == null)
            questionDialog = DialogUtils.showChooseDialog(this, "问题类型", 0, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    data.setQuestionType(questionArr[which]);
                    questionDialog.dismiss();
                }
            }, questionArr);

        questionDialog.show();
    }

    @Override
    public void chooseImportant() {
        if (activityType != 0) return;

        if (importantDialog == null)
            importantDialog = DialogUtils.showChooseDialog(this, "重要程度", 0, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    data.setImportantType(importantArr[which]);
                    importantDialog.dismiss();

                }
            }, importantArr);

        importantDialog.show();
    }

    @Override
    public void chooseDate() {
        if (activityType != 0) return;

        if (pvTime == null)
            pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
                @Override
                public void onTimeSelect(Date date, View v) {
                    data.setDate(DateUtils.formatData("yyyy-MM-dd", date.getTime()));
                    pvTime.dismiss();
                }
            }).setType(new boolean[]{true, true, true, false, false, false})
                    .build();
        pvTime.show();
    }

    @Override
    public void chooseLocation() {
        if (activityType != 0) return;
        IntentController.toProcessStartActivity(this, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mBinding.mcView.onActivityResult(requestCode, resultCode, data);
    }
}
