package net.sxkeji.shixinandroiddemo2.test.reflection;

import java.lang.reflect.Field;

/**
 * <br/> Description:
 * <p>
 * <br/> Created by shixinzhang on 17/1/14.
 * <p>
 * <br/> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class FieldSpy<T> extends BaseTestClass {
    public boolean[][] b = {{true, true}, {false, false}};
    public String name = "shixinzhang";
    public Integer integer = 23;
    public T type;

    public static final String CLASS_NAME = "net.sxkeji.shixinandroiddemo2.test.reflection.FieldSpy";

    public static void main(String[] args) {
        try {
            Class<?> aClass = Class.forName(CLASS_NAME);
            Field[] fields = aClass.getFields();
            for (Field field : fields) {
                printFormat("Field：%s \n",field.getName());
                printFormat("Type：\n  %s\n", field.getType().getCanonicalName());
                printFormat("GenericType:\n  %s\n", field.getGenericType().toString());
                printFormat("\n\n");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
