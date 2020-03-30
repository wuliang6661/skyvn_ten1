package com.skyvn.ten.bean;

public class RecommendBO {


    /**
     * operateApplicationBannerVO : {"applicationId":0,"createTime":"","delStatus":0,"forwardUrl":"","id":0,"imageUrl":"","remark":"","type":0,"updateTime":""}
     * recommend : 0
     */

    private OperateApplicationBannerVOBean operateApplicationBannerVO;
    private String recommend;

    public OperateApplicationBannerVOBean getOperateApplicationBannerVO() {
        return operateApplicationBannerVO;
    }

    public void setOperateApplicationBannerVO(OperateApplicationBannerVOBean operateApplicationBannerVO) {
        this.operateApplicationBannerVO = operateApplicationBannerVO;
    }

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

    public static class OperateApplicationBannerVOBean {
        /**
         * applicationId : 0
         * createTime :
         * delStatus : 0
         * forwardUrl :
         * id : 0
         * imageUrl :
         * remark :
         * type : 0
         * updateTime :
         */

        private String applicationId;
        private String createTime;
        private String delStatus;
        private String forwardUrl;
        private String id;
        private String imageUrl;
        private String remark;
        private String type;
        private String updateTime;

        public String getApplicationId() {
            return applicationId;
        }

        public void setApplicationId(String applicationId) {
            this.applicationId = applicationId;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getDelStatus() {
            return delStatus;
        }

        public void setDelStatus(String delStatus) {
            this.delStatus = delStatus;
        }

        public String getForwardUrl() {
            return forwardUrl;
        }

        public void setForwardUrl(String forwardUrl) {
            this.forwardUrl = forwardUrl;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }
    }
}
