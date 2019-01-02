package com.example.mylibrary;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

public class CircleProgressBar extends View {
    private int defaultSize=48;//dip
    private int radius;//圆半径
    private int scale;//半径与宽度比
    private int strokeWidth;//圆宽度
    private int circleBackgroundColor;//圆进度底部颜色
    private int circleForegroundColor;//进度加载颜色
    private int textBackgroundColor;
    private int textColor;
    private int textSize;//字体大小
    private int duration;//转完一圈需要的时间
    private int progress;
    private String text;//字体内容
    private RotationListener listener;
    private boolean hasStart;
    private Interpolator interpolator=new LinearInterpolator();
    public CircleProgressBar(Context context) {
        super(context);
    }

    public CircleProgressBar(Context context, AttributeSet attrs) {

        this(context,attrs,0);

    }

    public CircleProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CircleProgressBar, defStyleAttr, 0);


        scale=a.getInt(R.styleable.CircleProgressBar_scale,6);
        circleBackgroundColor=a.getColor(R.styleable.CircleProgressBar_color_background,Color.TRANSPARENT);
        circleForegroundColor=a.getColor(R.styleable.CircleProgressBar_color_foreground,Color.GREEN);
        textColor=a.getColor(R.styleable.CircleProgressBar_color_background,Color.WHITE);
        textBackgroundColor=a.getColor(R.styleable.CircleProgressBar_color_text_background,Color.GRAY);
        textSize= (int) a.getDimension(R.styleable.CircleProgressBar_text_size,0);
        text=a.getString(R.styleable.CircleProgressBar_text_inside);
        if (text==null)
            text="跳过";
        duration=a.getInt(R.styleable.CircleProgressBar_duration,2000);
    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        int heightMode=MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode==MeasureSpec.AT_MOST||heightMode==MeasureSpec.AT_MOST){//如果宽高是wrap_content
            if (widthMode==MeasureSpec.AT_MOST)
                widthMeasureSpec=MeasureSpec.makeMeasureSpec(dip2px(getContext(),defaultSize),MeasureSpec.AT_MOST);
            if (heightMode==MeasureSpec.AT_MOST)
                heightMeasureSpec=MeasureSpec.makeMeasureSpec(dip2px(getContext(),defaultSize),MeasureSpec.AT_MOST);;
            radius=dip2px(getContext(),defaultSize)/2;
        }else {
            int width=MeasureSpec.getSize(widthMeasureSpec);
            int height=MeasureSpec.getSize(heightMeasureSpec);
            radius=width>height?height/2:width/2;

        }
        if (textSize==0)
            textSize=radius*7/11;
        strokeWidth=radius/scale;
        radius=radius-strokeWidth/2;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint=new Paint();
        paint.setColor(circleBackgroundColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth);
        paint.setAntiAlias(true);

        canvas.drawCircle(getWidth()/2,getHeight()/2,radius,paint);
        paint.setColor(circleForegroundColor);
        RectF oval=new RectF(getWidth()/2-radius,getHeight()/2-radius,getWidth()/2+radius,getHeight()/2+radius);
        canvas.drawArc(oval,-90,progress,false,paint);

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(textBackgroundColor);
        canvas.drawCircle(getWidth()/2,getHeight()/2,radius-strokeWidth/2,paint);

        Rect textRect=new Rect();
        paint.setTextSize(textSize);
        paint.setColor(textColor);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.getTextBounds(text,0,text.length(),textRect);
        Paint.FontMetrics fontMetricsInt=paint.getFontMetrics();
        paint.setStyle(Paint.Style.FILL);
        float baseLine=getHeight()/2-fontMetricsInt.ascent/2;
        canvas.drawText(text,getWidth()/2,baseLine,paint);
    }

    public static int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    public void startAnim(int startProgress) {
        ValueAnimator animator = ValueAnimator.ofInt(startProgress, 360);
        hasStart=false;
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value= (int) animation.getAnimatedValue();

                if (listener!=null){
                    if (value==0&&!hasStart){
                        listener.onStartRotating();
                        hasStart=true;
                    }
                    else if (value<360)
                        listener.onRotating((int) (value/360f*100));
                    else
                        listener.onFinishRotating();
                }
                progress = value;
                postInvalidate();
            }
        });
        animator.setDuration(duration);
        animator.setInterpolator(interpolator);   //动画匀速
        animator.start();
    }
    public void setOnRotationListener(RotationListener rotationListener){
        listener=rotationListener;
    }
    public interface RotationListener{
        void onStartRotating();
        void onRotating(int value);
        void onFinishRotating();
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public int getCircleBackgroundColor() {
        return circleBackgroundColor;
    }

    public void setCircleBackgroundColor(int circleBackgroundColor) {
        this.circleBackgroundColor = circleBackgroundColor;
    }

    public int getCircleForegroundColor() {
        return circleForegroundColor;
    }

    public void setCircleForegroundColor(int circleForegroundColor) {
        this.circleForegroundColor = circleForegroundColor;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Interpolator getInterpolator() {
        return interpolator;
    }

    public void setInterpolator(Interpolator interpolator) {
        this.interpolator = interpolator;
    }

    public int getTextBackgroundColor() {
        return textBackgroundColor;
    }

    public void setTextBackgroundColor(int textBackgroundColor) {
        this.textBackgroundColor = textBackgroundColor;
    }
}
