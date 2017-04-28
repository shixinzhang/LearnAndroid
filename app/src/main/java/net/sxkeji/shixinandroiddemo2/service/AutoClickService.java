package net.sxkeji.shixinandroiddemo2.service;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <br/> Description: 使用辅助功能模拟点击事件
 * <p>
 * <br/> Created by shixinzhang on 17/3/31.
 * <p>
 * <br/> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class AutoClickService extends AccessibilityService {
    private final String TAG = this.getClass().getSimpleName();

    private final String CLICK_PACKAGE_NAME = "com.tencent.mm";
    private final String INSTALL_PACKAGE_NAME = "com.android.packageinstaller"; //安装管理器

    private final String CLICK_CLASS_NAME = "com.tencent.mm.plugin.chatroom.ui.ChatroomInfoUI";     //群信息页面进
    private final String ALL_MEMBER_CLASS_NAME = "com.tencent.mm.plugin.chatroom.ui.SeeRoomMemberUI";   //群成员页面
    private final String PROFILE_CLASS_NAME = "com.tencent.mm.plugin.profile.ui.ContactInfoUI"; //联系人详细资料页面

//    private static volatile Map<String, Boolean> friendsNameStateMap;    //好友名称和是否好友的状态

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
//        if (friendsNameStateMap == null) {
//            friendsNameStateMap = new HashMap<>();
//        }

        //也可以在这里配置信息
        showToast("自动点击服务已开启，请打开微信");

//        new AlertDialog.Builder(getApplicationContext())
//                .setTitle("已连接").show();
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.i(TAG, event.getPackageName().toString() + " , " + event.getClassName().toString());

        String packageName = event.getPackageName().toString();
        if (!CLICK_PACKAGE_NAME.equals(packageName) && !INSTALL_PACKAGE_NAME.equals(packageName)) {
            return;
        }
        int eventType = event.getEventType();
        switch (eventType) {
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:  //监听进入界面
                String className = event.getClassName().toString();
                if (CLICK_CLASS_NAME.equals(className)) {   //在群信息第一页
                    openAllMemberPage();
                }
                if (ALL_MEMBER_CLASS_NAME.equals(className)) { //在所有群成员页面
                    openFriendsInfoPages();
                }
                if (PROFILE_CLASS_NAME.equals(className)) {    //在详细资料页面
                    getFriendInfoAndSendRequest();
                }


                break;
        }

    }

    /**
     * 点击 "查看全部群成员"
     */
    private void openAllMemberPage() {
        boolean openAllMember = performClickByName("查看全部群成员");
        //进入群所有成员页面
        if (!openAllMember) {
            showToast("打开所有成员页面失败");
        }
    }

    /**
     * 挨个打开好友信息页面，发送好友申请
     * com.tencent.mm:id/lv
     */
    private void openFriendsInfoPages() {
        //找到所有成员的 GridView，遍历点击子孩子
        AccessibilityNodeInfo membersGridView = findNodeInfosById("com.tencent.mm:id/cbe");
        if (membersGridView != null) {
            int childCount = membersGridView.getChildCount();
            Log.i(TAG, String.format("该群有 %d 人", childCount));

            for (int i = 0; i < childCount; i++) {
                AccessibilityNodeInfo childNode = membersGridView.getChild(i);
                clickNode(childNode);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

//                membersGridView.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD);
            }
            //com.tencent.mm:id/la  名称ID
            //添加到通讯录 com.tencent.mm:id/acz clickable

            //
        }

    }

    /**
     * 获取好友信息，如果不是好友就加好友
     *
     */
    private void getFriendInfoAndSendRequest() {

        String name = "";
        Boolean isFriend = false;

        AccessibilityNodeInfo nameNode = findNodeInfosById("com.tencent.mm:id/la");
        if (nameNode != null) {
            name = nameNode.getText().toString();
            Log.i(TAG, String.format("名称为: %s ", name));
        }

        AccessibilityNodeInfo buttonNode = findNodeInfosById("com.tencent.mm:id/acz");
        if (buttonNode != null) {
            String buttonName = buttonNode.getText().toString();
            isFriend = "发消息".equals(buttonName);    //是好友才可以发消息
            Log.i(TAG, "按钮名称为：" + buttonName);
        }

        if (TextUtils.isEmpty(name)) {
//            friendsNameStateMap.put(name, isFriend);
        }
//        Log.i(TAG, "**************************************  " + friendsNameStateMap.size());
    }


    private void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInterrupt() {
        showToast("自动点击服务被中断");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        showToast("自动点击服务被系统关闭了，请开启");
        return super.onUnbind(intent);

    }


    /**
     * 递归找到可以点击的节点
     *
     * @param node
     * @return
     */
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

    //点击
    private boolean performClickByName(String buttonName) {
        Log.i(TAG, "要点击的按钮名称：" + buttonName);

        return clickNode(findNodeInfosByText(buttonName));

    }

    /**
     * 找到可以点击的按钮，返回点击结果
     *
     * @param targetNode
     * @return
     */
    private boolean clickNode(AccessibilityNodeInfo targetNode) {
        targetNode = getClickableTargetNode(targetNode);
        if (targetNode != null) {
            return targetNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        }
        return false;
    }

    //点击
    private boolean performClickById(String resourceId) {
        Log.i(TAG, "要点击的按钮 ID：" + resourceId);

        AccessibilityNodeInfo targetNode = null;
        targetNode = findNodeInfosById(resourceId);
        if (targetNode != null && targetNode.isClickable()) {
            return targetNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        }
        return false;
    }

    //通过id查找
    public AccessibilityNodeInfo findNodeInfosById(String resId) {
        AccessibilityNodeInfo currentRootNode = getRootInActiveWindow();
        if (currentRootNode == null){
            return null;
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
        AccessibilityNodeInfo currentRootNode = getRootInActiveWindow();
        List<AccessibilityNodeInfo> list = currentRootNode.findAccessibilityNodeInfosByText(text);
        currentRootNode.recycle();
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
}
