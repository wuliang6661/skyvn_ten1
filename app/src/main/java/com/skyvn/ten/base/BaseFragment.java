package com.skyvn.ten.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.gyf.barlibrary.ImmersionBar;
import com.skyvn.ten.R;
import com.skyvn.ten.util.ToastManager;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by wuliang on 2017/5/11.
 * <p>
 * 所有Fragment的父类
 */

public abstract class BaseFragment extends SupportFragment {

    private SVProgressHUD svProgressHUD;


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        svProgressHUD = new SVProgressHUD(getActivity());
    }


    /**
     * 常用的跳转方法
     */
    public void gotoActivity(Class<?> cls, boolean isFinish) {
        Intent intent = new Intent(getActivity(), cls);
        startActivity(intent);
        if (isFinish) {
            getActivity().finish();
        }
    }

    public void gotoActivity(Class<?> cls, Bundle bundle, boolean isFinish) {
        Intent intent = new Intent(getActivity(), cls);
        intent.putExtras(bundle);
        startActivity(intent);
        if (isFinish) {
            getActivity().finish();
        }
    }


    /**
     * 显示加载进度弹窗
     */
    protected void showProgress() {
        svProgressHUD.showWithStatus(getString(R.string.jiazaizhong), SVProgressHUD.SVProgressHUDMaskType.BlackCancel);
    }

    /**
     * 停止弹窗
     */
    protected void stopProgress() {
        if (svProgressHUD != null && svProgressHUD.isShowing()) {
            svProgressHUD.dismiss();
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        stopProgress();
    }

    /**
     * 初始化下拉刷新控件
     */
    protected void invitionSwipeRefresh(SwipeRefreshLayout mSwipeLayout) {
        // 设置下拉圆圈上的颜色，蓝色、绿色、橙色、红色
        mSwipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        mSwipeLayout.setDistanceToTriggerSync(400);// 设置手指在屏幕下拉多少距离会触发下拉刷新
        mSwipeLayout.setProgressBackgroundColor(R.color.white); // 设定下拉圆圈的背景
        mSwipeLayout.setSize(SwipeRefreshLayout.DEFAULT); // 设置圆圈的大小
    }


    protected void showToast(String message) {
//        ToastUtils.showShort(message);
//        Toast toast = Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT);
//        toast.setText(message);
//        toast.show();
        ToastManager.showShort(message);
    }

    public void onRequestEnd() {

    }

    /**
     * 设置fragment沉浸式
     */
    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        //请在onSupportVisible实现沉浸式
        if (isImmersionBarEnabled()) {
            initImmersionBar();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }

    public void initImmersionBar() {
        ImmersionBar.with(this).statusBarColor(R.color.white)
                .statusBarDarkFont(true).keyboardEnable(true).init();   //解决虚拟按键与状态栏沉浸冲突
    }

    private boolean isImmersionBarEnabled() {
        return true;
    }


}
