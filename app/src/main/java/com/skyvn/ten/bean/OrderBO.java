package com.skyvn.ten.bean;

import java.util.List;

public class OrderBO {


    /**
     * num : 1
     * size : 500
     * total : 1
     * pageCount : 1
     * data : [{"id":"4","clientId":"1217703242848575489","tenantId":"0","productId":"1","loanAmount":"10.00","days":10,"createTime":"2020-02-10 17:00:54","status":60,"smsName":null}]
     */

    private int num;
    private int size;
    private String total;
    private int pageCount;
    private List<DataBean> data;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 4
         * clientId : 1217703242848575489
         * tenantId : 0
         * productId : 1
         * loanAmount : 10.00
         * days : 10
         * createTime : 2020-02-10 17:00:54
         * status : 60
         * smsName : null
         */

        private String id;
        private String clientId;
        private String tenantId;
        private String productId;
        private String payAmount;
        private int days;
        private String createTime;
        private int status;
        private String smsName;
        private String logoOssUrl;
        /**
         * repaymentDate : null
         * overdueDays : 0
         * loanDate : null
         * delayRepaymentAmount : 0.00
         * actualRepaymentAmount : null
         * overdueAmount : 0.0000
         * interestAmount : 0.00
         * serviceOneName : 服务费1
         * serviceOnePrice : 0.00
         * serviceTwoName : 服务费2
         * serviceTwoPrice : 0.00
         * serviceThreeName : 服务费3
         * serviceThreePrice : 0.00
         * serviceAmount : 0.00
         */

        private String price;
        private String repaymentDate;
        private int overdueDays;
        private String loanDate;
        private String delayRepaymentAmount;
        private String actualRepaymentAmount;
        private String overdueAmount;
        private String interestAmount;
        private String serviceOneName;
        private String serviceOnePrice;
        private String serviceTwoName;
        private String serviceTwoPrice;
        private String serviceThreeName;
        private String serviceThreePrice;
        private String serviceAmount;

        public String getPayAmount() {
            return payAmount;
        }

        public void setPayAmount(String payAmount) {
            this.payAmount = payAmount;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
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



        public int getDays() {
            return days;
        }

        public void setDays(int days) {
            this.days = days;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
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

        public int getOverdueDays() {
            return overdueDays;
        }

        public void setOverdueDays(int overdueDays) {
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
    }
}
