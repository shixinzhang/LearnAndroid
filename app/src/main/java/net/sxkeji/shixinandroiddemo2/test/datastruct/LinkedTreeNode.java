package net.sxkeji.shixinandroiddemo2.test.datastruct;

import java.util.ArrayList;

/**
 * <header>
 * Description: 树节点
 * </header>
 * <p>
 * Author: shixinzhang
 * </p>
 * <p>
 * Create at: 11/11/2016
 * </p>
 * <p>
 * Update at: 11/11/2016
 * </p>
 * <p>
 * Related links: <a href="${link_address}">${linkName}</a>
 * </p>
 */
public class LinkedTreeNode {

    private Object mData;   //存储的数据
    private LinkedTreeNode mParent;   //父亲节点的下标
    private LinkedTreeNode mChild;	//孩子节点的引用

    public LinkedTreeNode(Object data, LinkedTreeNode parent) {
        mData = data;
        mParent = parent;
    }

    public Object getData() {
        return mData;
    }

    public void setData(Object data) {
        mData = data;
    }

    public Object getParent() {
        return mParent;
    }

    public void setParent(LinkedTreeNode parent) {
        mParent = parent;
    }

    public LinkedTreeNode getChild() {
        return mChild;
    }

    public void setChild(LinkedTreeNode child) {
        mChild = child;
    }

    public static void main(String[] args){
        ArrayList<LinkedTreeNode> linkedTree = new ArrayList<>();
    }
}
