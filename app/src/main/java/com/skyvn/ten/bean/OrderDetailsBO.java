package com.skyvn.ten.bean;

import java.io.Serializable;

public class OrderDetailsBO implements Serializable {


    /**
     * id : 4
     * clientId : 1217703242848575489
     * tenantId : 0
     * productId : 1
     * price : 10.00
     * loanAmount : 10.00
     * days : 10
     * createTime : 2020-02-10 17:00:54
     * status : 60
     * smsName : 1
     * logoOssUrl : 123
     * repaymentDate : null
     * overdueDays : 0
     * loanDate : null
     * delayRepaymentAmount : 0.00
     * actualRepaymentAmount : null
     * overdueAmount : 0.0000
     * overdueSum : 0.0000
     * interestAmount : 0.00
     * serviceOneName : 服务费1
     * serviceOnePrice : 0.00
     * serviceTwoName : 服务费2
     * serviceTwoPrice : 0.00
     * serviceThreeName : 服务费3
     * serviceThreePrice : 0.00
     * serviceAmount : 0.00
     */

    private String id;
    private String clientId;
    private String tenantId;
    private String productId;
    private String price;
    private String payAmount;
    private String days;
    private String createTime;
    private String status;
    private String smsName;
    private String logoOssUrl;
    private String repaymentDate;
    private String overdueDays;
    private String loanDate;
    private String delayRepaymentAmount;
    private String actualRepaymentAmount;
    private String overdueAmount;
    private String overdueSum;
    private String interestAmount;
    private String serviceOneName;
    private String serviceOnePrice;
    private String serviceTwoName;
    private String serviceTwoPrice;
    private String serviceThreeName;
    private String serviceThreePrice;
    private String serviceAmount;
    private String overdue;

    private String actualPayAmount;
    private String clientApplyOrderId;
    private String clientName;
    private String clientPhone;
    private String dayOverdueRate;
    private String deductionAmount;
    private String lastRepaymentDate;
    private String loanTimes;
    private String remark;
    private String repaymentAmount;
    private String replaceId;
    private String updateTime;
    private String repayAmount;

    public String getRepayAmount() {
        return repayAmount;
    }

    public void setRepayAmount(String repayAmount) {
        this.repayAmount = repayAmount;
    }

    public String getOverdue() {
        return overdue;
    }

    public void setOverdue(String overdue) {
        this.overdue = overdue;
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

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(String payAmount) {
        this.payAmount = payAmount;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSmsName() {
        return smsName;
    }

    public void setSmsName(String smsName) {
        this.smsName = smsName;
    }

    public String getLogoOssUrl() {
        return logoOssUrl;
    }

    public void setLogoOssUrl(String logoOssUrl) {
        this.logoOssUrl = logoOssUrl;
    }

    public String getRepaymentDate() {
        return repaymentDate;
    }

    public void setRepaymentDate(String repaymentDate) {
        this.repaymentDate = repaymentDate;
    }

    public String getOverdueDays() {
        return overdueDays;
    }

    public void setOverdueDays(String overdueDays) {
        this.overdueDays = overdueDays;
    }

    public String getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(String loanDate) {
        this.loanDate = loanDate;
    }

    public String getDelayRepaymentAmount() {
        return delayRepaymentAmount;
    }

    public void setDelayRepaymentAmount(String delayRepaymentAmount) {
        this.delayRepaymentAmount = delayRepaymentAmount;
    }

    public String getActualRepaymentAmount() {
        return actualRepaymentAmount;
    }

    public void setActualRepaymentAmount(String actualRepaymentAmount) {
        this.actualRepaymentAmount = actualRepaymentAmount;
    }

    public String getOverdueAmount() {
        return overdueAmount;
    }

    public void setOverdueAmount(String overdueAmount) {
        this.overdueAmount = overdueAmount;
    }

    public String getOverdueSum() {
        return overdueSum;
    }

    public void setOverdueSum(String overdueSum) {
        this.overdueSum = overdueSum;
    }

    public String getInterestAmount() {
        return interestAmount;
    }

    public void setInterestAmount(String interestAmount) {
        this.interestAmount = interestAmount;
    }

    public String getServiceOneName() {
        return serviceOneName;
    }

    public void setServiceOneName(String serviceOneName) {
        this.serviceOneName = serviceOneName;
    }

    public String getServiceOnePrice() {
        return serviceOnePrice;
    }

    public void setServiceOnePrice(String serviceOnePrice) {
        this.serviceOnePrice = serviceOnePrice;
    }

    public String getServiceTwoName() {
        return serviceTwoName;
    }

    public void setServiceTwoName(String serviceTwoName) {
        this.serviceTwoName = serviceTwoName;
    }

    public String getServiceTwoPrice() {
        return serviceTwoPrice;
    }

    public void setServiceTwoPrice(String serviceTwoPrice) {
        this.serviceTwoPrice = serviceTwoPrice;
    }

    public String getServiceThreeName() {
        return serviceThreeName;
    }

    public void setServiceThreeName(String serviceThreeName) {
        this.serviceThreeName = serviceThreeName;
    }

    public String getServiceThreePrice() {
        return serviceThreePrice;
    }

    public void setServiceThreePrice(String serviceThreePrice) {
        this.serviceThreePrice = serviceThreePrice;
    }

    public String getServiceAmount() {
        return serviceAmount;
    }

    public void setServiceAmount(String serviceAmount) {
        this.serviceAmount = serviceAmount;
    }

    public String getActualPayAmount() {
        return actualPayAmount;
    }

    public void setActualPayAmount(String actualPayAmount) {
        this.actualPayAmount = actualPayAmount;
    }


    public String getClientApplyOrderId() {
        return clientApplyOrderId;
    }

    public void setClientApplyOrderId(String clientApplyOrderId) {
        this.clientApplyOrderId = clientApplyOrderId;
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


    public String getLastRepaymentDate() {
        return lastRepaymentDate;
    }

    public void setLastRepaymentDate(String lastRepaymentDate) {
        this.lastRepaymentDate = lastRepaymentDate;
    }

    public String getLoanTimes() {
        return loanTimes;
    }

    public void setLoanTimes(String loanTimes) {
        this.loanTimes = loanTimes;
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

    public String getReplaceId() {
        return replaceId;
    }

    public void setReplaceId(String replaceId) {
        this.replaceId = replaceId;
    }


    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
