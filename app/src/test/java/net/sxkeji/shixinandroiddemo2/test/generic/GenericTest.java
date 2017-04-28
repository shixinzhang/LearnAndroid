package net.sxkeji.shixinandroiddemo2.test.generic;

import android.graphics.Color;

import net.sxkeji.shixinandroiddemo2.bean.BookBean;
import net.sxkeji.shixinandroiddemo2.bean.ChildBookBean;
import net.sxkeji.shixinandroiddemo2.util.StringUtils;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

/**
 * <br/> Description: 泛型的练习
 * <p>
 * <br/> Created by shixinzhang on 16/11/28.
 * <p>
 * <br/> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */
public class GenericTest {
    @Test
    public void getData() throws Exception {
        Generic generic = new Generic(10);
        generic.add(0, "shixin");
        generic.add(1, 23);

        String item1 = (String) generic.getData(0);
        String item2 = (String) generic.getData(1);
    }

    /**
     * 协变、逆变、不可变：
     * 数组是协变的；
     * 泛型是不可变的：
     * <p>
     * List<String> 并不是 List<Object> 的子类
     */
    @Test
    public void notChildType() {
        List<String> stringList = new ArrayList<>();
        //编译时不报错，运行时才报： 类型转换错误
        unsafeAdd(stringList, new Integer(1));
        //编辑时就报错：wrong argument type，类型错误
//        unsafeAddObject(stringList,new Integer(1));
        String s = stringList.get(0);
    }

    private void unsafeAdd(List stringList, Object object) {
        //注意编译器的警告：unchecked call to add(E) as a member of raw type
        stringList.add(object);
    }

    private void unsafeAddObject(List<Object> objects, Object integer) {
        objects.add(integer);
    }

    /**
     * 通配符类型：
     *      通配符类型是安全的，原生类型则不安全。
     * <p>
     *      1.无限制通配符 <?>
     *      2.限制通配符：
     *          a.
     *          b.
     * <p>
     * 特点：
     * 1.不可变的
     */
    @Test
    public void wildcard() {
        Set<String> set = new HashSet<>();
        set.add("shi");
        printSet(set);

        GenericList<String> generic = new GenericList();

        Generic2<String> generic2 = new Generic2<>();
        String data = generic2.getData(0);

//        Object[] objectArray = new Long[1];
//        //编译时不会报错，运行时才会报：ArrayStoreException
//        objectArray[0] = "Hello";

        //这里编译器就会报错：Incompatible type
//        List<Object> objectList = new ArrayList<Long>();
//        objectList.add("Hello");
    }

    private void printSet(Set<?> set) {
        if (set == null)
            return;
        for (Object o : set) {
            System.out.println(o);
        }
    }

    /**
     * 为什么不可以创建 泛型数组 ？
     */
    @Test
    public void covariant() {
        //1.创建了一个假泛型数组,下面那个才是真的泛型数组，但是编译器不允许这么做，禁止创建泛型数组，为什么呢？我们以这个假泛型数组为例走一遍
        List<String>[] stringList = new List[2];
//        List<String>[] stringList = new List<String>[2];
//        Set<String>[] stringList = new Set[2];
        //2.包含单个元素的 List<Integer>
        List<Integer> integerList = Arrays.asList(22);
        //3.泛型数组赋值给 Object 数组变量，完全可以，因为数组是协变的，List<String>[] 是 Object[] 的子类
        Object[] objects = stringList;
        //4.将 List<Integer> 赋值给 Object 数组第一个元素，这是可以的，因为运行时 泛型会被擦除，List<Integer> 会变成 List
        //而 object[0] 之前是一个 List<String>[]，运行时会变成 List[]，赋值完全可以，这点很重要！不然 把 1 改成注掉的 Set<String>[]，运行时会报错：ArrayStoreException
        objects[0] = integerList;
        //5.但现在有问题了！！编译器会把 List<String> stringList 里的元素自动转换为 String，事实上上一步我们把一个 Integer 实例放进去了，运行时才会报错：ClassCastException
        String s = stringList[0].get(0);
    }

    @Test
    public void compareWithArray() {

    }

    private Object reduce1(List list, Function1 f, Object initVal) {
        synchronized (list) {
            Object result = initVal;
            for (Object o : list) {
                result = f.apply(result, o);
            }
            return result;
        }
    }

    /**
     * 错误的使用，需要强转；而且强转以后还会报警告：
     *          unchecked cast 未经检查的转换
     *
     *          因为运行时不确定 list 的具体类型。虽然可以运行，但容易出错。
     *
     *  数组与泛型有着非常不同的类型规则：
     *          1.数组是协变且可以具体化的
     *          2.泛型是不可变并且可以擦除的
     *      因此数组提供了运行时的类型安全，但没有保障编译时的类型安全；
     *          泛型提供了编译时的类型安全，运行时也安全。
     *
     *      一般来说数组与泛型不可混用，建议用泛型代替数组
     *
     * @param list
     * @param f
     * @param initVal
     * @param <E> 返回的参数类型
     */
    private <E> E reduce(List<E> list, Function<E> f, E initVal){
        //不可具化的类型的 数组转换 要慎用
        E[] snapshot1 = (E[]) list.toArray();

        //正确的用法：用列表代替数组！
        List<E> snapshot ;
        synchronized (list){
            snapshot = new ArrayList<>(list);
        }

        E result = initVal;
        for (E e : snapshot) {
            result = f.apply(result, e);
        }
        return result;
    }

    /**
     * 传统的方法，会有 unchecked ... raw type 的警告
     * @param s1
     * @param s2
     * @return
     */
    public Set union(Set s1, Set s2){
        Set result = new HashSet(s1);
        result.addAll(s2);
        return result;
    }

    /**
     * 泛型方法，介于方法修饰符和返回值之间的称作 类型参数列表 <A,V,F,E...> (可以有多个)
     *      类型参数列表 指定参数、返回值中泛型参数的类型范围，命名惯例与泛型相同
     * @param s1
     * @param s2
     * @param <E>
     * @return
     */
    public <E> Set<E> union2(Set<E> s1, Set<E> s2){
        Set<E> result = new HashSet<>(s1);
        result.addAll(s2);
        return result;
    }

    @Test
    public void testGenericMethod(){
        String s1 = "shixin";
        String s2 = "cute";
        String test = test(s1, s2);     //自动转换为参数类型
//        BookBean bookBean = test2(s1, s2);  //test2 泛型方法的类型参数列表限制 参数类型必须是 BookBean 及其子类，因此这里会编译不过

        ChildBookBean childBookBean = new ChildBookBean("shixin",2);
        BookBean bookBean = new BookBean("rourou",1);
        BookBean bookBean1 = test2(childBookBean, bookBean);    //有限制通配符，使得三个参数可以不同，但必须是同一类

        List<String> list = Arrays.asList("b","a","c");
        String max = max(list);
        System.out.println(max);

        ArrayList<Object> arrayList = new ArrayList(list);
        add(arrayList,list);
        System.out.println(arrayList);
    }

    private <E> E test(E arg1, E arg2){
        E result = arg1;
        //.....
        return result;
    }

    /**
     * 有限制的通配符之 extends （有上限），表示参数类型 必须是 BookBean 及其子类，更灵活
     * @param arg1
     * @param arg2
     * @param <E>
     * @return
     */
    private <K extends ChildBookBean, E extends BookBean> E test2(K arg1, E arg2){
        E result = arg2;
        arg2.compareTo(arg1);
        //.....
        return result;
    }

    interface Function1 {
        Object apply(Object arg1, Object arg2);
    }

    interface Function<T> {
        T apply(T arg1, T arg2);
    }

    /**
     * 针对可以与自身进行比较的每个类型 E，返回最大值
     * @param e1
     * @param <E>
     * @return
     */
    private  <E extends Comparable<? super E>> E max(List<? extends E> e1){
        if (e1 == null){
            return null;
        }
        //迭代器返回的元素属于 E 的某个子类型
        Iterator<? extends E> iterator = e1.iterator();
        E result = iterator.next();
        while (iterator.hasNext()){
            E next = iterator.next();
            if (next.compareTo(result) > 0){
                result = next;
            }
        }
        return result;
    }

    private <E> void addAll(Collection<E> c1, Collection<E> c2){
        for (E e : c2) {
            c1.add(e);
        }
    }

    /**
     * 不加 super 的话编译器不通过
     *          > 为了获得最大限度的灵活性，要在表示 生产者或者消费者的输入参数上使用通配符
     *          PECS: producer-extends, costumer-super
     *          使用通配符的基本原则：
     *              如果参数化类型表示一个 T 的生产者，使用 <? extends T>;
     *              如果它表示一个 T 的消费者，就使用 <? super T>；
     *              如果既是生产又是消费，那使用通配符就没什么意义了，因为你需要的是精确的参数类型。
     *
     * @param dst
     * @param src
     * @param <E>
     */
    private <E> void add(List<? super E> dst, List<E> src){
        for (E e : src) {
            dst.add(e);
        }
    }

    /**
     *
     * 交换，List.set() 方法会返回指定位置之前的元素
     *      1 2
     *       1 1
     *
     * @param list
     * @param i
     * @param j
     * @param <E>
     */
    private <E> void swap1(List<E> list, int i, int j) {
        //...
        list.set(i, list.set(j, list.get(i)));
    }

    private void swap(List<?> list, int i, int j){
        swap1(list, i, j);
    }

    public interface OnTestListener{
        String mName = "";
        void test();
    }

    /**
     * 在整个类所有 Test 之前运行
     */
    @BeforeClass
    public static void beforeClass(){
        System.out.println("beforeClass");    
    }
    
    /**
     * before 每一个 Test 前运行，初始化资源
     */
    @Before
    public void beforeTest(){
        System.out.println("before");
    }


    /**
     * 全局每个方法最长时间为 1000
     */
    @Rule
    public Timeout globalTimeOut = new Timeout(1000);

    /**
     * 最长时间为 1000 毫秒
     */
    @Test(timeout = 1000)
    public void swapTest(){
        List<Integer> arrayList = Arrays.asList(1,2);
        System.out.println(arrayList.set(1,arrayList.get(0)));
        String a = "shi";
//        fail("fail test");
        assertEquals("测试是否相等", "shixin", "shi" + "xin");    // 类似字符串的 equals
        assertSame("测试是否同一个对象引用", "shixin", a + "xin");    //类似 Object 的 ==
        assertNotNull("测试不为空", 222);
    }

    /**
     * after 每一个 Test 运行，释放资源
     */
    @After
    public void afterTest(){
        System.out.println("after");
    }

    @Ignore
    @AfterClass
    public static void afterClass(){
        System.out.println("afterClass");
    }


    private void set(String o){
        ///
    }

    /**
     * 泛型的擦除，运行时都是 Object
     */
    @Test
    public void testErasure(){
        List<String> strings = new ArrayList<>();
        List<Integer> integers = new ArrayList<>();
//        System.out.println(strings.getClass() == integers.getClass());//true

        Shixin shixin = new Shixin();
        GenericErasure.People<Shixin> m = new GenericErasure.People<>(shixin);
        m.habit();
//        System.out.println(m.mPeople.getClass() == GenericErasure.Program.class);
    }


    @Test
    public void testString(){
        boolean s = StringUtils.isRgbValue("#7B0D16");
        System.out.println(s);

        System.out.println(Color.parseColor("#7B0D16"));
    }
}