package com.smartwifi.widget.databindingadapter;


import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Administrator on 2018/7/10 0010.
 */

public class BindingViewHolder<T extends ViewDataBinding> extends RecyclerView.ViewHolder {
    private   T mBinding;

    public BindingViewHolder(T binding) {
        super(binding.getRoot());
        mBinding=binding;
    }
    public T getBinding(){
        return mBinding;
    }
}
