package com.smartwifi.widget.mvvm.factory;

import com.smartwifi.widget.mvvm.viewmodel.BaseViewModel;

/**
 * 适用注解模式创建ViewModel
 */

public class ViewModelFactoryImpl< VM extends BaseViewModel > implements ViewModelFactory<  VM> {
    private Class<VM> mViewModel;

    public static <  VM extends BaseViewModel  > ViewModelFactoryImpl createViewModelFactory(Class<?> viewClazz) {
        CreateViewModel annotation = viewClazz.getAnnotation(CreateViewModel.class);
        if (annotation != null) {
            Class<VM> viewModelClazz = (Class<VM>) annotation.value();
            try {
                return new ViewModelFactoryImpl(viewModelClazz);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private ViewModelFactoryImpl(Class<VM> viewModel) {
        this.mViewModel = viewModel;
    }

    @Override
    public VM createViewModel() {
        try {
            return mViewModel.newInstance();
        } catch (Exception e) {
            throw new NullPointerException("viewModel 创建失败, 请查看view 模块是否添加了@CreateViewModel(xx.class) 注解");
        }
    }
}
