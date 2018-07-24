package com.smartwifi.part.commonpage.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.smartwifi.R;
import com.smartwifi.constant.Constant;

import java.io.File;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/30
 * @Describe
 */

public class PlayVideoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);
        init();
    }


    protected void init() {
        String path = "";
        int isNetUrlType = getIntent().getIntExtra("isNetUrlType", 1);
        String title = getIntent().getStringExtra("title");
        int videoThumbnailIsNet = getIntent().getIntExtra("videoThumbnailIsNet", 1);
        if (isNetUrlType == 1) {
            // 本地文件
            path = getIntent().getStringExtra("path");
        } else {
            // 网络
            // path = BuildConfig.BASE_URL + getIntent().getStringExtra("path");
        }
        JZVideoPlayerStandard player = (JZVideoPlayerStandard) findViewById(R.id.videoplayer);
        player.setUp(path, JZVideoPlayer.SCREEN_WINDOW_NORMAL, title);
        String videoVideoThumbnail = getIntent().getStringExtra("videoVideoThumbnail");
        if (videoThumbnailIsNet == Constant.FILE_FROM_TYPE.TYPE_LOCAL) {
            // 本地文件
            Glide.with(PlayVideoActivity.this).load(new File(videoVideoThumbnail)).into(player.thumbImageView);
        } else {
            // 网络
            Glide.with(PlayVideoActivity.this).load(videoVideoThumbnail).into(player.thumbImageView);
        }


    }

    @Override
    public void onBackPressed() {
        if (JZVideoPlayerStandard.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayerStandard.releaseAllVideos();
    }

}
