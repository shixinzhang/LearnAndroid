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

package top.shixinzhang.hook_sample;

import android.app.Application;
import android.app.Instrumentation;
import android.util.Log;

import java.lang.reflect.Field;

/**
 * Description:
 * <br> 插件化 ，Hook 练习
 * <p>
 * <br> Created by shixinzhang on 17/8/18.
 * <p>
 * <br> Email: shixinzhang2016@gmail.com
 * <p>
 * <br> https://about.me/shixinzhang
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        hookActivityStart();
    }

    /**
     * 简单的 hook 启动 activity
     * 思路：
     *  启动由 ActivityThread 中的 mInstrumentation 进行，而一个进程中只有一个静态 activityThread 对象
     *  因此只要替换这个对象的 mInstrumentation 为新的，即可拦截 Activity 启动
     *  使用反射
     */
    private void hookActivityStart() {
        Class activityThreadClass;

        try {
            activityThreadClass = Class.forName("android.app.ActivityThread");
            //拿到单例的 ActivityThread 对象
            Field sCurrentActivityThreadField = activityThreadClass.getDeclaredField("sCurrentActivityThread");
            sCurrentActivityThreadField.setAccessible(true);
            Object currentActivityThread = sCurrentActivityThreadField.get(null);

            //拿到原来的 Instrumentation
            Field mInstrumentationField = activityThreadClass.getDeclaredField("mInstrumentation");
            mInstrumentationField.setAccessible(true);
            Instrumentation mInstrumentation = (Instrumentation) mInstrumentationField.get(currentActivityThread);
            //创建假的
            Instrumentation fakeInstrumentation = new FakeInstrumentation(mInstrumentation);
            //替换
            mInstrumentationField.set(currentActivityThread, fakeInstrumentation);

            Log.d("shixinzhang", "Replace mInstrumentation success");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
