package com.skyvn.ten.bean;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2020/1/1910:21
 * desc   : 认证结果bean
 * version: 1.0
 */
public class AttentionSourrssBO {


    /**
     * code : 1
     * needStatus : 1
     */

    private String code;
    private int needStatus;
    private String copyWriter;

    public String getCopyWriter() {
        return copyWriter;
    }

    public void setCopyWriter(String copyWriter) {
        this.copyWriter = copyWriter;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getNeedStatus() {
        return needStatus;
    }

    public void setNeedStatus(int needStatus) {
        this.needStatus = needStatus;
    }
}
