package com.skyvn.ten.base;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.support.multidex.MultiDex;
import android.text.TextUtils;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.Utils;
import com.skyvn.ten.bean.LoginSuressBO;
import com.skyvn.ten.config.IConstant;
import com.skyvn.ten.util.language.LanguageUtil;

import cat.ereza.customactivityoncrash.CustomActivityOnCrash;

/**
 * 作者 by wuliang 时间 16/10/26.
 * <p>
 * 程序的application
 */

public class MyApplication extends Application {

    private static final String TAG = "hw_android";
    public static SPUtils spUtils;
    public static String token;
    public static boolean AppInBack = false;  //App 是否在后台

    public static LoginSuressBO userBO;

    /**
     * 活体检测的key
     */
    public static String LIVE_KEY = "";

    /**
     * 活体检测的
     */
    public static String Secret_Key = "";

    @Override
    public void onCreate() {
        super.onCreate();
        CustomActivityOnCrash.install(this);
        /***初始化工具类*/
        Utils.init(this);
        spUtils = SPUtils.getInstance(TAG);
        registerActivityLifecycleCallbacks(new AppLifecycleHandler());
        if (!TextUtils.isEmpty(spUtils.getString(IConstant.LANGUAGE_TYPE, ""))) {
//            spUtils.put(IConstant.LANGUAGE_TYPE, LanguageType.CHINESE.getLanguage());
            setLanguage();
        }
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }


    /**
     * 设置语言
     */
    private void setLanguage() {
        String language = spUtils.getString(IConstant.LANGUAGE_TYPE, "");
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            LanguageUtil.changeAppLanguage(getApplicationContext(), language);
        }
    }
}
