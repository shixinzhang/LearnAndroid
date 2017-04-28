package net.sxkeji.shixinandroiddemo2.test.collection;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.List;
import java.util.StringTokenizer;

/**
 * description:  Collections, 操作 Collection 和 Map 的工具类
 * 数组的操作类 Arrays ，可以翻译 https://docs.oracle.com/javase/tutorial/java/nutsandbolts/arrays.html
 * <br/>
 * author: shixinzhang
 * <br/>
 * data: 10/5/2016
 */
public class CollectionsTestTest {

    /**

     *
     * List 操作 常用方法:
     *      1.reverse(List) 反转 List 中的元素顺序
     *      2.shuffle(List) 对 List 中的元素进行随机排序
     *      3.sort(List) 根据元素的自然顺序对 List 中的元素按照 **升序**排序
     *      4.sort(List, Comparator) 根据指定的排序方式对 List 进行排序
     *      5.swap(List, int, int) 将 List 中 index 为 i 和 j 的元素进行交换
     * @throws Exception
     */
    @Test
    public void testTestCollections() throws Exception {
        List list = new ArrayList();
        list.add(1);
        list.add(3);
        list.add(5);
        list.add(7);
        System.out.println(list);

        Collections.reverse(list);      //很巧妙，看看如何实现快速反转的？
        System.out.println("reverse " + list);

        Collections.shuffle(list);      //看看如何实现打乱顺序？
        System.out.println("shuffle " + list);

        Collections.sort(list);     //升序
        System.out.println("sort " + list);

        Collections.sort(list, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                if (o1 instanceof Integer && o2 instanceof Integer){
                    Integer int1 = (Integer) o1;
                    Integer int2 = (Integer) o2;
                    return - int1.compareTo(int2);
                }
                return 0;
            }
        });
        System.out.println("自定义排序，这里是降序 " + list);

        Collections.swap(list,0,list.size()-1); //交换
        System.out.println("交换首尾元素 " + list);
    }

    /**
     * 比较、计算：
     *      Object max(Collection), 根据元素的自然顺序 Comparable ，返回集合中最大元素
     *      Object max(Collection, Comparator)，根据 定制顺序 Comparator 中的顺序返回最大元素
     *      Object min(Collection)
     *      Object min(Collection, Comparator)
     *      int frequency(Collection, Object)，返回集合中该元素出现的次数
     *
     * 复制
     *      void copy(List dest, List src) ，将 src 中的内容复制到 dest 中
     *
     * 替换
     *      boolean replaceAll(List list, Object oldValue, Object newValue)
     *
     * @throws Exception
     */

    @Test
    public void testTestCollections2() throws Exception {
        List list = new ArrayList();
        list.add(1);
        list.add(3);
        list.add(5);
        list.add(5);
        list.add(7);

        System.out.println("自然顺序最大 " + Collections.max(list));
        Object max = Collections.max(list, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                if (o1 instanceof Integer && o2 instanceof Integer){
                    Integer int1 = (Integer) o1;
                    Integer int2 = (Integer) o2;
                    return - int1.compareTo(int2);
                }
                return 0;
            }
        });
        System.out.println("定制顺序最大（指鹿为马） " + max);

        System.out.println("5 出现次数 " + Collections.frequency(list,5));      //遍历，count++

//        List newList = new ArrayList();     //错误的实现方式
        /**
         * List newList = new ArrayList();     //错误的实现方式
         *
         * Java.lang.IndexOutOfBoundsException: Source does not fit in dest
         * 因为 newList 目前长度为 0，需要初始化和 src 长度一样
         */

        List newList = Arrays.asList(new Object[list.size()]);
        Collections.copy(newList,list);
        System.out.println("newList " + newList);

        Collections.replaceAll(newList,5,555);
        System.out.println("替换5为555 " + newList);
    }

    /**
     * 同步控制
     * Collections 提供了多个 synchronizedXXX() 方法，可以将指定集合包装成 线程同步的集合，从而解决 多线程并发访问集合时的安全问题
     *      Collection synchronizedCollection(Collection);
     *      List synchronizedList(List);
     *      Map synchroniedMap();
     *      ...
     *
     * @throws Exception
     */
    @Test
    public void testTestCollections3() throws Exception {
        List list = new ArrayList();
        list.add(1);
        list.add(3);
        list.add(5);
        list.add(5);
        list.add(7);

        Collections.synchronizedList(list);     //如何实现?
    }

    /**
     * Enumeration 接口，是 Iterator 迭代器的 “古老版本”
     *         boolean hasMoreElements();
     *         T nextElement();
     *
     * 了解即可
     *
     * @throws Exception
     */
    @Test
    public void testTestCollections4() throws Exception {
        //切割
        //StringTokenizer : Breaks a string into tokens; new code should probably use {@link String#split}.
        Enumeration enumeration = new StringTokenizer("A-B-C", "-");
        while (enumeration.hasMoreElements()){
            System.out.println(enumeration.nextElement());
        }
    }

    @Test
    public void testTestIteratorFailFast() throws Exception {
        final List list = new ArrayList();
        list.add(1);
        list.add(3);
        list.add(5);
        list.add(5);
        list.add(7);

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Iterator iterator = list.iterator();
//                while (iterator.hasNext()){
//                    Integer next = (Integer) iterator.next();
//                    System.out.println("thread 1 " + next);
////                    try {
////                        Thread.sleep(500);
////                    } catch (InterruptedException e) {
////                        e.printStackTrace();
////                        System.out.println("error " + e.getMessage().toString());
////                    }
//                }
//            }
//        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = list.size() - 1; i >= 0; i--) {
                    Integer integer = (Integer) list.get(i);
                    if (integer == 5){
                        list.remove(integer);
                    }else {
                        System.out.println("thread2 " + integer);
                    }
                    list.add(integer);
                }
//                for (int i = 0; i < list.size(); i++) {
//                    Integer integer = (Integer) list.get(i);
//                    if (integer == 5){
//                        list.remove(integer);
//                    }else {
//                        System.out.println("thread2 " + integer);
//                    }
//                }
//                for (Object object : list) {
//                    if (object instanceof Integer){
//                        Integer integer = (Integer) object;
//                        if (integer == 5){
//                            list.remove(integer);
//                        }
//                    }
//                }
            }
        }).start();
    }
}