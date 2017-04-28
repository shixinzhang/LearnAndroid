package net.sxkeji.shixinandroiddemo2.test.reflection;

import android.text.TextUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;

/**
 * <br/> Description:
 * <p>
 * <br/> Created by shixinzhang on 17/1/18.
 * <p>
 * <br/> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class ConstructorTypeSpy<T> extends BaseTestClass {
    private String name;

    private ConstructorTypeSpy(){
    }

    public ConstructorTypeSpy(String name) throws IllegalArgumentException{
        if (TextUtils.isEmpty(name)){
            throw new IllegalArgumentException("Argument of constructor cannot be empty!");
        }
        this.name = name;
    }

    public ConstructorTypeSpy(String name, T info){
    }

    public static void main(String[] args) {
        ConstructorTypeSpy object = new ConstructorTypeSpy();
        Class<? extends ConstructorTypeSpy> cls = object.getClass();
        Constructor<?>[] declaredConstructors = cls.getDeclaredConstructors();      //获取 declared 构造方法
        for (Constructor<?> declaredConstructor : declaredConstructors) {
            printFormat("Constructor：\n %24s\n", declaredConstructor.toGenericString());    //全部信息

            Annotation[] declaredAnnotations = declaredConstructor.getDeclaredAnnotations();

            int modifiers = declaredConstructor.getModifiers();
            printFormat("Modifiers：%24s \n", Modifier.toString(modifiers));     //修饰符

            Class<?>[] parameterTypes = declaredConstructor.getParameterTypes();    //参数
            printFormat("ParameterType：\n");
            for (Type parameterType : parameterTypes) {
                printFormat("%24s \n", parameterType.toString());
            }

            Type[] genericParameterTypes = declaredConstructor.getGenericParameterTypes();  //泛型参数
            printFormat("GenericParameterType：\n");
            for (Type genericParameterType : genericParameterTypes) {
                printFormat("%24s \n", genericParameterType.toString());
            }
            printFormat("\n");
        }
    }
}
