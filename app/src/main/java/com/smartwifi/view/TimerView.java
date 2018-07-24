package com.smartwifi.view;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;

import com.smartwifi.R;


/**

 */
public class TimerView extends android.support.v7.widget.AppCompatTextView {
    private long max;

    public TimerView(Context context) {
        super(context);
    }

    public TimerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setText("00:00");
        setTextColor(getResources().getColor(R.color.white));
        setTextSize(30);

        task = new TimeTask();
    }

    public void setMax(long max){
        this.max = max;

    }

    private Long startTime  = 0L;
    private Long pastTime  = 0L;
    private Handler handler = new Handler();

    public int getState() {
        return state;
    }

    public void setState(int newState){

        if (state == STATE_COUNTING  && newState == STATE_STOP){
            reset();
        }

    }

    private int state = STATE_NONE;
    public static  final  int STATE_NONE = 0;
    public static  final  int STATE_COUNTING = 1;
    public static  final  int STATE_PAUSE =2;
    public static  final  int STATE_STOP = 3;

    private TimeTask task;



    public void start(){
        startTime = System.currentTimeMillis();
        state = STATE_COUNTING;
        counting();
    }

    public void reStart(){
        pastTime  = 0L;
        start();
    }

    public void pause(){
        state = STATE_PAUSE;
        handler.removeCallbacks(task);
    }

    public void resume(){
        start();
    }

    public void reset(){
       stop();
        setText("00:00");
    }

    public void stop(){
        startTime = 0L;
        pastTime = 0L;
        state = STATE_STOP;
        handler.removeCallbacks(task);

    }

    private class TimeTask implements Runnable {

        @Override
        public void run() {

                pastTime = pastTime + 1000;
                updateTimeView();
                counting();

        }
    }

    /**
     *
     * @return 毫秒数
     */
    public long getTotalTime(){
        return pastTime;
    }

    private void updateTimeView() {
        Integer minutes = TimerView.this.getMinutesFromMilliseconds(pastTime);
        Integer seconds = TimerView.this.getSecondsFromMilliseconds(pastTime);
        TimerView.this.setText(String.format("%02d", minutes) + ":" + String.format("%02d", seconds));
    }

    private   int getMinutesFromMilliseconds(long milliseconds) {
        long time = milliseconds / 1000;
        return (int) ((time % 3600) / 60);
    }

    private   int getSecondsFromMilliseconds(long milliseconds) {
        long time = milliseconds / 1000;
        return (int) (time % 60);
    }



    private void counting(){
        if (state == STATE_COUNTING){
            handler.postDelayed(task,1000);
        }

    }
}
