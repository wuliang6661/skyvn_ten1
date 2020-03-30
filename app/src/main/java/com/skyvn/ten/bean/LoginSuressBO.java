package com.skyvn.ten.bean;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2020/1/1614:55
 * desc   : 登录成功的返回
 * version: 1.0
 */
public class LoginSuressBO {


    /**
     * id : 1217701108866392065
     * phone : 13067980276
     * status : 0
     * token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkYXRlIjoiMTU3OTE1NzY5NDgwMSIsImlkIjoiMTIxNzcwMTEwODg2NjM5MjA2NSJ9.4e6Ua764ReJLmEZYojBHxULDoBhMQXjG-rveAHfcwPc
     */

    private String id;
    private String phone;
    private int status;
    private String token;
    private String headPortrait;

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
