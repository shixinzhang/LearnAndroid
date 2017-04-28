package net.sxkeji.shixinandroiddemo2.test.reflection;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * <br/> Description:   反射获取方法的修饰符
 * <p>
 * <br/> Created by shixinzhang on 17/1/16.
 * <p>
 * <br/> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class MethodModifierSpy extends BaseTestClass {

    private final static String CLASS_NAME = "java.lang.String";

    public static void main(String[] args) {
        MethodModifierSpy methodModifierSpy = new MethodModifierSpy();
        Class<? extends MethodModifierSpy> cls = methodModifierSpy.getClass();
        printFormat("Class: %s \n\n", cls.getCanonicalName());

        Method[] declaredMethods = cls.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            printFormat("\n\nMethod name： %s \n", declaredMethod.getName());
            printFormat("Method toGenericString： %s \n", declaredMethod.toGenericString());

            int modifiers = declaredMethod.getModifiers();
            printFormat("Method Modifiers： %s\n", Modifier.toString(modifiers));

            System.out.format("synthetic= %-5b,  var_args= %-5b,  bridge= %-5b \n"
                    , declaredMethod.isSynthetic(), declaredMethod.isVarArgs(), declaredMethod.isBridge());
        }

    }

    public final void varArgsMethod(String... strings) {
        String innerString = new InnerClass().innerString;
    }

    class InnerClass{
        String innerString = "InnerString by shixin";
    }

}
