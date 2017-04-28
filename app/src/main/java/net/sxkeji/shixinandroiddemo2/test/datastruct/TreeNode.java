package net.sxkeji.shixinandroiddemo2.test.datastruct;

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
public class TreeNode {

    private Object mData;   //存储的数据
    private int mParent;   //父亲节点的下标

    public TreeNode(Object data, int parent) {
        mData = data;
        mParent = parent;
    }

    public Object getData() {
        return mData;
    }

    public void setData(Object data) {
        mData = data;
    }

    public int getParent() {
        return mParent;
    }

    public void setParent(int parent) {
        mParent = parent;
    }

    public static void main(String[] args){
        TreeNode[] arrayTree = new TreeNode[10];
    }
}
