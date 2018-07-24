package com.smartwifi.part.splash.activity;


import com.smartwifi.R;
import com.smartwifi.bean.GuideImage;
import com.smartwifi.databinding.ActivityGuideBinding;
import com.smartwifi.part.splash.contract.GuideContract;
import com.smartwifi.part.splash.viewmodel.GuideViewModel;
import com.smartwifi.utils.IntentController;
import com.smartwifi.utils.SPController;
import com.smartwifi.utils.UIUtils;
import com.smartwifi.widget.mvvm.factory.CreateViewModel;
import com.smartwifi.widget.mvvm.view.BaseMVVMActivity;
@CreateViewModel(GuideViewModel.class)
public class GuideActivity extends BaseMVVMActivity<GuideViewModel,ActivityGuideBinding> implements GuideContract.View {
    @Override
    public int getLayoutId() {
        return R.layout.activity_guide;
    }

    @Override
    public void initView() {
        mViewModel.getImageList();

        UIUtils.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (SPController.getWelcomeInData()) {
                    SPController.saveWelcomeInData(false);
                    IntentController.toLoginActivity(GuideActivity.this);
                }else{
                    IntentController.toHomeActivity(GuideActivity.this);
                }
            }
        },2000);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    public void returnImageData(GuideImage data) {
        /*Glide.with(mBinding.ivImage.getContext()).load(data.orgImg)//
                .thumbnail(0.55f)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)//
                .into(mBinding.ivImage);*/
    }
}
