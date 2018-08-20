package com.smartwifi.part.home.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;

import com.smartwifi.R;
import com.smartwifi.bean.MineBean;
import com.smartwifi.databinding.FragmentMineBinding;
import com.smartwifi.part.home.viewmodel.MineFragmentViewModel;
import com.smartwifi.part.user.activity.LoginActivity;
import com.smartwifi.widget.databindingadapter.BaseBindingItemPresenter;
import com.smartwifi.widget.databindingadapter.SingleTypeBindingAdapter;
import com.smartwifi.widget.mvvm.factory.CreateViewModel;
import com.smartwifi.widget.mvvm.view.AppActivityManager;
import com.smartwifi.widget.mvvm.view.BaseMVVMFragment;

/**
 * @CreateTime 2018/7/9
 * @Describe
 */

@CreateViewModel(MineFragmentViewModel.class)
public class MineFragment extends BaseMVVMFragment<MineFragmentViewModel,FragmentMineBinding> implements BaseBindingItemPresenter<MineBean> {
    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initView() {
        //设置布局管理器
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        SingleTypeBindingAdapter adapter = new SingleTypeBindingAdapter(mActivity, MineBean.getMineBeanList(), R.layout.item_mine);
        adapter.setItemPresenter(this);
        mBinding.recyclerView.setAdapter(adapter);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    public void onItemClick(int position, MineBean itemData) {
        switch (itemData.name) {
         /*   case "内控任务":
                break;
            case "企业广场":
                IntentController.toProcessStartActivity(mActivity,1);
                break;
            case "发起监督":
                IntentController.toDirectListActivity(mActivity);
                break;
            case "巡查监督":
                break;
            case "质安监督":
                IntentController.toDirectListActivity(mActivity);
                break;
            case "整改记录":
                break;
            case "负面清单":
                IntentController.toNegativeListActivity(mActivity);
                break;
            case "履约监督":
                IntentController.toPerformanceHomeActivity(mActivity);
                break;*/
            case "更换号码":
                Toast.makeText(getContext(),"更换号码",Toast.LENGTH_LONG).show();
                break;
            case "绑定账号":
                Toast.makeText(getContext(),"绑定账号",Toast.LENGTH_LONG).show();
                break;
            case "推荐好友":
                Toast.makeText(getContext(),"推荐好友",Toast.LENGTH_LONG).show();
                break;
            case "检查更新":
                Toast.makeText(getContext(),"检查更新",Toast.LENGTH_LONG).show();
                break;
            case "服务协议":
                Toast.makeText(getContext(),"服务协议",Toast.LENGTH_LONG).show();
                break;
            case "关于我们":
                Toast.makeText(getContext(),"关于我们",Toast.LENGTH_LONG).show();
                break;
            case "退出登录":
                Toast.makeText(getContext(),"退出登录",Toast.LENGTH_LONG).show();
                Intent intent = new Intent();
                //Fragment跳转的Activity
                intent.setClass(getActivity(), LoginActivity.class);
                startActivity(intent);
                AppActivityManager.getAppActivityManager().finishAllActivity();
                break;
        }
    }
}
