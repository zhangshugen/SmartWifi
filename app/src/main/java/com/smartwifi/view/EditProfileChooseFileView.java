package com.smartwifi.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;

import com.orhanobut.logger.Logger;
import com.tencent.smtt.sdk.QbSdk;
import com.smartwifi.R;
import com.smartwifi.bean.CommonEditProfileBean;
import com.smartwifi.bean.ProfileFileBean;
import com.smartwifi.constant.Constant;
import com.smartwifi.databinding.ViewProfileChooseFileBinding;
import com.smartwifi.event.AudioRecordingStateEvent;
import com.smartwifi.interfaces.ChooseFileListener;
import com.smartwifi.interfaces.DownLoadListener;
import com.smartwifi.interfaces.ImagePickerListener;
import com.smartwifi.manager.AudioManager;
import com.smartwifi.manager.DownloadManage;
import com.smartwifi.manager.VideoManager;
import com.smartwifi.utils.FileUtils;
import com.smartwifi.utils.IntentController;
import com.smartwifi.utils.PickFileTools;
import com.smartwifi.utils.ToastUtils;
import com.smartwifi.utils.UIUtils;
import com.smartwifi.widget.BottomMenuDialog;
import com.smartwifi.widget.databindingadapter.BaseBindingItemPresenter;
import com.smartwifi.widget.databindingadapter.SingleTypeBindingAdapter;
import com.smartwifi.widget.mvvm.view.AppActivityManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/11
 * @Describe
 */

public class EditProfileChooseFileView extends EditProfileView<ViewProfileChooseFileBinding> implements BaseBindingItemPresenter<ProfileFileBean> {
    public static final int TYPE_IMAGE = 1;
    public static final int TYPE_FILE = 2;
    public static final int TYPE_VIDEO = 3;
    public static final int TYPE_RECORD = 4;
    private BottomMenuDialog bottomMenuDialog;
    private SingleTypeBindingAdapter adapter;
    private ChooseFileListener listener;
    private PickFileTools pickFileTools;
    private String viewTag;

    public EditProfileChooseFileView(Context context) {
        super(context);
    }

    public EditProfileChooseFileView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void initData(Context context) {

        mBinding.setView(this);
        adapter = new SingleTypeBindingAdapter(mContext, null, R.layout.item_editprofile_choose_file);
        mBinding.recyclerView.setAdapter(adapter);
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter.setItemPresenter(this);
        EventBus.getDefault().register(this);
    }

    @Override
    public void init(CommonEditProfileBean info) {
        super.init(info);
        mBinding.setInfo(mInfo);
    }

    @Override
    public void initEvent(Context context) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.view_profile_choose_file;
    }

    public void deleteFile(ProfileFileBean bean, int position) {
        adapter.delete(position);

    }

    @Override
    protected void initView(Context context, AttributeSet attrs) {
        pickFileTools = PickFileTools.init(AppActivityManager.getAppActivityManager().currentActivity());
    }

    public void clickChooseView() {
        Logger.d(mInfo.getDISPLAY_FLAG().equals(Constant.TYPE_FALSE) + "");
        if (listener == null) return;

        if (bottomMenuDialog == null)
            bottomMenuDialog = new BottomMenuDialog.Builder(mContext).setTitle
                    (UIUtils.getString(R.string.select_file)).addMenu(UIUtils.getString(R.string.take_photo),

                    new Dialog.OnClickListener() {
                        @Override
                        public void onClick(final DialogInterface dialog, int which) {
                            listener.onOpenFile(viewTag);
                            pickFileTools.clearPhotos();
                            pickFileTools.pick(9, new ImagePickerListener() {
                                @Override
                                public void getMultiUrlStringImages(List<String> imgs) {
                                    super.getMultiUrlStringImages(imgs);
                                    List<ProfileFileBean> list = new ArrayList<>();
                                    for (String path : imgs
                                            ) {
                                        File file = new File(path);
                                        list.add(addFileBean(file, TYPE_IMAGE));
                                    }
                                    listener.onFile(list, viewTag);
                                }

                                @Override
                                public void getSingleUrlImages(String uri) {
                                    super.getSingleUrlImages(uri);
                                    File file = new File(uri);
                                    listener.onFile(addFileBean(file, TYPE_IMAGE), viewTag);
                                }
                            });
                            bottomMenuDialog.dismiss();
                        }
                    }
            )/*.addMenu(context.getResources().getString(R.string.take_photo_from_album),
                new Dialog.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })*/.addMenu(UIUtils.getString(R.string.recording_voice)
                    , new Dialog.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            listener.onOpenFile(viewTag);
                            AudioManager.getInstance().showRecordDialogFragment((FragmentActivity) mContext);
                        }
                    }).addMenu(UIUtils.getString(R.string.recording_video)
                    , new Dialog.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            listener.onOpenFile(viewTag);
                            VideoManager.getInstance().startShootVideo((FragmentActivity) mContext, new Observer<ProfileFileBean>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(ProfileFileBean profileFileBean) {
                                    profileFileBean.fileType = TYPE_VIDEO;
                                    listener.onFile(profileFileBean, viewTag);
                                }

                                @Override
                                public void onError(Throwable e) {
                                    ToastUtils.showShort("未知错误 :" + e.getMessage());
                                }

                                @Override
                                public void onComplete() {

                                }
                            });
                            bottomMenuDialog.dismiss();
                        }
                    }).addMenu(UIUtils.getString(R.string
                    .select_other_file), new Dialog.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    FileUtils.openFileExplorer((Activity) mContext, "选择文件");
                    listener.onOpenFile(viewTag);
                    dialog.dismiss();
                }
            }).setCancelListener(new Dialog.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    bottomMenuDialog.dismiss();
                }
            }).create();


        bottomMenuDialog.show();
    }

    private ProfileFileBean addFileBean(File file, int fileType) {
        ProfileFileBean fileBean = new ProfileFileBean();
        fileBean.filePath = file.getAbsolutePath();
        fileBean.fileName = file.getName();
        fileBean.fileType = fileType;
        fileBean.file = file;
        return fileBean;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        listener = null;
    }

    @Override
    public void onItemClick(int position, final ProfileFileBean itemData) {
        if (FileUtils.isImage(itemData.filePath)) {
            IntentController.toBigImageNetActivity((FragmentActivity) mContext, itemData.filePath, 0);
        } else if (FileUtils.isVideoFile(itemData.filePath)) {
            VideoManager.getInstance().openVideoPlay((AppCompatActivity) mContext, itemData.filePath, null, Constant.FILE_FROM_TYPE.TYPE_NET);
        } else if (FileUtils.isRecordFile(itemData.filePath)) {
            AudioManager.getInstance().playRecordNetUrl(itemData.filePath, (FragmentActivity) mContext);
        } else {
            DownloadManage.getInstance().downloadFile((FragmentActivity) mContext, itemData.filePath, itemData.fileName, new DownLoadListener() {
                @Override
                public void onSuccess(File file) {
                    QbSdk.openFileReader(mContext, file.getAbsolutePath(), null, null);
                }

                @Override
                public void onFailed() {
                }
            });
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        VideoManager.getInstance().onActivityResult(requestCode, resultCode, data);
        pickFileTools.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.GET_FILE_SYSTEM) {
            if (data == null) return;
            File file = FileUtils.getFileFromUri(mContext, data.getData());

            listener.onFile(addFileBean(file, TYPE_FILE), viewTag);
            Logger.d(file.getAbsolutePath());
        }
    }

    public void setChooseFileListener(ChooseFileListener listener) {
        this.listener = listener;
    }

    public void setViewTag(String viewTag) {
        this.viewTag = viewTag;
    }

    public void addRecordFile(File audioFile, int totalTime, String viewTag) {
        if (!viewTag.equals(this.viewTag)) return;
        ProfileFileBean bean = addFileBean(audioFile, TYPE_RECORD);
        bean.recordTime = totalTime;
        bottomMenuDialog.dismiss();
        listener.onFile(bean, viewTag);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAudioRecordingStateEvent(AudioRecordingStateEvent event) {
        addRecordFile(event.audioFile, event.totalTime, event.viewTag);
    }

    public void addProfileFile(List<ProfileFileBean> file) {
        adapter.addAll(file);
    }
}
