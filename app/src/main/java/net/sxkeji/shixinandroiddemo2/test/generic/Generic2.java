package net.sxkeji.shixinandroiddemo2.test.generic;

import java.util.Arrays;

/**
 * <br/> Description: 使用 泛型 实现的一个 容器类
 * <p>
 * <br/> Created by shixinzhang on 16/11/28.
 * <p>
 * <br/> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

@SuppressWarnings("unchecked")
public class Generic2<E> {
    private E[] mData;
    private Object[] mElements;
    private final int DEFAULT_CAPACITY = 16;
    private int mSize = 0;

    /**
     * 过程：
     *      1.直接初始化为 E[]
     *              mData = new E[DEFAULT_CAPACITY]; 会报错，不能创建不可具体化 (non-reifiable) 的类型的数组；
     *      2.先初始化为 Object 数组，然后强转成 E[]
     *              mData = (E[]) new Object[DEFAULT_CAPACITY] ，不报错，但是会有警告：unchecked cast。
     *              然而我们这个变量是内部的，不存在其他类型赋值的可能，因此可以确保是类型安全的，因此可以在尽可能小的范围内禁止警告：这里完全可以在类前面加 注解声明
     */
    public Generic2() {
        mData = (E[]) new Object[DEFAULT_CAPACITY];
    }

    public Generic2(int capacity) {
        mData = (E[]) new Object[DEFAULT_CAPACITY];
    }

    /**
     * 获取元素，实际情况中一定需要强制转换，风险！
     * @param index
     * @return
     */
    public E getData(int index) {
        if (index > mData.length){
            return null;
        }
        return mData[index];
    }

    public void add(E item){
        ensureCapacity();
        mData[mSize++] = item;
    }

    public void add(int index, E item) {
        mData[index] = item;
    }

    /**
     * 需要的时候进行扩容
     */
    private void ensureCapacity() {
        if (mData.length == mSize + 1 ){
            mData = Arrays.copyOf(mData, 2  * mSize);
        }
    }
}
