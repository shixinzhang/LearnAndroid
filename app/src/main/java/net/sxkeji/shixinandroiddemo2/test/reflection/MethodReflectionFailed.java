package net.sxkeji.shixinandroiddemo2.test.reflection;

import java.lang.reflect.Method;

/**
 * <br/> Description:
 * <p>
 * <br/> Created by shixinzhang on 17/1/18.
 * <p>
 * <br/> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class MethodReflectionFailed<T> extends BaseTestClass {

    public void lookUp(T t){}
    public void find(Integer integer){}

    public static void main(String[] args) {
        //虽然声明类型为 Integer，实际会被擦除
        Class<? extends MethodReflectionFailed> cls = (new MethodReflectionFailed<Integer>()).getClass();
//        Class<Integer> parameterClass = Integer.class;
        Class<Object> parameterClass = Object.class;
        try {
            Method lookUp = cls.getMethod("lookUp", parameterClass);
            printFormat("Method:    %s\n", lookUp.toGenericString());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
