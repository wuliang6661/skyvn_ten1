package com.skyvn.ten.bean;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2020/1/1710:56
 * desc   :  标签bean
 * version: 1.0
 */
public class LablesBO {


    /**
     * createTime : 2020-01-16 19:34:00
     * updateTime : 2020-01-16 19:34:00
     * delStatus : 0
     * id : 13
     * code : 13
     * content : 1年
     * parentId : 6
     * remark :
     */

    private String createTime;
    private String updateTime;
    private int delStatus;
    private String id;
    private String code;
    private String content;
    private int parentId;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
