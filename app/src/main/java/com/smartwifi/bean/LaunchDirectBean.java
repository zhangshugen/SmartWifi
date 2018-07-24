package com.smartwifi.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.alibaba.fastjson.JSONArray;
import com.smartwifi.BR;

import java.io.Serializable;
import java.util.List;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/30
 * @Describe
 */

public class LaunchDirectBean extends BaseObservable implements Serializable {

    public static String data = "[\n" +
            "    {\n" +
            "        \"locationDetails\": \"龙琅高速 > 第一合同段 > 中交路建有限\",\n" +
            "        \"code\": \"桩号：AK1+100.22-AK12+988.46\",\n" +
            "        \"locationDesc\": \"这是位置描述啊\",\n" +
            "        \"id\": 1,\n" +
            "        \"questionType\": \"问题类型\",\n" +
            "        \"importantType\": \"重要程度\",\n" +
            "\"date\": \"2018-06-01\",\n" +
            "        \"superviseDesc\": \"张三同志因为年龄问题退休,张胖胖更改成李四\",\n" +
            "        \n" +
            "        \"file\": [\n" +
            "            \"http://pic2.ooopic.com/12/22/95/08bOOOPICe2_1024.jpg\",\n" +
            "            \"http://pic2.ooopic.com/12/22/95/08bOOOPICe2_1024.jpg\",\n" +
            "            \"http://pic2.ooopic.com/12/22/95/08bOOOPICe2_1024.jpg\"\n" +
            "        ] \n" +
            "    }\n" +
            "]";

    /**
     * location : 龙琅高速 > 第一合同段 > 中交路建有限
     * locationDesc : 这是位置描述啊
     * id : 1
     * superviseDesc : 张三同志因为年龄问题退休,张胖胖更改成李四
     * file : ["图片地址","图片地址","图片地址"]
     */
    public String code;
    public String location;
    public String locationDesc;
    public int id;
    public String superviseDesc;
    public List<String> file;

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
        notifyPropertyChanged(BR.questionType);
    }

    @Bindable
    public String questionType = "请选择";

    public void setDate(String date) {
        this.date = date;
        notifyPropertyChanged(BR.date);
    }

    @Bindable
    public String date = "请选择";

    public void setImportantType(String importantType) {
        this.importantType = importantType;
        notifyPropertyChanged(BR.importantType);
    }

    @Bindable
    public String importantType = "请选择";

    public void setLocationDetails(String locationDetails) {
        this.locationDetails = locationDetails;
        notifyPropertyChanged(BR.locationDetails);
    }

    @Bindable
    public String locationDetails = "";

    public static List<LaunchDirectBean> getDirectListBean() {
        return JSONArray.parseArray(data, LaunchDirectBean.class);
    }

}
