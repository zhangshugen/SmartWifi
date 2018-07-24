package com.smartwifi.part.commonpage.activity;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.smartwifi.R;
import com.smartwifi.base.BasePageManageActivity;
import com.smartwifi.bean.CommonEditProfileBean;
import com.smartwifi.bean.EditProfileBean;
import com.smartwifi.bean.ProfileFileBean;
import com.smartwifi.constant.Constant;
import com.smartwifi.databinding.ActivityEditProfileBinding;
import com.smartwifi.event.ProfileSelectionStaffLocalEvent;
import com.smartwifi.interfaces.ChooseFileListener;
import com.smartwifi.interfaces.UploadListener;
import com.smartwifi.manager.UploadFileManager;
import com.smartwifi.part.commonpage.contract.EditProfileContract;
import com.smartwifi.part.commonpage.viewmodel.EditProfileViewModel;
import com.smartwifi.utils.ToolbarUtils;
import com.smartwifi.view.EditProfileChooseBooleanView;
import com.smartwifi.view.EditProfileChooseDateView;
import com.smartwifi.view.EditProfileChooseFileView;
import com.smartwifi.view.EditProfileChooseNumberView;
import com.smartwifi.view.EditProfileChooseTypeView;
import com.smartwifi.view.EditProfileTextView;
import com.smartwifi.view.EditProfileView;
import com.smartwifi.widget.mvvm.factory.CreateViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/9
 * @Describe
 */
@CreateViewModel(EditProfileViewModel.class)
public class EditProfileActivity extends BasePageManageActivity<EditProfileViewModel, ActivityEditProfileBinding> implements EditProfileContract.View<EditProfileBean>, UploadListener {
    private EditProfileBean mProfileBeanData;
    private String mProfileViewTag;

    @Override
    public int getLayoutId() {
        return R.layout.activity_edit_profile;
    }


    @Override
    public void initView() {
        super.initView();
        //访问网络数据
        requestNetData();
        ToolbarUtils.initToolBar(mBinding.toolbar, this);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void requestNetData() {
        mViewModel.getProfileParamsData(this);
    }
    //loading 和空页面和错误页面的显示范围
    @Override
    protected View getPageManagerView() {
        return mBinding.scContent;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

    }

    List<EditProfileView> profileView = new ArrayList<>();
    Map<String, EditProfileView> profileViewMap = new HashMap<>();

    public void pageLoadSuccess(EditProfileBean data) {
        com.orhanobut.logger.Logger.d(data.ListData.toString());
        if (data == null || data.ListData == null || data.ListData.size() == 0) {
            showEmpty("");
        }
        mProfileBeanData = data;
        for (int i = 0; i < data.ListData.size(); i++) {
            EditProfileView itemView = null;
            CommonEditProfileBean bean = data.ListData.get(i);
            switch (bean.getDEFAULT_TYPE()) {
                case Constant.COMMON_EDIT_PROFILE_TYPE.TYPE_C:
                    EditProfileChooseTypeView editProfileChooseTypeView = new EditProfileChooseTypeView(this);
                    itemView = editProfileChooseTypeView;
                    break;
                case Constant.COMMON_EDIT_PROFILE_TYPE.TYPE_N:
                    EditProfileChooseNumberView editProfileChooseNumberView = new EditProfileChooseNumberView(this);
                    itemView = editProfileChooseNumberView;
                    break;
                case Constant.COMMON_EDIT_PROFILE_TYPE.TYPE_B:
                    EditProfileChooseBooleanView editProfileChooseBooleanView = new EditProfileChooseBooleanView(this);
                    itemView = editProfileChooseBooleanView;
                    break;
                case Constant.COMMON_EDIT_PROFILE_TYPE.TYPE_T:
                    EditProfileTextView editProfileTextView = new EditProfileTextView(this);
                    editProfileTextView.setTag(bean.getFIELD_NAME());
                    itemView = editProfileTextView;
                    break;
                case Constant.COMMON_EDIT_PROFILE_TYPE.TYPE_D:
                    EditProfileChooseDateView chooseTypeViewD = new EditProfileChooseDateView(this);
                    itemView = chooseTypeViewD;
                    break;
                case Constant.COMMON_EDIT_PROFILE_TYPE.TYPE_F:
                    EditProfileChooseFileView editProfileChooseFileView = new EditProfileChooseFileView(this);
                    editProfileChooseFileView.setViewTag(bean.getFIELD_NAME());
                    editProfileChooseFileView.setChooseFileListener(new ChooseFileListener() {
                        @Override
                        public void onFile(List<ProfileFileBean> profileFileBean, String viewTag) {
                            uploadFileList(profileFileBean, viewTag);
                        }

                        @Override
                        public void onOpenFile(String viewTag) {
                            mProfileViewTag = viewTag;
                        }
                    });
                    itemView = editProfileChooseFileView;
                    break;
                default:
                    break;
            }
            itemView.init(bean);
            //profileView.add(itemView);
            profileViewMap.put(bean.getFIELD_NAME(), itemView);
            mBinding.llContent.addView(itemView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            mPageManage.showContent();
        }
    }

    private void uploadFileList(List<ProfileFileBean> profileFileBean, String viewTag) {
        this.mProfileViewTag = viewTag;
        UploadFileManager.getInstance().uploadFileBean(profileFileBean, this);
    }


    @Override
    public void showError(String message, int code) {
        mPageManage.showError(message);
    }

    @Override
    public void showLoading(String message) {
        mPageManage.showLoading("我在加载啊");
    }

    @Override
    public void showContent(EditProfileBean data) {
        pageLoadSuccess(data);
        mPageManage.showContent();
    }

    @Override
    public void showEmpty(String empty) {
        mPageManage.showEmpty(empty);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mProfileBeanData == null) return;
        EditProfileChooseFileView fileView = (EditProfileChooseFileView) profileViewMap.get(mProfileViewTag);
        fileView.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        for (EditProfileView v : profileView
                ) {
            v.onDestroy();
        }
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onUploadSuccess(List<ProfileFileBean> file) {
        EditProfileChooseFileView fileView = (EditProfileChooseFileView) profileViewMap.get(mProfileViewTag);
        fileView.addProfileFile(file);
    }

    @Override
    public void onUploadFailed() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onProfileSelectionStaffLocalEvent(ProfileSelectionStaffLocalEvent event) {
      /*  if ("BAK4".equals(event.mViewTag)) {
            //mBinding.etText.setText(event.mProfileSelectionStaffLocalBean.userName);
            EditProfileTextView view = (EditProfileTextView) profileViewMap.get("BAK9");
            view.setText(event.mProfileSelectionStaffLocalBean.superiorUnit);
        } else if ("BAK9".equals(event.mViewTag)) {
            EditProfileTextView view = (EditProfileTextView) profileViewMap.get("BAK4");
            view.setText(event.mProfileSelectionStaffLocalBean.userName);
        }*/

}

}
