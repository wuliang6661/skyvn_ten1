package com.skyvn.ten.util;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Bitmap util.
 * <p>从Uri直接读取图片流，避免路径转换的适配问题</p>
 */
public class BitmapUtil {
    /**
     * 读取一个缩放后的图片，限定图片大小，避免OOM
     *
     * @param uri       图片uri，支持“file://”、“content://”
     * @param maxWidth  最大允许宽度
     * @param maxHeight 最大允许高度
     * @return 返回一个缩放后的Bitmap，失败则返回null
     */
    public static Bitmap decodeUri(Context context, Uri uri, int maxWidth, int maxHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; //只读取图片尺寸
        readBitmapScale(context, uri, options);

        //计算实际缩放比例
        int scale = 1;
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            if ((options.outWidth / scale > maxWidth &&
                    options.outWidth / scale > maxWidth * 1.4) ||
                    (options.outHeight / scale > maxHeight &&
                            options.outHeight / scale > maxHeight * 1.4)) {
                scale++;
            } else {
                break;
            }
        }

        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;//读取图片内容
        options.inPreferredConfig = Bitmap.Config.RGB_565; //根据情况进行修改
        Bitmap bitmap = null;
        try {
            bitmap = readBitmapData(context, uri, options);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    private static void readBitmapScale(Context context, Uri uri, BitmapFactory.Options options) {
        if (uri == null) {
            return;
        }
        String scheme = uri.getScheme();
        if (ContentResolver.SCHEME_CONTENT.equals(scheme) ||
                ContentResolver.SCHEME_FILE.equals(scheme)) {
            InputStream stream = null;
            try {
                stream = context.getContentResolver().openInputStream(uri);
                BitmapFactory.decodeStream(stream, null, options);
            } catch (Exception e) {
                Log.w("readBitmapScale", "Unable to open content: " + uri, e);
            } finally {
                if (stream != null) {
                    try {
                        stream.close();
                    } catch (IOException e) {
                        Log.e("readBitmapScale", "Unable to close content: " + uri, e);
                    }
                }
            }
        } else if (ContentResolver.SCHEME_ANDROID_RESOURCE.equals(scheme)) {
            Log.e("readBitmapScale", "Unable to close content: " + uri);
        } else {
            Log.e("readBitmapScale", "Unable to close content: " + uri);
        }
    }

    private static Bitmap readBitmapData(Context context, Uri uri, BitmapFactory.Options options) {
        if (uri == null) {
            return null;
        }
        Bitmap bitmap = null;
        String scheme = uri.getScheme();
        if (ContentResolver.SCHEME_CONTENT.equals(scheme) ||
                ContentResolver.SCHEME_FILE.equals(scheme)) {
            InputStream stream = null;
            try {
                stream = context.getContentResolver().openInputStream(uri);
                bitmap = BitmapFactory.decodeStream(stream, null, options);
            } catch (Exception e) {
                Log.e("readBitmapData", "Unable to open content: " + uri, e);
            } finally {
                if (stream != null) {
                    try {
                        stream.close();
                    } catch (IOException e) {
                        Log.e("readBitmapData", "Unable to close content: " + uri, e);
                    }
                }
            }
        } else if (ContentResolver.SCHEME_ANDROID_RESOURCE.equals(scheme)) {
            Log.e("readBitmapData", "Unable to close content: " + uri);
        } else {
            Log.e("readBitmapData", "Unable to close content: " + uri);
        }
        return bitmap;
    }


    /**
     * base64转bitmap
     */
    public static Bitmap base64ToBitMap(String code) {
        Bitmap bitmap = null;
        try {
            byte[] bitmapByte = Base64.decode(code, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapByte, 0, bitmapByte.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }


    /**
     * 图片转成文件
     */
    public static void decoderBase64File(String base64Code, String savePath) throws Exception {
        //byte[] buffer = new BASE64Decoder().decodeBuffer(base64Code);
        byte[] buffer = Base64.decode(base64Code, Base64.DEFAULT);
        FileOutputStream out = new FileOutputStream(savePath);
        out.write(buffer);
        out.close();
    }

    /**
     * 判断文件是否是图片文件
     */
    public static boolean isImageFile(String filePath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        if (options.outWidth == -1) {
            return false;
        }
        return true;
    }
}