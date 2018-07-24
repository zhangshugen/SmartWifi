package com.zsq.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import q.rorbin.badgeview.Badge;

/**
 * @author ChayChan
 * @description: 底部页签根节点
 * @date 2018/7/9  11:02
 */
public class BottomMenuLayout extends LinearLayout implements ViewPager.OnPageChangeListener {

    private static final String STATE_INSTANCE = "instance_state";
    private static final String STATE_ITEM = "state_item";


    private ViewPager mViewPager;
    private int mChildCount;//子条目个数
    private List<BottomMenuItem> mItemViews = new ArrayList<>();
    private int mCurrentItem;//当前条目的索引
    private boolean mSmoothScroll;

    public BottomMenuLayout(Context context) {
        this(context, null);
    }

    public BottomMenuLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomMenuLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.BottomMenuLayout);
        mSmoothScroll = ta.getBoolean(R.styleable.BottomMenuLayout_smoothScroll, false);
        ta.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        init();
    }

    @Override
    public void setOrientation(int orientation) {
        super.setOrientation(orientation);
    }

    public void setViewPager(ViewPager viewPager) {
        this.mViewPager = viewPager;
        init();
    }

    private void init() {
        mChildCount = getChildCount();

        if (mViewPager != null) {
            if (mViewPager.getAdapter().getCount() != mChildCount) {
                throw new IllegalArgumentException("LinearLayout的子View数量必须和ViewPager条目数量一致");
            }
        }

        for (int i = 0; i < mChildCount; i++) {
            if (getChildAt(i) instanceof BottomMenuItem) {
                BottomMenuItem bottomMenuItem = (BottomMenuItem) getChildAt(i);
                mItemViews.add(bottomMenuItem);
                //设置点击监听
                bottomMenuItem.setOnClickListener(new MyOnClickListener(i));
            } else {
                throw new IllegalArgumentException("BottomBarLayout的子View必须是BottomBarItem");
            }
        }

        mItemViews.get(mCurrentItem).setTabSelect(true);//设置选中项

        if (mViewPager != null) {
            //mViewPager.setOnPageChangeListener(this);//被add代替
            mViewPager.addOnPageChangeListener(this);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        resetState();
        mItemViews.get(position).setTabSelect(true);
        if (bottomMenuOnPageChangeListener != null) {
            bottomMenuOnPageChangeListener.onPageSelected(getBottomItem(position), mCurrentItem, position);
        }
        mCurrentItem = position;//记录当前位置
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private class MyOnClickListener implements OnClickListener {

        private int currentIndex;

        public MyOnClickListener(int i) {
            this.currentIndex = i;
        }

        @Override
        public void onClick(View v) {
            //回调点击的位置
            if (mViewPager != null) {
                //有设置viewPager
                if (currentIndex == mCurrentItem) {
                    //如果还是同个页签，使用setCurrentItem不会回调OnPageSelecte(),所以在此处需要回调点击监听
                    if (bottomMenuOnPageChangeListener != null) {
                        bottomMenuOnPageChangeListener.onPageSelected(getBottomItem(currentIndex), mCurrentItem, currentIndex);
                    }
                } else {
                    mViewPager.setCurrentItem(currentIndex, mSmoothScroll);
                }
            } else {
                //没有设置viewPager
                if (bottomMenuOnPageChangeListener != null) {
                    bottomMenuOnPageChangeListener.onPageSelected(getBottomItem(currentIndex), mCurrentItem, currentIndex);
                }

                updateTabState(currentIndex);
            }
        }
    }

    private void updateTabState(int position) {
        resetState();
        mCurrentItem = position;
        mItemViews.get(mCurrentItem).setTabSelect(true);
    }

    /**
     * 重置当前按钮的状态
     */
    private void resetState() {
        if (mCurrentItem < mItemViews.size()) {
            mItemViews.get(mCurrentItem).setTabSelect(false);
        }
    }

    public void setCurrentItem(int currentItem) {
        if (mViewPager != null) {
            mViewPager.setCurrentItem(currentItem, mSmoothScroll);
        } else {
            updateTabState(currentItem);
        }
    }

    /**
     * 设置未读数
     *
     * @param position  底部标签的下标
     * @param unreadNum 未读数
     */
    public void setUnread(int position, int unreadNum) {
        mItemViews.get(position).setUnreadNum(unreadNum);
    }

    public int getCurrentItem() {
        return mCurrentItem;
    }

    public void setOnDragStateChangedListener(int position,Badge.OnDragStateChangedListener listener){
        mItemViews.get(position).setOnDragStateChangedListener(listener);
    }

    public void setSmoothScroll(boolean smoothScroll) {
        this.mSmoothScroll = smoothScroll;
    }

    public BottomMenuItem getBottomItem(int position) {
        return mItemViews.get(position);
    }

    /**
     * @return 当View被销毁的时候，保存数据
     */
    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(STATE_INSTANCE, super.onSaveInstanceState());
        bundle.putInt(STATE_ITEM, mCurrentItem);
        return bundle;
    }

    /**
     * @param state 用于恢复数据使用
     */
    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            mCurrentItem = bundle.getInt(STATE_ITEM);
            //重置所有按钮状态
            resetState();
            //恢复点击的条目颜色
            mItemViews.get(mCurrentItem).setTabSelect(true);
            super.onRestoreInstanceState(bundle.getParcelable(STATE_INSTANCE));
        } else {
            super.onRestoreInstanceState(state);
        }
    }

    private BottomMenuOnPageChangeListener bottomMenuOnPageChangeListener;

    public interface BottomMenuOnPageChangeListener {
        void onPageSelected(BottomMenuItem bottomMenuItem, int previousPosition, int currentPosition);
    }

    public void setBottomMenuOnPageChangeListener(BottomMenuOnPageChangeListener bottomMenuOnPageChangeListener) {
        this.bottomMenuOnPageChangeListener = bottomMenuOnPageChangeListener;
    }
}
