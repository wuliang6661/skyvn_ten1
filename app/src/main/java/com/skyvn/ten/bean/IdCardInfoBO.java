package com.skyvn.ten.bean;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2020/4/114:30
 * desc   :
 * version: 1.0
 */
public class IdCardInfoBO {


    /**
     * birthday :
     * gender : 0
     * idCardBackOssUrl :
     * idCardFrontOssUrl :
     * idCardNo :
     * realName :
     */

    private String birthday;
    private int gender;
    private String idCardBackOssUrl;
    private String idCardFrontOssUrl;
    private String idCardNo;
    private String realName;

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getIdCardBackOssUrl() {
        return idCardBackOssUrl;
    }

    public void setIdCardBackOssUrl(String idCardBackOssUrl) {
        this.idCardBackOssUrl = idCardBackOssUrl;
    }

    public String getIdCardFrontOssUrl() {
        return idCardFrontOssUrl;
    }

    public void setIdCardFrontOssUrl(String idCardFrontOssUrl) {
        this.idCardFrontOssUrl = idCardFrontOssUrl;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
}
