package com.skyvn.ten.view.pay_back_style2;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.skyvn.ten.R;
import com.skyvn.ten.api.HttpResultSubscriber;
import com.skyvn.ten.api.HttpServerImpl;
import com.skyvn.ten.base.BaseActivity;
import com.skyvn.ten.bean.OrderDetailsBO;
import com.skyvn.ten.view.HuanKuanListActivty;
import com.skyvn.ten.view.ZhanQiActivity;
import com.skyvn.ten.view.pay_back_style1.PayBackActivity1;
import com.skyvn.ten.widget.MyDialog;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 还款详情风格2
 */
public class PayBackActivity2 extends BaseActivity {

    @BindView(R.id.bt_huankuan)
    Button btHuankuan;
    @BindView(R.id.bt_zhanqi)
    Button btZhanqi;
    @BindView(R.id.buttom_layout)
    LinearLayout buttomLayout;
    @BindView(R.id.user_img)
    RoundedImageView userImg;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.yuqi_day)
    TextView yuqiDay;
    @BindView(R.id.pay_num)
    TextView payNum;
    @BindView(R.id.pay_back_time)
    TextView payBackTime;
    @BindView(R.id.borrow_time)
    TextView borrowTime;
    @BindView(R.id.borrow_amount)
    TextView borrowAmount;
    @BindView(R.id.lixi_amount)
    TextView lixiAmount;
    @BindView(R.id.fuwu_amount)
    TextView fuwuAmount;
    @BindView(R.id.yuqi_amount)
    TextView yuqiAmount;
    @BindView(R.id.yuqi_amount_layout)
    LinearLayout yuqiAmountLayout;
    @BindView(R.id.yihuan_amount)
    TextView yihuanAmount;
    @BindView(R.id.zhaniq_hint)
    TextView zhaniqHint;

    private String orderId;
    private OrderDetailsBO orderDetailsBO;

    @Override
    protected int getLayout() {
        return R.layout.act_pay_back2;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText(getResources().getString(R.string.huankuanxiangqing));
        rightButton();

        orderDetailsBO = (OrderDetailsBO) getIntent().getExtras().getSerializable("order");
//        getOrderDetails();
        showData();
    }


    private void getOrderDetails() {
        showProgress();
        HttpServerImpl.getMyLoan(orderId).subscribe(new HttpResultSubscriber<OrderDetailsBO>() {
            @Override
            public void onSuccess(OrderDetailsBO s) {
                stopProgress();
                orderDetailsBO = s;
                showData();
            }

            @Override
            public void onFiled(String message) {
                stopProgress();
                showToast(message);
            }
        });
    }

    /**
     * 设置界面显示
     */
    @SuppressLint("SetTextI18n")
    private void showData() {
        Glide.with(this).load(orderDetailsBO.getLogoOssUrl())
                .error(R.drawable.user_img_defalt).placeholder(R.drawable.user_img_defalt).into(userImg);
        userName.setText(orderDetailsBO.getSmsName());
        if (StringUtils.isEmpty(orderDetailsBO.getOverdueDays()) || "0".equals(orderDetailsBO.getOverdueDays())) {
            yuqiAmountLayout.setVisibility(View.GONE);
            yuqiDay.setVisibility(View.GONE);
            payBackTime.setTextColor(Color.parseColor("#FF7028"));
            if (StringUtils.isEmpty(orderDetailsBO.getRepaymentDate())) {
                payBackTime.setText(getResources().getString(R.string.zuihouqixian));
            } else {
                payBackTime.setText(getResources().getString(R.string.zuihouqixian) + (orderDetailsBO.getRepaymentDate().split(" ")[0]));
            }
            btZhanqi.setVisibility(View.VISIBLE);
            zhaniqHint.setVisibility(View.VISIBLE);
        } else {
            yuqiAmountLayout.setVisibility(View.VISIBLE);
            yuqiDay.setVisibility(View.VISIBLE);
            yuqiAmount.setText(orderDetailsBO.getOverdueSum() + getResources().getString(R.string.danwei_yuan));
            payBackTime.setTextColor(Color.parseColor("#888888"));
            yuqiDay.setText(getResources().getString(R.string.yiyuqi) + "：" + orderDetailsBO.getOverdueDays() +
                    getResources().getString(R.string.danwei_tian));
            if (StringUtils.isEmpty(orderDetailsBO.getRepaymentDate())) {
                payBackTime.setText(getResources().getString(R.string.yinghuankuan_riqi));
            } else {
                payBackTime.setText(getResources().getString(R.string.yinghuankuan_riqi) + (orderDetailsBO.getRepaymentDate().split(" ")[0]));
            }
            btZhanqi.setVisibility(View.GONE);
            zhaniqHint.setVisibility(View.GONE);
        }
        borrowTime.setText(getResources().getString(R.string.jiekuanshijian) + orderDetailsBO.getLoanDate());
        payNum.setText(orderDetailsBO.getDelayRepaymentAmount());
        borrowAmount.setText(orderDetailsBO.getPrice() + getResources().getString(R.string.danwei_yuan));
        lixiAmount.setText(orderDetailsBO.getInterestAmount() + getResources().getString(R.string.danwei_yuan));
        fuwuAmount.setText(orderDetailsBO.getServiceAmount() + getResources().getString(R.string.danwei_yuan));
        yihuanAmount.setText(orderDetailsBO.getActualRepaymentAmount() + getResources().getString(R.string.danwei_yuan));
    }


    @OnClick(R.id.bt_zhanqi)
    public void clickZhanqi() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("order", orderDetailsBO);
        gotoActivity(ZhanQiActivity.class, bundle, false);
    }

    @OnClick(R.id.bt_huankuan)
    public void clickHuanKuan() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("order", orderDetailsBO);
        bundle.putInt("type", 0);
        gotoActivity(PayBackActivity1.class, bundle, false);
    }


    @OnClick({R.id.fuwu_img, R.id.yihuan_img})
    public void clickImg(View view) {
        switch (view.getId()) {
            case R.id.fuwu_img:
                showFuWuDialog();
                break;
            case R.id.yihuan_img:
                Bundle bundle = new Bundle();
                bundle.putSerializable("order", orderDetailsBO);
                gotoActivity(HuanKuanListActivty.class, bundle, false);
                break;
        }
    }


    /**
     * 显示服务费明细
     */
    private void showFuWuDialog() {
        View view = getLayoutInflater().inflate(R.layout.dialog_fuwufei, null);
        TextView fuwuName1 = view.findViewById(R.id.fuwu_name1);
        TextView fuwuName2 = view.findViewById(R.id.fuwu_name2);
        TextView fuwuName3 = view.findViewById(R.id.fuwu_name3);
        TextView fuwuNum1 = view.findViewById(R.id.fuwu_num1);
        TextView fuwuNum2 = view.findViewById(R.id.fuwu_num2);
        TextView fuwuNum3 = view.findViewById(R.id.fuwu_num3);
        TextView guanBi = view.findViewById(R.id.guanbi);
        fuwuName1.setText(orderDetailsBO.getServiceOneName());
        fuwuName2.setText(orderDetailsBO.getServiceTwoName());
        fuwuName3.setText(orderDetailsBO.getServiceThreeName());
        fuwuNum1.setText(orderDetailsBO.getServiceOnePrice() + getResources().getString(R.string.danwei_yuan));
        fuwuNum2.setText(orderDetailsBO.getServiceTwoPrice() + getResources().getString(R.string.danwei_yuan));
        fuwuNum3.setText(orderDetailsBO.getServiceThreePrice() + getResources().getString(R.string.danwei_yuan));
        MyDialog mMyDialog = new MyDialog(this, 0, 0, view, R.style.DialogTheme);
        mMyDialog.setCancelable(true);
        guanBi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMyDialog.dismiss();
            }
        });
        mMyDialog.show();
    }

}
