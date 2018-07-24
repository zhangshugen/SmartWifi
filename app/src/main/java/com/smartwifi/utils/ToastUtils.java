package com.smartwifi.utils;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.smartwifi.R;


/**
 * Toast统一管理类
 */
public class ToastUtils {
    private static Toast toast;
    private static Toast initToast(CharSequence message, int duration) {
 

        if (toast == null) {
            toast = new Toast(UIUtils.getContext());
        }
        View view = LayoutInflater.from(UIUtils.getContext()).inflate(R.layout.toast_custom_tv, null);
        TextView tv = (TextView) view;
        tv.setText(TextUtils.isEmpty(message) ? "" : message);
        toast.setDuration(duration);
        toast.setView(view);
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER, 0, 0);
//        toast.show();

        return toast;
    }


    private static Toast initToast(Context ctx, CharSequence message, int duration) {
//        if (toast == null) {
//            toast = Toast.makeText(UIUtils.getContext(), message, duration);
//        } else {
//            toast.getView().setBackgroundResource(R.drawable.shape_text_golden_shadow);
//            toast.setText(message);
//            toast.setDuration(duration);
//        }


        if (toast == null) {
            toast = new Toast(ctx);
        }
        View view = LayoutInflater.from(ctx).inflate(R.layout.toast_custom_tv, null);
        TextView tv = (TextView) view;
        tv.setText(TextUtils.isEmpty(message) ? "" : message);
        toast.setDuration(duration);
        toast.setView(view);
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER, 0, 0);
//        toast.show();

        return toast;
    }


    /**
     * 短时间显示Toast
     *
     * @param message
     */
    public static void showShort(CharSequence message) {
        if (TextUtils.isEmpty(message)) return;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            initToast(message, Toast.LENGTH_SHORT).show();
        else Toast.makeText(UIUtils.getContext(), message, Toast.LENGTH_SHORT).show();

    }

    /**
     * 短时间显示Toast
     *
     * @param message
     */
    public static void showShort(Context ctx, CharSequence message) {
        if (TextUtils.isEmpty(message)) return;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            initToast(ctx, message, Toast.LENGTH_SHORT).show();
        else Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
    }


    /**
     * 短时间显示Toast
     *
     * @param strResId
     */
    public static void showShort(int strResId) {
//		Toast.makeText(context, strResId, Toast.LENGTH_SHORT).show();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            initToast(UIUtils.getContext().getResources().getText(strResId), Toast.LENGTH_SHORT).show();
        else Toast.makeText(UIUtils.getContext(), strResId, Toast.LENGTH_SHORT).show();
    }

    /**
     * 长时间显示Toast
     *
     * @param message
     */
    public static void showLong(CharSequence message) {
        if (TextUtils.isEmpty(message)) return;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            initToast(message, Toast.LENGTH_LONG).show();
        else Toast.makeText(UIUtils.getContext(), message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 长时间显示Toast
     *
     * @param strResId
     */
    public static void showLong(int strResId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            initToast(UIUtils.getContext().getResources().getText(strResId), Toast.LENGTH_LONG).show();
        else Toast.makeText(UIUtils.getContext(), strResId, Toast.LENGTH_SHORT).show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param message
     * @param duration
     */
    public static void show(CharSequence message, int duration) {
        if (TextUtils.isEmpty(message)) return;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            initToast(message, duration).show();
        else Toast.makeText(UIUtils.getContext(), message, duration).show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param strResId
     * @param duration
     */
    public static void show(Context context, int strResId, int duration) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            initToast(context.getResources().getText(strResId), duration).show();
        else Toast.makeText(context, strResId, duration).show();
    }

    /**
     * 显示有image的toast
     *
     * @param tvStr
     * @param imageResource
     * @return
     */
    public static Toast showToastWithImg(String tvStr, int imageResource) {

        if (toast == null) {
            toast = new Toast(UIUtils.getContext());
        }
        View view = LayoutInflater.from(UIUtils.getContext()).inflate(R.layout.toast_custom, null);
        TextView tv = (TextView) view.findViewById(R.id.toast_custom_tv);
        tv.setText(TextUtils.isEmpty(tvStr) ? "" : tvStr);

        ImageView iv = (ImageView) view.findViewById(R.id.toast_custom_iv);
        if (imageResource > 0) {
            //  iv.setVisibility(View.VISIBLE);
            iv.setImageResource(imageResource);
        } else {
           // iv.setImageResource(R.mipmap.ic_wdqy_pj_bumanyi);

        }
        toast.setView(view);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        return toast;

    }

    public static Toast showToastWithImg(String tvStr) {

        return showToastWithImg(tvStr, R.mipmap.ic_dxtz);
    }


}
