package net.sxkeji.shixinandroiddemo2.test.generic;

import java.util.HashMap;
import java.util.Map;

/**
 * <br/> Description:类型安全的异构容器
 *              异构指的是成员可以是不同类型
 * <p>
 * <br/> Created by shixinzhang on 16/12/9.
 * <p>
 * <br/> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class Favorite {
    private Map<Class<?>, Object> favorites = new HashMap<>();

    /**
     * 放弃了键和值之间的"类型联系" ？？
     * @param type
     * @param instance
     * @param <T>
     */
    public <T> void putFavorite(Class<T> type, T instance) {
        if (type == null) {
            throw new NullPointerException("type can't be null");
        }
        favorites.put(type, instance);
        //为了避免直接使用不含类型参数的 Class 表示 type，导致取元素时的类型不安全，可以在添加时就添加一个转换，在添加时就进行检查
//        favorites.put(type, type.cast(instance));
    }

    /**
     * 重新建立 键值之间的类型联系 ??
     *  使用 Class.cast 将 Object 类型的对象引用 动态地转换成 Class 对象所表示的类型  dynamic cast
     * @param type
     * @param <T>
     * @return
     */
    public <T> T getFavorite(Class<T> type) {
        return type.cast(favorites.get(type));
    }

    /**
     * Class 类被泛型化的证据：（Class 当然需要泛型化了，毕竟要表示不同种类型的类）
     *      Class.cast 的实现，检查是否为 Class 对象所表示的类型的实例，然后进行强转
     * @param claz
     * @param obj
     * @param <T>
     * @return
     */
    private  <T>T cast(Class<T> claz,Object obj) {
        if (obj == null) {
            return null;
        } else if (claz.isInstance(obj)) {
            return (T)obj;
        }
        String actualClassName = obj.getClass().getName();
        String desiredClassName = claz.getName();
        throw new ClassCastException(actualClassName + " cannot be cast to " + desiredClassName);
    }
}
