package com.skyvn.ten.view;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.skyvn.ten.R;
import com.skyvn.ten.api.HttpResultSubscriber;
import com.skyvn.ten.api.HttpServerImpl;
import com.skyvn.ten.base.BaseActivity;
import com.skyvn.ten.base.MyApplication;
import com.skyvn.ten.bean.CodeImgBO;
import com.skyvn.ten.bean.LoginSuressBO;
import com.skyvn.ten.util.BitmapUtil;
import com.skyvn.ten.util.GPSUtils;
import com.skyvn.ten.util.MyLocationUtil;
import com.skyvn.ten.view.main.MainActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2020/1/99:34
 * desc   :
 * version: 1.0
 */
public class LoginActivity extends BaseActivity {

    @BindView(R.id.et_photo)
    EditText etPhoto;
    @BindView(R.id.input_layout_phone)
    TextInputLayout inputLayoutPhone;
    @BindView(R.id.et_image_verfication)
    EditText etImageVerfication;
    @BindView(R.id.input_layout_image_verification)
    TextInputLayout inputLayoutImageVerification;
    @BindView(R.id.image_verfication)
    ImageView imageVerfication;
    @BindView(R.id.et_verfication)
    EditText etVerfication;
    @BindView(R.id.input_layout_verfication)
    TextInputLayout inputLayoutVerfication;
    @BindView(R.id.get_verfication)
    TextView getVerfication;
    @BindView(R.id.bt_login)
    Button btLogin;

    private CodeImgBO codeImgBO;

    @Override
    protected int getLayout() {
        return R.layout.act_login;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitleText(getResources().getString(R.string.login_txt));
        inputLayoutPhone.setErrorEnabled(false);
        inputLayoutImageVerification.setErrorEnabled(false);
        inputLayoutVerfication.setErrorEnabled(false);
        setListener();

        requestPermission();
        checkPermissions();
        getCodeImg();
//        if (BuildConfig.DEBUG) {
//            etPhoto.setText("15151977426");
//            // 15726818192
//            etVerfication.setText("111111");
//            etImageVerfication.setText("ffff");
//        }
    }


    /**
     * 错误校验
     */
    private void setListener() {
        etImageVerfication.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (StringUtils.isEmpty(charSequence) || charSequence.length() != 4) {
                    inputLayoutImageVerification.setError(getResources().getString(R.string.input_image_verfication_error));
                    inputLayoutImageVerification.setErrorEnabled(true);
                } else {
                    inputLayoutImageVerification.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        etPhoto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (StringUtils.isEmpty(charSequence) || (charSequence.length() != 10 && charSequence.length() != 11)) {
                    inputLayoutPhone.setError(getResources().getString(R.string.input_phone_error));
                    inputLayoutPhone.setErrorEnabled(true);
                } else {
                    inputLayoutPhone.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        etVerfication.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (StringUtils.isEmpty(charSequence) || charSequence.length() != 6) {
                    inputLayoutVerfication.setError(getResources().getString(R.string.input_verfication_error));
                    inputLayoutVerfication.setErrorEnabled(true);
                } else {
                    inputLayoutVerfication.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


    @OnClick(R.id.bt_login)
    public void login() {
        synLogin();
    }


    @OnClick(R.id.image_verfication)
    public void clickImage() {
        getCodeImg();
    }

    @OnClick(R.id.get_verfication)
    public void get_verfication() {
        getVersionCode();
    }


    private void requestPermission() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.READ_PHONE_STATE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION
                    }, 1);

        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    // 权限被用户同意。
                    // 执形我们想要的操作
                    checkPermissions();
                } else {
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                            || !ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                        //提示用户前往设置界面自己打开权限
//                        Toast.makeText(this, "请前往设置界面打开权限", Toast.LENGTH_SHORT).show();
                        return;
                    }

                }
            }
        }
    }

    /**
     * 获取图片验证码
     */
    private void getCodeImg() {
        HttpServerImpl.getCodeImg().subscribe(new HttpResultSubscriber<CodeImgBO>() {
            @Override
            public void onSuccess(CodeImgBO s) {
                codeImgBO = s;
                imageVerfication.setImageBitmap(BitmapUtil.base64ToBitMap(s.getImgString()));
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }


    /**
     * 获取短线验证码
     */
    private void getVersionCode() {
        String strPhone = etPhoto.getText().toString().trim();
        String strCodeImg = etImageVerfication.getText().toString().trim();
        if (StringUtils.isEmpty(strPhone) || (strPhone.length() != 10 && strPhone.length() != 11)) {
            inputLayoutPhone.setError(getResources().getString(R.string.input_phone_error));
            inputLayoutPhone.setErrorEnabled(true);
            return;
        }
        if (StringUtils.isEmpty(strCodeImg) || strCodeImg.length() != 4) {
            inputLayoutImageVerification.setError(getResources().getString(R.string.input_image_verfication_error));
            inputLayoutImageVerification.setErrorEnabled(true);
            return;
        }
        if (codeImgBO == null) {
            getCodeImg();
            showToast(getResources().getString(R.string.login_hint));
            return;
        }
        showProgress();
        HttpServerImpl.getVerificationCode(codeImgBO.getKey(), strPhone, strCodeImg)
                .subscribe(new HttpResultSubscriber<String>() {
                    @Override
                    public void onSuccess(String s) {
                        stopProgress();
                        getVerfication.setEnabled(false);
                        timer.start();
                    }

                    @Override
                    public void onFiled(String message) {
                        stopProgress();
                        showToast(message);
                    }
                });
    }


    CountDownTimer timer = new CountDownTimer(60000, 1000) {
        @Override
        public void onTick(long l) {
            getVerfication.setText((l / 1000) + "S");
        }

        @Override
        public void onFinish() {
            getVerfication.setEnabled(true);
            getVerfication.setText(getResources().getString(R.string.reset_get_verification));
        }
    };


    @Override
    protected void onPause() {
        super.onPause();
        timer.cancel();
    }

    /**
     * 登录请求
     */
    private void synLogin() {
        String strPhone = etPhoto.getText().toString().trim();
        String strCodeImg = etImageVerfication.getText().toString().trim();
        String strVersition = etVerfication.getText().toString().trim();
        if (StringUtils.isEmpty(strPhone) || (strPhone.length() != 10 && strPhone.length() != 11)) {
            inputLayoutPhone.setError(getResources().getString(R.string.input_phone_error));
            inputLayoutPhone.setErrorEnabled(true);
            return;
        }
        if (StringUtils.isEmpty(strCodeImg) || strCodeImg.length() != 4) {
            inputLayoutImageVerification.setError(getResources().getString(R.string.input_image_verfication_error));
            inputLayoutImageVerification.setErrorEnabled(true);
            return;
        }
        if (StringUtils.isEmpty(strVersition) || strVersition.length() != 6) {
            inputLayoutVerfication.setError(getResources().getString(R.string.input_verfication_error));
            inputLayoutVerfication.setErrorEnabled(true);
        }
        checkPermissions();
        showProgress();
        HttpServerImpl.loginUser(loginLatitude, loginLongitude, strPhone, strVersition).subscribe(new HttpResultSubscriber<LoginSuressBO>() {
            @Override
            public void onSuccess(LoginSuressBO s) {
                MyApplication.userBO = s;
                MyApplication.token = s.getToken();
                MyApplication.spUtils.put("token", s.getToken());
                stopProgress();
                //清空任务栈确保当前打开activity为前台任务栈栈顶
//                Intent it = new Intent(LoginActivity.this, MainActivity.class);
//                it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(it);
//                finish();
                gotoActivity(MainActivity.class, true);
            }

            @Override
            public void onFiled(String message) {
                stopProgress();
                showToast(message);
            }
        });
    }


    private double loginLatitude;
    private double loginLongitude;

    /**
     * Detect camera authorization
     */
    public void checkPermissions() {
        if (allPermissionsGranted()) {
            GPSUtils.getInstance(getApplicationContext()).getLngAndLat(new GPSUtils.OnLocationResultListener() {
                @Override
                public void onLocationResult(Location location) {
                    loginLatitude = location.getLatitude();
                    loginLongitude = location.getLongitude();
                    LogUtils.e("loginLatitude == " + loginLatitude + "   loginLongitude ==  " + loginLongitude);
                }

                @Override
                public void OnLocationChange(Location location) {
                    loginLatitude = location.getLatitude();
                    loginLongitude = location.getLongitude();
                    LogUtils.e("loginLatitude == " + loginLatitude + "   loginLongitude ==  " + loginLongitude);
                }
            });
//            LocationManager lm = (LocationManager) Utils.getApp().getSystemService(Context.LOCATION_SERVICE);
//            @SuppressLint("MissingPermission") Location mLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//            if (mLocation == null) {
//                loginLatitude = 0;
//                loginLongitude = 0;
//            } else {
//                loginLatitude = mLocation.getLatitude();
//                loginLongitude = mLocation.getLongitude();
//            }
        } else {
            loginLatitude = 0;
            loginLongitude = 0;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        GPSUtils.getInstance(getApplicationContext()).removeListener();
    }

    public String[] getRequiredPermissions() {
        return new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
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
}
