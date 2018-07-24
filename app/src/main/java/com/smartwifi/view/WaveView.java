package com.smartwifi.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/10
 * @Describe
 */

public class WaveView extends View {
    private int width = 0;
    private int height = 0;
    private int baseLine = 100;// 基线，用于控制水位上涨的，这里是写死了没动，你可以不断的设置改变。
    private Paint mPaint;
    private int waveHeight = 50;// 波浪的最高度
    private int waveWidth;//波长
    private float offset = 0f;//偏移量
    private boolean isStart;

    public WaveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    /**
     * 不断的更新偏移量，并且循环。
     */
    private void updateXControl() {
        //设置一个波长的偏移
        ValueAnimator mAnimator = ValueAnimator.ofFloat(0, waveWidth);
        mAnimator.setInterpolator(new LinearInterpolator());
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatorValue = (float) animation.getAnimatedValue();
                offset = animatorValue;//不断的设置偏移量，并重画
                postInvalidate();
            }
        });
        mAnimator.setDuration(1000);
        mAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mAnimator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(getPath(), mPaint);
    }

    //初始化paint，没什么可说的。
    private void initView() {
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        waveWidth = width;
        //baseLine = height / 2;
        if (isStart)
            updateXControl();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


    }

    private int measureHeight(int measureSpec) {
        int result = 0;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            result = 75;
            if (mode == MeasureSpec.AT_MOST) {
                result = Math.min(result, size);
            }
        }
        return result;

    }

    private int measureWidth(int measureSpec) {
        int result = 0;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            result = 75;//根据自己的需要更改
            if (mode == MeasureSpec.AT_MOST) {
                result = Math.min(result, size);
            }
        }
        return result;

    }

    public void start() {
        isStart = true;
        requestLayout();
    }

    /**
     * 核心代码，计算path
     *
     * @return
     */
    private Path getPath() {
        int itemWidth = waveWidth / 2;//半个波长
        Path mPath = new Path();
        mPath.moveTo(-itemWidth * 3, baseLine);//起始坐标
        //核心的代码就是这里
        for (int i = -3; i < 2; i++) {
            int startX = i * itemWidth;
            mPath.quadTo(
                    startX + itemWidth / 2 + offset,//控制点的X,（起始点X + itemWidth/2 + offset)
                    getWaveHeigh(i),//控制点的Y
                    startX + itemWidth + offset,//结束点的X
                    baseLine//结束点的Y
            );//只需要处理完半个波长，剩下的有for循环自已就添加了。
        }
        //下面这三句话很重要，它是形成了一封闭区间，让曲线以下的面积填充一种颜色，大家可以把这3句话注释了看看效果。
        mPath.lineTo(width, height);
        mPath.lineTo(0, height);
        mPath.close();
        return mPath;
    }

    //奇数峰值是正的，偶数峰值是负数
    private int getWaveHeigh(int num) {
        if (num % 2 == 0) {
            return baseLine + waveHeight;
        }
        return baseLine - waveHeight;
    }

    public void stop() {
        isStart = false;
        requestLayout();
    }
}
