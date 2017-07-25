package top.shixinzhang.datacrawler;

import android.os.Environment;

import java.io.File;
import java.util.Set;

/**
 * Description:
 * <br>
 * <p>
 * <br> Created by shixinzhang on 17/5/4.
 * <p>
 * <br> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class Config {
    public static final String FOLDER = "datacrawler" + File.separator;
    public static final String CURRENT_PKG_NAME = "top.shixinzhang.datacrawler";
    public static final String EXT_DIR = Environment.getExternalStorageDirectory() + File.separator + FOLDER;
    public static final String WX_DATA_DIR = Environment.getExternalStorageDirectory() + File.separator + FOLDER + "wxlog" + File.separator;

    //sp
    public static final String SP_GROUP_NAME = "current_group_name";
    public static final String SP_CLICK_GROUP_SET = "click_group_set";
    public static final String SP_CLICK_NAME_SET = "click_name_set";

    public final static String INSTALL_PACKAGE_NAME = "com.android.packageinstaller"; //安装管理器

    //微信
    public final static String WX_PACKAGE_NAME = "com.tencent.mm";
    public final static String WX_HOME_CLASS_NAME = "com.tencent.mm.ui.LauncherUI"; //微信首页
    public final static String GROUP_CHAT_CLASS_NAME = "com.tencent.mm.ui.contact.ChatroomContactUI"; //群聊列表页面
    public final static String GROUP_CHAT_INNER_CLASS_NAME = "com.tencent.mm.ui.chatting.ChattingUI";   //群聊天页面
    public final static String CLICK_CLASS_NAME = "com.tencent.mm.plugin.chatroom.ui.ChatroomInfoUI";     //群信息页面进
    public final static String ALL_MEMBER_CLASS_NAME = "com.tencent.mm.plugin.chatroom.ui.SeeRoomMemberUI";   //群成员页面
    public final static String PROFILE_CLASS_NAME = "com.tencent.mm.plugin.profile.ui.ContactInfoUI"; //联系人详细资料页面
    public final static String SEND_REQUEST_CLASS_NAME = "com.tencent.mm.plugin.profile.ui.SayHiWithSnsPermissionUI"; //发送好友申请页面
    public final static String WX_GRID_VIEW = "android.widget.GridView";
    public static final String DIALOG_TITLE_ID = "com.tencent.mm:id/bpj";   //提示框

    //车镇车源
    public final static String CT_PACKAGE_NAME = "cn.kooki.app.chezhen";
    public final static String CT_MAIN_TAB = "cn.kooki.app.chezhen.activity.maintab.TabRootActivity";  //车镇首页
    public final static String CT_SELECT_CAR_SERIES = "cn.kooki.app.chezhen.activity.SelectCarCatActivity";//选车系
    public final static String CT_SELECT_CAR_TYPE = "cn.kooki.app.chezhen.activity.SelectCarTypeActivity";    //选择车源类型
    public final static String CT_SELECT_CAR_BRAND = "cn.kooki.app.chezhen.activity.carsources.FindBrandListActivity"; //选车款
    public final static String CT_SELECT_CAR_DETAIL = "cn.kooki.app.chezhen.activity.CarDetailActivity";   //车详情
    public final static String CT_RECYCLER_VIEW = "android.support.v7.widget.RecyclerView";   //List 改变
    public final static String CT_CALL_DIALOG = "android.support.v7.app.AlertDialog";  //弹出的对话框

    public final static String RESUME_FROM_CRASH = "crash_from_resume";
}
