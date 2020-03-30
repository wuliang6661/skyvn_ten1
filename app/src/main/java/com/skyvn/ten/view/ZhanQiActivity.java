package com.skyvn.ten.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.skyvn.ten.R;
import com.skyvn.ten.base.BaseActivity;
import com.skyvn.ten.bean.OrderDetailsBO;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 展期说明
 */
public class ZhanQiActivity extends BaseActivity {


    @BindView(R.id.user_img)
    RoundedImageView userImg;
    @BindView(R.id.user_name)
    TextView userName;

    @Override
    protected int getLayout() {
        return R.layout.act_zhanqi;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText(getResources().getString(R.string.zhanqi_title));
        rightButton();

        OrderDetailsBO detailsBO = (OrderDetailsBO) getIntent().getExtras().getSerializable("order");
        Glide.with(this).load(detailsBO.getLogoOssUrl())
                .error(R.drawable.user_img_defalt).placeholder(R.drawable.user_img_defalt).into(userImg);
        userName.setText(detailsBO.getSmsName());
    }


    @OnClick(R.id.kefu_layout)
    public void clickKefu() {
        gotoActivity(KefuActivity.class, false);
    }
}
