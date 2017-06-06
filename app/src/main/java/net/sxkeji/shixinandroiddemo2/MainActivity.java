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

package net.sxkeji.shixinandroiddemo2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import net.sxkeji.shixinandroiddemo2.adapter.MainModuleAdapter;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Description:
 * <br>
 * <p>
 * <br> Created by shixinzhang on 17/6/6.
 * <p>
 * <br> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class MainActivity extends BaseActivity {

    @BindView(R.id.recycler_main)
    RecyclerView mRecyclerMain;

    private GridLayoutManager mLayoutManager;
    private MainModuleAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initViews();
    }

    @Override
    public void initViews() {
        mLayoutManager = new GridLayoutManager(this, 2);
        mAdapter = new MainModuleAdapter(this, Arrays.asList("普通练习项目", "熟悉第三方框架项目", "自定义框架实验室", "待定..."));
        mRecyclerMain.setLayoutManager(mLayoutManager);
        mRecyclerMain.setAdapter(mAdapter);
    }

    @Override
    public void loadData() {

    }
}
