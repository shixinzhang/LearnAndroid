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

package top.shixinzhang.rxjavademo.model;

/**
 * Description:
 * <br>
 * <p>
 * <br> Created by shixinzhang on 17/7/12.
 * <p>
 * <br> Email: shixinzhang2016@gmail.com
 * <p>
 * <br> https://about.me/shixinzhang
 */

public class People {
    private int age;
    private String name;

    public People(final int age, final String name) {
        this.age = age;
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public People setAge(final int age) {
        this.age = age;
        return this;
    }

    public String getName() {
        return name;
    }

    public People setName(final String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return "People{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }
}
