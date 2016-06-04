package com.liangduo.kr36.bean;

import android.content.Context;

/**
 * Created by liangduo on 16/5/21.
 */
public class EventBusBean {
    String  phone;
    String  psw;

    public EventBusBean(String phone, String psw) {
        this.phone = phone;
        this.psw = psw;
    }

    public EventBusBean() {
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }
}
