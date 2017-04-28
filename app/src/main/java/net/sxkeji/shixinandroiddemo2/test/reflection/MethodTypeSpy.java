package net.sxkeji.shixinandroiddemo2.test.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.HashMap;

/**
 * <br/> Description:   反射获取方法的信息
 * <p>
 * <br/> Created by shixinzhang on 17/1/15.
 * <p>
 * <br/> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class MethodTypeSpy extends BaseTestClass {
    private static final String fmt = "%24s:   %s\n";
    private static final String CLASS_NAME = "java.io.PrintStream";
    private static final String CLASS_NAME_HASHMAP = "java.util.HashMap";

    @Deprecated
    public static void main(String[] args) throws ClassNotFoundException {
//        MethodTypeSpy methodTypeSpy = new MethodTypeSpy();
//        Class<? extends MethodTypeSpy> cls = methodTypeSpy.getClass();
        Class<?> cls = Class.forName(CLASS_NAME_HASHMAP);

        printFormat("Class：%s \n\n", cls.getCanonicalName());
        Method[] declaredMethods = cls.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            printFormat(fmt, "Method name", declaredMethod.getName());  //获得单独的方法名
            //获得完整的方法信息（包括修饰符、返回值、路径、名称、参数、抛出值）
            printFormat(fmt, "toGenericString", declaredMethod.toGenericString());

            int modifiers = declaredMethod.getModifiers();      //获得修饰符
            printFormat(fmt, "Modifiers", Modifier.toString(modifiers));

            System.out.format(fmt, "ReturnType", declaredMethod.getReturnType());   //获得返回值
            System.out.format(fmt, "getGenericReturnType", declaredMethod.getGenericReturnType());//获得完整信息的返回值

            TypeVariable<Method>[] typeParameters = declaredMethod.getTypeParameters();
            for (TypeVariable<Method> typeParameter : typeParameters) {
                System.out.format(fmt, "TypeParameter", typeParameter.getName());
            }

            Class<?>[] parameterTypes = declaredMethod.getParameterTypes(); //获得参数类型
            Type[] genericParameterTypes = declaredMethod.getGenericParameterTypes();
            for (int i = 0; i < parameterTypes.length; i++) {
                System.out.format(fmt, "ParameterType", parameterTypes[i]);
                System.out.format(fmt, "GenericParameterType", genericParameterTypes[i]);
            }

            Class<?>[] exceptionTypes = declaredMethod.getExceptionTypes();     //获得异常名称
            Type[] genericExceptionTypes = declaredMethod.getGenericExceptionTypes();
            for (int i = 0; i < exceptionTypes.length; i++) {
                System.out.format(fmt, "ExceptionTypes", exceptionTypes[i]);
                System.out.format(fmt, "GenericExceptionTypes", genericExceptionTypes[i]);
            }

            Annotation[] annotations = declaredMethod.getAnnotations(); //获得注解
            for (Annotation annotation : annotations) {
                System.out.format(fmt, "Annotation", annotation);
                System.out.format(fmt, "AnnotationType", annotation.annotationType());
            }

            printFormat("\n\n");
        }
    }
}
