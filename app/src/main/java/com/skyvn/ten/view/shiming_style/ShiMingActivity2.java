package com.skyvn.ten.view.shiming_style;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.guoqi.actionsheet.ActionSheet;
import com.skyvn.ten.R;
import com.skyvn.ten.api.HttpResultSubscriber;
import com.skyvn.ten.api.HttpServerImpl;
import com.skyvn.ten.base.BaseActivity;
import com.skyvn.ten.bean.AttentionSourrssBO;
import com.skyvn.ten.util.AuthenticationUtils;
import com.skyvn.ten.util.PhotoFromPhotoAlbum;
import com.skyvn.ten.util.UpdateFileUtils;
import com.skyvn.ten.widget.AlertDialog;

import java.io.File;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 风格2的身份证认证
 */
public class ShiMingActivity2 extends BaseActivity implements ActionSheet.OnActionSheetSelected {

    @BindView(R.id.id_card_font)
    ImageView idCardFont;
    @BindView(R.id.add_img)
    LinearLayout addImg;
    @BindView(R.id.id_card_back)
    ImageView idCardBack;
    @BindView(R.id.add_img1)
    LinearLayout addImg1;
    @BindView(R.id.jump_skip)
    TextView jumpSkip;

    private File cameraSavePath;//拍照照片路径
    private Uri uri;
    private String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private String idCardFontUrl;
    private String idCardBackUrl;

    private int selectIdCardType = 0;  //默认是正面

    @Override
    protected int getLayout() {
        return R.layout.shiming_layout2;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout imageView = findViewById(R.id.back);
        imageView.setVisibility(View.VISIBLE);
        setTitleText(getResources().getString(R.string.shenfenzhengyanzheng));
        rightButton();

        int needStatus = getIntent().getIntExtra("needStatus", 1);
        if (needStatus == 0) {
            jumpSkip.setVisibility(View.VISIBLE);
        } else {
            jumpSkip.setVisibility(View.GONE);
        }

        cameraSavePath = new File(Environment.getExternalStorageDirectory().getPath() + "/" +
                System.currentTimeMillis() + ".jpg");
    }


    @OnClick(R.id.jump_skip)
    public void jump() {
        HttpServerImpl.jumpAuth(AuthenticationUtils.ID_CARD).subscribe(new HttpResultSubscriber<AttentionSourrssBO>() {
            @Override
            public void onSuccess(AttentionSourrssBO s) {
                AuthenticationUtils.goAuthNextPage(s.getCode(), s.getNeedStatus(), ShiMingActivity2.this);
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }

    @OnClick(R.id.back)
    public void back() {
        HttpServerImpl.getBackMsg(AuthenticationUtils.ID_CARD).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                new AlertDialog(ShiMingActivity2.this).builder().setGone().setTitle(getResources().getString(R.string.tishi))
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


    @OnClick({R.id.add_img, R.id.add_img1})
    public void clickBirthDay(View view) {
        switch (view.getId()) {
            case R.id.add_img:
                selectIdCardType = 0;
//                ActionSheet.showSheet(this, this, null);
                checkPermissions();
                break;
            case R.id.add_img1:
                selectIdCardType = 1;
//                ActionSheet.showSheet(this, this, null);
                checkPermissions();
                break;
        }
    }

    @OnClick(R.id.bt_login)
    public void commitShiMing() {
        if (StringUtils.isEmpty(idCardFontUrl)) {
            showToast(getResources().getString(R.string.wanshan_toast));
            return;
        }
        if (StringUtils.isEmpty(idCardBackUrl)) {
            showToast(getResources().getString(R.string.wanshan_toast));
            return;
        }
        HttpServerImpl.addClientIdcardAuth(idCardFontUrl, idCardBackUrl)
                .subscribe(new HttpResultSubscriber<AttentionSourrssBO>() {
                    @Override
                    public void onSuccess(AttentionSourrssBO s) {
                        showToast(getResources().getString(R.string.commit_sourss_toast));
                        AuthenticationUtils.goAuthNextPage(s.getCode(), s.getNeedStatus(), ShiMingActivity2.this);
                    }

                    @Override
                    public void onFiled(String message) {
                        showToast(message);
                    }
                });
    }


    private boolean allPermissionsGranted() {
        for (String permission : getRequiredPermissions()) {
            if (ContextCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }


    /**
     * Detect camera authorization
     */
    public void checkPermissions() {
        if (allPermissionsGranted()) {
            onPermissionGranted();
        } else {
            ActivityCompat.requestPermissions(this, getRequiredPermissions(), 0x11);
        }
    }

    public String[] getRequiredPermissions() {
        return new String[]{Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0x11) {
            //已授权
            if (allGranted(grantResults)) {
                onPermissionGranted();
            } else {
                onPermissionRefused();
            }
        }
    }

    /**
     * Denied camera permissions
     */
    public void onPermissionRefused() {
        new android.support.v7.app.AlertDialog.Builder(this).setMessage(getString(R.string.liveness_no_camera_permission)).setPositiveButton(getString(R.string.liveness_perform), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).create().show();
    }

    private boolean allGranted(int[] grantResults) {
        boolean hasPermission = true;
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                hasPermission = false;
            }
        }
        return hasPermission;
    }

    /**
     * Got camera permissions
     */
    public void onPermissionGranted() {
        ActionSheet.showSheet(this, this, null);
    }


    //激活相册操作
    private void goPhotoAlbum() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 2);
    }

    //激活相机操作
    private void goCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(Objects.requireNonNull(this), "com.skyvn.ten.fileprovider", cameraSavePath);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            uri = Uri.fromFile(cameraSavePath);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, 1);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        String photoPath;
        if (requestCode == 1 && resultCode == RESULT_OK) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                photoPath = String.valueOf(cameraSavePath);
            } else {
                photoPath = uri.getEncodedPath();
            }
            Log.d("拍照返回图片路径:", photoPath);
            showProgress();
            updateFile(new File(Objects.requireNonNull(photoPath)));
        } else if (requestCode == 2 && resultCode == RESULT_OK) {
            photoPath = PhotoFromPhotoAlbum.getRealPathFromUri(this, data.getData());
            showProgress();
            updateFile(new File(photoPath));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(int whichButton) {
        switch (whichButton) {
            case ActionSheet.CHOOSE_PICTURE:
                //相册
                goPhotoAlbum();
                break;
            case ActionSheet.TAKE_PICTURE:
                //拍照
                goCamera();
                break;
            case ActionSheet.CANCEL:
                //取消
                break;
        }
    }


    /**
     * 上传文件
     */
    private void updateFile(File file) {
//        showProgress();
//        HttpServerImpl.updateFile(file).subscribe(new HttpResultSubscriber<String>() {
//            @Override
//            public void onSuccess(String s) {
//                stopProgress();
//                if (selectIdCardType == 0) {
//                    idCardFontUrl = s;
//                    Glide.with(ShiMingActivity2.this).load(s).into(idCardFont);
//                } else {
//                    idCardBackUrl = s;
//                    Glide.with(ShiMingActivity2.this).load(s).into(idCardBack);
//                }
//            }
//
//            @Override
//            public void onFiled(String message) {
//                stopProgress();
//                showToast(message);
//            }
//        });
        UpdateFileUtils utils = new UpdateFileUtils();
        utils.setListener(new UpdateFileUtils.OnCallBackListener() {
            @Override
            public void call(String s) {
                if (selectIdCardType == 0) {
                    idCardFontUrl = s;
                } else {
                    idCardBackUrl = s;
                }
                handler.sendEmptyMessage(0x11);
            }

            @Override
            public void callError(String message) {
                handler.sendEmptyMessage(0x22);
                showToast(message);
            }
        });
        utils.updateFile(0, file.getAbsolutePath());
    }


    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x11:
                    stopProgress();
                    if (selectIdCardType == 0) {
                        Glide.with(ShiMingActivity2.this).load(idCardFontUrl).into(idCardFont);
                    } else {
                        Glide.with(ShiMingActivity2.this).load(idCardBackUrl).into(idCardBack);
                    }
                    break;
                case 0x22:
                    stopProgress();
                    break;
            }
        }
    };
}
