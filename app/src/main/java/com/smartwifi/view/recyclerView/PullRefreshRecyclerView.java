package com.smartwifi.view.recyclerView;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.smartwifi.R;
import com.smartwifi.bean.FootLoadMoreBean;
import com.smartwifi.constant.Constant;
import com.smartwifi.databinding.FootRecyclerLoadMoreBinding;
import com.smartwifi.utils.CommonUtils;
import com.smartwifi.widget.databindingadapter.BaseDataBindingAdapter;
import com.smartwifi.widget.databindingadapter.BindingViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/19
 * @Describe
 */

public class PullRefreshRecyclerView extends FrameLayout {
    private MySwipeToRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mSwipeRecycler;
    private View mRootView;
    private boolean isAddFootLoadMore;
    private boolean isNoMoreData;
    public boolean isLoadMoreing;
    private boolean loadMoreEnable;
    private RecyclerView.Adapter mAdapter;
    private boolean refreshState;
    private BindingViewHolder footHolder;
    private FootRecyclerLoadMoreBinding footView;
    private FootLoadMoreBean footLoadMoreBean;

    public PullRefreshRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public PullRefreshRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullRefreshRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mRootView = LayoutInflater.from(context).inflate(R.layout.view_recycle_root, this, true);
        mSwipeRefreshLayout = (MySwipeToRefreshLayout) mRootView.findViewById(R.id.mySwipe);
        mSwipeRecycler = (RecyclerView) mRootView.findViewById(R.id.swipe_recycler);
        initEvent();
    }

    private void initEvent() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (loadMoreListener != null) {
                    loadMoreListener.onRefresh();
                }

            }
        });
        mSwipeRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {

            int lastVisibleItem;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);


                if (onScrollListener != null) {
                    onScrollListener.onScrolled(recyclerView, dx, dy);
                }
                if (!isAddFootLoadMore) {
                    return;
                }
                if (isSlideToBottom(recyclerView)) {
                    if (isNoMoreData) {
                        setLoadMoreStatus(2, "没有更多数据了");
                        return;
                    }
                    if (isLoadMoreing) {
                        setLoadMoreStatus(1, "正在加载当中...");
                        return;
                    }
                    if (!loadMoreEnable) {
                        setLoadMoreStatus(2, "");
                        return;
                    } else {
                        setLoadMoreStatus(3, "上拉加载更多数据");
                    }
                    refreshEnd();
                    if (loadMoreListener != null) {
                        isLoadMoreing = true;
                        setLoadMoreStatus(1, "正在加载当中...");
                        loadMoreListener.onLoadMore();
                    }

                }
            }
        });
    }

    private void setLoadMoreStatus(int status, String text) {
        if (mAdapter == null) {
            return;
        }
        boolean in = mAdapter instanceof BaseDataBindingAdapter;
        if (in) {
            BaseDataBindingAdapter adapter = (BaseDataBindingAdapter) mAdapter;
            HashMap<Integer, BindingViewHolder> bindingViewHolderMap = adapter.getBindingViewHolderMap();
            if (!bindingViewHolderMap.containsKey(Constant.ADAPTER_FOOT_KEY.LOAD_MORE_FOOT_KEY)) {
                return;
            }
            footHolder = bindingViewHolderMap.get(Constant.ADAPTER_FOOT_KEY.LOAD_MORE_FOOT_KEY);
            if (footHolder == null) {
                return;
            }
            ViewDataBinding binding = footHolder.getBinding();
            boolean in2 = binding instanceof FootRecyclerLoadMoreBinding;

            if (!in2) {
                return;
            }
            if (footView == null) {
                footView = (FootRecyclerLoadMoreBinding) binding;
            }
            if (footView == null) {
                return;
            }
            if (status == 1) {

                footView.llContent.setVisibility(VISIBLE);
                footView.progress.setVisibility(VISIBLE);
            } else if (status == 2) {
                footView.llContent.setVisibility(GONE);
                footView.progress.setVisibility(GONE);
            } else if (status == 3) {
                footView.llContent.setVisibility(VISIBLE);
                footView.progress.setVisibility(GONE);
            }


            if (!TextUtils.isEmpty(text)) {
                footView.tvFootStateDec.setText(text);
            }
        }
    }

    public void refreshEnd() {
        if (mSwipeRefreshLayout.isRefreshing())
            mSwipeRefreshLayout.setRefreshing(false);
    }

    public static boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) return false;
        return recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView.computeVerticalScrollRange();
    }


    private OnLoadMoreListener loadMoreListener;

    public void setOnLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }

    public void setItemAnimator(RecyclerView.ItemAnimator animator) {
        mSwipeRecycler.setItemAnimator(animator);
    }

    public RecyclerView getRecyclerView() {
        return mSwipeRecycler;
    }

    public void loadMoreError() {
        isLoadMoreing = false;
        setLoadMoreStatus(3, "加载出错...");
    }

    public void noTopMoreData(List listInfoPojos, int count) {

    }

    public interface OnLoadMoreListener {
        /**
         * 下拉
         */
        void onRefresh();

        /**
         * 上拉
         */
        void onLoadMore();
    }

    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }

    private OnScrollListener onScrollListener;

    public static class OnScrollListener {
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

        }
    }

    public void setRecyclerViewBackgroundRes(int res) {
        mSwipeRefreshLayout.setBackgroundResource(res);
        mSwipeRecycler.setBackgroundResource(res);
    }

    public void setLoadMoreAdapter(RecyclerView.Adapter adapter) {
        setLoadMoreAdapter(adapter, new LinearLayoutManager(getContext()));
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        setAdapter(adapter, new LinearLayoutManager(getContext()));
    }

    public void setManagerAdapter(RecyclerView.Adapter adapter) {
        setManagerAdapter(adapter, new LinearLayoutManager(getContext()));

    }

    public void setLoadManagerAdapter(RecyclerView.Adapter adapter) {
        setLoadManagerAdapter(adapter, new LinearLayoutManager(getContext()));
    }

    public void setLoadMoreAdapter(RecyclerView.Adapter adapter, RecyclerView.LayoutManager manager) {
        setAdapter(adapter, manager, true, false);
    }

    public void setAdapter(RecyclerView.Adapter adapter, RecyclerView.LayoutManager manager) {
        setAdapter(adapter, manager, false, false);
    }

    public void setManagerAdapter(RecyclerView.Adapter adapter, RecyclerView.LayoutManager manager) {
        setAdapter(adapter, manager, false, true);

    }

    public void setLoadManagerAdapter(RecyclerView.Adapter adapter, RecyclerView.LayoutManager manager) {
        setAdapter(adapter, new LinearLayoutManager(getContext()), true, true);
    }

    private void setAdapter(RecyclerView.Adapter adapter, RecyclerView.LayoutManager layoutManager, boolean isAddLoadMore, boolean isManager) {
        if (adapter == null) return;
        this.mAdapter = adapter;
        if (layoutManager == null) {
            layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        }
        if (isAddLoadMore)
            setLoadMoreView();
        if (isManager)
            CommonUtils.setManagerConfig(layoutManager, mSwipeRecycler);

        isAddFootLoadMore = isAddLoadMore;
        mSwipeRecycler.setLayoutManager(layoutManager);
        mSwipeRecycler.setAdapter(mAdapter);
    }


    private void setLoadMoreView() {
        if (mAdapter == null) {
            return;
        }
        addFootPojo(1, "上拉加载更多数据", true);
        if (mAdapter instanceof BaseDataBindingAdapter) {
            BaseDataBindingAdapter adapter = (BaseDataBindingAdapter) mAdapter;
            adapter.addSingleFootConfig(Constant.ADAPTER_FOOT_KEY.LOAD_MORE_FOOT_KEY, R.layout.foot_recycler_load_more, footLoadMoreBean);
        }

    }

    private void addFootPojo(int status, String text, boolean isShowLine) {
        if (footLoadMoreBean == null) {
            footLoadMoreBean = new FootLoadMoreBean();
        }
        footLoadMoreBean.setStatus(status);
        footLoadMoreBean.setText(text);
        footLoadMoreBean.setShowLine(isShowLine);
    }


    /**
     * 设置是否可以下拉和上拉
     *
     * @param refreshEnable
     * @param loadMoreEnable
     */
    public void setRefreshLoaderMore(boolean refreshEnable, boolean loadMoreEnable) {
        mSwipeRefreshLayout.setEnabled(refreshEnable);

        this.loadMoreEnable = loadMoreEnable;
    }

    public void smoothScrollToPosition(int _position) {
        mSwipeRecycler.smoothScrollToPosition(_position);

    }

    public void scrollToPosition(int _position) {
        mSwipeRecycler.scrollToPosition(_position);

    }

    /**
     * @param b true  显示  false 隐藏
     * @doc 设置refresh的下拉刷新头的状态
     */
    public void setRefreshState(boolean b) {
        refreshState = b;
        mSwipeRefreshLayout.setRefreshing(b);
    }

    public void LoadMoreStart() {
        setLoadMoreStatus(1, "正在加载中...");
    }

    public void loadTopMoreStart() {
        //  setTopMoreStatus(1, "正在加载中...");
    }

    /**
     * 是否能够上啦加载
     */
    public void setLoadMoreState(boolean b) {
        isNoMoreData = b;
    }

    public void noMoreData(List data, int count) {
        if (data == null) {
            data = new ArrayList();
        }

        if (data.size() >= count) {
            isNoMoreData = false;
            setLoadMoreStatus(3, "上拉加载更多数据");
        } else {

            isNoMoreData = true;
            setLoadMoreStatus(2, "没有更多数据了");
        }
        isLoadMoreing = false;
    }

}
