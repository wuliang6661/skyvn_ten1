package com.skyvn.ten.util;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.skyvn.ten.R;

public class ToastManager {

    public static void showShort(String message) {

        Message msg = new Message();
        msg.obj = message;
        handler.sendMessage(msg);
    }


    private static Handler handler = new Handler(AppManager.getAppManager().curremtActivity().getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            String message = (String) msg.obj;
            Context context = AppManager.getAppManager().curremtActivity();
            //使用布局加载器，将编写的toast_layout布局加载进来
            View view = LayoutInflater.from(context).inflate(R.layout.toast_view, null);
            //获取TextView
            TextView title = view.findViewById(R.id.toast_tv);
            //设置显示的内容
            title.setText(message);
            Toast toast = new Toast(context);
            //设置Toast要显示的位置，水平居中并在底部，X轴偏移0个单位，Y轴偏移70个单位，
            toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, 280);
            //设置显示时间
            toast.setDuration(Toast.LENGTH_SHORT);

            toast.setView(view);
            toast.show();
        }
    };

}
