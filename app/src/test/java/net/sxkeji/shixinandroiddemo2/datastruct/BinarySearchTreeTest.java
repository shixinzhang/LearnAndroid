package net.sxkeji.shixinandroiddemo2.datastruct;

import net.sxkeji.shixinandroiddemo2.test.datastruct.BinarySearchTree;

import org.junit.Test;

/**
 * <br/> Description:测试 二叉排序树 的删除操作
 * <p>
 * <br/> Created by shixinzhang on 16/11/20.
 * <p>
 * <br/> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */
public class BinarySearchTreeTest {
    @Test
    public void delete() throws Exception {
        //乱序插入到二叉排序树中
        BinarySearchTree binarySearchTree = new BinarySearchTree(null);
        binarySearchTree.insert(8);
        binarySearchTree.insert(3);
        binarySearchTree.insert(1);
        binarySearchTree.insert(6);
        binarySearchTree.insert(4);
        binarySearchTree.insert(7);
        binarySearchTree.insert(10);
        binarySearchTree.insert(13);
        binarySearchTree.insert(14);

        //中序遍历
        binarySearchTree.iterateMediumOrder(binarySearchTree.getRoot());
        System.out.println("");
        //查找某个数据
        System.out.println(binarySearchTree.search(10).getData());
        //删除某个数据对应的元素
        binarySearchTree.delete(11);
        //中序遍历删除后的二叉排序树
        binarySearchTree.iterateMediumOrder(binarySearchTree.getRoot());
    }

}