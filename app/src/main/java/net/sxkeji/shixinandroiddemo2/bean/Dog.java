package net.sxkeji.shixinandroiddemo2.bean;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * <br/> Description: Realm 实体类
 * <p>
 * <br/> Created by shixinzhang on 17/1/13.
 * <p>
 * <br/> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class Dog extends RealmObject {
    //不能为空
    @Required
    private String name;

    //忽略，不存储到本地
    @Ignore
    private int age;

    //主键
    @PrimaryKey
    private String id;

    //添加一个索引，查询时可以快一点
    @Index
    private String index;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
