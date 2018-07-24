package com.smartwifi.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.orhanobut.logger.Logger;
import com.smartwifi.bean.CommonEditProfileBean;
import com.smartwifi.utils.DateUtils;

import java.util.Date;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/10
 * @Describe
 */

public class EditProfileChooseDateView extends EditProfileChooseView {

    private TimePickerView pvTime;
    private String temPext;
    private boolean isShowMonth;
    private boolean isShowDay;

    public EditProfileChooseDateView(Context context) {
        super(context);
    }

    public EditProfileChooseDateView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public void init(CommonEditProfileBean info) {
        super.init(info);
        String text = "";
        isShowMonth = true;
        isShowDay = true;
        switch (info.getDEFTABLE()) {
            case "%Y":  //年  2018
                temPext = "yyyy";
                text = DateUtils.formatData(temPext, System.currentTimeMillis());
                isShowMonth = false;
                isShowDay = false;
                break;
            case "%Y%m":  //年月  201802
                temPext = "yyyyMM";
                text = DateUtils.formatData(temPext, System.currentTimeMillis());
                isShowDay = false;
                break;
            case "%Y%m%d": //前一天  2018-02-01
                temPext = "yyyy-MM-dd";
                text = DateUtils.getNextDay(-1, temPext);
                break;
            case "%Y%m%dN"://今天  2018-02-02
                temPext = "yyyy-MM-dd";
                text = DateUtils.formatData(temPext, System.currentTimeMillis());
                break;
            case "%Y%m%dN+1": //后一天  2018-02-03
                temPext = "yyyy-MM-dd";
                text = DateUtils.getNextDay(1, temPext);
                break;
            case "%Y%m%dN-7":  //前7天  2018-01-26
                temPext = "yyyy-MM-dd";
                text = DateUtils.getNextDay(-7, temPext);
                break;
            case "%Y%W": //返回周数  201806
                temPext = "yyyyW";
                text = DateUtils.formatData(temPext, System.currentTimeMillis());
                break;
            case "%Y%m%d%WS":  //返回周开始  周一   2018-01-29
                temPext = "yyyy-MM-dd";
                text = DateUtils.getFirstDayOfWeek(temPext);
                break;
            case "%Y%m%d%WE"://返回周结束 周五  2018-02-02
                temPext = "yyyy-MM-dd";
                text = DateUtils.getFridayOfWeek(temPext);
                break;
            case "%Y%m%d%FMS"://返回月开始  2018-02-01
                temPext = "yyyy-MM-dd";
                text = DateUtils.getFridayOfWeek(temPext);
                break;
            case "%Y%m%d%FME"://返回月结束  2018-02-28
                temPext = "yyyy-MM-dd";
                text = DateUtils.getLastDayOfMonth(temPext);
                break;
            case "%Y%m%d%JDS"://当前季度开始  2018-01-01
                temPext = "yyyy-MM-dd";
                text = DateUtils.getCurrentQuarterStart(temPext);
                break;
            case "%Y%m%d%JDE":  //当前季度结束  2018-03-31
                temPext = "yyyy-MM-dd";
                text = DateUtils.getCurrentQuarterEnd(temPext);
                break;
            case "%Y%m%d%FYS":   //当年开始  2018-01-01
                temPext = "yyyy-MM-dd";
                text = DateUtils.getStartDayOfYear(temPext);
                break;
            case "%Y%m%d%FYE":   //当年结束  2018-12-01
                temPext = "yyyy-MM-dd";
                text = DateUtils.getEndDayOfYear(temPext);
                break;
            default:
                break;
        }
        info.setDEFFIELD(text);
    }

    @Override
    public void chooseViewAction() {
        pvTime = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                String text = "";
                if (mInfo.getDEFTABLE().equals("%Y%W")) {
                    text = DateUtils.getYearWeekString(date.getTime());
                } else {
                    text = DateUtils.formatData(temPext, date.getTime());
                }
                Logger.d(text);
            }
        }).setType(new boolean[]{true, isShowMonth, isShowDay, false, false, false})
                .build();
        pvTime.show();

    }

    @Override
    public void onDestroy() {
        if (pvTime != null && pvTime.isShowing()) {
            pvTime.dismiss();
            pvTime = null;
        }
    }

}
