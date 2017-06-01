package net.sxkeji.shixinandroiddemo2.test.ipc;

import android.os.Parcel;
import android.os.Parcelable;

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
public class ParcelableGroupBean implements Parcelable {

    private String mName;
    private List<String> mMemberNameList;
    private User mUser;

    /**
     * 需要我们手动创建的构造函数
     * @param name
     * @param memberNameList
     * @param user
     */
    public ParcelableGroupBean(String name, List<String> memberNameList, User user) {
        mName = name;
        mMemberNameList = memberNameList;
        mUser = user;
    }

    /**
     * 1.内容描述
     * @return
     */
    @Override
    public int describeContents() {
        //几乎都返回 0，除非当前对象中存在文件描述符时为 1
        return 0;
    }

    /**
     * 2.序列化
     * @param dest
     * @param flags 0 或者 1
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeStringList(mMemberNameList);
        dest.writeParcelable(mUser, flags);
    }

    /**
     * 3.反序列化
     */
    public static final Creator<ParcelableGroupBean> CREATOR = new Creator<ParcelableGroupBean>() {
        /**
         * 反序列创建对象
         * @param in
         * @return
         */
        @Override
        public ParcelableGroupBean createFromParcel(Parcel in) {
            return new ParcelableGroupBean(in);
        }

        /**
         * 反序列创建对象数组
         * @param size
         * @return
         */
        @Override
        public ParcelableGroupBean[] newArray(int size) {
            return new ParcelableGroupBean[size];
        }
    };

    /**
     * 4.自动创建的的构造器，使用反序列化得到的 Parcel 构造对象
     * @param in
     */
    protected ParcelableGroupBean(Parcel in) {
        mName = in.readString();
        mMemberNameList = in.createStringArrayList();
        //反序列化时，如果熟悉也是 Parcelable 的类，需要使用它的类加载器作为参数，否则报错无法找到类
        mUser = in.readParcelable(User.class.getClassLoader());
    }

}
