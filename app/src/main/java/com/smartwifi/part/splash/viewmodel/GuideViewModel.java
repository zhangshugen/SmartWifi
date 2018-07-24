package com.smartwifi.part.splash.viewmodel;



import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.smartwifi.bean.GuideImage;
import com.smartwifi.databinding.ActivityGuideBinding;
import com.smartwifi.part.splash.contract.GuideContract;
import com.smartwifi.part.splash.model.GuideModel;
import com.smartwifi.widget.retrofithelper.rxsubscriber.ProgressObserver;

public class GuideViewModel extends GuideContract.ViewModel<GuideContract.View, ActivityGuideBinding, GuideModel> {
    @Override
    public void getImageList() {
        mModel.getImageList().subscribe(new ProgressObserver<GuideImage>(false, this) {
            @Override
            public void _onNext(GuideImage guideImage) {
               // mView.returnImageData(guideImage);
                mBinding.setImageData(guideImage);

              /*  Glide.with(mBinding.ivImage.getContext()).load(guideImage.orgImg)//
                        .thumbnail(0.55f)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)//
                        .into(mBinding.ivImage);*/
            }
        });
    }




}
