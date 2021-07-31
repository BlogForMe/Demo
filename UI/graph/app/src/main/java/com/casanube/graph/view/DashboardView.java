package com.casanube.graph.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import com.casanube.graph.R;
import com.casanube.graph.util.CanvasUtil;

public class DashboardView extends View {

    private static final String TAG = "DashboardView";

//    private int width;
//    private int height;
    private int canvasWidth;
    private int canvasHeight;
//    int r;
    Context context;

    int centerX;
    int centerY;
    float value;
    float lastValue = 0f;
    float tmpValue;
    String measureValue="";
    String measureLabel = "";
    float[] levels;
    int currentLevel;
    SweepGradient sweepGradient;
    RectF cRectF;
    RectF dRectF;
    int radius;

    static int MAX_VALUE = 15;
    static int STEP_ANGLE = 60;
    static float SINGLE_ARC_ANGLE = 58f;
    static int DEFAULT_RADIUS = CanvasUtil.dip2px(120);

    private static int PADDING = 20;
    private static int ARC_WIDTH = 80;
    private static int[] ARC_GRADIENT_COLORD = new int[]{
            Color.parseColor("#35BC9E"),
            Color.parseColor("#35BC9E"),
            Color.parseColor("#F8BE40"),
            Color.parseColor("#F8BE40"),
            Color.parseColor("#EC7C30"),
            Color.parseColor("#EC7C30"),
            Color.parseColor("#EC7C30")
    };
    Bitmap bgBitMap = null;
    Bitmap valueBitMap = null;
    Canvas bgCanvas = null;
    Canvas valueCanvas = null;
    Paint paint = null;
    private Paint txtPaint;

    public DashboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DashboardView);
        this.radius = typedArray.getDimensionPixelOffset(R.styleable.DashboardView_radius, DEFAULT_RADIUS);
        typedArray.recycle();
        this.paint = new Paint(Paint.DITHER_FLAG);
        this.paint.setAntiAlias(true);
        this.canvasWidth = 2 * (this.radius + PADDING);
        this.canvasHeight = (int)(1.5 * this.radius + 2 * PADDING);
        this.centerX = canvasWidth / 2;
        this.centerY = this.centerX;
        Log.d(TAG, String.format("radius: %s", radius));
        initView();
    }

    private void initView() {
        this.bgBitMap = Bitmap.createBitmap(canvasWidth, canvasHeight, Bitmap.Config.ARGB_8888);
        this.valueBitMap= Bitmap.createBitmap(canvasWidth, canvasHeight, Bitmap.Config.ARGB_8888);
        this.bgCanvas = new Canvas();
        this.bgCanvas.setBitmap(this.bgBitMap);
        this.valueCanvas = new Canvas();
        this.valueCanvas.setBitmap(this.valueBitMap);
        this.sweepGradient = new SweepGradient(this.centerX, this.centerY, ARC_GRADIENT_COLORD, null);
        int cPad = 46, dPad = 30;
        this.cRectF = new RectF(PADDING + cPad, PADDING + cPad, 2 * this.radius + PADDING - cPad, 2 * this.radius + PADDING - cPad);
        this.dRectF = new RectF(PADDING + dPad, PADDING + dPad, 2 * this.radius + PADDING - dPad, 2 * this.radius + PADDING - dPad);
        this.drawDashboard();
    }

    private void drawDashboard() {
        bgCanvas.save();
        bgCanvas.rotate(150, centerX, centerY);
        RectF rectF = new RectF(PADDING, PADDING, 2 * this.radius + PADDING, 2 * this.radius + PADDING);

        paint.setShader(sweepGradient);
        paint.setStrokeWidth(CanvasUtil.dip2px(3));
        paint.setStyle(Paint.Style.STROKE);
        bgCanvas.drawArc(rectF, 1, 238, false, this.paint);

        paint.setStrokeWidth(CanvasUtil.dip2px(20));
        for (int i = 0; i < 5; i++) {
            int startAngle = i * STEP_ANGLE;
            float sweep = 0.5f;
            bgCanvas.drawArc(cRectF, startAngle - sweep / 2, 0.5f, false, this.paint); //绘制刻度
        }

        paint.setStrokeWidth(CanvasUtil.dip2px(10));
        paint.setShader(null);
        paint.setColor(Color.parseColor("#dddddd"));
        for (int i = 0; i < 4; i++) {
            int startAngle = i * STEP_ANGLE;
            bgCanvas.drawArc(dRectF, startAngle + 1, 58, false, this.paint);   //绘制默认数值弧度
        }
        bgCanvas.restore();
    }

    private void drawTextItem(String txt) {
        paint.setShader(null);
        paint.setStrokeWidth(1);
        paint.setTextSize(CanvasUtil.sp2px(16));
        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setColor(Color.parseColor("#dddddd"));

        txtPaint = new Paint();
        txtPaint.setShader(null);
        txtPaint.setStrokeWidth(1);
        txtPaint.setTextSize(CanvasUtil.sp2px(16));
        txtPaint.setStyle(Paint.Style.FILL);
        txtPaint.setTextAlign(Paint.Align.CENTER);
        txtPaint.setColor(Color.parseColor("#F8BE40"));
        Rect rect = CanvasUtil.getTextRect("非常", txtPaint);
        Rect bRect = CanvasUtil.getTextRect("非常高", txtPaint);
        int offset = ARC_WIDTH + PADDING;
        int fontXOffset = rect.width() / 2;
        int fontYOffset = rect.height() / 2;
        int[] labelOffset = calcLabelOffset();

        Log.d(TAG, String.format("labelOffset: [%s, %s]", labelOffset[0], labelOffset[1]));
        if ("偏低".equals(txt)) {
            bgCanvas.drawText("偏低", offset + fontXOffset, centerY + fontYOffset, txtPaint);
        }else {
            bgCanvas.drawText("偏低", offset + fontXOffset, centerY + fontYOffset, paint);
        }

        if ("非常高".equals(txt)){
            bgCanvas.drawText("非常高", canvasWidth - offset - bRect.width()/2, centerY + fontYOffset, txtPaint);
        }else {
            bgCanvas.drawText("非常高", canvasWidth - offset - bRect.width()/2, centerY + fontYOffset, paint);
        }
        if ("正常".equals(txt)){
            bgCanvas.drawText("正常", labelOffset[0] + fontXOffset, labelOffset[1] + fontYOffset , txtPaint);
        }else {
            bgCanvas.drawText("正常", labelOffset[0] + fontXOffset, labelOffset[1] + fontYOffset , paint);
        }
        if ("偏高".equals(txt)){
            bgCanvas.drawText("偏高", canvasWidth - labelOffset[0] - fontXOffset, labelOffset[1] + fontYOffset , txtPaint);
        }else {
            bgCanvas.drawText("偏高", canvasWidth - labelOffset[0] - fontXOffset, labelOffset[1] + fontYOffset , paint);
        }

    }

    private int[] calcLabelOffset(){
        float a =  radius - ARC_WIDTH - PADDING;
        float angle = (float)(50*Math.PI/180);
        int x = (int)(centerX - Math.cos(angle) * a);
        int y = (int)(centerY - Math.sin(angle) * a);
        return new int[]{x, y};

    }


    private void drawValueLabel(String str) {
        Paint tPaint = getPaint(16, Paint.Align.LEFT, "#F8BE40");
        Rect strRect = CanvasUtil.getTextRect(str, tPaint);
        tPaint = getPaint(50, Paint.Align.RIGHT, "#F8BE40");
        Rect valRect = CanvasUtil.getTextRect(str, tPaint);
        int a = (strRect.width() + valRect.width())/2 - valRect.width();
        stroke(measureLabel, centerX,centerY - valRect.height(), 20, Paint.Align.CENTER, "#333333", valueCanvas );
        stroke(str, radius - a, centerY + valRect.height() / 2, 50, Paint.Align.RIGHT, "#F8BE40", valueCanvas );
        stroke("mmol", radius - a, radius + valRect.height() / 2, 16, Paint.Align.LEFT, "#F8BE40", valueCanvas );
    }

    private Paint getPaint(int fontSize, Paint.Align align, String color){
        Paint tPaint = new Paint();
        tPaint.setStyle(Paint.Style.FILL);
        tPaint.setTextSize(CanvasUtil.sp2px(fontSize));
        tPaint.setColor(Color.parseColor(color));
        tPaint.setShader(null);
        tPaint.setTextAlign(align);
        return tPaint;
    }

    private void drawArc(float value){
        valueCanvas.save();
        valueCanvas.rotate(150, centerX, centerY);
        paint.setStrokeWidth(CanvasUtil.dip2px(10));
        paint.setStyle(Paint.Style.STROKE);
        paint.setShader(sweepGradient);
        int tempLevel = 0;
        float startVal = 0, endVal = 0;
        value = Math.min(value, MAX_VALUE);
        value = Math.max(value, 0);
        if(value >= levels[4]){
            startVal = levels[4];
            endVal = startVal;
            tempLevel = 3;
        }else{
            for(int i=1; i<levels.length; i++){
                if(value >= levels[i-1] && value < levels[i]){
                    startVal = levels[i-1];
                    endVal = levels[i];
                    tempLevel = i-1;
                }
            }
        }
        float sweepAngle = 0f;
        if(endVal-startVal==0){
            sweepAngle = SINGLE_ARC_ANGLE;
        }else{
            sweepAngle = SINGLE_ARC_ANGLE * (value - startVal)  / (endVal-startVal);
        }
        float startAngle = tempLevel * STEP_ANGLE + 1;
        for (int i = 0; i < tempLevel; i++) {
            int tmpStartAngle = i * STEP_ANGLE + 1;
            valueCanvas.drawArc(dRectF, tmpStartAngle, 58, false, this.paint);
        }
//        Log.d(TAG, String.format("startVal: %s, endVal: %s, value: %s, tempLevel: %s, drawArc startAngle: %s , sweepAngle: %s", startVal, endVal, value, tempLevel, startAngle, sweepAngle));
        valueCanvas.drawArc(dRectF, startAngle, sweepAngle, false, this.paint);
        valueCanvas.restore();
    }

    private void stroke(String str, int x, int y, int fontSize, Paint.Align align, String color, Canvas canvas){
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(CanvasUtil.sp2px(fontSize));
        paint.setColor(Color.parseColor(color));
        paint.setShader(null);
        paint.setTextAlign(align);
        canvas.drawText(str, x, y, paint);
    }
    private String txt;

    public void setValue(float[] levels, String measureValue, String measureLabel,String txtValue) {
        this.txt = txtValue;
        if(levels == null || levels.length != 3){
            Log.e(TAG, "d档位参数必须是长度为3的数组");
            return;
        }
        this.lastValue = this.value;
        this.tmpValue = this.lastValue;
        this.value = Float.valueOf(measureValue);
        this.levels = new float[]{0f, 0f, 0f, 0f, MAX_VALUE};
        currentLevel = 4;
        for(int i=0; i<3; i++){
            this.levels[i+1] = levels[i];
        }
        this.measureValue = measureValue;
        this.measureLabel = measureLabel;
        drawValue();
        drawTextItem(txt);
        DashboardAnim dashboardAnim = new DashboardAnim(this.lastValue, this.value);
        dashboardAnim.setDuration(1500);
        this.startAnimation(dashboardAnim);
    }

    private void drawValue(){
        CanvasUtil.cleanCanvas(valueCanvas);
        if(this.measureValue != null && this.measureValue != ""){
            drawValueLabel(measureValue);
            drawArc(this.tmpValue);
        }
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if(widthMode!=MeasureSpec.EXACTLY){
            int widthSize = this.canvasWidth;
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(widthSize,MeasureSpec.EXACTLY);
        }
        if(heightMode != MeasureSpec.EXACTLY){
            int heightSize = this.canvasHeight;
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize,MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint bitMapPaint = new Paint();
        canvas.drawBitmap(this.bgBitMap, 0, 0, bitMapPaint);
        drawValue();
        canvas.drawBitmap(this.valueBitMap, 0, 0, bitMapPaint);
    }

    public class DashboardAnim extends Animation {

        float targetValue;
        float startValue;

        public DashboardAnim(float startValue, float targetValue){
            this.startValue = startValue;
            this.targetValue = targetValue;
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            tmpValue = startValue + (targetValue-startValue) * interpolatedTime;
//            Log.d(TAG, String.format("startValue: %s, targetValue: %s, tmpValue: %s", startValue, targetValue, tmpValue));
            postInvalidate();
        }
    }

}