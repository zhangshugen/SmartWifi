package com.smartwifi.manager;

import android.Manifest;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;

import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;
import com.smartwifi.R;
import com.smartwifi.event.AudioRecordingStateEvent;
import com.smartwifi.utils.FileUtils;
import com.smartwifi.utils.ToastUtils;
import com.smartwifi.utils.UIUtils;
import com.smartwifi.widget.dialogfragment.AudioRecordingDialogFragment;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/9
 * @Describe
 */

public class AudioManager {
    //录音API
    private MediaRecorder mMediaRecorder;
    //录音所保存的文件
    private File mAudioFile;
    private static final int RECORD_SUCCESS = 3;
    private static final int RECORD_FAIL = 4;
    private static final int RECORD_TOO_SHORT = 5;
    private static final int RECORD_CANCEL = 2;
    private static final int STATE_NORMAL = 6;
    private static final int STATE_RECORDING = 7;
    private static final int STATE_WANT_TO_CANCEL = 8;


    private boolean isRecording;
    private int mCurState;
    private long startTime;
    private MediaPlayer mediaPlayer;
    private boolean isPlaying;
    private ExecutorService mExecutorService;
    private boolean isRecordUrlNet;
    private AudioRecordingDialogFragment fragment;
    private String mViewTag;

    public void recordingSuccess(int time) {
        EventBus.getDefault().post(new AudioRecordingStateEvent(true, mAudioFile, time, mViewTag));
    }

    public void cancelRecording() {
        mediaRecorderStop();
        deleteRecodingFile();

    }

    public void setTag(String viewTga) {
        this.mViewTag = viewTga;
    }

    public void handlerRecordActionUp() {
        //记录停止时间
        long endTime = System.currentTimeMillis();
        //录音时间处理，比如只有大于2秒的录音才算成功
        int time = (int) (endTime - startTime);
        startTime = 0;
        if (!isRecording || (time <= 1000)) {
            ToastUtils.showShort("录音时间不能少于一秒");
            cancelRecording();
        } else if (mCurState == STATE_RECORDING) {//正常录制结束

            recordingSuccess(time);
            mediaRecorderStop();

        } else if (mCurState == STATE_WANT_TO_CANCEL) {
            ToastUtils.showShort("录音时间太短");
            cancelRecording();
        }


    }

    public void handlerRecordActionMove() {
        if (isRecording) {
                          /*  //根据想x,y的坐标，判断是否想要取消
                            if (wantToCancel(x, y)) {
                                changeState(STATE_WANT_TO_CANCEL);
                            } else {

                            }*/
            changeState(STATE_RECORDING);
        }
    }

    public void handlerRecordActionDown() {
        mediaRecorderStart();
    }


    private static class RecordingManageSingleton {
        private static final AudioManager INSTANCE = new AudioManager();

    }

    public static AudioManager getInstance() {
        return AudioManager.RecordingManageSingleton.INSTANCE;
    }


    /**
     * @description 翻放录音相关资源
     */
    private void releaseRecorder() {
        if (null != mMediaRecorder) {
            mMediaRecorder.setOnErrorListener(null);
            try {

                mMediaRecorder.stop();
            } catch (Exception e) {

            } finally {
                isRecording = false;
                mMediaRecorder.reset();
                mMediaRecorder.release();
                mMediaRecorder = null;
            }
        }
    }

    /**
     * @description 录音操作
     */
    private void recordOperation() {
        //创建MediaRecorder对象
        mMediaRecorder = new MediaRecorder();
        //配置mMediaRecorder相应参数
        //从麦克风采集声音数据
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        //设置保存文件格式为MP4
        //设置音频格式
        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
        //设置采样频率,44100是所有安卓设备都支持的频率,频率越高，音质越好，当然文件越大
        mMediaRecorder.setAudioSamplingRate(44100);
        //设置声音数据编码格式,音频通用格式是AAC
        mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
        //设置编码频率
        mMediaRecorder.setAudioEncodingBitRate(96000);
        //创建录音文件,.m4a为MPEG-4音频标准的文件的扩展名
        mAudioFile = FileUtils.getRecodingFile();
        try {
            //设置录音保存的文件
            mMediaRecorder.setOutputFile(mAudioFile.getAbsolutePath());
            //开始录音
            mMediaRecorder.prepare();
            mMediaRecorder.start();

            isRecording = true;
            startTime = System.currentTimeMillis();
        } catch (Exception e) {
            e.printStackTrace();
            recordFail();
        }
    }

    public void mediaRecorderStart() {
        recordOperation();
    }

    public void reset() {
    }


    public void mediaRecorderStop() {
        if (mMediaRecorder != null && isRecording) {
            releaseRecorder();
        }
    }

    public void deleteRecodingFile() {
        FileUtils.deleteFile(mAudioFile);
    }

    /**
     * @description 录音失败处理
     */
    private void recordFail() {
        ToastUtils.showShort("未知异常,请重试");
        mediaRecorderStop();
        deleteRecodingFile();
    }

    //改变状态
    private void changeState(int state) {
        if (mCurState != state) {
            mCurState = state;
            switch (state) {
                case STATE_NORMAL:

                    break;
                case STATE_RECORDING:
                    if (isRecording) {

                    }
                    break;
                case STATE_WANT_TO_CANCEL:
                    // 想要取消录音
                    break;
            }
        }
    }

    public void playRecord(String path) {
        isPlaying = false;
        isRecordUrlNet = true;

        playAudio(path);
    }

    public void playRecordNetUrl(String netUrl, FragmentActivity activity) {
    /*    if (!netUrl.startsWith(BuildConfig.BASE_URL)) {
            netUrl = BuildConfig.BASE_URL + netUrl;
        }*/
        isPlaying = false;

        LoadingFragmentManager.getInstance().showLoadingDialog(activity, UIUtils.getString(R.string.open_file));
        isRecordUrlNet = true;
        playAudio(netUrl);
    }

    private void playAudio(final String path) {
        if (!TextUtils.isEmpty(path) && !isPlaying) {
            if (isPlaying) {
                playEndOrFail(true);
            } else {

                if (mExecutorService == null)
                    mExecutorService = Executors.newSingleThreadExecutor();
                mExecutorService.submit(new Runnable() {
                    @Override
                    public void run() {
                        startPlay(path);
                    }
                });
            }
        }
        isPlaying = !isPlaying;
    }

    private void startPlay(String path) {
        try {
            //初始化播放器
            mediaPlayer = new MediaPlayer();
            //设置播放音频数据文件
            mediaPlayer.setDataSource(path);
            //设置播放监听事件
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    //播放完成
                    playEndOrFail(true);
                }
            });
            //播放发生错误监听事件
            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                    playEndOrFail(false);
                    return true;
                }
            });
            //是否循环播放
            mediaPlayer.setLooping(false);
            //准备及播放
            mediaPlayer.prepareAsync();

            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mediaPlayer.start();
                    dismissLoadingDialog();
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
            //播放失败正理
            playEndOrFail(false);
        }

    }

    private void playEndOrFail(boolean isEnd) {
        isPlaying = false;
        if (isEnd) {
        } else {
            ToastUtils.showShort("播放失败,请重试");
        }
        if (null != mediaPlayer) {
            try {
                mediaPlayer.setOnCompletionListener(null);
                mediaPlayer.setOnErrorListener(null);
                mediaPlayer.stop();
            } catch (Exception e) {

            } finally {
                mediaPlayer.reset();
                mediaPlayer.release();
                mediaPlayer = null;
                dismissLoadingDialog();
            }

        }
    }

    void dismissLoadingDialog() {
        if (!isRecordUrlNet) return;
        UIUtils.postTaskSafely(new Runnable() {
            @Override
            public void run() {
                LoadingFragmentManager.getInstance().dismissLoadingDialog();

            }
        });
    }

    public void stopRecordPlayer() {
        playEndOrFail(true);
    }

    public DialogFragment showRecordDialogFragment(final FragmentActivity activity) {
        PermissionsUtil.requestPermission(UIUtils.getContext(), new PermissionListener() {
                    @Override
                    public void permissionGranted(@NonNull String[] permissions) {
                        if (fragment == null) {
                            fragment = new AudioRecordingDialogFragment();
                        }
                        fragment.show(activity.getSupportFragmentManager());
                    }

                    @Override
                    public void permissionDenied(@NonNull String[] permissions) {
                        ToastUtils.showShort("请到设置-权限管理中开启");

                    }
                }, Manifest.permission.RECORD_AUDIO,
                Manifest.permission.CAMERA
        );

        return fragment;

    }

    public DialogFragment showRecordDialogFragment(final FragmentActivity activity, String viewTag) {
        setTag(viewTag);
        return showRecordDialogFragment(activity);

    }

    public void dismissRecordDialogFragment() {
        if (fragment == null) return;
        if (fragment.isShowing()) fragment.dismiss();
        fragment = null;
    }

    public void onDetory() {
        dismissRecordDialogFragment();
    }
}
