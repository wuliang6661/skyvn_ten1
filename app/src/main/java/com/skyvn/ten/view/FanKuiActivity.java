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
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.blankj.utilcode.util.StringUtils;
import com.guoqi.actionsheet.ActionSheet;
import com.skyvn.ten.R;
import com.skyvn.ten.api.HttpResultSubscriber;
import com.skyvn.ten.api.HttpServerImpl;
import com.skyvn.ten.base.BaseActivity;
import com.skyvn.ten.bean.ImageBO;
import com.skyvn.ten.util.PhotoFromPhotoAlbum;
import com.skyvn.ten.util.UpdateFileUtils;
import com.skyvn.ten.widget.AlertDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 反馈页面
 */
public class FanKuiActivity extends BaseActivity implements ActionSheet.OnActionSheetSelected {

    @BindView(R.id.edit_email)
    EditText editEmail;
    @BindView(R.id.edit_message)
    EditText editMessage;
    @BindView(R.id.image_recycle)
    RecyclerView imageRecycle;
    @BindView(R.id.bt_login)
    Button btLogin;

    private File cameraSavePath;//拍照照片路径
    private Uri uri;
    private String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    List<ImageBO> imageBOS;      //添加的图片列表

    @Override
    protected int getLayout() {
        return R.layout.act_fankui;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText(getResources().getString(R.string.my_fankui));
        rightButton();

//        getPermission();
        cameraSavePath = new File(Environment.getExternalStorageDirectory().getPath() + "/" +
                System.currentTimeMillis() + ".jpg");

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        gridLayoutManager.setSmoothScrollbarEnabled(true);
        imageRecycle.setHasFixedSize(true);
        imageRecycle.setLayoutManager(gridLayoutManager);

        imageBOS = new ArrayList<>();
        setImageAdapter();
    }


    @OnClick(R.id.bt_login)
    public void commit() {
        commitFanKui();
    }


    /**
     * 设置添加图片的适配器
     */
    private void setImageAdapter() {
        ImageAddAdapter addAdapter = new ImageAddAdapter(this, imageBOS);
        addAdapter.setListener(new ImageAddAdapter.onAddImageAdapterListener() {
            @Override
            public void addImage() {
                checkPermissions();
//                ActionSheet.showSheet(FanKuiActivity.this, FanKuiActivity.this, null);
            }

            @Override
            public void deleteImage(int position, ImageBO imageBO) {
                new AlertDialog(FanKuiActivity.this).builder().setGone().setMsg(getResources().getString(R.string.delete_img))
                        .setNegativeButton(getResources().getString(R.string.cancle), null)
                        .setPositiveButton(getResources().getString(R.string.commit), v -> removeImage(position)).show();
            }

            @Override
            public void editName(int position) {
            }
        });
        imageRecycle.setAdapter(addAdapter);
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
     * 删除照片
     */
    private void removeImage(int position) {
        imageBOS.remove(position);
        setImageAdapter();
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
//                ImageBO imageBO = new ImageBO();
//                imageBO.url = s;
//                imageBOS.add(imageBO);
//                setImageAdapter();
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
                ImageBO imageBO = new ImageBO();
                imageBO.url = s;
                imageBOS.add(imageBO);
                handler.sendEmptyMessage(0x11);
            }

            @Override
            public void callError(String message) {
                handler.sendEmptyMessage(0x22);
                showToast(message);
            }
        });
        utils.updateFile(9, file.getAbsolutePath());
    }


    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x11:
                    stopProgress();
                    setImageAdapter();
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

    /**
     * 提交反馈
     */
    private void commitFanKui() {
        String strContact = editEmail.getText().toString().trim();
        String strContent = editMessage.getText().toString().trim();
        if (StringUtils.isEmpty(strContent)) {
            showToast(getResources().getString(R.string.content_hint_toast));
            return;
        }
        StringBuilder images = new StringBuilder();
        String strImages = null;
        for (ImageBO imageBO : imageBOS) {
            images.append(imageBO.url).append(",");
        }
        if (images.length() > 0) {
            strImages = images.substring(0, images.length() - 1);
        }
        HttpServerImpl.addOperateApplicationFeedback(strContent, strContact, strImages)
                .subscribe(new HttpResultSubscriber<String>() {
                    @Override
                    public void onSuccess(String s) {
                        showToast(getResources().getString(R.string.commit_sourss_toast));
                        finish();
                    }

                    @Override
                    public void onFiled(String message) {
                        showToast(message);
                    }
                });
    }

}
