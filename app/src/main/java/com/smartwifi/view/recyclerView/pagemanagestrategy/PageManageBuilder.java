package com.smartwifi.view.recyclerView.pagemanagestrategy;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.smartwifi.widget.databindingadapter.BaseDataBindingAdapter;


/**
 * @Author zhangshurong
 * @CreateTime 2018/7/19
 * @Describe
 */

public class PageManageBuilder {


    private final Context mContext;
    /**
     * @Describe adapter
     */
    private BaseDataBindingAdapter adapter;
    /**
     * @Describe 返回retrofit 的访问者
     */

    /**
     * @Describe 设置数据为null的时候是否显示空的页面
     */

    private boolean isShowEmptyStatus = true;
    /**
     * @Describe 设置数据为error的时候是否显示空的页面
     */

    private boolean isShowErrorStatus = true;
    /**
     * @Describe 使用pageManager 或者使用 head的方式刷新
     */
    private boolean choosePageManager = true;

    /**
     * @Describe 是否可以下拉刷新
     */

    private boolean isRefresh = true;
    /**
     * @Describe 是否可以上啦加载更多
     */

    private boolean isLoadMore = true;
    /**
     * @Describe 是否需要使用loading
     */
    private boolean isNotShowPageManager;
    /**
     * @Describe loading页面的text
     */
    String loadingMsg = "加载中,请稍后";
    /**
     * @Describe 错误的页面的text
     */
    String retryMsg = "出错了,请点击重试";
    /**
     * @Describe 空的页面的text
     */
    String emptyMsg = "搜寻不到您的内容哦";


    private boolean isRefreshDataNotShowLoading;
    private boolean isLoaderMoreSecondPageError;
    private boolean isLoaderMoreSecondPageSuccess;

    public boolean isLoaderMoreSecondPageEmpty() {
        return isLoaderMoreSecondPageEmpty;
    }

    private boolean isLoaderMoreSecondPageEmpty;


    public PageManageBuilder(Context context) {
        this.mContext = context;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public PageManageBuilder setRecyclerView(RecyclerView pageManagerShowView) {
        this.recyclerView = pageManagerShowView;
        return this;
    }

    private RecyclerView recyclerView;

    public PageManageBuilder setLoadingMsg(String loadingMsg) {
        this.loadingMsg = loadingMsg;
        return this;
    }

    public PageManageBuilder setRetryMsg(String retryMsg) {
        this.retryMsg = retryMsg;
        return this;
    }

    public PageManageBuilder setRefreshDataNotShowLoading(boolean refreshDataNotShowLoading) {
        isRefreshDataNotShowLoading = refreshDataNotShowLoading;
        return this;
    }


    public PageManageBuilder setEmptyMsg(String emptyMsg) {
        this.emptyMsg = emptyMsg;
        return this;
    }

    public PageManageBuilder setAdapter(BaseDataBindingAdapter adapter) {
        this.adapter = adapter;
        return this;
    }


    public PageManageBuilder setShowEmptyStatus(boolean showEmptyStatus) {
        isShowEmptyStatus = showEmptyStatus;
        return this;
    }

    public PageManageBuilder setShowErrorStatus(boolean showErrorStatus) {
        isShowErrorStatus = showErrorStatus;
        return this;
    }

    public PageManageBuilder setShowPageManager(boolean choosePageManager) {
        choosePageManager = choosePageManager;
        return this;
    }


    public PageManageBuilder setRefresh(boolean refresh) {
        isRefresh = refresh;
        return this;
    }

    public PageManageBuilder setLoadMore(boolean loadMore) {
        isLoadMore = loadMore;
        return this;
    }

    public PageManageBuilder setNotShowPageManager(boolean notShowPageManager) {
        isNotShowPageManager = notShowPageManager;
        return this;
    }

    public BaseDataBindingAdapter getAdapter() {
        return adapter;
    }

    public boolean isShowEmptyStatus() {
        return isShowEmptyStatus;
    }

    public boolean isShowErrorStatus() {
        return isShowErrorStatus;
    }

    public boolean choosePageManager() {
        return choosePageManager;
    }

    public boolean isRefresh() {
        return isRefresh;
    }

    public boolean isLoadMore() {
        return isLoadMore;
    }

    public boolean isNotShowPageManager() {
        return isNotShowPageManager;
    }

    public String getLoadingMsg() {
        return loadingMsg;
    }

    public String getRetryMsg() {
        return retryMsg;
    }

    public String getEmptyMsg() {
        return emptyMsg;
    }

    public boolean isRefreshDataNotShowLoading() {
        return isRefreshDataNotShowLoading;
    }

    public Context getContext() {
        return mContext;
    }

    public PageManageBuilder setLoadMoreSecondPage(boolean loadMoreSecondPage) {
        this.isLoadMoreSecondPage = loadMoreSecondPage;
        return this;
    }

    public PageManageBuilder setIsLoaderMoreSecondPageIng(boolean isLoaderMoreSecondPageIng) {
        this.isLoaderMoreSecondPageIng = isLoaderMoreSecondPageIng;
        return this;
    }

    public boolean isLoaderMoreSecondPageIng() {
        return isLoaderMoreSecondPageIng;
    }

    private boolean isLoaderMoreSecondPageIng;

    public boolean isLoadMoreSecondPage() {
        return isLoadMoreSecondPage;
    }

    private boolean isLoadMoreSecondPage;

    public boolean isLoadMoreDataNotShowLoading() {
        return isLoadMoreDataNotShowLoading;
    }

    private boolean isLoadMoreDataNotShowLoading;

    public PageManageBuilder setLoadMoreDataNotShowLoading(boolean loadMoreDataNotShowLoading) {
        isLoadMoreDataNotShowLoading = loadMoreDataNotShowLoading;
        return this;
    }

    public void setIsLoaderMoreSecondPageError(boolean isLoaderMoreSecondPageError) {
        this.isLoaderMoreSecondPageError = isLoaderMoreSecondPageError;
    }

    public boolean isLoaderMoreSecondPageError() {
        return isLoaderMoreSecondPageError;
    }

    public PageManageBuilder setLoaderMoreSecondPageError(boolean isLoaderMoreSecondPageError) {
        this.isLoaderMoreSecondPageError = isLoaderMoreSecondPageError;
        return this;
    }

    public void setIsLoaderMoreSecondPageSuccess(boolean isLoaderMoreSecondPageSuccess) {
        this.isLoaderMoreSecondPageSuccess = isLoaderMoreSecondPageSuccess;
    }

    public boolean isLoaderMoreSecondPageSuccess() {
        return isLoaderMoreSecondPageSuccess;
    }

    public PageManageBuilder setLoaderMoreSecondPageSuccess(boolean isLoaderMoreSecondPageSuccess) {
        this.isLoaderMoreSecondPageSuccess = isLoaderMoreSecondPageSuccess;
        return this;
    }

    public PageManageBuilder setIsLoaderMoreSecondPageEmpty(boolean isLoaderMoreSecondPageEmpty) {
        this.isLoaderMoreSecondPageEmpty = isLoaderMoreSecondPageEmpty;
        return this;
    }
}
