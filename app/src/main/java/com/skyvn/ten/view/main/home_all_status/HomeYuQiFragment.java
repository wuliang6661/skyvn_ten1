package com.skyvn.ten.view.main.home_all_status;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skyvn.ten.R;
import com.skyvn.ten.base.BaseFragment;
import com.skyvn.ten.bean.IndexBO;
import com.skyvn.ten.view.pay_back_style1.PayBackActivity1;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 已逾期的首页
 */
public class HomeYuQiFragment extends BaseFragment {

    @BindView(R.id.shouxin_shengyu_text)
    TextView shouxinShengyuText;
    @BindView(R.id.shouxin_shengyu_layout)
    LinearLayout shouxinShengyuLayout;
    @BindView(R.id.yuqi_weiyuejin)
    TextView yuqiWeiyuejin;
    @BindView(R.id.yinghuan_jine)
    TextView yinghuanJine;
    @BindView(R.id.bt_login)
    Button btLogin;
    Unbinder unbinder;

    IndexBO indexBO;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fra_home_yuqi, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        indexBO = HomeAllFragment.indexBO;

    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        shouxinShengyuText.setText(indexBO.getOrderLoanVO().getOverdueDays() + getString(R.string.danwei_tian));
        yuqiWeiyuejin.setText(indexBO.getOrderLoanVO().getOverdueSum());
        yinghuanJine.setText(indexBO.getOrderLoanVO().getRepaymentAmount() + "");
    }

    @OnClick(R.id.bt_login)
    public void commit() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("order", indexBO.getOrderLoanVO());
        bundle.putInt("type", 0);
        gotoActivity(PayBackActivity1.class, bundle, false);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
