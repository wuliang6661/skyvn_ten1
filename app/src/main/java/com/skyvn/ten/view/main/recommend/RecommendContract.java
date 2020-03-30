package com.skyvn.ten.view.main.recommend;

import android.content.Context;

import com.skyvn.ten.bean.BannerBO;
import com.skyvn.ten.mvp.BasePresenter;
import com.skyvn.ten.mvp.BaseRequestView;
import com.skyvn.ten.mvp.BaseView;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class RecommendContract {
    interface View extends BaseRequestView {

        void getBanner(BannerBO bannerBO);
    }

    interface Presenter extends BasePresenter<View> {

    }
}
