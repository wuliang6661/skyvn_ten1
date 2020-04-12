package com.skyvn.ten.view.main.mine;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gyf.barlibrary.ImmersionBar;
import com.makeramen.roundedimageview.RoundedImageView;
import com.skyvn.ten.R;
import com.skyvn.ten.api.HttpResultSubscriber;
import com.skyvn.ten.api.HttpServerImpl;
import com.skyvn.ten.base.MyApplication;
import com.skyvn.ten.bean.AttentionSourrssBO;
import com.skyvn.ten.mvp.MVPBaseFragment;
import com.skyvn.ten.util.AuthenticationUtils;
import com.skyvn.ten.view.FanKuiActivity;
import com.skyvn.ten.view.KefuActivity;
import com.skyvn.ten.view.MyBankCardActivity;
import com.skyvn.ten.view.MyOrderActivity;
import com.skyvn.ten.view.SettingActivity;
import com.skyvn.ten.view.attentionziliao.AttentionZiliaoActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class MineFragment extends MVPBaseFragment<MineContract.View, MinePresenter>
        implements MineContract.View {


    @BindView(R.id.user_img)
    RoundedImageView userImg;
    @BindView(R.id.go_attention)
    TextView goAttention;
    @BindView(R.id.user_phone)
    TextView userPhone;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fra_mine, null);
        unbinder = ButterKnife.bind(this, view);
        return view;

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }


    @OnClick({R.id.layout_yinghangka, R.id.layout_ziliao, R.id.layout_kefu, R.id.layout_fankui, R.id.layout_order})
    public void layoutClick(View view) {
        switch (view.getId()) {
            case R.id.layout_yinghangka:   //银行卡
                gotoActivity(MyBankCardActivity.class, false);
                break;
            case R.id.layout_ziliao:    //资料补充
                gotoActivity(AttentionZiliaoActivity.class, false);
                break;
            case R.id.layout_kefu:    //客服
                gotoActivity(KefuActivity.class, false);
                break;
            case R.id.layout_fankui:    //反馈
                gotoActivity(FanKuiActivity.class, false);
                break;
            case R.id.layout_order:
                gotoActivity(MyOrderActivity.class, false);
                break;
        }
    }


    @OnClick(R.id.go_attention)
    public void clickGoAttention() {
        showProgress();
        HttpServerImpl.getFirstAuth().subscribe(new HttpResultSubscriber<AttentionSourrssBO>() {
            @Override
            public void onSuccess(AttentionSourrssBO s) {
                stopProgress();
                if (!"-1".equals(s.getCode())) {
                    gotoActivity(AttentionZiliaoActivity.class, false);
                }
                AuthenticationUtils.goAuthNextPageByHome(s.getCode(), s.getNeedStatus(), false, getActivity());
            }

            @Override
            public void onFiled(String message) {
                stopProgress();
                showToast(message);
            }
        });
    }


    private void initAttention() {
        HttpServerImpl.getFirstAuth().subscribe(new HttpResultSubscriber<AttentionSourrssBO>() {
            @Override
            public void onSuccess(AttentionSourrssBO s) {
                if ("-1".equals(s.getCode())) {
                    goAttention.setText(R.string.yirenzheng);
                } else {
                    goAttention.setText(R.string.qurenzheng);
                }
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }


    @OnClick(R.id.btn_album)
    public void settingClick() {
        gotoActivity(SettingActivity.class, false);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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
        initAttention();
        if (MyApplication.userBO == null) {
            userPhone.setText(R.string.weidenglu);
            userImg.setImageResource(R.drawable.user_img_defalt);
        } else {
            userPhone.setText(MyApplication.userBO.getPhone());
            Glide.with(getActivity()).load(MyApplication.userBO.getHeadPortrait())
                    .error(R.drawable.person_defalt).
                    placeholder(R.drawable.person_defalt).into(userImg);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }

    public void initImmersionBar() {
        ImmersionBar.with(this).statusBarColor(R.color.blue_color)
                .statusBarDarkFont(true).keyboardEnable(true).init();   //解决虚拟按键与状态栏沉浸冲突
    }

    private boolean isImmersionBarEnabled() {
        return true;
    }
}
