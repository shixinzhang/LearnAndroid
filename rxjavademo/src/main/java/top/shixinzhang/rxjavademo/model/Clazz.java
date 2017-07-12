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

import java.util.List;

/**
 * Description:
 * <br> 班级
 * <p>
 * <br> Created by shixinzhang on 17/7/12.
 * <p>
 * <br> Email: shixinzhang2016@gmail.com
 * <p>
 * <br> https://about.me/shixinzhang
 */

public class Clazz {
    private String name;
    private List<String> studentNameList;

    public Clazz(final String name, final List<String> studentNameList) {
        this.name = name;
        this.studentNameList = studentNameList;
    }

    public String getName() {
        return name;
    }

    public Clazz setName(final String name) {
        this.name = name;
        return this;
    }

    public List<String> getStudentNameList() {
        return studentNameList;
    }

    public Clazz setStudentNameList(final List<String> studentNameList) {
        this.studentNameList = studentNameList;
        return this;
    }

    @Override
    public String toString() {
        return "Clazz{" +
                "name='" + name + '\'' +
                ", studentNameList=" + studentNameList +
                '}';
    }
}
