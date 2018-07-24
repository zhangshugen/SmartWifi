package com.smartwifi.widget.databindingadapter;

import java.util.List;

/**
 * Created by Administrator on 2018/7/17
 */
public interface RefreshableAdapter<T> {
    void refresh(List<T> newData);

    void refresh();

    void addAll(List<T> newData);

    void addAllTop(List<T> newData);

    void clear();

    void delete(int position);

    void add(T object);


    void add(int position, T data);

    void addLast(T data);

    void set(int position, T data);
}
