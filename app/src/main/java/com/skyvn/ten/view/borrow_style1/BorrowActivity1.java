package com.skyvn.ten.view.borrow_style1;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.skyvn.ten.R;
import com.skyvn.ten.api.HttpResultSubscriber;
import com.skyvn.ten.api.HttpServerImpl;
import com.skyvn.ten.base.BaseActivity;
import com.skyvn.ten.bean.HuanKuanBO;
import com.skyvn.ten.bean.OrderDetailsBO;
import com.skyvn.ten.widget.MyDialog;
import com.skyvn.ten.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.skyvn.ten.widget.lgrecycleadapter.LGViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 借款详情风格1
 */
public class BorrowActivity1 extends BaseActivity {

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
    @BindView(R.id.yihuan_amount)
    TextView yihuanAmount;
    @BindView(R.id.recycle_view)
    RecyclerView recycleView;

    private String orderId;
    private OrderDetailsBO orderDetailsBO;

    private List<HuanKuanBO.DataBean> list;

    @Override
    protected int getLayout() {
        return R.layout.act_borrow_style1;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText(getResources().getString(R.string.jiekuanxiangqing));
        rightButton();

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleView.setLayoutManager(manager);
        recycleView.setNestedScrollingEnabled(false);

        orderId = getIntent().getExtras().getString("id");
        getOrderDetails();
        getHuanKuanList();
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


    private void showData() {
        Glide.with(this).load(orderDetailsBO.getLogoOssUrl())
                .error(R.drawable.user_img_defalt).placeholder(R.drawable.user_img_defalt).into(userImg);
        userName.setText(orderDetailsBO.getSmsName());
        if (StringUtils.isEmpty(orderDetailsBO.getOverdueDays()) || "0".equals(orderDetailsBO.getOverdueDays())) {
            yuqiDay.setVisibility(View.GONE);
            payBackTime.setTextColor(Color.parseColor("#FFAA00"));
            payBackTime.setText(getResources().getString(R.string.zuihouqixian) + orderDetailsBO.getRepaymentDate());
        } else {
            yuqiDay.setVisibility(View.VISIBLE);
            yuqiDay.setText(getResources().getString(R.string.yiyuqi) + "：" + orderDetailsBO.getOverdueDays() +
                    getResources().getString(R.string.danwei_tian));
            payBackTime.setTextColor(Color.parseColor("#999999"));
            payBackTime.setText(getResources().getString(R.string.zuihouqixian) + orderDetailsBO.getRepaymentDate());
        }
        borrowTime.setText(getResources().getString(R.string.jiekuanshijian) + orderDetailsBO.getLoanDate());
        payNum.setText(orderDetailsBO.getDelayRepaymentAmount());
        borrowAmount.setText(orderDetailsBO.getPrice() + getResources().getString(R.string.danwei_yuan));
        lixiAmount.setText(orderDetailsBO.getInterestAmount() + getResources().getString(R.string.danwei_yuan));
        fuwuAmount.setText(orderDetailsBO.getServiceAmount() + getResources().getString(R.string.danwei_yuan));
        yihuanAmount.setText(orderDetailsBO.getActualRepaymentAmount() + getResources().getString(R.string.danwei_yuan));
    }


    /**
     * 查询还款流水
     */
    private void getHuanKuanList() {
        HttpServerImpl.getMyRepaySerial(orderId).subscribe(new HttpResultSubscriber<HuanKuanBO>() {
            @Override
            public void onSuccess(HuanKuanBO s) {
                list = s.getData();
                setAdapter();
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }


    /**
     * 设置适配器
     */
    private void setAdapter() {
        LGRecycleViewAdapter<HuanKuanBO.DataBean> adapter = new LGRecycleViewAdapter<HuanKuanBO.DataBean>(list) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_huankuan;
            }

            @Override
            public void convert(LGViewHolder holder, HuanKuanBO.DataBean dataBean, int position) {
                holder.setText(R.id.update_price, dataBean.getRepaymentAmount() + getResources().getString(R.string.danwei_yuan));
                holder.setText(R.id.update_time, getResources().getString(R.string.huankuanshijian) + dataBean.getUpdateTime());
            }
        };
        recycleView.setAdapter(adapter);
    }


    @OnClick(R.id.fuwu_img)
    public void clickFuwu() {
        showFuWuDialog();
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
