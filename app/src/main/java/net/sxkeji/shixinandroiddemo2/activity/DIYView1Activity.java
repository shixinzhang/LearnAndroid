package net.sxkeji.shixinandroiddemo2.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import net.sxkeji.shixinandroiddemo2.BaseActivity;
import net.sxkeji.shixinandroiddemo2.R;
import net.sxkeji.shixinandroiddemo2.view.CircleColorView;
import net.sxkeji.shixinandroiddemo2.view.ListCircleColorView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * description:
 * <br/>
 * author: shixinzhang
 * <br/>
 * data: 10/25/2016
 */
public class DIYView1Activity extends BaseActivity {

    @BindView(R.id.circle_color)
    CircleColorView mCircleColor;
    @BindView(R.id.ll_color_list)
    LinearLayout mLlColorList;
    @BindView(R.id.list_circle_color)
    ListCircleColorView mListCircleColor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diy_view_1);
        ButterKnife.bind(this);
        initViews();
    }

    @Override
    public void initViews() {
        mCircleColor.setCircleSize(8);
        mCircleColor.setCircleColor(Color.parseColor("#ffffff"));
        mCircleColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DIYView1Activity.this, "click", 0).show();
            }
        });

        for (int i = 0; i < 8; i++) {
            CircleColorView circle = new CircleColorView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.rightMargin = 20;
            circle.setLayoutParams(params);
            circle.setCircleSize(50);
            if (i % 3 == 0) {
                circle.setCircleColor(Color.parseColor("#ffffff"));
            } else if ((i % 2 == 0)) {
                circle.setCircleColor(Color.parseColor("#255A99"));
            } else {
                circle.setCircleColor(Color.parseColor("#d70000"));
            }

            mLlColorList.addView(circle);
        }


        mListCircleColor.setCircleSize(8);
        mListCircleColor.setMargin(new int[]{0, 0, 10, 0});
        ArrayList<Integer> colorList = new ArrayList<>();
        colorList.add(Color.BLUE);
        colorList.add(Color.CYAN);
        colorList.add(Color.GREEN);
        colorList.add(Color.LTGRAY);
        colorList.add(Color.YELLOW);
        colorList.add(Color.WHITE);
        mListCircleColor.setColorList(colorList);
        mListCircleColor.show();
    }

    @Override
    public void loadData() {

    }

    @Override
    public void addListeners() {

    }
}
