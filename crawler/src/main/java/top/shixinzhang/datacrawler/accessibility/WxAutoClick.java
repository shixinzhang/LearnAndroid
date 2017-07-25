package top.shixinzhang.datacrawler.accessibility;

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import top.shixinzhang.datacrawler.Config;
import top.shixinzhang.datacrawler.CrashHandler;
import top.shixinzhang.datacrawler.R;
import top.shixinzhang.datacrawler.WxFriendsMonitorThread;
import top.shixinzhang.datacrawler.model.WxFriendRequestBean;
import top.shixinzhang.datacrawler.utils.AlertUtil;
import top.shixinzhang.datacrawler.utils.DateUtils;
import top.shixinzhang.datacrawler.utils.FileUtils;
import top.shixinzhang.datacrawler.utils.NodeUtil;
import top.shixinzhang.datacrawler.utils.ShellUtil;
import top.shixinzhang.datacrawler.utils.SpUtil;
import top.shixinzhang.datacrawler.utils.StringUtil;

/**
 * Description:
 * <br> 微信自动点击
 * <p>
 * <br> Created by shixinzhang on 17/5/9.
 * <p>
 * <br> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class WxAutoClick implements CrashHandler.OnCrashCallback, Handler.Callback {
    private final String TAG = this.getClass().getSimpleName();

    public static volatile WxAutoClick mInstance;
    private ExecutorService mThreadPool = Executors.newFixedThreadPool(10);
    private final int mScreenHeight;
    private final String mSwipeCmd;
    private AccessibilityService mAccessibilityService;
    private HashSet<String> mClickedGroupNameSet = new HashSet<>(); //点击的群聊名称集合
    public static HashSet<String> mClickedMemberNameSet = new HashSet<>(); //点击的群友名称集合
    //    private HashSet<String> mSendRequestFriendNameSet = new HashSet<>(); //已发送请求的好友名称集合
    private HashMap<String, Integer> mGroupMemberCountMap = new HashMap<>();
    private WxFriendRequestBean mFriendRequestBean;
    private Context mContext;
    private String mCurrentGroupName;
    private AccessibilityNodeInfo mCurrentRootNode;
    private String mFileTimeStamp = DateUtils.getMMddhhmmss(System.currentTimeMillis());
    private WxFriendsMonitorThread mMonitorThread;
    private String mCurrentClassName;
    private AccessibilityEvent mCurrentEvent;
    private Set<String> mCannotBackActivitySet = new HashSet<>();

    private WxAutoClick(AccessibilityService service) {
        mAccessibilityService = service;
        mContext = service.getApplicationContext();
        mScreenHeight = mContext.getResources().getDisplayMetrics().heightPixels;
        mSwipeCmd = String.format("input swipe %d %d %d %d", 10, mScreenHeight - 450, 10, 100);
        CrashHandler.registerCallback(this);
        initCannotBackActivitySet();

    }

    private void initCannotBackActivitySet() {
        mCannotBackActivitySet = new HashSet<>();
        mCannotBackActivitySet.add(Config.GROUP_CHAT_CLASS_NAME);
        mCannotBackActivitySet.add(Config.GROUP_CHAT_INNER_CLASS_NAME);
        mCannotBackActivitySet.add(Config.CLICK_CLASS_NAME);
        mCannotBackActivitySet.add(Config.ALL_MEMBER_CLASS_NAME);
    }

    public static synchronized WxAutoClick getInstance(AccessibilityService service) {
        if (mInstance == null) {
            mInstance = new WxAutoClick(service);
        }
        return mInstance;
    }

    public void receiveEvent(AccessibilityNodeInfo rootNode, AccessibilityEvent event) {
        mCurrentRootNode = rootNode;
        String className = event.getClassName().toString();
        mCurrentEvent = event;
        if (className.contains("com.tencent.mm") || Config.WX_GRID_VIEW.equals(className)) {
            autoClickWx(event, className);
        }
    }

    /**
     * 微信中的自动点击
     *
     * @param event
     * @param className
     */
    private void autoClickWx(AccessibilityEvent event, String className) {
        if (className.contains("com.temcemt.mm")) {
            mCurrentClassName = className;
        }
        if (Config.WX_HOME_CLASS_NAME.equals(className)) {   //在微信首页
            openGroupChatList(event);
        } else if (Config.GROUP_CHAT_CLASS_NAME.equals(className)) {  //在群聊列表页面
            iterateOpenGroupChat(event);
        } else if (Config.GROUP_CHAT_INNER_CLASS_NAME.equals(className)) {  //在群聊聊天页面
            OpenGroupMembersInfo(event);
        } else if (Config.CLICK_CLASS_NAME.equals(className)) {   //在群信息第一页
            openAllMemberPage();
        } else if (Config.ALL_MEMBER_CLASS_NAME.equals(className) || Config.WX_GRID_VIEW.equals(className)) { //在所有群成员页面
            openFriendsInfoPages();
        } else if (Config.PROFILE_CLASS_NAME.equals(className)) {    //在详细资料页面
            getFriendInfoAndSendRequest();
        } else if (Config.SEND_REQUEST_CLASS_NAME.equals(className)) {   //发送加好友请求
            sendRequest();
        } else if (findNodeInfosById(Config.DIALOG_TITLE_ID) != null) {
            onWxDialog(mCurrentRootNode, null);
        }
    }

    /**
     * 打开群信息页面
     *
     * @param event
     */
    private void OpenGroupMembersInfo(AccessibilityEvent event) {
        if (event == null || !Config.GROUP_CHAT_INNER_CLASS_NAME.equals(event.getClassName().toString())) {
            return;
        }
        if (currentGroupFinishIterate()) {
            clickBack();
            return;
        }
        try {
            AccessibilityNodeInfo actionBarNode = findNodeInfosById("com.tencent.mm:id/fx");
            if (actionBarNode == null) {
                return;
            }
            AccessibilityNodeInfo groupTitleNode = actionBarNode.getChild(0).getChild(1).getChild(0);
            String memberCount = StringUtil.getNumberFromString(groupTitleNode.getText().toString());
            mGroupMemberCountMap.put(mCurrentGroupName, Integer.valueOf(memberCount));  //记录当前群人数
            AccessibilityNodeInfo rightNode = actionBarNode.getChild(1);
            int rightNodeChildCount = rightNode.getChildCount();
            for (int i = 0; i < rightNodeChildCount; i++) {
                AccessibilityNodeInfo child = rightNode.getChild(i);
                if (clickNode(child)) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            onCrash(e);
//            clickBack();
        }
    }

    /**
     * 进入群聊列表页面
     *
     * @param event
     */
    private void openGroupChatList(AccessibilityEvent event) {
        AccessibilityNodeInfo contactNode = findNodeInfosByText("通讯录");
        clickNode(contactNode);
        AccessibilityNodeInfo groupChatListNode = findNodeInfosByText("群聊");
        clickNode(groupChatListNode);
    }

    /**
     * 挨个点击群聊
     *
     * @param event
     */
    private void iterateOpenGroupChat(AccessibilityEvent event) {
        AccessibilityNodeInfo listNode = findNodeInfosById("com.tencent.mm:id/hr"); //ListView
        if (listNode != null) {
            int groupChatCount = listNode.getChildCount();
            for (int i = 0; i < groupChatCount; i++) {
                AccessibilityNodeInfo child = listNode.getChild(i);     //群聊 item
                if (child == null) {
                    continue;
                }
                AccessibilityNodeInfo groupNameNode = findNodeInfosById(child, "com.tencent.mm:id/a1h");
                if (groupNameNode != null && groupNameNode.getText() != null) {
                    String groupName = groupNameNode.getText().toString();
                    if (!currentGroupFinishIterate(groupName)) {
                        mCurrentGroupName = groupName;
                        initClickNameSetData(groupName); //获取该群聊下的点击名称数据
                        clickNode(groupNameNode);
                        break;
                    }
                }
            }
        }
    }


    private boolean currentGroupFinishIterate() {
        return currentGroupFinishIterate(mCurrentGroupName);
    }

    /**
     * 本地获取 该群是否遍历结束 的标志位
     *
     * @param groupName
     * @return
     */
    private boolean currentGroupFinishIterate(String groupName) {
        return (boolean) SpUtil.getDataFromDefault(mContext, getSpIterateFinishName(groupName), false);
    }

    /**
     * 完成遍历，保存
     *
     * @param groupName
     */
    private void setCurrentGroupFinishSp(String groupName) {
        Log.d(TAG, "**********好消息！[" + groupName + "] 加完好友了！！******** ");
        SpUtil.saveDataInDefault(mContext, getSpIterateFinishName(groupName), true);
    }

    /**
     * 拼接一个当前群是否遍历结束的 sp key name
     *
     * @param groupName
     * @return
     */
    private String getSpIterateFinishName(String groupName) {
        return groupName + "_iterate_finish";
    }

    /**
     * 获取 sp 中 groupName 群中的点击名称数据
     *
     * @param groupName
     */
    private void initClickNameSetData(String groupName) {
        mClickedMemberNameSet = (HashSet<String>) SpUtil.getDataFromDefault(mContext, getSpClickName(groupName), new HashSet<String>());
        Log.d(TAG, groupName + " 该群已点击人数：" + mClickedMemberNameSet.size());
    }

    /**
     * 根据群名称拼接一个用于记录，点击过的名称数据 set
     *
     * @param groupName
     * @return
     */
    private String getSpClickName(String groupName) {
        return groupName + "_click_set";
    }

    /**
     * 先把这页的加完，再点击 "查看全部群成员"
     */
    private void openAllMemberPage() {

        AccessibilityNodeInfo listViewNode = findNodeInfosById(mCurrentRootNode, "android:id/list");
        if (listViewNode == null) {
            return;
        }

        int gridViewCount = listViewNode.getChildCount();
        List<AccessibilityNodeInfo> memberNameNode = new ArrayList<>();
        for (int i = 0; i < gridViewCount; i++) {
            AccessibilityNodeInfo memberLinearNode = listViewNode.getChild(i);
            int childCount = memberLinearNode.getChildCount();
            for (int j = 0; j < childCount; j++) {
                if (memberLinearNode.getChild(j) == null || memberLinearNode.getChild(j).getChildCount() < 2) {
                    continue;
                }
                AccessibilityNodeInfo child = memberLinearNode.getChild(j).getChild(1);
                memberNameNode.add(child);
            }
        }
        int memberSize = memberNameNode.size();
        int clickIndex = 0;
        for (; clickIndex < memberSize; clickIndex++) {
            if (checkAndClick(memberNameNode.get(clickIndex))) {
                break;
            }
        }

        if (clickIndex >= memberSize) {
            if (currentGroupFinishIterate(mCurrentGroupName)) {
                clickBack();
                return;
            }
            swipeUp();
            AccessibilityNodeInfo getAllMembersNode = findNodeInfosByText("查看全部群成员");
            if (getAllMembersNode == null) {
                clickBack();
                return;
            }
            //进入群所有成员页面
            if (!clickNode(getAllMembersNode)) {
                AlertUtil.toastShort(mContext, "打开所有成员页面失败");
                clickBack();
            }
        }
    }

    /**
     * 发送好友申请
     */
    private void sendRequest() {
        AccessibilityNodeInfo rootNode = mAccessibilityService.getRootInActiveWindow();
        if (rootNode == null) {
            clickBack();
            return;
        }
        List<AccessibilityNodeInfo> sendNodeList = rootNode.findAccessibilityNodeInfosByText("发送");
        if (sendNodeList == null) {
            clickBack();
            return;
        }
        AccessibilityNodeInfo sendNode = null;
        for (AccessibilityNodeInfo nodeInfo : sendNodeList) {
            if (nodeInfo.getText() != null) {
                if ("发送".equals(nodeInfo.getText().toString())) {
                    sendNode = nodeInfo;
                    break;
                }
            }
        }
        if (sendNode != null) {
            clickNode(sendNode);
            if (getFriendRequestMap() != null) {
                Log.d(TAG, "已申请：" + getFriendRequestMap().size());
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            clickBack();
        }
    }

    private void swipeUp() {
        ShellUtil.execShellCmd(mSwipeCmd);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 提示框
     *
     * @param root
     * @param event
     */
    private void onWxDialog(AccessibilityNodeInfo root, AccessibilityEvent event) {
        String dialogTitle = NodeUtil.getNodeText(root, Config.DIALOG_TITLE_ID);
        if (!TextUtils.isEmpty(dialogTitle) && TextUtils.equals(dialogTitle, "提示")) {
            NodeUtil.actionText(root, getString(R.string.ok));

            if (NodeUtil.hasText(root, getString(R.string.cancel_install))) {
                NodeUtil.actionText(root, getString(R.string.yes));
            } else if (NodeUtil.hasText(root, getString(R.string.no))) {
                NodeUtil.actionText(root, getString(R.string.no));
            }
        } else if (TextUtils.equals(dialogTitle, getString(R.string.update))) {
            NodeUtil.actionText(root, getString(R.string.cancel));
        }
    }

    private String getString(int id) {
        return mContext.getString(id);
    }

    /**
     * 检查这个群成员是否点击过
     *
     * @param memberNode
     * @return
     */
    private boolean checkAndClick(AccessibilityNodeInfo memberNode) {
        if (memberNode == null || memberNode.getText() == null) {
            return false;
        }
        String memberName = memberNode.getText().toString();
        if (!mClickedMemberNameSet.contains(memberName)) {
            if (mMonitorThread == null) {
                initMonitorThread();
            }
            mClickedMemberNameSet.add(memberName);
            clickNode(memberNode);
            return true;
        }
        return false;
    }

    private void clickBack() {
        ShellUtil.execShellCmd("input keyevent 4");
    }

    /**
     * 挨个打开好友信息页面，发送好友申请
     * com.tencent.mm:id/lv
     */
    private void openFriendsInfoPages() {
        if (currentGroupFinishIterate()) {
            clickBack();
            return;
        }
        //找到所有成员的 GridView，遍历点击子孩子
        AccessibilityNodeInfo membersGridView = findNodeInfosById(mCurrentRootNode, "com.tencent.mm:id/cbe");
        if (membersGridView != null) {
            int gridViewCount = membersGridView.getChildCount();
            int clickIndex = 0;
            for (; clickIndex < gridViewCount; clickIndex++) {
                AccessibilityNodeInfo childNode = membersGridView.getChild(clickIndex);
                if (childNode == null || childNode.getChildCount() < 2) {
                    continue;
                }
                if (checkAndClick(childNode.getChild(1))) {
                    break;
                }
            }

            if (clickIndex >= gridViewCount) {
                //结束条件
                Integer memberCount = mGroupMemberCountMap.get(mCurrentGroupName);
                if (mClickedMemberNameSet != null && mClickedMemberNameSet.size() >= memberCount * 0.9) {
                    setCurrentGroupFinishSp(mCurrentGroupName);
                    clickBack();
                    return;
                }
                swipeUp();
                saveFriendRequestData();
            }
        }

    }

    private int lastClickNameNumber;

    /**
     * 保存数据
     */
    private void saveFriendRequestData() {
        final String path = String.format("%s%s_%s.txt", Config.WX_DATA_DIR, "加好友", mCurrentGroupName);
        final String clickNameFilePath = String.format("%s%s_%s.txt", Config.WX_DATA_DIR, "点击过的名称", mCurrentGroupName);
        if (lastClickNameNumber != mClickedMemberNameSet.size()) {
            lastClickNameNumber = mClickedMemberNameSet.size();
            mThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG, "Save info mClickedMemberNameSet.size() " + mClickedMemberNameSet.size() + ", path: " + clickNameFilePath);
                    Log.d(TAG, "Save info mFriendNameStateMap.size() " + getFriendRequestMap().size() + ", path: " + path);
                    FileUtils.writeFile(clickNameFilePath, mFileTimeStamp + "\n" + mClickedMemberNameSet.size() + "\n" + mClickedMemberNameSet.toString(), false);
                    FileUtils.writeFile(path, mFileTimeStamp + "\n" + getFriendRequestMap().size() + "\n" + getFriendRequestMap().toString(), false);
                    SpUtil.saveDataInDefault(mContext, getSpClickName(mCurrentGroupName), mClickedMemberNameSet);

//                SpUtil.saveDataInDefault(mContext, mCurrentGroupName, mFriendRequestBean);
                }
            });
        }

    }

    /**
     * 获取好友信息，如果不是好友就加好友
     */
    private void getFriendInfoAndSendRequest() {
        AccessibilityNodeInfo nameNode = findNodeInfosById("com.tencent.mm:id/la");
        if (nameNode == null || nameNode.getText() == null) {
            return;
        }
        AccessibilityNodeInfo buttonNode = findNodeInfosById("com.tencent.mm:id/acz");
        if (buttonNode == null || buttonNode.getText() == null) {
            clickBack();
            return;
        }

        String name = nameNode.getText().toString();        //名称
        String buttonName = buttonNode.getText().toString();    //按钮
        boolean isFriend = "发消息".equals(buttonName);
        if (getFriendRequestMap().containsKey(name)) {
            clickBack();
            return;
        }
        String state = isFriend ? "已加" : "发出申请";
        getFriendRequestMap().put(name, state + "\n");
        if (!isFriend) {     //不是朋友，发出申请
            clickNode(buttonNode);
        } else {
            clickBack();
        }
    }

    private HashMap<String, String> getFriendRequestMap() {
        if (mFriendRequestBean == null) {
            mFriendRequestBean = WxFriendRequestBean.create();
        }
        return mFriendRequestBean.getFriendNameStateMap();
    }


    public AccessibilityNodeInfo findNodeInfosById(String resId) {
        AccessibilityNodeInfo rootInActiveWindow = mAccessibilityService.getRootInActiveWindow();
        if (rootInActiveWindow == null) {
            return null;
        }
        return findNodeInfosById(rootInActiveWindow, resId);
    }

    private AccessibilityNodeInfo findNodeInfosById(AccessibilityNodeInfo currentRootNode, String resId) {
        if (currentRootNode == null) {
            return findNodeInfosById(resId);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            List<AccessibilityNodeInfo> list = currentRootNode.findAccessibilityNodeInfosByViewId(resId);
            currentRootNode.recycle();
            if (list != null && !list.isEmpty()) {
                return list.get(0);
            }
        }
        return null;
    }

    //通过文本查找
    public AccessibilityNodeInfo findNodeInfosByText(String text) {
        AccessibilityNodeInfo rootInActiveWindow = mAccessibilityService.getRootInActiveWindow();
        List<AccessibilityNodeInfo> list = rootInActiveWindow.findAccessibilityNodeInfosByText(text);
        rootInActiveWindow.recycle();
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    private boolean clickNode(AccessibilityNodeInfo targetNode) {
        targetNode = getClickableTargetNode(targetNode);
        if (targetNode != null) {
            return targetNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        }
        return false;
    }

    private AccessibilityNodeInfo getClickableTargetNode(AccessibilityNodeInfo node) {
        if (node == null) {
            return null;
        }
        if (node.isClickable()) {
            return node;
        } else {
            return getClickableTargetNode(node.getParent());
        }
    }


    private void initMonitorThread() {
        mMonitorThread = new WxFriendsMonitorThread("Monitor_Handler_Thread");
        mMonitorThread.setUIHandler(new Handler(this));
        mMonitorThread.start();
    }

    private void destroyMonitorThread() {
        if (mMonitorThread != null) {
            mMonitorThread.quit();
            mMonitorThread = null;
        }
    }

    private void testFakeData() {
        mFriendRequestBean = WxFriendRequestBean.create();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("sss", "aaa");
        mFriendRequestBean.setFriendNameStateMap(hashMap);
        mCurrentGroupName = "s";
        mClickedGroupNameSet.add("shixin");
        SpUtil.saveDataInDefault(mContext, Config.SP_CLICK_GROUP_SET, mClickedGroupNameSet);
        HashSet<String> dataFromDefault = (HashSet<String>) SpUtil.getDataFromDefault(mContext, Config.SP_CLICK_GROUP_SET, new HashSet<String>());

        SpUtil.saveDataInDefault(mContext, Config.SP_GROUP_NAME, mCurrentGroupName);
        SpUtil.saveDataInDefault(mContext, mCurrentGroupName, mFriendRequestBean);
        initFriendRequestData();
    }

    /**
     * 从 sp 中读取之前保存的数据
     */
    private void initFriendRequestData() {
        mFriendRequestBean = (WxFriendRequestBean) SpUtil.getDataFromDefault(mContext, mCurrentGroupName, WxFriendRequestBean.create());
        if (TextUtils.isEmpty(mFriendRequestBean.toString())) {
            mFriendRequestBean = WxFriendRequestBean.create();
            AlertUtil.toastShort(mContext, "新建一个朋友申请");
        } else {
            AlertUtil.toastShort(mContext, "接着上次的朋友申请");
        }
    }

    public void onDestroy() {
        if (mFriendRequestBean != null) {
            saveFriendRequestData();
        }
        destroyMonitorThread();
    }

    /**
     * 奔溃回调，保存数据
     *
     * @param throwable
     */
    @Override
    public void onCrash(Throwable throwable) {
        AlertUtil.toastShort(mContext, "出事了！赶紧保存数据");
        saveFriendRequestData();
    }

    @Override
    public boolean handleMessage(Message msg) {
        AlertUtil.toastShort(mContext, "出大事了，又不动了！");

        if (!mCannotBackActivitySet.contains(mCurrentClassName)) {      //特殊页面以外的页面卡顿时返回上一级
            clickBack();
            return true;
        }
        if (mCurrentRootNode != null && mCurrentRootNode.findAccessibilityNodeInfosByText("提示") != null) {
            clickBack();
        }
        return true;
    }
}
