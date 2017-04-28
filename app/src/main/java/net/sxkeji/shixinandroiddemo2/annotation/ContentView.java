package net.sxkeji.shixinandroiddemo2.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <br/> Description:简单的运行时注解，结合反射使用
 * <p>
 * <br/> Created by shixinzhang on 16/12/13.
 * <p>
 * <br/> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ContentView {
    //属性叫 value ，在使用时可以直接传参数即可，不必显式的指明键值对，是一种快捷方法
    int value() ;
}
