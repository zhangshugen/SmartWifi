package com.smartwifi.part.process;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.smartwifi.R;
import com.smartwifi.base.BaseFragmentAdapter;
import com.smartwifi.event.ResetChooseTitleEvent;
import com.smartwifi.part.process.fragment.ProcessStartSuperFragment;
import com.smartwifi.utils.ToolbarUtils;
import com.smartwifi.widget.mvvm.view.BaseMVVMActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * @Author zhangshurong
 * @CreateTime 2018/7/9
 * @Describe
 */

public class ProcessStartActivity extends BaseMVVMActivity {
    public static final int PROCESS_SAVE = 2;
    public static final int PROCESS_LIST = 1;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.viewPage)
    ViewPager viewPage;

    private ProcessStartSuperFragment fileFragment;
    private int type;
    private String id;
    private List<Fragment> mFragmentList;
    private BaseFragmentAdapter pagerAdapter;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public int getLayoutId() {

        return R.layout.activity_process_start;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        fileFragment.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void initView() {
        long l = System.currentTimeMillis();
        type = getIntent().getIntExtra("type", PROCESS_LIST);
        mFragmentList = new ArrayList<>();
        fileFragment = ProcessStartSuperFragment.getSingleton(type, id);
        mFragmentList.add(fileFragment);
        pagerAdapter = new BaseFragmentAdapter(getSupportFragmentManager(), mFragmentList);
        viewPage.setAdapter(pagerAdapter);
        ToolbarUtils.initToolBar(toolbar, this);
        EventBus.getDefault().register(this);

    }

    @Override
    public void initData() {

    }

    @Override
    public void onBackPressed() {
        if (!fileFragment.onBackPressed()) {
            super.onBackPressed();
        }else {
            fileFragment.switchFragment();
        }
    }

    @Override
    public void initEvent() {

    }

    @Override
    public boolean isUseProxy() {
        return false;
    }

    @Override
    public boolean isUseDataBinding() {
        return false;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onResetChooseTitleEvent(ResetChooseTitleEvent event) {
        Intent intent = new Intent();
        intent.putExtra("PROJECT", (Bundle) event.project);
        setResult(RESULT_OK, intent);
        finish();
    }
}
