package net.sxkeji.shixinandroiddemo2.test;

/**
 * description: 测试枚举
 * <br/>
 * author: shixinzhang
 * <br/>
 * data: 10/13/2016
 */
public enum MyEnum {
    /**
     * 每个枚举项 相当于是 一个 MyEnum 的子类，需要实现方法
     */
    RED {
        @Override
        public String getName() {
            return "I'm red dream.";
        }

    },

    GREEN{
        @Override
        public String getName() {
            return "I'm green hat.";
        }
    },

    BLUE{
        @Override
        public String getName() {
            return "I'm blue sky.";
        }
    };

    public abstract String getName();   //抽象方法

    private int age;

    //构造器，不能 public, why
    private MyEnum(){}

    //构造器，不能 public, why
    MyEnum(int age){
        this.age = age;
    }
}
