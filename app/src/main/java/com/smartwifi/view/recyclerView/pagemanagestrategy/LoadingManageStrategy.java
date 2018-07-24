package com.smartwifi.view.recyclerView.pagemanagestrategy;

import com.smartwifi.interfaces.PageErrorRetryListener;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/19
 * @Describe
 */

public abstract class LoadingManageStrategy {


    protected PageManageBuilder mBuilder;
    protected PageErrorRetryListener mPageErrorRetryListener;

    public LoadingManageStrategy(PageManageBuilder builder) {
        this.mBuilder = builder;
    }


    // void retryAction();

    public abstract void showPageContent();

    public abstract void showPageLoading(String loadingMsg);

    public abstract void showPageEmpty(String emptyMsg);

    public abstract void showPageError(String errorMsg);

    public void setBuilder(PageManageBuilder builder) {
        this.mBuilder = builder;
    }
    public void setPageErrorRetryListener(PageErrorRetryListener listener){
            this.mPageErrorRetryListener =listener;
    }
}
