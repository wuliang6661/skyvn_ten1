package com.skyvn.ten.view.main.home_all_status;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.skyvn.ten.R;
import com.skyvn.ten.api.HttpResultSubscriber;
import com.skyvn.ten.api.HttpServerImpl;
import com.skyvn.ten.base.BaseFragment;
import com.skyvn.ten.base.MyApplication;
import com.skyvn.ten.bean.AttentionSourrssBO;
import com.skyvn.ten.bean.IndexBO;
import com.skyvn.ten.util.AuthenticationUtils;
import com.skyvn.ten.util.GPSUtils;
import com.skyvn.ten.view.ConfirmationActivity;
import com.skyvn.ten.view.KefuActivity;
import com.skyvn.ten.view.LoginActivity;
import com.skyvn.ten.widget.PopXingZhi;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 不同认证状态的fragment, 不同授信状态   1 和 5
 */
public class HomeAttentionFragment extends BaseFragment {

    @BindView(R.id.days_text)
    TextView daysText;
    @BindView(R.id.days_layout)
    LinearLayout daysLayout;
    @BindView(R.id.shouxin_shengyu_text)
    TextView shouxinShengyuText;
    @BindView(R.id.shouxin_shengyu_layout)
    LinearLayout shouxinShengyuLayout;
    @BindView(R.id.bt_login)
    Button btLogin;
    @BindView(R.id.hint_img)
    ImageView hintImg;
    @BindView(R.id.hint_text)
    TextView hintText;
    @BindView(R.id.hint_layout)
    LinearLayout hintLayout;
    Unbinder unbinder;
    @BindView(R.id.pay_num)
    TextView payNum;

    private IndexBO indexBO;

    private List<String> days;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fra_home_attention, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        indexBO = HomeAllFragment.indexBO;
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                showIndex();
            }
        });
        getDays();
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();

    }

    @OnClick(R.id.kefu_layout)
    public void clickKefu() {
//        if (StringUtils.isEmpty(MyApplication.token)) {
//            gotoActivity(LoginActivity.class, false);
//            return;
//        }
        gotoActivity(KefuActivity.class, false);
    }

    /**
     * 设置界面显示
     */
    private void showIndex() {
        if (indexBO.getLogin() == 0) {  //未登录
            hintLayout.setVisibility(View.GONE);
            daysLayout.setVisibility(View.VISIBLE);
            shouxinShengyuLayout.setVisibility(View.GONE);
            btLogin.setText(getString(R.string.huoquwodeedu));
            getPayNum();
            return;
        }
        switch (indexBO.getAuthStatus()) {   //认证状态
            case "0":   //未认证
                hintLayout.setVisibility(View.GONE);
                daysLayout.setVisibility(View.VISIBLE);
                shouxinShengyuLayout.setVisibility(View.GONE);
                btLogin.setText(getString(R.string.huoquwodeedu));
                getPayNum();
                break;
            case "1":   //认证中
                hintLayout.setVisibility(View.VISIBLE);
                daysLayout.setVisibility(View.VISIBLE);
                shouxinShengyuLayout.setVisibility(View.GONE);
                btLogin.setText(getString(R.string.jixurenzheng));
                hintImg.setImageResource(R.drawable.black_hint_img);
                hintText.setText(getString(R.string.home_attention_hint1));
                hintText.setTextColor(Color.parseColor("#666666"));
                getPayNum();
                break;
            case "3":   //授信失效
                hintLayout.setVisibility(View.VISIBLE);
                daysLayout.setVisibility(View.VISIBLE);
                shouxinShengyuLayout.setVisibility(View.GONE);
                btLogin.setText(getString(R.string.qurenzheng));
                hintImg.setImageResource(R.drawable.black_hint_img);
                hintText.setText(R.string.renzhengguoqi_hint);
                hintText.setTextColor(Color.parseColor("#666666"));
                getPayNum();
                break;
            case "2":    //通过
                if (indexBO.getOrderLoanVO() == null) {   //完成认证
                    if ("0".equals(indexBO.getQuota()) || StringUtils.isEmpty(indexBO.getQuota())) {
                        getPayNum();
                        hintLayout.setVisibility(View.VISIBLE);
                        daysLayout.setVisibility(View.VISIBLE);
                        shouxinShengyuLayout.setVisibility(View.GONE);
                        btLogin.setText(getString(R.string.qurenzheng));
                        hintImg.setImageResource(R.drawable.black_hint_img);
                        hintText.setText(R.string.yunyingshangshixiao_hint);
                        hintText.setTextColor(Color.parseColor("#666666"));
                    } else {
                        payNum.setText(indexBO.getQuota());
                        hintLayout.setVisibility(View.VISIBLE);
                        daysLayout.setVisibility(View.GONE);
                        shouxinShengyuLayout.setVisibility(View.VISIBLE);
                        btLogin.setText(getString(R.string.mashangshenqing));
                        shouxinShengyuText.setText(indexBO.getDays() + getString(R.string.danwei_tian));
                        hintImg.setImageResource(R.drawable.attention_sourss);
                        hintText.setText(getString(R.string.home_attention_hint2));
                        hintText.setTextColor(Color.parseColor("#FF5D2D"));
                    }
                } else {
                    switch (indexBO.getLoanStatus()) {   //订单状态
                        case "5":
                            payNum.setText(indexBO.getQuota());
                            hintLayout.setVisibility(View.VISIBLE);
                            daysLayout.setVisibility(View.GONE);
                            shouxinShengyuLayout.setVisibility(View.VISIBLE);
                            btLogin.setText(getString(R.string.mashangshenqing));
                            shouxinShengyuText.setText(indexBO.getDays() + getString(R.string.danwei_tian));
                            hintImg.setImageResource(R.drawable.attention_sourss);
                            hintText.setText(getString(R.string.home_attention_hint2));
                            hintText.setTextColor(Color.parseColor("#FF5D2D"));
                            break;
                    }
                }
                break;
        }
    }


    /**
     * 获取首页借款范围
     */
    private void getPayNum() {
        HttpServerImpl.getIndexFanwei().subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                payNum.setText(s == null ? "" : s);
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }


    /**
     * 获取首页天数
     */
    private void getDays() {
        HttpServerImpl.getIndexDays().subscribe(new HttpResultSubscriber<List<String>>() {
            @Override
            public void onSuccess(List<String> s) {
                if (s == null || s.isEmpty()) {
                    return;
                }
                days = s;
                daysText.setText(s.get(0));
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }

    /**
     * 去认证
     */
    public void clickGoAttention() {
        showProgress();
        HttpServerImpl.getFirstAuth().subscribe(new HttpResultSubscriber<AttentionSourrssBO>() {
            @Override
            public void onSuccess(AttentionSourrssBO s) {
                stopProgress();
                AuthenticationUtils.goAuthNextPageByHome(s.getCode(), s.getNeedStatus(), false, getActivity());
            }

            @Override
            public void onFiled(String message) {
                stopProgress();
                showToast(message);
            }
        });
    }


    @OnClick(R.id.bt_login)
    public void commit() {
        if (StringUtils.isEmpty(MyApplication.token)) {
            gotoActivity(LoginActivity.class, false);
            return;
        }
        switch (indexBO.getAuthStatus()) {   //认证状态
            case "0":   //未认证
                if (StringUtils.isEmpty(MyApplication.token)) {
                    gotoActivity(LoginActivity.class, false);
                } else {
                    clickGoAttention();
                }
                break;
            case "1":   //认证中
                clickGoAttention();
                break;
            case "3":   //授信失效
                clickGoAttention();
                break;
            case "2":    //通过
                if (indexBO.getOrderLoanVO() == null) {   //完成认证
                    if ("0".equals(indexBO.getQuota()) || StringUtils.isEmpty(indexBO.getQuota())) {  //运营商认证失效
                        clickGoAttention();
                    } else {
//                        gotoActivity(ConfirmationActivity.class, false);
                        requestPermission();
                    }
                } else {
                    switch (indexBO.getLoanStatus()) {   //订单状态
                        case "5":
//                            gotoActivity(ConfirmationActivity.class, false);
                            requestPermission();
                            break;
                    }
                }
                break;
        }
    }


    private void requestPermission() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                ) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION
                    }, 1);

        } else {
            checkPermissions();
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
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                            || !ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)) {
                        //提示用户前往设置界面自己打开权限
//                        Toast.makeText(this, "请前往设置界面打开权限", Toast.LENGTH_SHORT).show();
                        showToast(getString(R.string.qingdakaigps));
                        return;
                    }

                }
            }
        }
    }


    private double loginLatitude;
    private double loginLongitude;

    /**
     * Detect camera authorization
     */
    public void checkPermissions() {
        showProgress();
        timer.start();
        GPSUtils.getInstance(getActivity().getApplicationContext()).getLngAndLat(new GPSUtils.OnLocationResultListener() {
            @Override
            public void onLocationResult(Location location) {
                loginLatitude = location.getLatitude();
                loginLongitude = location.getLongitude();
                timer.cancel();
                GPSUtils.getInstance(getActivity().getApplicationContext()).removeListener();
                updateLocation(loginLatitude + "", loginLongitude + "");
                LogUtils.e("loginLatitude == " + loginLatitude + "   loginLongitude ==  " + loginLongitude);
            }

            @Override
            public void OnLocationChange(Location location) {
                loginLatitude = location.getLatitude();
                loginLongitude = location.getLongitude();
                timer.cancel();
                GPSUtils.getInstance(getActivity().getApplicationContext()).removeListener();
                updateLocation(loginLatitude + "", loginLongitude + "");
                LogUtils.e("loginLatitude == " + loginLatitude + "   loginLongitude ==  " + loginLongitude);
            }

            @Override
            public void OnLocationError() {
                timer.cancel();
                stopProgress();
            }
        });
    }


    CountDownTimer timer = new CountDownTimer(10000, 1000) {
        @Override
        public void onTick(long l) {

        }

        @Override
        public void onFinish() {
            stopProgress();
            GPSUtils.getInstance(getActivity().getApplicationContext()).removeListener();
            showToast(getString(R.string.gpshuoqushibai));
        }
    };


    /**
     * 上传GPS
     */
    public void updateLocation(String longation, String langation) {
        HttpServerImpl.updateLocation(longation, langation).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                stopProgress();
                gotoActivity(ConfirmationActivity.class, false);
            }

            @Override
            public void onFiled(String message) {
                stopProgress();
                showToast(message);
            }
        });
    }


    @OnClick(R.id.days_layout)
    public void switchDays() {
        if (days == null) {
            return;
        }
        PopXingZhi popXingZhi = new PopXingZhi(getActivity(), "", days);
        popXingZhi.setListener((position, item) -> {
            daysText.setText(item);
        });
        popXingZhi.showAtLocation(getActivity().getWindow().getDecorView());
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        if (timer != null) {
            timer.cancel();
        }
        GPSUtils.getInstance(getActivity().getApplicationContext()).removeListener();
    }
}
