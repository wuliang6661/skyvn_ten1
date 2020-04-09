package com.skyvn.ten.util;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;

import com.blankj.utilcode.util.AppUtils;
import com.skyvn.ten.R;
import com.skyvn.ten.api.HttpResultSubscriber;
import com.skyvn.ten.api.HttpServerImpl;
import com.skyvn.ten.bean.VersionBO;
import com.skyvn.ten.widget.AlertDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2019/6/2111:14
 * desc   : App检查更新的工具类
 * version: 1.0
 */
public class UpdateUtils {

    private Context context;

    public void checkUpdate(Context context, onUpdateListener listener) {
        this.context = context;
        HttpServerImpl.checkUpdate().subscribe(new HttpResultSubscriber<VersionBO>() {
            @Override
            public void onSuccess(VersionBO s) {
                if (s == null) {
                    return;
                }
                if (s.getVersion() > AppUtils.getAppVersionCode()) {
                    if (s.getForceUpdate() == 1) { //强制更新
                        showDownloadDialog(s.getDownloadUrl());
                    } else {
                        new AlertDialog(context).builder().setGone().setTitle(context.getString(R.string.faxianxinbanben))
                                .setMsg(context.getString(R.string.shifoulijigengxin))
                                .setNegativeButton(context.getResources().getString(R.string.cancle), null)
                                .setPositiveButton(context.getResources().getString(R.string.commit), v -> {
                                    showDownloadDialog(s.getDownloadUrl());
                                }).show();
                    }
                } else {
                    if (listener != null) {
                        listener.noUpdate();
                    }
                }
            }

            @Override
            public void onFiled(String message) {
                ToastManager.showShort(message);
            }
        });
    }


    public interface onUpdateListener {
        void noUpdate();
    }

    private boolean mIsCancel = false;
    private String version = "bbyiot_android.apk";


    private ProgressDialog progressDialog;

    /*
     * 显示正在下载对话框
     */
    private void showDownloadDialog(String url) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMessage(context.getString(R.string.zhengzaijiazai));
        progressDialog.setCancelable(false);//不能手动取消下载进度对话框
        progressDialog.setProgressNumberFormat("");
        progressDialog.show();

        // 下载文件
        downloadAPK(url);
    }

    /* 开启新线程下载apk文件
     */
    public void downloadAPK(String url) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mIsCancel = false;
                    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                        File dir = new File(FILE_APK_PATH);
                        if (!dir.exists()) {
                            dir.mkdirs();
                        }
                        // 下载文件
                        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
                        conn.connect();
                        InputStream is = conn.getInputStream();
                        int length = conn.getContentLength();
                        progressDialog.setMax(length);

                        File apkFile = new File(FILE_APK_PATH, version);
                        FileOutputStream fos = new FileOutputStream(apkFile);

                        int count = 0;
                        byte[] buffer = new byte[1024];

                        while (!mIsCancel) {
                            int numread = is.read(buffer);
                            count += numread;
                            Message message = new Message();
                            message.what = 0x11;
                            message.obj = count;
                            handler.sendMessage(message);
                            // 下载完成
                            if (numread < 0) {
                                handler.sendEmptyMessage(0x22);
                                AppUtils.installApp(apkFile);
                                break;
                            }
                            fos.write(buffer, 0, numread);
                        }
                        fos.close();
                        is.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x11:
                    int progress = (int) msg.obj;
                    progressDialog.setProgress(progress);
                    break;
                case 0x22:
                    progressDialog.dismiss();
                    break;
            }
        }
    };


    /**
     * 更新apk存放地址
     */
    public final static String FILE_APK_PATH = getFolderPath() + "/APK";

    /**
     * sdb/SynwayOSC
     */
    public static final String getFolderPath() {
        return Environment.getExternalStorageDirectory().getPath()
                + "/hitClient";
    }
}
