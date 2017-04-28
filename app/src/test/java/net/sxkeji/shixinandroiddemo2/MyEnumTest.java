package net.sxkeji.shixinandroiddemo2;

import android.support.annotation.IntDef;

import net.sxkeji.shixinandroiddemo2.test.reflection.ClassAnalyzeUtils;
import net.sxkeji.shixinandroiddemo2.test.MyEnum;

import org.junit.Test;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * description: 测试枚举
 * <br/>
 * author: shixinzhang
 * <br/>
 * data: 10/13/2016
 */
public class MyEnumTest {

    @IntDef({RED, GREEN, BLUE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface MyInterfaceEnum{
        public abstract int getColorInt();
    }

    public static final int RED = 0;
    public static final int GREEN = 1;
    public static final int BLUE = 2;

    @Test
    public void testMyEnum() {
        MyEnum myEnum = MyEnum.BLUE;
        List list = new LinkedList();
        System.out.println(list);
        System.out.println(myEnum.getName());
        System.out.println(myEnum.name());
        System.out.println(myEnum.ordinal());

        System.out.println("\n**********************************");
        System.out.println("************** MyEnum **************");
        Set<String> myEnumMethods = ClassAnalyzeUtils.analyze(MyEnum.class);

        System.out.println("\n**********************************");
        System.out.println("************** Enum **************");
        Set<String> enumMethods = ClassAnalyzeUtils.analyze(Enum.class);

        System.out.println("\n自定义枚举是否包含 Enum 全部方法：" + myEnumMethods.contains(enumMethods));

        myEnumMethods.removeAll(enumMethods);
        System.out.println("自定义枚举额外的方法：" + myEnumMethods);

    }

    public class ChildClass {
        MyInnerEnum innerEnum = MyInnerEnum.BLUE;
    }

    public  enum MyInnerEnum {
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
//        private MyEnum(){}
//
//        //构造器，不能 public, why
//        MyEnum(int age){
//            this.age = age;
//        }
    }

    public boolean remove(Object object) {
        Iterator<?> it = iterator();
        while (it.hasNext()) {
            if (object != null ? object.equals(it.next()) : it.next() == null) {
                it.remove();
                return true;
            }
        }
        return false;
    }

    Iterator iterator(){
        return null;
    }
}
