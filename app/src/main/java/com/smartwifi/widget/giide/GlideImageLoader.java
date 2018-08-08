package com.smartwifi.widget.giide;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.IntegerRes;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.smartwifi.R;
import com.smartwifi.utils.DisplayUtils;
import com.smartwifi.utils.UIUtils;

import org.greenrobot.greendao.annotation.NotNull;

import java.io.File;


public class GlideImageLoader {

    private static GlideImageLoader _singleInstance = null;

    private GlideImageLoader() {
    }


    public static GlideImageLoader getInstance() {
        synchronized (GlideImageLoader.class) {
            if (_singleInstance == null) {
                _singleInstance = new GlideImageLoader();
            }
        }
        return _singleInstance;
    }


    public static void onDisplayFileGif(ImageView imageView, String filePath) {
        Glide.with(imageView.getContext())
                .load(Uri.fromFile(new File(filePath)))//
                .override(240, 240)
                .placeholder(R.mipmap.ic_launcher)//
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .dontAnimate()
                .fitCenter()
                .into(new GlideDrawableImageViewTarget(imageView));
    }


    public static void onDisplayGif(ImageView imageView, String filePath, RequestListener<Uri, GlideDrawable> listener) {
        Glide.with(imageView.getContext())
                .load(Uri.fromFile(new File(filePath)))//
                .override(240, 240)
                .listener(listener)
                .placeholder(R.mipmap.ic_launcher)//
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .dontAnimate()
                .fitCenter()
                .into(new GlideDrawableImageViewTarget(imageView));
    }


    public static void onDisplayGifUrl(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(Uri.parse(url))//
                .override(240, 240)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .dontAnimate()
                .placeholder(R.mipmap.ic_launcher)//
                .fitCenter()
                .into(new GlideDrawableImageViewTarget(imageView));
    }

    public static void onDisplayGifUrl(Context context, ImageView imageView, String url) {
        Glide.with(context)
                .load(Uri.parse(url))//
                .override(240, 240)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .dontAnimate()
                .placeholder(R.mipmap.ic_launcher)//
                .fitCenter()
                .into(new GlideDrawableImageViewTarget(imageView));
    }


    public static void onDisplayGifUrl(ImageView imageView, String url, RequestListener<String, GlideDrawable> listener) {
        Glide.with(imageView.getContext())
                .load(url)//
                .override(240, 240)
                .placeholder(R.mipmap.ic_launcher)//
                .listener(listener)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .dontAnimate()
                .fitCenter()
                .into(new GlideDrawableImageViewTarget(imageView));
    }


    public static void onDisplayImage(@NotNull final Context context, final ImageView imageView, @NotNull String imgUrl) {
        if (imageView == null) {
            return;
        }

        Glide.with(imageView.getContext()).load(imgUrl)//
                .placeholder(R.mipmap.ic_launcher)
                //.placeholder(R.mipmap.ic_mrtx)//
                .error(R.mipmap.ic_loading_image_error)//
                .thumbnail(0.55f)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)//
                .into(imageView);


    }

    public static void onDisplayImage(@NotNull final Context context, @NotNull final ImageView imageView, @NotNull File _file) {
        if (imageView == null) {
            return;
        }
        Glide.with(imageView.getContext()).load(_file)//
                .placeholder(R.mipmap.ic_launcher)//
                .error(R.mipmap.ic_loading_image_error)//
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)//
                .into(imageView);
    }

   /* public static void onDisplayImage(final ImageView context, @NotNull final String imageView, @NotNull int _bitmap) {

        if (imageView == null) {
            return;
        }
        Glide.with(context.getContext()).load(imageView)//
                .placeholder(_bitmap)//
                .error(_bitmap)//
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(context);
    }*/
    public static void onDisplayImage(final ImageView imageView, String resource, int placeholder) {
        if (imageView == null) {
            return;
        }
        Glide.with(imageView.getContext()).load(resource)//
                .placeholder(placeholder)//
                .error(placeholder)//
                .thumbnail(0.55f)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)//
                .into(imageView);
    }

    public static void onDisplayImage(final Context context, @NotNull final ImageView imageView, @NotNull Bitmap _bitmap) {

        if (imageView == null) {
            return;
        }
        Glide.with(imageView.getContext()).load(_bitmap)//
                .placeholder(R.mipmap.ic_launcher)//
                .error(R.mipmap.ic_loading_image_error)//
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageView);
    }

    public static void onDisplayImage(final Context context, final ImageView imageView, Integer resource) {
        if (imageView == null) {
            return;
        }
        Glide.with(imageView.getContext()).load(resource)//
                .placeholder(R.mipmap.ic_launcher)//
                .error(R.mipmap.ic_loading_image_error)//
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageView);
    }


    public static void onDisplayImage(final Context context, final ImageView imageView, Uri resource) {
        if (imageView == null) {
            return;
        }
        Glide.with(imageView.getContext()).load(resource)//
                .placeholder(R.mipmap.ic_launcher)//
                .error(R.mipmap.ic_loading_image_error)//
                .thumbnail(0.55f)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)//
                .into(imageView);
    }


    public static void onDisplayImage(final Context context, final ImageView imageView, Uri resource, RequestListener<Uri, GlideDrawable> listener) {
        if (imageView == null) {
            return;
        }
        Glide.with(imageView.getContext()).load(resource)//
                .placeholder(R.mipmap.ic_launcher)//
                .error(R.mipmap.ic_loading_image_error)//
                .thumbnail(0.55f)
                .listener(listener)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)//
                .into(imageView);
    }


    /**
     * 自定义图片的大小
     *
     * @param activity
     * @param path
     * @param imageView
     * @param width
     * @param height
     */
    public void displayImage(@NotNull final Activity activity, @NotNull String path, @NotNull final ImageView imageView, @NotNull final int width, @NotNull final int height) {

        if (imageView == null) {
            return;
        }
        Glide.with(imageView.getContext()).load(path)//
                .placeholder(R.mipmap.ic_launcher)//
                .error(R.mipmap.ic_loading_image_error)//
                .override(width, height)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)//
                .into(imageView);
    }

    public static void displayRound(@NotNull final Context context, @NotNull final ImageView imageView, @NotNull String path) {

        if (imageView == null) {
            return;
        }

        Glide
                .with(imageView.getContext())
                .load(path)
                .error(R.mipmap.ic_mrtx)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .transform(new GlideRoundTransform(context))
                .into(imageView);
    }


    public static void displayBorderRound(@NotNull final Context context, float borderWidth, int color, @NotNull final ImageView imageView, @NotNull String path) {

        if (imageView == null) {
            return;
        }

        displayBorderImage(imageView.getContext(), borderWidth, color, imageView, path);
    }

    private static void displayBorderImage(@NotNull Context context, float borderWidth, int color, @NotNull ImageView imageView, @NotNull String path) {
        Glide.with(context)
                .load(path)
                .error(R.mipmap.ic_mrtx)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)

                .transform(new GlideBorderTransform(context, DisplayUtils.dip2px(borderWidth), UIUtils.getColor(color)))
                .into(imageView);
    }

    private static void displayBorderImage(@NotNull Context context, float borderWidth, int color, @NotNull ImageView imageView, @NotNull File path) {
        Glide.with(context)
                .load(path)
                .error(R.mipmap.ic_mrtx)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .transform(new GlideBorderTransform(context, DisplayUtils.dip2px(borderWidth), UIUtils.getColor(color)))
                .into(imageView);
    }

    public static void displayRound(@NotNull final Context context, @NotNull final ImageView imageView, @NotNull @IntegerRes Integer resouce) {

        if (imageView == null) {
            return;
        }
        Glide.with(imageView.getContext())
                .load(resouce)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .error(R.mipmap.ic_loading_image_error)
                .centerCrop()
                .transform(new GlideRoundTransform(context))

                .into(imageView);
    }

    public static void displayRadius(@NotNull final Context context, @NotNull final ImageView imageView, @NotNull String imgUrl) {

        if (imageView == null) {
            return;
        }
        Glide
                .with(imageView.getContext())
                .load(imgUrl).centerCrop()
                .placeholder(R.mipmap.ic_launcher)//
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .transform(new CornersTransform(context))
                .error(R.mipmap.ic_loading_image_error)
                .into(imageView);


    }

    public static void displayRadiusForPath(@NotNull final Context context, @NotNull final ImageView imageView, @NotNull String imgUrl) {
        File file = new File(imgUrl);
        if (!file.exists()) {
            return;
        }
        Glide.with(imageView.getContext()).load(file)//
                .placeholder(R.mipmap.ic_launcher)//
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)//
                .error(R.mipmap.ic_loading_image_error)
                .into(imageView);
    }

    public static void displayRadius(@NotNull final Context context, @NotNull final ImageView imageView, @NotNull Uri imgUrl) {

        if (imageView == null) {
            return;
        }
        Glide
                .with(imageView.getContext())
                .load(imgUrl).centerCrop()
                .placeholder(R.mipmap.ic_launcher)//
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .transform(new CornersTransform(context))
                .error(R.mipmap.ic_loading_image_error)
                .into(imageView);

    }


    public static void displayRadiusAndRes(@NotNull final Context context, @NotNull final ImageView imageView, @NotNull String imgUrl, int res) {
        if (imageView == null) {
            return;
        }
        Glide
                .with(imageView.getContext())
                .load(imgUrl).centerCrop()
                .placeholder(R.mipmap.ic_launcher)//
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .transform(new CornersTransform(context))
                .error(res)
                .into(imageView);
    }

    public static void displayRadius(@NotNull final Context context, @NotNull final ImageView imageView, @NotNull String imgUrl, final int radius) {
        if (imageView == null) {
            return;
        }
        Glide
                .with(imageView.getContext())
                .load(imgUrl).centerCrop()
                .placeholder(R.mipmap.ic_launcher)//
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .transform(new CornersTransform(context, radius))
                .error(R.mipmap.ic_loading_image_error)
                .into(imageView);


    }

    public static void displayBlur(@NotNull final Context context, @NotNull final ImageView imageView, @NotNull String imgUrl) {
        if (imageView == null) {
            return;
        }
        Glide.with(imageView.getContext())
                .load(imgUrl).centerCrop()
                .placeholder(R.mipmap.ic_launcher)//
                .error(R.mipmap.ic_loading_image_error)//
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .transform(new BlurTransformation(context))

                .into(imageView);
    }

    public static void loadIntoUseFitWidth(Context context, final String imageUrl, final ImageView imageView) {
        Glide.with(context)
                .load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        if (imageView == null) {
                            return false;
                        }
                        if (imageView.getScaleType() != ImageView.ScaleType.FIT_XY) {
                            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                        }
                        ViewGroup.LayoutParams params = imageView.getLayoutParams();
                        int vw = imageView.getWidth() - imageView.getPaddingLeft() - imageView.getPaddingRight();
                        float scale = (float) vw / (float) resource.getIntrinsicWidth();
                        int vh = Math.round(resource.getIntrinsicHeight() * scale);
                        params.height = vh + imageView.getPaddingTop() + imageView.getPaddingBottom();
                        imageView.setLayoutParams(params);
                        return false;
                    }
                })
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_loading_image_error)
                .into(imageView);
    }
}
