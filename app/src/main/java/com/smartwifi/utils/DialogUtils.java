package com.smartwifi.utils;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.smartwifi.widget.dialogfragment.LoadingDialogFragment;

import java.util.List;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/9
 * @Describe
 */

public class DialogUtils {

    private static LoadingDialogFragment fragment;

    public static AlertDialog showChooseDialog(AppCompatActivity activity, String title, int normal, DialogInterface.OnClickListener listener, List<String> selectList) {
        String[] selectArr = new String[selectList.size()];
        for (int i = 0; i < selectList.size(); i++) {
            selectArr[i] = selectList.get(i);
        }
        return showChooseDialog(activity, title, normal, listener, selectArr);

    }

    public static AlertDialog showMultiChooseDialog(AppCompatActivity activity, String title, boolean[] normal, DialogInterface.OnMultiChoiceClickListener listener, List<String> selectList) {
        String[] selectArr = new String[selectList.size()];
        for (int i = 0; i < selectList.size(); i++) {
            selectArr[i] = selectList.get(i);
        }
        return showMultiChooseDialog(activity, title, normal, listener, selectArr);

    }

    public static AlertDialog showChooseDialog(AppCompatActivity activity, String title, int normal, DialogInterface.OnClickListener listener, String[] select) {
        return new AlertDialog.Builder(activity).setTitle(title)
                .setSingleChoiceItems(select, normal, listener).create();

    }

    public static AlertDialog showMultiChooseDialog(AppCompatActivity activity, String title, boolean[] normal, DialogInterface.OnMultiChoiceClickListener listener, String[] select) {
        return new AlertDialog.Builder(activity).setTitle(title)
                .setMultiChoiceItems(select, normal, listener)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                })
                .create();

    }


}
