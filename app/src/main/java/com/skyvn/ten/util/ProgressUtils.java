package com.skyvn.ten.util;

import android.app.Activity;

import com.bigkoo.svprogresshud.SVProgressHUD;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2020/4/812:18
 * desc   : 进度条工具类
 * version: 1.0
 */
public class ProgressUtils {

    private SVProgressHUD svProgressHUD;

    public ProgressUtils(Activity activity) {
        svProgressHUD = new SVProgressHUD(activity);
    }


    /**
     * 显示加载进度弹窗
     */
    protected void showProgress() {
        svProgressHUD.showWithStatus("加载中...", SVProgressHUD.SVProgressHUDMaskType.Black);
    }

    /**
     * 停止弹窗
     */
    protected void stopProgress() {
        if (svProgressHUD.isShowing()) {
            svProgressHUD.dismiss();
        }
    }


}
