package com.smartwifi.interfaces;


import com.smartwifi.widget.mvvm.view.BaseMVVMView;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/16
 * @Describe
 */

public interface BasePageManageView<E> extends BaseMVVMView {

    void showError(String message, int code);

    void showLoading(String message);

    void showContent(E data);

    void showEmpty(String empty);

}
