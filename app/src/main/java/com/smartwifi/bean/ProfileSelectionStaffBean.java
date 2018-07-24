package com.smartwifi.bean;

import android.databinding.BaseObservable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/16
 * @Describe
 */

public class ProfileSelectionStaffBean extends BaseObservable implements Serializable {


    /**
     * NW : 000000010423
     * NWNAME : 直属机构
     * BM : 103
     * BMNAME : 湖南省水运管理局
     * XJBM : 10302
     * XJBMNAME : 水运局办公室
     * PX : 11923
     * IDENTITY : 13723870808
     * XM : 胡建芳
     * ZC : null
     */

    @SerializedName("NW")
    public String NW;
    @SerializedName("NWNAME")
    public String NWNAME;
    @SerializedName("BM")
    public String BM;
    @SerializedName("BMNAME")
    public String BMNAME;
    @SerializedName("XJBM")
    public String XJBM;
    @SerializedName("XJBMNAME")
    public String XJBMNAME;
    @SerializedName("PX")
    public String PX;
    @SerializedName("IDENTITY")
    public String IDENTITY;
    @SerializedName("XM")
    public String XM;
    @SerializedName("ZC")
    public String ZC;

    public String getNW() {
        return NW == null ? "" : NW;
    }

    public String getNWNAME() {
        return NWNAME == null ? "" : NWNAME;
    }

    public String getBM() {
        return BM == null ? "" : BM;
    }

    public String getBMNAME() {
        return BMNAME == null ? "" : BMNAME;
    }

    public String getXJBM() {
        return XJBM == null ? "" : XJBM;
    }

    public String getXJBMNAME() {
        return XJBMNAME == null ? "" : XJBMNAME;
    }

    public String getPX() {
        return PX == null ? "" : PX;
    }

    public String getIDENTITY() {
        return IDENTITY == null ? "" : IDENTITY;
    }

    public String getXM() {
        return XM == null ? "" : XM;
    }

    public String getZC() {
        return ZC == null ? "" : ZC;
    }
}
