package com.skyvn.ten.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.skyvn.ten.R;
import com.skyvn.ten.api.HttpResultSubscriber;
import com.skyvn.ten.api.HttpServerImpl;
import com.skyvn.ten.base.BaseActivity;
import com.skyvn.ten.base.MyApplication;
import com.skyvn.ten.bean.ContaceBO;
import com.skyvn.ten.bean.OrderDetailsBO;
import com.skyvn.ten.config.IConstant;
import com.skyvn.ten.util.AppManager;
import com.skyvn.ten.util.language.LanguageType;
import com.skyvn.ten.widget.MyDialog;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/***
 * 立即提现界面
 */
public class ConfirmationActivity extends BaseActivity {


    @BindView(R.id.jine_num)
    TextView jineNum;
    @BindView(R.id.qixian_num)
    TextView qixianNum;
    @BindView(R.id.dangzhang_num)
    TextView dangzhangNum;
    @BindView(R.id.lixi_num)
    TextView lixiNum;
    @BindView(R.id.fuwufei_num)
    TextView fuwufeiNum;
    @BindView(R.id.yinghuan_num)
    TextView yinghuanNum;
    @BindView(R.id.checkbox)
    CheckBox checkbox;
    @BindView(R.id.bt_commit)
    Button btCommit;
    @BindView(R.id.shuoming)
    TagFlowLayout shuoming;
    @BindView(R.id.person_img)
    RoundedImageView personImg;
    @BindView(R.id.person_name)
    TextView personName;
    @BindView(R.id.kefu_num1)
    TextView kefuNum1;
    @BindView(R.id.kefu_layout)
    LinearLayout kefuLayout;
    @BindView(R.id.kefu_num2)
    TextView kefuNum2;
    @BindView(R.id.id_flowlayout)
    TagFlowLayout idFlowlayout;
    @BindView(R.id.xieyi_layout)
    LinearLayout xieyiLayout;
    @BindView(R.id.kefu_img)
    ImageView kefuImg;
    @BindView(R.id.title_shuoming)
    TagFlowLayout titleShuoming;

    private String orderId;
    private OrderDetailsBO orderDetailsBO;

    @Override
    protected int getLayout() {
        return R.layout.act_comfirmation;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText(getResources().getString(R.string.querenjiekuan));
        rightButton();


        getOrderDetails();
        setFlow();
    }

    private void setFlow() {
        List<String> list = new ArrayList<>();
        list.add(getResources().getString(R.string.jiekuan_hint1));
        list.add(getResources().getString(R.string.jiekuan_hint2));
        list.add(getResources().getString(R.string.jiekuan_hint3));
        list.add(getResources().getString(R.string.jiekuan_hint4));
        idFlowlayout.setAdapter(new TagAdapter<String>(list) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) getLayoutInflater().inflate(R.layout.text_xieyi,
                        idFlowlayout, false);
                tv.setText(s);
                if (position == 1 || position == 3) {
                    tv.setTextColor(Color.parseColor("#FF5D2D"));
                } else {
                    tv.setTextColor(Color.parseColor("#888888"));
                }
                return tv;
            }
        });
        idFlowlayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                if (position == 1 || position == 3) {
                    getContact(position);
                }
                return false;
            }
        });
    }


    private void getContact(int position) {
        showProgress();
        HttpServerImpl.getContract().subscribe(new HttpResultSubscriber<ContaceBO>() {
            @Override
            public void onSuccess(ContaceBO s) {
                stopProgress();
                if (s == null) {
                    showToast(getString(R.string.huoquxieyishibai));
                    return;
                }
                String url;
                String title;
                if (position == 1) {
                    url = s.getLoanContractUrl();
                    title = getResources().getString(R.string.jiekuan_hint2);
                } else {
                    url = s.getServiceContractUrl();
                    title = getResources().getString(R.string.jiekuan_hint4);
                }
                if (url.contains("?")) {
                    url += "&productId=" + orderDetailsBO.getProductId() + "&tenantId=" + orderDetailsBO.getTenantId() + "&language=" +
                            MyApplication.spUtils.getString(IConstant.LANGUAGE_TYPE, LanguageType.CHINESE.getLanguage());
                } else {
                    url += "?productId=" + orderDetailsBO.getProductId() + "&tenantId=" + orderDetailsBO.getTenantId() + "&language=" +
                            MyApplication.spUtils.getString(IConstant.LANGUAGE_TYPE, LanguageType.CHINESE.getLanguage());
                }
                Bundle bundle = new Bundle();
                bundle.putString("url", url);
                bundle.putString("title",title);
                gotoActivity(WebActivity.class, bundle, false);
            }

            @Override
            public void onFiled(String message) {
                stopProgress();
                showToast(message);
            }
        });
    }


    private void getOrderDetails() {
        showProgress();
        HttpServerImpl.getOrderProduct().subscribe(new HttpResultSubscriber<OrderDetailsBO>() {
            @Override
            public void onSuccess(OrderDetailsBO s) {
                stopProgress();
                orderDetailsBO = s;
                showData();
            }

            @Override
            public void onFiled(String message) {
                stopProgress();
                showToast(message);
            }
        });
    }


    private void showData() {
        showTitleShuoming();
        if (orderDetailsBO == null) {
            jineNum.setText("****");
            qixianNum.setText("**");
            dangzhangNum.setText("****" + getResources().getString(R.string.danwei_yuan));
            lixiNum.setText("**" + getResources().getString(R.string.danwei_yuan));
            yinghuanNum.setText("**" + getResources().getString(R.string.danwei_yuan));
            fuwufeiNum.setText("****" + getResources().getString(R.string.danwei_yuan));
            xieyiLayout.setVisibility(View.GONE);
            btCommit.setVisibility(View.GONE);
            kefuImg.setVisibility(View.GONE);
            return;
        }
        jineNum.setText(orderDetailsBO.getPrice());
        qixianNum.setText(orderDetailsBO.getDays() + "");
        dangzhangNum.setText(orderDetailsBO.getPayAmount() + getResources().getString(R.string.danwei_yuan));
        lixiNum.setText(orderDetailsBO.getInterestAmount() + getResources().getString(R.string.danwei_yuan));
        yinghuanNum.setText(orderDetailsBO.getRepayAmount() + getResources().getString(R.string.danwei_yuan));
        fuwufeiNum.setText(orderDetailsBO.getServiceAmount() + getResources().getString(R.string.danwei_yuan));

        showYuqiFlow();
    }


    private void showTitleShuoming() {
        List<String> list = new ArrayList<>();
        if (orderDetailsBO == null) {
            list.add(getString(R.string.wupipei_title));
            list.add(getString(R.string.qing));
            list.add(getString(R.string.lianxikefu));
        } else {
            list.add(getString(R.string.jiekuan_content));
        }
        titleShuoming.setAdapter(new TagAdapter<String>(list) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) getLayoutInflater().inflate(R.layout.text_shuoming,
                        titleShuoming, false);
                tv.setText(s);
//                tv.setTextSize(SizeUtils.sp2px(15));
                if (position == 2) {
                    tv.setTextColor(Color.parseColor("#FF5D2D"));
                } else {
                    tv.setTextColor(Color.parseColor("#333333"));
                }
                return tv;
            }
        });
        titleShuoming.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                if (position == 2) {
                    gotoActivity(KefuActivity.class, false);
                }
                return false;
            }
        });
    }


    /**
     * 设置逾期费用显示
     */
    private void showYuqiFlow() {
        List<String> list = new ArrayList<>();
        list.add(getResources().getString(R.string.jiekuan_shuoming1));
        list.add(getResources().getString(R.string.jiekuan_shuoming2) + (orderDetailsBO == null ? "**" : orderDetailsBO.getOverdueAmount()) + "/" +
                getResources().getString(R.string.danwei_tian));
        list.add(getResources().getString(R.string.jiekuan_shuoming3));
        shuoming.setAdapter(new TagAdapter<String>(list) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) getLayoutInflater().inflate(R.layout.text_xieyi,
                        shuoming, false);
                tv.setText(s);
                if (position == 1) {
                    tv.setTextColor(Color.parseColor("#000000"));
                } else {
                    tv.setTextColor(Color.parseColor("#888888"));
                }
                return tv;
            }
        });
    }


    @OnClick(R.id.bt_commit)
    public void commit() {
        if (!checkbox.isChecked()) {
            showToast(getResources().getString(R.string.xieyi_hint));
            return;
        }
        if (orderDetailsBO == null) {
            return;
        }
        HttpServerImpl.confirm(orderDetailsBO.getId()).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                showTiXianSourss();
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }


    private void showTiXianSourss() {
        View view = getLayoutInflater().inflate(R.layout.dialog_jiekuan_souress, null);
        TextView guanBi = view.findViewById(R.id.guanbi);
        MyDialog mMyDialog = new MyDialog(this, 0, 0, view, R.style.DialogTheme);
        mMyDialog.setCancelable(false);
        guanBi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMyDialog.dismiss();
                AppManager.getAppManager().goHome();
            }
        });
        mMyDialog.show();
    }


    @OnClick(R.id.kefu_img)
    public void clickFuwu() {
        showFuWuDialog();
    }

    /**
     * 显示服务费明细
     */
    private void showFuWuDialog() {
        View view = getLayoutInflater().inflate(R.layout.dialog_fuwufei, null);
        TextView fuwuName1 = view.findViewById(R.id.fuwu_name1);
        TextView fuwuName2 = view.findViewById(R.id.fuwu_name2);
        TextView fuwuName3 = view.findViewById(R.id.fuwu_name3);
        TextView fuwuNum1 = view.findViewById(R.id.fuwu_num1);
        TextView fuwuNum2 = view.findViewById(R.id.fuwu_num2);
        TextView fuwuNum3 = view.findViewById(R.id.fuwu_num3);
        TextView guanBi = view.findViewById(R.id.guanbi);
        fuwuName1.setText(orderDetailsBO.getServiceOneName());
        fuwuName2.setText(orderDetailsBO.getServiceTwoName());
        fuwuName3.setText(orderDetailsBO.getServiceThreeName());
        fuwuNum1.setText(orderDetailsBO.getServiceOnePrice() + getResources().getString(R.string.danwei_yuan));
        fuwuNum2.setText(orderDetailsBO.getServiceTwoPrice() + getResources().getString(R.string.danwei_yuan));
        fuwuNum3.setText(orderDetailsBO.getServiceThreePrice() + getResources().getString(R.string.danwei_yuan));
        MyDialog mMyDialog = new MyDialog(this, 0, 0, view, R.style.DialogTheme);
        mMyDialog.setCancelable(true);
        guanBi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMyDialog.dismiss();
            }
        });
        mMyDialog.show();
    }

}
