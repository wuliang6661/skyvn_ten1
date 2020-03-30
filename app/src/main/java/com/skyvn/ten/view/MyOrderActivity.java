package com.skyvn.ten.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.skyvn.ten.R;
import com.skyvn.ten.api.HttpResultSubscriber;
import com.skyvn.ten.api.HttpServerImpl;
import com.skyvn.ten.base.BaseActivity;
import com.skyvn.ten.bean.OrderDetailsBO;
import com.skyvn.ten.view.pay_back_style2.PayBackActivity2;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的借款页面
 */
public class MyOrderActivity extends BaseActivity {


    @BindView(R.id.pay_num)
    TextView payNum;
    @BindView(R.id.yinghuankuan_riqi)
    TextView yinghuankuanRiqi;
    @BindView(R.id.yuqi_text)
    TextView yuqiText;
    @BindView(R.id.order_layout)
    RelativeLayout orderLayout;
    @BindView(R.id.none_layout)
    LinearLayout noneLayout;

    private OrderDetailsBO orderDetailsBO;

    @Override
    protected int getLayout() {
        return R.layout.act_my_order;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        goBack();
        setTitleText(getString(R.string.wodejiekuan));
        rightButton();

        getMyOrder();
    }


    private void getMyOrder() {
        HttpServerImpl.getMyLastLoan().subscribe(new HttpResultSubscriber<OrderDetailsBO>() {
            @Override
            public void onSuccess(OrderDetailsBO orderDetailsBO) {
                MyOrderActivity.this.orderDetailsBO = orderDetailsBO;
                if (orderDetailsBO == null) {
                    orderLayout.setVisibility(View.GONE);
                    noneLayout.setVisibility(View.VISIBLE);
                    return;
                }
                orderLayout.setVisibility(View.VISIBLE);
                noneLayout.setVisibility(View.GONE);
                payNum.setText(orderDetailsBO.getDelayRepaymentAmount() + getResources().getString(R.string.danwei_yuan));
                if (StringUtils.isEmpty(orderDetailsBO.getOverdue()) || "0".equals(orderDetailsBO.getOverdue())) {
                    yuqiText.setVisibility(View.GONE);
                    yinghuankuanRiqi.setGravity(Gravity.CENTER);
                    yuqiText.setText(getString(R.string.zuihouqixian) + orderDetailsBO.getOverdueDays() + getString(R.string.danwei_yuan));
                } else {
                    yuqiText.setVisibility(View.VISIBLE);
                    yinghuankuanRiqi.setText(getResources().getString(R.string.yinghuankuan_riqi) + (orderDetailsBO.getRepaymentDate().split(" ")[0]));
                    yuqiText.setText(getString(R.string.yiyuqi_yinhao) + orderDetailsBO.getOverdueDays() + getString(R.string.danwei_yuan));
                }
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }


    @OnClick(R.id.order_layout)
    public void clickOrder() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("order", orderDetailsBO);
        gotoActivity(PayBackActivity2.class, bundle, false);
    }
}
