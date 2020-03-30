package com.skyvn.ten.view.main.recommend;

import android.content.Context;

import com.skyvn.ten.api.HttpResultSubscriber;
import com.skyvn.ten.api.HttpServerImpl;
import com.skyvn.ten.bean.BannerBO;
import com.skyvn.ten.mvp.BasePresenterImpl;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class RecommendPresenter extends BasePresenterImpl<RecommendContract.View> implements RecommendContract.Presenter{

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

}
