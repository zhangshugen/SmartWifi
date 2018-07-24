package com.smartwifi.part.commonpage.viewmodel;

import com.smartwifi.bean.EditProfileBean;
import com.smartwifi.databinding.ActivityEditProfileBinding;
import com.smartwifi.part.commonpage.activity.EditProfileActivity;
import com.smartwifi.part.commonpage.contract.EditProfileContract;
import com.smartwifi.part.commonpage.model.EditProfileModel;
import com.smartwifi.widget.retrofithelper.rxsubscriber.ProgressObserver;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/9
 * @Describe
 */

public class EditProfileViewModel extends EditProfileContract.ViewModel<EditProfileContract.View, ActivityEditProfileBinding, EditProfileModel> {
    @Override
    public void getProfileParamsData(EditProfileActivity editProfileActivity) {
        // 显示loading
        mView.showLoading("");
        mModel.getProfileParamsData().subscribe(new ProgressObserver<EditProfileBean>(false, this) {
            @Override
            public void _onNext(EditProfileBean o) {
                if (o == null || o.ListData == null || o.ListData.size() == 0) {
                    mView.showEmpty("");
                } else {
                    mView.showContent(o);
                }
            }
        });

    }
}
