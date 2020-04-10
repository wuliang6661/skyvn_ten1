package com.skyvn.ten.bean;

import java.util.List;

public class HuanKuanBO {


    /**
     * data : [{"auditTime":"","repayAmount":0}]
     * num : 0
     * pageCount : 0
     * size : 0
     * total : 0
     */

    private int num;
    private int pageCount;
    private int size;
    private int total;
    private List<DataBean> data;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * auditTime :
         * repayAmount : 0
         */

        private String auditTime;
        private int repayAmount;

        public String getAuditTime() {
            return auditTime;
        }

        public void setAuditTime(String auditTime) {
            this.auditTime = auditTime;
        }

        public int getRepayAmount() {
            return repayAmount;
        }

        public void setRepayAmount(int repayAmount) {
            this.repayAmount = repayAmount;
        }
    }
}
