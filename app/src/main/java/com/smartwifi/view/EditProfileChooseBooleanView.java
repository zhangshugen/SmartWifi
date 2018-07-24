package com.smartwifi.view;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.orhanobut.logger.Logger;
import com.smartwifi.bean.CommonEditProfileBean;
import com.smartwifi.utils.DialogUtils;

import java.util.Arrays;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/17
 * @Describe
 */

public class EditProfileChooseBooleanView extends EditProfileChooseView {
    private AlertDialog chooseDialog;
    private String[] chooseTypeArray;

    public EditProfileChooseBooleanView(Context context) {
        super(context);
    }

    public EditProfileChooseBooleanView(Context context, AttributeSet attrs) {
        super(context, attrs);
        chooseTypeArray = new String[]{"是", "否"};
    }

    @Override
    public void init(CommonEditProfileBean info) {
        if (TextUtils.isEmpty(info.getDEFAULT_VALUE())) {
            info.setDEFFIELD("否");
            info.setDEFAULT_VALUE("否");
        }
        super.init(info);
    }

    @Override
    public void chooseViewAction() {
        if (chooseDialog == null) {
            int normal = 0;
            if (!TextUtils.isEmpty(mInfo.getDEFAULT_VALUE())) {
                normal = Arrays.asList(chooseTypeArray).indexOf(mInfo.getDEFAULT_VALUE());
            }

            chooseDialog = DialogUtils.showChooseDialog((AppCompatActivity) mContext, mInfo.getTEXT_PROMPT(), normal, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    content = chooseTypeArray[which];
                    mInfo.setDEFFIELD(content);
                    Logger.d(content);
                    dialog.dismiss();
                }
            }, chooseTypeArray);
        }
        chooseDialog.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (chooseDialog!=null && chooseDialog.isShowing()){
            chooseDialog.dismiss();
        }
        chooseDialog=null;
    }
}
