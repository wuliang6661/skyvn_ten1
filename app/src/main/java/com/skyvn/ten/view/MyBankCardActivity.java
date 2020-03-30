package com.skyvn.ten.view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.skyvn.ten.R;
import com.skyvn.ten.api.HttpResultSubscriber;
import com.skyvn.ten.api.HttpServerImpl;
import com.skyvn.ten.base.BaseActivity;
import com.skyvn.ten.bean.BankCardBO;
import com.skyvn.ten.view.bindbankcard.BindBankCardActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的银行卡页面
 */
public class MyBankCardActivity extends BaseActivity {

    @BindView(R.id.yinghang_img)
    RoundedImageView yinghangImg;
    @BindView(R.id.yinghang_name)
    TextView yinghangName;
    @BindView(R.id.yinghang_num)
    TextView yinghangNum;
    @BindView(R.id.card_layout)
    RelativeLayout cardLayout;
    @BindView(R.id.no_card_layout)
    LinearLayout noCardLayout;
    @BindView(R.id.bt_img)
    ImageView btImg;
    @BindView(R.id.bt_name)
    TextView btName;
    @BindView(R.id.btn_layout)
    LinearLayout btnLayout;


    @Override
    protected int getLayout() {
        return R.layout.act_bank_card;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText(getResources().getString(R.string.yinghangka));
        rightButton();

    }


    @Override
    protected void onResume() {
        super.onResume();
        getBankCard();
    }

    /**
     * 获取我的银行卡
     */
    private void getBankCard() {
        HttpServerImpl.getBankCard().subscribe(new HttpResultSubscriber<BankCardBO>() {
            @Override
            public void onSuccess(BankCardBO s) {
                if (s == null) {
                    cardLayout.setVisibility(View.GONE);
                    noCardLayout.setVisibility(View.VISIBLE);
                    btImg.setImageResource(R.drawable.add_card);
                    btName.setText(getResources().getString(R.string.add_card));
                } else {
                    cardLayout.setVisibility(View.VISIBLE);
                    noCardLayout.setVisibility(View.GONE);
                    btImg.setImageResource(R.drawable.switch_card);
                    btName.setText(getResources().getString(R.string.switch_card));
                    Glide.with(MyBankCardActivity.this).load(s.getImageOssUrl()).
                            error(R.drawable.user_img_defalt)
                            .placeholder(R.drawable.user_img_defalt).into(yinghangImg);
                    yinghangName.setText(s.getBank());
                    yinghangNum.setText(s.getCardNo());
                }
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }

    @OnClick(R.id.btn_layout)
    public void clickBankCard() {
        gotoActivity(BindBankCardActivity.class, false);
    }
}
