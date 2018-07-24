package com.smartwifi.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.smartwifi.BR;

import java.util.List;


/**
 * Created by Administrator on 2018/7/9.
 */

public abstract class BaseViewPagerAdapter<E extends Object, D extends ViewDataBinding> extends PagerAdapter {
    public List numberList;
    private BaseViewPageItemPresenter mPresenter;

    public BaseViewPagerAdapter(List<E> numberList) {
        this.numberList = numberList;
    }

    public BaseViewPagerAdapter() {

    }

    @Override
    public int getCount() {
        return numberList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public void setItemDecorator( BaseViewPageItemDecorator decorator) {
        this.decorator = decorator;
    }
    public void setItemPresenter(BaseViewPageItemPresenter presenter) {
        this.mPresenter = presenter;
    }
    public interface BaseViewPageItemDecorator<T, D> {
        void decorator(D binding, int position, int viewType, T data);
    }

    protected BaseViewPageItemDecorator decorator;

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View rootView = getRootView();
        D b = DataBindingUtil.bind(rootView);
        E data = (E) numberList.get(position);
        b.setVariable(BR.presenter, mPresenter);
        b.setVariable(BR.itemData, data);
        b.setVariable(BR.itemPosition, position);
        if (decorator != null)
            decorator.decorator(b, position, 0, data);
        b.executePendingBindings();
        container.addView(rootView);
        return rootView;
    }


    private int mChildCount = 0;

    @Override
    public void notifyDataSetChanged() {
        mChildCount = getCount();
        super.notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        if (mChildCount > 0) {
            mChildCount--;
            return POSITION_NONE;
        }
        return super.getItemPosition(object);
    }
    public  interface BaseViewPageItemPresenter<T> {
        void onViewPageItemClick(int position, T itemData);
    }
    public abstract View getRootView();
}
