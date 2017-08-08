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

package net.sxkeji.shixinandroiddemo2.activity.annotation;

import android.os.Bundle;
import android.support.annotation.CheckResult;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.Size;
import android.support.annotation.UiThread;
import android.support.annotation.WorkerThread;

import net.sxkeji.shixinandroiddemo2.BaseActivity;
import net.sxkeji.shixinandroiddemo2.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Description:
 * <br> 官方注解库练习
 * <p>
 * https://developer.android.com/studio/write/annotations.html
 * https://blog.mindorks.com/improve-your-android-coding-through-annotations-26b3273c137a
 * <p>
 * <br> Created by shixinzhang on 17/8/7.
 * <p>
 * <br> Email: shixinzhang2016@gmail.com
 * <p>
 * <br> https://about.me/shixinzhang
 */

public class SupportAnnotationSampleActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);


        setText(null);

        List<String> nameList = new ArrayList<>(2);
        nameList.add("a");
        nameList.add("b");
        setRecyclerViewData(nameList);

        setRecyclerViewData(new String[]{"a", "b"});

        substring("a");

        startWithHttp("shixinzhang.top");


        String withHttp = startWithHttp("shixinzhang.top");

        getDataFromNet();

        new Thread(new Runnable() {
            @Override
            public void run() {
                getDataFromNet();

//                updateView();     为啥这个不报错呢？
            }
        }).start();
    }


    private void setText(@NonNull String text) {
        //...
    }

    private void setRecyclerViewData(@Size(min = 3) List<String> nameList) {    //集合，不起作用
        //...
    }

    private void setRecyclerViewData(@Size(min = 3) String[] nameList) {
        //...
    }

    private void substring(@Size(min = 2) String oldString){
        //...
    }

    @CheckResult(suggest = "#substring(string)")
    private String startWithHttp(String oldString){
        //...
        return "http://" + oldString;
    }

    @WorkerThread
    private Object getDataFromNet(){
        return null;
    }

    @UiThread
    private void updateView(){
        //...
    }

    @Override
    public void initViews() {

    }

    @Override
    public void loadData() {

    }
}
