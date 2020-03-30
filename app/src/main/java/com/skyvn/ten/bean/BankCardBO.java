package com.skyvn.ten.bean;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2020/1/1617:12
 * desc   :  银行卡对象
 * version: 1.0
 */
public class BankCardBO {


    /**
     * createTime : 2020-01-16 16:44:10
     * updateTime : 2020-01-16 16:44:10
     * del_status : 0
     * id : 1217729239274790913
     * clientId : 1217357586153803778
     * tenantId : 0
     * name : df
     * bank : dfg
     * subbranch : df
     * depositAddress :
     * cardNo : dfg
     * virtualCardNo : null
     * imageOssUrl :
     * remark :
     */

    private String createTime;
    private String updateTime;
    private int del_status;
    private String id;
    private String clientId;
    private String tenantId;
    private String name;
    private String bank;
    private String subbranch;
    private String depositAddress;
    private String cardNo;
    private Object virtualCardNo;
    private String imageOssUrl;
    private String remark;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public int getDel_status() {
        return del_status;
    }

    public void setDel_status(int del_status) {
        this.del_status = del_status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getSubbranch() {
        return subbranch;
    }

    public void setSubbranch(String subbranch) {
        this.subbranch = subbranch;
    }

    public String getDepositAddress() {
        return depositAddress;
    }

    public void setDepositAddress(String depositAddress) {
        this.depositAddress = depositAddress;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public Object getVirtualCardNo() {
        return virtualCardNo;
    }

    public void setVirtualCardNo(Object virtualCardNo) {
        this.virtualCardNo = virtualCardNo;
    }

    public String getImageOssUrl() {
        return imageOssUrl;
    }

    public void setImageOssUrl(String imageOssUrl) {
        this.imageOssUrl = imageOssUrl;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
