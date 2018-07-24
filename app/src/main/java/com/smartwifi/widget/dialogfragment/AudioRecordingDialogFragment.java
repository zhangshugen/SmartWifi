package com.smartwifi.widget.dialogfragment;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.smartwifi.R;
import com.smartwifi.event.AudioRecordingStateEvent;
import com.smartwifi.manager.AudioManager;
import com.smartwifi.view.TimerView;
import com.smartwifi.view.WaveView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/9
 * @Describe
 */

public class AudioRecordingDialogFragment extends BaseDialogFragment {

    private static final int DISTANCE_Y_CANCEL = 50;
    @BindView(R.id.tv_time)
    TimerView tvTime;
    @BindView(R.id.view)
    WaveView waveView;
    @BindView(R.id.tv_record)
    ImageView tvRecord;


    @Override
    public void initView(Context context, View rootView, AttributeSet attrs) {
        EventBus.getDefault().register(this);
    }


    @Override
    public void initEvent(Context context) {
        tvRecord.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int x = (int) motionEvent.getX();
                int y = (int) motionEvent.getY();
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        AudioManager.getInstance().handlerRecordActionDown();
                        startTimeView();
                        break;
                    case MotionEvent.ACTION_MOVE:

                        AudioManager.getInstance().handlerRecordActionMove();
                        break;
                    case MotionEvent.ACTION_UP:
                        AudioManager.getInstance().handlerRecordActionUp();
                        resetTimeView();
                        break;

                }
                return true;
            }
        });

    }

    @Override
    protected boolean isUseDataBinding() {
        return false;
    }

    private boolean wantToCancel(int x, int y) {

        //如果上下滑出 button  加上我们自定义的距离
        if (y < -DISTANCE_Y_CANCEL || y > getHeight() + DISTANCE_Y_CANCEL) {
            return true;
        }
        return false;
    }


    @Override
    public void initData(Context context) {

    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_audio_record;
    }

    @Override
    public boolean isUseBlur() {
        return true;
    }

    @Override
    public int setDisplayAnimation() {
        return TYPE_TOP;
    }

    @Override
    public int getGravity() {
        return Gravity.CENTER;
    }


    @Override
    public void show(FragmentManager fragmentManager) {
        super.show(fragmentManager);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //if (mMediaRecorder != null) mMediaRecorder = null;
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void dismiss() {
        super.dismiss();

    }

    @Override
    public void onPause() {
        super.onPause();
        AudioManager.getInstance().mediaRecorderStop();
        resetTimeView();
    }

    private void resetTimeView() {
        if (waveView != null)
            waveView.stop();
        if (tvTime != null)
            tvTime.reset();
    }

    public void startTimeView() {
        // 正在录制
        waveView.start();
        tvTime.start();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void ButtomMuneShowStates(AudioRecordingStateEvent e) {
        dismiss();
    }
}