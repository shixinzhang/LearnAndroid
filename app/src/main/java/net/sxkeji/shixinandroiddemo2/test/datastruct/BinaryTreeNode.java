package net.sxkeji.shixinandroiddemo2.test.datastruct;

/**
 * <br/> Description: 二叉树节点
 * <p>
 * <br/> Created by shixinzhang on 16/11/16.
 * <p>
 * <br/> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class BinaryTreeNode {
    /**
     * 递归节点实现法/左右链表示法
     * 一个二叉树包括 数据、左右孩子 三部分
     */
    private int mData;
    private BinaryTreeNode mLeftChild;
    private BinaryTreeNode mRightChild;

    public BinaryTreeNode() {
    }

    public BinaryTreeNode(int data, BinaryTreeNode leftChild, BinaryTreeNode rightChild) {
        mData = data;
        mLeftChild = leftChild;
        mRightChild = rightChild;
    }

    public int getData() {
        return mData;
    }

    public void setData(int data) {
        mData = data;
    }

    public BinaryTreeNode getLeftChild() {
        return mLeftChild;
    }

    public void setLeftChild(BinaryTreeNode leftChild) {
        mLeftChild = leftChild;
    }

    public BinaryTreeNode getRightChild() {
        return mRightChild;
    }

    public void setRightChild(BinaryTreeNode rightChild) {
        mRightChild = rightChild;
    }
}
