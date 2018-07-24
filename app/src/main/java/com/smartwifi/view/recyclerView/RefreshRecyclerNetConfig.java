package com.smartwifi.view.recyclerView;

import java.util.Map;

import io.reactivex.Observable;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/20
 * @Describe
 */

public abstract class RefreshRecyclerNetConfig {
    public abstract Observable getNetObservable(Map<String, Object> map);
}
