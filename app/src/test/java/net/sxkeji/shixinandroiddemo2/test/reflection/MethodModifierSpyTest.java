package net.sxkeji.shixinandroiddemo2.test.reflection;

import net.sxkeji.shixinandroiddemo2.test.*;
import net.sxkeji.shixinandroiddemo2.test.MethodModifierSpy;

import org.junit.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.junit.Assert.*;

/**
 * <br/> Description:
 * <p>
 * <br/> Created by shixinzhang on 17/1/16.
 * <p>
 * <br/> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */
public class MethodModifierSpyTest {

    @Test
    public void doNothing(){
        new InnerClass().reflectMethodModifier();
    }

    private String referenceField = "java.lang.String";

    class InnerClass {

        public void reflectMethodModifier(){
            InnerClass innerClass = new InnerClass();
            Class<? extends InnerClass> cls = innerClass.getClass();
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

        public void print() {
            System.out.println(referenceField);
        }

        public final void varArgsMethod(String... strings) {

        }


        void printFormat(String format, String string) {
            System.out.format(format, string);
        }
    }
}