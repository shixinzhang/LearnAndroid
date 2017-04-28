package net.sxkeji.shixinandroiddemo2.test.reflection;

import android.text.TextUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;

/**
 * <br/> Description:   反射调用方法
 * <p>
 * <br/> Created by shixinzhang on 17/1/16.
 * <p>
 * <br/> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class MethodInvoke extends BaseTestClass {

    private boolean checkString(String s) {
        printFormat("checkString: %s\n", s);
        return TextUtils.isEmpty(s);
    }

    private static void saySomething(String something) {
        System.out.println(something);
    }

    private String onEvent(TestEvent event) {
        System.out.format("Event name: %s\n", event.getEventName());
        return event.getResult();
    }

    static class TestEvent {
        private String eventName;
        private String result;

        public TestEvent(String eventName, String result) {
            this.eventName = eventName;
            this.result = result;
        }

        public String getResult() {
            return result;
        }

        public String getEventName() {
            return eventName;
        }
    }

    public static void main(String[] args) {
        try {
            Class<?> cls = Class.forName("net.sxkeji.shixinandroiddemo2.test.reflection.MethodInvoke");
            MethodInvoke object = (MethodInvoke) cls.newInstance();
            Method[] declaredMethods = cls.getDeclaredMethods();
            for (Method declaredMethod : declaredMethods) {
                String methodName = declaredMethod.getName();       //获取方法名
                Type returnType = declaredMethod.getGenericReturnType();    //获取带泛型的返回值类型
                int modifiers = declaredMethod.getModifiers();      //获取方法修饰符

//                declaredMethod.setAccessible(true);

                if (methodName.equals("onEvent")) {
                    TestEvent testEvent = new TestEvent("shixin's Event", "cuteType");
                    try {
                        Object invokeResult = declaredMethod.invoke(object, testEvent);
                        System.out.format("Invoke of %s, return %s \n", methodName, invokeResult.toString());
                    } catch (InvocationTargetException e) {     //处理被调用方法可能抛出的异常
                        Throwable cause = e.getCause();
                        System.out.format("Invocation of %s failed:  %s\n", methodName, cause.getMessage());
                    }
                } else if (returnType == boolean.class) {
                    try {
                        declaredMethod.invoke(object, "shixin's parameter");
                    } catch (InvocationTargetException e) {
                        Throwable cause = e.getCause();
                        System.out.format("Invocation of %s failed:  %s\n", methodName, cause.getMessage());
                    }
                }else if (Modifier.isStatic(modifiers) && !methodName.equals("main")){    //静态方法，调用时 object 直接传入 null
                    try {
                        declaredMethod.invoke(null, "static method");
                    } catch (InvocationTargetException e) {
                        Throwable cause = e.getCause();
                        System.out.format("Invocation of %s failed:  %s\n", methodName, cause.getMessage());
                    }
                }

            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
