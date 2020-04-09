package com.skyvn.ten.util;

import android.widget.Toast;

public class ToastManager {

    public static void showShort(String message) {
        Toast toast = Toast.makeText(AppManager.getAppManager().curremtActivity(), null, Toast.LENGTH_SHORT);
        toast.setText(message);
        toast.show();
    }

}
