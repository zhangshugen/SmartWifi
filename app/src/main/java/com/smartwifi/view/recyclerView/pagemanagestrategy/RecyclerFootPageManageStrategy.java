package com.smartwifi.view.recyclerView.pagemanagestrategy;

import android.view.View;

import com.smartwifi.R;
import com.smartwifi.bean.FootRefreshBean;
import com.smartwifi.constant.Constant;
import com.smartwifi.databinding.FootRefreshRecyclerviewBinding;
import com.smartwifi.widget.databindingadapter.BaseDataBindingDecorator;
import com.smartwifi.widget.databindingadapter.BindingViewHolder;
import com.smartwifi.widget.pagemanage.PageManager;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/19
 * @Describe
 */

public class RecyclerFootPageManageStrategy extends LoadingManageStrategy {

    private FootRefreshBean footLoadMoreBean;

    public RecyclerFootPageManageStrategy(PageManageBuilder builder) {
        super(builder);
    }


    @Override
    public void showPageContent() {
        footLoadMoreBean.setStatus(4);
    }

    @Override
    public void showPageLoading(String loadingMsg) {
        addFootRefreshView();
    }

    @Override
    public void showPageEmpty(String emptyMsg) {
        footLoadMoreBean.setStatus(1);
    }

    @Override
    public void showPageError(String errorMsg) {
        footLoadMoreBean.setStatus(2);
        // 添加重试操作
    }

    private void addFootRefreshView() {
        footLoadMoreBean = new FootRefreshBean();
        footLoadMoreBean.setStatus(0);

        mBuilder.getAdapter().addSingleFootConfig(Constant.ADAPTER_FOOT_KEY.REFRESH_RECYCLER_FOOT_KEY, R.layout.foot_refresh_recyclerview, footLoadMoreBean);

        mBuilder.getAdapter().setFootDecorator(new BaseDataBindingDecorator() {
            @Override
            public void decorator(BindingViewHolder holder, int position, int viewType, Object mData) {
                if (viewType == Constant.ADAPTER_FOOT_KEY.REFRESH_RECYCLER_FOOT_KEY) {
                    FootRefreshRecyclerviewBinding binding = (FootRefreshRecyclerviewBinding) holder.getBinding();
                    if (!PageManager.isNetWorkAvailable(mBuilder.getContext())) {
                        binding.pageError.ivErrorImage.setImageResource(R.drawable.img_error);
                        binding.pageError.tvErrorMsg.setText("没有网络,请检查网络情况");
                    }
                    binding.pageError.pageLoading.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mPageErrorRetryListener != null)
                                mPageErrorRetryListener.errorRetry();
                        }
                    });
                }

            }
        });

    }

}
