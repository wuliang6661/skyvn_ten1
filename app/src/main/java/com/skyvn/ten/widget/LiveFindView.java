package com.skyvn.ten.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2020/1/1413:30
 * desc   : 去识别人脸之前扫描的空间
 * version: 1.0
 */
public class LiveFindView extends View {

    //扫描线颜色
    private int laserColor;

    public static int scannerStart = 0;
    public static int scannerEnd = 0;

    private static final int CORNER_RECT_WIDTH = 2;  //扫描区边角的宽
    private static final int CORNER_RECT_HEIGHT = 400; //扫描区边角的高
    private static final int SCANNER_LINE_MOVE_DISTANCE = 5;  //扫描线移动距离
    private static final int SCANNER_LINE_HEIGHT = 10;  //扫描线宽度

    private Paint paint;

    public LiveFindView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        Rect frame = CameraManager.get().getFramingRect();
//        if (frame == null) {
//            return;
//        }
//        if (scannerStart == 0 || scannerEnd == 0) {
//            scannerStart = frame.top;
//            scannerEnd = frame.bottom;
//        }
//
//        int width = canvas.getWidth();
//        int height = canvas.getHeight();
//        // Draw the exterior (i.e. outside the framing rect) darkened
//        drawExterior(canvas, frame, width, height);
    }


    //绘制扫描线
    private void drawLaserScanner(Canvas canvas, Rect frame) {
        paint.setColor(laserColor);
        //扫描线闪烁效果
//    paint.setAlpha(SCANNER_ALPHA[scannerAlpha]);
//    scannerAlpha = (scannerAlpha + 1) % SCANNER_ALPHA.length;
//    int middle = frame.height() / 2 + frame.top;
//    canvas.drawRect(frame.left + 2, middle - 1, frame.right - 1, middle + 2, paint);
        //线性渐变
        LinearGradient linearGradient = new LinearGradient(
                frame.left, scannerStart,
                frame.left, scannerStart + SCANNER_LINE_HEIGHT,
                shadeColor(laserColor),
                laserColor,
                Shader.TileMode.MIRROR);

        RadialGradient radialGradient = new RadialGradient(
                (float) (frame.left + frame.width() / 2),
                (float) (scannerStart + SCANNER_LINE_HEIGHT / 2),
                360f,
                laserColor,
                shadeColor(laserColor),
                Shader.TileMode.MIRROR);
        SweepGradient sweepGradient = new SweepGradient(
                (float) (frame.left + frame.width() / 2),
                (float) (scannerStart + SCANNER_LINE_HEIGHT),
                shadeColor(laserColor),
                laserColor);
        ComposeShader composeShader = new ComposeShader(radialGradient, linearGradient, PorterDuff.Mode.ADD);
        paint.setShader(radialGradient);
        if (scannerStart <= scannerEnd) {
            //矩形
//      canvas.drawRect(frame.left, scannerStart, frame.right, scannerStart + SCANNER_LINE_HEIGHT, paint);
            //椭圆
            RectF rectF = new RectF(frame.left + 2 * SCANNER_LINE_HEIGHT, scannerStart, frame.right - 2 * SCANNER_LINE_HEIGHT, scannerStart + SCANNER_LINE_HEIGHT);
            canvas.drawOval(rectF, paint);
            scannerStart += SCANNER_LINE_MOVE_DISTANCE;
        } else {
            scannerStart = frame.top;
        }
        paint.setShader(null);
    }


    //处理颜色模糊
    public int shadeColor(int color) {
        String hax = Integer.toHexString(color);
        String result = "20" + hax.substring(2);
        return Integer.valueOf(result, 16);
    }
}
