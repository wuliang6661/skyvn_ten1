package com.skyvn.ten.view.main.order;

import com.skyvn.ten.bean.OrderBO;
import com.skyvn.ten.mvp.BasePresenter;
import com.skyvn.ten.mvp.BaseRequestView;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class OrderContract {
    interface View extends BaseRequestView {

        void getOrder(OrderBO orderBO);

        void loanAgainSouress();

    }

    interface Presenter extends BasePresenter<View> {

    }
}
