package com.skyvn.ten.bean;

import java.io.Serializable;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2020/1/1914:40
 * desc   : 通讯录信息
 * version: 1.0
 */
public class ContactBO implements Serializable {

    private String name;

    private String phone;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
