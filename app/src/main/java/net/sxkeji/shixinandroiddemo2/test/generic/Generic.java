package net.sxkeji.shixinandroiddemo2.test.generic;

import java.util.Arrays;

/**
 * <br/> Description: 使用 Object 实现的一个 容器类
 * <p>
 * <br/> Created by shixinzhang on 16/11/28.
 * <p>
 * <br/> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class Generic implements GenericInterface<String>{
    private Object[] mData;
    private final int DEFAULT_CAPACITY = 16;
    private int mSize = 0;

    public Generic() {
        mData = new Object[DEFAULT_CAPACITY];
    }

    public Generic(int capacity) {
        mData = new Object[capacity];
    }

    /**
     * 获取元素，实际情况中一定需要强制转换，风险！
     * @param index
     * @return
     */
    public Object getData(int index) {
        if (index > mData.length){
            return null;
        }
        return mData[index];
    }

    public void add(Object item){
        ensureCapacity();
        mData[mSize++] = item;
    }

    public void add(int index, Object item) {
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

    @Override
    public void doSomething(String s) {

    }
}
