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

    protected ParcelableGroupBean(Parcel in) {
        mName = in.readString();
        mMemberNameList = in.createStringArrayList();
    }

    public static final Creator<ParcelableGroupBean> CREATOR = new Creator<ParcelableGroupBean>() {
        @Override
        public ParcelableGroupBean createFromParcel(Parcel in) {
            return new ParcelableGroupBean(in);
        }

        @Override
        public ParcelableGroupBean[] newArray(int size) {
            return new ParcelableGroupBean[size];
        }
    };

    /**
     * 1.内容描述
     * @return
     */
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mName);
        parcel.writeStringList(mMemberNameList);
    }
}
