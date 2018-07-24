package com.smartwifi.view.recyclerView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.orhanobut.logger.Logger;
import com.smartwifi.R;
import com.smartwifi.base.BaseCustomView;
import com.smartwifi.bean.BaseListData;
import com.smartwifi.databinding.ViewRefreshRecyclerviewBinding;
import com.smartwifi.interfaces.PageErrorRetryListener;
import com.smartwifi.interfaces.RefreshRecyclerLoadListener;
import com.smartwifi.rxjava.RxJavaHttpHelper;
import com.smartwifi.view.recyclerView.pagemanagestrategy.LoadingManageStrategy;
import com.smartwifi.view.recyclerView.pagemanagestrategy.PageManageBuilder;
import com.smartwifi.view.recyclerView.pagemanagestrategy.PageManagerStrategy;
import com.smartwifi.view.recyclerView.pagemanagestrategy.RecyclerFootPageManageStrategy;
import com.smartwifi.view.recyclerView.pagemanagestrategy.RecyclerPageManger;
import com.smartwifi.widget.databindingadapter.BaseBindingItemPresenter;
import com.smartwifi.widget.databindingadapter.BaseDataBindingAdapter;
import com.smartwifi.widget.databindingadapter.BaseDataBindingDecorator;
import com.smartwifi.widget.retrofithelper.rxschedulers.RxSchedulersHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * Created by Administrator on 2018/7/15 0015.
 */

public class RefreshRecyclerView extends BaseCustomView<ViewRefreshRecyclerviewBinding> implements PullRefreshRecyclerView.OnLoadMoreListener, PageErrorRetryListener {

    public int action;
    private boolean isTopRefresh;
    private BaseDataBindingAdapter adapter;
    public int COUNT = 20;
    int currentPage = 1;
    public static final int ACTION_FIRST_LOAD = 1;
    public static final int ACTION_REFRESH = 2;
    public static final int ACTION_LOAD_MORE = 3;
    private static final int ACTION_TOP_LOAD_MORE = 4;
    public static final int ACTION_SECOND_LOAD_MORE = 5;
    public PullRefreshRecyclerView mPullRecyclerView;
    public RefreshRecyclerLoadListener mRefreshRecyclerLoadListener;
    /**
     * @Describe 判断使用pageManager 或者使用 recyclerView的foot进行loading处理
     */
    private boolean choosePageManager = true;
    private boolean isRefresh;
    private boolean isLoadMore;
    private PageManageBuilder mBuilder;
    private RecyclerPageManger mRecyclerPageManger;
    private LoadingManageStrategy mLoadingStrategy;
    private boolean isFirstIn;
    private RefreshRecyclerNetConfig netConfig;
    private List secondPageListData;
    private boolean isAutoLoadTriggerOnLoadMore;
    private boolean isAuthLoadMoreSecondPage;
    private Disposable netDisposable;

    public RefreshRecyclerView(@NonNull Context context) {
        super(context);

    }

    public RefreshRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.view_refresh_recyclerview;
    }

    @Override
    public void initView(Context context, AttributeSet attrs) {
        mPullRecyclerView = mBinding.reclerview;
        choosePageManager = true;
        isRefresh = true;
        isLoadMore = true;
        isFirstIn = true;
        isAuthLoadMoreSecondPage = true;
    }

    public int getCurrentPage() {
        return currentPage;
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


    private void requestNet(int page) {

/*
        switch (action) {
            case ACTION_FIRST_LOAD:

                break;
            case ACTION_REFRESH:
                page = 1;
                break;
            case ACTION_LOAD_MORE:
                page = currentPage + 1;
                break;
            case ACTION_TOP_LOAD_MORE:
                page = currentPage + 1;
                break;
            case ACTION_SECOND_LOAD_MORE:
                page = currentPage + 1;
                break;
        }
*/
        Map<String, Object> mapParams = new HashMap<String, Object>();
        mapParams.put("PageSize", COUNT + "");
        mapParams.put("PageNum", page + "");
        // config.addNetParams(mapParams);
        if (netConfig == null) {
            throw new NullPointerException("net config is null");
        }
        observable = netConfig.getNetObservable(mapParams);
        mPullRecyclerView.setOnLoadMoreListener(this);
        Logger.v("---------------requestNet123456789");
        observable.compose(RxJavaHttpHelper.<BaseListData>handleResult()).compose(RxSchedulersHelper.applyIoTransformer()).subscribe(new Observer<BaseListData>() {

            @Override
            public void onSubscribe(Disposable d) {
                setUpStart(d);
            }

            @Override
            public void onNext(BaseListData o) {
                Logger.v(Thread.currentThread().getName() + "1");
                if (o == null || o.ListData == null || o.ListData.size() == 0) {
                    setUpEmpty();
                } else {
                    setUpListData(o.ListData);
                }

            }

            @Override
            public void onError(Throwable e) {
                setUpError("", 1);
            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void setRefreshRecyclerNetConfig(RefreshRecyclerNetConfig netConfig) {
        this.netConfig = netConfig;
    }

    public void setRefreshRecyclerLoadListener(RefreshRecyclerLoadListener loadDataStatusListener) {
        this.mRefreshRecyclerLoadListener = loadDataStatusListener;
    }

    private io.reactivex.Observable observable;

    public void create(PageManageBuilder builder) {
        this.isLoadMore = builder.isLoadMore();
        this.isRefresh = builder.isRefresh();
        this.adapter = builder.getAdapter();
        this.choosePageManager = builder.choosePageManager();
        this.mBuilder = builder;
    }

    private void setUpEmpty() {
        switch (action) {
            case ACTION_FIRST_LOAD:
                showPageEmpty();
                break;
            case ACTION_REFRESH:
                showPageEmpty();
                mPullRecyclerView.setRefreshState(false);
                break;
            case ACTION_LOAD_MORE:
                mPullRecyclerView.setLoadMoreState(false);
                mPullRecyclerView.noMoreData(new ArrayList(), COUNT);
                break;
            case ACTION_SECOND_LOAD_MORE:
                setIsLoaderMoreSecondPageEmpty(true);
                mPullRecyclerView.setLoadMoreState(false);
                mPullRecyclerView.noMoreData(new ArrayList(), COUNT);
                break;
        }
        if (mRefreshRecyclerLoadListener != null) {
            mRefreshRecyclerLoadListener.onEmpty(action);
        }


    }

    private void showPageEmpty() {
        mRecyclerPageManger.showPageEmpty();
    }

    private void setUpStart(Disposable d) {
        if (netDisposable != null)
            netDisposable.dispose();
        this.netDisposable = d;
        switch (action) {
            case ACTION_FIRST_LOAD:
                showLoading();
                break;
            case ACTION_REFRESH:

                mPullRecyclerView.setRefreshState(true);
                break;
            case ACTION_LOAD_MORE:
                mPullRecyclerView.LoadMoreStart();
                break;
            case ACTION_TOP_LOAD_MORE:
                mPullRecyclerView.loadTopMoreStart();
                break;
            case ACTION_SECOND_LOAD_MORE:
                setIsLoaderMoreSecondPageIng(true);
                break;
        }
        if (mRefreshRecyclerLoadListener != null) {
            mRefreshRecyclerLoadListener.onStart(action);
        }
    }


    private void setUpError(String errorInfo, int errorCode) {
        if (TextUtils.isEmpty(errorInfo)) {
            errorInfo = "页面加载失败,请稍后重试";
        }
        switch (action) {
            case ACTION_FIRST_LOAD:
                currentPage = 1;
                showPageError();
                break;
            case ACTION_REFRESH:
                currentPage = 1;
                mPullRecyclerView.setRefreshState(false);
                break;
            case ACTION_LOAD_MORE:
                mPullRecyclerView.loadMoreError();
                break;
            case ACTION_SECOND_LOAD_MORE:
                setIsLoaderMoreSecondPageError(true);
                break;

        }
        if (mRefreshRecyclerLoadListener != null) {
            mRefreshRecyclerLoadListener.onError(errorInfo, errorCode, action);
        }
    }

    private void showPageError() {
        mRecyclerPageManger.showPageError();
    }


    public void setUpListData(List listInfoPojos) {

        switch (action) {
            case ACTION_FIRST_LOAD:
                isFirstIn = false;
                currentPage = 1;
                mPullRecyclerView.noMoreData(listInfoPojos, COUNT);
                adapter.refresh(listInfoPojos);
                if (listInfoPojos.size() < COUNT) {
                    mPullRecyclerView.setRefreshLoaderMore(isRefresh, false);
                } else if (listInfoPojos.size() >= COUNT) {
                    onLoadMoreSecondPage();
                    mPullRecyclerView.setRefreshLoaderMore(isRefresh, isLoadMore);
                }

                showPageContent();
                break;
            case ACTION_REFRESH:
                showPageContent();
                currentPage = 1;
                if (listInfoPojos != null && listInfoPojos.size() != 0) {
                    adapter.refresh(listInfoPojos);
                }
                mPullRecyclerView.setRefreshState(false);
                mPullRecyclerView.noMoreData(listInfoPojos, COUNT);
                if (listInfoPojos.size() < COUNT) {
                    mPullRecyclerView.setRefreshLoaderMore(isRefresh, false);
                } else if (listInfoPojos.size() >= COUNT) {
                    onLoadMoreSecondPage();
                    mPullRecyclerView.setRefreshLoaderMore(isRefresh, isLoadMore);
                }

                break;
            case ACTION_LOAD_MORE:
                currentPage++;
                if (listInfoPojos != null && listInfoPojos.size() != 0) {
                    adapter.addAll(listInfoPojos);
                }
                mPullRecyclerView.noMoreData(listInfoPojos, COUNT);
                onLoadMoreSecondPage();
                break;
            case ACTION_TOP_LOAD_MORE:
                currentPage++;
                if (listInfoPojos != null && listInfoPojos.size() != 0) {
                    adapter.addAllTop(listInfoPojos);
                }
                mPullRecyclerView.noTopMoreData(listInfoPojos, COUNT);
                break;
            case ACTION_SECOND_LOAD_MORE:
                currentPage++;
                if (secondPageListData != null)
                    secondPageListData.clear();
                setIsLoaderMoreSecondPageIng(false);
                if (!isAutoLoadTriggerOnLoadMore) {
                    secondPageListData = listInfoPojos;
                    setIsLoaderMoreSecondPageSuccess(true);
                } else {
                    isAutoLoadTriggerOnLoadMore = false;
                    adapter.addAll(listInfoPojos);
                    setIsLoaderMoreSecondPageSuccess(false);
                    mPullRecyclerView.noMoreData(listInfoPojos, COUNT);
                    if (listInfoPojos.size() >= COUNT)
                        onLoadMoreSecondPage();
                }
                break;
        }

    }

    private void showLoading() {

        if (mBuilder == null) {
            mBuilder = new PageManageBuilder(getContext());
        }
        mBuilder.setRecyclerView(mPullRecyclerView.getRecyclerView());
        mBuilder.setAdapter(adapter);
        if (mRecyclerPageManger == null)
            mRecyclerPageManger = new RecyclerPageManger();
        // 策略模式, 使用不同的对象
        if (mLoadingStrategy == null) {
            if (choosePageManager) {
                mLoadingStrategy = new PageManagerStrategy(mBuilder);
            } else {
                mLoadingStrategy = new RecyclerFootPageManageStrategy(mBuilder);
            }
        } else {
            mLoadingStrategy.setBuilder(mBuilder);
        }
        mRecyclerPageManger.setBuilder(mBuilder);
        mRecyclerPageManger.setPageManagerStrategy(mLoadingStrategy);
        mRecyclerPageManger.showPageLoading();
        mLoadingStrategy.setPageErrorRetryListener(this);
    }


    @Override
    public void onRefresh() {
        action = ACTION_REFRESH;
        setRefreshDataNotShowLoading(false);
        requestNet(1);
    }

    @Override
    public void onLoadMore() {
        if (isAuthLoadMoreSecondPage) {
            if (mBuilder.isLoaderMoreSecondPageSuccess()) {
                mPullRecyclerView.noMoreData(secondPageListData, COUNT);
                adapter.addAll(secondPageListData);
                mPullRecyclerView.noMoreData(secondPageListData, COUNT);
                setIsLoaderMoreSecondPageIng(false);
                setIsLoaderMoreSecondPageSuccess(false);
                if (secondPageListData.size() >= COUNT)
                    onLoadMoreSecondPage();
            } else if (mBuilder.isLoaderMoreSecondPageIng()) {
                isAutoLoadTriggerOnLoadMore = true;
                return;
            }
        }
        Logger.d("isLoaderMoreSecondPageSuccess  onLoadMore");
        setRefreshDataNotShowLoading(false);
        action = ACTION_LOAD_MORE;
        requestNet(currentPage + 1);
    }

    public void firstLoad() {
        action = ACTION_FIRST_LOAD;
        isTopRefresh = true;
        setRefreshDataNotShowLoading(false);
        requestNet(1);
    }

    public void refresh() {
        action = ACTION_FIRST_LOAD;
        setRefreshDataNotShowLoading(true);
        requestNet(1);
    }

    public void onLoadMoreSecondPage() {
        if (!isAuthLoadMoreSecondPage)
            return;
        action = ACTION_SECOND_LOAD_MORE;
        setRefreshDataNotShowLoading(false);
        setLoadMoreDataNotShowLoading(false);
        setIsLoaderMoreSecondPageIng(true);
        loadMoreSecondPage(true);
        requestNet(currentPage + 1);
        Logger.v("---------------onLoadMoreSecondPage123456789");
    }

    protected void loadMoreSecondPage(boolean b) {
        createBuilder();
        mBuilder.setLoadMoreSecondPage(b);
    }


    public void showPageContent() {
        mRecyclerPageManger.showContent();
    }

    public void setIsShowPageManager(boolean isShowPageManager) {
        this.choosePageManager = isShowPageManager;
    }

    public void setIsAuthLoadMoreSecondPage(boolean isAuthLoadMoreSecondPage) {
        this.isAuthLoadMoreSecondPage = isAuthLoadMoreSecondPage;
    }

    public void setIsRefresh(boolean isRefresh) {
        this.isRefresh = isRefresh;
    }

    public int getAction() {
        return action;
    }

    public void setIsLoaderMore(boolean isLoadMore) {
        this.isLoadMore = isLoadMore;
    }

    public void smoothScrollToPosition(int position) {
        mPullRecyclerView.smoothScrollToPosition(position);
    }

    public void scrollToPosition(int position) {
        mPullRecyclerView.scrollToPosition(position);
    }


    public void setOnScrollListener(PullRefreshRecyclerView.OnScrollListener onScrollListener) {
        mPullRecyclerView.setOnScrollListener(onScrollListener);
    }

    public void setRecyclerViewBackgroundRes(int res) {
        mPullRecyclerView.setRecyclerViewBackgroundRes(res);
    }

    public void setChangeDuration(int i) {
        mPullRecyclerView.getRecyclerView().getItemAnimator().setChangeDuration(i);
    }

    public void setRefreshLoaderMore(boolean refreshEnable, boolean loadMoreEnable) {
        mPullRecyclerView.setRefreshLoaderMore(refreshEnable, loadMoreEnable);
    }

    public void setIsShowEmptyStatus(boolean f) {
        createBuilder();
        mBuilder.setShowEmptyStatus(f);
    }

    public void setIsShowErrorStatus(boolean f) {
        createBuilder();
        mBuilder.setShowEmptyStatus(f);
    }

    private void setRefreshDataNotShowLoading(boolean b) {
        createBuilder();
        mBuilder.setRefreshDataNotShowLoading(b);
    }

    private void setLoadMoreDataNotShowLoading(boolean b) {
        createBuilder();
        mBuilder.setRefreshDataNotShowLoading(b);
    }

    private void createBuilder() {
        if (mBuilder == null) mBuilder = new PageManageBuilder(getContext());
    }

    public void setLoadingMsg(String loadingMsg) {
        createBuilder();
        mBuilder.setLoadingMsg(loadingMsg);
    }

    public void setErrorMsg(String retryMsg) {
        createBuilder();
        mBuilder.setLoadingMsg(retryMsg);
    }


    public void setEmptyMsg(String emptyMsg) {
        createBuilder();
        mBuilder.setLoadingMsg(emptyMsg);
    }

    public boolean isFirstIn() {
        return isFirstIn;
    }

    public void setIsFristIn(boolean isFristIn) {
        this.isFirstIn = isFristIn;
    }

    public void setItemAnimator(RecyclerView.ItemAnimator animator) {
        mPullRecyclerView.setItemAnimator(animator);
    }


    public void setLoadMoreAdapter(RecyclerView.Adapter adapter) {
        mPullRecyclerView.setLoadMoreAdapter(adapter);
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        mPullRecyclerView.setAdapter(adapter);
    }

    public void setManagerAdapter(RecyclerView.Adapter adapter) {
        mPullRecyclerView.setManagerAdapter(adapter);
    }

    public void setLoadManagerAdapter(RecyclerView.Adapter adapter) {
        mPullRecyclerView.setLoadManagerAdapter(adapter);
    }

    public void setLoadMoreAdapter(RecyclerView.Adapter adapter, RecyclerView.LayoutManager manager) {
        mPullRecyclerView.setLoadMoreAdapter(adapter, manager);
    }

    public void setAdapter(RecyclerView.Adapter adapter, RecyclerView.LayoutManager manager) {
        mPullRecyclerView.setAdapter(adapter, manager);
    }

    public void setManagerAdapter(RecyclerView.Adapter adapter, RecyclerView.LayoutManager manager) {
        mPullRecyclerView.setManagerAdapter(adapter, manager);

    }

    public void setLoadManagerAdapter(RecyclerView.Adapter adapter, RecyclerView.LayoutManager manager) {
        mPullRecyclerView.setLoadManagerAdapter(adapter, manager);
    }

    public void setAdapterItemPresenter(BaseBindingItemPresenter presenter) {
        if (adapter == null) {
            return;
        }
        if (presenter == null) {
            return;
        }
        adapter.setItemPresenter(presenter);
    }


    public void setAdapterItemDecorator(BaseDataBindingDecorator d) {
        if (adapter == null) {
            return;
        }
        if (d == null) {
            return;
        }
        adapter.setItemDecorator(d);
    }

    //重试
    @Override
    public void errorRetry() {
        firstLoad();
    }


    private void setIsLoaderMoreSecondPageIng(boolean isLoaderMoreSecondPageIng) {
        mBuilder.setIsLoaderMoreSecondPageIng(isLoaderMoreSecondPageIng);
    }

    private void setIsLoaderMoreSecondPageError(boolean isLoaderMoreSecondPageError) {
        mBuilder.setLoaderMoreSecondPageError(isLoaderMoreSecondPageError);
    }

    private void setIsLoaderMoreSecondPageSuccess(boolean isLoaderMoreSecondPageSuccess) {
        mBuilder.setIsLoaderMoreSecondPageSuccess(isLoaderMoreSecondPageSuccess);
    }

    private void setIsLoaderMoreSecondPageEmpty(boolean isLoaderMoreSecondPageSuccess) {
        mBuilder.setIsLoaderMoreSecondPageEmpty(isLoaderMoreSecondPageSuccess);
    }
}
