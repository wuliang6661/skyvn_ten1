package com.skyvn.ten.view.shiming_style;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
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
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.bumptech.glide.Glide;
import com.guoqi.actionsheet.ActionSheet;
import com.skyvn.ten.R;
import com.skyvn.ten.api.HttpResultSubscriber;
import com.skyvn.ten.api.HttpServerImpl;
import com.skyvn.ten.base.BaseActivity;
import com.skyvn.ten.bean.AttentionSourrssBO;
import com.skyvn.ten.util.AuthenticationUtils;
import com.skyvn.ten.util.PhotoFromPhotoAlbum;
import com.skyvn.ten.util.TextChangedListener;
import com.skyvn.ten.util.UpdateFileUtils;
import com.skyvn.ten.widget.AlertDialog;
import com.skyvn.ten.widget.PopXingZhi;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2020/1/1315:48
 * desc   : 实名认证
 * version: 1.0
 */
public class ShiMingActivity extends BaseActivity implements ActionSheet.OnActionSheetSelected {

    @BindView(R.id.jump_skip)
    TextView jumpSkip;
    @BindView(R.id.bt_login)
    Button btLogin;
    @BindView(R.id.edit_user_name)
    EditText editUserName;
    @BindView(R.id.edit_user_idcard)
    EditText editUserIdcard;
    @BindView(R.id.edit_birthday)
    TextView editBirthday;
    @BindView(R.id.birthday_layout)
    LinearLayout birthdayLayout;
    @BindView(R.id.edit_sex)
    TextView editSex;
    @BindView(R.id.sex_layout)
    LinearLayout sexLayout;
    @BindView(R.id.id_card_font)
    ImageView idCardFont;
    @BindView(R.id.add_img)
    LinearLayout addImg;
    @BindView(R.id.id_card_back)
    ImageView idCardBack;
    @BindView(R.id.add_img1)
    LinearLayout addImg1;

    TimePickerView pvTime;
    @SuppressLint("SimpleDateFormat")
    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    private File cameraSavePath;//拍照照片路径
    private Uri uri;
    private String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private String idCardFontUrl;
    private String idCardBackUrl;

    private int selectIdCardType = 0;  //默认是正面

    private int selectSex = 0; // 性别默认是男


    @Override
    protected int getLayout() {
        return R.layout.shiming_layout;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout imageView = findViewById(R.id.back);
        imageView.setVisibility(View.VISIBLE);
        setTitleText(getResources().getString(R.string.shimingrenzheng));
        rightButton();

        int needStatus = getIntent().getIntExtra("needStatus", 1);
        if (needStatus == 0) {
            jumpSkip.setVisibility(View.VISIBLE);
        } else {
            jumpSkip.setVisibility(View.GONE);
        }

        getPermission();
        cameraSavePath = new File(Environment.getExternalStorageDirectory().getPath() + "/" +
                System.currentTimeMillis() + ".jpg");
        TextChangedListener.StringWatcher(editUserName);
    }


    @OnClick(R.id.jump_skip)
    public void jump() {
        HttpServerImpl.jumpAuth(AuthenticationUtils.ID_CARD).subscribe(new HttpResultSubscriber<AttentionSourrssBO>() {
            @Override
            public void onSuccess(AttentionSourrssBO s) {
                AuthenticationUtils.goAuthNextPage(s.getCode(), s.getNeedStatus(), ShiMingActivity.this);
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
                new AlertDialog(ShiMingActivity.this).builder().setGone().setTitle(getResources().getString(R.string.tishi))
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

    @OnClick({R.id.birthday_layout, R.id.sex_layout, R.id.add_img, R.id.add_img1})
    public void clickBirthDay(View view) {
        switch (view.getId()) {
            case R.id.birthday_layout:
                initTimePicker();
                break;
            case R.id.sex_layout:
                selectSex();
                break;
            case R.id.add_img:
                selectIdCardType = 0;
                ActionSheet.showSheet(this, this, null);
                break;
            case R.id.add_img1:
                selectIdCardType = 1;
                ActionSheet.showSheet(this, this, null);
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

    @OnClick(R.id.bt_login)
    public void commitShiMing() {
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
        if (StringUtils.isEmpty(idCardFontUrl)) {
            showToast(getResources().getString(R.string.wanshan_toast));
            return;
        }
        if (StringUtils.isEmpty(idCardBackUrl)) {
            showToast(getResources().getString(R.string.wanshan_toast));
            return;
        }
        if (strIdCard.length() != 9 && strIdCard.length() != 12) {
            showToast(getString(R.string.shenfenzheng_xianzhi));
            return;
        }
        HttpServerImpl.commitIdCard(strBirthDay, selectSex + "", idCardFontUrl, idCardBackUrl, strIdCard, strName)
                .subscribe(new HttpResultSubscriber<AttentionSourrssBO>() {
                    @Override
                    public void onSuccess(AttentionSourrssBO s) {
                        showToast(getResources().getString(R.string.commit_sourss_toast));
                        AuthenticationUtils.goAuthNextPage(s.getCode(), s.getNeedStatus(), ShiMingActivity.this);
                    }

                    @Override
                    public void onFiled(String message) {
                        showToast(message);
                    }
                });
    }



    //获取权限
    private boolean getPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA
                    }, 1);
            return false;
        } else {
            return true;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1://刚才的识别码
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {//用户同意权限,执行我们的操作
//                    goPhotoAlbum();
                } else {//用户拒绝之后,当然我们也可以弹出一个窗口,直接跳转到系统设置页面
                    Toast.makeText(this, R.string.weikaiqishexiangtouquanxian, Toast.LENGTH_LONG).show();
                }
                break;
            default:
                break;
        }
    }


    //激活相册操作
    private void goPhotoAlbum() {
        if (getPermission()) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, 2);
        }
    }

    //激活相机操作
    private void goCamera() {
        if (getPermission()) {
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
//                    Glide.with(ShiMingActivity.this).load(s).into(idCardFont);
//                } else {
//                    idCardBackUrl = s;
//                    Glide.with(ShiMingActivity.this).load(s).into(idCardBack);
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
                stopProgress();
                if (selectIdCardType == 0) {
                    idCardFontUrl = s;
                } else {
                    idCardBackUrl = s;
                }
                handler.sendEmptyMessage(0x11);
            }

            @Override
            public void callError(String message) {
                stopProgress();
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
            if (selectIdCardType == 0) {
                Glide.with(ShiMingActivity.this).load(idCardFontUrl).into(idCardFont);
            } else {
                Glide.with(ShiMingActivity.this).load(idCardBackUrl).into(idCardBack);
            }
        }
    };

}
