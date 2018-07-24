package com.smartwifi.event;

import java.io.File;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/11
 * @Describe
 */

public class AudioRecordingStateEvent {
    public String viewTag;
    public int totalTime;
    public File audioFile;
    public boolean state;

    public AudioRecordingStateEvent(boolean b, File mAudioFile, int time, String viewTag) {
        this.state = b;
        this.audioFile = mAudioFile;
        this.totalTime = time;
        this.viewTag = viewTag;
    }
}
