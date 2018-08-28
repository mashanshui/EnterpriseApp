package com.shenhesoft.enterpriseapp.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

/**
 * 作者：Tornado
 * 创作日期：2018/1/31.
 * 描述：简易的饼状图
 */

public class PieChartView extends View {

    public final static float START_ANGLE_LEFT = -180;
    public final static float START_ANGLE_TOP = -90;

    Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    TextPaint txtPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);

    private float pieCharWidth;

    private int[] nums;
    private int[] colors;
    private int dataSum;
    private float scale;

    public PieChartView(Context context) {
        super(context);
    }

    public PieChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PieChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 设置饼状图数据
     *
     * @param nums   每项数据值的数组
     * @param colors 每项对应的颜色数组
     */
    public void setPieChartData(int[] nums, int[] colors) {
        this.nums = nums;
        this.colors = colors;

        for (int i = 0; i < nums.length; i++) {
            dataSum += nums[i];
        }
        scale = 360.0f / dataSum;

        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setLayerType(LAYER_TYPE_SOFTWARE,null);
        pieCharWidth = getWidth() * 0.2f;   //饼图的线宽为宽度的 1/5
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(pieCharWidth);
        if (nums == null || colors == null) {
            return;
    }
        float surplus = pieCharWidth / 2 + 2; //超出View范围外的大小
        //饼图的大小，以View的宽度为准。
        RectF rF = new RectF(0 + surplus, 0 + surplus, getWidth() - surplus, getWidth() - surplus);
        //开始角度
        float startAngle = START_ANGLE_LEFT;
        for (int i = 0; i < nums.length; i++) {
            mPaint.setColor(getResources().getColor(colors[i]));
            canvas.drawArc(rF, startAngle, nums[i] * scale, false, mPaint);
            startAngle += nums[i] * scale;
        }

        //-------------绘制文字------------
        txtPaint.setTextSize(getWidth() / 5);
        txtPaint.setColor(Color.parseColor("#333333"));
        String txtSum = dataSum + "";
        float txtSumW = txtPaint.measureText(txtSum);
        canvas.drawText(txtSum, getWidth() / 2 - (txtSumW / 2), getHeight() / 2, txtPaint);


        txtPaint.setTextSize(getWidth() / 10);
        txtPaint.setColor(Color.parseColor("#999999"));
        String txtAll = "全部";
        float txtAllW = txtPaint.measureText(txtAll);
        float txtALlH = txtPaint.getFontSpacing();

        canvas.drawText(txtAll, getWidth() / 2 - (txtAllW / 2), getHeight() / 2 + txtALlH + 20, txtPaint);
    }
}
