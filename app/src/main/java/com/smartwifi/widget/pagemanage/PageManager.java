package com.smartwifi.widget.pagemanage;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.smartwifi.R;


/**
 * Created by zhy on 15/8/27.
 */
public class PageManager {
    public static final int NO_LAYOUT_ID = 0;
    public static final int TYPE_CUSTOM_LAYOUT_ID = 1;
    public static final int TYPE_CUSTOM_NONE = 0;
    public static final int TYPE_CUSTOM_VIEW = 2;
    public static final int TYPE_CUSTOM_MESSAGE = 3;
    public static int BASE_LOADING_LAYOUT_ID = R.layout.pager_loading;
    public static int BASE_RETRY_LAYOUT_ID = R.layout.pager_error;
    public static int BASE_EMPTY_LAYOUT_ID = R.layout.pager_empty;
    private PageLayout mLoadingAndRetryLayout;

    private static Context appContext;

    public static void initInApp(Context appContext) {
        initInApp(appContext, 0, 0, 0);
    }


    /**
     * @param appContext
     * @param layoutIdOfEmpty
     * @param layoutIdOfLoading
     * @param layoutIdOfError
     */
    public static void initInApp(Context appContext, int layoutIdOfEmpty, int layoutIdOfLoading, int layoutIdOfError) {

        PageManager.appContext = appContext;
        if (layoutIdOfEmpty > 0) {
            BASE_EMPTY_LAYOUT_ID = layoutIdOfEmpty;
        }

        if (layoutIdOfLoading > 0) {
            BASE_LOADING_LAYOUT_ID = layoutIdOfLoading;
        }

        if (layoutIdOfError > 0) {
            BASE_RETRY_LAYOUT_ID = layoutIdOfError;
        }


    }


    /**
     * @param container              必须为activity或者view.如果是view,则该view对象必须有parent
     * @param retryAction            点击重试的动作,注意,只需要关注有网络的情况,无网络状态时已经封装好:弹出对话框询问用户是否去设置网络
     * @param isShowLoadingOrContent 第一次是显示loading(true)还是content(false)
     * @return 当前页面的状态管理器
     */
    public static PageManager init(final Object container, boolean isShowLoadingOrContent, final Runnable retryAction) {
        PageManager manager = generate(container, new PageListener() {
            @Override
            public void setRetryEvent(View retryView) {
                retryView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!isNetWorkAvailable(appContext)) {
                            showNoNetWorkDlg(container);
                        } else {
                            retryAction.run();
                        }
                    }
                });
            }
        });
        if (isShowLoadingOrContent) {
            manager.showLoading();
        } else {
            manager.showContent();
        }
        return manager;
    }

    public static PageManager init(final Object container, final Runnable retryAction) {
        PageManager manager = generate(container, new PageListener() {
            @Override
            public void setRetryEvent(View retryView) {
                retryView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!isNetWorkAvailable(appContext)) {
                            showNoNetWorkDlg(container);
                        } else {
                            retryAction.run();
                        }
                    }
                });
            }
        });


        return manager;
    }

    /**
     * @param container   必须为activity,fragment或者view.如果是view,则该view对象必须有parent
     * @param retryAction
     * @return
     */
    public static PageManager init(final Object container, final String retryMsg, final String emptyMsg, final String loadingMsg, final Runnable retryAction) {
        PageManager manager = generate(container, new PageListener() {
            @Override
            public String generateEmptyLayoutMessage() {
                return emptyMsg;
            }

            @Override
            public String generateLoadingMessage() {
                return loadingMsg;
            }

            @Override
            public String generateRetryMessage() {
                return retryMsg;
            }

            @Override
            public void setRetryEvent(View retryView) {
                retryView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!isNetWorkAvailable(appContext)) {
                            showNoNetWorkDlg(container);
                        } else {
                            retryAction.run();
                        }
                    }
                });

            }

        });

        return manager;
    }

    public static PageManager init(final Object container, final View retry, final View empty, final View loading, final Runnable retryAction) {
        PageManager manager = generate(container, new PageListener() {
            @Override
            public View generateRetryLayout() {
                return retry;
            }

            @Override
            public View generateEmptyLayout() {
                return empty;
            }

            @Override
            public View generateLoadingLayout() {
                return loading;
            }

            @Override
            public void setRetryEvent(View retryView) {
                retryView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!isNetWorkAvailable(appContext)) {
                            showNoNetWorkDlg(container);
                        } else {
                            retryAction.run();
                        }
                    }
                });

            }

        });

        return manager;
    }

    public static PageManager init(final Object container, final int retry, final int empty, final int loading, final Runnable retryAction) {
        PageManager manager = generate(container, new PageListener() {
            @Override
            public int generateEmptyLayoutId() {
                return empty;
            }

            @Override
            public int generateRetryLayoutId() {
                return retry;
            }

            @Override
            public int generateLoadingLayoutId() {
                return loading;
            }

            @Override
            public void setRetryEvent(View retryView) {
                retryView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!isNetWorkAvailable(appContext)) {
                            showNoNetWorkDlg(container);
                        } else {
                            retryAction.run();
                        }
                    }
                });

            }

        });

        return manager;
    }

    public void setCustomLoadingView(View view) {
        mLoadingAndRetryLayout.setLoadingView(view);
    }

    public void setCustomRetryView(View view) {
        mLoadingAndRetryLayout.setRetryView(view);
    }

    public void setCustomEmptyView(View view) {
        mLoadingAndRetryLayout.setEmptyView(view);

    }

    public void setCustomLoadingLayoutId(int layoutId) {
        mLoadingAndRetryLayout.setLoadingView(layoutId);
    }

    public void setCustomRetryLayoutId(int layoutId) {
        mLoadingAndRetryLayout.setRetryView(layoutId);
    }

    public void setCustomEmptyLayoutId(int layoutId) {
        mLoadingAndRetryLayout.setEmptyView(layoutId);

    }

    public void showLoading(String loading) {
        if (TextUtils.isEmpty(loading)) {
            showLoading();
        } else {
            mLoadingAndRetryLayout.setCurrentLoadingMessageLayout(loading);
        }
    }

    public void showError(String retry) {
        if (TextUtils.isEmpty(retry)) {
            showError();
        } else {
            mLoadingAndRetryLayout.setCurrentRetryMessageViewLayout(retry);
        }
    }

    public void showEmpty(String empty) {
        if (TextUtils.isEmpty(empty)) {
            showEmpty();
        } else {
            mLoadingAndRetryLayout.setCurrentEmptyMessageLayout(empty);
        }
    }

    public void setEmptyLayoutParam() {
        View emptyView = mLoadingAndRetryLayout.getEmptyView();
        LinearLayout llContent = (LinearLayout) emptyView.findViewById(R.id.ll_content);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        llContent.setLayoutParams(params);
    }

    public void setRetryLayoutParam() {
        View retryView = mLoadingAndRetryLayout.getRetryView();
        LinearLayout llContent = (LinearLayout) retryView.findViewById(R.id.ll_content);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        llContent.setLayoutParams(params);
    }

    public void seLoadingLayoutParam() {
        View loadingView = mLoadingAndRetryLayout.getLoadingView();
        LinearLayout llContent = (LinearLayout) loadingView.findViewById(R.id.ll_content);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        llContent.setLayoutParams(params);
    }

    public void showLoading() {
        mLoadingAndRetryLayout.showLoading();
    }


    public void showError() {
        mLoadingAndRetryLayout.showRetry();
    }

    public void showContent() {
        mLoadingAndRetryLayout.showContent();
    }

    public void showEmpty() {
        mLoadingAndRetryLayout.showEmpty();
    }

    public static boolean isNetWorkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) {
            return false;
        } else {
            NetworkInfo info = connectivityManager.getActiveNetworkInfo();
            if (info == null) {
                return false;
            } else {
                if (info.isAvailable()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 当判断当前手机没有网络时选择是否打开网络设置
     */
    public static AlertDialog showNoNetWorkDlg(final Object container) {
        AlertDialog dialog = null;
        Context context = null;


        if (container instanceof Activity) {
            context = (Activity) container;

        } else if (container instanceof Fragment) {
            context = ((Fragment) container).getActivity();

        } else if (container instanceof View) {
            context = ((View) container).getContext();
        }

        try {

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            final Activity finalActivity = (Activity) context;

            dialog = builder        //
                    .setTitle("提示")            //
                    .setMessage("当前无网络").setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 跳转到系统的网络设置界面
                            Intent intent = null;
                            // 先判断当前系统版本
                            if (android.os.Build.VERSION.SDK_INT > 10) {  // 3.0以上
                                intent = new Intent(android.provider.Settings.ACTION_SETTINGS);
                            } else {
                                intent = new Intent();
                                intent.setClassName("com.android.settings", "com.android.settings.WirelessSettings");
                            }
                            finalActivity.startActivity(intent);
                            dialog.dismiss();
                        }
                    }).setNegativeButton("知道了", null).show();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dialog;
    }


    public PageListener DEFAULT_LISTENER = new PageListener() {
        @Override
        public void setRetryEvent(View retryView) {

        }
    };


    public PageManager(Object activityOrView, PageListener listener) {
        if (listener == null) listener = DEFAULT_LISTENER;

        ViewGroup contentParent = null;
        Context context;
        if (activityOrView instanceof Activity) {
            Activity activity = (Activity) activityOrView;
            context = activity;
            contentParent = (ViewGroup) activity.findViewById(android.R.id.content);
        } else if (activityOrView instanceof Fragment) {

            Fragment fragment = (Fragment) activityOrView;
            context = fragment.getActivity();
            contentParent = (ViewGroup) (fragment.getView().getParent());

            throw new IllegalArgumentException("the support for fragment has been canceled,please use give me a view object which has a parent");

        } else if (activityOrView instanceof View) {
            View view = (View) activityOrView;
            contentParent = (ViewGroup) (view.getParent());
            if (contentParent == null) {
                throw new IllegalArgumentException("the view must already has a parent ");
            }
            context = view.getContext();
        } else {
            throw new IllegalArgumentException("the container's type must be Fragment or Activity or a view ");
        }


        int childCount = contentParent.getChildCount();
        //get contentParent
        int index = 0;
        View oldContent;
        if (activityOrView instanceof View) {
            oldContent = (View) activityOrView;
            for (int i = 0; i < childCount; i++) {
                if (contentParent.getChildAt(i) == oldContent) {
                    index = i;
                    break;
                }
            }
        } else {
            oldContent = contentParent.getChildAt(0);
        }
        contentParent.removeView(oldContent);
        //setup content layout
        PageLayout loadingAndRetryLayout = new PageLayout(context);
        ViewGroup.LayoutParams lp = oldContent.getLayoutParams();
        contentParent.addView(loadingAndRetryLayout, index, lp);
        loadingAndRetryLayout.setContentView(oldContent);
        // setup loading,retry,empty layout
        setupLoadingLayout(listener, loadingAndRetryLayout);
        setupRetryLayout(listener, loadingAndRetryLayout);
        setupEmptyLayout(listener, loadingAndRetryLayout);
        //callback
        listener.setRetryEvent(loadingAndRetryLayout.getRetryView());
        listener.setLoadingEvent(loadingAndRetryLayout.getLoadingView());
        listener.setEmptyEvent(loadingAndRetryLayout.getEmptyView());
        mLoadingAndRetryLayout = loadingAndRetryLayout;
    }

    private void setupEmptyLayout(PageListener listener, PageLayout loadingAndRetryLayout) {
        switch (listener.isSetEmptyLayout()) {
            case TYPE_CUSTOM_LAYOUT_ID:
                loadingAndRetryLayout.setEmptyView(listener.generateEmptyLayoutId());
                break;
            case TYPE_CUSTOM_VIEW:
                loadingAndRetryLayout.setEmptyView(listener.generateEmptyLayout());
                break;
            case TYPE_CUSTOM_MESSAGE:
                loadingAndRetryLayout.setEmptyCustomMessageLayout(BASE_EMPTY_LAYOUT_ID, listener.generateEmptyLayoutMessage());
                break;
            case TYPE_CUSTOM_NONE:
                if (BASE_EMPTY_LAYOUT_ID != NO_LAYOUT_ID)
                    loadingAndRetryLayout.setEmptyView(BASE_EMPTY_LAYOUT_ID);
                break;

        }
    }

    private void setupLoadingLayout(PageListener listener, PageLayout loadingAndRetryLayout) {
        switch (listener.isSetLoadingLayout()) {
            case TYPE_CUSTOM_LAYOUT_ID:
                loadingAndRetryLayout.setLoadingView(listener.generateLoadingLayoutId());
                break;
            case TYPE_CUSTOM_VIEW:
                loadingAndRetryLayout.setLoadingView(listener.generateLoadingLayout());
                break;
            case TYPE_CUSTOM_MESSAGE:
                loadingAndRetryLayout.setLoadingCustomMessageLayout(BASE_LOADING_LAYOUT_ID, listener.generateLoadingMessage());
                break;
            case TYPE_CUSTOM_NONE:
                if (BASE_LOADING_LAYOUT_ID != NO_LAYOUT_ID)
                    loadingAndRetryLayout.setLoadingView(BASE_LOADING_LAYOUT_ID);
                break;

        }
    }

    private void setupRetryLayout(PageListener listener, PageLayout loadingAndRetryLayout) {

        switch (listener.isSetRetryLayout()) {
            case TYPE_CUSTOM_LAYOUT_ID:
                loadingAndRetryLayout.setRetryView(listener.generateRetryLayoutId());
                break;
            case TYPE_CUSTOM_VIEW:
                loadingAndRetryLayout.setRetryView(listener.generateRetryLayout());
                break;
            case TYPE_CUSTOM_MESSAGE:
                loadingAndRetryLayout.setRetryCustomMessageViewLayout(BASE_RETRY_LAYOUT_ID, listener.generateRetryMessage());
                break;
            case TYPE_CUSTOM_NONE:
                if (BASE_RETRY_LAYOUT_ID != NO_LAYOUT_ID)
                    loadingAndRetryLayout.setRetryView(BASE_RETRY_LAYOUT_ID);
                break;

        }
    }

    public static PageManager generate(Object activityOrView, PageListener listener) {
        return new PageManager(activityOrView, listener);
    }


}
