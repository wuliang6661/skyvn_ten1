package com.skyvn.ten.util;

import android.util.Log;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.Utils;
import com.skyvn.ten.api.HttpResultSubscriber;
import com.skyvn.ten.api.HttpServerImpl;
import com.skyvn.ten.bean.StsTokenBean;
import com.skyvn.ten.config.IConstant;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import id.zelory.compressor.Compressor;

/**
 * OSS上传文件工具
 */
public class UpdateFileUtils {

    private static String key = "vqZS3bbwDbMXDi09Ankwxw==";
    private String ossFiles;

    public void updateFile(int type, String filePath) {
        //当前时间戳，用于自定义文件在OSS中存储路径末尾的名称
        long image_url_time = System.currentTimeMillis();
        // 构造上传请求,第二个数参是ObjectName，第三个参数是本地文件路径
        ossFiles = IConstant.zuhuID + "/" + TimeUtils.millis2String(image_url_time, new SimpleDateFormat("yyyyMMdd")) + "/" + image_url_time + "." + FileUtils.getFileExtension(filePath);
        HttpServerImpl.getOssInfo(type, ossFiles).subscribe(new HttpResultSubscriber<StsTokenBean>() {
            @Override
            public void onSuccess(StsTokenBean s) {
                new Thread(() -> upload_file(s, filePath)).start();
            }

            @Override
            public void onFiled(String message) {
                if (listener != null) {
                    listener.callError(message);
                }
            }
        });
    }


    /**
     * 解密
     */
    private String decodeMsg(String msg) {
        AES aes1 = SecureUtil.aes(cn.hutool.core.codec.Base64.decode(key));
        return aes1.decryptStr(cn.hutool.core.codec.Base64.decode(msg));

    }

    //上传文件方法
    private void upload_file(StsTokenBean stsTokenBean, String loacalFilePath) {
        //根据你的OSS的地区而自行定义，本文中的是杭州
//        String url = "http://oss-cn-hangzhou.aliyuncs.com";
        String url = decodeMsg(stsTokenBean.getHttpUrl());
        String endpoint = decodeMsg(stsTokenBean.getEndpoint());
        String AccessKeyId = decodeMsg(stsTokenBean.getAccessKeyId());
        String AccessKeySecret = decodeMsg(stsTokenBean.getAccessKeySecret());
        String accessToken = decodeMsg(stsTokenBean.getSecurityToken());
        String bucket = decodeMsg(stsTokenBean.getBucket());
        String ossUrl = decodeMsg(stsTokenBean.getOosUrl());

        //移动端建议使用该方式，此时，stsToken中的前三个参数就派上用场了
        OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(AccessKeyId, AccessKeySecret,
                accessToken);

        // 配置类如果不设置，会有默认配置。
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000);   // 连接超时，默认15秒。
        conf.setSocketTimeout(15 * 1000);       // socket超时，默认15秒。
        conf.setMaxConcurrentRequest(5);        // 最大并发请求数，默认5个。
        conf.setMaxErrorRetry(2);               // 失败后最大重试次数，默认2次。

        //初始化OSS服务的客户端oss
        //事实上，初始化OSS的实例对象，应该具有与整个应用程序相同的生命周期，在应用程序生命周期结束时销毁
        //但这里只是实现功能，若时间紧，你仍然可以按照本文方式先将功能实现，然后优化
        OSS oss = new OSSClient(Utils.getApp(), endpoint, credentialProvider, conf);
        File file = new File(loacalFilePath);
        if (BitmapUtil.isImageFile(loacalFilePath)) {
            File compressedImageFile;
            try {
                compressedImageFile = new Compressor(Utils.getApp())
                        .setMaxHeight(4096)
                        .setMaxWidth(4096)
                        .setQuality(50)
                        .compressToFile(file);
            } catch (IOException e) {
                e.printStackTrace();
                compressedImageFile = file;
            }
            loacalFilePath = compressedImageFile.getAbsolutePath();
        }
        PutObjectRequest put = new PutObjectRequest(bucket, ossFiles, loacalFilePath);
        //异步上传可以设置进度回调
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {

//                Log.i("上传进度：", "当前进度" + currentSize + "   总进度" + totalSize);

            }
        });
        //实现异步上传
        OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                Log.d("PutObject", "UploadSuccess");
                Log.d("ETag", result.getETag());
                Log.d("RequestId", result.getRequestId());
                //这个image_url左边的字符串部分是我OSS的Bucket的文件存储地址，根据个人的文件存储地址不同，替换成自己的即可，而后面的image_url_time则是为了区分每个文件的文件名
                //注意，最好的方式是设置回调，因为回调的功能必须要在线上服务器才能测试，我服务器在本地环境中是不允许回调的
                //在咨询阿里云相关人员之后，他们说也允许记住地址，进行拼接的方式保存线上文件url路径
                //但是这种方式需要在OSS的管理控制台中将你的存储空间设置为公共读的方式，不然没法用下面的拼接链接。
                //此时你上传的文件所在的线上地址就已经获得了，想怎么使用则随意了
//                image_url = stsTokenBean.getBucket()+ image_url_time;
//                String aliPath = url + "/" + ossFiles;
                LogUtils.d(ossUrl);
                if (listener != null) {
                    listener.call(ossUrl);
                }
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientException, ServiceException serviceException) {
                if (clientException != null) {
                    // 本地异常，如网络异常等。
                    clientException.printStackTrace();
                    if (listener != null) {
                        listener.callError("Internet Error!");
                    }
                }
                if (serviceException != null) {
                    Log.e("ErrorCode", serviceException.getErrorCode());
                    Log.e("RequestId", serviceException.getRequestId());
                    Log.e("HostId", serviceException.getHostId());
                    Log.e("RawMessage", serviceException.getRawMessage());
                    if (listener != null) {
                        listener.callError(serviceException.getRawMessage());
                    }
                }
            }
        });
        // 等异步上传过程完成
        task.waitUntilFinished();
    }


    private OnCallBackListener listener;

    public void setListener(OnCallBackListener listener) {
        this.listener = listener;
    }

    public interface OnCallBackListener {

        void call(String s);

        void callError(String message);
    }
}
