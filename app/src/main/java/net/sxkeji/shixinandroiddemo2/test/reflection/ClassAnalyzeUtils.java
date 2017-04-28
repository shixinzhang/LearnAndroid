package net.sxkeji.shixinandroiddemo2.test.reflection;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

/**
 * description: 反射得到类的信息
 * <br/>
 * author: shixinzhang
 * <br/>
 * data: 10/13/2016
 */
public class ClassAnalyzeUtils {

    /**
     * 分析一个类，输出，返回一个 Set,包含所有方法
     * @param enumClass
     * @return
     */
    public static Set<String> analyze(Class<?> enumClass){
        Set<String> methods = new HashSet<>();  //为什么改成 TreeSet 上面的测试运行就会报错？？
        System.out.println("正在分析: " + enumClass);
        System.out.print("实现的接口：");

        //遍历接口
        for (Type type : enumClass.getGenericInterfaces()) {
            System.out.print(type + ", ");
        }
        System.out.println("");

        System.out.println("超类：" + enumClass.getSuperclass());
        System.out.println("包含的方法：");

        //遍历所有方法，添加到 Set 中
        for (Method method : enumClass.getMethods()) {
            methods.add(method.getName());
        }
        System.out.println(methods);

        return methods;
    }
}
