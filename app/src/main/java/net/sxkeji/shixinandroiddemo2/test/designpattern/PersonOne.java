package net.sxkeji.shixinandroiddemo2.test.designpattern;

/**
 * <br/> Description:
 *          常见的构造方式之一: 含多个参数的构造函数
 *          优点：
 *              简单
 *          缺点：
 *              只适用于成员变量少的情况，太多了不容易理解、维护
 * <p>
 * <br/> Created by shixinzhang on 16/11/20.
 * <p>
 * <br/> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class PersonOne {
    //固定不变的对象，一般变量需要声明为 final
    private final String mName;     //必选，final 类型需要在 构造器中初始化，不允许不初始化它的构造器存在
    private String mLocation;       //可选
    private String mJob;            //可选
    private String mHabit;          //可选

    public PersonOne(String name) {
        mName = name;
    }

    public PersonOne(String location, String name) {
        mLocation = location;
        mName = name;
    }

    public PersonOne(String name, String location, String job) {
        mName = name;
        mLocation = location;
        mJob = job;
    }

    public PersonOne(String name, String location, String job, String habit) {
        mName = name;
        mLocation = location;
        mJob = job;
        mHabit = habit;
    }
}
