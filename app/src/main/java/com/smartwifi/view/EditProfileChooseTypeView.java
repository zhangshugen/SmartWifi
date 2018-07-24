package com.smartwifi.view;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.orhanobut.logger.Logger;
import com.smartwifi.bean.EditProfileCategoryBean;
import com.smartwifi.manager.retrofit.RetrofitJsonManager;
import com.smartwifi.rxjava.RxJavaHttpHelper;
import com.smartwifi.utils.DialogUtils;
import com.smartwifi.widget.retrofithelper.rxschedulers.RxSchedulersHelper;
import com.smartwifi.widget.retrofithelper.rxsubscriber.ProgressObserver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/10
 * @Describe
 */

public class EditProfileChooseTypeView extends EditProfileChooseView {
    private AlertDialog chooseDialog;
    private List<String> chooseTypeList;
    private List<String> contentList;


    public EditProfileChooseTypeView(Context context) {
        this(context, null);
    }

    public EditProfileChooseTypeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        chooseTypeList = new ArrayList<>();
        contentList = new ArrayList<>();
        multiChooseMap = new HashMap<>();
    }

    public void setChooseTypeArray(String[] arr) {
        this.chooseTypeList = Arrays.asList(arr);
    }

    public void setChooseTypeArray(List<String> list) {
        this.chooseTypeList = list;
    }

    private boolean[] isSelectArray;
    private String[] chooseData;
    private Map<Integer, Boolean> multiChooseMap;

    @Override
    public void chooseViewAction() {
        if (chooseDialog != null && chooseData.length != 0) {
            chooseDialog.show();
            return;
        }
        RetrofitJsonManager.getInstance().getApiService().getEditProfileTypeCategory(mInfo.getDEFTABLE()).compose(RxSchedulersHelper.applyIoTransformer()).compose(RxJavaHttpHelper.<EditProfileCategoryBean>handleResult()).subscribe(new ProgressObserver<EditProfileCategoryBean>((AppCompatActivity) mContext, false, lifeCycleListener) {


            @Override
            public void _onNext(EditProfileCategoryBean editProfileCategoryBean) {
                if (editProfileCategoryBean == null || editProfileCategoryBean.ListData == null || editProfileCategoryBean.ListData.size() == 0)
                    return;
                List<EditProfileCategoryBean.ProfileCategoryBean> listData = editProfileCategoryBean.ListData;
                if (listData == null || listData.size() == 0) return;
                isSelectArray = new boolean[listData.size()];
                chooseData = new String[listData.size()];

                for (int i = 0; i < listData.size(); i++) {
                    chooseData[i] = listData.get(i).PHRASE_TEXT;
                    isSelectArray[i] = false;
                }

                if (!TextUtils.isEmpty(mInfo.getBAK1()) && mInfo.getBAK1().equals("M")) {
                    chooseDialog = new AlertDialog.Builder(mContext).setTitle(mInfo.getDEFTABLE())
                            .setMultiChoiceItems(chooseData, isSelectArray, new DialogInterface.OnMultiChoiceClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                    multiChooseMap.put(which, isChecked);
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Set<Map.Entry<Integer, Boolean>> entries = multiChooseMap.entrySet();
                                    Iterator<Map.Entry<Integer, Boolean>> iterator = entries.iterator();
                                    while (iterator.hasNext()) {
                                        Map.Entry<Integer, Boolean> next = iterator.next();
                                        Integer key = next.getKey();
                                        Boolean value = next.getValue();
                                        isSelectArray[key] = value;
                                        String selectString = chooseData[key];
                                        if (contentList.contains(selectString)) {
                                            contentList.remove(selectString);
                                        } else {
                                            contentList.add(selectString);
                                        }
                                    }
                                    content = "";
                                    for (int i = 0; i < contentList.size(); i++) {
                                        String text = contentList.get(i);
                                        if (i == contentList.size() - 1) {
                                            content = content + text;
                                        } else {
                                            content = content + text + ",";
                                        }
                                    }

                                    mInfo.setDEFFIELD(content);
                                    dialog.dismiss();

                                }
                            })
                            .create();

                } else {
                    chooseDialog = DialogUtils.showChooseDialog((AppCompatActivity) mContext, mInfo.getDEFTABLE(), normal, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            content = chooseData[which];
                            mInfo.setDEFFIELD(content);
                            Logger.d(content);
                            dialog.dismiss();
                        }
                    }, chooseData);
                }
                chooseDialog.show();
            }
        });

    }

    @Override
    public void onDestroy() {
        if (chooseDialog != null && chooseDialog.isShowing()) {
            chooseDialog.dismiss();
            chooseDialog = null;
        }
    }

}
