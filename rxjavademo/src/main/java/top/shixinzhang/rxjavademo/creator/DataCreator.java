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

package top.shixinzhang.rxjavademo.creator;

import java.util.Arrays;
import java.util.List;

import top.shixinzhang.rxjavademo.model.Clazz;
import top.shixinzhang.rxjavademo.model.Grade;
import top.shixinzhang.rxjavademo.model.People;

/**
 * Description:
 * <br> 造数据
 * <p>
 * <br> Created by shixinzhang on 17/7/12.
 * <p>
 * <br> Email: shixinzhang2016@gmail.com
 * <p>
 * <br> https://about.me/shixinzhang
 */

public class DataCreator {
    public static Grade getGradeData() {
        //数据源
        Clazz secondClass = new Clazz("四年级二班", Arrays.asList("张三", "李四", "王五"));
        Clazz thirdClass = new Clazz("四年级三班", Arrays.asList("赵六", "喜洋洋", "灰太狼"));
        Grade forthGrade = new Grade("四年级", Arrays.asList(secondClass, thirdClass));
        return forthGrade;
    }

    public static List<People> getPeopleData() {
        return Arrays.asList(new People(15, "大熊"), new People(13, "静安"), new People(15, "胖虎"), new People(14, "多来A梦"), new People(13, "拭心"));
    }
}
