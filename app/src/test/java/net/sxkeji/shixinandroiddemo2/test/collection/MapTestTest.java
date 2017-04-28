package net.sxkeji.shixinandroiddemo2.test.collection;

import net.sxkeji.shixinandroiddemo2.bean.Person;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;

/**
 * description: Map 练习
 * <br/>
 * author: shixinzhang
 * <br/>
 * data: 10/5/2016
 */
public class MapTestTest {

    /**
     * Map 和 Collection 平级，用于保存具有 **映射关系** 的数据，key-value 键值对
     *          1.Map 中的 key 和 value 可以是任何引用类型的数据，key/value 都可以是 null
     *          2.key 用 Set 存放，所以 **key 不允许重复**，因此 key 对应的类需要重写 hashCode 和 equals 方法
     *          3.一个 key 唯一对应一个 value
     *          4.所有的 key 是一个 KeySet (Set)
     *          5.所有的 value 是用 Collection 存放(可以是 List, Set 等等)
     *          6.一个 key-value 键值对 是一个 Entry，所有的 Entry 也是用 Set 存放的，也不可重复
     *
     * Map 接口
     *      | --- HashMap, Map 的主要实现类
     *      | --- LinkedHashMap
     *      | --- TreeMap
     *      | --- HashTable
     *          | --- Properties
     *
     *
     * Collection 中添加是 add, Map 中是 put
     *
     * @throws Exception
     */
    @Test
    public void testTestMap() throws Exception {
        Map map = new HashMap();
        //key/value 都可以为空
        map.put(null,null);
        map.put("zsx",222);

        //key 用 Set 存放，所以 key 要想不允许重复， key **对应的类就要重写 hashCode 和 equals 方法**
        map.put(new Person("zsx",18),22);
        map.put(new Person("zsx",18),25);   //put, 存在就覆盖

//        System.out.println("remove " + map.remove("zsx"));      //remove, 删除并返回指定 key 对应的 value

        map.containsValue(222);     //要想知道有没有，一样不一样，就得重写 hashCode, equals

        map.put("zsx",666);
        System.out.println("map size " + map);

    }


    /**
     * 如何遍历 Map ? 元视图操作的方法
     *      Set keySet();
     *      Collection values();
     *      Set entrySet();
     */
    @Test
    public void testTestLoopMap() throws Exception {
        Map map = new HashMap();
        map.put(null,null);
        map.put("zsx",222);
        map.put(new Person("zsx",18),22);

        //1.遍历 key 集合
        Set set = map.keySet();
        for (Object key : set) {
            System.out.println(map.get(key));
        }

//        set.iterator();

        //2.遍历 value 集合
        Collection values = map.values();
        Iterator iterator = values.iterator();
        while (iterator.hasNext()){
            System.out.println("value " + iterator.next());
        }

        //3.遍历 map 的每个 Entry
            //3.1
        Set set1 = map.keySet();
        for (Object o : set1) {
            System.out.println("entry " + o + " / " + map.get(o));
        }
            //3.2
        Set entrySet = map.entrySet();
        for (Object o : entrySet) {
            Map.Entry entry = (Map.Entry) o;
            System.out.println(entry);      //key=value
            System.out.println(entry.getKey() + " / " + entry.getValue());
        }
    }

    /**
     * LinkedHashMap
     *      类似 LinkedHashSet
     *      使用链表维护 LinkedHashMap 中元素的顺序，使得输出顺序跟输入顺序一致
     *      遍历时很快，添加删除时慢一些，因为要维护个链表嘛
     * @throws Exception
     */
    @Test
    public void testTestLinkedHashMap() throws Exception {
        Map map = new LinkedHashMap();
        map.put(null,null);
        map.put("zsx",222);
        map.put(new Person("zsx",18),22);

        Set entrySet = map.entrySet();
        for (Object o : entrySet) {
            Map.Entry entry = (Map.Entry) o;
            System.out.println(entry);      //key=value
        }

    }

    /**
     * TreeMap,类似 TreeSet
     *      按照 key 的指定属性进行排序（ 因为 key 集合就是一个 Set）
     *      要求 key 必须是同一个类的对象，废话
     *
     * 两种排序方式：（ key）
     *      自然排序；
     *      定制排序
     * @throws Exception
     */
    @Test
    public void testTestTreeMap() throws Exception {
//        Comparator comparator = new Comparator() {
//            @Override
//            public int compare(Object lhs, Object rhs) {
//
//                return 0;
//            }
//        };

//        Map map = new TreeMap(comparator);
        Map map = new TreeMap();
        map.put(new Person("A",20), 90);
        map.put(new Person("S",30), 60);
        map.put(new Person("C",18), 70);

        System.out.println(map);
    }


    /**
     * Hashtable
     *      古老的 Map 实现类（JDK 1.0），命名都不是驼峰的，不建议使用
     *      1.和 Vector 相似，线程安全的
     *      2.与 HashMap 不同，Hashtable 不允许 key/value 为 null，否则报空指针
     *      3.与 HashMap 一致的是，Hashtable 不能保证 key-value 的顺序
     *      4.判断相等的标准和 HashMap 一致
     *
     * Hashtable
     *      | --- Properties: 常用来处理属性文件，键值都是 String 类型
     * @throws Exception
     */
    @Test
    public void testTestHashtable() throws Exception {
        Hashtable hashtable = new Hashtable();
//        hashtable.put(null,null);

        System.out.println(hashtable);

        Properties properties = new Properties();
        properties.load(new FileInputStream(new File("jdbc.properties")));
        String user = properties.getProperty("user");
        System.out.println("user " + user);
    }


    @Test
    public void testHash(){
        int[] numbers = new int[]{2,5,9,13};
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] == 13){
                System.out.println("find it!");
                return;
            }
        }
    }
}