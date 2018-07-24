package com.smartwifi.utils;

import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by wificityios on 2018/7/20.
 */

public class CountUtils {
    public static void getCount(final TextView tvCount){
        new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                //tvCount.setEnabled(false);setClickable
                tvCount.setClickable(false);
                tvCount.setText(millisUntilFinished / 1000 + "秒");
            }

            @Override
            public void onFinish() {
                tvCount.setClickable(true);
                tvCount.setText("重新获取");

            }
        }.start();
    }
}
