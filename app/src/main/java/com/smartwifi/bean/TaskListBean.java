package com.smartwifi.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.gson.annotations.SerializedName;
import com.smartwifi.BR;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/18
 * @Describe
 */

public class TaskListBean extends BaseObservable {

    public String getBAK5() {
        return BAK5 == null ? "" : BAK5;
    }

    public String getBAK0() {
        return BAK0 == null ? "" : BAK0;
    }

    public String getPA() {
        return PA == null ? "" : PA;
    }

    public String getPLANDATE() {
        return PLANDATE == null ? "" : PLANDATE;
    }

    public String getSC() {
        return SC == null ? "" : SC;
    }

    public int getPGNODE() {
        return PGNODE;
    }

    public int getPANODE() {
        return PANODE;
    }

    public String getSS() {
        return SS == null ? "" : SS;
    }

    public String getBAK9() {
        return BAK9 == null ? "" : BAK9;
    }

    public String getZRR() {
        return ZRR == null ? "" : ZRR;
    }

    public String getBAK4() {
        return BAK4 == null ? "" : BAK4;
    }

    public int getPERCENT() {
        return PERCENT;
    }

    public String getSTATUSNAME() {
        return STATUSNAME == null ? "" : STATUSNAME;
    }

    public Object getAPPROVESTATUS() {
        return APPROVESTATUS;
    }

    public String getSTATUSCOLOR() {
        return STATUSCOLOR == null ? "" : STATUSCOLOR;
    }

    public int getTZFOLLOW() {
        return TZFOLLOW;
    }

    public Object getDATECOMPLETED() {
        return DATECOMPLETED;
    }

    public int getRN() {
        return RN;
    }

    /**
     * BAK5 : 厅文件
     * BAK0 : 交通建设市场监管体系构建调研实施及问题梳理
     * PA : 3月中旬深入一线开展调查研究，3月下旬集中对调研情况进行全面梳理，列出问题清单，认真剖析、找准症结和关键。
     * PLANDATE : 2018-03-31
     * SC : 2018030900006
     * PGNODE : 1000000
     * PANODE : 1000000
     * SS : KR
     * BAK9 : 厅基本建设处
     * ZRR : 罗恒
     * BAK4 : 曾胜
     * PERCENT : 80
     * STATUSNAME : 逾期18天
     * APPROVESTATUS : null
     * STATUSCOLOR : #EA1B1B
     * TZFOLLOW : 0
     * DATECOMPLETED : null
     * RN : 1
     */

    @SerializedName("BAK5")
    public String BAK5;
    @SerializedName("BAK0")
    public String BAK0;
    @SerializedName("PA")
    public String PA;
    @SerializedName("PLANDATE")
    public String PLANDATE;
    @SerializedName("SC")
    public String SC;
    @SerializedName("PGNODE")
    public int PGNODE;
    @SerializedName("PANODE")
    public int PANODE;
    @SerializedName("SS")
    public String SS;
    @SerializedName("BAK9")
    public String BAK9;
    @SerializedName("ZRR")
    public String ZRR;
    @SerializedName("BAK4")
    public String BAK4;
    @SerializedName("PERCENT")
    public int PERCENT;
    @SerializedName("STATUSNAME")
    public String STATUSNAME;
    @SerializedName("APPROVESTATUS")
    public Object APPROVESTATUS;
    @SerializedName("STATUSCOLOR")
    public String STATUSCOLOR;
    @SerializedName("TZFOLLOW")
    public int TZFOLLOW;
    @SerializedName("DATECOMPLETED")
    public Object DATECOMPLETED;
    @SerializedName("RN")
    public int RN;

    public void setOpen(boolean open) {
        isOpen = open;
        notifyPropertyChanged(BR.isOpen);
    }

    @Bindable
    public boolean isOpen = false;
}
