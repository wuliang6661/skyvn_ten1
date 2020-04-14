package com.skyvn.ten.view.main.home_all_status;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.skyvn.ten.R;
import com.skyvn.ten.base.BaseFragment;
import com.skyvn.ten.bean.IndexBO;
import com.skyvn.ten.bean.RefreshEvent;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class HomeBufenFragment extends BaseFragment {

    @BindView(R.id.left_title)
    TextView leftTitle;
    @BindView(R.id.left_num)
    TextView leftNum;
    @BindView(R.id.right_title)
    TextView rightTitle;
    @BindView(R.id.right_num)
    TextView rightNum;
    @BindView(R.id.hint_text)
    TextView hintText;
    @BindView(R.id.bt_login)
    Button btLogin;
    Unbinder unbinder;

    private IndexBO indexBO;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fra_home_bufen, null);
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
        showIndex();
    }

    private void showIndex() {
        switch (indexBO.getOrderLoanRepaySerialVO().getType()) {
            case "11":
            case "31":
                leftTitle.setText(getString(R.string.daihuanjine_vnd));
                rightTitle.setText(R.string.huankuanjine_vnd);
                leftNum.setText(indexBO.getOrderLoanVO().getDelayRepaymentAmount());
                rightNum.setText(indexBO.getOrderLoanRepaySerialVO().getRepayAmount());
                hintText.setText(R.string.bufenhuankuan_loading);
                break;
            case "41":
                leftTitle.setText(R.string.zhanqijine_vnd);
                rightTitle.setText(R.string.zhanqitianshu_tian);
                leftNum.setText(indexBO.getOrderLoanRepaySerialVO().getRepayAmount());
                rightNum.setText(indexBO.getOrderLoanRepaySerialVO().getRolloverDays());
                hintText.setText(R.string.zhanqi_loading);
                break;
            case "51":
            case "52":
            case "53":
            case "54":
                leftTitle.setText(R.string.daihuanjine_vnd);
                rightTitle.setText(R.string.jianmianjine_vnd);
                leftNum.setText(indexBO.getOrderLoanVO().getDelayRepaymentAmount());
                rightNum.setText(indexBO.getOrderLoanRepaySerialVO().getRepayAmount());
                hintText.setText(R.string.jianmian_loading);
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
