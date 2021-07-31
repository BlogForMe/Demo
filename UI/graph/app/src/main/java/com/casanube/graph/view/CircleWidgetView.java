package com.casanube.graph.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import com.casanube.graph.R;
import com.casanube.graph.util.CanvasUtil;

public class CircleWidgetView extends View {

    private static final String TAG = "CircleWidgetView";

    Bitmap bgBitMap = null;
    Bitmap valueBitMap = null;
    Canvas bgCanvas = null;
    Canvas valueCanvas = null;
    Paint paint = null;
    Context context;
    private int canvasWidth;
    private int canvasHeight;
    int centerX;
    int centerY;
    int radius;
    int fontSize;
    int bgLineColor;
    int lineColor;

    String measureValue = "";

    float sweepAngle;
    static int PADDING = 40;
    static int DEFAULT_RADIUS = CanvasUtil.dip2px(40);
    static int DEFAULT_FONT_SIZE = CanvasUtil.dip2px(40);
    static int DEFAULT_BG_LINE_COLOR = Color.parseColor("#dddddd");
    static int DEFAULT_LINE_COLOR = Color.parseColor("#FAC742");

    public CircleWidgetView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DashboardView);
        this.radius = typedArray.getDimensionPixelOffset(R.styleable.DashboardView_radius, DEFAULT_RADIUS);
        this.fontSize = typedArray.getDimensionPixelSize(R.styleable.DashboardView_fontSize, DEFAULT_FONT_SIZE);
        this.bgLineColor = typedArray.getColor(R.styleable.DashboardView_bgLineColor, DEFAULT_BG_LINE_COLOR);
        this.lineColor = typedArray.getColor(R.styleable.DashboardView_lineColor, DEFAULT_LINE_COLOR);
        typedArray.recycle();
        this.paint = new Paint(Paint.DITHER_FLAG);
        this.paint.setAntiAlias(true);

        this.canvasWidth = 2 * (radius + PADDING);
        this.canvasHeight = canvasWidth;
        this.centerX = canvasWidth / 2;
        this.centerY = this.centerX;
        initView();
    }

    private void initView(){
        Log.d(TAG, "initView radius: " + radius);
        this.bgBitMap = Bitmap.createBitmap(canvasWidth, canvasHeight, Bitmap.Config.ARGB_8888);
        this.bgCanvas = new Canvas();
        this.bgCanvas.setBitmap(this.bgBitMap);
        this.valueBitMap= Bitmap.createBitmap(canvasWidth, canvasHeight, Bitmap.Config.ARGB_8888);
        this.valueCanvas = new Canvas();
        this.valueCanvas.setBitmap(this.valueBitMap);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(this.bgLineColor);
        paint.setStrokeWidth(2);
        bgCanvas.drawCircle(centerX, centerY, this.radius, paint );

    }

    private void drawNumber(){
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(this.lineColor);
        paint.setStrokeWidth(CanvasUtil.dip2px(2));
        paint.setStrokeCap(Paint.Cap.ROUND);
        valueCanvas.drawArc(PADDING, PADDING, this.radius*2 + PADDING, this.radius*2 + PADDING, 90, sweepAngle, false, paint);
    }

    private void drawValue(){
        valueCanvas.save();
        /*paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        valueCanvas.drawPaint(paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));*/
        drawNumber();
        paint.setColor(this.lineColor);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setTextSize(this.fontSize);
        paint.setStrokeWidth(CanvasUtil.dip2px(1));
        Rect rect = CanvasUtil.getTextRect(this.measureValue, paint);
        valueCanvas.drawText(this.measureValue, centerX, centerY + rect.height()/2, paint);
        valueCanvas.restore();
    }


    public void setValue(String value){
        this.measureValue = value;
        drawValue();
        float angle = 360;
        CircleWidgetAnim circleWidgetAnim = new CircleWidgetAnim(angle);
        circleWidgetAnim.setDuration(1500);
        this.startAnimation(circleWidgetAnim);
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

    public class CircleWidgetAnim extends Animation {

        float targetAngle;

        public CircleWidgetAnim(float targetAngle){
            this.targetAngle = targetAngle;
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            sweepAngle = interpolatedTime * targetAngle;
            postInvalidate();
        }
    }
}
