package com.skyvn.ten.bean;

public class BannerBO {


    /**
     * createTime : 2019-12-28 11:36:32
     * updateTime : 2020-02-10 14:28:51
     * delStatus : 0
     * id : 1
     * applicationId : 1
     * imageUrl : http://
     * forwardUrl : 1
     * remark : ldfjg
     */

    private String createTime;
    private String updateTime;
    private int delStatus;
    private String id;
    private String applicationId;
    private String imageUrl;
    private String forwardUrl;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getForwardUrl() {
        return forwardUrl;
    }

    public void setForwardUrl(String forwardUrl) {
        this.forwardUrl = forwardUrl;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
