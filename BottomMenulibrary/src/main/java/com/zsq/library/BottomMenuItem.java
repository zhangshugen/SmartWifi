package com.zsq.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;


/**
 * @author ChayChan
 * @description: 底部tab条目
 * @date 2018/7/9  9:14
 */

public class BottomMenuItem extends LinearLayout {

    private Context mContext;
    private int mIconNormalResourceId;//普通状态图标的资源id
    private int mIconSelectedResourceId;//选中状态图标的资源id
    private String mText;//文本
    private int mTextSize = 12;//文字大小 默认为12sp
    private int mTextColorNormal = 0xFF999999;    //描述文本的默认显示颜色
    private int mTextColorSelected = 0xFF46C01B;  //述文本的默认选中显示颜色
    private int mMarginTop = 0;//文字和图标的距离,默认0dp
    private boolean mOpenTouchBg = false;// 是否开启触摸背景，默认关闭
    private Drawable mTouchDrawable;//触摸时的背景
    private int mIconWidth;//图标的宽度
    private int mIconHeight;//图标的高度
    private int mItemPadding;//BottomBarItem的padding


    private ImageView mImageView;
    private TextView mTextView;

    private int mUnreadTextSize = 8; //未读数默认字体大小10sp
    private TextView mTvBadgeView;
    private Badge mBadge;
    private Badge.OnDragStateChangedListener mOnDragStateChangedListener;


    private boolean isShowBadgeHint;

    public BottomMenuItem(Context context) {
        this(context, null);
    }

    public BottomMenuItem(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomMenuItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mContext = context;

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.BottomMenuItem);

        mIconNormalResourceId = ta.getResourceId(R.styleable.BottomMenuItem_iconNormal, -1);
        mIconSelectedResourceId = ta.getResourceId(R.styleable.BottomMenuItem_iconSelected, -1);

        mText = ta.getString(R.styleable.BottomMenuItem_itemText);
        mTextSize = ta.getDimensionPixelSize(R.styleable.BottomMenuItem_itemTextSize, UIUtils.sp2px(mContext, mTextSize));

        mTextColorNormal = ta.getColor(R.styleable.BottomMenuItem_textColorNormal, mTextColorNormal);
        mTextColorSelected = ta.getColor(R.styleable.BottomMenuItem_textColorSelected, mTextColorSelected);

        mMarginTop = ta.getDimensionPixelSize(R.styleable.BottomMenuItem_itemMarginTop, UIUtils.dip2Px(mContext, mMarginTop));

        mOpenTouchBg = ta.getBoolean(R.styleable.BottomMenuItem_openTouchBg, mOpenTouchBg);
        isShowBadgeHint = ta.getBoolean(R.styleable.BottomMenuItem_isShowBadgeHint, false);
        mTouchDrawable = ta.getDrawable(R.styleable.BottomMenuItem_touchDrawable);

        mIconWidth = ta.getDimensionPixelSize(R.styleable.BottomMenuItem_iconWidth, 0);
        mIconHeight = ta.getDimensionPixelSize(R.styleable.BottomMenuItem_iconHeight, 0);
        mItemPadding = ta.getDimensionPixelSize(R.styleable.BottomMenuItem_itemPadding, 0);

        mUnreadTextSize = ta.getDimensionPixelSize(R.styleable.BottomMenuItem_unreadTextSize, mUnreadTextSize);

        ta.recycle();

        checkValues();
        init();
    }

    /**
     * 检查传入的值是否完善
     */
    private void checkValues() {
        if (mIconNormalResourceId == -1) {
            throw new IllegalStateException("您还没有设置默认状态下的图标，请指定iconNormal的图标");
        }

        if (mIconSelectedResourceId == -1) {
            throw new IllegalStateException("您还没有设置选中状态下的图标，请指定iconSelected的图标");
        }

        if (mOpenTouchBg && mTouchDrawable == null) {
            //如果有开启触摸背景效果但是没有传对应的drawable
            throw new IllegalStateException("开启了触摸效果，但是没有指定touchDrawable");
        }
    }

    private void init() {
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);

        View view = View.inflate(mContext, R.layout.item_bottom_bar, null);
        if (mItemPadding != 0) {
            //如果有设置item的padding
            view.setPadding(mItemPadding, mItemPadding, mItemPadding, mItemPadding);
        }
        mImageView = (ImageView) view.findViewById(R.id.iv_icon);
        mTextView = (TextView) view.findViewById(R.id.tv_text);
        mTvBadgeView = (TextView) view.findViewById(R.id.tv_badge_view);

        mImageView.setImageResource(mIconNormalResourceId);


        if (mIconWidth != 0 && mIconHeight != 0) {
            //如果有设置图标的宽度和高度，则设置ImageView的宽高
            FrameLayout.LayoutParams imageLayoutParams = (FrameLayout.LayoutParams) mImageView.getLayoutParams();
            imageLayoutParams.width = mIconWidth;
            imageLayoutParams.height = mIconHeight;
            mImageView.setLayoutParams(imageLayoutParams);
        }
        //   mUnreadTextSize
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);//设置底部文字字体大小

        mTextView.setTextColor(mTextColorNormal);//设置底部文字字体颜色
        mTextView.setText(mText);//设置标签文字

        LayoutParams textLayoutParams = (LayoutParams) mTextView.getLayoutParams();
        textLayoutParams.topMargin = mMarginTop;
        mTextView.setLayoutParams(textLayoutParams);

        if (mOpenTouchBg) {
            //如果有开启触摸背景
            setBackgroundDrawable(mTouchDrawable);
        }

        if (isShowBadgeHint) {
            mTvBadgeView.setVisibility(VISIBLE);
            getBadgeView(0, mTvBadgeView);
        }
        addView(view);
    }

    private Badge getBadgeView(int number, View target) {
        // add badge
        mBadge = new QBadgeView(mContext)
                .setBadgeNumber(number)
                .setBadgeTextSize((float) mUnreadTextSize, true)
                .setGravityOffset(0, 0, true)
                .setBadgeGravity(Gravity.END | Gravity.BOTTOM)
                .bindTarget(target);
        if (mOnDragStateChangedListener != null) {
            mBadge.setOnDragStateChangedListener(mOnDragStateChangedListener);
        }
        return mBadge;


    }

    public ImageView getImageView() {
        return mImageView;
    }

    public TextView getTextView() {
        return mTextView;
    }

    public void setIconNormalResourceId(int mIconNormalResourceId) {
        this.mIconNormalResourceId = mIconNormalResourceId;
    }

    public void setIconSelectedResourceId(int mIconSelectedResourceId) {
        this.mIconSelectedResourceId = mIconSelectedResourceId;
    }

    public void setTabSelect(boolean isSelected) {
        mImageView.setImageDrawable(getResources().getDrawable(isSelected ? mIconSelectedResourceId : mIconNormalResourceId));
        mTextView.setTextColor(isSelected ? mTextColorSelected : mTextColorNormal);
    }


    public void setShowBadgeHint(boolean showBadgeHint) {
        isShowBadgeHint = showBadgeHint;
    }

    public void setUnreadNum(int unreadNum) {
        if (mBadge != null) {
            mBadge.setBadgeNumber(unreadNum);
        }
    }

    public void setOnDragStateChangedListener(Badge.OnDragStateChangedListener onDragStateChangedListener) {
        this.mOnDragStateChangedListener = onDragStateChangedListener;
    }
}
