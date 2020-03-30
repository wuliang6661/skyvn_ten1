package com.skyvn.ten.view.main.none;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skyvn.ten.R;

import me.yokeyword.fragmentation.SupportFragment;

public class NoneFragment extends SupportFragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fra_load, container, false);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        if (findChildFragment(RecommendFragment.class) == null) {
//            loadRootFragment(R.id.fl_first_container, new RecommendFragment());
//        }
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);


    }
}
