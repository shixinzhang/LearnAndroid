package net.sxkeji.shixinandroiddemo2.test.generic;

import java.util.ArrayList;
import java.util.List;

/**
 * <br/> Description: 使用泛型实现的容器，GenericList<E> 中的 <E> 表示类型参数
 * <p>
 * <br/> Created by shixinzhang on 16/11/28.
 * <p>
 * <br/> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class GenericList<E> {
    private List<E>  mData;

    public GenericList() {
        mData = new ArrayList<>();
    }

    public E getData(int index) {
        if (index > mData.size()){
            return null;
        }
        return mData.get(index);
    }

    public void add(int index, E item) {
        mData.add(item);
    }
}
