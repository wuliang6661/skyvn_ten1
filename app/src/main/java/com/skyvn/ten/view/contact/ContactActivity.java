package com.skyvn.ten.view.contact;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.skyvn.ten.R;
import com.skyvn.ten.api.HttpResultSubscriber;
import com.skyvn.ten.api.HttpServerImpl;
import com.skyvn.ten.bean.ContactBO;
import com.skyvn.ten.mvp.MVPBaseActivity;
import com.skyvn.ten.util.AuthenticationUtils;
import com.skyvn.ten.util.phone.PhoneDto;
import com.skyvn.ten.util.phone.PhoneUtil;
import com.skyvn.ten.widget.AlertDialog;
import com.skyvn.ten.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.skyvn.ten.widget.lgrecycleadapter.LGViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * MVPPlugin
 * 通讯录
 */

public class ContactActivity extends MVPBaseActivity<ContactContract.View, ContactPresenter>
        implements ContactContract.View {

    @BindView(R.id.edit_search)
    EditText editSearch;
    @BindView(R.id.search_delete)
    ImageView searchDelete;
    @BindView(R.id.recycle_view)
    RecyclerView recycleView;

    int type = Integer.MAX_VALUE;

    @Override
    protected int getLayout() {
        return R.layout.act_contact;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        goBack();
        LinearLayout imageView = findViewById(R.id.back);
        imageView.setVisibility(View.VISIBLE);
        setTitleText(getResources().getString(R.string.contact));
        rightButton();

        type = getIntent().getIntExtra("auth_type", Integer.MAX_VALUE);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleView.setLayoutManager(manager);
        requestPermission();
        initView();
    }


    @OnClick(R.id.back)
    public void back() {
        HttpServerImpl.getBackMsg(AuthenticationUtils.PHONE_LIST).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                new AlertDialog(ContactActivity.this).builder().setGone().setTitle(getResources().getString(R.string.tishi))
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


    private void initView() {
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                getPersonList(editable.toString());
            }
        });
    }


    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {//未开启定位权限
            //开启定位权限,200是标识码
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 200);
        } else {
            getPersonList("");
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 200://刚才的识别码
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {//用户同意权限,执行我们的操作
                    getPersonList("");
                } else {//用户拒绝之后,当然我们也可以弹出一个窗口,直接跳转到系统设置页面
                    Toast.makeText(this, "未开启通讯录权限,请手动到设置去开启权限", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                break;
        }
    }


    /**
     * 获取通讯录用户
     */
    private void getPersonList(String msg) {
        showProgress();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<PhoneDto> phones = new PhoneUtil(ContactActivity.this).searchContacts(msg);
                stopProgress();
                LGRecycleViewAdapter<PhoneDto> adapter = new LGRecycleViewAdapter<PhoneDto>(phones) {
                    @Override
                    public int getLayoutId(int viewType) {
                        return R.layout.item_persons_layout;
                    }

                    @Override
                    public void convert(LGViewHolder holder, PhoneDto phoneDto, int position) {
                        holder.setText(R.id.person_name, phoneDto.getName());
                        holder.setText(R.id.phone, phoneDto.getTelPhone());
                    }
                };
                adapter.setOnItemClickListener(R.id.item_layout, new LGRecycleViewAdapter.ItemClickListener() {
                    @Override
                    public void onItemClicked(View view, int position) {
                        if (type != 0) {
                            String phone = phones.get(position).getTelPhone().replaceAll(" ", "")
                                    .replaceAll("-", "");
                            if (phone.length() != 10) {
                                showToast(getString(R.string.jingjilianxiren_xianzhi));
                                return;
                            }
                            Intent intent = new Intent();
                            ContactBO contactBO = new ContactBO();
                            contactBO.setName(phones.get(position).getName());
                            contactBO.setPhone(phone);
                            intent.putExtra("contact", contactBO);
                            setResult(0x11, intent);
                            finish();
                        }
                    }
                });
                recycleView.setAdapter(adapter);
                if (type == 0) {
                    new AlertDialog(ContactActivity.this).builder().setGone().setTitle(getResources().getString(R.string.tishi))
                            .setMsg(getResources().getString(R.string.contact_aleg_dialog))
                            .setNegativeButton(getResources().getString(R.string.buyunxu), null)
                            .setPositiveButton(getResources().getString(R.string.hao), v -> {
                                commitContactList(phones);
                            }).show();
                }
            }
        }, 1000);
    }


    /**
     * 上传通讯录验证
     */
    private void commitContactList(List<PhoneDto> phoneDtos) {
        List<ContactBO> contactBOS = new ArrayList<>();
        for (PhoneDto phone : phoneDtos) {
            ContactBO contactBO = new ContactBO();
            contactBO.setPhone(phone.getTelPhone());
            contactBO.setName(phone.getName());
            contactBOS.add(contactBO);
        }
//        HttpServerImpl.commitContactList(contactBOS).subscribe(new HttpResultSubscriber<AttentionSourrssBO>() {
//            @Override
//            public void onSuccess(AttentionSourrssBO s) {
//                showToast(getResources().getString(R.string.commit_sourss_toast));
//                AuthenticationUtils.goAuthNextPage(s.getCode(), s.getNeedStatus(), ContactActivity.this);
//
//            }
//
//            @Override
//            public void onFiled(String message) {
//                showToast(message);
//            }
//        });
    }


}
