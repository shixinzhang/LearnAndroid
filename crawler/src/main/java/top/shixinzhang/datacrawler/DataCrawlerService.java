package top.shixinzhang.datacrawler;

import android.accessibilityservice.AccessibilityService;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import top.shixinzhang.datacrawler.accessibility.WxAutoClick;
import top.shixinzhang.datacrawler.utils.AlertUtil;
import top.shixinzhang.datacrawler.utils.ApplicationUtil;
import top.shixinzhang.datacrawler.utils.DateUtils;
import top.shixinzhang.datacrawler.utils.FileUtils;
import top.shixinzhang.datacrawler.utils.NodeUtil;
import top.shixinzhang.datacrawler.utils.PageUtil;
import top.shixinzhang.datacrawler.utils.ShellUtil;
import top.shixinzhang.datacrawler.utils.StringUtil;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class DataCrawlerService extends AccessibilityService implements Handler.Callback {

    public static final String MODE_KEY = "MODE";
    public final String TAG = DataCrawlerService.class.getSimpleName();

    private Set<String> mClickCarBrandSet = new HashSet<>();
    private Set<String> mClickCarSeriesSet = new HashSet<>();   //已点击的车系
    private Set<String> mClickCarModelSet = new HashSet<>();   //已点击的车款
    public static volatile Map<String, String> mCarPhoneMap = new HashMap<>(); //车款和电话映射表
    private AtomicBoolean mCanGoToNextCarModule = new AtomicBoolean(true);
    private LinkedList<AccessibilityNodeInfo> mCarModuleNodeList = new LinkedList<>();
    private String mCurrentCarName;
    private int mScreenHeight;
    private String mSwipeCmd;
    private String mSwipeCmdUp; //上滑

    private String mCurrentActivityName;
    private String mCurrentBrandName;
    private String mCurrentSeriesName;  //当前查看的车系名称
    private String mFileTimeStamp = DateUtils.getMMddhhmmss(System.currentTimeMillis());

    private int mMode;
    public static final int MODE_STOP = -1; //停止
    public static final int MODE_SELECT_BRAND = 1;    //首页，选择品牌
    private final int MODE_SELECT_CAR_SERIES = 2;   //选择车系
    private final int MODE_SELECT_CAR_MODEL = 3;    //选择车款
    private final int MODE_CLICK_PHONE_NODE = 4;
    private final int MODE_GET_NUMBER = 5;
    private boolean mFirstOpen = true;

    private static List<String> mWhiteClassNameList;    //白名单

    static {
        mWhiteClassNameList = Arrays.asList(Config.CT_MAIN_TAB, Config.CT_SELECT_CAR_SERIES,
                Config.CT_SELECT_CAR_TYPE, Config.CT_SELECT_CAR_BRAND, Config.CT_SELECT_CAR_BRAND,
                Config.CT_SELECT_CAR_DETAIL, Config.CT_RECYCLER_VIEW, Config.CT_CALL_DIALOG);
    }


    private CarTownMonitorThread mMonitorHandlerThread;

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        showToast("自动点击服务已开启");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mScreenHeight = getApplicationContext().getResources().getDisplayMetrics().heightPixels;
        mSwipeCmd = String.format("input swipe %d %d %d %d", 10, mScreenHeight - 250, 10, 100);
        mSwipeCmdUp = String.format("input swipe %d %d %d %d", 10, 100, 10, mScreenHeight - 100);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mCanGoToNextCarModule.set(true);
        if (null != intent) {
            mMode = intent.getIntExtra(MODE_KEY, MODE_SELECT_BRAND);
            Log.i(TAG, "onStartCommand mode:" + mMode);
            if (mMode == MODE_SELECT_BRAND) {
                ApplicationUtil.startApplication(this, Config.CT_PACKAGE_NAME);
                mFirstOpen = true;
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void initMonitorHandler() {
        mMonitorHandlerThread = new CarTownMonitorThread("Monitor_Handler_Thread");
        mMonitorHandlerThread.setUIHandler(new Handler(this));
        mMonitorHandlerThread.start();
    }


    private void destroyMonitorHandler() {
        mMonitorHandlerThread.quit();
        mMonitorHandlerThread = null;
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        String packageName = event.getPackageName().toString();
        int eventType = event.getEventType();
        switch (eventType) {
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:  //监听进入界面
            case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:
                if (Config.WX_PACKAGE_NAME.equals(packageName)) {
                    WxAutoClick.getInstance(this).receiveEvent(getRootInActiveWindow(), event);
                } else if (Config.CT_PACKAGE_NAME.equals(packageName)) {
                    dealWithCarTown(getRootInActiveWindow(), event);
                }
                break;
        }

    }

    /**
     * 处理车镇 v4.0.1
     *
     * @param rootNode
     * @param event
     */
    private void dealWithCarTown(AccessibilityNodeInfo rootNode, AccessibilityEvent event) {
//        if (mMonitorHandlerThread == null) {
//            initMonitorHandler();
//        }
        if (mFirstOpen) {
            ShellUtil.execShellCmd(mSwipeCmdUp);
            mFirstOpen = false;
        }

        String className = event.getClassName().toString();
        if (!mWhiteClassNameList.contains(className)) {
            return;
        }

        Log.d(TAG, "当前 mode: " + mMode + "\nclassName: " + className);

        if (className.contains("cn.kooki.app.chezhen.activity")) {
            mCurrentActivityName = className;
        }

        //还是通过 mode 分辨靠谱

        if (mMode == MODE_SELECT_BRAND) {
            doMainTabWork(className);
        } else if (mMode == MODE_SELECT_CAR_SERIES) {
            doCarSeriesWork(rootNode, className);
        } else if (mMode == MODE_SELECT_CAR_MODEL) {
            doCarModelWork(rootNode, className);
        } else if (mMode == MODE_CLICK_PHONE_NODE) {
            clickPhoneNode(className);
        } else if (mMode == MODE_GET_NUMBER) {
            getPhoneNumber();
        }
    }

    /**
     * 点击 "电话" 按钮
     *
     * @param className
     */
    private void clickPhoneNode(final String className) {

        if (PageUtil.isMainTab(className)) {
            return;
        } else if (PageUtil.isSeriesTab(className)) {
            return;
        } else if (PageUtil.isModelTab(className)) {
            return;
        }

        SystemClock.sleep(500);
        String order = "", carName = "", shopName = "", company = "";

        order = getTextByNodeId("cn.kooki.app.chezhen:id/tv_order_number");
        carName = getTextByNodeId("cn.kooki.app.chezhen:id/tv_carlist_name");
        shopName = getTextByNodeId("cn.kooki.app.chezhen:id/tv_shop_name");
        company = getTextByNodeId("cn.kooki.app.chezhen:id/tv_personName_or_company");

        if (!TextUtils.isEmpty(order)) {
            order = order.replace("车源编号", "");
        }

        if (TextUtils.isEmpty(order) && TextUtils.isEmpty(carName) && TextUtils.isEmpty(shopName)) {
            Log.e(TAG, "车源编号和车源名称都没找到！");
            return;
        }
        try {
            // TODO: 17/7/25 还要拿到公司？

            carName = order + "_" + carName + "_" + shopName + "_" + company;
            mCurrentCarName = carName;
            if (mCarPhoneMap.containsKey(carName) && !"1".equals(mCarPhoneMap.get(carName))) {     //已经点击过了，返回
                mMode = MODE_SELECT_CAR_MODEL;
                clickBack();
                return;
            }
            mCarPhoneMap.put(carName, "1");
        } catch (Exception e) {
            e.printStackTrace();
            mMode = MODE_SELECT_CAR_MODEL;
            clickBack();
        }

        AccessibilityNodeInfo phoneNode = findNodeInfosByText("电话");
        if (phoneNode == null) {
            phoneNode = findNodeInfosById("cn.kooki.app.chezhen:id/phone_btn");//电话
        }
        if (phoneNode != null) {
            mMode = MODE_GET_NUMBER;
            clickNode(phoneNode);
        } else {
            mMode = MODE_SELECT_CAR_MODEL;
            Log.d(TAG, "没找到电话节点");
        }
    }

    private String getTextByNodeId(final String nodeId) {
        AccessibilityNodeInfo node = findNodeInfosById(nodeId);
        if (node != null && node.getText() != null) {
            return node.getText().toString();
        }
        return null;
    }

    /**
     * 选择车款
     *
     * @param rootNode
     * @param className
     */
    private void doCarModelWork(AccessibilityNodeInfo rootNode, String className) {

        if (PageUtil.isMainTab(className)) {
            return;
        } else if (PageUtil.isSeriesTab(className)) {
            return;
        } else if (PageUtil.isDetailTab(className)) {
//            clickBack();
            return;
        }


//        if (event == null ||
//                !(Config.CT_SELECT_CAR_BRAND.equals(event.getClassName().toString()) || Config.CT_SELECT_CAR_BRAND.equals(mCurrentActivityName))) {
//            return;
//        }
        if (rootNode == null || NodeUtil.hasText(rootNode, "车源详情") || NodeUtil.hasText(rootNode, "发起定金担保")) {
            return;
        }
        if (NodeUtil.hasText(rootNode, "没有更多数据了") || NodeUtil.hasText(rootNode, "无相关车源")) {
            Log.d(TAG, "没有数据了");
            mMode = MODE_SELECT_CAR_SERIES;
            clickBack();
            return;
        }
        SystemClock.sleep(200);

        AccessibilityNodeInfo parentNode = findNodeInfosById("cn.kooki.app.chezhen:id/recyclerview"); //recycleView 的父亲节点
        if (parentNode != null) {
            int childCount = parentNode.getChildCount();
            if (childCount < 1) {
                Log.e(TAG, "车款列表的 RecyclerView 不在索引 1 上");
                return;
            }
            AccessibilityNodeInfo listNode = parentNode.getChild(1);   //RecyclerView
            if (listNode != null) {
                int recyclerViewCount = listNode.getChildCount();
                AccessibilityNodeInfo modelParentNode;
                String carName = "";
                StringBuilder identityStr;  //用来辨识是否点击过的唯一标识
                int checkIndex = 1;
                for (; checkIndex < recyclerViewCount; checkIndex++) {
                    try {
                        modelParentNode = listNode.getChild(checkIndex).getChild(0);        //RelativeLayout
                        if (modelParentNode != null && modelParentNode.getChildCount() > 5) {
                            AccessibilityNodeInfo nameNode = modelParentNode.getChild(0);   //车款名称
                            AccessibilityNodeInfo colorNode = modelParentNode.getChild(2);  //车款颜色
                            AccessibilityNodeInfo priceNode = modelParentNode.getChild(4);  //价格
                            AccessibilityNodeInfo authorNameNode = modelParentNode.getChild(5);     //发布者所属公司
                            if (nameNode != null && nameNode.getText() != null) {
                                carName = nameNode.getText().toString();
                            }

                            identityStr = new StringBuilder(carName);
                            safetyAppendString(identityStr, colorNode);
                            safetyAppendString(identityStr, priceNode);
                            safetyAppendString(identityStr, authorNameNode);
                            Log.d("service", "当前车款名称：" + identityStr.toString());
                            if (notClickedCarModel(identityStr.toString())) {
                                mClickCarModelSet.add(identityStr.toString());
                                mMode = MODE_CLICK_PHONE_NODE;
                                clickNode(modelParentNode);
                                return;
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        mMode = MODE_SELECT_CAR_MODEL;
                        Log.e(TAG, e.getMessage());
                    }
                }

                if (checkIndex >= recyclerViewCount) {
                    ShellUtil.execShellCmd(mSwipeCmd);
                    saveNumber();
                }
            } else {
                Log.d(TAG, "没找到车款列表 RecyclerView ");
            }
        } else {
            Log.d(TAG, "没找到车款列表的 RecyclerView 父节点");
        }
    }

    private void safetyAppendString(final StringBuilder identityStr, final AccessibilityNodeInfo colorNode) {
        if (identityStr == null || colorNode == null || colorNode.getText() == null) {
            return;
        }
        identityStr.append(colorNode.getText().toString());
    }

    private int mLastPhoneNumber;

    /**
     * 保存号码，去重
     */
    private void saveNumber() {
        final Collection<String> values = mCarPhoneMap.values();
        final HashSet<String> phoneSet = new HashSet<>(values);


        if (mLastPhoneNumber != phoneSet.size()) {
            Log.d(TAG, phoneSet.size() + " , " + values.toString());
            mLastPhoneNumber = phoneSet.size();
            final String path = String.format("%s%s_%s_%s.txt", Config.EXT_DIR, "车镇_去重", mFileTimeStamp, "number");
            final String duplicatePath = String.format("%s%s_%s_%s.txt", Config.EXT_DIR, "车镇_未去重", mFileTimeStamp, "number");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    FileUtils.writeFile(path, phoneSet.size() + "\n" + phoneSet.toString(), false);
                    FileUtils.writeFile(duplicatePath, values.size() + "\n" + values.toString(), false);
                }
            }).start();
        }
    }

    private void clickBack() {
        ShellUtil.execShellCmd("input keyevent 4");
    }

    /**
     * 之前是否点击过这个品牌
     *
     * @param name
     * @return
     */
    private boolean notClickedCarBrand(String name) {
        Log.d(TAG, "当前品牌名称：" + name + "\n" + mClickCarBrandSet.toString());
        return !TextUtils.isEmpty(name) && !mClickCarBrandSet.contains(name);
    }

    /**
     * 之前是否点击过这个车系
     *
     * @param name
     * @return
     */
    private boolean notClickedCarSeries(String name) {
        Log.d(TAG, "当前车系名称：" + name + "\n" + mClickCarSeriesSet.toString());
        return !TextUtils.isEmpty(name) && !mClickCarSeriesSet.contains(name);
    }

    /**
     * 是否之前点击过这个车款
     *
     * @param name
     * @return
     */
    private boolean notClickedCarModel(String name) {
//        Log.d(TAG, "当前车款名称：" + name + "\n" + mClickCarModelSet.toString());
        return !TextUtils.isEmpty(name) && !mClickCarModelSet.contains(name);
    }

    /**
     * 拿到号码
     */
    private void getPhoneNumber() {
        AccessibilityNodeInfo msgNode = findNodeInfosById("android:id/message");
        if (msgNode != null) {
            String message = msgNode.getText().toString();
            String phoneNumber = StringUtil.getNumberFromString(message);

            if ("1".equals(mCarPhoneMap.get(mCurrentCarName))) {
                mCarPhoneMap.put(mCurrentCarName, phoneNumber);
            }
            Log.i(TAG, mCurrentCarName);
            Log.i(TAG, "current number : " + phoneNumber);
            Log.i(TAG, "phone size : " + mCarPhoneMap.size());
            AccessibilityNodeInfo cancelNode = findNodeInfosByText("取消");
            if (cancelNode != null) {
                mMode = MODE_CLICK_PHONE_NODE;
                clickNode(cancelNode);
            } else {
                AlertUtil.toastShort("找不到取消按钮");
            }
        }
    }

    /**
     * 选择车系页面
     *
     * @param rootNode
     * @param className
     */
    private void doCarSeriesWork(AccessibilityNodeInfo rootNode, String className) {

        if (PageUtil.isMainTab(className)) {
            return;
        } else if (PageUtil.isModelTab(className)) {
            return;
        } else if (PageUtil.isDetailTab(className)) {
            return;
        }

//        if (className == null ||
//                !(Config.CT_SELECT_CAR_SERIES.equals(className)
//                        || Config.CT_SELECT_CAR_SERIES.equals(mCurrentActivityName)
//                        || Config.CT_RECYCLER_VIEW.equals(className))) {
//            return;
//        }

        AccessibilityNodeInfo listNode = findNodeInfosById("cn.kooki.app.chezhen:id/rv_sticky_example");    //RecyclerView

        if (listNode != null) {
            // TODO: 17/7/25 什么时候返回上一级
            if (Config.CT_RECYCLER_VIEW.equals(className)) { //滚动完，检查是否到底
                if (checkNoMoreSeriesData(listNode)) {
                    mMode = MODE_SELECT_BRAND;
                    clickBack();
                    return;
                }
            }

            int recyclerViewCount = listNode.getChildCount();
            int checkIndex = 0;
            AccessibilityNodeInfo child;
            String carSeriesName;
            int clickSeriesIndex;   //点击哪个车系
            for (; checkIndex < recyclerViewCount; checkIndex++) {
                child = listNode.getChild(checkIndex);
                if (child == null) {
                    Log.d(TAG, "车系列表，没找到第 " + checkIndex + " 个节点");
                    continue;
                }

                clickSeriesIndex = child.getChildCount() == 3 ? 1 : 0;
                try {
                    carSeriesName = child.getChild(clickSeriesIndex).getChild(0).getText().toString();
                    if (notClickedCarSeries(carSeriesName)) {
                        Log.d(TAG, "选了车系：" + carSeriesName);
                        mClickCarSeriesSet.add(carSeriesName);
                        mCurrentSeriesName = carSeriesName;
                        mMode = MODE_SELECT_CAR_MODEL;
                        clickNode(child);
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG, e.getMessage());
                }
            }
            if (checkIndex >= recyclerViewCount) {
                ShellUtil.execShellCmd(mSwipeCmd);
                mMode = MODE_SELECT_CAR_SERIES;
//                saveNumber();
            }

        } else {
            Log.d(TAG, "没找到车系列表的 RecyclerView");
        }
    }

    private String mLastCarSeriesName;  //上次最后一个车系名称

    /**
     * 检查是否没有更多车系数据了
     *
     * @param listNode
     * @return true if no more data
     */
    private boolean checkNoMoreSeriesData(AccessibilityNodeInfo listNode) {
//        AccessibilityNodeInfo lastChildNode = listNode.getChild(listNode.getChildCount() - 1).getChild(0);  //最后一个元素
//        if (lastChildNode == null || TextUtils.isEmpty(lastChildNode.getText())) {
//            return false;
//        }
        if (!TextUtils.isEmpty(mCurrentSeriesName)) {   //只要滚动后的列表中包含上次最后的数据，就算没有更多数据
            for (int i = 0; i < listNode.getChildCount(); i++) {
                AccessibilityNodeInfo child = listNode.getChild(i).getChild(0);
                if (child != null && child.getText() != null && mCurrentSeriesName.equals(child.getText().toString())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 在车镇首页，点击各个品牌
     *
     * @param className
     */

    private void doMainTabWork(final String className) {
        if (PageUtil.isSeriesTab(className)) {
            clickBack();
            return;
        } else if (PageUtil.isModelTab(className)) {
            clickBack();
            SystemClock.sleep(100);
            clickBack();
            return;
        } else if (PageUtil.isDetailTab(className)) {
            clickBack();
            SystemClock.sleep(100);
            clickBack();
            SystemClock.sleep(100);
            clickBack();
            return;
        }


        AccessibilityNodeInfo listNode = findNodeInfosById("cn.kooki.app.chezhen:id/country_lvcountry");


        if (listNode != null) {
            int recyclerViewCount = listNode.getChildCount();
            int checkIndex = 1; //从第二个开始是品牌
            AccessibilityNodeInfo child;
            String carBrandName;
            AccessibilityNodeInfo brandNode;
            int brandNameIndex; //名称所在位置的索引
            for (; checkIndex < recyclerViewCount; checkIndex++) {
                child = listNode.getChild(checkIndex);
                if (child == null || child.getChildCount() == 0 || child.getChild(0).getText() == null) {
                    Log.d(TAG, "没找到第 " + checkIndex + " 个品牌");
                    continue;
                }
                int childCount = child.getChildCount();
                brandNameIndex = childCount == 3 ? 1 : 0;
//                cn.kooki.app.chezhen:id/title     brand 的 TextView id

                try {
                    brandNode = child.getChild(brandNameIndex).getChild(1);
                    carBrandName = brandNode.getText().toString();
                    if ("众泰".equals(carBrandName)) {//停止
                        mMode = MODE_STOP;
                        saveNumber();
                        clickBack();
                        return;
                    }
                    if (notClickedCarBrand(carBrandName)) {
                        Log.d(TAG, "进入品牌 " + carBrandName);
                        mClickCarBrandSet.add(carBrandName);
                        mCurrentBrandName = carBrandName;
                        mMode = MODE_SELECT_CAR_SERIES;
                        clickNode(child);
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG, e.getMessage());
                }
            }
            if (checkIndex >= recyclerViewCount) {
                ShellUtil.execShellCmd(mSwipeCmd);
//                saveNumber();
            }
        } else {
            Log.d(TAG, "没找到品牌 ListView 节点");
        }

    }

    /**
     * 匹配安装或卸载的界面是否属于白名单里的应用
     * 遍历白名单对比监听的界面，如果命中即返回
     *
     * @param nodeInfo
     * @param whiteList
     * @return
     */
    private String matchTrueAppName(AccessibilityNodeInfo nodeInfo, HashSet<String> whiteList) {
        if (whiteList == null || whiteList.isEmpty() || nodeInfo == null) {
            return null;
        }
        for (Iterator<String> ite = whiteList.iterator(); ite.hasNext(); ) {
            String appName = ite.next();
            Log.d(TAG, "待安装/卸载的应用：" + appName);

            List<AccessibilityNodeInfo> nodes = nodeInfo.findAccessibilityNodeInfosByText(appName);
            if (nodes != null && !nodes.isEmpty()) {
                return appName;
            }
        }
        return null;
    }

    /**
     * 处理安装事件
     *
     * @param nodeInfo
     */
    private void processInstalledEvent(AccessibilityNodeInfo nodeInfo) {

        if (nodeInfo == null) {
            return;
        }

        findAndPerformAction("安装", nodeInfo);
        findAndPerformAction("下一步", nodeInfo);
        findAndPerformAction("完成", nodeInfo);
        String country = getResources().getConfiguration().locale.getCountry();
        if (country != null && !"CN".equalsIgnoreCase(country)) {
            findAndPerformAction("Install", nodeInfo);
            findAndPerformAction("Next", nodeInfo);
            findAndPerformAction("Complete", nodeInfo);
        }
    }

    /**
     * 处理卸载事件
     *
     * @param nodeInfo
     */
    private void processUnInstalledEvent(AccessibilityNodeInfo nodeInfo) {
        if (nodeInfo == null) {
            return;
        }

        findAndPerformAction("确定", nodeInfo);
        findAndPerformAction("卸载", nodeInfo);//华为
    }

    /**
     * 模拟点击指定的按钮或文字
     *
     * @param text
     * @param source
     */
    private void findAndPerformAction(String text, AccessibilityNodeInfo source) {
        if (source == null || text == null) {//text不过滤“”
            Log.e(TAG, "findAndPerformAction 参数为空!");
            return;
        }
        List<AccessibilityNodeInfo> nodes = source.findAccessibilityNodeInfosByText(text);
        if (nodes != null && !nodes.isEmpty()) {
            AccessibilityNodeInfo node;
            for (int i = 0; i < nodes.size(); i++) {
                node = nodes.get(i);
                if (node == null) {
                    continue;
                }
                CharSequence ch = node.getText();
                if (ch == null) {
                    continue;
                }
                //equals不可以，只能用contains，并且如果不匹配的不进行点击 ps：模拟点击安装，界面上有“外部安装程序”不处理就会浪费点击
                if (ch.toString().contains(text) && ch.length() != text.length()) {
                    continue;
                }
                performActionClick(node);
            }
        }
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

    public AccessibilityNodeInfo findNodeInfosById(String resId) {
        AccessibilityNodeInfo currentRootNode = getRootInActiveWindow();
        if (currentRootNode == null) {
            return null;
        }
        List<AccessibilityNodeInfo> list = currentRootNode.findAccessibilityNodeInfosByViewId(resId);
        currentRootNode.recycle();
        if (list != null && !list.isEmpty()) {
            return list.get(0);
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
        AlertUtil.toastShort("找不到可点击的节点");
        return false;
    }

    /**
     * 执行点击操作
     *
     * @param nodeInfo
     */
    private void performActionClick(AccessibilityNodeInfo nodeInfo) {
        if (nodeInfo == null || (!nodeInfo.isEnabled() && !nodeInfo.isClickable())) {
            return;
        }
        if (isButton(nodeInfo) || isTextView(nodeInfo) || isView(nodeInfo)) {
            nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            CharSequence ch = nodeInfo.getText();
            if (ch.toString().contains("完成") && ch.length() == "完成".length()) {
//                String appName = matchTrueAppName(getRootInActiveWindow(), installedWhitelList);
//                removeInstalledWhitelList(appName);
            }
            if (ch.toString().contains("确定") && ch.length() == "确定".length()) {
//                String appName = matchTrueAppName(getRootInActiveWindow(), unInstalledWhitelList);
//                removeUnInstalledWhitelList(appName);
            }
        }
    }


    /**
     * 节点是否为Button控件
     *
     * @param node
     * @return
     */
    private boolean isButton(AccessibilityNodeInfo node) {
        return node != null && "android.widget.Button".equals(node.getClassName());
    }

    /**
     * 节点是否为TextView控件
     *
     * @param node
     * @return
     */
    private boolean isTextView(AccessibilityNodeInfo node) {
        return node != null && "android.widget.Button".equals(node.getClassName());
    }

    /**
     * 节点是否为View控件
     *
     * @param node
     * @return
     */
    private boolean isView(AccessibilityNodeInfo node) {
        return node != null && "android.widget.Button".equals(node.getClassName());
    }


    @Override
    public void onInterrupt() {
        Log.e(TAG, "onInterrupt:  ");

        showToast("自动点击服务 onInterrupted ");
    }


    private void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 监听状态线程处理，抛到主线程进行
     *
     * @param msg
     * @return
     */
    @Override
    public boolean handleMessage(Message msg) {
        Toast.makeText(getApplicationContext(), "你为什么又不动了？", Toast.LENGTH_SHORT).show();
        clickBack();
        return true;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        showToast("自动点击服务被系统关闭了，请开启");
        return super.onUnbind(intent);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        destroyMonitorHandler();
        WxAutoClick.getInstance(this).onDestroy();
    }

}
