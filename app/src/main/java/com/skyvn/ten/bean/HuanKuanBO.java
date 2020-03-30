package com.skyvn.ten.bean;

import java.util.List;

public class HuanKuanBO {


    /**
     * num : 1
     * size : 500
     * total : 12
     * pageCount : 1
     * data : [{"repaymentAmount":"2.00","updateTime":"2020-02-12 19:03:24"},{"repaymentAmount":"3.21","updateTime":"2020-02-12 19:03:24"},{"repaymentAmount":"3.21","updateTime":"2020-02-12 19:03:24"},{"repaymentAmount":"3.21","updateTime":"2020-02-12 19:03:24"},{"repaymentAmount":"3.21","updateTime":"2020-02-12 19:03:24"},{"repaymentAmount":"3.21","updateTime":"2020-02-12 19:03:24"},{"repaymentAmount":"3.21","updateTime":"2020-02-12 19:03:24"},{"repaymentAmount":"3.21","updateTime":"2020-02-12 19:03:24"},{"repaymentAmount":"3.21","updateTime":"2020-02-12 19:03:24"},{"repaymentAmount":"3.21","updateTime":"2020-02-12 19:03:24"},{"repaymentAmount":"3.21","updateTime":"2020-02-12 19:03:24"},{"repaymentAmount":"3.21","updateTime":"2020-02-12 19:03:24"}]
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
         * repaymentAmount : 2.00
         * updateTime : 2020-02-12 19:03:24
         */

        private String repaymentAmount;
        private String updateTime;

        public String getRepaymentAmount() {
            return repaymentAmount;
        }

        public void setRepaymentAmount(String repaymentAmount) {
            this.repaymentAmount = repaymentAmount;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }
    }
}
