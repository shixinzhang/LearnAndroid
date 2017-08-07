/*
 * Copyright (c) 2017. shixinzhang (shixinzhang2016@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package top.shixinzhang.mvpcrawler.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;

import static android.content.Context.CLIPBOARD_SERVICE;

public class NodeUtil {

    public static boolean clickNode(@NonNull AccessibilityNodeInfo root, final String viewId) {
        AccessibilityNodeInfo nodeInfo = findClickableNode(root, viewId);
        if (null != nodeInfo)
            return nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        return false;
    }

    public static boolean clickNode(@NonNull AccessibilityNodeInfo root, final String viewId, final String text) {
        AccessibilityNodeInfo nodeInfo = findClickableNode(root, viewId, text);
        if (null != nodeInfo) {
            return nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        }
        return false;
    }

    public static boolean actionText(@NonNull AccessibilityNodeInfo root, String buttonText) {
        AccessibilityNodeInfo node = getClickableNode(root, buttonText);
        if (null != node) {
            return node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        }
        return false;
    }

    public static boolean clickNode(@NonNull AccessibilityNodeInfo root, final String viewId, int action) {
        AccessibilityNodeInfo nodeInfo = findNode(root, viewId);
        if (null != nodeInfo) {
            AccessibilityNodeInfo clickNode = getClickableNode(nodeInfo);
            if (null != clickNode)
            return clickNode.performAction(action);
        }

        return false;
    }

    public static boolean actionText(@NonNull AccessibilityNodeInfo root, String buttonText, int action) {
        AccessibilityNodeInfo node = getClickableNode(root, buttonText);
        if (null != node) {
            return node.performAction(action);
        }
        return false;
    }


    public static AccessibilityNodeInfo findNode(@NonNull AccessibilityNodeInfo root, final String viewId) {
        List<AccessibilityNodeInfo> list = root.findAccessibilityNodeInfosByViewId(viewId);

        if (null == list || list.isEmpty())
            return null;
        return list.get(0);
    }

    public static AccessibilityNodeInfo findNode(@NonNull AccessibilityNodeInfo root, final String viewId, final String text) {
        List<AccessibilityNodeInfo> list = root.findAccessibilityNodeInfosByViewId(viewId);

        if (null == list || list.isEmpty())
            return null;

        for (AccessibilityNodeInfo nodeInfo : list) {
            if (TextUtils.equals(nodeInfo.getText(), text))
                return nodeInfo;
        }
        return null;
    }

    public static AccessibilityNodeInfo findClickableNode(@NonNull AccessibilityNodeInfo root, final String viewId) {
        AccessibilityNodeInfo node = findNode(root, viewId);
        if (null != node)
            return getClickableNode(node);
        return null;
    }

    public static AccessibilityNodeInfo findClickableNode(@NonNull AccessibilityNodeInfo root, final String viewId, final String text) {
        AccessibilityNodeInfo node = findNode(root, viewId, text);
        if (null != node)
            return getClickableNode(node);
        return null;
    }

    public static String getNodeText(@NonNull AccessibilityNodeInfo root, final String viewId) {
        AccessibilityNodeInfo nodeInfo = findNode(root, viewId);
        if (nodeInfo == null || nodeInfo.getText() == null)
            return null;
        return nodeInfo.getText().toString();
    }

    /**
     *  view 是否对用户可见
     * @param root
     * @param viewId
     * @param viewText
     * @return
     */
    public static boolean viewVisible(@NonNull AccessibilityNodeInfo root, final String viewId, final String viewText) {
        List<AccessibilityNodeInfo> list = root.findAccessibilityNodeInfosByViewId(viewId);
        if (null == list || list.isEmpty()) return false;

        for( AccessibilityNodeInfo temp : list) {
            if (TextUtils.equals(viewText, temp.getText()) && temp.isVisibleToUser())
                return true;
        }
        return false;
    }

    public static boolean viewVisible(@NonNull AccessibilityNodeInfo root, final String id) {
        List<AccessibilityNodeInfo> list = root.findAccessibilityNodeInfosByViewId(id);
        if (null == list || list.isEmpty() || !list.get(0).isVisibleToUser())
            return false;
        return true;
    }

    public static boolean viewEnable(@NonNull AccessibilityNodeInfo root, final String id) {
        List<AccessibilityNodeInfo> list = root.findAccessibilityNodeInfosByViewId(id);
        if (null == list || list.isEmpty() || !list.get(0).isEnabled())
            return false;
        return true;
    }

    /**
     *
     * @param root
     * @param text
     * @return
     */
    public static boolean hasText(@NonNull AccessibilityNodeInfo root, final String text) {
        List<AccessibilityNodeInfo> list = root.findAccessibilityNodeInfosByText(text);
        return null != list && !list.isEmpty() && list.get(0).isVisibleToUser();
    }

    public static boolean hasNode(@NonNull AccessibilityNodeInfo root, final String viewId) {
        AccessibilityNodeInfo nodeInfo = findNode(root, viewId);
        return null != nodeInfo;
    }

    /**
     *
     * @param node
     * @return
     */
    public static AccessibilityNodeInfo getClickableNode(AccessibilityNodeInfo node) {
        if (node.isClickable()) {
            return node;
        } else {
            AccessibilityNodeInfo parentNode = node;
            for (int i = 0; i < 5; i++) {
                if (null != parentNode) {
                    parentNode = parentNode.getParent();
                    if (null != parentNode && parentNode.isClickable()) {
                        return parentNode;
                    }
                }
            }
        }

        return null;
    }

    private static AccessibilityNodeInfo getClickableNode(@NonNull AccessibilityNodeInfo root, String text) {

        if (root == null || TextUtils.isEmpty(text))  return null;

        List<AccessibilityNodeInfo> list = root.findAccessibilityNodeInfosByText(text);
        if (null == list || list.isEmpty()) return null;

        return getClickableNode(list.get(list.size() - 1));
    }

    /**
     *
     * @param context
     * @param nodeInfo
     * @param text
     * @return
     */
    private static boolean performEdit(@NonNull Context context, AccessibilityNodeInfo nodeInfo, final String text) {
        if (null == nodeInfo || TextUtils.isEmpty(text))
            return false;

        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("label", text);
        clipboard.setPrimaryClip(clip);
        nodeInfo.performAction(AccessibilityNodeInfo.ACTION_PASTE);
        return true;
    }

    public static boolean performEdit(@NonNull Context context, AccessibilityNodeInfo root, final String editViewId, final String text) {
        AccessibilityNodeInfo editNode = findNode(root, editViewId);
        return performEdit(context, editNode, text);
    }

}
