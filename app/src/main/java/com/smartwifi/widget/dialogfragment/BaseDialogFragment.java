package com.smartwifi.widget.dialogfragment;

import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.StyleRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.smartwifi.R;
import com.smartwifi.widget.mvvm.ViewInitializeLifeCycle;

import butterknife.ButterKnife;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/9
 * @Describe 替代diglog 使用dialogFragment, 这是封装的基类
 */


public abstract class BaseDialogFragment<D extends ViewDataBinding> extends DialogFragment implements ViewInitializeLifeCycle {
    public static final String TAG = "base_bottom_dialog";
    private static final float DEFAULT_DIM = 0.2f;
    /**
     * @Describe dialogFragment 从上往下出现
     */
    public static final int TYPE_TOP = 1;
    /**
     * @Describe dialogFragment 从下往上出现
     */
    public static final int TYPE_BOTTOM = 2;
    /**
     * @Describe dialogFragment 从左往右出现
     */
    public static final int TYPE_LEFT = 3;
    /**
     * @Describe dialogFragment 从右往左出现
     */
    public static final int TYPE_RIGHT = 4;
    private View mRootView;
    private Toolbar mToolbar;
    private boolean mDimmingEffect;
    private BlurDialogEngine mBlurEngine;
    private boolean mUseBlur;
    protected D mBinding;
    protected FragmentActivity mActivity;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, getStyle());
        mUseBlur = isUseBlur();
        if (mUseBlur) {
            mBlurEngine = new BlurDialogEngine(mActivity);
            if (mToolbar != null) {
                mBlurEngine.setToolbar(mToolbar);
            }
            int radius = getBlurRadius();
            if (radius <= 0) {
                throw new IllegalArgumentException("Blur radius must be strictly positive. Found : " + radius);
            }
            mBlurEngine.setBlurRadius(radius);

            float factor = getDownScaleFactor();
            if (factor <= 1.0) {
                throw new IllegalArgumentException("Down scale must be strictly greater than 1.0. Found : " + factor);
            }
            mBlurEngine.setDownScaleFactor(factor);

            mBlurEngine.setUseRenderScript(isRenderScriptEnable());

            mBlurEngine.debug(isDebugEnable());

            mBlurEngine.setBlurActionBar(isActionBarBlurred());

            mDimmingEffect = isDimmingEnable();
        }
    }


    @StyleRes
    public int getStyle() {
        switch (setDisplayAnimation()) {
            case TYPE_TOP:
                return R.style.BaseDialogUpFragmentStyle;
            case TYPE_BOTTOM:
                return R.style.BaseDialogBottomFragmentStyle;
            case TYPE_LEFT:
                return R.style.BaseDialogLeftFragmentStyle;
            case TYPE_RIGHT:
                return R.style.BaseDialogRightFragmentStyle;
            default:
                return R.style.BaseDialogBottomFragmentStyle;

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(getCancelOutside());
        mRootView = inflater.inflate(getLayoutRes(), container, false);
        if (isUseDataBinding()) {
            mBinding = DataBindingUtil.bind(mRootView);
            initView(mActivity, mRootView, null);
        } else {
            ButterKnife.bind(this, mRootView);
            initView(mActivity, mRootView, null);
        }


        initData(mActivity);
        initEvent(mActivity);


        return mRootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (FragmentActivity) context;
    }

    /**
     * @Describe 是否使用dataBinding
     */
    protected boolean isUseDataBinding() {
        return true;
    }

    @LayoutRes
    public abstract int getLayoutRes();

    /**
     * @Author zhangshurong
     * @Describe 是否使dialogFragment全屏
     */
    public boolean isHeightMatchParent() {
        return false;
    }

    @Override
    public void onStart() {
        super.onStart();

        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.dimAmount = getDimAmount();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        if (getHeight() > 0) {
            params.height = getHeight();
        } else {
            if (isHeightMatchParent()) {
                params.height = WindowManager.LayoutParams.MATCH_PARENT;
            } else {
                params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            }
        }
        if (mDimmingEffect) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }
        params.gravity = getGravity();

        window.setAttributes(params);
    }

    /**
     * @Author zhangshurong
     * @Describe 设置dialogFragment具体高度
     */

    public int getHeight() {
        return -1;
    }

    /**
     * @Author zhangshurong
     * @Describe 设置dialogFragment的位置
     */

    public int getGravity() {
        return Gravity.BOTTOM;
    }

    /**
     * @Author zhangshurong
     * @Describe 设置dialogFragment的背景模糊的透明度
     */
    public float getDimAmount() {
        return DEFAULT_DIM;
    }

    /**
     * @Author zhangshurong
     * @Describe 设置dialogFragment是否可以被返回键取消
     */
    public boolean getCancelOutside() {
        return true;
    }

    /**
     * @Author zhangshurong
     * @Describe 获取dialogFragment的TAG
     */
    public String getFragmentTag() {
        return TAG;
    }

    /**
     * @Author zhangshurong
     * @Describe 设置dialogFragment的出现方式
     */
    public int setDisplayAnimation() {
        return TYPE_BOTTOM;
    }


    /**
     * @Author zhangshurong
     * @Describe 显示dialogFragment
     */
    public void show(FragmentManager fragmentManager) {
        show(fragmentManager, TAG);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mUseBlur)
            mBlurEngine.onResume(getRetainInstance());
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (mUseBlur)
            mBlurEngine.onDismiss();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mUseBlur)
            mBlurEngine.onDetach();
    }

    @Override
    public void onDestroyView() {
        if (getDialog() != null) {
            getDialog().setDismissMessage(null);
        }
        super.onDestroyView();
    }

    /**
     * 设置toolsBar 使toolsBar 可以不用被高斯模糊
     */
    public void setToolbar(Toolbar toolBar) {
        mToolbar = toolBar;
        if (mBlurEngine != null) {
            mBlurEngine.setToolbar(toolBar);
        }
    }

    /**
     * 是否是debug 看渲染时间
     */
    protected boolean isDebugEnable() {
        return BlurDialogEngine.DEFAULT_DEBUG_POLICY;
    }

    protected float getDownScaleFactor() {
        return BlurDialogEngine.DEFAULT_BLUR_DOWN_SCALE_FACTOR;
    }

    /**
     * 设置 blur radius
     */
    protected int getBlurRadius() {
        return BlurDialogEngine.DEFAULT_BLUR_RADIUS;
    }

    /**
     * 启用或禁用调光效果。(变暗)
     */
    protected boolean isDimmingEnable() {
        return BlurDialogEngine.DEFAULT_DIMMING_POLICY;
    }

    /**
     * 是否模糊toolsBar
     */
    protected boolean isActionBarBlurred() {
        return BlurDialogEngine.DEFAULT_ACTION_BAR_BLUR;
    }

    /**
     * 是否使用快速的高斯模糊
     */
    protected boolean isRenderScriptEnable() {
        return BlurDialogEngine.DEFAULT_USE_RENDERSCRIPT;
    }

    /**
     * 是否将dialog背景模糊
     */
    public boolean isUseBlur() {
        return false;
    }

    public boolean isShowing() {
        return getDialog() != null
                && getDialog().isShowing();

    }
}
