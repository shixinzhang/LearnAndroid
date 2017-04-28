package net.sxkeji.shixinandroiddemo2.test.reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * <br/> Description:   反射调用含有可变参数的方法
 * <p>
 * <br/> Created by shixinzhang on 17/1/17.
 * <p>
 * <br/> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class VarArgsMethodInvoke extends BaseTestClass {

    public void printVarArgs(String... varArgs) {
        System.out.format("printVarArgs：\n");
        for (String arg : varArgs) {
            System.out.format("%20s\n", arg);
        }
    }

    public static void main(String[] args) {
        VarArgsMethodInvoke object = new VarArgsMethodInvoke();
        Class<? extends VarArgsMethodInvoke> cls = object.getClass();
        try {
//            Class[] argTypes = new Class[]{String[].class};
            Method declaredMethod = cls.getDeclaredMethod("printVarArgs", String[].class);
            String[] varArgs = {"shixin", "zhang"};
            declaredMethod.invoke(object, (Object) varArgs);

        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
