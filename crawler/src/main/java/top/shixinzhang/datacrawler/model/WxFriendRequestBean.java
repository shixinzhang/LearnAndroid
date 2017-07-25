package top.shixinzhang.datacrawler.model;

import android.text.TextUtils;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Description:
 * <br>
 * <p>
 * <br> Created by shixinzhang on 17/5/11.
 * <p>
 * <br> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class WxFriendRequestBean implements Serializable {
    private static final long serialVersionUID = 7406922042380482635L;
    private int mNumber;    //已申请人数
    private HashMap<String, String> mFriendNameStateMap;  //好友名称与状态 map

    public WxFriendRequestBean() {
        mFriendNameStateMap = new HashMap<>();
    }

    public WxFriendRequestBean(HashMap<String, String> friendNameStateMap) {
        mFriendNameStateMap = friendNameStateMap;
    }

    public int getNumber() {
        return mNumber;
    }

    public HashMap<String, String> getFriendNameStateMap() {
        return mFriendNameStateMap;
    }

    public void setFriendNameStateMap(HashMap<String, String> friendNameStateMap) {
        mFriendNameStateMap = friendNameStateMap;
        mNumber = mFriendNameStateMap.size();
    }

    @Override
    public String toString() {
        if (getFriendNameStateMap() == null || getFriendNameStateMap().isEmpty()) {
            return "";
        }
        return mNumber + "\n" + getFriendNameStateMap().toString();
    }

    public static WxFriendRequestBean create() {
        return new WxFriendRequestBean();
    }
}
