package com.smartwifi.view;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;

import com.smartwifi.R;
import com.smartwifi.bean.BaseListData;
import com.smartwifi.bean.CommonEditProfileBean;
import com.smartwifi.bean.ProfileSelectionStaffBean;
import com.smartwifi.bean.ProfileSelectionStaffLocalBean;
import com.smartwifi.constant.Constant;
import com.smartwifi.databinding.ViewEditProfileTextBinding;
import com.smartwifi.event.ProfileSelectionStaffLocalEvent;
import com.smartwifi.manager.retrofit.RetrofitJsonManager;
import com.smartwifi.rxjava.RxJavaHttpHelper;
import com.smartwifi.utils.CommonUtils;
import com.smartwifi.utils.ToastUtils;
import com.smartwifi.widget.dialogfragment.ProfileSelectionStaffDialogFragment;
import com.smartwifi.widget.retrofithelper.rxschedulers.RxSchedulersHelper;
import com.smartwifi.widget.retrofithelper.rxsubscriber.ProgressObserver;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/9
 * @Describe
 */

public class EditProfileTextView extends EditProfileView<ViewEditProfileTextBinding> {
    private List<ProfileSelectionStaffLocalBean> profileSelectionStaffLocalBeanList;


    private List<ProfileSelectionStaffBean> staffBeanList;


    public EditProfileTextView(Context context) {
        super(context);
    }

    public EditProfileTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void initData(Context context) {

    }

    @Override
    public void initEvent(Context context) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.view_edit_profile_text;
    }

    @Override
    protected void initView(Context context, AttributeSet attrs) {
        EventBus.getDefault().register(this);
    }

    @Override
    public void init(CommonEditProfileBean info) {
        super.init(info);
        mBinding.setInfo(mInfo);
        mBinding.setView(this);
    }

    public void onEditTextClick() {

    }

    public void onViewClick() {
        //  if (mInfo.getFIELD_NAME().equals("BAK9") || mInfo.getFIELD_NAME().equals("BAK9")||mInfo.getFIELD_NAME().equals("BAK9"))
        if (staffBeanList != null && staffBeanList.size() != 0) {
            ProfileSelectionStaffDialogFragment dialogFragment = new ProfileSelectionStaffDialogFragment();
            dialogFragment.setFirstTitleString("选择部门");
            dialogFragment.setData(profileSelectionStaffLocalBeanList, staffBeanList, (String) getTag(), mInfo);
            dialogFragment.show(((AppCompatActivity) mContext).getSupportFragmentManager());
            return;
        }
        RetrofitJsonManager.getInstance().getApiService().getStaffData().subscribeOn(Schedulers.io()).compose(RxJavaHttpHelper.<BaseListData<ProfileSelectionStaffBean>>handleResult()).flatMap(new Function<BaseListData<ProfileSelectionStaffBean>, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(BaseListData<ProfileSelectionStaffBean> profileSelectionStaffBeanBaseListData) throws Exception {
                List<ProfileSelectionStaffLocalBean> profileList1 = new ArrayList<>();
                List<ProfileSelectionStaffBean> netList = profileSelectionStaffBeanBaseListData.ListData;
                for (int i = 0; i < netList.size(); i++) {

                    ProfileSelectionStaffBean bean = netList.get(i);
                    ProfileSelectionStaffLocalBean localBean = new ProfileSelectionStaffLocalBean();
/*
                    if (mInfo.getFIELD_NAME().equals("BAK24")) {
                        //如果是选择配合处室,那么不需要增加厅领导层次
                        if (bean.getNWNAME().equals("厅领导")) {
                            continue;
                        }
                    }
*/
                    localBean.chooseLevel = mInfo.getBAK1();
                    localBean.viewCategoryTag = (String) getTag();
                    localBean.position = 0;
                    localBean.job = bean.getZC();
                    localBean.userName = bean.getXM();
                    localBean.phone = bean.getIDENTITY();
                    localBean.companyName = bean.getNWNAME();
                    localBean.sort = bean.getNW();
                    profileList1.add(localBean);
                }
                CommonUtils.handlerList(profileList1);
                final Map<Integer, List> map = new HashMap();
                map.put(1, profileSelectionStaffBeanBaseListData.ListData);
                map.put(2, profileList1);
                return Observable.create(new ObservableOnSubscribe<Map<Integer, List>>() {
                    @Override
                    public void subscribe(ObservableEmitter<Map<Integer, List>> emitter) throws Exception {
                        try {
                            emitter.onNext(map);
                            emitter.onComplete();
                        } catch (Exception e) {
                            emitter.onError(e);
                        }
                    }
                });

            }
        }).compose(RxSchedulersHelper.applyIoTransformer()).subscribe(new ProgressObserver<Map<Integer, List>>((AppCompatActivity) mContext, true, lifeCycleListener) {
            @Override
            public void _onNext(Map<Integer, List> map) {
                staffBeanList = map.get(1);
                ProfileSelectionStaffDialogFragment dialogFragment = new ProfileSelectionStaffDialogFragment();
                dialogFragment.setFirstTitleString("选择部门");
                profileSelectionStaffLocalBeanList = map.get(2);
                dialogFragment.setData(profileSelectionStaffLocalBeanList, staffBeanList, (String) getTag(),mInfo);
                dialogFragment.show(((AppCompatActivity) mContext).getSupportFragmentManager());
            }

        });

    }

    public void setText(String text) {
        mInfo.setDEFFIELD(text);
    }

    public String getEditextString() {
        return mBinding.etText.getText().toString().trim();
    }

    public boolean canSubmit() {
        if (mInfo.getDISPLAY_FLAG().equals(Constant.TYPE_TRUE)) {
            return false;
        }
        if (mInfo.getMANDATORY_FLAG().equals(Constant.TYPE_TRUE)) {
            ToastUtils.showShort(mInfo.getBAK2());
            return false;
        }
        return true;
    }

    public String sumbit() {
        if (canSubmit()) return getEditextString();
        return "";
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onProfileSelectionStaffLocalEvent(ProfileSelectionStaffLocalEvent event) {
        if (getTag().equals(event.mViewTag)) {
            if (event.mViewTag.equals("BAK9")) {
                setText(event.mProfileSelectionStaffLocalBean.superiorUnit);
            } else {
                setText(event.showName);
            }
        }
    }

    public List<ProfileSelectionStaffBean> getStaffBeanList() {
        if (staffBeanList == null) {
            return new ArrayList<>();
        }
        return staffBeanList;
    }
}
