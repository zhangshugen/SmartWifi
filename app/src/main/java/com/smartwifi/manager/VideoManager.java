package com.smartwifi.manager;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;
import com.smartwifi.bean.ProfileFileBean;
import com.smartwifi.part.commonpage.activity.PlayVideoActivity;
import com.smartwifi.utils.FileUtils;
import com.smartwifi.utils.ToastUtils;

import java.io.File;

import io.reactivex.Observer;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/11
 * @Describe
 */

public class VideoManager {


    private Uri mFileUri;
    private File mVideoFile;
    private static final int VIDEO_RESULE = 10001;
    private Observer observer;


    private VideoManager() {

    }

    public void openSystemVideoPlay(AppCompatActivity questionTrendsActivity, String path) {
        Intent openVideo = new Intent(Intent.ACTION_VIEW);
        openVideo.setDataAndType(Uri.parse(path), "video/*");
        questionTrendsActivity.startActivity(openVideo);
    }


    public void openVideoPlay(AppCompatActivity appCompatActivity, String path, File videoVideoThumbnail, int isNetUrlType) {
        Intent intent = new Intent(appCompatActivity, PlayVideoActivity.class);
        intent.putExtra("path", path);
        String videoVideoThumbnailPath = "";
        if (videoVideoThumbnail != null || !TextUtils.isEmpty(videoVideoThumbnail.getAbsolutePath()))
            videoVideoThumbnailPath = videoVideoThumbnail.getAbsolutePath();
        intent.putExtra("videoVideoThumbnail", videoVideoThumbnailPath);
        intent.putExtra("isNetUrlType", isNetUrlType);
        intent.putExtra("videoThumbnailIsNet", 1);
        intent.putExtra("title", "");

        appCompatActivity.startActivity(intent);
    }

    public void openVideoPlay(Activity questionTrendsActivity, String path, String videoVideoThumbnail, int isNetUrlType, int videoThumbnailIsNet, String title) {
        Intent intent = new Intent(questionTrendsActivity, PlayVideoActivity.class);
        intent.putExtra("path", path);
        intent.putExtra("videoVideoThumbnail", videoVideoThumbnail);
        intent.putExtra("videoThumbnailIsNet", videoThumbnailIsNet);
        intent.putExtra("isNetUrlType", isNetUrlType);
        intent.putExtra("videoThumbnailIsNet", videoThumbnailIsNet);
        intent.putExtra("title", title);

        questionTrendsActivity.startActivity(intent);
    }


    private static class VideoManagerSingleton {
        private static final VideoManager INSTANCE = new VideoManager();

    }

    public static VideoManager getInstance() {
        return VideoManagerSingleton.INSTANCE;
    }

    public void startShootVideo(final FragmentActivity appCompatActivity, final Observer o) {
        PermissionsUtil.requestPermission(appCompatActivity, new PermissionListener() {
                    @Override
                    public void permissionGranted(@NonNull String[] permissions) {

                        File mediaFile = createMediaFile();
                        if (mediaFile == null) {
                            ToastUtils.showShort("未知错误");
                            return;
                        }
                        startCamera(appCompatActivity, mediaFile);
                        observer = o;
                    }

                    @Override
                    public void permissionDenied(@NonNull String[] permissions) {
                        ToastUtils.showShort("请到设置-权限管理中开启");

                    }
                }, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
        );


    }

    public void startCamera(FragmentActivity appCompatActivity, File file) {
        mVideoFile = file;
        mFileUri = FileUtils.setFilePermission(file);
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mFileUri);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
        appCompatActivity.startActivityForResult(intent, VIDEO_RESULE);
    }

    private File createMediaFile() {
        return FileUtils.createVideoFile();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == VIDEO_RESULE) {
            if (resultCode == Activity.RESULT_OK) {
                observer.onNext(getFileBean());
                observer.onComplete();
            } else if (resultCode == Activity.RESULT_CANCELED) {
                // cancel
            } else {
                // failed
            }
        }
    }

    public Uri getVideoFileUri() {
        return mFileUri;
    }

    public File getVideoFile() {
        return mVideoFile;
    }

    public ProfileFileBean getFileBean() {
        ProfileFileBean fileBean = new ProfileFileBean();
        fileBean.fileName = mVideoFile.getName();
        fileBean.filePath = mVideoFile.getAbsolutePath();
        return fileBean;
    }

    public void onDestory() {
        mVideoFile = null;
        mFileUri = null;
    }
}
