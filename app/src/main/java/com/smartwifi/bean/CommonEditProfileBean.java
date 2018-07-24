package com.smartwifi.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.gson.annotations.SerializedName;
import com.smartwifi.BR;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/9
 * @Describe
 */

public class CommonEditProfileBean extends BaseObservable {


    public void setFIELD_NAME(String FIELD_NAME) {
        this.FIELD_NAME = FIELD_NAME;
    }

    public void setTEXT_PROMPT(String TEXT_PROMPT) {
        this.TEXT_PROMPT = TEXT_PROMPT;
    }

    public void setDEFAULT_VALUE(String DEFAULT_VALUE) {
        this.DEFAULT_VALUE = DEFAULT_VALUE;
    }

    public void setPROMPT_FLAG(String PROMPT_FLAG) {
        this.PROMPT_FLAG = PROMPT_FLAG;
    }

    public void setCOPY_FLAG(String COPY_FLAG) {
        this.COPY_FLAG = COPY_FLAG;
    }

    public void setDISPLAY_FLAG(String DISPLAY_FLAG) {
        this.DISPLAY_FLAG = DISPLAY_FLAG;
    }

    public void setMANDATORY_FLAG(String MANDATORY_FLAG) {
        this.MANDATORY_FLAG = MANDATORY_FLAG;
    }

    public void setEXTFLAG(String EXTFLAG) {
        this.EXTFLAG = EXTFLAG;
    }

    public void setDEFTABLE(String DEFTABLE) {
        this.DEFTABLE = DEFTABLE;
    }

    public void setDEFFIELD(String DEFFIELD) {
        this.dEFFIELD = DEFFIELD;
        notifyPropertyChanged(BR.dEFFIELD);
    }

    public void setDEFAULT_TYPE(String DEFAULT_TYPE) {
        this.DEFAULT_TYPE = DEFAULT_TYPE;
    }

    public void setKD(double KD) {
        this.KD = KD;
    }

    public void setBAK1(String BAK1) {
        this.BAK1 = BAK1;
    }

    public void setBAK2(String BAK2) {
        this.BAK2 = BAK2;
    }

    /**
     * FIELD_NAME : BAK2
     * TEXT_PROMPT : 任务属性
     * DEFAULT_VALUE : null
     * PROMPT_FLAG : null
     * COPY_FLAG : null
     * DISPLAY_FLAG : F
     * MANDATORY_FLAG : T
     * EXTFLAG : T
     * DEFTABLE : 任务分类
     * DEFFIELD : 综合管理
     * DEFAULT_TYPE : C
     * KD : 0.0
     * BAK1 : null
     * BAK2 : null
     */

    private String FIELD_NAME;
    private String TEXT_PROMPT;
    private String DEFAULT_VALUE;
    private String PROMPT_FLAG;
    private String COPY_FLAG;
    private String DISPLAY_FLAG;
    private String MANDATORY_FLAG;
    private String EXTFLAG;
    private String DEFTABLE;
    @Bindable
    @SerializedName("DEFFIELD")
    private String dEFFIELD;
    private String DEFAULT_TYPE;
    private double KD;
    private String BAK1;
    private String BAK2;

    public String getFIELD_NAME() {
        return FIELD_NAME == null ? "" : FIELD_NAME;
    }

    public String getTEXT_PROMPT() {
        return TEXT_PROMPT == null ? "" : TEXT_PROMPT;
    }

    public String getDEFAULT_VALUE() {
        return DEFAULT_VALUE == null ? "" : DEFAULT_VALUE;
    }

    public String getPROMPT_FLAG() {
        return PROMPT_FLAG == null ? "" : PROMPT_FLAG;
    }

    public String getCOPY_FLAG() {
        return COPY_FLAG == null ? "" : COPY_FLAG;
    }

    public String getDISPLAY_FLAG() {
        return DISPLAY_FLAG == null ? "" : DISPLAY_FLAG;
    }

    public String getMANDATORY_FLAG() {
        return MANDATORY_FLAG == null ? "" : MANDATORY_FLAG;
    }

    public String getEXTFLAG() {
        return EXTFLAG == null ? "" : EXTFLAG;
    }

    public String getDEFTABLE() {
        return DEFTABLE == null ? "" : DEFTABLE;
    }

    public String getDEFFIELD() {
        return dEFFIELD == null ? "" : dEFFIELD;
    }

    public String getDEFAULT_TYPE() {
        return DEFAULT_TYPE == null ? "" : DEFAULT_TYPE;
    }

    public double getKD() {
        return KD;
    }

    public String getBAK1() {
        return BAK1 == null ? "" : BAK1;
    }

    public String getBAK2() {
        return BAK2 == null ? "" : BAK2;
    }
}
