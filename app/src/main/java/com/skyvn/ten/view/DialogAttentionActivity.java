package com.skyvn.ten.view;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.blankj.utilcode.util.FileUtils;
import com.google.gson.Gson;
import com.skyvn.ten.R;
import com.skyvn.ten.api.HttpResultSubscriber;
import com.skyvn.ten.api.HttpServerImpl;
import com.skyvn.ten.base.BaseActivity;
import com.skyvn.ten.bean.AttentionSourrssBO;
import com.skyvn.ten.bean.ContactBO;
import com.skyvn.ten.bean.SmsBO;
import com.skyvn.ten.util.AppManager;
import com.skyvn.ten.util.AuthenticationUtils;
import com.skyvn.ten.util.SMSUtils;
import com.skyvn.ten.util.ToastManager;
import com.skyvn.ten.util.UpdateFileUtils;
import com.skyvn.ten.util.phone.PhoneDto;
import com.skyvn.ten.util.phone.PhoneUtil;
import com.skyvn.ten.widget.AlertDialog;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2020/4/912:51
 * desc   :  短信和通讯录认证界面
 * version: 1.0
 */
public class DialogAttentionActivity extends BaseActivity {


    private int needSourss;

    @Override
    protected int getLayout() {
        return R.layout.act_dialog_attention;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        needSourss = getIntent().getIntExtra("needStatus", 1);
        int type = getIntent().getIntExtra("type", 0);
        if (type == 0) {  //通讯录认证
            setTitleText(getString(R.string.contact));
            showContact();
        } else {
            setTitleText(getString(R.string.duanxin));
            showSms();
        }
    }


    private void showContact() {
        if (needSourss == 0) {   //可以跳过
            new AlertDialog(this).builder().setGone().setTitle(this.getResources().getString(R.string.tishi))
                    .setMsg(this.getResources().getString(R.string.contact_aleg_dialog))
                    .setCancelable(false)
                    .setNegativeButton(this.getResources().getString(R.string.tiaoguo), new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            jump(AuthenticationUtils.PHONE_LIST);
                        }
                    })
                    .setPositiveButton(this.getResources().getString(R.string.hao), v -> {
                        requestPermission(this);
                    }).show();
        } else {// 不能跳过
            new AlertDialog(this).builder().setGone().setTitle(this.getResources().getString(R.string.tishi))
                    .setMsg(this.getResources().getString(R.string.contact_aleg_dialog))
                    .setCancelable(false)
                    .setNegativeButton(this.getResources().getString(R.string.buyunxu), view ->
                            back(AuthenticationUtils.PHONE_LIST))
                    .setPositiveButton(this.getResources().getString(R.string.hao), v -> {
                        requestPermission(this);
                    }).show();
        }
    }


    private void showSms() {
        if (needSourss == 0) {   //可以跳过
            new AlertDialog(this).builder().setGone().setTitle(this.getResources().getString(R.string.tishi))
                    .setMsg(this.getResources().getString(R.string.sms_aleg_dialog))
                    .setCancelable(false)
                    .setNegativeButton(this.getResources().getString(R.string.tiaoguo), view -> jump(AuthenticationUtils.SMS__JILU_PAGE))
                    .setPositiveButton(this.getResources().getString(R.string.hao), v -> {
                        requestSmsPermission(this);
                    }).show();
        } else {// 不能跳过
            new AlertDialog(this).builder().setGone().setTitle(this.getResources().getString(R.string.tishi))
                    .setMsg(this.getResources().getString(R.string.sms_aleg_dialog))
                    .setCancelable(false)
                    .setNegativeButton(this.getResources().getString(R.string.buyunxu), view ->
                            back(AuthenticationUtils.SMS__JILU_PAGE))
                    .setPositiveButton(this.getResources().getString(R.string.hao), v -> {
                        requestSmsPermission(this);
                    }).show();
        }
    }


    public void back(String code) {
        HttpServerImpl.getBackMsg(code).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                new AlertDialog(DialogAttentionActivity.this).builder().setGone().setTitle(getResources().getString(R.string.tishi))
                        .setMsg(s)
                        .setNegativeButton(getResources().getString(R.string.fangqishenqing), view -> finish())
                        .setPositiveButton(getResources().getString(R.string.jixurenzheng), new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                switch (code) {
                                    case AuthenticationUtils.SMS__JILU_PAGE:
                                        showSms();
                                        break;
                                    case AuthenticationUtils.PHONE_LIST:
                                        showContact();
                                        break;
                                }
                            }
                        }).show();
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }


    /**
     * 通讯录权限
     */
    private void requestPermission(Activity activity) {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {//未开启定位权限
            //开启定位权限,200是标识码
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_CONTACTS,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 200);
        } else {
            getPersonList(activity);
        }
    }


    /**
     * 短信记录权限
     */
    private void requestSmsPermission(Activity activity) {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_CONTACTS)
                        != PackageManager.PERMISSION_GRANTED) {
            //开启定位权限,200是标识码
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_SMS,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_CONTACTS,
                    Manifest.permission.READ_EXTERNAL_STORAGE}, 300);
        } else {
            getSmsList(activity);
        }
    }

    static List<PhoneDto> phones;

    /**
     * 获取通讯录用户
     */
    private void getPersonList(Activity activity) {
        handler.sendEmptyMessage(0x11);
        new Thread(() -> {
            try {
                phones = new PhoneUtil(activity).searchContacts("");
                commitContactList(phones);
            } catch (Exception ex) {
                ex.printStackTrace();
                handler.sendEmptyMessage(0x22);
            }
        }).start();
    }

    static List<SmsBO> smsBOS;

    /**
     * 获取短信记录
     */
    private void getSmsList(Activity activity) {
        handler.sendEmptyMessage(0x11);
        new Thread(() -> {
            try {
                smsBOS = SMSUtils.obtainPhoneMessage(activity);
                commitSmsList();
            } catch (Exception ex) {
                ex.printStackTrace();
                handler.sendEmptyMessage(0x22);
            }
        }).start();
    }


    /**
     * 上传短信记录验证
     */
    private void commitSmsList() {
        String json = new Gson().toJson(smsBOS);
        String filePath = Environment.getExternalStorageDirectory().getPath() + "/saas.json";
        FileUtils.createFileByDeleteOldFile(filePath);
        try {
            FileWriter fw = new FileWriter(new File(filePath));
            BufferedWriter out = new BufferedWriter(fw);
            out.write(json);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        UpdateFileUtils utils = new UpdateFileUtils();
        utils.setListener(new UpdateFileUtils.OnCallBackListener() {
            @Override
            public void call(String s) {
                updateSms(s);
            }

            @Override
            public void callError(String message) {
                ToastManager.showShort(message);
                handler.sendEmptyMessage(0x22);
            }
        });
        utils.updateFile(4, filePath);
    }

    private void updateSms(String url) {
        HttpServerImpl.addClientSmsRecordAuth(url).subscribe(new HttpResultSubscriber<AttentionSourrssBO>() {
            @Override
            public void onSuccess(AttentionSourrssBO s) {
                handler.sendEmptyMessage(0x22);
                AuthenticationUtils.goAuthNextPage(s.getCode(), s.getNeedStatus(), AppManager.getAppManager().curremtActivity());
            }

            @Override
            public void onFiled(String message) {
                ToastManager.showShort(message);
                handler.sendEmptyMessage(0x22);
            }
        });
    }


    /**
     * 上传通讯录验证
     */
    private void commitContactList(List<PhoneDto> phoneDtos) {
        List<ContactBO> contactBOS = new ArrayList<>();
        for (PhoneDto phone : phoneDtos) {
            ContactBO contactBO = new ContactBO();
            contactBO.setPhone(phone.getTelPhone());
            contactBO.setName(phone.getName());
            contactBOS.add(contactBO);
        }
        String json = new Gson().toJson(contactBOS);
        String filePath = Environment.getExternalStorageDirectory().getPath() + "/saas.json";
        FileUtils.createFileByDeleteOldFile(filePath);
        try {
            FileWriter fw = new FileWriter(new File(filePath));
            BufferedWriter out = new BufferedWriter(fw);
            out.write(json);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        UpdateFileUtils utils = new UpdateFileUtils();
        utils.setListener(new UpdateFileUtils.OnCallBackListener() {
            @Override
            public void call(String s) {
                updateContact(s);
            }

            @Override
            public void callError(String message) {
                handler.sendEmptyMessage(0x22);
                ToastManager.showShort(message);
            }
        });
        utils.updateFile(3, filePath);
    }


    private void updateContact(String url) {
        HttpServerImpl.commitContactList(url).subscribe(new HttpResultSubscriber<AttentionSourrssBO>() {
            @Override
            public void onSuccess(AttentionSourrssBO s) {
                handler.sendEmptyMessage(0x22);
                AuthenticationUtils.goAuthNextPage(s.getCode(), s.getNeedStatus(), AppManager.getAppManager().curremtActivity());
            }

            @Override
            public void onFiled(String message) {
                handler.sendEmptyMessage(0x22);
                ToastManager.showShort(message);
            }
        });
    }

    public void jump(String status) {
        HttpServerImpl.jumpAuth(status).subscribe(new HttpResultSubscriber<AttentionSourrssBO>() {
            @Override
            public void onSuccess(AttentionSourrssBO s) {
                AuthenticationUtils.goAuthNextPage(s.getCode(), s.getNeedStatus(), AppManager.getAppManager().curremtActivity());
            }

            @Override
            public void onFiled(String message) {
                ToastManager.showShort(message);
            }
        });
    }


    private Handler handler = new Handler(AppManager.getAppManager().curremtActivity().getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x11:
                    showProgress();
                    break;
                case 0x22:
                    stopProgress();
                    break;
            }
        }
    };

}
