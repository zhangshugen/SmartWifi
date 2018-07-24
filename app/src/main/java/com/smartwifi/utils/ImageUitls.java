package com.smartwifi.utils;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.smartwifi.widget.MyMediaScannerConnectionClient;
import com.smartwifi.widget.retrofithelper.rxschedulers.RxSchedulersHelper;

import java.io.File;
import java.io.FileNotFoundException;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by apanda on 2018/7/9.
 */

public class ImageUitls {

    /**
     * 根据文件名判断文件是否为图片
     *
     * @param file 　文件
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isImg(File file) {
        return file != null && isImg(file.getPath());
    }

    /**
     * 根据文件名判断文件是否为图片
     *
     * @param filePath 　文件路径
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isImg(String filePath) {
        String path = filePath.toUpperCase();
        return path.endsWith(".PNG") || path.endsWith(".JPG")
                || path.endsWith(".JPEG") || path.endsWith(".BMP")
                || path.endsWith(".GIF");
    }


    /**
     * 是否网络图 地址
     *
     * @param filePath
     * @return
     */
    public static boolean netImg(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return false;
        }
        String path = filePath.toUpperCase();
        return path.startsWith("HTTP");
    }


    /**
     * 根据文件名判断文件是否为图片
     *
     * @param filePath 　文件路径
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isGif(String filePath) {
        String path = filePath.toLowerCase();
        return path.endsWith(".gif");
    }
    public static void savePicRefreshGallery(Activity activity, String filePath) {
        File file = new File(filePath);
        try {
            MediaStore.Images.Media.insertImage(activity.getContentResolver(), file.getAbsolutePath(), file.getName(), null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT > 19) {
            saveImageSendScanner(activity, new MyMediaScannerConnectionClient(filePath));
        } else {

            saveImageSendBroadcast(activity, filePath);
        }
    }
    /**
     * 保存后用MediaScanner扫描，通用的方法
     */
    public static void saveImageSendScanner(Activity activity, MyMediaScannerConnectionClient scannerClient) {

        final MediaScannerConnection scanner = new MediaScannerConnection(activity, scannerClient);
        scannerClient.setScanner(scanner);
        scanner.connect();
    }

    /**
     * 保存后用广播扫描，Android4.4以下使用这个方法
     *
     * @author YOLANDA
     */
    public static void saveImageSendBroadcast(Activity activity, String filePath) {
        activity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + filePath)));
    }
    public static void savePicRefreshGallery(final Activity activity, final File file) {
        io.reactivex.Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                try {
                    MediaStore.Images.Media.insertImage(activity.getContentResolver(), file.getAbsolutePath(), file.getName(), null);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                if (Build.VERSION.SDK_INT > 19) {
                    saveImageSendScanner(activity, new MyMediaScannerConnectionClient(file.getAbsolutePath()));
                } else {

                    saveImageSendBroadcast(activity, file.getAbsolutePath());
                }
            }
        }).compose(RxSchedulersHelper.applyIoTransformer()).subscribe(new Observer<Object>() {


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
            public void onNext(Object o) {

            }
        });

    }

}
