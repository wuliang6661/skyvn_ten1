package com.skyvn.ten.view;

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
import android.widget.Button;
import android.widget.EditText;
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
import com.skyvn.ten.bean.LablesBO;
import com.skyvn.ten.util.AuthenticationUtils;
import com.skyvn.ten.util.PhotoFromPhotoAlbum;
import com.skyvn.ten.util.UpdateFileUtils;
import com.skyvn.ten.widget.AlertDialog;
import com.skyvn.ten.widget.PopXingZhi;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2020/1/1313:20
 * desc   : 公司信息
 * version: 1.0
 */
public class CommonMsgActivity extends BaseActivity implements ActionSheet.OnActionSheetSelected {

    @BindView(R.id.bt_login)
    Button btLogin;
    @BindView(R.id.edit_common_name)
    EditText editCommonName;
    @BindView(R.id.edit_common_address)
    EditText editCommonAddress;
    @BindView(R.id.edit_common_phone)
    EditText editCommonPhone;
    @BindView(R.id.edit_zhicheng)
    TextView editZhicheng;
    @BindView(R.id.zhicheng_layout)
    LinearLayout zhichengLayout;
    @BindView(R.id.edit_shouru)
    TextView editShouru;
    @BindView(R.id.shouru_layout)
    LinearLayout shouruLayout;
    @BindView(R.id.edit_shichang_time)
    TextView editShichangTime;
    @BindView(R.id.shichang_layout)
    LinearLayout shichangLayout;
    @BindView(R.id.add_img)
    LinearLayout addImg;
    @BindView(R.id.gongsi_img_hint)
    TextView gongsiImgHint;
    @BindView(R.id.gongsi_img)
    ImageView gongsiImg;
    @BindView(R.id.jump_skip)
    TextView jumpSkip;

    private File cameraSavePath;//拍照照片路径
    private Uri uri;
    private String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private String gongsiImgUrl;

    @Override
    protected int getLayout() {
        return R.layout.act_common_msg;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout imageView = findViewById(R.id.back);
        imageView.setVisibility(View.VISIBLE);
        setTitleText(getResources().getString(R.string.gongsixinxi));
        rightButton();

//        getPermission();
        cameraSavePath = new File(Environment.getExternalStorageDirectory().getPath() + "/" +
                System.currentTimeMillis() + ".jpg");

        int needStatus = getIntent().getIntExtra("needStatus", 1);
        if (needStatus == 0) {
            jumpSkip.setVisibility(View.VISIBLE);
        } else {
            jumpSkip.setVisibility(View.GONE);
        }
    }


    @OnClick(R.id.jump_skip)
    public void jump() {
        HttpServerImpl.jumpAuth(AuthenticationUtils.COMMON_MSG_PAGE).subscribe(new HttpResultSubscriber<AttentionSourrssBO>() {
            @Override
            public void onSuccess(AttentionSourrssBO s) {
                AuthenticationUtils.goAuthNextPage(s.getCode(), s.getNeedStatus(), CommonMsgActivity.this);
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }


    @OnClick(R.id.back)
    public void back() {
        HttpServerImpl.getBackMsg(AuthenticationUtils.COMMON_MSG_PAGE).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                new AlertDialog(CommonMsgActivity.this).builder().setGone().setTitle(getResources().getString(R.string.tishi))
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


    @OnClick({R.id.zhicheng_layout, R.id.shouru_layout, R.id.shichang_layout})
    public void clickLables(View view) {
        switch (view.getId()) {
            case R.id.zhicheng_layout:
                getLables(9);
                break;
            case R.id.shouru_layout:
                getLables(10);
                break;
            case R.id.shichang_layout:
                getLables(11);
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
                case 9:
                    editZhicheng.setText(item);
                    break;
                case 10:
                    editShouru.setText(item);
                    break;
                case 11:
                    editShichangTime.setText(item);
                    break;
            }
        });
        popXingZhi.showAtLocation(getWindow().getDecorView());
    }


    @OnClick(R.id.add_img)
    public void addImage() {
//        ActionSheet.showSheet(this, this, null);
        checkPermissions();
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



    /**
     * 提交
     */
    @OnClick(R.id.bt_login)
    public void commitCommon() {
        String strCommonName = editCommonName.getText().toString().trim();
        if (StringUtils.isEmpty(strCommonName)) {
            showToast(getResources().getString(R.string.common_name_toast));
            return;
        }
        String strCommonAddress = editCommonAddress.getText().toString().trim();
        if (StringUtils.isEmpty(strCommonAddress)) {
            showToast(getResources().getString(R.string.common_address_toast));
            return;
        }
        String strCommonPhone = editCommonPhone.getText().toString().trim();
        if (StringUtils.isEmpty(strCommonPhone)) {
            showToast(getResources().getString(R.string.email_hint_toast));
            return;
        }
        String strZhiCheng = editZhicheng.getText().toString().trim();
        if (StringUtils.isEmpty(strZhiCheng)) {
            showToast(getResources().getString(R.string.common_zhicheng_toast));
            return;
        }
        String strShouru = editShouru.getText().toString().trim();
        if (StringUtils.isEmpty(strShouru)) {
            showToast(getResources().getString(R.string.common_shouru_toast));
            return;
        }
        String strShichang = editShichangTime.getText().toString().trim();
        if (StringUtils.isEmpty(strShichang)) {
            showToast(getResources().getString(R.string.common_shichang_toast));
            return;
        }
        if (StringUtils.isEmpty(gongsiImgUrl)) {
            showToast(getResources().getString(R.string.common_img_toast));
            return;
        }
        HttpServerImpl.commitComplanyInfo(strCommonName, strCommonAddress, strCommonPhone, strShouru,
                strZhiCheng, strShichang, gongsiImgUrl).subscribe(new HttpResultSubscriber<AttentionSourrssBO>() {
            @Override
            public void onSuccess(AttentionSourrssBO s) {
                showToast(getResources().getString(R.string.commit_sourss_toast));
                AuthenticationUtils.goAuthNextPage(s.getCode(), s.getNeedStatus(), CommonMsgActivity.this);
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }


    //获取权限
    private void getPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA
                    }, 1);

        }
    }

    //激活相册操作
    private void goPhotoAlbum() {
        getPermission();
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 2);
    }

    //激活相机操作
    private void goCamera() {
        getPermission();
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
//                gongsiImgUrl = s;
//                Glide.with(CommonMsgActivity.this).load(s).into(gongsiImg);
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
                gongsiImgUrl = s;
                handler.sendEmptyMessage(0x11);
            }

            @Override
            public void callError(String message) {
                handler.sendEmptyMessage(0x22);
                showToast(message);
            }
        });
        utils.updateFile(8, file.getAbsolutePath());
    }


    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x11:
                    stopProgress();
                    Glide.with(CommonMsgActivity.this).load(gongsiImgUrl).into(gongsiImg);
                    break;
                case 0x22:
                    stopProgress();
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}
