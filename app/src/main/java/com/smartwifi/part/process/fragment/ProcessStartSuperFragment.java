package com.smartwifi.part.process.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;

import com.smartwifi.R;
import com.smartwifi.bean.ProcessStartBean;
import com.smartwifi.bean.ProcessStartSwitchFragmentEvent;
import com.smartwifi.bean.ProcessStartTitleBean;
import com.smartwifi.databinding.FragmentSuperProcessStartBinding;
import com.smartwifi.widget.databindingadapter.BaseBindingItemPresenter;
import com.smartwifi.widget.databindingadapter.SingleTypeBindingAdapter;
import com.smartwifi.widget.mvvm.view.BaseMVVMFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;


/**
 * @Author zhangshurong
 * @CreateTime 2018/7/11
 * @Describe
 */

public class ProcessStartSuperFragment extends BaseMVVMFragment implements BaseBindingItemPresenter<ProcessStartBean> {
    private ProcessStartFragment mContent;
    private List<String> tags;
    private int type;
    private List<ProcessStartFragment> fragmentQueue = new ArrayList<>();
    List<String> titleList = new ArrayList<>();
    private FragmentSuperProcessStartBinding mBiniding;
    private SingleTypeBindingAdapter headAdapter;
    private int typeId;

    public void initData() {
        mBiniding = DataBindingUtil.bind(mRootView);
        mBiniding.setView(this);
        type = getArguments().getInt("type");
        tags = new ArrayList<>();
        EventBus.getDefault().register(this);
        typeId = 0;
        ProcessStartBean bean = new ProcessStartBean();
        bean.intentPosition = typeId;
        bean.type = type;
        ProcessStartFragment fragment = getNetDiskFragment(bean);
        tags.add("");
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fl_content, fragment, tags.get(0)).commit();
        mContent = fragment;
        fragmentQueue.add(fragment);
        //headAdapter = new SingleTypeBindingAdapter(mActivity, titleList, R.layout.item_process_start_super_title);
        // mBiniding.recyclerViewHead.setAdapter(headAdapter);
        //mBiniding.recyclerViewHead.setLayoutManager(new GridLayoutManager(mContext, 3, LinearLayoutManager.VERTICAL, false));
        //headAdapter.setItemPresenter(this);
    }

    @Override
    public void initEvent() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_super_process_start;
    }

    @Override
    public void initView() {
    }


    public static ProcessStartSuperFragment getSingleton(int type, String id) {
        ProcessStartSuperFragment fragment = new ProcessStartSuperFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        bundle.putString("id", id);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void switchFragment(ProcessStartFragment from, ProcessStartFragment to, int position, boolean isRemoveTag) {
        if (mContent != to) {
            mContent = to;
            FragmentManager fragmentManager = getChildFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            if (!to.isAdded()) { // 先判断是否被add过
                transaction.hide(from)
                        .add(R.id.fl_content, to, tags.get(position)).commit(); // 隐藏当前的fragment，add下一个到Activity中
                fragmentQueue.add(to);
                //重点 需要将fragment的事务加入到返回栈当中 不然在获取返回栈列表时，是没有这个fragment的
                transaction.addToBackStack(null);
            } else {
                to.setListData();
                transaction.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
                //删除最后一个
                fragmentQueue.remove(from);
                //删除最后一个
                if (isRemoveTag) {
                    tags.remove(position + 1);
                    if (tags.size() <= 3) {
                        StringBuilder builder = new StringBuilder();
                        for (int i = 0; i < tags.size(); i++) {
                            if (TextUtils.isEmpty(tags.get(i)))
                                continue;
                            builder.append(tags.get(i) + " > ");
                        }
                        mBiniding.tvHead.setText(builder.toString());
                    }
                }

            }
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNetDiskSwitchFragmentEvent(ProcessStartSwitchFragmentEvent event) {
        tags.add(event.itemData.name);
        ProcessStartTitleBean bean = new ProcessStartTitleBean();
        bean.id = event.itemData.intentPosition;
        bean.title = event.itemData.name;
        // headAdapter.add(bean);

        if (event.itemData.intentPosition <= 3) {
            String text = mBiniding.tvHead.getText().toString();
         /*   if (event.itemData.intentPosition == 3) {

                mBiniding.tvHead.setText(text + event.itemData.name);
            } else { }*/
            mBiniding.tvHead.setText(text + event.itemData.name + " > ");

        }
        switchFragment(mContent, getNetDiskFragment(event.itemData), tags.size() - 1, true);
    }

    ProcessStartFragment getNetDiskFragment(ProcessStartBean bean) {
        ProcessStartFragment fragment = new ProcessStartFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("itemData", bean);
        bundle.putSerializable("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    public boolean onBackPressed() {
        if (fragmentQueue.size() == 1)
            return false;

        return true;
    }

    public String switchFragment() {
        if (tags.size() < 2) return "";
        int index = tags.size() - 2;
        String title = tags.get(index);
        ProcessStartFragment processStartFragment = fragmentQueue.get(fragmentQueue.size() - 2);
        switchFragment(mContent, processStartFragment, index, true);
        FragmentManager fragmentManager = getChildFragmentManager();
        processStartFragment.setItemDataIntentPosition(-1);
        return title;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        tags.clear();
        EventBus.getDefault().unregister(this);
    }

  /*  @OnClick(R.id.tv_cancel)
    public void cancel() {
       *//* AudioRecordingDialogFragment fragment = new AudioRecordingDialogFragment();
        fragment.show(getChildFragmentManager());*//*
        mActivity.finish();
    }*/

    public void resetChooseTitle() {
        if (fragmentQueue.size() == 1) return;
        mBiniding.tvHead.setText("");
        for (int i = fragmentQueue.size() - 1; i >= 1; i--) {
            tags.remove(i);
            fragmentQueue.remove(i);
        }
        emptyBackStackNow();
        switchFragment(mContent, fragmentQueue.get(0), 0, false);
    }

    public void emptyBackStackNow() {
        FragmentManager fragmentManager = getChildFragmentManager();

        int count = fragmentManager.getBackStackEntryCount();
        for (int i = 0; i < count; i++) {
            fragmentManager.popBackStackImmediate();
        }
    }

    @Override
    public void onItemClick(int i, ProcessStartBean processStartBean) {

    }

    @Override
    public boolean isUseProxy() {
        return false;
    }

    @Override
    protected boolean isUseDataBinding() {
        return false;
    }
}
