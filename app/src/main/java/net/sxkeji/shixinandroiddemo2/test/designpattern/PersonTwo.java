package net.sxkeji.shixinandroiddemo2.test.designpattern;

/**
 * <br/> Description:
 *        常见的构造方式之二：使用 setter 方法挨个构造
 *        好处：
 *              易于阅读，并且可以只对有用的成员变量赋值；
 *        缺点：
 *              成员变量不可以是 final 类型，失去了不可变对象的很多好处；
 *              对象状态不连续，你必须调用 4 次 setter 方法才能得到一个具备 4 个属性值得变量，在这期间用户可能拿到不完整状态的对象。
 * <p>
 * <br/> Created by shixinzhang on 16/11/20.
 * <p>
 * <br/> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class PersonTwo {
    //固定不变的对象，一般变量需要声明为 final
    private final String mName;     //必选，final 类型需要在 构造器中初始化，不允许不初始化它的构造器存在
    private String mLocation;       //可选
    private String mJob;            //可选
    private String mHabit;          //可选

    public PersonTwo(String s) {
        this.mName = s;
    }

    public String getName() {
        return mName;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String location) {
        mLocation = location;
    }

    public String getJob() {
        return mJob;
    }

    public void setJob(String job) {
        mJob = job;
    }

    public String getHabit() {
        return mHabit;
    }

    public void setHabit(String habit) {
        mHabit = habit;
    }
}
