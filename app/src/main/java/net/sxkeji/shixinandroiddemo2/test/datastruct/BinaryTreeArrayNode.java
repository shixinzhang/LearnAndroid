package net.sxkeji.shixinandroiddemo2.test.datastruct;

/**
 * <br/> Description:
 * <p>
 * <br/> Created by shixinzhang on 16/11/17.
 * <p>
 * <br/> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class BinaryTreeArrayNode {
    /**
     * 数组实现，保存的不是 左右子树的引用，而是数组下标
     */
    private int mData;
    private int mLeftChild;
    private int mRightChild;

    public int getData() {
        return mData;
    }

    public void setData(int data) {
        mData = data;
    }

    public int getLeftChild() {
        return mLeftChild;
    }

    public void setLeftChild(int leftChild) {
        mLeftChild = leftChild;
    }

    public int getRightChild() {
        return mRightChild;
    }

    public void setRightChild(int rightChild) {
        mRightChild = rightChild;
    }
}
