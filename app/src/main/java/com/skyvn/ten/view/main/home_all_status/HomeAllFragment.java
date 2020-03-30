package com.skyvn.ten.view.main.home_all_status;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.FragmentUtils;
import com.blankj.utilcode.util.StringUtils;
import com.skyvn.ten.R;
import com.skyvn.ten.api.HttpResultSubscriber;
import com.skyvn.ten.api.HttpServerImpl;
import com.skyvn.ten.base.BaseFragment;
import com.skyvn.ten.bean.GongGaoBO;
import com.skyvn.ten.bean.IndexBO;
import com.skyvn.ten.bean.RefreshEvent;
import com.skyvn.ten.view.MessageActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class HomeAllFragment extends BaseFragment {


    @BindView(R.id.gonggao_text)
    TextView gonggaoText;
    @BindView(R.id.gonggao_layout)
    LinearLayout gonggaoLayout;
    Unbinder unbinder;

    public static IndexBO indexBO;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fra_home_all, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EventBus.getDefault().register(this);
        gonggaoLayout.setVisibility(View.GONE);

    }


    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        getNotices();
        getIndex();
    }

    /**
     * 获取公告
     */
    private void getNotices() {
        HttpServerImpl.getNotices().subscribe(new HttpResultSubscriber<List<GongGaoBO>>() {
            @Override
            public void onSuccess(List<GongGaoBO> gongGaoBOS) {
                if (gongGaoBOS == null || gongGaoBOS.isEmpty()) {
                    return;
                }
                StringBuilder builder = new StringBuilder();
                for (GongGaoBO gongGaoBO : gongGaoBOS) {
                    builder.append(gongGaoBO.getContent());
                    builder.append("           ");
                }
                gonggaoText.setText(builder.toString());
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
                gonggaoLayout.setVisibility(View.GONE);
            }
        });
    }

    /**
     * 获取首页显示
     */
    private void getIndex() {
//        showProgress();
        HttpServerImpl.getIndex().subscribe(new HttpResultSubscriber<IndexBO>() {
            @Override
            public void onSuccess(IndexBO s) {
//                stopProgress();
                indexBO = s;
                if (s == null) {
                    showToast("首页返回数据空！");
                    return;
                }
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        showIndex();
                    }
                });
            }

            @Override
            public void onFiled(String message) {
//                stopProgress();
                showToast(message);
            }
        });
    }

    /**
     * 显示不同的页面风格
     */
    private void showIndex() {
        switch (indexBO.getAuthStatus()) {   //认证状态
            case "0":   //未认证
            case "1":   //认证中
            case "3":   //授信失效
                FragmentUtils.replace(getFragmentManager(),
                        new HomeAttentionFragment(), R.id.fragment_home);
                break;
            case "2":    //通过
                if (indexBO.getOrderLoanVO() == null) {   //完成认证
                    if ("0".equals(indexBO.getQuota()) || StringUtils.isEmpty(indexBO.getQuota())) {   //额度为空
                        FragmentUtils.replace(getFragmentManager(),
                                new HomeEDuNoneFragment(), R.id.fragment_home);
                    } else {
                        FragmentUtils.replace(getFragmentManager(),
                                new HomeAttentionFragment(), R.id.fragment_home);
                    }
                } else {
                    switch (indexBO.getLoanStatus()) {   //订单状态
                        case "0":   //审核中
                        case "1":  //审核失败
                        case "2":   //放款中
                        case "3":   //放款失败
                            FragmentUtils.replace(getActivity().getSupportFragmentManager(),
                                    new HomeExamineFragment(), R.id.fragment_home);
                            break;
                        case "4":   //待还款
                            if (indexBO.getOrderLoanRepaySerialVO() == null ||
                                    !"0".equals(indexBO.getOrderLoanRepaySerialVO().getStatus())) {   //还款流水为空
                                if ("0".equals(indexBO.getOrderLoanVO().getOverdue())) {  //未逾期
                                    FragmentUtils.replace(getActivity().getSupportFragmentManager(),
                                            new HomeLoadingFragment(), R.id.fragment_home);
                                } else {    //已逾期
                                    FragmentUtils.replace(getActivity().getSupportFragmentManager(),
                                            new HomeYuQiFragment(), R.id.fragment_home);
                                }
                            } else {  //有还款流水
                                switch (indexBO.getOrderLoanRepaySerialVO().getType()) {
                                    case "1":    //还款处理中
                                    case "21":
                                        FragmentUtils.replace(getActivity().getSupportFragmentManager(),
                                                new HomeLoadingFragment(), R.id.fragment_home);
                                        break;
                                    case "11":
                                    case "31":
                                    case "41":
                                    case "51":
                                    case "52":
                                    case "53":
                                    case "54":
                                        FragmentUtils.replace(getActivity().getSupportFragmentManager(),
                                                new HomeBufenFragment(), R.id.fragment_home);
                                        break;
                                }
                            }
                            break;
                        case "5":    //已结清
                            FragmentUtils.replace(getFragmentManager(),
                                    new HomeAttentionFragment(), R.id.fragment_home);
                            break;
                    }
                }
                break;
        }

    }

    @OnClick(R.id.gonggao_layout)
    public void clickGonggao() {
        gotoActivity(MessageActivity.class, false);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refresh(RefreshEvent event) {
        getIndex();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }
}
