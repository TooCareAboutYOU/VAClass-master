package com.dts.vaclass.database.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by zs on 2018/2/2.
 */

@Entity
public class PayDataBean {

    private int payType;
    private String payName;
    private String payMoney;

    public String getPayMoney() {
        return this.payMoney;
    }
    public void setPayMoney(String payMoney) {
        this.payMoney = payMoney;
    }
    public String getPayName() {
        return this.payName;
    }
    public void setPayName(String payName) {
        this.payName = payName;
    }
    public int getPayType() {
        return this.payType;
    }
    public void setPayType(int payType) {
        this.payType = payType;
    }
    @Generated(hash = 588242560)
    public PayDataBean(int payType, String payName, String payMoney) {
        this.payType = payType;
        this.payName = payName;
        this.payMoney = payMoney;
    }
    @Generated(hash = 955675747)
    public PayDataBean() {
    }


}
