package net.sxkeji.shixinandroiddemo2.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * description:纯色圆圈列表，需要传入颜色 List
 * <br/>
 * author: shixinzhang
 * <br/>
 * data: 10/25/2016
 */
public class ListCircleColorView extends LinearLayout {
    private int[] mMargin;
    private int mCircleSize;    //圆圈尺寸
    private ArrayList<Integer> mColorList;
    private Context mContext;

    public ListCircleColorView(Context context) {
        super(context);
        initView(context);
    }

    public ListCircleColorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ListCircleColorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        mContext = context;
        mMargin = new int[4];
        setGravity(Gravity.CENTER);
    }

    public void show() {
        if (mColorList == null)
            return;

        for (int i = 0; i < mColorList.size(); i++) {
            CircleColorView circle = new CircleColorView(mContext);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.leftMargin = mMargin[0];
            params.topMargin = mMargin[1];
            params.rightMargin = mMargin[2];
            params.bottomMargin = mMargin[3];

            circle.setLayoutParams(params);
            circle.setCircleSize(mCircleSize);
            circle.setCircleColor(mColorList.get(i));

            addView(circle);
        }
    }

    public int[] getMargin() {
        return mMargin;
    }

    public void setMargin(int[] margin) {
        mMargin = margin;
    }

    public int getCircleSize() {
        return mCircleSize;
    }

    public void setCircleSize(int circleSize) {
        mCircleSize = circleSize;
    }

    public ArrayList<Integer> getColorList() {
        return mColorList;
    }

    public void setColorList(ArrayList<Integer> colorList) {
        mColorList = colorList;
    }
}
