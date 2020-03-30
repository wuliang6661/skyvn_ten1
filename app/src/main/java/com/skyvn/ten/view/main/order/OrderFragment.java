package com.skyvn.ten.view.main.order;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.StringUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.skyvn.ten.R;
import com.skyvn.ten.bean.OrderBO;
import com.skyvn.ten.config.IConstant;
import com.skyvn.ten.mvp.MVPBaseFragment;
import com.skyvn.ten.view.ConfirmationActivity;
import com.skyvn.ten.view.pay_back_style1.PayBackActivity1;
import com.skyvn.ten.view.pay_back_style2.PayBackActivity2;
import com.skyvn.ten.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.skyvn.ten.widget.lgrecycleadapter.LGViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class OrderFragment extends MVPBaseFragment<OrderContract.View, OrderPresenter>
        implements OrderContract.View {


    @BindView(R.id.bt_daiqueren)
    TextView btDaiqueren;
    @BindView(R.id.bt_daihuankuan)
    TextView btDaihuankuan;
    @BindView(R.id.bt_yijieshu)
    TextView btYijieshu;
    @BindView(R.id.recycle_view)
    RecyclerView recycleView;
    Unbinder unbinder;
    @BindView(R.id.animotion_view)
    View animotionView;

    private int viewInType = 0; // view 在第几个选项

    private TextView[] titles;

    List<OrderBO.DataBean> list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fra_order, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleView.setLayoutManager(manager);

        ViewGroup.LayoutParams params = animotionView.getLayoutParams();
        params.width = ScreenUtils.getScreenWidth() / 3;
        animotionView.setLayoutParams(params);

        titles = new TextView[]{btDaiqueren, btDaihuankuan, btYijieshu};
        mPresenter.getMyConfirmLoan();
    }


    @OnClick({R.id.bt_daiqueren, R.id.bt_daihuankuan, R.id.bt_yijieshu})
    public void clickTitle(View view) {
        switch (view.getId()) {
            case R.id.bt_daiqueren:   //待确认
                if (viewInType == 0) {
                    return;
                }
                animationStart(0);
                setTitles(0);
                viewInType = 0;
                showProgress();
                mPresenter.getMyConfirmLoan();
                break;
            case R.id.bt_daihuankuan:   //待还款
                if (viewInType == 1) {
                    return;
                }
                animationStart(1);
                setTitles(1);
                viewInType = 1;
                showProgress();
                mPresenter.getMyRepayLoan();
                break;
            case R.id.bt_yijieshu:   //已结束
                if (viewInType == 2) {
                    return;
                }
                animationStart(2);
                setTitles(2);
                viewInType = 2;
                showProgress();
                mPresenter.getMyEndLoan();
                break;
        }
    }

    /**
     * 设置title字体颜色
     */
    private void setTitles(int position) {
        for (int i = 0; i < titles.length; i++) {
            if (i == position) {
                titles[i].setTextColor(Color.parseColor("#3296FA"));
            } else {
                titles[i].setTextColor(Color.parseColor("#A3A5A8"));
            }
        }
    }


    private void animationStart(int position) {
        Animation animation = new TranslateAnimation(ScreenUtils.getScreenWidth() / 3 * viewInType, ScreenUtils.getScreenWidth() / 3 * position, 0, 0);
        animation.setDuration(300);
        animation.setRepeatCount(0);//动画的反复次数
        animation.setFillAfter(true);//设置为true，动画转化结束后被应用
        animotionView.startAnimation(animation);//開始动画
    }

    LGRecycleViewAdapter<OrderBO.DataBean> adapter;

    /**
     * 设置数据适配器
     */
    private void setAdapter() {
        if(adapter != null){
            adapter.setData(list);
            return;
        }
        adapter = new LGRecycleViewAdapter<OrderBO.DataBean>(list) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_order;
            }

            @Override
            public void convert(LGViewHolder holder, OrderBO.DataBean s, int position) {
                holder.setImageUrl(getActivity(), R.id.user_img, s.getLogoOssUrl());
                holder.setText(R.id.user_name, s.getSmsName());
                holder.setText(R.id.pay_num, s.getPrice() + "VND");
                holder.setText(R.id.qixian_day, s.getDays() + getResources().getString(R.string.danwei_tian));
                TextView statusText = (TextView) holder.getView(R.id.status_text);
                TextView item_btn = (TextView) holder.getView(R.id.item_btn);
                if (viewInType == 0) {  //待确认
                    holder.setText(R.id.create_time, getResources().getString(R.string.shenqing_riqi) + (s.getCreateTime().split(" ")[0]));
                    holder.setText(R.id.pay_num_text, getResources().getString(R.string.shenqingjine));
                    switch (s.getStatus()) {
                        case 0:  //审核中
                            statusText.setVisibility(View.VISIBLE);
                            statusText.setTextColor(Color.parseColor("#0077EA"));
                            statusText.setText(getResources().getString(R.string.shenhezhong));
                            item_btn.setVisibility(View.GONE);
                            break;
                        case 1:   //待提现
                            statusText.setVisibility(View.GONE);
                            item_btn.setVisibility(View.VISIBLE);
                            item_btn.setText(getResources().getString(R.string.lijitixian));
                            break;
                    }
                } else if (viewInType == 1) {    //待还款
                    holder.setText(R.id.pay_num_text, getResources().getString(R.string.jiekuanjine));
                    if (s.getOverdueDays() > 0) {
                        statusText.setVisibility(View.VISIBLE);
                        statusText.setTextColor(Color.parseColor("#BC792F"));
                        statusText.setText(getResources().getString(R.string.yiyuqi) + s.getOverdueDays() +
                                getResources().getString(R.string.danwei_tian));
                    } else {
                        statusText.setVisibility(View.GONE);
                    }
                    if (!StringUtils.isEmpty(s.getRepaymentDate())) {
                        holder.setText(R.id.create_time, getResources().getString(R.string.yinghuankuan_riqi) + (s.getRepaymentDate().split(" ")[0]));
                    } else {
                        holder.setText(R.id.create_time, getResources().getString(R.string.yinghuankuan_riqi));
                    }
                    item_btn.setVisibility(View.VISIBLE);
                    item_btn.setText(getResources().getString(R.string.lijihuankuan));
                } else {   //已结束
                    holder.setText(R.id.pay_num_text, getResources().getString(R.string.jiekuanjine));
                    if (!StringUtils.isEmpty(s.getLoanDate())) {
                        holder.setText(R.id.create_time, getResources().getString(R.string.jiekuan_riqi) + (s.getLoanDate().split(" ")[0]));
                    } else {
                        holder.setText(R.id.create_time, getResources().getString(R.string.jiekuan_riqi));
                    }
                    switch (s.getStatus()) {
                        case 0:  //审核失败
                            holder.setText(R.id.create_time, getResources().getString(R.string.shenqing_riqi) + (s.getCreateTime().split(" ")[0]));
                            statusText.setTextColor(Color.parseColor("#888888"));
                            statusText.setText(getResources().getString(R.string.shenhebutongguo));
                            item_btn.setVisibility(View.GONE);
                            break;
                        case 1:   //正常已还
                            statusText.setVisibility(View.VISIBLE);
                            item_btn.setVisibility(View.VISIBLE);
                            statusText.setTextColor(Color.parseColor("#15BC83"));
                            statusText.setText(getResources().getString(R.string.zhengchangyihuan));
                            item_btn.setText(getResources().getString(R.string.zaijieyici));
                            break;
                        case 2:   //逾期已还
                            statusText.setVisibility(View.VISIBLE);
                            item_btn.setVisibility(View.VISIBLE);
                            statusText.setTextColor(Color.parseColor("#BC792F"));
                            statusText.setText(getResources().getString(R.string.yuqiyihuan));
                            item_btn.setText(getResources().getString(R.string.zaijieyici));
                            break;
                        case 3:   //展期已还
                            statusText.setVisibility(View.VISIBLE);
                            item_btn.setVisibility(View.VISIBLE);
                            statusText.setTextColor(Color.parseColor("#2F87E0"));
                            statusText.setText(getResources().getString(R.string.zhanqiyihuan));
                            item_btn.setText(getResources().getString(R.string.zaijieyici));
                            break;
                        case 4:   //未提现
                            statusText.setVisibility(View.VISIBLE);
                            item_btn.setVisibility(View.VISIBLE);
                            statusText.setTextColor(Color.parseColor("#888888"));
                            statusText.setText(getResources().getString(R.string.weitixian));
                            item_btn.setText(getResources().getString(R.string.zaijieyici));
                            break;
                    }
                }
            }
        };
        adapter.setOnItemClickListener(R.id.item_btn, (view, position) -> {
            Bundle bundle = new Bundle();
            bundle.putString("id", list.get(position).getId());
            if (viewInType == 0) {  //提现界面
                gotoActivity(ConfirmationActivity.class, bundle, false);
            } else if (viewInType == 1) {  //还款详情
                if (IConstant.STYLE == 1) {
                    bundle.putInt("type", 1);
                    gotoActivity(PayBackActivity1.class, bundle, false);
                } else {
                    gotoActivity(PayBackActivity2.class, bundle, false);
                }
            } else {   //借款详情
//                gotoActivity(BorrowActivity1.class, bundle, false);
                mPresenter.loanAgain(list.get(position).getTenantId());
            }
        });
        recycleView.setAdapter(adapter);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onRequestError(String msg) {
        stopProgress();
        showToast(msg);
    }

    @Override
    public void getOrder(OrderBO orderBO) {
        stopProgress();
        this.list = orderBO.getData();
        setAdapter();
    }

    private void refreshList(){
        switch (viewInType){
            case 0:
                mPresenter.getMyConfirmLoan();
                break;
            case 1:
                mPresenter.getMyRepayLoan();
                break;
            case 2:
                mPresenter.getMyEndLoan();
                break;
        }
    }
    @Override
    public void loanAgainSouress() {
        showToast(getResources().getString(R.string.commit_sourss_toast));
        refreshList();
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
