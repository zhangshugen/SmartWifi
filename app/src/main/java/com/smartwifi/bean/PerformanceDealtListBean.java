package com.smartwifi.bean;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/30
 * @Describe
 */

public class PerformanceDealtListBean implements Serializable {

    public static String data = "[\n" +
            "    {\n" +
            "        \"categoryName\": \"人员变更\",\n" +
            "        \"institution\": \"机 构：龙琅高速 > 第一合同段 > 中交路建有限\",\n" +
            "        \"id\": 1,\n" +
            "        \"stage\": \"流程阶段：质安局核备\",\n" +
            "        \"changeEngineer\": \"张胖胖更改成李四\",\n" +
            "        \"changeEngineerReason\": \"张三同志因为年龄问题退休,张胖胖更改成李四\",\n" +
            "        \"approvalProcess\": [\n" +
            "            {\n" +
            "                \"number\": 3,\n" +
            "                \"title\": \"赵柳-业主单位\",\n" +
            "                \"desc\": \"我是胖胖\",\n" +
            "                \"status\": \"2\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"number\": 2,\n" +
            "                \"title\": \"胖胖-主任科员\",\n" +
            "                \"desc\": \"我是胖胖\",\n" +
            "                \"status\": \"1\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"number\": 1,\n" +
            "                \"title\": \"胖胖-主任科员\",\n" +
            "                \"desc\": \"我是胖胖\",\n" +
            "                \"status\": \"0\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"file\": [\n" +
            "            \"http://pic2.ooopic.com/12/22/95/08bOOOPICe2_1024.jpg\",\n" +
            "            \"http://pic2.ooopic.com/12/22/95/08bOOOPICe2_1024.jpg\",\n" +
            "            \"http://pic2.ooopic.com/12/22/95/08bOOOPICe2_1024.jpg\"\n" +
            "        ],\n" +
            "        \"opinion\": \"同意此行为\"\n" +
            "    }\n" +
            "]";
    /**
     * categoryName : 人员变更
     * institution : 机 构：龙琅高速 > 第一合同段 > 中交路建有限
     * id : 1
     * stage : 流程阶段：质安局核备
     * changeEngineer : 张胖胖更改成李四
     * changeEngineerReason : 张三同志因为年龄问题退休,张胖胖更改成李四
     * approvalProcess : [{"number":3,"title":"赵柳-业主单位","status":"2"},{"number":2,"title":"胖胖-主任科员","status":"1"},{"number":1,"title":"胖胖-主任科员","status":"0"}]
     * file : ["图片地址","图片地址","图片地址"]
     * opinion : 同意此行为
     */

    public String categoryName;
    public String institution;
    public int id;
    public String stage;
    public String changeEngineer;
    public String changeEngineerReason;
    public String opinion;
    public List<ApprovalProcessBean> approvalProcess;
    public List<String> file;

    public static List getPerformanceDealtListBeanList(int fragmentType) {
        return JSONObject.parseArray(data, PerformanceDealtListBean.class);
    }

    public static class ApprovalProcessBean implements Serializable {
        /**
         * number : 3
         * title : 赵柳-业主单位
         * status : 2
         */

        public int number;
        public String title;
        public String status;
        public String desc;
    }
}
