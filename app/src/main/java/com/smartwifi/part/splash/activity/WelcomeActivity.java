package com.smartwifi.part.splash.activity;

import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.smartwifi.R;
import com.smartwifi.base.BaseViewPagerAdapter;
import com.smartwifi.databinding.ActivityWelcomeBinding;
import com.smartwifi.databinding.ItemActivityWelcomBinding;
import com.smartwifi.part.MainView;
import com.smartwifi.part.MainViewModel;
import com.smartwifi.utils.IntentController;
import com.smartwifi.utils.ToastUtils;
import com.smartwifi.widget.mvvm.factory.CreateViewModel;
import com.smartwifi.widget.mvvm.view.BaseMVVMActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by wificityios on 2018/7/10.
 * <p>
 * http://wifi.hktfi.com/ws/wifi/findImgByOrgId.do?orgId=8a8ab0b246dc81120146dc8180ba0017
 * http://wifi.hktfi.com/ws/wifi/findDeliveryByOrgId.do?orgId=8a8ab0b246dc81120146dc8180ba0017&number=1
 * item_activity_welcom
 * http://wifi.hktfi.com/upload/plug-in/accordion/hktorgimg/20180615170002vdfQ9EsL.jpg
 */

@CreateViewModel(MainViewModel.class)
public class WelcomeActivity extends BaseMVVMActivity<MainViewModel, ActivityWelcomeBinding> implements MainView, BaseViewPagerAdapter.BaseViewPageItemPresenter<Integer> {

    @Override
    public int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    public void initView() {
        List<Integer> imageList = new ArrayList<>();
        imageList.add(R.mipmap.welcom_one);
        imageList.add(R.mipmap.welcom_two);
        imageList.add(R.mipmap.wolcom_three);
        // imageList.add("http://img.zcool.cn/community/010f87596f13e6a8012193a363df45.jpg@1280w_1l_2o_100sh.jpg");
        // imageList.add("http://img.zcool.cn/community/01f09e577b85450000012e7e182cf0.jpg@1280w_1l_2o_100sh.jpg");

        BaseViewPagerAdapter adapter = new BaseViewPagerAdapter(imageList) {
            @Override
            public View getRootView() {
                return View.inflate(WelcomeActivity.this, R.layout.item_activity_welcom, null);
            }
        };

        adapter.setItemPresenter(this);
        adapter.setItemDecorator(new BaseViewPagerAdapter.BaseViewPageItemDecorator<Integer, ItemActivityWelcomBinding>() {
            @Override
            public void decorator(ItemActivityWelcomBinding binding, int position, int viewType, Integer data) {
                Glide.with(binding.ivImage.getContext()).load(data)//
                        .thumbnail(0.55f)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)//
                        .into(binding.ivImage);
            }
        });
        mBinding.viewPage.setAdapter(adapter);
     }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    public void onViewPageItemClick(int position, Integer itemData) {
        if (position == 2) {
            IntentController.toLoginActivity(WelcomeActivity.this);
        }/* else {
            ToastUtils.showShort(itemData);
        }*/
    }

}
