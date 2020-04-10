package com.skyvn.ten.view.main.recommend;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.skyvn.ten.R;
import com.skyvn.ten.bean.BannerBO;
import com.skyvn.ten.mvp.MVPBaseFragment;
import com.skyvn.ten.view.WebActivity;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class RecommendFragment extends MVPBaseFragment<RecommendContract.View, RecommendPresenter>
        implements RecommendContract.View {


    @BindView(R.id.id_flowlayout)
    TagFlowLayout idFlowlayout;
    Unbinder unbinder;
    @BindView(R.id.home_banner_img)
    RoundedImageView homeBannerImg;

    private BannerBO bannerBO;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fra_recommend, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setFlow();
        mPresenter.getHomeBanner();
    }


    private void setFlow() {
        List<String> list = new ArrayList<>();
        list.add(getResources().getString(R.string.tuijian_hint1));
        list.add(getResources().getString(R.string.tuijian_hint2));
        list.add(getResources().getString(R.string.tuijian_hint3));
        idFlowlayout.setAdapter(new TagAdapter<String>(list) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) getLayoutInflater().inflate(R.layout.text_xieyi,
                        idFlowlayout, false);
                tv.setText(s);
                if (position == 1) {
                    tv.setTextColor(Color.parseColor("#FF601C"));
                } else {
                    tv.setTextColor(Color.parseColor("#666666"));
                }
                return tv;
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.home_banner_img)
    public void clickBanner() {
        if (bannerBO == null || StringUtils.isEmpty(bannerBO.getForwardUrl())) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString("url", bannerBO.getForwardUrl());
        bundle.putString("title", getString(R.string.guanggao));
        gotoActivity(WebActivity.class, bundle, false);
    }

    @Override
    public void getBanner(BannerBO bannerBO) {
        this.bannerBO = bannerBO;
        Glide.with(getActivity()).load(bannerBO.getImageUrl()).placeholder(R.drawable.home_banner_img1).error(R.drawable.home_banner_img1)
                .into(homeBannerImg);
    }

    @Override
    public void onRequestError(String msg) {

    }
}
