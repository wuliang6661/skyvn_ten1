package com.skyvn.ten.view.bindbankcard;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.skyvn.ten.R;
import com.skyvn.ten.api.HttpResultSubscriber;
import com.skyvn.ten.api.HttpServerImpl;
import com.skyvn.ten.bean.AttentionSourrssBO;
import com.skyvn.ten.bean.BankBO;
import com.skyvn.ten.mvp.MVPBaseActivity;
import com.skyvn.ten.util.AuthenticationUtils;
import com.skyvn.ten.util.TextChangedListener;
import com.skyvn.ten.widget.AlertDialog;
import com.skyvn.ten.widget.PopXingZhi;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * MVPPlugin
 * 绑定银行卡
 */

public class BindBankCardActivity extends MVPBaseActivity<BindBankCardContract.View, BindBankCardPresenter>
        implements BindBankCardContract.View {

    @BindView(R.id.edit_user_name)
    EditText editUserName;
    @BindView(R.id.edit_yinghang_num)
    EditText editYinghangNum;
    @BindView(R.id.edit_yinghang_name)
    TextView editYinghangName;
    @BindView(R.id.yinghang_name_layout)
    LinearLayout yinghangNameLayout;
    @BindView(R.id.edit_suoshuzhihang)
    EditText editSuoshuzhihang;
    @BindView(R.id.bt_login)
    Button btLogin;
    @BindView(R.id.jump_skip)
    TextView jumpSkip;
    @BindView(R.id.edit_yinghang_type)
    TextView editYinghangType;
    @BindView(R.id.yinghang_type_layout)
    LinearLayout yinghangTypeLayout;

    private int selectPosition = 0;
    private List<BankBO> bankBOS;

    private int selectYingHangType = 0;

    @Override
    protected int getLayout() {
        return R.layout.act_bind_bank_card;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout imageView = findViewById(R.id.back);
        imageView.setVisibility(View.VISIBLE);
        setTitleText(getResources().getString(R.string.bangdingyinghangka));
        rightButton();

        int needStatus = getIntent().getIntExtra("needStatus", 1);
        if (needStatus == 0) {
            jumpSkip.setVisibility(View.VISIBLE);
        } else {
            jumpSkip.setVisibility(View.GONE);
        }

        TextChangedListener.StringWatcher(editUserName);
        editUserName.setEnabled(false);
        getRealName();
    }


    @OnClick(R.id.back)
    public void back() {
        HttpServerImpl.getBackMsg(AuthenticationUtils.BIND_BANK_CARD).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                new AlertDialog(BindBankCardActivity.this).builder().setGone().setTitle(getResources().getString(R.string.tishi))
                        .setMsg(s)
                        .setNegativeButton(getResources().getString(R.string.fangqishenqing), view -> finish())
                        .setPositiveButton(getResources().getString(R.string.jixurenzheng), null).show();
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }

    @OnClick(R.id.jump_skip)
    public void jump() {
        HttpServerImpl.jumpAuth(AuthenticationUtils.BIND_BANK_CARD).subscribe(new HttpResultSubscriber<AttentionSourrssBO>() {
            @Override
            public void onSuccess(AttentionSourrssBO s) {
                AuthenticationUtils.goAuthNextPage(s.getCode(), s.getNeedStatus(), BindBankCardActivity.this);
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }


    private void getRealName() {
        showProgress();
        HttpServerImpl.getRealName().subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                stopProgress();
                editUserName.setText(s);
            }

            @Override
            public void onFiled(String message) {
                stopProgress();
                showToast(message);
                finish();
            }
        });
    }


    @OnClick(R.id.yinghang_name_layout)
    public void clickYinghang() {
        getBankNames();
    }


    @OnClick(R.id.yinghang_type_layout)
    public void clickYinghangKaType() {
        List<String> list = new ArrayList<>();
        list.add(getString(R.string.yinghangzhanghao));
        list.add(getString(R.string.yinghangkahao));
        PopXingZhi popXingZhi = new PopXingZhi(this, "", list);
        popXingZhi.setListener((position, item) -> {
            selectYingHangType = position;
            editYinghangType.setText(item);
            editYinghangNum.setHint(item);
        });
        popXingZhi.setSelectPosition(selectYingHangType);
        popXingZhi.showAtLocation(getWindow().getDecorView());
    }


    /**
     * 获取所有银行名称
     */
    private void getBankNames() {
        showProgress();
        HttpServerImpl.getSysBanks().subscribe(new HttpResultSubscriber<List<BankBO>>() {
            @Override
            public void onSuccess(List<BankBO> s) {
                stopProgress();
                bankBOS = s;
                switchBanks(s);
            }

            @Override
            public void onFiled(String message) {
                stopProgress();
                showToast(message);
            }
        });
    }


    private void switchBanks(List<BankBO> s) {
        if (s == null) {
            showToast(getResources().getString(R.string.wushuju));
            return;
        }
        List<String> list = new ArrayList<>();
        for (BankBO bankBO : s) {
            list.add(bankBO.getName());
        }
        PopXingZhi popXingZhi = new PopXingZhi(this, "", list);
        popXingZhi.setListener((position, item) -> {
            selectPosition = position;
            editYinghangName.setText(item);
        });
        popXingZhi.setSelectPosition(selectPosition);
        popXingZhi.showAtLocation(getWindow().getDecorView());
    }

    /**
     * 提交
     */
    @OnClick(R.id.bt_login)
    public void commit() {
        String strName = editUserName.getText().toString().trim();
        if (StringUtils.isEmpty(strName)) {
            showToast(getResources().getString(R.string.user_name_toast));
            return;
        }
        String strBankNum = editYinghangNum.getText().toString().trim();
        if (StringUtils.isEmpty(strBankNum)) {
            showToast(getResources().getString(R.string.bank_num_toast));
            return;
        }
        String stryinhangName = editYinghangName.getText().toString().trim();
        if (StringUtils.isEmpty(stryinhangName)) {
            showToast(getResources().getString(R.string.yinghang_name_toast));
            return;
        }
        String suoshuzhihang = editSuoshuzhihang.getText().toString().trim();
        if (StringUtils.isEmpty(suoshuzhihang)) {
            showToast(getResources().getString(R.string.suoshuzhihang_toast));
            return;
        }
        if (strBankNum.length() < 9) {
            showToast(getString(R.string.yinghangkahaoxu));
            return;
        }
        HttpServerImpl.bindBankCard(stryinhangName, strBankNum, strName, suoshuzhihang, bankBOS.get(selectPosition).getCode(), selectYingHangType + "")
                .subscribe(new HttpResultSubscriber<AttentionSourrssBO>() {
                    @Override
                    public void onSuccess(AttentionSourrssBO s) {
                        showToast(getResources().getString(R.string.commit_sourss_toast));
                        AuthenticationUtils.goAuthNextPage(s.getCode(), s.getNeedStatus(), BindBankCardActivity.this);
                    }

                    @Override
                    public void onFiled(String message) {
                        showToast(message);
                    }
                });
    }

}
