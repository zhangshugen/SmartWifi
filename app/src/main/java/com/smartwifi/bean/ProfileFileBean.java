package com.smartwifi.bean;

import android.databinding.BaseObservable;

import java.io.File;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/11
 * @Describe
 */

public class ProfileFileBean extends BaseObservable{
    public String fileName;
    public String filePath;
    public int fileType;
    public long recordTime;
    public String fileId;
    public File file;
}
