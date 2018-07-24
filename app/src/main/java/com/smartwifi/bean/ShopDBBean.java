package com.smartwifi.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/16
 * @Describe
 */
@Entity
public class ShopDBBean {   //不能用int
    @Id(autoincrement = true)
    public Long id;
    public String name;
    public String price;
    @Generated(hash = 33578642)
    public ShopDBBean(Long id, String name, String price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
    @Generated(hash = 728999138)
    public ShopDBBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPrice() {
        return this.price;
    }
    public void setPrice(String price) {
        this.price = price;
    }

}
