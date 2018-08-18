package com.smartwifi.widget.loopviewpage;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;


import com.smartwifi.R;
import com.smartwifi.bean.BannerInfo;
import com.smartwifi.widget.giide.GlideImageLoader;

import java.util.List;


/**
 * @author ryze
 * @since 1.0  2018/07/17
 */
public class AutoSwitchAdapter extends AutoLoopSwitchBaseAdapter {

    private Context mContext;

    private List<BannerInfo> mDatas;

    public AutoSwitchAdapter() {
        super();
    }

    public AutoSwitchAdapter(Context mContext, List<BannerInfo> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
    }

    @Override
    public int getDataCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public View getView(int position) {

        ImageView imageView = new ImageView(mContext);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT));
        final BannerInfo info = (BannerInfo) getItem(position);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        // 展示数据
        GlideImageLoader.onDisplayImage(imageView, info.imgUrl, R.drawable.test);
        return imageView;
    }

    @Override
    public Object getItem(int position) {
        if (position >= 0 && position < getDataCount()) {

            return mDatas.get(position);
        }
        return null;
    }


    @Override
    public View getEmptyView() {
        return null;
    }

    @Override
    public void updateView(View view, int position) {

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }
}
