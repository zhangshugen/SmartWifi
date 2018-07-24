package com.smartwifi.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.smartwifi.widget.retrofithelper.rxschedulers.RxSchedulersHelper;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;
import rx.Observable;
import rx.Observer;

/**
 * Created by Administrator on 2018/7/9.
 */

public class BitmapUtils {
    public static Bitmap scaleImage(Bitmap bm, int newWidth, int newHeight) {
        if (bm == null) {
            return null;
        }
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix,
                true);
        if (bm != null & !bm.isRecycled()) {
            bm.recycle();
            bm = null;
        }
        return newbm;
    }

    public static Bitmap readBitmapById(int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        InputStream is = UIUtils.getContext().getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }

    public static Bitmap scaleEmoji(int bm, int newWidth, int newHeight) {
        return scaleImage(readBitmapById(bm), newWidth, newHeight);
    }


    public static void saveFile(final Bitmap bm, final String fileName, final AppCompatActivity activity) {
        io.reactivex.Observable.create(new ObservableOnSubscribe<File>() {
            @Override
            public void subscribe(ObservableEmitter<File> emitter) throws Exception {
                File imageFolderPath =FileUtils. getImageFolderPath();
                if (imageFolderPath == null) return;
                File jpgFile = new File(imageFolderPath, fileName);
                if (!jpgFile.exists()) {
                    BufferedOutputStream bos = null;
                    try {
                        jpgFile.createNewFile();
                        bos = new BufferedOutputStream(new FileOutputStream(jpgFile));
                        bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                        emitter.onNext(jpgFile);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            bos.flush();
                            bos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                } else {
                    emitter.onNext(jpgFile);
                }
            }
        }).compose(RxSchedulersHelper.applyIoTransformer()).subscribe(new io.reactivex.Observer<File>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(File file) {
                ToastUtils.showShort("已保存图片至" + file.getAbsolutePath());
                ImageUitls.savePicRefreshGallery(activity, file);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });


    }

    public static float getInitImageScale(String imagePath, FragmentActivity activity) {
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        WindowManager wm = activity.getWindowManager();
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        // 拿到图片的宽和高
        int dw = bitmap.getWidth();
        int dh = bitmap.getHeight();
        float scale = 1.0f;
        //图片宽度大于屏幕，但高度小于屏幕，则缩小图片至填满屏幕宽
        if (dw > width && dh <= height) {
            scale = width * 1.0f / dw;
        }
        //图片宽度小于屏幕，但高度大于屏幕，则放大图片至填满屏幕宽
        if (dw <= width && dh > height) {
            scale = width * 1.0f / dw;
        }
        //图片高度和宽度都小于屏幕，则放大图片至填满屏幕宽
        if (dw < width && dh < height) {
            scale = width * 1.0f / dw;
        }
        //图片高度和宽度都大于屏幕，则缩小图片至填满屏幕宽
        if (dw > width && dh > height) {
            scale = width * 1.0f / dw;
        }
        return scale;
    }

    public static File saveBitmapFile(Bitmap bitmap) {
        File imageFolderPath =FileUtils. getImageFolderPath();
        if (imageFolderPath == null) return null;
        File file = new File(imageFolderPath, "voteDetails.png");//将要保存图片的路径
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
            return file;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
