package com.skyvn.ten.bean;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2020/1/199:53
 * desc   : 客服实体类
 * version: 1.0
 */
public class KeFuBO {


    /**
     * createTime : 2020-01-15 15:17:29
     * updateTime : 2020-01-16 16:39:01
     * delStatus : 0
     * id : 0
     * applicationId : 1
     * name : 客服电话1
     * contact : 038271273
     * type : 0
     * orderNum : 0
     * remark :
     */

    private String createTime;
    private String updateTime;
    private int delStatus;
    private String id;
    private String applicationId;
    private String name;
    private String contact;
    private int type;
    private int orderNum;
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

    public int getDelStatus() {
        return delStatus;
    }

    public void setDelStatus(int delStatus) {
        this.delStatus = delStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
