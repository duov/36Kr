package com.liangduo.kr36.tool;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;

/**
 * Created by liangduo on 16/6/1.
 * 这个类没用上
 */
public class DrawableCircle  extends Drawable{
    private Paint paint;//画笔
    private Bitmap bitmap;//我们要操作的Bitmap
    private RectF rectF;//矩形(float)

    public DrawableCircle(Bitmap bitmap) {
        this.bitmap = bitmap;
        paint = new Paint();
        paint.setAntiAlias(true);//抗锯齿
        //位图渲染器
        //参数1 我们要操作的BitMap
        //参数2 ,3  x轴,y轴图片填充类型
        //类型一共分为三种
        //REPEAT 重复类型
        //CLAMP  拉伸类型 注意:这里的拉伸,是指拉伸图片的最后一个像素
        //MIRROR 镜像类型
        BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        paint.setShader(bitmapShader);
    }


    /**
     * 这个方法是指drawable将被绘制在画布上的区域
     *
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    //参数:左上右下
    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        super.setBounds(left, top, right, bottom);
        rectF = new RectF(left, top, right, bottom);
    }

    /**
     * 获取Bitmap的高度
     *
     * @return
     */
    //获取高度
    @Override
    public int getIntrinsicHeight() {
        return bitmap.getHeight();
    }

    /**
     * 获取Bitmap的宽
     *
     * @return
     */
    //获取宽度
    @Override
    public int getIntrinsicWidth() {
        return bitmap.getWidth();
    }


    /**
     * 这是我们核心的方法,绘制我们想要的图片
     *
     * @param canvas
     */
    @Override
    public void draw(Canvas canvas) {
        //圆角
        //参数
        //1 绘制区域
        // 2 x轴圆角半径
        //3 y周圆角半径
        //4 画笔
//        canvas.drawRoundRect(rectF,5000, 5000, paint);
        //画圆
        //参数1.2 圆坐标
        //半径
        //画笔
        canvas.drawCircle(getIntrinsicWidth()/2,getIntrinsicHeight()/2,getIntrinsicWidth()/2,paint);
    }

    /**
     * 设置透明度
     *
     * @param alpha
     */
    @Override
    public void setAlpha(int alpha) {

    }

    /**
     * 设置滤镜渲染颜色
     *
     * @param colorFilter
     */
    @Override
    public void setColorFilter(ColorFilter colorFilter) {

    }

    /**
     * 获取透明度
     *
     * @return
     */
    @Override
    public int getOpacity() {
        return 0;
    }
}
