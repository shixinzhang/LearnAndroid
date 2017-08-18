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

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.lang.reflect.Method;

/**
 * Description:
 * <br> 自定义的 Instrumentation
 * <p>
 * <br> Created by shixinzhang on 17/8/18.
 * <p>
 * <br> Email: shixinzhang2016@gmail.com
 * <p>
 * <br> https://about.me/shixinzhang
 */

public class FakeInstrumentation extends Instrumentation {

    private final String TAG = this.getClass().getSimpleName();

    private Instrumentation mBase;

    public FakeInstrumentation(final Instrumentation base) {
        mBase = base;
    }

    public ActivityResult execStartActivity(
            Context who, IBinder contextThread, IBinder token, Activity target,
            Intent intent, int requestCode, Bundle options) {

        Log.d("shixinzhang", "Before execStartActivity, log by shixinzhang!");
        if (intent != null && intent.getComponent() != null) {
            Toast.makeText(who, "去 " + intent.getComponent().getClassName() + " 的跳转被拦截了", Toast.LENGTH_SHORT).show();
        }

        Log.d(TAG, "参数如下: \n" + "who = [" + who + "], " +
                "\ncontextThread = [" + contextThread + "], \ntoken = [" + token + "], " +
                "\ntarget = [" + target + "], \nintent = [" + intent +
                "], \nrequestCode = [" + requestCode + "], \noptions = [" + options + "]");

        try {
            Method execStartActivityMethod = Instrumentation.class.getDeclaredMethod("execStartActivity", Context.class, IBinder.class, IBinder.class,
                    Activity.class, Intent.class, int.class, Bundle.class);
            execStartActivityMethod.setAccessible(true);

            return (ActivityResult) execStartActivityMethod.invoke(mBase, who, contextThread, token, target, intent, requestCode, options);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("hook failed! " + e.getMessage());
        }
    }
}
