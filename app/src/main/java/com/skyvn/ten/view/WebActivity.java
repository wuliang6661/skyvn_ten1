package com.skyvn.ten.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;

import com.skyvn.ten.R;
import com.skyvn.ten.base.BaseWebActivity;

import butterknife.BindView;

/**
 * 加载网页的页面
 */
public class WebActivity extends BaseWebActivity {

    @BindView(R.id.web_view)
    WebView webView;

    @Override
    protected int getLayout() {
        return R.layout.act_web;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText(getResources().getString(R.string.guanggao));

        initWebView(webView);
        String url = getIntent().getExtras().getString("url");
        webView.loadUrl(url);

    }
}
