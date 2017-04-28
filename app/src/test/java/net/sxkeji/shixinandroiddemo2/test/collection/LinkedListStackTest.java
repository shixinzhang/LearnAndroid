package net.sxkeji.shixinandroiddemo2.test.collection;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * description:
 * <br/>
 * author: shixinzhang
 * <br/>
 * data: 10/23/2016
 */
public class LinkedListStackTest {

    @Test
    public void testPush() throws Exception {
        LinkedListStack stack = new LinkedListStack();
        System.out.println("栈是否为空: " + stack.isEmpty());

        stack.push("shixin");
        stack.push("好帅");
        stack.push("技巧一流");
        stack.push("haha");

        System.out.println("栈中元素: " + stack);

        System.out.println("获取顶端元素 peek :" + stack.peek());

        System.out.println("顶端元素出栈 pop :" + stack.pop());

        System.out.println("出栈后栈内元素:" + stack);

        System.out.println("search(好帅) 的位置:" + stack.search("好帅"));
    }

    @Test
    public void testSet(){
        Set set = new HashSet<>();
        set.add("z");
        set.add("s");

        Set set1 = new HashSet<>();
        set1.add("ssss");
        set1.add("xxxx");

        Set set2 = new HashSet();
        set2.add(set);
        set2.add(set1);

        Object[] strings = new Object[10];
        strings[1] = "zsx";
        strings[7] = "qq";
        for (Object string : strings) {
            System.out.println(string);
        }
        System.out.println("*******************************");
        set2.toArray(strings);
//        set1.addAll(set);

        for (Object string : strings) {
            System.out.println(string);
        }
    }
}