package net.sxkeji.shixinandroiddemo2.test.reflection;

import android.util.Log;

import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.List;

/**
 * <br/> Description:
 * <p>
 * <br/> Created by shixinzhang on 17/1/14.
 * <p>
 * <br/> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class ClassDeclarationSpy extends BaseTestClass{
    private static final String TAG = "ClassDeclarationSpy";
    private static final String CLASS_NAME = "java.util.concurrent.ConcurrentNavigableMap";
    private static final String CLASS_NAME_STRING = "java.lang.String";
    private static final String CLASS_NAME_COMPAT_ACTIVITY = "android.support.v7.app.AppCompatActivity";
    private static final String CLASS_NAME_ACTIVITY = "android.app.Activity";
    private static final String CLASS_NAME_EXCEPTION = "java.io.InterruptedIOException";

    public static void main(String[] args) {
        try {
            Class<?> aClass = Class.forName(CLASS_NAME_EXCEPTION);
            printFormat("Class: %n    %s%n%n", aClass.getCanonicalName());    //规范名称
            printFormat("Modifier: %n   %s%n%n", Modifier.toString(aClass.getModifiers())); //修饰符

            System.out.format("Type Parameters:%n");
            TypeVariable<? extends Class<?>>[] typeParameters = aClass.getTypeParameters();
            if (typeParameters.length != 0){
                System.out.format(" ");
                for (TypeVariable<? extends Class<?>> parameter : typeParameters) {
                    printFormat("%s ",parameter.getName());
                }
                System.out.format("%n%n");
            }else {
                System.out.format(" -- No Type Parameters -- \n\n");
            }

            System.out.format("Implemented Interfaces:\n");
            Type[] genericInterfaces = aClass.getGenericInterfaces();
            if (genericInterfaces.length != 0){
                for (Type anInterface : genericInterfaces) {
                    printFormat("   %s\n", anInterface.toString());
                }
                System.out.format("\n");
            }else {
                System.out.format(" -- No Super Classes -- \n\n");
            }

            printFormat("Inheritance Path：\n");
            ArrayList<Class> classArrayList = new ArrayList<>();
            printAncestor(aClass, classArrayList);
            printFormat("\n");

            printFormat("Annotations: \n");
            Annotation[] annotations = aClass.getAnnotations();
            if (annotations.length != 0) {
                for (Annotation annotation : annotations) {
                    printFormat("   %s\n", annotation.toString());
                }
                printFormat("\n");
            }else {
                printFormat(" -- No Annotations -- \n\n");
            }


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Log.e(TAG, "ClassNotFoundException " + e.getMessage());
        }
    }

    private static void printAncestor(Class<?> c, List<Class> list){
        Class<?> ancestor = c.getSuperclass();
        if (ancestor != null){
            printFormat("   %s\n", c.getCanonicalName());
            list.add(ancestor);
            printAncestor(ancestor, list);
        }
    }
}
