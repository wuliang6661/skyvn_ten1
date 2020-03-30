package com.skyvn.ten.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.blankj.utilcode.util.SizeUtils;

public class MyDialog extends Dialog {
    //    style引用style样式
    public MyDialog(Context context, int width, int height, View layout, int style) {
        super(context, style);
        setContentView(layout);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        params.width = SizeUtils.dp2px(269);
        window.setAttributes(params);

    }
}