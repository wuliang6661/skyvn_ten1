package com.skyvn.ten.view.emergencycontact;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.skyvn.ten.R;
import com.skyvn.ten.api.HttpResultSubscriber;
import com.skyvn.ten.api.HttpServerImpl;
import com.skyvn.ten.bean.AttentionSourrssBO;
import com.skyvn.ten.bean.ContactBO;
import com.skyvn.ten.mvp.MVPBaseActivity;
import com.skyvn.ten.util.AuthenticationUtils;
import com.skyvn.ten.view.contact.ContactActivity;
import com.skyvn.ten.widget.AlertDialog;
import com.skyvn.ten.widget.PopXingZhi;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * MVPPlugin
 * 紧急联系人
 */

public class EmergencyContactActivity extends MVPBaseActivity<EmergencyContactContract.View,
        EmergencyContactPresenter> implements EmergencyContactContract.View {

    @BindView(R.id.edit_guanxi)
    TextView editGuanxi;
    @BindView(R.id.guanxi_layout1)
    LinearLayout guanxiLayout1;
    @BindView(R.id.edit_yinghang_name)
    TextView editYinghangName;
    @BindView(R.id.one_phone_layout)
    LinearLayout onePhoneLayout;
    @BindView(R.id.edit_name)
    EditText editName;
    @BindView(R.id.edit_two_guanxi)
    TextView editTwoGuanxi;
    @BindView(R.id.guanxi_layout2)
    LinearLayout guanxiLayout2;
    @BindView(R.id.edit_two_phone_num)
    TextView editTwoPhoneNum;
    @BindView(R.id.two_phone_layout)
    LinearLayout twoPhoneLayout;
    @BindView(R.id.edit_two_name)
    EditText editTwoName;
    @BindView(R.id.checkbox)
    CheckBox checkbox;
    @BindView(R.id.bt_login)
    Button btLogin;
    @BindView(R.id.jump_skip)
    TextView jumpSkip;

    private int selectGuanxi1 = 0;  //关系1

    private int selectGuanxi2 = 1;   //关系2

    @Override
    protected int getLayout() {
        return R.layout.act_emergency_contact;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        goBack();
        LinearLayout imageView = findViewById(R.id.back);
        imageView.setVisibility(View.VISIBLE);
        setTitleText(getResources().getString(R.string.jingjilianxiren));
        rightButton();

        checkbox.setOnCheckedChangeListener((compoundButton, b) -> btLogin.setEnabled(b));

        int needStatus = getIntent().getIntExtra("needStatus", 1);
        if (needStatus == 0) {
            jumpSkip.setVisibility(View.VISIBLE);
        } else {
            jumpSkip.setVisibility(View.GONE);
        }
    }


    @OnClick(R.id.jump_skip)
    public void jump() {
        HttpServerImpl.jumpAuth(AuthenticationUtils.CONTACT_PAGE).subscribe(new HttpResultSubscriber<AttentionSourrssBO>() {
            @Override
            public void onSuccess(AttentionSourrssBO s) {
                AuthenticationUtils.goAuthNextPage(s.getCode(), s.getNeedStatus(), EmergencyContactActivity.this);
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }


    @OnClick(R.id.back)
    public void back() {
        HttpServerImpl.getBackMsg(AuthenticationUtils.CONTACT_PAGE).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                new AlertDialog(EmergencyContactActivity.this).builder().setGone().setTitle(getResources().getString(R.string.tishi))
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


    @OnClick({R.id.one_phone_layout, R.id.two_phone_layout})
    public void phoneClick(View view) {
        Intent intent = new Intent(this, ContactActivity.class);
        switch (view.getId()) {
            case R.id.one_phone_layout:
                intent.putExtra("type", 0);
                startActivityForResult(intent, 0x11);
                break;
            case R.id.two_phone_layout:
                intent.putExtra("type", 1);
                startActivityForResult(intent, 0x22);
                break;
        }
    }

    @OnClick({R.id.guanxi_layout1, R.id.guanxi_layout2})
    public void clickGuanxi(View view) {
        switch (view.getId()) {
            case R.id.guanxi_layout1:
                List<String> list = new ArrayList<>();
                list.add(getResources().getString(R.string.fuqin));
                list.add(getResources().getString(R.string.muqin));
                list.add(getResources().getString(R.string.peiou));
                list.add(getResources().getString(R.string.erzi));
                list.add(getResources().getString(R.string.nver));
                switchHunYin(list, 0);
                break;
            case R.id.guanxi_layout2:
                List<String> list2 = new ArrayList<>();
                list2.add(getResources().getString(R.string.xiongdi));
                list2.add(getResources().getString(R.string.pengyou));
                list2.add(getResources().getString(R.string.tongshi));
                list2.add(getResources().getString(R.string.tongxue));
                list2.add(getResources().getString(R.string.qinqi));
                switchHunYin(list2, 1);
                break;
        }
    }


    /**
     * 选择婚姻状况
     */
    private void switchHunYin(List<String> lists, int type) {
        PopXingZhi popXingZhi = new PopXingZhi(this, "", lists);
        popXingZhi.setListener((position, item) -> {
            switch (type) {
                case 0:
                    selectGuanxi1 = position;
                    editGuanxi.setText(item);
                    break;
                case 1:
                    selectGuanxi2 = position + 5;
                    editTwoGuanxi.setText(item);
                    break;
            }
        });
        popXingZhi.showAtLocation(getWindow().getDecorView());
    }


    @OnClick(R.id.bt_login)
    public void commitContact() {
        String strGuanxi1 = editGuanxi.getText().toString().trim();
        if (StringUtils.isEmpty(strGuanxi1)) {
            showToast(getResources().getString(R.string.wanshan_toast));
            return;
        }
        String strGuanxi2 = editTwoGuanxi.getText().toString().trim();
        if (StringUtils.isEmpty(strGuanxi2)) {
            showToast(getResources().getString(R.string.wanshan_toast));
            return;
        }
        String strPhone1 = editYinghangName.getText().toString().trim();
        if (StringUtils.isEmpty(strPhone1)) {
            showToast(getResources().getString(R.string.wanshan_toast));
            return;
        }
        String strPhone2 = editTwoPhoneNum.getText().toString().trim();
        if (StringUtils.isEmpty(strPhone2)) {
            showToast(getResources().getString(R.string.wanshan_toast));
            return;
        }
        String strName1 = editName.getText().toString().trim();
        if (StringUtils.isEmpty(strName1)) {
            showToast(getResources().getString(R.string.wanshan_toast));
            return;
        }
        String strName2 = editTwoName.getText().toString().trim();
        if (StringUtils.isEmpty(strName2)) {
            showToast(getResources().getString(R.string.wanshan_toast));
            return;
        }
        HttpServerImpl.commitContactInfo(strName1, strName2, strPhone1, strPhone2,
                selectGuanxi1, selectGuanxi2).subscribe(new HttpResultSubscriber<AttentionSourrssBO>() {
            @Override
            public void onSuccess(AttentionSourrssBO s) {
                showToast(getResources().getString(R.string.commit_sourss_toast));
                AuthenticationUtils.goAuthNextPage(s.getCode(), s.getNeedStatus(), EmergencyContactActivity.this);
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        ContactBO contactBO = (ContactBO) data.getSerializableExtra("contact");
        switch (requestCode) {
            case 0x11:
                editName.setText(contactBO.getName());
                editYinghangName.setText(contactBO.getPhone());
                break;
            case 0x22:
                editTwoName.setText(contactBO.getName());
                editTwoPhoneNum.setText(contactBO.getPhone());
                break;
        }
    }
}
