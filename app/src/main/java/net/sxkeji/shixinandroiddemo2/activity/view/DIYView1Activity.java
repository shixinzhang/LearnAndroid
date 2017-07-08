/*
 * Copyright (c) 2017. shixinzhang (shixinzhang2016@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.sxkeji.shixinandroiddemo2.activity.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import net.sxkeji.shixinandroiddemo2.BaseActivity;
import net.sxkeji.shixinandroiddemo2.R;
import net.sxkeji.shixinandroiddemo2.view.CircleColorView;
import net.sxkeji.shixinandroiddemo2.view.ListCircleColorView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import top.shixinzhang.sxframework.views.MarqueeTextView;

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
    @BindView(R.id.ts_activities)
    TextSwitcher mTsActivities;

    String[] activityTitles = new String[]{"震惊，最帅安卓开发张拭心竟然这样评价上海", "这个故事男人听了沉默，女人听了流泪",
            "一生劳碌的他在去世前竟然说出这样的话"};
    int currentTitle;
    @BindView(R.id.mtv_activities)
    MarqueeTextView mMtvActivities;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diy_view_1);
        ButterKnife.bind(this);
        initViews();

    }

    @Override
    public void initViews() {
        mTsActivities.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView textView = new TextView(DIYView1Activity.this);
                textView.setTextSize(16);
                textView.setTextColor(Color.MAGENTA);
                return textView;
            }
        });
        clickTitle();

        initMarqueueView();

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

    private void initMarqueueView() {
        mMtvActivities.setResources(activityTitles);
        mMtvActivities.setTextStillTime(3_000L);
    }

    @OnClick(R.id.ts_activities)
    public void clickTitle() {
        mTsActivities.setText(activityTitles[currentTitle++ % activityTitles.length]);
    }

    @Override
    public void loadData() {

    }

    public void addListeners() {

    }
}
