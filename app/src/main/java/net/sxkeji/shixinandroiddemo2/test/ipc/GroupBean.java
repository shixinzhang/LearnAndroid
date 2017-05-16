package net.sxkeji.shixinandroiddemo2.test.ipc;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

/**
 * <header>
 * Description:
 * </header>
 * <p>
 * Author: shixinzhang
 * </p>
 * <p>
 * Create at: 5/16/2017
 * </p>
 * <p>
 * Update at: 5/16/2017
 * </p>
 * <p>
 * Related links: <a href="${link_address}">${linkName}</a>
 * </p>
 */
public class GroupBean implements Serializable {

    private static final long serialVersionUID = 8829975621220483374L;
    private String mName;
    private List<String> mMemberNameList;

    public GroupBean() {
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public List<String> getMemberNameList() {
        return mMemberNameList;
    }

    public void setMemberNameList(List<String> memberNameList) {
        mMemberNameList = memberNameList;
    }

    private void writeObject(java.io.ObjectOutputStream out) throws IOException{
        //...
    }
}
