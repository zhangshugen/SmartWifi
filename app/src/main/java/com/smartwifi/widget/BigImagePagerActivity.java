package com.smartwifi.widget;


import android.graphics.PointF;
import android.net.Uri;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.ImageViewState;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.smartwifi.R;
import com.smartwifi.base.BaseViewPagerAdapter;
import com.smartwifi.databinding.ActivityBigImagePagerBinding;
import com.smartwifi.databinding.ItemPagerImageBinding;
import com.smartwifi.utils.BitmapUtils;
import com.smartwifi.utils.DisplayUtils;
import com.smartwifi.utils.FileUtils;
import com.smartwifi.utils.ImageUitls;
import com.smartwifi.utils.MD5Utils;
import com.smartwifi.utils.ToastUtils;
import com.smartwifi.widget.mvvm.view.BaseMVVMActivity;
import com.smartwifi.widget.mvvm.view.BaseMVVMView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * 查看大图 glide
 */
public class BigImagePagerActivity extends BaseMVVMActivity implements BaseMVVMView, BaseViewPagerAdapter.BaseViewPageItemDecorator<String, ItemPagerImageBinding>, BaseViewPagerAdapter.BaseViewPageItemPresenter<String> {
    public static final String INTENT_IMG_URLS = "imgUrls";
    public static final String INTENT_POSITION = "position";
    public static final String INTENT_IAG_TYPE = "imageType";
    ActivityBigImagePagerBinding binding;
    private List<View> pointViewList;
    private int imageType;
    public static final int TYPE_NET_IMAGE = 1;
    public static final int TYPE_FILE_IMAGE = 2;

    @Override
    public int getLayoutId() {
        return R.layout.activity_big_image_pager;
    }

    @Override
    public void initView() {
        binding = (ActivityBigImagePagerBinding) mBinding;
        SystemBarTintManagerHelper.getInsatance().setStatusBarTintResource(R.color.transparent);

        ArrayList<String> imgUrls = getIntent().getStringArrayListExtra(INTENT_IMG_URLS);
        int startPosition = getIntent().getIntExtra(INTENT_POSITION, 0);
        imageType = getIntent().getIntExtra(INTENT_IAG_TYPE, 1);
        pointViewList = new ArrayList<>();
        addGuidePointView(startPosition, imgUrls);
        BaseViewPagerAdapter adapter = new BaseViewPagerAdapter(imgUrls) {
            @Override
            public View getRootView() {
                return View.inflate(BigImagePagerActivity.this, R.layout.item_pager_image, null);
            }
        };
        binding.pager.setAdapter(adapter);
        adapter.setItemDecorator(this);
        adapter.setItemPresenter(this);
        binding.pager.setCurrentItem(startPosition);
    }

    private void addGuidePointView(int startPosition, ArrayList<String> imgUrls) {
        if (imgUrls != null && imgUrls.size() > 0) {
            pointViewList.clear();
            for (int i = 0; i < imgUrls.size(); i++) {
                if (!TextUtils.isEmpty(imgUrls.get(i))) {
                    View view = new View(this);
                    view.setBackgroundResource(R.drawable.selector_guide_bg);
                    view.setSelected(i == startPosition);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(DisplayUtils.dip2px(6),
                            DisplayUtils.dip2px(6));
                    layoutParams.setMargins(10, 0, 0, 0);
                    binding.guideGroup.addView(view, layoutParams);
                    pointViewList.add(view);
                }
            }
        }

    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    public boolean isUseProxy() {
        return false;
    }

    @Override
    public void decorator(ItemPagerImageBinding itemBinding, int position, int viewType, String data) {
        ProgressBar loading = new ProgressBar(BigImagePagerActivity.this);
        FrameLayout.LayoutParams loadingLayoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingLayoutParams.gravity = Gravity.CENTER;
        loading.setLayoutParams(loadingLayoutParams);
        ViewGroup root = (ViewGroup) itemBinding.getRoot();
        root.addView(loading);
        loading.setVisibility(View.VISIBLE);
        itemBinding.image.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CUSTOM);
        itemBinding.image.setMinScale(0.1F);

        if (imageType == TYPE_NET_IMAGE) {
            showNetImage(data, itemBinding.image, loading, itemBinding.save);

        } else {
            lookFileImage(data, itemBinding.image, itemBinding.save, loading);
        }

    }

    private void lookFileImage(final String imgurl, final SubsamplingScaleImageView imageView, final TextView save, final ProgressBar loading) {
        File file = new File(imgurl);
        notifyImage(file, save);
        ImageUitls.savePicRefreshGallery(BigImagePagerActivity.this, file);
        imageView.setImage(ImageSource.uri(file.getAbsolutePath()), new ImageViewState(1f, new PointF(0, 0), 0));
        loading.setVisibility(View.GONE);
    }

    private void showNetImage(final String imgUrl, final SubsamplingScaleImageView imageView, final ProgressBar loading, final TextView save) {

        Glide.with(BigImagePagerActivity.this).
                load(imgUrl).downloadOnly(new SimpleTarget<File>() {
            @Override
            public void onResourceReady(File resource, GlideAnimation<? super File> glideAnimation) {
                loading.setVisibility(View.GONE);
                notifyImage(resource, imgUrl, save);
                float initImageScale = BitmapUtils.getInitImageScale(resource.getAbsolutePath(), BigImagePagerActivity.this);
                imageView.setMaxScale(initImageScale + 2.0f);//最大显示比例
                imageView.setImage(ImageSource.uri(Uri.fromFile(resource)),
                        new ImageViewState(initImageScale, new PointF(0, 0), 0));
            }
        });
    }

    private void notifyImage(final File bitmap, final String url, final TextView save) {
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileUtils.saveFile(bitmap, MD5Utils.encode(url) + ".jpg", BigImagePagerActivity.this);
                save.setVisibility(View.GONE);
            }
        });
    }

    private void notifyImage(final File file, final TextView save) {
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save.setVisibility(View.GONE);
                ToastUtils.showShort("图片处于" + file.getAbsolutePath());

            }
        });
    }

    public void exitImageActivity() {
        BigImagePagerActivity.this.finish();
        BigImagePagerActivity.this.overridePendingTransition(R.anim.act_fade_in_center,
                R.anim.act_fade_out_center);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            exitImageActivity();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onViewPageItemClick(int position, String itemData) {

    }
}
