package net.sxkeji.shixinandroiddemo2.test.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * <br/> Description:
 * <p>
 * <br/> Created by shixinzhang on 17/1/14.
 * <p>
 * <br/> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class FieldModifierSpy extends BaseTestClass {
    volatile int number;
    transient String name;

    class InnerClass {
    }

    static final String CLASS_NAME = "net.sxkeji.shixinandroiddemo2.test.reflection.FieldModifierSpy";

    public static void main(String[] args) {
        try {
            Class<?> aClass = Class.forName(CLASS_NAME);
            printFormat("Class：%s\n\n", aClass.getCanonicalName());
            Field[] declaredFields = aClass.getDeclaredFields();
            for (Field declaredField : declaredFields) {
                printFormat("Field：%s\n", declaredField.getName());
                System.out.format("isSynthetic：%b , isEnumConstant: %b\n", declaredField.isSynthetic(), declaredField.isEnumConstant());
                int modifiers = declaredField.getModifiers();
                printModifier(modifiers);
                printFormat("\n\n");
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void printModifier(int modifiers) {
        printFormat("Modifiers： ");
        if (Modifier.isPublic(modifiers)) {
            printFormat("public ");
        }
        if (Modifier.isPrivate(modifiers)) {
            printFormat("private ");
        }
        if (Modifier.isProtected(modifiers)) {
            printFormat("protected  ");
        }
        if (Modifier.isStatic(modifiers)) {
            printFormat("static ");
        }
        if (Modifier.isFinal(modifiers)) {
            printFormat("final  ");
        }
        if (Modifier.isTransient(modifiers)) {
            printFormat("transient  ");
        }
        if (Modifier.isVolatile(modifiers)) {
            printFormat("volatile   ");
        }

        printFormat("\n");
    }
}
