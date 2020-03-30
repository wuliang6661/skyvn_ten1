package com.skyvn.ten.view.main.order;

import com.skyvn.ten.api.HttpResultSubscriber;
import com.skyvn.ten.api.HttpServerImpl;
import com.skyvn.ten.bean.OrderBO;
import com.skyvn.ten.mvp.BasePresenterImpl;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class OrderPresenter extends BasePresenterImpl<OrderContract.View> implements OrderContract.Presenter {


    /**
     * 查询待确认订单
     */
    public void getMyConfirmLoan() {
        HttpServerImpl.getMyConfirmLoan().subscribe(new HttpResultSubscriber<OrderBO>() {
            @Override
            public void onSuccess(OrderBO s) {
                if (mView != null) {
                    mView.getOrder(s);
                }
            }

            @Override
            public void onFiled(String message) {
                if (mView != null) {
                    mView.onRequestError(message);
                }
            }
        });
    }


    /**
     * 查询已结束订单
     */
    public void getMyEndLoan() {
        HttpServerImpl.getMyEndLoan().subscribe(new HttpResultSubscriber<OrderBO>() {
            @Override
            public void onSuccess(OrderBO s) {
                if (mView != null) {
                    mView.getOrder(s);
                }
            }

            @Override
            public void onFiled(String message) {
                if (mView != null) {
                    mView.onRequestError(message);
                }
            }
        });
    }

    /**
     * 查询待还款订单
     */
    public void getMyRepayLoan() {
        HttpServerImpl.getMyRepayLoan().subscribe(new HttpResultSubscriber<OrderBO>() {
            @Override
            public void onSuccess(OrderBO s) {
                if (mView != null) {
                    mView.getOrder(s);
                }
            }

            @Override
            public void onFiled(String message) {
                if (mView != null) {
                    mView.onRequestError(message);
                }
            }
        });
    }


    /**
     * 再借一次
     */
    public void loanAgain(String id) {
        HttpServerImpl.loanAgain(id).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                if (mView != null) {
                    mView.loanAgainSouress();
                }
            }

            @Override
            public void onFiled(String message) {
                if (mView != null) {
                    mView.onRequestError(message);
                }
            }
        });
    }

}
