package com.skyvn.ten.bean;

/**
 * 首页的加载类
 */
public class IndexBO {


    /**
     * authStatus : 0
     * days : 0
     * loanStatus : 0
     * orderLoanRepaySerialVO : {"auditDate":"","callbackResult":"","certificateOssUrl":"","clientId":0,"createTime":"","delStatus":0,"id":0,"loanId":0,"overdue":0,"remark":"","repaymentAmount":0,"rolloverDays":0,"status":0,"submitId":0,"submitResource":0,"submitTime":"","tenantGatheringAccountId":0,"tenantId":0,"tenantReviewId":0,"type":0,"updateTime":""}
     * orderLoanVO : {"actualPayAmount":0,"actualRepaymentAmount":0,"clientApplyOrderId":0,"clientId":0,"clientName":"","clientPhone":"","createTime":"","dayOverdueRate":0,"deductionAmount":0,"delStatus":0,"delayRepaymentAmount":0,"id":0,"interestAmount":0,"lastRepaymentDate":"","loanDate":"","loanTimes":0,"overdue":0,"overdueAmount":0,"overdueDays":0,"payAmount":0,"productId":0,"remark":"","repaymentAmount":0,"repaymentDate":"","replaceId":0,"serviceAmount":0,"status":0,"tenantId":0,"updateTime":""}
     * quota : 0
     */

    private String authStatus;
    private String days;
    private String loanStatus;
    private OrderLoanRepaySerialVOBean orderLoanRepaySerialVO;
    private OrderDetailsBO orderLoanVO;
    private String quota;

    public String getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(String authStatus) {
        this.authStatus = authStatus;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getLoanStatus() {
        return loanStatus;
    }

    public void setLoanStatus(String loanStatus) {
        this.loanStatus = loanStatus;
    }

    public OrderLoanRepaySerialVOBean getOrderLoanRepaySerialVO() {
        return orderLoanRepaySerialVO;
    }

    public void setOrderLoanRepaySerialVO(OrderLoanRepaySerialVOBean orderLoanRepaySerialVO) {
        this.orderLoanRepaySerialVO = orderLoanRepaySerialVO;
    }

    public OrderDetailsBO getOrderLoanVO() {
        return orderLoanVO;
    }

    public void setOrderLoanVO(OrderDetailsBO orderLoanVO) {
        this.orderLoanVO = orderLoanVO;
    }

    public String getQuota() {
        return quota;
    }

    public void setQuota(String quota) {
        this.quota = quota;
    }

    public static class OrderLoanRepaySerialVOBean {
        /**
         * auditDate :
         * callbackResult :
         * certificateOssUrl :
         * clientId : 0
         * createTime :
         * delStatus : 0
         * id : 0
         * loanId : 0
         * overdue : 0
         * remark :
         * repaymentAmount : 0
         * rolloverDays : 0
         * status : 0
         * submitId : 0
         * submitResource : 0
         * submitTime :
         * tenantGatheringAccountId : 0
         * tenantId : 0
         * tenantReviewId : 0
         * type : 0
         * updateTime :
         */

        private String auditDate;
        private String callbackResult;
        private String certificateOssUrl;
        private String clientId;
        private String createTime;
        private String delStatus;
        private String id;
        private String loanId;
        private String overdue;
        private String remark;
        private String repaymentAmount;
        private String rolloverDays;
        private String status;
        private String submitId;
        private String submitResource;
        private String submitTime;
        private String tenantGatheringAccountId;
        private String tenantId;
        private String tenantReviewId;
        private String type;
        private String updateTime;
        private String repayAmount;

        public String getRepayAmount() {
            return repayAmount;
        }

        public void setRepayAmount(String repayAmount) {
            this.repayAmount = repayAmount;
        }

        public String getAuditDate() {
            return auditDate;
        }

        public void setAuditDate(String auditDate) {
            this.auditDate = auditDate;
        }

        public String getCallbackResult() {
            return callbackResult;
        }

        public void setCallbackResult(String callbackResult) {
            this.callbackResult = callbackResult;
        }

        public String getCertificateOssUrl() {
            return certificateOssUrl;
        }

        public void setCertificateOssUrl(String certificateOssUrl) {
            this.certificateOssUrl = certificateOssUrl;
        }

        public String getClientId() {
            return clientId;
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
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

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLoanId() {
            return loanId;
        }

        public void setLoanId(String loanId) {
            this.loanId = loanId;
        }

        public String getOverdue() {
            return overdue;
        }

        public void setOverdue(String overdue) {
            this.overdue = overdue;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getRepaymentAmount() {
            return repaymentAmount;
        }

        public void setRepaymentAmount(String repaymentAmount) {
            this.repaymentAmount = repaymentAmount;
        }

        public String getRolloverDays() {
            return rolloverDays;
        }

        public void setRolloverDays(String rolloverDays) {
            this.rolloverDays = rolloverDays;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getSubmitId() {
            return submitId;
        }

        public void setSubmitId(String submitId) {
            this.submitId = submitId;
        }

        public String getSubmitResource() {
            return submitResource;
        }

        public void setSubmitResource(String submitResource) {
            this.submitResource = submitResource;
        }

        public String getSubmitTime() {
            return submitTime;
        }

        public void setSubmitTime(String submitTime) {
            this.submitTime = submitTime;
        }

        public String getTenantGatheringAccountId() {
            return tenantGatheringAccountId;
        }

        public void setTenantGatheringAccountId(String tenantGatheringAccountId) {
            this.tenantGatheringAccountId = tenantGatheringAccountId;
        }

        public String getTenantId() {
            return tenantId;
        }

        public void setTenantId(String tenantId) {
            this.tenantId = tenantId;
        }

        public String getTenantReviewId() {
            return tenantReviewId;
        }

        public void setTenantReviewId(String tenantReviewId) {
            this.tenantReviewId = tenantReviewId;
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

    public static class OrderLoanVOBean {
        /**
         * actualPayAmount : 0
         * actualRepaymentAmount : 0
         * clientApplyOrderId : 0
         * clientId : 0
         * clientName :
         * clientPhone :
         * createTime :
         * dayOverdueRate : 0
         * deductionAmount : 0
         * delStatus : 0
         * delayRepaymentAmount : 0
         * id : 0
         * interestAmount : 0
         * lastRepaymentDate :
         * loanDate :
         * loanTimes : 0
         * overdue : 0
         * overdueAmount : 0
         * overdueDays : 0
         * payAmount : 0
         * productId : 0
         * remark :
         * repaymentAmount : 0
         * repaymentDate :
         * replaceId : 0
         * serviceAmount : 0
         * status : 0
         * tenantId : 0
         * updateTime :
         */

        private String actualPayAmount;
        private String actualRepaymentAmount;
        private String clientApplyOrderId;
        private String clientId;
        private String clientName;
        private String clientPhone;
        private String createTime;
        private String dayOverdueRate;
        private String deductionAmount;
        private String delStatus;
        private String delayRepaymentAmount;
        private String id;
        private String interestAmount;
        private String lastRepaymentDate;
        private String loanDate;
        private String loanTimes;
        private String overdue;
        private String overdueAmount;
        private String overdueDays;
        private String payAmount;
        private String productId;
        private String remark;
        private String repaymentAmount;
        private String repaymentDate;
        private String replaceId;
        private String serviceAmount;
        private String status;
        private String tenantId;
        private String updateTime;

        public String getActualPayAmount() {
            return actualPayAmount;
        }

        public void setActualPayAmount(String actualPayAmount) {
            this.actualPayAmount = actualPayAmount;
        }

        public String getActualRepaymentAmount() {
            return actualRepaymentAmount;
        }

        public void setActualRepaymentAmount(String actualRepaymentAmount) {
            this.actualRepaymentAmount = actualRepaymentAmount;
        }

        public String getClientApplyOrderId() {
            return clientApplyOrderId;
        }

        public void setClientApplyOrderId(String clientApplyOrderId) {
            this.clientApplyOrderId = clientApplyOrderId;
        }

        public String getClientId() {
            return clientId;
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

        public String getClientName() {
            return clientName;
        }

        public void setClientName(String clientName) {
            this.clientName = clientName;
        }

        public String getClientPhone() {
            return clientPhone;
        }

        public void setClientPhone(String clientPhone) {
            this.clientPhone = clientPhone;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getDayOverdueRate() {
            return dayOverdueRate;
        }

        public void setDayOverdueRate(String dayOverdueRate) {
            this.dayOverdueRate = dayOverdueRate;
        }

        public String getDeductionAmount() {
            return deductionAmount;
        }

        public void setDeductionAmount(String deductionAmount) {
            this.deductionAmount = deductionAmount;
        }

        public String getDelStatus() {
            return delStatus;
        }

        public void setDelStatus(String delStatus) {
            this.delStatus = delStatus;
        }

        public String getDelayRepaymentAmount() {
            return delayRepaymentAmount;
        }

        public void setDelayRepaymentAmount(String delayRepaymentAmount) {
            this.delayRepaymentAmount = delayRepaymentAmount;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getInterestAmount() {
            return interestAmount;
        }

        public void setInterestAmount(String interestAmount) {
            this.interestAmount = interestAmount;
        }

        public String getLastRepaymentDate() {
            return lastRepaymentDate;
        }

        public void setLastRepaymentDate(String lastRepaymentDate) {
            this.lastRepaymentDate = lastRepaymentDate;
        }

        public String getLoanDate() {
            return loanDate;
        }

        public void setLoanDate(String loanDate) {
            this.loanDate = loanDate;
        }

        public String getLoanTimes() {
            return loanTimes;
        }

        public void setLoanTimes(String loanTimes) {
            this.loanTimes = loanTimes;
        }

        public String getOverdue() {
            return overdue;
        }

        public void setOverdue(String overdue) {
            this.overdue = overdue;
        }

        public String getOverdueAmount() {
            return overdueAmount;
        }

        public void setOverdueAmount(String overdueAmount) {
            this.overdueAmount = overdueAmount;
        }

        public String getOverdueDays() {
            return overdueDays;
        }

        public void setOverdueDays(String overdueDays) {
            this.overdueDays = overdueDays;
        }

        public String getPayAmount() {
            return payAmount;
        }

        public void setPayAmount(String payAmount) {
            this.payAmount = payAmount;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getRepaymentAmount() {
            return repaymentAmount;
        }

        public void setRepaymentAmount(String repaymentAmount) {
            this.repaymentAmount = repaymentAmount;
        }

        public String getRepaymentDate() {
            return repaymentDate;
        }

        public void setRepaymentDate(String repaymentDate) {
            this.repaymentDate = repaymentDate;
        }

        public String getReplaceId() {
            return replaceId;
        }

        public void setReplaceId(String replaceId) {
            this.replaceId = replaceId;
        }

        public String getServiceAmount() {
            return serviceAmount;
        }

        public void setServiceAmount(String serviceAmount) {
            this.serviceAmount = serviceAmount;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTenantId() {
            return tenantId;
        }

        public void setTenantId(String tenantId) {
            this.tenantId = tenantId;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }
    }
}
