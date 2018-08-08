package com.smartwifi.part.splash.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.smartwifi.utils.IntentController;
import com.smartwifi.utils.SPController;
import com.smartwifi.utils.UIUtils;

/**
 * 启动屏
 * Created by wificityios on 2018/7/13
 */

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_welcome);

        UIUtils.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (SPController.getWelcomeInData()) {
                    SPController.saveWelcomeInData(false);
                    IntentController.toWelcomeActivity(SplashActivity.this);
                } else {
                    IntentController.toGuideActivity(SplashActivity.this);
                    //IntentController.toWelcomeActivity(SplashActivity.this);
                }
            }
        }, 2000);
    }
}
