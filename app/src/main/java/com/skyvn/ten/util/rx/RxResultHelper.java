package com.skyvn.ten.util.rx;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.blankj.utilcode.util.ToastUtils;
import com.skyvn.ten.R;
import com.skyvn.ten.api.DialogCallException;
import com.skyvn.ten.base.MyApplication;
import com.skyvn.ten.bean.BaseResult;
import com.skyvn.ten.util.AppManager;
import com.skyvn.ten.view.LoginActivity;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 作者 by wuliang 时间 16/11/24.
 */

public class RxResultHelper {
    private static final String TAG = "RxResultHelper";

    public static <T> Observable.Transformer<BaseResult<T>, T> httpRusult() {
        return apiResponseObservable -> apiResponseObservable.flatMap(
                (Func1<BaseResult<T>, Observable<T>>) mDYResponse -> {
                    Log.d(TAG, "call() called with: mDYResponse = [" + mDYResponse + "]");
                    if (mDYResponse.surcess()) {
                        return createData(mDYResponse.getData());
                    } else if (mDYResponse.getCode() == 421) {   //重新登录
                        MyApplication.spUtils.remove("token");
                        Activity activity = AppManager.getAppManager().curremtActivity();
                        if (activity instanceof LoginActivity) {
                            return Observable.error(new RuntimeException(mDYResponse.getMsg()));
                        }
                        Intent intent = new Intent(activity, LoginActivity.class);
                        ToastUtils.showShort(R.string.dengluguoqi);
                        AppManager.getAppManager().finishAllActivity();
                        activity.startActivity(intent);
                        return Observable.error(new RuntimeException(activity.getString(R.string.dengluguoqi)));
                    } else if (mDYResponse.getCode() == 398) {  //可拨打电话的弹窗
                        return Observable.error(new DialogCallException(mDYResponse.getMsg()));
                    } else if (mDYResponse.getCode() == 401) {
                        MyApplication.spUtils.remove("token");
                        Activity activity = AppManager.getAppManager().curremtActivity();
                        if (activity instanceof LoginActivity) {
                            return Observable.error(new RuntimeException(mDYResponse.getMsg()));
                        }
                        Intent intent = new Intent(activity, LoginActivity.class);
                        ToastUtils.showShort(activity.getString(R.string.dengluguoqi));
                        AppManager.getAppManager().finishAllActivity();
                        activity.startActivity(intent);
                        return Observable.error(new RuntimeException(activity.getString(R.string.dengluguoqi)));
                    } else {
                        return Observable.error(new RuntimeException(mDYResponse.getMsg()));
                    }
                }
        ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }


    private static <T> Observable<T> createData(final T t) {
        return Observable.create(subscriber -> {
            try {
//                subscriber.setPageInfo(pageBO);
                subscriber.onNext(t);
                subscriber.onCompleted();
            } catch (Exception e) {
                subscriber.onError(e);
            }
        });
    }
}
