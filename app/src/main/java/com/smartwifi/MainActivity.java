package com.smartwifi;

import com.orhanobut.logger.Logger;
import com.smartwifi.base.BaseListActivity;
import com.smartwifi.bean.TestBean;
import com.smartwifi.databinding.ItemMainBinding;
import com.smartwifi.part.MainViewModel;
import com.smartwifi.view.recyclerView.RefreshRecyclerNetConfig;
import com.smartwifi.widget.databindingadapter.BaseBindingItemPresenter;
import com.smartwifi.widget.databindingadapter.BaseDataBindingDecorator;
import com.smartwifi.widget.databindingadapter.BindingViewHolder;
import com.smartwifi.widget.mvvm.factory.CreateViewModel;
import com.smartwifi.widget.mvvm.view.BaseMVVMView;

import java.util.Map;
import io.reactivex.Observable;


@CreateViewModel(MainViewModel.class)
public class MainActivity extends BaseListActivity<BaseMVVMView, MainViewModel> implements BaseMVVMView, BaseBindingItemPresenter<TestBean> {


    @Override
    protected RefreshRecyclerNetConfig getRefreshRecyclerNetConfig() {
        return new RefreshRecyclerNetConfig() {
            @Override
            public Observable getNetObservable(Map<String, Object> map) {
                return null;
            }
        };
    }

    @Override
    protected int getItemLayout() {
        return R.layout.item_main;
    }

    @Override
    public void initData() {
        super.initData();
        mAdapter.setItemPresenter(this);
        mAdapter.setItemDecorator(new BaseDataBindingDecorator<TestBean>() {

            @Override
            public void decorator(BindingViewHolder holder, int position, int viewType, TestBean mData) {
                ItemMainBinding binding = (ItemMainBinding) holder.getBinding();
                binding.tv.setText(mData.text);
            }
        });
    }

    @Override
    public void onItemClick(int position, TestBean itemData) {
        Logger.d(itemData.text + position);
    }

    public void test(int position, TestBean itemData) {
        Logger.d(itemData.text + position);
    }
}

