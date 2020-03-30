package com.skyvn.ten.view.person_msg_style;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.skyvn.ten.R;
import com.skyvn.ten.api.HttpResultSubscriber;
import com.skyvn.ten.api.HttpServerImpl;
import com.skyvn.ten.base.BaseActivity;
import com.skyvn.ten.bean.AttentionSourrssBO;
import com.skyvn.ten.bean.LablesBO;
import com.skyvn.ten.util.AuthenticationUtils;
import com.skyvn.ten.util.TextChangedListener;
import com.skyvn.ten.widget.AlertDialog;
import com.skyvn.ten.widget.PopXingZhi;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class PersonMsgActivity2 extends BaseActivity {

    @BindView(R.id.edit_xueli)
    TextView editXueli;
    @BindView(R.id.edit_hunyin)
    TextView editHunyin;
    @BindView(R.id.edit_zinv_num)
    TextView editZinvNum;
    @BindView(R.id.edit_juzhu_time)
    TextView editJuzhuTime;
    @BindView(R.id.edit_address)
    EditText editAddress;
    @BindView(R.id.edit_zalo)
    EditText editZalo;
    @BindView(R.id.edit_facebook)
    EditText editFacebook;
    @BindView(R.id.jump_skip)
    TextView jumpSkip;

    @BindView(R.id.edit_user_name)
    EditText editUserName;
    @BindView(R.id.edit_user_idcard)
    EditText editUserIdcard;
    @BindView(R.id.edit_birthday)
    TextView editBirthday;
    @BindView(R.id.edit_sex)
    TextView editSex;

    private int selectHunyin = 0;
    private int selectSex = 0; // 性别默认是男

    TimePickerView pvTime;
    @SuppressLint("SimpleDateFormat")
    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");


    @Override
    protected int getLayout() {
        return R.layout.act_person_msg2;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout imageView = findViewById(R.id.back);
        imageView.setVisibility(View.VISIBLE);
        setTitleText(getResources().getString(R.string.gerenziliao));
        rightButton();

        int needStatus = getIntent().getIntExtra("needStatus", 1);
        if (needStatus == 0) {
            jumpSkip.setVisibility(View.VISIBLE);
        } else {
            jumpSkip.setVisibility(View.GONE);
        }
        TextChangedListener.StringWatcher(editUserName);
    }


    @OnClick(R.id.jump_skip)
    public void jump() {
        HttpServerImpl.jumpAuth(AuthenticationUtils.PERSON_MSG).subscribe(new HttpResultSubscriber<AttentionSourrssBO>() {
            @Override
            public void onSuccess(AttentionSourrssBO s) {
                AuthenticationUtils.goAuthNextPage(s.getCode(), s.getNeedStatus(), PersonMsgActivity2.this);
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }

    @OnClick(R.id.back)
    public void back() {
        HttpServerImpl.getBackMsg(AuthenticationUtils.PERSON_MSG).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                new AlertDialog(PersonMsgActivity2.this).builder().setGone().setTitle(getResources().getString(R.string.tishi))
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


    @OnClick({R.id.xueli_layout, R.id.zinv_num_layout, R.id.juzhu_time_layout, R.id.hunyin_layout})
    public void selectXueli(View view) {
        switch (view.getId()) {
            case R.id.xueli_layout:
                getLables(6);
                break;
            case R.id.zinv_num_layout:
                getLables(7);
                break;
            case R.id.juzhu_time_layout:
                getLables(8);
                break;
            case R.id.hunyin_layout:
                switchHunYin();
                break;
        }
    }

    /**
     * 获取标签
     */
    private void getLables(int parentId) {
        showProgress();
        HttpServerImpl.getSysLables(parentId).subscribe(new HttpResultSubscriber<List<LablesBO>>() {
            @Override
            public void onSuccess(List<LablesBO> s) {
                stopProgress();
                switchBanks(s, parentId);
            }

            @Override
            public void onFiled(String message) {
                stopProgress();
                showToast(message);
            }
        });
    }


    /**
     * 选择标签
     */
    private void switchBanks(List<LablesBO> s, int parentIds) {
        if (s == null) {
            showToast(getResources().getString(R.string.wushuju));
            return;
        }
        List<String> list = new ArrayList<>();
        for (LablesBO bankBO : s) {
            list.add(bankBO.getContent());
        }
        PopXingZhi popXingZhi = new PopXingZhi(this, "", list);
        popXingZhi.setListener((position, item) -> {
            switch (parentIds) {
                case 6:
                    editXueli.setText(item);
                    break;
                case 7:
                    editZinvNum.setText(item);
                    break;
                case 8:
                    editJuzhuTime.setText(item);
                    break;
            }
        });
        popXingZhi.showAtLocation(getWindow().getDecorView());
    }


    @OnClick({R.id.birthday_layout, R.id.sex_layout})
    public void clickBirthDay(View view) {
        switch (view.getId()) {
            case R.id.birthday_layout:
                initTimePicker();
                break;
            case R.id.sex_layout:
                selectSex();
                break;
        }
    }


    /**
     * 性别选择
     */
    private void selectSex() {
        List<String> list = new ArrayList<>();
        list.add(getResources().getString(R.string.nan));
        list.add(getResources().getString(R.string.nv));
        list.add(getResources().getString(R.string.qita));
        PopXingZhi popXingZhi = new PopXingZhi(this, "", list);
        popXingZhi.setListener((position, item) -> {
            editSex.setText(item);
            selectSex = position;
        });
        popXingZhi.showAtLocation(getWindow().getDecorView());
    }


    /**
     * 时间选择器
     */
    @SuppressLint("SimpleDateFormat")
    private void initTimePicker() {
        Calendar startDate = Calendar.getInstance();
        pvTime = new TimePickerBuilder(this, (date, v) -> editBirthday.setText(TimeUtils.date2String(date, format)))
                .setType(new boolean[]{true, true, true, false, false, false})
                .isDialog(true) //默认设置false ，内部实现将DecorView 作为它的父控件。
                .setDate(startDate)
                .setLineSpacingMultiplier(1.8f)
                .build();
        Dialog mDialog = pvTime.getDialog();
        if (mDialog != null) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);
            params.leftMargin = 0;
            params.rightMargin = 0;
            pvTime.getDialogContainerLayout().setLayoutParams(params);
            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
                dialogWindow.setDimAmount(0.1f);
            }
        }
        pvTime.show();
    }


    /**
     * 选择婚姻状况
     */
    private void switchHunYin() {
        List<String> list = new ArrayList<>();
        list.add(getResources().getString(R.string.hunyin1));
        list.add(getResources().getString(R.string.hunyin2));
        list.add(getResources().getString(R.string.hunyin3));
        list.add(getResources().getString(R.string.hunyin4));
        PopXingZhi popXingZhi = new PopXingZhi(this, "", list);
        popXingZhi.setListener((position, item) -> {
            selectHunyin = position;
            editHunyin.setText(item);
        });
        popXingZhi.showAtLocation(getWindow().getDecorView());
    }


    @OnClick(R.id.bt_login)
    public void commit() {
        String strName = editUserName.getText().toString().trim();
        if (StringUtils.isEmpty(strName)) {
            showToast(getResources().getString(R.string.wanshan_toast));
            return;
        }
        String strIdCard = editUserIdcard.getText().toString().trim();
        if (StringUtils.isEmpty(strIdCard)) {
            showToast(getResources().getString(R.string.wanshan_toast));
            return;
        }
        String strBirthDay = editBirthday.getText().toString().trim();
        if (StringUtils.isEmpty(strBirthDay)) {
            showToast(getResources().getString(R.string.wanshan_toast));
            return;
        }
        String strSex = editSex.getText().toString().trim();
        if (StringUtils.isEmpty(strSex)) {
            showToast(getResources().getString(R.string.wanshan_toast));
            return;
        }
        String strXueli = editXueli.getText().toString().trim();
        if (StringUtils.isEmpty(strXueli)) {
            showToast(getResources().getString(R.string.xueli_toast));
            return;
        }
        String strHunyin = editHunyin.getText().toString().trim();
        if (StringUtils.isEmpty(strHunyin)) {
            showToast(getResources().getString(R.string.hunyin_toast));
            return;
        }
        String strZiNvNum = editZinvNum.getText().toString().trim();
        if (StringUtils.isEmpty(strZiNvNum)) {
            showToast(getResources().getString(R.string.zinv_num_toast));
            return;
        }
        String strjuzhuTime = editJuzhuTime.getText().toString().trim();
        if (StringUtils.isEmpty(strjuzhuTime)) {
            showToast(getResources().getString(R.string.juzhu_time_toast));
            return;
        }
        String strJuzhuAddress = editAddress.getText().toString().trim();
        if (StringUtils.isEmpty(strJuzhuAddress)) {
            showToast(getResources().getString(R.string.juzhudizhi_toast));
            return;
        }
        if (strIdCard.length() != 9 && strIdCard.length() != 12) {
            showToast(getString(R.string.shenfenzheng_xianzhi));
            return;
        }
        String zalo = editZalo.getText().toString().trim();
        String facebook = editFacebook.getText().toString().trim();
        HttpServerImpl.addClientInfoAuth(strBirthDay, selectSex + "", strIdCard, strName,
                strXueli, selectHunyin + "", strZiNvNum, strjuzhuTime,
                strJuzhuAddress, zalo, facebook).subscribe(new HttpResultSubscriber<AttentionSourrssBO>() {
            @Override
            public void onSuccess(AttentionSourrssBO s) {
                showToast(getResources().getString(R.string.commit_sourss_toast));
                AuthenticationUtils.goAuthNextPage(s.getCode(), s.getNeedStatus(), PersonMsgActivity2.this);
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }
}
