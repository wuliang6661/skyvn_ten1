package com.skyvn.ten.api;

import android.util.Log;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.skyvn.ten.base.MyApplication;
import com.skyvn.ten.config.IConstant;
import com.skyvn.ten.util.MD5;
import com.skyvn.ten.util.language.LanguageType;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 作者 by wuliang 时间 16/11/24.
 * <p>
 * 所有的请求控制
 */

public class ApiManager {

    private static final Charset UTF8 = Charset.forName("UTF-8");

    private static final String TAG = "ApiManager";
    private Retrofit mRetrofit;
    private static final int DEFAULT_TIMEOUT = 60;
    private OkHttpClient.Builder builder;

    /**
     * 初始化请求体
     */
    private ApiManager() {
        //手动创建一个OkHttpClient并设置超时时间
        builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        builder.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        builder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.BODY;
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(message -> Log.i(TAG, "log: " + message));
        loggingInterceptor.setLevel(level);
        builder.addInterceptor(loggingInterceptor);
        builder.addInterceptor(headerInterceptor);
    }

    private static class SingletonHolder {
        private static final ApiManager INSTANCE = new ApiManager();
    }

    //获取单例
    public static ApiManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 获取请求代理
     *
     * @param service
     * @param url
     * @param <T>
     * @return
     */
    public <T> T configRetrofit(Class<T> service, String url) {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return mRetrofit.create(service);
    }


    /**
     * 获取STS服务的代理服务
     */
    public <T> T configRetrofit2(Class<T> service, String url) {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return mRetrofit.create(service);
    }


    /**
     * 自定义网络拦截器，如果头部信息中有通知数据已压缩，则解压过滤
     */
    private Interceptor postInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Request.Builder requestBuilder = request.newBuilder();
            String postBodyString = bodyToString(request.body());
            String params = "";
            //避免中文生成签名时和后台的编码冲突
            String encodeParm = StringUtils.isEmpty(params) ? postBodyString : params.substring(0, params.length() - 1);
            String sign = MD5.strToMd5Low32(URLEncoder.encode(URLDecoder.decode(encodeParm, "UTF-8"), "UTF-8"));
            RequestBody formBody = new FormBody.Builder()
                    .add("sign", sign.replaceAll("\\*", "%2A").replaceAll("\\+", "%20"))
                    .build();
            postBodyString += ((postBodyString.length() > 0) ? "&" : "") + bodyToString(formBody);
            request = requestBuilder
                    .post(RequestBody.create(MediaType.parse("application/x-www-form-urlencoded;charset=UTF-8"),
                            postBodyString))
                    .build();
            return chain.proceed(request);
        }
    };

    private static String bodyToString(final RequestBody request) {
        try {
            final Buffer buffer = new Buffer();
            if (request != null)
                request.writeTo(buffer);
            else
                return "";
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }


    /**
     * 所有请求头统一处理
     */
    private Interceptor headerInterceptor = chain -> {
        Request request;
        if (StringUtils.isEmpty(MyApplication.token)) {
            // 以拦截到的请求为基础创建一个新的请求对象，然后插入Header
            request = chain.request().newBuilder()
                    .addHeader("currentApplicationId", IConstant.appid)
                    .addHeader("currentTenantId", IConstant.zuhuID)
                    .addHeader("language", MyApplication.spUtils.getString(IConstant.LANGUAGE_TYPE, LanguageType.CHINESE.getLanguage()))
//                    .addHeader("Authorization", "1")
                    .build();
        } else {
            // 以拦截到的请求为基础创建一个新的请求对象，然后插入Header
            request = chain.request().newBuilder()
                    .addHeader("Authorization", MyApplication.token)
                    .addHeader("currentApplicationId", IConstant.appid)
                    .addHeader("currentTenantId", IConstant.zuhuID)
                    .addHeader("language", MyApplication.spUtils.getString(IConstant.LANGUAGE_TYPE, LanguageType.CHINESE.getLanguage()))
                    .build();
        }
        LogUtils.d(request.headers().toString());
        return chain.proceed(request);
    };

}
