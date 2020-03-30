package com.skyvn.ten.view;

import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.skyvn.ten.R;
import com.skyvn.ten.api.HttpResultSubscriber;
import com.skyvn.ten.api.HttpServerImpl;
import com.skyvn.ten.base.BaseActivity;
import com.skyvn.ten.base.MyApplication;
import com.skyvn.ten.bean.KeFuBO;
import com.skyvn.ten.util.PhoneUtils;
import com.skyvn.ten.widget.AlertDialog;
import com.skyvn.ten.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.skyvn.ten.widget.lgrecycleadapter.LGViewHolder;

import java.util.List;

import butterknife.BindView;


/**
 * 客服界面
 */
public class KefuActivity extends BaseActivity {

    @BindView(R.id.recycle_view)
    RecyclerView recycleView;
    @BindView(R.id.user_img)
    RoundedImageView userImg;
    @BindView(R.id.user_name)
    TextView userName;

    private List<KeFuBO> kefus;

    @Override
    protected int getLayout() {
        return R.layout.act_kefu;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText(getResources().getString(R.string.my_kefu));

        userName.setText(MyApplication.userBO.getPhone());
        Glide.with(this).load(MyApplication.userBO.getHeadPortrait())
                .error(R.drawable.user_img_defalt).
                placeholder(R.drawable.user_img_defalt).into(userImg);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleView.setLayoutManager(manager);
        getKeFu();
    }


    /**
     * 获取全部客服
     */
    private void getKeFu() {
        HttpServerImpl.getCustomerServicesByApplicationId().subscribe(new HttpResultSubscriber<List<KeFuBO>>() {
            @Override
            public void onSuccess(List<KeFuBO> strings) {
                kefus = strings;
                showKeFu();
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }

    /**
     * 显示客服
     */
    private void showKeFu() {
        if (kefus == null) {
            showToast(getString(R.string.wushuju));
            return;
        }
        LGRecycleViewAdapter<KeFuBO> adapter = new LGRecycleViewAdapter<KeFuBO>(kefus) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_kefu;
            }

            @Override
            public void convert(LGViewHolder holder, KeFuBO keFuBO, int position) {
                holder.setText(R.id.kefu_name, keFuBO.getName());
                holder.setText(R.id.phone1, keFuBO.getContact());
            }
        };
        adapter.setOnItemClickListener(R.id.kefu1, (view, position) -> {
            switch (kefus.get(position).getType()) {
                case 0:   //电话
                    new AlertDialog(KefuActivity.this).builder().setGone().setTitle(kefus.get(position).getName())
                            .setMsg(kefus.get(position).getContact())
                            .setNegativeButton(getResources().getString(R.string.cancle), null)
                            .setPositiveButton(getResources().getString(R.string.call), v -> PhoneUtils.callPhone(kefus.get(position).getContact())).show();
                    break;
                default:
                    new AlertDialog(KefuActivity.this).builder().setGone().setTitle(kefus.get(position).getName())
                            .setMsg(kefus.get(position).getContact())
                            .setNegativeButton(getResources().getString(R.string.cancle), null)
                            .setPositiveButton(getResources().getString(R.string.copy), v -> copyText(kefus.get(position).getContact())).show();
                    break;
            }
        });
        recycleView.setAdapter(adapter);
    }


    private void copyText(String text) {
        // 从API11开始android推荐使用android.content.ClipboardManager
        // 为了兼容低版本我们这里使用旧版的android.text.ClipboardManager，虽然提示deprecated，但不影响使用。
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        // 将文本内容放到系统剪贴板里。
        cm.setText(text);
        showToast(getResources().getString(R.string.copy_sourss));
    }

}
