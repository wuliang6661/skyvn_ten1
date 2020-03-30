package com.skyvn.ten.view.main.home;

import com.skyvn.ten.bean.BannerBO;
import com.skyvn.ten.bean.GongGaoBO;
import com.skyvn.ten.bean.StatusBO;
import com.skyvn.ten.mvp.BasePresenter;
import com.skyvn.ten.mvp.BaseRequestView;

import java.util.List;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class HomeContract {
    interface View extends BaseRequestView {

        void getBanner(BannerBO bannerBO);

        void getGongGao(List<GongGaoBO> gongGaoBOS);

        void getLunBoImgs(List<BannerBO> bannerBOS);

        void getStatus(StatusBO statusBO);

        void getPayNumOrDays(List<String> list, int type);
    }

    interface Presenter extends BasePresenter<View> {

    }
}
