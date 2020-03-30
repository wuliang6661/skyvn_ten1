package com.skyvn.ten.view.main.home_all_status;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skyvn.ten.R;
import com.skyvn.ten.api.HttpResultSubscriber;
import com.skyvn.ten.api.HttpServerImpl;
import com.skyvn.ten.base.BaseFragment;
import com.skyvn.ten.bean.BankCardBO;
import com.skyvn.ten.bean.IndexBO;
import com.skyvn.ten.view.KefuActivity;
import com.skyvn.ten.widget.PopXingZhi;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 2.审核状态
 */
public class HomeExamineFragment extends BaseFragment {

    @BindView(R.id.pay_num)
    TextView payNum;
    @BindView(R.id.days_text)
    TextView daysText;
    @BindView(R.id.days_layout)
    LinearLayout daysLayout;
    @BindView(R.id.shouxin_shengyu_text)
    TextView shouxinShengyuText;
    @BindView(R.id.shouxin_shengyu_layout)
    LinearLayout shouxinShengyuLayout;
    @BindView(R.id.hint_img)
    ImageView hintImg;
    @BindView(R.id.hint_text)
    TextView hintText;
    @BindView(R.id.hint_layout)
    LinearLayout hintLayout;
    @BindView(R.id.shenhe_title)
    TextView shenheTitle;
    @BindView(R.id.shenhe_msg)
    TextView shenheMsg;
    @BindView(R.id.shenhe_layout)
    LinearLayout shenheLayout;
    Unbinder unbinder;
    @BindView(R.id.kefu_img)
    ImageView kefuImg;
    @BindView(R.id.kefu_text)
    TextView kefuText;
    @BindView(R.id.kefu_layout)
    LinearLayout kefuLayout;
    @BindView(R.id.pay_num_title)
    TextView payNumTitle;

    private IndexBO indexBO;

    private List<String> days;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fra_home_exmine, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        indexBO = HomeAllFragment.indexBO;
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                showIndex();
            }
        });
    }

    /**
     * 显示不同样式页面
     */
    private void showIndex() {
        payNum.setText(indexBO.getQuota());
        switch (indexBO.getLoanStatus()) {   //订单状态
            case "0":   //审核中
                daysLayout.setVisibility(View.GONE);
                shouxinShengyuLayout.setVisibility(View.VISIBLE);
                shouxinShengyuText.setText(indexBO.getDays() + getString(R.string.danwei_tian));
                shenheLayout.setVisibility(View.VISIBLE);
                hintLayout.setVisibility(View.GONE);
                break;
            case "1":  //审核失败
                daysLayout.setVisibility(View.VISIBLE);
                shouxinShengyuLayout.setVisibility(View.GONE);
                shenheLayout.setVisibility(View.GONE);
                hintLayout.setVisibility(View.VISIBLE);
                hintImg.setImageResource(R.drawable.shenhe_error_hint);
                hintText.setText(R.string.shenhe_weitongguo);
                hintText.setTextColor(Color.parseColor("#666666"));
                getDays();
                break;
            case "2":   //放款中
                payNumTitle.setText(R.string.daozhangjine);
                daysLayout.setVisibility(View.GONE);
                shouxinShengyuLayout.setVisibility(View.VISIBLE);
                shouxinShengyuText.setText(indexBO.getDays() + getString(R.string.danwei_tian));
                hintLayout.setVisibility(View.GONE);
                kefuImg.setVisibility(View.GONE);
                kefuText.setText(R.string.butongyinghangdaozhang);
                getBankCard();
                break;
            case "3":   //放款失败
                payNumTitle.setText(R.string.daozhangjine);
                daysLayout.setVisibility(View.GONE);
                shouxinShengyuLayout.setVisibility(View.VISIBLE);
                shouxinShengyuText.setText(indexBO.getDays() + getString(R.string.danwei_tian));
                shenheLayout.setVisibility(View.GONE);
                hintLayout.setVisibility(View.VISIBLE);
                hintImg.setImageResource(R.drawable.shenhe_error_hint);
                hintText.setText(R.string.fangkuanshibai);
                hintText.setTextColor(Color.parseColor("#666666"));
                break;
        }
    }


    /**
     * 获取我的银行卡
     */
    private void getBankCard() {
        HttpServerImpl.getBankCard().subscribe(new HttpResultSubscriber<BankCardBO>() {
            @Override
            public void onSuccess(BankCardBO s) {
                shenheLayout.setVisibility(View.VISIBLE);
                shenheTitle.setVisibility(View.GONE);
                if (s == null) {
                    shenheMsg.setText(R.string.wuyinghangkaxinxi);
                } else {
                    String cardNo = s.getCardNo().length() > 4 ?
                            s.getCardNo().substring(s.getCardNo().length() - 4, s.getCardNo().length()) : s.getCardNo();
                    shenheMsg.setText(R.string.fangkuan_yinghang1 + cardNo + getString(R.string.de) + s.getBank() +
                            getString(R.string.fangkuan_yinghang2));
                }
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }


    /**
     * 获取首页天数
     */
    private void getDays() {
        HttpServerImpl.getIndexDays().subscribe(new HttpResultSubscriber<List<String>>() {
            @Override
            public void onSuccess(List<String> s) {
                if (s == null || s.isEmpty()) {
                    return;
                }
                days = s;
                daysText.setText(s.get(0));
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }


    @OnClick(R.id.days_layout)
    public void switchDays() {
        if (days == null) {
            return;
        }
        PopXingZhi popXingZhi = new PopXingZhi(getActivity(), "", days);
        popXingZhi.setListener((position, item) -> {
            daysText.setText(item);
        });
        popXingZhi.showAtLocation(getActivity().getWindow().getDecorView());
    }


    @OnClick(R.id.kefu_layout)
    public void clickKefu() {
        gotoActivity(KefuActivity.class, false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
