package com.smartwifi.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import com.smartwifi.R;
import com.smartwifi.base.BaseCustomView;
import com.smartwifi.databinding.ViewMultiFunctionButtonBinding;
import com.smartwifi.utils.UIUtils;


/**
 * Created by Administrator on 2018/7/12.
 */

public class MultiFunctionButtonView extends BaseCustomView<ViewMultiFunctionButtonBinding> {


    public MultiFunctionButtonView(Context context) {
        super(context);
    }

    public MultiFunctionButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.view_multi_function_button;
    }

    @Override
    protected void initView(Context context, AttributeSet attrs) {
        mBinding.setView(this);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MultiFunctionButtonView);
        String text = typedArray.getString(R.styleable.MultiFunctionButtonView_buttonText);
        int buttonBackgroundResource = typedArray.getResourceId(R.styleable.MultiFunctionButtonView_buttonBackgroundResource, -1);
        mBinding.btnSign.setText(text);

        int buttonBackgroundResourceColor = typedArray.getColor(R.styleable.MultiFunctionButtonView_buttonBackgroundColor, -1);
        if (buttonBackgroundResourceColor !=-1) {
            setButtonBackgroundColor(buttonBackgroundResourceColor);
        }else {
            if (buttonBackgroundResource !=-1) {
                mBinding.btnSign.setBackgroundResource(buttonBackgroundResource);
            }
        }
        int buttonTextColor = typedArray.getColor(R.styleable.MultiFunctionButtonView_buttonTextColor, -1);
        int textSize = typedArray.getInt(R.styleable.MultiFunctionButtonView_buttonTextSize, 15);
        if (textSize != 15) {
            mBinding.btnSign.setTextSize(textSize);
        }
        if (buttonTextColor !=-1) {
            mBinding.btnSign.setTextColor(buttonTextColor);
        }
        typedArray.recycle();
    }

    @Override
    public void initEvent(Context context) {

    }

    @Override
    public void initView(Context context, View rootView, AttributeSet attrs) {

    }

    @Override
    public void initData(Context context) {

    }


    public void setTextColor(int color) {
        mBinding.btnSign.setTextColor(UIUtils.getColor(color));
    }


    public void setText(String text) {
        mBinding.btnSign.setText(text);
    }


    public void setButtonBackgroundResource(int tag) {
        mBinding.btnSign.setBackgroundResource(tag);
    }

    public void setButtonBackgroundColor(int tag) {
        mBinding.btnSign.setBackgroundColor(tag);
    }

    @BindingAdapter("setButtonText")
    public static void setButtonText(MultiFunctionButtonView iv, String url) {
        iv.setText(url);
    }


    @BindingAdapter("buttonBackgroundResourceBinding")
    public static void buttonBackgroundResourceBinding(MultiFunctionButtonView iv, Drawable resid) {
        iv.mBinding.btnSign.setBackground(resid);
    }
}
