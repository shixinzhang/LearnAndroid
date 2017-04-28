package net.sxkeji.shixinandroiddemo2.test.collection;

import java.util.LinkedList;

/**
 * description:LinkedList 模拟 Stack
 * <br/>
 * author: shixinzhang
 * <br/>
 * data: 10/23/2016
 */
public class LinkedListStack extends LinkedList{
    public LinkedListStack(){
        super();
    }

    @Override
    public void push(Object o) {
        super.push(o);
    }

    @Override
    public Object pop() {
        return super.pop();
    }

    @Override
    public Object peek() {
        return super.peek();
    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty();
    }

    public int search(Object o){
        return indexOf(o);
    }
}
