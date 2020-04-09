package com.skyvn.ten.view.pay_back_style1;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.skyvn.ten.R;
import com.skyvn.ten.api.HttpResultSubscriber;
import com.skyvn.ten.api.HttpServerImpl;
import com.skyvn.ten.base.BaseActivity;
import com.skyvn.ten.bean.AccountBO;
import com.skyvn.ten.bean.OrderDetailsBO;
import com.skyvn.ten.util.OtherUtils;
import com.skyvn.ten.view.ZhanQiActivity;
import com.skyvn.ten.view.borrow_style1.BorrowActivity1;
import com.skyvn.ten.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.skyvn.ten.widget.lgrecycleadapter.LGViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 还款详情风格1
 */
public class PayBackActivity1 extends BaseActivity {

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
    @BindView(R.id.zhanqi_layout)
    LinearLayout zhanqiLayout;
    @BindView(R.id.recycle_view)
    RecyclerView recycleView;
    @BindView(R.id.next_img)
    ImageView nextImg;

    /**
     * 0为还款界面  1为风格1界面
     */
    private int type = 0;

    private String orderId;
    private OrderDetailsBO orderDetailsBO;

    @Override
    protected int getLayout() {
        return R.layout.act_pay_back1;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText(getResources().getString(R.string.huankuanxiangqing));
        rightButton();

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleView.setLayoutManager(manager);
        recycleView.setNestedScrollingEnabled(false);

        type = getIntent().getExtras().getInt("type", 1);
        orderDetailsBO = (OrderDetailsBO) getIntent().getExtras().getSerializable("order");
//        getOrderDetails();
        showData();
        getPayNums();
    }


    @OnClick(R.id.zhanqi_layout)
    public void clickZhanQi() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("order", orderDetailsBO);
        gotoActivity(ZhanQiActivity.class, bundle, false);
    }

    @OnClick(R.id.next_layout)
    public void clickNext() {
        Bundle bundle = new Bundle();
        bundle.putString("id", orderId);
        gotoActivity(BorrowActivity1.class, bundle, false);
    }


    private void getOrderDetails() {
        showProgress();
        HttpServerImpl.getMyLoan(orderId).subscribe(new HttpResultSubscriber<OrderDetailsBO>() {
            @Override
            public void onSuccess(OrderDetailsBO s) {
                stopProgress();
                orderDetailsBO = s;
                showData();
                getPayNums();
            }

            @Override
            public void onFiled(String message) {
                stopProgress();
                showToast(message);
            }
        });
    }

    /**
     * 获取借款用户账户
     */
    private void getPayNums() {
        HttpServerImpl.getUserPayNums(orderDetailsBO.getTenantId()).subscribe(new HttpResultSubscriber<List<AccountBO>>() {
            @Override
            public void onSuccess(List<AccountBO> s) {
                setAccountAdapter(s);
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }


    /**
     * 设置账户适配器
     */
    private void setAccountAdapter(List<AccountBO> s) {
        LGRecycleViewAdapter<AccountBO> adapter = new LGRecycleViewAdapter<AccountBO>(s) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_account;
            }

            @Override
            public void convert(LGViewHolder holder, AccountBO accountBO, int position) {
                holder.setText(R.id.fangshi_num, getResources().getString(R.string.fangshi) +
                        (position + 1));
                holder.setText(R.id.remark, accountBO.getRemark());
                holder.setText(R.id.bank_num, getResources().getString(R.string.yinghang_kahao)
                        + accountBO.getCardNumber());
                holder.setText(R.id.bank_person, getResources().getString(R.string.shoukuanren) + accountBO.getName());
                holder.setText(R.id.bank_name, getResources().getString(R.string.shoukuanyinghang) + accountBO.getBankName());
                holder.setText(R.id.bank_other_name, getResources().getString(R.string.zhihangmingcheng) + accountBO.getSubbranchName());
            }
        };
        recycleView.setAdapter(adapter);
    }


    /**
     * 显示界面
     */
    private void showData() {
        Glide.with(this).load(orderDetailsBO.getLogoOssUrl())
                .error(R.drawable.user_img_defalt).placeholder(R.drawable.user_img_defalt).into(userImg);
        userName.setText(orderDetailsBO.getSmsName());
        payNum.setText(orderDetailsBO.getDelayRepaymentAmount());
        if (type == 0) {  //还款界面
            yuqiDay.setVisibility(View.GONE);
            nextImg.setVisibility(View.GONE);
            zhanqiLayout.setVisibility(View.GONE);
            payBackTime.setTextColor(Color.parseColor("#FFAA00"));
            payBackTime.setText(getResources().getString(R.string.zuihouqixian) + orderDetailsBO.getRepaymentDate());
        } else {
            nextImg.setVisibility(View.VISIBLE);
            zhanqiLayout.setVisibility(View.VISIBLE);
            if (StringUtils.isEmpty(orderDetailsBO.getOverdueDays()) || "0".equals(orderDetailsBO.getOverdueDays())) {
                yuqiDay.setVisibility(View.GONE);
                payBackTime.setTextColor(Color.parseColor("#FFAA00"));
                payBackTime.setText(getResources().getString(R.string.zuihouqixian) + orderDetailsBO.getRepaymentDate());
            }else{
                yuqiDay.setVisibility(View.VISIBLE);
                yuqiDay.setText(getResources().getString(R.string.yiyuqi) + "：" + orderDetailsBO.getOverdueDays() +
                        getResources().getString(R.string.danwei_tian));
                payBackTime.setTextColor(Color.parseColor("#999999"));
                payBackTime.setText(getResources().getString(R.string.yinghuankuan_riqi) + orderDetailsBO.getRepaymentDate());
            }
        }
    }
}
