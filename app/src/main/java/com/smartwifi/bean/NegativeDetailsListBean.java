package com.smartwifi.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/9
 * @Describe
 */

public class NegativeDetailsListBean {
    public String code;
    public String content;
    public String id;
    public int type;

    public NegativeDetailsListBean(String code, String content, String id,int type) {
        this.code = code;
        this.content = content;
        this.id = id;
        this.type = type;
    }

    public static List<NegativeDetailsListBean> getNegativeDetailsListData() {
        List<NegativeDetailsListBean> listBeans= new ArrayList<>();
        listBeans.add(new NegativeDetailsListBean("ALJ15101","非软土路基的沉降最大处超过50mm，或沉降30mm以上长度累计超过合同段路基长度的5%。","1",1));
        listBeans.add(new NegativeDetailsListBean("ALJ15102","边坡单处塌方长度超过10m，或多处塌方累计长度超过合同段边坡长度的5%。","1",1));
        listBeans.add(new NegativeDetailsListBean("ALJ15103","路基构造物单处损坏（挡土墙、坡面防 护、排水设施等断裂或严重沉陷、坍塌） 长度超过10m，或多处损坏累计长度超过 合同段同类工程长度的5%","1",1));
        listBeans.add(new NegativeDetailsListBean("ALJ15104","沥青路面横向力系数(SFC)代表值小于设计值。","1",1));
        listBeans.add(new NegativeDetailsListBean("ALJ15105","沥青路面出现松散、严重泛油、明显离析和裂缝（少量反射裂缝除外）等现象。","1",1));
        listBeans.add(new NegativeDetailsListBean("ALJ15108","水泥混凝土路面存在断板情况。","1",1));
        listBeans.add(new NegativeDetailsListBean("ALJ15103","基础及下部构造、上部构造混凝土强度达不到设计要求。","1",1));
        listBeans.add(new NegativeDetailsListBean("ALJ15101","墩、台存在的裂缝超出有关标准和规范规定。","1",1));
        listBeans.add(new NegativeDetailsListBean("ALJ15106","预应力混凝土梁等受弯构件存在梁体竖向裂缝或宽度大于0.2mm的纵向裂缝。","1",1));
        listBeans.add(new NegativeDetailsListBean("ALJ15107","钢筋混凝土梁的主筋附近存在宽度大于0.2mm竖向裂缝，或梁腹板存在宽度大于0.3mm斜向或水平向裂缝。","1",1));
        listBeans.add(new NegativeDetailsListBean("ALJ15103","拱桥墩、台的位移或沉降超过设计允许值。","1",1));
        listBeans.add(new NegativeDetailsListBean("ALJ15104","二衬混凝土强度达不到设计要求。","1",1));
        listBeans.add(new NegativeDetailsListBean("ALJ15101","隧道路面存在隆起，路面板明显错台、断裂。","1",1));
        return listBeans;
    }
}
