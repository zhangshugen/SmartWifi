package com.smartwifi.widget.databindingadapter;

/**
 * Created by Administrator on 2018/7/15 0015.
 */

public interface BaseDataBindingDecorator<T> {
    void decorator(BindingViewHolder holder, int position, int viewType, T data);
}
