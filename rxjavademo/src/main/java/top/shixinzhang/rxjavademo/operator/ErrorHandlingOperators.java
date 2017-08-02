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

package top.shixinzhang.rxjavademo.operator;

/**
 * Description:
 * <br> 错误处理操作符
 * http://reactivex.io/documentation/operators/catch.html
 * <p>
 * <br> Created by shixinzhang on 17/7/27.
 * <p>
 * <br> Email: shixinzhang2016@gmail.com
 * <p>
 * <br> https://about.me/shixinzhang
 */

public class ErrorHandlingOperators extends BaseOperators {

    private ErrorHandlingOperators() {
        //do some init work
    }

    private volatile static ErrorHandlingOperators mInstance = new ErrorHandlingOperators();

    public static ErrorHandlingOperators getInstance() {
        return mInstance;
    }

    public static void destroyInstance() {
        mInstance = null;
    }

    public static void test() {
        getInstance().testErrorHandingOperators();
    }

    private void testErrorHandingOperators() {
        onErrorResumeNext();
    }

    private void onErrorResumeNext() {

    }
}
