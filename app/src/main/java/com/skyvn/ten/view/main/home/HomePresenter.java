package com.skyvn.ten.view.main.home;

import com.skyvn.ten.api.HttpResultSubscriber;
import com.skyvn.ten.api.HttpServerImpl;
import com.skyvn.ten.bean.BannerBO;
import com.skyvn.ten.bean.GongGaoBO;
import com.skyvn.ten.bean.StatusBO;
import com.skyvn.ten.mvp.BasePresenterImpl;

import java.util.List;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class HomePresenter extends BasePresenterImpl<HomeContract.View> implements HomeContract.Presenter {


    /**
     * 获取首页Banner
     */
    public void getHomeBanner() {
        HttpServerImpl.getHomeBanner().subscribe(new HttpResultSubscriber<BannerBO>() {
            @Override
            public void onSuccess(BannerBO s) {
                if (mView != null) {
                    mView.getBanner(s);
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
     * 获取首页轮播图
     */
    public void getHomeCarses() {
        HttpServerImpl.getHomeImgList().subscribe(new HttpResultSubscriber<List<BannerBO>>() {
            @Override
            public void onSuccess(List<BannerBO> s) {
                if (mView != null) {
                    mView.getLunBoImgs(s);
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
     * 获取公告列表
     */
    public void getNoticeList() {
        HttpServerImpl.getNoticeList().subscribe(new HttpResultSubscriber<List<GongGaoBO>>() {
            @Override
            public void onSuccess(List<GongGaoBO> s) {
                if (mView != null) {
                    mView.getGongGao(s);
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
     * 获取期限和金额列表
     */
    public void getPayNumAndDays(String code, int type) {
        HttpServerImpl.getPayNumOrDays(code).subscribe(new HttpResultSubscriber<List<String>>() {
            @Override
            public void onSuccess(List<String> s) {
                if (mView != null) {
                    mView.getPayNumOrDays(s, type);
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
     * 获取当天提交的申请
     */
    public void getMyApply() {
        HttpServerImpl.getMyApplys().subscribe(new HttpResultSubscriber<StatusBO>() {
            @Override
            public void onSuccess(StatusBO s) {
                if (mView != null) {
                    mView.getStatus(s);
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
     * 提交客户申请
     */
    public void addMyApply(String days, String endAmount, String startAmount) {
        HttpServerImpl.addApplys(days, endAmount, startAmount).subscribe(new HttpResultSubscriber<Object>() {
            @Override
            public void onSuccess(Object s) {
                if (mView != null) {
                    getMyApply();
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
