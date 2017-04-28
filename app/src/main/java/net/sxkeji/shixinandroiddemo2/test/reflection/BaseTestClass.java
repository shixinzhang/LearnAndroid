package net.sxkeji.shixinandroiddemo2.test.reflection;


import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;

/**
 * <br/> Description:
 * <p>
 * <br/> Created by shixinzhang on 17/1/14.
 * <p>
 * <br/> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class BaseTestClass {
    private static final String fmt = "%24s：    %s\n";

    static void printFormat(String string) {
        System.out.format( string);
    }

    static void printFormat(String format, String string) {
        System.out.format(format, string);
    }

    static void printFormat(String format, String name, String string) {
        System.out.format(format, name, string);
    }

    static void printClasses(Class<?> aClass) {
        printFormat("Classes:\n");
        Class<?>[] classes = aClass.getClasses();
        for (Class<?> cls : classes) {
            printFormat("   %s\n", cls.getCanonicalName());
        }
        if (classes.length == 0){
            printFormat("   -- No member interfaces, classes, or enums -- \n");
        }
        printFormat("\n");
    }

    static void printClassConstructors(Class c){
        Constructor[] constructors = c.getConstructors();
        System.out.format(fmt, "Number of constructor", constructors.length);
        for (Constructor constructor : constructors) {
            printConstructor(constructor);
        }

        Constructor[] declaredConstructors = c.getDeclaredConstructors();
        System.out.format(fmt, "Number of declared constructor", declaredConstructors.length);
        for (Constructor declaredConstructor : declaredConstructors) {
            printConstructor(declaredConstructor);
        }
    }

    static void printConstructor(Constructor constructor) {
        printFormat("%s\n", constructor.toGenericString());        //Android SDK 中没有 Constructor.getParameters();
    }

    static void printMethod(Method method){     //Android SDK 中没有 Method.getParameters();
    }

    static void printParameter(){

    }

    /**
     * 输出成员
     * @param members
     * @param s
     */
    public static void printMembers(Member[] members, String s) {
        printFormat("%s\n", s);
        for (Member member : members) {
            if (member instanceof Field) {
                printFormat("   %s\n", ((Field) member).toGenericString());
            } else if (member instanceof Constructor) {
                printFormat("   %s\n", ((Constructor) member).toGenericString());
            } else if (member instanceof Method) {
                printFormat("   %s\n", ((Method) member).toGenericString());
            }
        }
        if (members.length == 0) {
            printFormat("   -- No %s -- \n", s);
        }
        printFormat("\n");
    }

}
