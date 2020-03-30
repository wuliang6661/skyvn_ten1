package com.skyvn.ten.view;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.blankj.utilcode.util.FileUtils;
import com.skyvn.ten.R;
import com.skyvn.ten.api.HttpResultSubscriber;
import com.skyvn.ten.api.HttpServerImpl;
import com.skyvn.ten.base.BaseActivity;
import com.skyvn.ten.bean.AttentionSourrssBO;
import com.skyvn.ten.util.AuthenticationUtils;
import com.skyvn.ten.util.BitmapUtil;
import com.skyvn.ten.util.UpdateFileUtils;

import butterknife.BindView;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2020/1/159:52
 * desc   :  刷脸成功页面
 * version: 1.0
 */
public class LiveAuthSouressActivity extends BaseActivity {


    @BindView(R.id.down_time_text)
    TextView downTimeText;

    private String base64;

    private String imageFilePath;

    @Override
    protected int getLayout() {
        return R.layout.live_auth_souress;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText(getResources().getString(R.string.shuanianrenzheng));
        rightButton();

        imageFilePath = Environment.getExternalStorageDirectory().getPath() + "/faceImg.jpg";
        base64 = getIntent().getExtras().getString("base64");
        try {
            BitmapUtil.decoderBase64File(base64, imageFilePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        timer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        timer.cancel();
    }

    CountDownTimer timer = new CountDownTimer(3000, 1000) {
        @Override
        public void onTick(long l) {
            downTimeText.setText((l / 1000) + "");
        }

        @Override
        public void onFinish() {
            showProgress();
            UpdateFileUtils updateFileUtils = new UpdateFileUtils();
            updateFileUtils.setListener(new UpdateFileUtils.OnCallBackListener() {
                @Override
                public void call(String s) {
                    updateLiveAuth(s);
                }

                @Override
                public void callError(String message) {
                    showToast(message);
                    stopProgress();
                    FileUtils.deleteFile(imageFilePath);
                }
            });
            updateFileUtils.updateFile(2, imageFilePath);
        }
    };


    private void updateLiveAuth(String url) {
        HttpServerImpl.addClientActiveAuth(url).subscribe(new HttpResultSubscriber<AttentionSourrssBO>() {
            @Override
            public void onSuccess(AttentionSourrssBO s) {
                stopProgress();
                showToast(getResources().getString(R.string.commit_sourss_toast));
                FileUtils.deleteFile(imageFilePath);
                AuthenticationUtils.goAuthNextPage(s.getCode(), s.getNeedStatus(), LiveAuthSouressActivity.this);
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
                stopProgress();
                FileUtils.deleteFile(imageFilePath);
                finish();
            }
        });
    }
}
