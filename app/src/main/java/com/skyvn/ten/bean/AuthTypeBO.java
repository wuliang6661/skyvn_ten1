package com.skyvn.ten.bean;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2020/1/1913:30
 * desc   : 认证状态
 * version: 1.0
 */
public class AuthTypeBO {


    /**
     * code : 0
     * name : 个人资料验证
     * needStatus : 1
     * status : 1
     */

    private String code;
    private String name;
    private int needStatus;
    private int status;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNeedStatus() {
        return needStatus;
    }

    public void setNeedStatus(int needStatus) {
        this.needStatus = needStatus;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
