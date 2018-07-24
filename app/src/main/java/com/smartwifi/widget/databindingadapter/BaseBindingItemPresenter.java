package com.smartwifi.widget.databindingadapter;

/**
 * Created by Administrator on 2018/7/10 0010.
 */

public  interface BaseBindingItemPresenter<T> {
     void onItemClick(int position, T itemData);
}
