package net.sxkeji.shixinandroiddemo2.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * description: 纯色圆圈
 * <br/>
 * author: shixinzhang
 * <br/>
 * data: 10/21/2016
 */
public class CircleColorView extends View {
    private static final int DEFAULT_SIZE = 10;
    private static final int DEFAULT_COLOR = Color.parseColor("#d70000");

    private Paint mPaint;
    private Context mContext;
    private int mCircleSize;
    private int mTextSize;
    private int mCircleColor;

    public CircleColorView(Context context) {
        super(context);
        initView(context);
    }

    public CircleColorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public CircleColorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        mContext = context;
        mCircleSize = DEFAULT_SIZE;
        mCircleColor = DEFAULT_COLOR;

        mPaint = new Paint();
        mPaint.setAntiAlias(true);  //去锯齿
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);     //不用父类测量
        int measureWidth = getMeasuredSize(widthMeasureSpec);
        int measureHeight = getMeasuredSize(heightMeasureSpec);
        setMeasuredDimension(measureWidth, measureHeight);
    }

    /**
     * 解决继承 View，wrap_content 没作用的问题
     *
     * @param measureSpec
     * @return
     */
    private int getMeasuredSize(int measureSpec) {
        int result = 0;

        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        switch (specMode) {
            case MeasureSpec.UNSPECIFIED:
                result = specSize;
                break;
            case MeasureSpec.AT_MOST:
                result = Math.min(mCircleSize, specSize);
                break;
            case MeasureSpec.EXACTLY:
                result = specSize;
                break;
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int center = getWidth() / 2;
        int radius = getWidth() / 2 - 2;

        //绘制实心圆
        mPaint.setColor(mCircleColor);
        canvas.drawCircle(center, center, radius, mPaint);

        //绘制圆环
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(1);
        mPaint.setColor(Color.parseColor("#b3b3b3"));
        canvas.drawCircle(center, center, radius + 1, mPaint);

    }

    private int dp2px(int dp) {
        float density = mContext.getResources().getDisplayMetrics().density;
        return (int) (density * dp + 0.5f);
    }

    public int getCircleSize() {
        return mCircleSize;
    }

    public void setCircleSize(int circleSize) {
        mCircleSize = dp2px(circleSize);
    }

    public int getTextSize() {
        return mTextSize;
    }

    public void setTextSize(int textSize) {
        mTextSize = textSize;
    }

    public int getCircleColor() {
        return mCircleColor;
    }

    public void setCircleColor(int circleColor) {
        mCircleColor = circleColor;
    }
}
