package com.skyvn.ten.view.main.home_all_status;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.skyvn.ten.R;
import com.skyvn.ten.base.BaseFragment;
import com.skyvn.ten.bean.IndexBO;
import com.skyvn.ten.bean.RefreshEvent;
import com.skyvn.ten.view.ZhanQiActivity;
import com.skyvn.ten.view.pay_back_style1.PayBackActivity1;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class HomeLoadingFragment extends BaseFragment {

    @BindView(R.id.pay_num_title)
    TextView payNumTitle;
    @BindView(R.id.pay_num)
    TextView payNum;
    @BindView(R.id.loading_text)
    TextView loadingText;
    @BindView(R.id.loading_layout)
    LinearLayout loadingLayout;
    @BindView(R.id.left_button)
    Button leftButton;
    @BindView(R.id.right_button)
    Button rightButton;
    @BindView(R.id.button_layout)
    LinearLayout buttonLayout;
    @BindView(R.id.bt_login)
    Button btLogin;
    Unbinder unbinder;

    private IndexBO indexBO;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fra_home_order_loading, null);
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
        payNumTitle.setText(R.string.yinghuanjine_yuan);
        payNum.setText(indexBO.getOrderLoanVO().getDelayRepaymentAmount());
        showIndex();
    }

    /**
     * 显示不同页面样式
     */
    private void showIndex() {
        if (indexBO.getOrderLoanRepaySerialVO() == null) {   //还款流水为空
            if ("0".equals(indexBO.getOrderLoanVO().getOverdue())) {  //未逾期
                loadingLayout.setVisibility(View.VISIBLE);
                buttonLayout.setVisibility(View.VISIBLE);
                btLogin.setVisibility(View.GONE);
                if (!StringUtils.isEmpty(indexBO.getOrderLoanVO().getRepaymentDate())) {
                    loadingText.setText(getString(R.string.yinghuankuan_riqi) + (indexBO.getOrderLoanVO().getRepaymentDate().split(" ")[0]));
                } else {
                    loadingText.setText(getString(R.string.yinghuankuan_riqi));
                }
            }
        } else {
//            switch (indexBO.getOrderLoanRepaySerialVO().getType()) {
//                case "0":
            loadingLayout.setVisibility(View.VISIBLE);
            buttonLayout.setVisibility(View.GONE);
            btLogin.setVisibility(View.VISIBLE);
            loadingText.setText(R.string.huankuanchulizhong);
            payNum.setTextColor(Color.parseColor("#333333"));
//                    break;
//            }
        }
    }


    @OnClick({R.id.left_button, R.id.right_button})
    public void clickButton(View view) {
        switch (view.getId()) {
            case R.id.left_button:
                Bundle bundle = new Bundle();
                bundle.putSerializable("order", indexBO.getOrderLoanVO());
                bundle.putInt("type", 0);
                gotoActivity(PayBackActivity1.class, bundle, false);
                break;
            case R.id.right_button:
                Bundle bundle1 = new Bundle();
                bundle1.putSerializable("order", indexBO.getOrderLoanVO());
                gotoActivity(ZhanQiActivity.class, bundle1, false);
                break;
        }
    }

    @OnClick(R.id.bt_login)
    public void refreshClick() {
        EventBus.getDefault().post(new RefreshEvent());
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
