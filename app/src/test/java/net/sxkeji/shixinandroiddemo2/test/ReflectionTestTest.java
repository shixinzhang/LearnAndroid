package net.sxkeji.shixinandroiddemo2.test;

import org.junit.Test;

import java.lang.reflect.Modifier;

import static org.junit.Assert.*;

/**
 * <br/> Description:
 * <p>
 * <br/> Created by shixinzhang on 17/1/4.
 * <p>
 * <br/> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */
public class ReflectionTestTest {
    @Test
    public void reflectFirst() throws Exception {
        Class<?> aClass = Class.forName("[Ljava.lang.String;"); //String 数组
        String[] o = (String[]) aClass.newInstance();
        o[0] = "string array";
        System.out.println(o[0]);
        Class<?> aClass1 = Class.forName("java.lang.String");
        String string = (String) aClass1.newInstance();
        string = "ss";
        System.out.println(string);

        Class<Integer> integerWrapper = Integer.TYPE;
        Class<Double> doubleWrapper = Double.TYPE;
        Class<Void> voidWrapper = Void.TYPE;

        Class<? super Double> superclass = doubleWrapper.getSuperclass();
        Class<?>[] classes = superclass.getClasses();
        Class<?>[] declaredClasses = superclass.getDeclaredClasses();

    }

    @Test
    public void reflectModifiers(){
        Class<String> stringClass = String.class;
        int modifiers = stringClass.getModifiers();     //返回值是 修饰符的 Int 值
        boolean isFinal = Modifier.isFinal(modifiers);
    }

}