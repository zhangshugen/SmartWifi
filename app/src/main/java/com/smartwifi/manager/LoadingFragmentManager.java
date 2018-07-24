package com.smartwifi.manager;

import android.support.v4.app.FragmentActivity;

import com.smartwifi.widget.dialogfragment.LoadingDialogFragment;
import com.smartwifi.widget.mvvm.view.AppActivityManager;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/11
 * @Describe
 */

public class LoadingFragmentManager {
    private LoadingDialogFragment loadingDialogFragment;

    private LoadingFragmentManager() {

    }

    private static class LoadingFragmentManagerSingleton {
        private static final LoadingFragmentManager INSTANCE = new LoadingFragmentManager();

    }

    public static LoadingFragmentManager getInstance() {
        return LoadingFragmentManager.LoadingFragmentManagerSingleton.INSTANCE;
    }

    public void showLoadingDialog(FragmentActivity activity, String loadingText) {
        if (activity == null)
            activity = AppActivityManager.getAppActivityManager().currentActivity();
        if (activity == null)
            return;
        if (loadingDialogFragment != null && loadingDialogFragment.isShowing()) {
            loadingDialogFragment.dismiss();
        }
        if (loadingDialogFragment != null)
            loadingDialogFragment = null;
        loadingDialogFragment = new LoadingDialogFragment();
        loadingDialogFragment.setLoadingText(loadingText);
        loadingDialogFragment.show(activity.getSupportFragmentManager());
    }

    public void dismissLoadingDialog() {

        if (loadingDialogFragment == null) return;
        if (loadingDialogFragment.isShowing()) {
            loadingDialogFragment.dismiss();
            loadingDialogFragment = null;
        }
    }
}
