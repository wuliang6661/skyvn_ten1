package com.skyvn.ten.view.main.home_all_status;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skyvn.ten.R;
import com.skyvn.ten.api.HttpResultSubscriber;
import com.skyvn.ten.api.HttpServerImpl;
import com.skyvn.ten.base.BaseFragment;
import com.skyvn.ten.view.KefuActivity;
import com.skyvn.ten.widget.PopXingZhi;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class HomeEDuNoneFragment extends BaseFragment {

    @BindView(R.id.pay_num_title)
    TextView payNumTitle;
    @BindView(R.id.pay_num)
    TextView payNum;
    @BindView(R.id.days_text)
    TextView daysText;
    @BindView(R.id.days_layout)
    LinearLayout daysLayout;
    @BindView(R.id.lianxikefu)
    TextView lianxikefu;
    Unbinder unbinder;

    private List<String> days;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fra_edu_none, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getPayNum();
        getDays();
    }


    /**
     * 获取首页借款范围
     */
    private void getPayNum() {
        HttpServerImpl.getIndexFanwei().subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                payNum.setText(s);
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


    @OnClick(R.id.lianxikefu)
    public void clickLian() {
        gotoActivity(KefuActivity.class, false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
