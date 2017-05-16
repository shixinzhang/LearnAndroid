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

package net.sxkeji.shixinandroiddemo2.bean;

import android.util.LongSparseArray;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Description:
 * <br> 微信加好友实体类
 * <p>
 * <br> Created by shixinzhang on 17/5/11.
 * <p>
 * <br> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class WxFriendRequestBean implements Serializable {
    private static final long serialVersionUID = 7406922042380482635L;
    private List<String> mFinishedGroupList;
    private long saveTimeMillis;    //保存时间
    private int mNumber;    //已申请人数
    private HashMap<String, String> mFriendNameStateMap;  //好友名称与状态 map
    private HashMap<String, List<String>> mGroupNameMap;
//    private LongSparseArray<Integer> mDateRequestMap = new LongSparseArray<>();

    public WxFriendRequestBean() {
        mFinishedGroupList = new LinkedList<>();
        mFriendNameStateMap = new HashMap<>();
    }

    public HashMap<String, List<String>> getGroupNameMap() {
        return mGroupNameMap;
    }

    public void setGroupNameMap(HashMap<String, List<String>> groupNameMap) {
        mGroupNameMap = groupNameMap;
    }

    public WxFriendRequestBean(HashMap<String, String> friendNameStateMap) {
        mFriendNameStateMap = friendNameStateMap;
    }

    public List<String> getFinishedGroupList() {
        return mFinishedGroupList;
    }

    public void setFinishedGroupList(List<String> finishedGroupList) {
        mFinishedGroupList = finishedGroupList;
    }

    public long getSaveTimeMillis() {
        return saveTimeMillis;
    }

    public void setSaveTimeMillis(long saveTimeMillis) {
        this.saveTimeMillis = saveTimeMillis;
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
        return mFinishedGroupList.toString() + "\n" + saveTimeMillis + "\n" +
                mNumber + "\n" + getFriendNameStateMap().toString();
    }

    public static WxFriendRequestBean create() {
        return new WxFriendRequestBean();
    }
}
