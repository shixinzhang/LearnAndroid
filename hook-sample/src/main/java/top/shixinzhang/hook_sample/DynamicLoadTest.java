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

import top.shixinzhang.hook_sample.classloader.ICalculator;
import top.shixinzhang.hook_sample.classloader.NetworkClassLoader;

/**
 * 从网络加载一个类，可以有两种使用方式：
 * <p>
 * 1.使用反射
 * 2.使用接口
 * <p>
 * 不能直接调用，因为客户端的类加载器找不到这个类
 */
public class DynamicLoadTest {

    public static void main(String[] args) {
        String url = "http://localhost:8080/ClassloaderTest/classes";
        NetworkClassLoader ncl = new NetworkClassLoader(url);
        String basicClassName = "top.shixinzhang.hook_sample.impl.CalculatorBasic";
        String advancedClassName = "top.shixinzhang.hook_sample.impl.CalculatorAdvanced";
        try {
            Class<?> clazz = ncl.loadClass(basicClassName);
            //引用创建时的类型是接口
            ICalculator calculator = (ICalculator) clazz.newInstance();
            System.out.println(calculator.getVersion());

            clazz = ncl.loadClass(advancedClassName);
            calculator = (ICalculator) clazz.newInstance();
            System.out.println(calculator.getVersion());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
