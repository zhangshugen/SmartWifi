package com.smartwifi.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;

import com.smartwifi.constant.Constant;
import com.smartwifi.widget.retrofithelper.rxschedulers.RxSchedulersHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.FileNameMap;
import java.net.URLConnection;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.MediaType;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/27
 * @Describe
 */

public class FileUtils {
    public static File getImageFolderPath() {
        if (!hasSdcard())
            return null;
        File dirFile = new File(Constant.IMAGE_ROOT_PATH);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        return dirFile;
    }

    public static File getVideoFolderPath() {
        if (!hasSdcard())
            return null;
        File dirFile = new File(Constant.VIDEO_ROOT_PATH);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        return dirFile;
    }

    public static File getRecordFolderPath() {
        if (!hasSdcard())
            return null;
        File dirFile = new File(Constant.RECORD_ROOT_PATH);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        return dirFile;
    }

    public static void saveFile(final File file, final String fileName, final FragmentActivity activity) {
        if (!file.exists()) {
            return;
        }
        io.reactivex.Observable.create(new ObservableOnSubscribe<File>() {
            @Override
            public void subscribe(ObservableEmitter<File> emitter) throws Exception {
                File imageFolderPath = getImageFolderPath();
                if (imageFolderPath == null) return;
                File copyFile = new File(imageFolderPath, fileName);
                if (!copyFile.exists()) {
                    try {
                        int byteread = 0;
                        copyFile.createNewFile();
                        InputStream inStream = new FileInputStream(file); //读入原文件
                        FileOutputStream fs = new FileOutputStream(copyFile);
                        byte[] buffer = new byte[1024];
                        while ((byteread = inStream.read(buffer)) != -1) {
                            fs.write(buffer, 0, byteread);
                        }
                        inStream.close();
                        emitter.onNext(copyFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    emitter.onNext(copyFile);
                }
            }
        }).compose(RxSchedulersHelper.<File>applyIoTransformer()).subscribe(new Observer<File>() {


            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(File file) {
                ToastUtils.showShort("已保存图片至" + file.getAbsolutePath());
                ImageUitls.savePicRefreshGallery(activity, file);
            }
        });


    }

    public static Uri setFilePermission(File mVideoFile) {
        Uri fileUri = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //针对Android7.0，需要通过FileProvider封装过的路径，提供给外部调用
            fileUri = FileProvider.getUriForFile(UIUtils.getContext(), UIUtils.getContext()
                            .getPackageName() + ".fileProvider",
                    mVideoFile);//通过FileProvider创建一个content类型的Uri，进行封装
        } else { //7.0以下，如果直接拿到相机返回的intent值，拿到的则是拍照的原图大小，很容易发生OOM
            // ，所以我们同样将返回的地址，保存到指定路径，返回到Activity时，去指定路径获取，压缩图片
            fileUri = Uri.fromFile(mVideoFile);
        }
        return fileUri;
    }


    public static File createVideoFile() {
        File mediaStorageDir = getVideoFolderPath();
        if (mediaStorageDir == null) return null;
        String imageFileName = "VID_" + DateUtils.getStringMilliSecondTime();
        String suffix = ".mp4";
        File mediaFile = new File(mediaStorageDir + File.separator + imageFileName + suffix);
        try {
            mediaFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mediaFile;
    }

    public static boolean hasSdcard() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @Description: 打开系统文件管理器
     * @Author: niebin
     * @Date: 2018/3/20 下午4:20
     */
    public static void openFileExplorer(Activity from, String title) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        from.startActivityForResult(Intent.createChooser(intent, title), Constant.GET_FILE_SYSTEM);
    }

    public static File getRecodingFile() {

        File recodingFile = getRecordFolderPath();
        if (recodingFile == null)
            return null;
        File file = new File(recodingFile + File.separator + "AUD_" + DateUtils.getStringMilliSecondTime
                () + ".mp3");
        //Logger.e(file.getAbsolutePath());
        if (!file.exists()) {
            try {

                file.createNewFile();
                file.setWritable(true);
                file.setReadable(true);
                file.setExecutable(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    public static void deleteFile(File file) {
        if (file == null) return;
        if (file.isFile() || file.exists()) {
            file.delete();
        }
    }

    /**
     * @Description: 根据Uri获取File
     * @Author: niebin
     * @Date: 2018/3/23 下午12:11
     */
    public static File getFileFromUri(Context context, Uri uri) {
        if (uri == null) {
            ToastUtils.showShort("未知错误");
        }
        File file = null;
        String filePath = getFileData(context, uri);
        if (isImage(filePath) || isUnloadFileType(filePath)) {
            file = new File(filePath);
        } else {
            ToastUtils.showShort("该文件不符合文件上传类型");
        }
        return file;
    }

    /***
     *
     * @param path
     * @return 返回是文件还是图片类型
     */
    public static boolean isImage(String path) {
        if (TextUtils.isEmpty(path)) {
            return false;
        }
        path = path.toLowerCase();
        if (path.endsWith(".jpeg") || path.endsWith(".jpg") || path.endsWith(".gif") || path
                .endsWith(".tif") || path.endsWith(".png")) {
            return true;
        }
        return false;
    }

    /***
     *
     * @param path
     * @return 返回是否文件是上传要求格式类型
     */
    public static boolean isUnloadFileType(String path) {
        path = path.toLowerCase();
        if (path.endsWith(".txt") || path.endsWith(".doc") || path.endsWith(".docx") || path
                .endsWith(".ppt") || path.endsWith(".pptx") || path.endsWith(".jpg") || path
                .endsWith(".gif") || path.endsWith(".xls") || path.endsWith(".rar") || path
                .endsWith(".sql") || path.endsWith(".zip") || path.endsWith(".tif") || path
                .endsWith(".pdf") || path.endsWith(".xlsx") || path.endsWith(".mp4") || path
                .endsWith(".mp3")) {
            return true;
        } else if (isVideoFile(path)) {
            return true;
        } else if (isRecordFile(path)) {
            return true;
        }
        return false;
    }

    public static boolean isVideoFile(String path) {
        File file = isFileAndExists(path);
        if (file == null) {
            return false;
        }
        path = file.getName();
        if (TextUtils.isEmpty(path)) {
            return false;
        }
        path = path.toLowerCase();
        if (path.endsWith(".mp4") || path.endsWith(".m4v") || path.endsWith(".3gp") || path
                .endsWith(".3gpp") || path.endsWith(".3g2") || path.endsWith(".wmv")) {
            return true;
        }
        return false;
    }

    public static File isFileAndExists(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }

        File file = new File(path);
        if (file.exists() && file.isFile()) {
            return file;
        }

        return null;
    }

    public static boolean isRecordFile(String path) {
        File file = isFileAndExists(path);
        if (file == null) {
            return false;
        }

        path = file.getName();
        if (TextUtils.isEmpty(path)) {
            return false;
        }

        path = path.toLowerCase();
        if (path.endsWith(".mp3") || path.endsWith(".m4a") || path.endsWith(".wav") || path
                .endsWith(".amr") || path.endsWith(".awb") || path.endsWith(".wma")) {
            return true;
        }
        return false;
    }

    public static String getFileData(Context context, Uri uri) {
        if ((PhoneUtils.PHONE_BRAND_HUAWEI.equals(PhoneUtils.getPhoneBrand()) || PhoneUtils
                .PHONE_BRAND_HONOR.equals(PhoneUtils.getPhoneBrand())) && uri.toString()
                .contains("content://com.huawei.hidisk.fileprovider/root")) {
            String path = uri.toString().replace("content", "file").replace("com.huawei.hidisk" +
                    ".fileprovider/root", "");
            uri = Uri.parse(path);
        }

        if ("file".equalsIgnoreCase(uri.getScheme())) {
            //使用第三方应用打开
            return uri.getPath();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //4.4以后
            return getPath(context, uri);

        } else {
            //4.4以下下系统调用方法
            return getRealPathFromURI(context, uri);
        }

    }

    /**
     * 从Android系统4.4版本开始，选取相册中的的图片不再返回图片真实的Uri了，而是一个封装过的Uri，
     * 因此如果是4.4版本及以上的手机就需要对这个Uri进行解析才行。
     */
    @SuppressLint("NewApi")
    public static String getPath(final Context context, final Uri uri) {

        if (DocumentsContract.isDocumentUri(context, uri)) {
            if (isExternalStorageDocument(uri)) {
                // ExternalStorageProvider
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if (isDownloadsDocument(uri)) {
                // DownloadsProvider
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            } else if (isMediaDocument(uri)) {
                // MediaProvider
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // MediaStore (and general)
            return getDataColumn(context, uri, null, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // File
            return uri.getPath();
        }
        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static String getRealPathFromURI(Context context, Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
        if (null != cursor && cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
            cursor.close();
        }
        return res;
    }
    public static MediaType guessMimeType(String fileName) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        fileName = fileName.replace("#", "");
        String contentType = fileNameMap.getContentTypeFor(fileName);
        return contentType == null? MediaType.parse("application/octet-stream"):MediaType.parse(contentType);
    }

}
