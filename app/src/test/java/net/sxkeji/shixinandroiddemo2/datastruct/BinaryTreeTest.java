package net.sxkeji.shixinandroiddemo2.datastruct;

import net.sxkeji.shixinandroiddemo2.test.datastruct.BinaryTree;
import net.sxkeji.shixinandroiddemo2.test.datastruct.BinaryTreeNode;

import org.junit.Test;

/**
 * <br/> Description: 测试二叉树的常用操作
 * http://blog.csdn.net/u011240877/article/details/53193918
 * <p>
 * <br/> Created by shixinzhang on 16/11/17.
 * <p>
 * <br/> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */
public class BinaryTreeTest {
    @Test
    public void getRoot() throws Exception {
        BinaryTreeNode node = new BinaryTreeNode(1, null, null);
        BinaryTree binaryTree = new BinaryTree(node);

        BinaryTreeNode childNode2 = new BinaryTreeNode(2, null, null);
        BinaryTreeNode childNode4 = new BinaryTreeNode(4, null, null);
        childNode2.setLeftChild(childNode4);

        BinaryTreeNode childNode5 = new BinaryTreeNode(5, null, null);
        BinaryTreeNode childNode7 = new BinaryTreeNode(7, null, null);
        childNode5.setLeftChild(childNode7);
        childNode2.setRightChild(childNode5);

        binaryTree.insertAsLeftChild(childNode2);
        BinaryTreeNode childNode3 = new BinaryTreeNode(3, null, null);
        BinaryTreeNode childNode6 = new BinaryTreeNode(6, null, null);
        childNode3.setRightChild(childNode6);

        binaryTree.insertAsRightChild(childNode3);


        System.out.println("树的高度 " + binaryTree.getTreeHeight());
        System.out.println("树的节点个数 " + binaryTree.getSize());
        BinaryTreeNode parentNode = binaryTree.getParent(childNode4);
        System.out.println("4 父亲节点的数据 " + parentNode.getData());

        System.out.println("\n\n先序遍历");
        binaryTree.iterateFirstOrder(node);
        System.out.println("\n中序遍历");
        binaryTree.iterateMediumOrder(node);
        System.out.println("\n后序遍历");
        binaryTree.iterateLastOrder(node);
    }

}