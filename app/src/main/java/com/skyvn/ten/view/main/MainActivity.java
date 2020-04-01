package com.skyvn.ten.view.main;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;

import com.skyvn.ten.R;
import com.skyvn.ten.api.HttpResultSubscriber;
import com.skyvn.ten.api.HttpServerImpl;
import com.skyvn.ten.base.BaseActivity;
import com.skyvn.ten.base.MyApplication;
import com.skyvn.ten.bean.LiveKeyBO;
import com.skyvn.ten.bean.RecommendBO;
import com.skyvn.ten.util.AppManager;
import com.skyvn.ten.util.UpdateUtils;
import com.skyvn.ten.view.main.none.NoneFragment;
import com.skyvn.ten.view.main.none.NoneFragment1;
import com.skyvn.ten.view.main.none.NoneFragment2;
import com.skyvn.ten.view.main.none.NoneFragment3;
import com.xyz.tabitem.BottmTabItem;

import ai.advance.liveness.lib.GuardianLivenessDetectionSDK;
import ai.advance.liveness.lib.Market;
import butterknife.BindView;
import butterknife.OnClick;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * 主页
 */
public class MainActivity extends BaseActivity {


    @BindView(R.id.main1)
    BottmTabItem main1;
    @BindView(R.id.main2)
    BottmTabItem main2;
    @BindView(R.id.main3)
    BottmTabItem main3;


    private int selectPosition = 0;
    private BottmTabItem[] buttms;
    private SupportFragment[] mFragments = new SupportFragment[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        main2.setVisibility(View.GONE);
        buttms = new BottmTabItem[]{main1, main2, main3};
        showTuijian();
        getSaasKey();
        requestPermission();
    }


    /**
     * 获取活体检测的key
     */
    private void getSaasKey() {
        HttpServerImpl.getSaaSActiveKey().subscribe(new HttpResultSubscriber<LiveKeyBO>() {
            @Override
            public void onSuccess(LiveKeyBO s) {
                MyApplication.LIVE_KEY = s.getSdkKey();
                MyApplication.Secret_Key = s.getSecretKey();
                GuardianLivenessDetectionSDK.init(getApplication(), "7cec70f1679a5c3c", "728e65227bf25507",
                        Market.Vietnam);
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }


    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void onResume() {
        super.onResume();
        new UpdateUtils().checkUpdate(this, null);
    }

    @OnClick({R.id.main1, R.id.main2, R.id.main3})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main1:
                showHideFragment(mFragments[0], mFragments[selectPosition]);
                selectPosition = 0;
                setButtom(0);
                break;
            case R.id.main2:
                showHideFragment(mFragments[1], mFragments[selectPosition]);
                selectPosition = 1;
                setButtom(1);
                break;
            case R.id.main3:
                showHideFragment(mFragments[2], mFragments[selectPosition]);
                selectPosition = 2;
                setButtom(2);
                break;
        }
    }


    /**
     * 初始化fragment
     */
    private void initFragment(boolean tuijianVisiable) {
        SupportFragment firstFragment = findFragment(NoneFragment1.class);
        if (firstFragment == null) {
            mFragments[0] = NoneFragment1.newInstance();
            if (tuijianVisiable) {
                mFragments[1] = new NoneFragment2();
            } else {
                mFragments[1] = new NoneFragment();
            }
            mFragments[2] = new NoneFragment3();

            loadMultipleRootFragment(R.id.fragment_container, 0,
                    mFragments[0],
                    mFragments[1],
                    mFragments[2]);
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题
            // 这里我们需要拿到mFragments的引用
            mFragments[0] = firstFragment;
            if (tuijianVisiable) {
                mFragments[1] = findFragment(NoneFragment2.class);
            } else {
                mFragments[1] = findFragment(NoneFragment.class);
            }
            mFragments[2] = findFragment(NoneFragment3.class);
        }
    }

    /**
     * 设置推荐是否可见
     */
    private void showTuijian() {
        showProgress();
//        HttpServerImpl.getAppRecommend().subscribe(new HttpResultSubscriber<String>() {
//            @Override
//            public void onSuccess(String s) {
//                stopProgress();
//                if ("0".equals(s)) {
//                    main2.setVisibility(View.GONE);
//                    initFragment(false);
//                } else {
//                    main2.setVisibility(View.VISIBLE);
//                    initFragment(true);
//                }
//            }
//
//            @Override
//            public void onFiled(String message) {
//                stopProgress();
//                showToast(message);
//                main2.setVisibility(View.GONE);
//                initFragment(false);
//            }
//        });

        HttpServerImpl.getRemomImg().subscribe(new HttpResultSubscriber<RecommendBO>() {
            @Override
            public void onSuccess(RecommendBO s) {
                stopProgress();
                if (s == null) {
                    showToast(getString(R.string.wushuju));
                    return;
                }
                if ("0".equals(s.getRecommend())) {
                    main2.setVisibility(View.GONE);
                    initFragment(false);
                } else {
                    main2.setVisibility(View.VISIBLE);
                    initFragment(true);
                }
            }

            @Override
            public void onFiled(String message) {
                stopProgress();
                showToast(message);
                main2.setVisibility(View.GONE);
                initFragment(false);
            }
        });
    }


    /**
     * 设置底部按钮显示
     */
    private void setButtom(int position) {
        for (int i = 0; i < buttms.length; i++) {
            if (position == i) {
                buttms[i].setSelectState(true);
            } else {
                buttms[i].setSelectState(false);
            }
        }
    }


    //记录用户首次点击返回键的时间
    private long firstTime = 0;

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 2000) {
                    showToast("再按一次退出程序");
                    firstTime = secondTime;
                    return true;
                } else {
                    AppManager.getAppManager().finishAllActivity();
                    System.exit(0);
                }
                break;
        }
        return super.onKeyUp(keyCode, event);
    }


    private void requestPermission() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.READ_PHONE_STATE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION
                    }, 1);

        }
    }

}
