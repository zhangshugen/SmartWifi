package com.smartwifi.utils;

import android.Manifest;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;

import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;
import com.smartwifi.interfaces.ImagePickerListener;
import com.smartwifi.widget.mvvm.view.AppActivityManager;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;

import static android.app.Activity.RESULT_OK;

/**
 * 作者: created by 张树荣
 * 描述: 选择图片工具类
 */


public class PickFileTools {

    private static SoftReference<FragmentActivity> act;
    private int tag;
    private ArrayList<String> photos = new ArrayList<>();
    private int picCount;

    public static PickFileTools init(FragmentActivity acty) {
        act = new SoftReference<>(acty);
        return new PickFileTools();
    }

    public static PickFileTools init(Fragment _fragment) {
        act = new SoftReference<>(_fragment.getActivity());
        return new PickFileTools();
    }


    private final int REQUEST_CODE_CHOOSE = 10001;


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //选择返回
        if (resultCode == RESULT_OK && (requestCode == PhotoPicker.REQUEST_CODE || requestCode == PhotoPreview.REQUEST_CODE)) {
            if (data != null) {
                photos = null;
                photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
            }
            if (photos == null || photos.size() == 0) return;
            if (listener != null) {
                if (picCount == 1) {
                    listener.getSingleUrlImages(photos.get(0), tag);
                } else {
                    listener.getMultiUrlStringImages(photos);
                }
            }
        }
        //拍照功能或者裁剪后返回
        if (resultCode == RESULT_OK && requestCode == PhotoPicker.CROP_CODE) {
            String photo = data.getStringExtra(PhotoPicker.KEY_CAMEAR_PATH);
            if (TextUtils.isEmpty(photo)) return;
            if (listener != null) {
                listener.getSingleUrlImages(photo, tag);
            }
        }
    }

    private ImagePickerListener listener;

    private boolean sCrop;

    public void pick(final int picCount, final boolean cameraShow, final boolean crop, final ImagePickerListener listener) {
        if (act == null) {
            act = new SoftReference<>(AppActivityManager.getAppActivityManager().currentActivity());
        }
        PermissionsUtil.requestPermission(act.get(), new PermissionListener() {
                    @Override
                    public void permissionGranted(@NonNull String[] permissions) {

                        PickFileTools.this.picCount = picCount;
                        if (picCount > 1) sCrop = false;//多张图片不设置裁剪
                        else sCrop = crop;
                        PhotoPicker.builder()
                                //设置选择个数
                                .setPhotoCount(picCount)
                                //选择界面第一个显示拍照按钮
                                .setShowCamera(cameraShow)
                                .setCrop(sCrop)
                                //选择时点击图片放大浏览
                                .setPreviewEnabled(true)
                                //附带已经选中过的图片
                                .setSelected(photos)
                                .start(act.get());
                        if (listener != null) {
                            PickFileTools.this.listener = listener;
                        }
                    }

                    @Override
                    public void permissionDenied(@NonNull String[] permissions) {
                        ToastUtils.showShort("请到设置-权限管理中开启");

                    }
                }, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
        );

        PermissionsUtil.requestPermission(act.get(), new PermissionListener() {
                    @Override
                    public void permissionGranted(@NonNull String[] permissions) {
                    }

                    @Override
                    public void permissionDenied(@NonNull String[] permissions) {
                        ToastUtils.showShort("请到设置-权限管理中开启");

                    }
                }, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA
        );

    }

    /**
     * 显示 拍照按钮，不裁剪
     *
     * @param picCount
     * @param lisenter
     */
    public void pick(int picCount, ImagePickerListener lisenter) {
        pick(picCount, true, false, lisenter);
    }


    /**
     * 是否显示拍照按钮  是否裁剪  单张图片
     *
     * @param lisenter
     */
    public void pick(boolean cameraShow, boolean crop, ImagePickerListener lisenter) {
        pick(1, cameraShow, crop, lisenter);
    }


    /**
     * 不显示拍照按钮  不裁剪  单张图片
     *
     * @param lisenter
     */
    public void pick(ImagePickerListener lisenter) {
        pick(true, false, lisenter);
    }

    /**
     * 不显示拍照 单张 裁剪 无标记
     *
     * @param lisenter
     */
    public void pickOne(ImagePickerListener lisenter) {
        pickOne(-1, true, lisenter);

    }

    /**
     * 不显示拍照 单张 裁剪
     *
     * @param lisenter
     */
    public void pickOne(int tag, boolean crop, ImagePickerListener lisenter) {
        this.tag = tag;
        pick(1, false, crop, lisenter);
    }

    /**
     * 拍照 不裁剪 无标记
     */
    public void capture(ImagePickerListener lisenter) {
        capture(lisenter, -1);
    }


    /**
     * 拍照 不裁剪
     */
    public void capture(ImagePickerListener lisenter, int tag) {
        capture(false, lisenter, tag);
    }

    /**
     * 拍照 是否裁剪 无标记
     */
    public void capture(boolean crop, final ImagePickerListener lisenter) {
        capture(crop, lisenter, -1);
    }

    /**
     * 拍照 是否裁剪？ 标记
     *
     * @param crop
     */
    public void capture(final boolean crop, final ImagePickerListener lisenter, final int tag) {
        if (act == null) {
            act = new SoftReference<>(AppActivityManager.getAppActivityManager().currentActivity());
        }
        PermissionsUtil.requestPermission(act.get(), new PermissionListener() {
                    @Override
                    public void permissionGranted(@NonNull String[] permissions) {
                        PickFileTools.this.tag = tag;
                        PickFileTools.this.listener = lisenter;
                        PhotoPicker.builder()
                                .setOpenCamera(true)
                                .setCrop(crop)
                                .start(act.get());
                    }

                    @Override
                    public void permissionDenied(@NonNull String[] permissions) {
                    }
                }, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
        );

    }

    public void deleteImage(int position) {
        if (photos == null || photos.size() == 0 || photos.size() <= position) {
            return;
        }
        photos.remove(position);
    }

    public void clearPhotos() {
        if (photos != null) photos.clear();
    }

    public void setPhotosList(List pathList) {
        photos.clear();
        photos.addAll(pathList);
    }
}
