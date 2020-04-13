package com.skyvn.ten.view;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.skyvn.ten.R;
import com.skyvn.ten.api.HttpResultSubscriber;
import com.skyvn.ten.api.HttpServerImpl;
import com.skyvn.ten.base.BaseActivity;
import com.skyvn.ten.base.MyApplication;
import com.skyvn.ten.bean.GoHomeEvent;
import com.skyvn.ten.config.IConstant;
import com.skyvn.ten.util.AppManager;
import com.skyvn.ten.util.language.LanguageType;
import com.skyvn.ten.util.language.LanguageUtil;
import com.skyvn.ten.view.main.MainActivity;
import com.skyvn.ten.widget.AlertDialog;
import com.skyvn.ten.widget.PopXingZhi;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 设置界面
 */
public class SettingActivity extends BaseActivity {


    @BindView(R.id.user_img)
    RoundedImageView userImg;
    @BindView(R.id.user_phone)
    TextView userPhone;
    @BindView(R.id.lanunger_text)
    TextView lanungerText;
    @BindView(R.id.bt_logout)
    Button btLogout;

    private String checkPosition = null;


    @Override
    protected int getLayout() {
        return R.layout.act_setting;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText(getResources().getString(R.string.setting));

        if (MyApplication.userBO == null) {
            userPhone.setText(R.string.weidenglu);
            userImg.setImageResource(R.drawable.user_img_defalt);
        } else {
            userPhone.setText(MyApplication.userBO.getPhone());
            Glide.with(this).load(MyApplication.userBO.getHeadPortrait())
                    .error(R.drawable.user_img_defalt).
                    placeholder(R.drawable.user_img_defalt).into(userImg);
        }

        checkPosition = MyApplication.spUtils.getString(IConstant.LANGUAGE_TYPE, "");
        int type = getIntent().getIntExtra("type", 0);
        if (type != 0) {
            btLogout.setVisibility(View.GONE);
        }
    }


    @OnClick(R.id.switch_lanunger)
    public void switchLanunger() {
        List<String> list = new ArrayList<>();
        list.add(getResources().getString(R.string.china));
        list.add(getResources().getString(R.string.yuelanwen));
        PopXingZhi popXingZhi = new PopXingZhi(this, getResources().getString(R.string.switch_langnger), list);
        popXingZhi.setListener((position, item) -> {
            lanungerText.setText(item);
            if (position == 0) {
                checkPosition = LanguageType.CHINESE.getLanguage();
            } else {
                checkPosition = LanguageType.THAILAND.getLanguage();
            }
            switchLanguage();
        });
        if (checkPosition.equals(LanguageType.CHINESE.getLanguage())) {
            popXingZhi.setSelectPosition(0);
        } else {
            popXingZhi.setSelectPosition(1);
        }
        popXingZhi.showAtLocation(getWindow().getDecorView());
    }


    /**
     * 设置语言
     */
    private void switchLanguage() {
        String local = null;
        if (checkPosition.equals(LanguageType.CHINESE.getLanguage())) {
//            ChangeLanguageUtils.updateLocale(this, Locale.CHINESE);
            local = LanguageType.CHINESE.getLanguage();
        } else if (checkPosition.equals(LanguageType.ENGLISH.getLanguage())) {
//            ChangeLanguageUtils.updateLocale(this, Locale.ENGLISH);
            local = LanguageType.ENGLISH.getLanguage();
        } else if (checkPosition.equals(LanguageType.THAILAND.getLanguage())) {
            //ChangeLanguageUtils.updateLocale(this, Locale.ENGLISH);
            local = LanguageType.THAILAND.getLanguage();
//            ChangeLanguageUtils.updateLocale(this, new Locale("es", "ES"));
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            LanguageUtil.changeAppLanguage(Utils.getApp(), local);
        }
        MyApplication.spUtils.put(IConstant.LANGUAGE_TYPE, local);
        //清空任务栈确保当前打开activity为前台任务栈栈顶
        Intent it = new Intent(SettingActivity.this, MainActivity.class);
        it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(it);
        finish();
    }


    @OnClick(R.id.bt_logout)
    public void logout() {
        new AlertDialog(this).builder().setGone().setTitle(getResources().getString(R.string.tishi))
                .setMsg(getResources().getString(R.string.is_logout))
                .setNegativeButton(getResources().getString(R.string.cancle), null)
                .setPositiveButton(getResources().getString(R.string.commit), v -> {
                    exitLogin();
                }).show();
    }

    /**
     * 退出登录
     */
    private void exitLogin() {
        showProgress();
        HttpServerImpl.exitLogin().subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                stopProgress();
                MyApplication.spUtils.remove("token");
                MyApplication.token = null;
                EventBus.getDefault().post(new GoHomeEvent());
                gotoActivity(LoginActivity.class, true);
            }

            @Override
            public void onFiled(String message) {
                stopProgress();
                showToast(message);
            }
        });
    }
}
