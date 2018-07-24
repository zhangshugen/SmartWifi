package com.smartwifi.manager;

import android.support.annotation.NonNull;

import com.smartwifi.R;
import com.smartwifi.bean.ProfileFileBean;
import com.smartwifi.http.Url;
import com.smartwifi.interfaces.UploadListener;
import com.smartwifi.manager.retrofit.RetrofitStringManager;
import com.smartwifi.utils.FileUtils;
import com.smartwifi.utils.ToastUtils;
import com.smartwifi.utils.UIUtils;
import com.smartwifi.widget.retrofithelper.rxschedulers.RxSchedulersHelper;
import com.smartwifi.widget.retrofithelper.rxsubscriber.ProgressObserver;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/12
 * @Describe
 */

public class UploadFileManager {
    private List<ProfileFileBean> fileBeanList;
    private UploadListener mUploadListener;
    private int mIndex;
    private boolean isShowLoading;

    private UploadFileManager() {

    }


    private static class UploadFileManagerSingleton {
        private static final UploadFileManager INSTANCE = new UploadFileManager();

    }

    public static UploadFileManager getInstance() {
        return UploadFileManager.UploadFileManagerSingleton.INSTANCE;
    }


    public void uploadPath(List<String> imageList, UploadListener listener) {
        init(listener);
        uploadFileToServer(mIndex, imageList);
    }


    public void uploadPath(String imagePath, UploadListener listener) {
        init(listener);
        List<String> list = new ArrayList<>();
        list.add(imagePath);
        uploadFileToServer(mIndex, list);
    }

    public void uploadFileBean(ProfileFileBean bean, UploadListener listener) {
        init(listener);

        List<ProfileFileBean> list = new ArrayList<>();
        list.add(bean);
        uploadFileBeanToServer(mIndex, list);
    }

    public void uploadFileBean(List<ProfileFileBean> list, UploadListener listener) {
        init(listener);
        uploadFileBeanToServer(mIndex, list);
    }

    private void init(UploadListener listener) {
        mIndex = 0;
        mUploadListener = listener;
        fileBeanList = new ArrayList<>();
        isShowLoading = false;
    }

    private void uploadFileToServer(int index, final List<String> files) {
        if (files == null || files.size() == 0) return;
        if (index > files.size()) return;
        final String path = files.get(index);
        if (path.startsWith(Url.BASE_URL)) {
            return;
        }
        showLoadingDialog();
        Luban.with(UIUtils.getContext())
                .load(path)
                .setCompressListener(new OnCompressListener() { //设置回调
                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onSuccess(File file) {
                        startUploadFilePath(file, files);
                    }

                    @Override
                    public void onError(Throwable e) {
                        startUploadFilePath(new File(path), files);
                    }
                }).launch();    //启动压缩
    }

    private void showLoadingDialog() {
        if (!isShowLoading) {
            LoadingFragmentManager.getInstance().showLoadingDialog(null, UIUtils.getString(R.string.upload_file));
            isShowLoading = true;
        }

    }

    private void startUploadFilePath(final File file, final List<String> files) {
        String url = "sample/ashx/MobileUpload.ashx?FILENAME=" + file.getAbsolutePath();
        MultipartBody multipartBody = filesToMultipartBody(file);
        RetrofitStringManager.getInstance().getApiService().uploadFileWithRequestBody(url, multipartBody).compose(RxSchedulersHelper.applyIoTransformer())
                .subscribe(new ProgressObserver<ResponseBody>(false, null) {

                    @Override
                    public void _onError(String errorMessage, int errorCode) {
                        ToastUtils.showShort(errorMessage);
                        mUploadListener.onUploadFailed();
                    }

                    @Override
                    public void _onNext(ResponseBody responseBody) {
                        ProfileFileBean bean = new ProfileFileBean();
                        bean.file = file;
                        try {
                            bean.fileId = responseBody.string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        fileBeanList.add(bean);

                        if (fileBeanList.size() == files.size()) {
                            // 成功
                            mUploadListener.onUploadSuccess(fileBeanList);
                            mIndex = 0;
                            isShowLoading = false;
                            LoadingFragmentManager.getInstance().dismissLoadingDialog();
                        } else {
                            mIndex++;
                            uploadFileToServer(mIndex, files);
                        }
                    }
                });

    }

    private void uploadFileBeanToServer(int index, final List<ProfileFileBean> files) {
        if (files == null || files.size() == 0) return;
        if (index > files.size()) return;
        final ProfileFileBean profileFileBean = files.get(index);
        if (profileFileBean.filePath.startsWith(Url.BASE_URL)) {
            if (files.size() == 1) {
                mUploadListener.onUploadSuccess(files);
            } else {
                fileBeanList.add(profileFileBean);
                mIndex++;
                uploadFileBeanToServer(mIndex, files);
            }
            return;
        }
        showLoadingDialog();
        Luban.with(UIUtils.getContext())
                .load(profileFileBean.filePath)
                .setCompressListener(new OnCompressListener() { //设置回调
                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onSuccess(File file) {
                        startUploadFileBean(file, profileFileBean, files);
                    }

                    @Override
                    public void onError(Throwable e) {
                        startUploadFileBean(new File(profileFileBean.filePath), profileFileBean, files);
                    }
                }).launch();    //启动压缩


    }

    private void startUploadFileBean(final File file, final ProfileFileBean profileFileBean, final List<ProfileFileBean> files) {
        String url = "sample/ashx/MobileUpload.ashx?FILENAME=" + file.getName();
        MultipartBody stringRequestBodyMap = filesToMultipartBody(file);
        RetrofitStringManager.getInstance().getApiService().uploadFileWithRequestBody(url, stringRequestBodyMap).compose(RxSchedulersHelper.applyIoTransformer()).subscribe(new ProgressObserver<ResponseBody>(false, null) {
            @Override
            public void _onError(String errorMessage, int errorCode) {
                ToastUtils.showShort(errorMessage);
                mUploadListener.onUploadFailed();

            }

            @Override
            public void _onNext(ResponseBody responseBody) {
                profileFileBean.file = file;
                try {
                    profileFileBean.fileId = responseBody.string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                fileBeanList.add(profileFileBean);

                if (fileBeanList.size() == files.size()) {
                    // 成功
                    mUploadListener.onUploadSuccess(fileBeanList);
                    mIndex = 0;
                    isShowLoading = false;
                    LoadingFragmentManager.getInstance().dismissLoadingDialog();
                } else {
                    mIndex++;
                    uploadFileBeanToServer(mIndex, files);
                }
            }
        });

    }


    private List<MultipartBody.Part> filesToMultipartBodyParts(List<File> files) {
        List<MultipartBody.Part> parts = new ArrayList<>(files.size());
        for (File file : files) {
            MultipartBody.Part part = getPart(file);
            parts.add(part);
        }
        return parts;
    }

    @NonNull
    private MultipartBody.Part getPart(File file) {
        RequestBody requestBody = RequestBody.create(FileUtils.guessMimeType(file.getName()), file);
        return MultipartBody.Part.createFormData("file", file.getName(), requestBody);
    }

    private MultipartBody filesToMultipartBody(List<File> files) {
        MultipartBody.Builder builder = new MultipartBody.Builder();

        for (File file : files) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);
            builder.addFormDataPart("aFile", file.getName(), requestBody);
        }

        builder.setType(MultipartBody.FORM);
        MultipartBody multipartBody = builder.build();
        return multipartBody;
    }

    private MultipartBody filesToMultipartBody(File file) {


        MultipartBody.Builder builder = new MultipartBody.Builder();
        RequestBody requestBody = RequestBody.create(FileUtils.guessMimeType(file.getName()), file);
        builder.addFormDataPart("aFile", file.getAbsolutePath(), requestBody);
        builder.setType(MultipartBody.FORM);
        MultipartBody multipartBody = builder.build();
        return multipartBody;
    }

    private Map<String, RequestBody> filesToRequestBody(File file) {
        Map<String, RequestBody> map = new HashMap<>();
        RequestBody fileBody = RequestBody.create(FileUtils.guessMimeType(file.getName()), file);
        map.put("file\"; filename=\"" + file.getName() + "", fileBody);
      /*  MultipartBody.Builder builder = new MultipartBody.Builder();
        RequestBody requestBody = RequestBody.create(FileUtils.guessMimeType(file.getName()), file);
        builder.addFormDataPart("aFile", file.getName(), requestBody);
        builder.setType(MultipartBody.FORM);
        MultipartBody multipartBody = builder.build();*/
        return map;
    }
}
