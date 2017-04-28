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

public class BinaryTree {
    private BinaryTreeNode mRoot;   //根节点

    public BinaryTree() {
    }

    public BinaryTree(BinaryTreeNode root) {
        mRoot = root;
    }

    public BinaryTreeNode getRoot() {
        return mRoot;
    }

    public void setRoot(BinaryTreeNode root) {
        mRoot = root;
    }

    public void insertAsLeftChild(BinaryTreeNode child) {
        checkTreeEmpty();
        mRoot.setLeftChild(child);
    }

    public void insertAsRightChild(BinaryTreeNode child) {
        checkTreeEmpty();
        mRoot.setRightChild(child);
    }

    private void checkTreeEmpty() {
        if (mRoot == null) {
            throw new IllegalStateException("Can't operate a null tree! Are you forgot set value for root?");
        }
    }

    public void deleteNode(BinaryTreeNode node) {
        checkTreeEmpty();
        if (node == null) {  //递归出口
            return;
        }
        deleteNode(node.getLeftChild());
        deleteNode(node.getRightChild());
        node = null;
    }

    public void clear() {
        if (mRoot != null) {
            deleteNode(mRoot);
        }
    }

    /**
     * 获取树的高度 ，特殊的获得节点高度
     *
     * @return
     */
    public int getTreeHeight() {
        return getHeight(mRoot);
    }

    /**
     * 获得指定节点的度
     *
     * @param node
     * @return
     */
    public int getHeight(BinaryTreeNode node) {
        if (node == null) {      //递归出口
            return 0;
        }
        int leftChildHeight = getHeight(node.getLeftChild());
        int rightChildHeight = getHeight(node.getRightChild());

        int max = Math.max(leftChildHeight, rightChildHeight);

        return max + 1; //加上自己本身
    }

    public int getSize() {
        return getChildSize(mRoot);
    }

    /**
     * 获得指定节点的子节点个数
     *
     * @param node
     * @return
     */
    public int getChildSize(BinaryTreeNode node) {
        if (node == null) {
            return 0;
        }
        int leftChildSize = getChildSize(node.getLeftChild());
        int rightChildSize = getChildSize(node.getRightChild());

        return leftChildSize + rightChildSize + 1;
    }

    /**
     * 获得指定节点的父亲节点
     *
     * @param node
     * @return
     */
    public BinaryTreeNode getParent(BinaryTreeNode node) {
        if (mRoot == null || mRoot == node) {   //如果是空树，或者这个节点就是根节点，返回空
            return null;
        } else {
            return getParent(mRoot, node);  //否则递归查找 父亲节点
        }
    }

    /**
     * 递归对比 节点的孩子节点 与 指定节点 是否一致
     *
     * @param subTree 子二叉树根节点
     * @param node    指定节点
     * @return
     */
    public BinaryTreeNode getParent(BinaryTreeNode subTree, BinaryTreeNode node) {
        if (subTree == null) {       //如果子树为空，则没有父亲节点，递归出口 1
            return null;
        }
        //正好这个根节点的左右孩子之一与目标节点一致
        if (subTree.getLeftChild() == node || subTree.getRightChild() == node) {    //递归出口 2
            return subTree;
        }
        //需要遍历这个节点的左右子树
        BinaryTreeNode parent;
        if ((parent = getParent(subTree.getLeftChild(), node)) != null) { //左子树节点就是指定节点，返回
            return parent;
        } else {
            return getParent(subTree.getRightChild(), node);    //从右子树找找看
        }

    }

    /**
     * 模拟操作
     *
     * @param node
     */
    public void operate(BinaryTreeNode node) {
        if (node == null) {
            return;
        }
        System.out.print(node.getData() + " ");
    }

    /**
     * 先序遍历
     *
     * @param node
     */
    public void iterateFirstOrder(BinaryTreeNode node) {
        if (node == null) {
            return;
        }
        operate(node);
        iterateFirstOrder(node.getLeftChild());
        iterateFirstOrder(node.getRightChild());
    }

    /**
     * 中序遍历
     *
     * @param node
     */
    public void iterateMediumOrder(BinaryTreeNode node) {
        if (node == null) {
            return;
        }
        iterateMediumOrder(node.getLeftChild());
        operate(node);
        iterateMediumOrder(node.getRightChild());
    }

    /**
     * 后序遍历
     *
     * @param node
     */
    public void iterateLastOrder(BinaryTreeNode node) {
        if (node == null) {
            return;
        }
        iterateLastOrder(node.getLeftChild());
        iterateLastOrder(node.getRightChild());
        operate(node);
    }

//    @Override
//    public String toString() {
//        String result = "" + mRoot.getData();
//        while (mRoot.getLeftChild() != null){
//            result +=
//        }
//        return ;
//    }
}
