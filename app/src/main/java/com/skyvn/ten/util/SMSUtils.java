package com.skyvn.ten.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.skyvn.ten.bean.SmsBO;

import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class SMSUtils {

    private static Uri SMS_INBOX = Uri.parse("content://sms/");

    public static List<SmsBO> obtainPhoneMessage(Context context) {
        List<SmsBO> list = new ArrayList<>();
        ContentResolver cr = context.getContentResolver();
        String[] projection = new String[]{"address", "body", "date"};
        Cursor cur = cr.query(SMS_INBOX, projection, null, null, "date desc");
        if (null == cur) {
            Log.i("ooc", "************cur == null");
            return null;
        }
        while (cur.moveToNext()) {
            String number = cur.getString(cur.getColumnIndex("address"));//手机号
            String body = cur.getString(cur.getColumnIndex("body"));//短信内容
            long date = cur.getLong(cur.getColumnIndex("date"));
            //至此就获得了短信的相关的内容, 以下是把短信加入map中，构建listview,非必要。
            String personName = getContactByAddr(context, number);
            Log.i(TAG, "name=" + personName + "phoneNumber=" + number + ",body=" + body);
            SmsBO smsBO = new SmsBO();
            smsBO.setMessage(body);
//            smsBO.setName(StringUtils.isEmpty(name) ? "-" : name);
            smsBO.setName(personName);
            smsBO.setPhone(number);
            smsBO.setSendTime(TimeUtils.millis2String(date));
            list.add(smsBO);
        }
        cur.close();
        return list;
    }


    /**
     * 根据短信号码查询通讯录里的备注
     */
    private static String getContactByAddr(Context context, String mAddress) {
        Uri personUri = Uri.withAppendedPath(
                ContactsContract.PhoneLookup.CONTENT_FILTER_URI, mAddress);
        Cursor cur = context.getContentResolver().query(personUri,
                new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME},
                null, null, null);
        if (cur == null) {
            return "-";
        }
        if (cur.moveToFirst()) {
            int nameIdx = cur.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME);
            String mName = cur.getString(nameIdx);
            cur.close();
            return mName;
        }
        return "-";
    }

}
