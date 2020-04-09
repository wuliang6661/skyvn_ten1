package com.skyvn.ten.view.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.skyvn.ten.R;
import com.skyvn.ten.api.HttpResultSubscriber;
import com.skyvn.ten.api.HttpServerImpl;
import com.skyvn.ten.base.BaseFragment;
import com.skyvn.ten.bean.RecommendBO;
import com.skyvn.ten.util.UpdateUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class RecommendNewFragment extends BaseFragment {


    @BindView(R.id.tuijian_img)
    ImageView tuijianImg;
    Unbinder unbinder;

    RecommendBO recommendBO;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fra_new_recommend, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


//        List<Map<String, Object>> params = SMSUtils.obtainPhoneMessage(getActivity());
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        getRecommend();
    }

    private void getRecommend() {
        HttpServerImpl.getRemomImg().subscribe(new HttpResultSubscriber<RecommendBO>() {
            @Override
            public void onSuccess(RecommendBO s) {
                if (s == null) {
                    showToast(getString(R.string.wushuju));
                    return;
                }
                recommendBO = s;
                if(s.getOperateApplicationBannerVO() == null){
                    return;
                }
                Glide.with(getActivity()).load(s.getOperateApplicationBannerVO().getImageUrl()).into(tuijianImg);
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }


    @OnClick(R.id.tuijian_img)
    public void clickTuijian() {
        if (recommendBO == null || recommendBO.getOperateApplicationBannerVO() == null) {
            return;
        }
        new UpdateUtils().downloadAPK(recommendBO.getOperateApplicationBannerVO().getForwardUrl());
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
