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

package net.sxkeji.shixinandroiddemo2.activity.ipc;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import net.sxkeji.shixinandroiddemo2.BaseActivity;
import net.sxkeji.shixinandroiddemo2.IMyAidl;
import net.sxkeji.shixinandroiddemo2.R;
import net.sxkeji.shixinandroiddemo2.bean.Person;
import net.sxkeji.shixinandroiddemo2.helper.ConfigHelper;
import net.sxkeji.shixinandroiddemo2.provider.IPCPersonProvider;
import net.sxkeji.shixinandroiddemo2.receiver.RepeatReceiver;
import net.sxkeji.shixinandroiddemo2.service.MessengerService;
import net.sxkeji.shixinandroiddemo2.service.MyAidlService;
import net.sxkeji.shixinandroiddemo2.service.TCPServerService;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import top.shixinzhang.sxframework.manager.ThreadPoolManager;
import top.shixinzhang.sxframework.utils.DateUtils;
import top.shixinzhang.sxframework.utils.LogUtils;


/**
 * Description:
 * <br> IPC 测试
 * <p>
 * <br> Created by shixinzhang on 17/5/19.
 * <p>
 * <br> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class IPCTestActivity extends BaseActivity {
    private final String TAG = this.getClass().getSimpleName();

    @BindView(R.id.tv_result)
    TextView mTvResult;
    @BindView(R.id.btn_add_person)
    Button mBtnAddPerson;
    @BindView(R.id.et_msg_content)
    EditText mEtMsgContent;
    @BindView(R.id.btn_send_msg)
    Button mBtnSendMsg;
    @BindView(R.id.tv_messenger_service_state)
    TextView mTvMessengerServiceState;
    @BindView(R.id.tv_cp_result)
    TextView mTvCpResult;
    @BindView(R.id.tv_socket_message)
    TextView mTvSocketMessage;
    @BindView(R.id.et_client_socket)
    EditText mEtClientSocket;
    @BindView(R.id.bt_send_socket)
    Button mBtSendSocket;

    private IMyAidl mAidl;

    private ServiceConnection mAIDLConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //连接后拿到 Binder，转换成 AIDL，在不同进程会返回个代理
            mAidl = IMyAidl.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mAidl = null;
        }
    };

    /**
     * 客户端的 Messenger
     */
    Messenger mClientMessenger = new Messenger(new Handler() {
        @Override
        public void handleMessage(final Message msg) {
            if (msg != null && msg.arg1 == ConfigHelper.MSG_ID_SERVER) {
                if (msg.getData() == null) {
                    return;
                }

                String content = (String) msg.getData().get(ConfigHelper.MSG_CONTENT);
                LogUtils.d(TAG, "Message from server: " + content);
            }
        }
    });

    //服务端的 Messenger
    private Messenger mServerMessenger;

    private ServiceConnection mMessengerConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(final ComponentName name, final IBinder service) {
            mServerMessenger = new Messenger(service);
            mTvMessengerServiceState.setText(mTvMessengerServiceState.getText().toString() + " 服务已连接");
        }

        @Override
        public void onServiceDisconnected(final ComponentName name) {
            mServerMessenger = null;
            mTvMessengerServiceState.setText(mTvMessengerServiceState.getText().toString() + " 服务断开");
        }
    };

    /**
     * 处理 Socket 线程切换
     */
    @SuppressWarnings("HandlerLeak")
    public class SocketHandler extends Handler {
        public static final int CODE_SOCKET_CONNECT = 1;
        public static final int CODE_SOCKET_MSG = 2;

        @Override
        public void handleMessage(final Message msg) {
            switch (msg.what) {
                case CODE_SOCKET_CONNECT:
                    mBtSendSocket.setEnabled(true);
                    break;
                case CODE_SOCKET_MSG:
                    mTvSocketMessage.setText(mTvSocketMessage.getText() + (String) msg.obj);
                    break;
            }
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipc);
        ButterKnife.bind(this);
        bindAIDLService();
        bindMessengerService();

        getContentFromContentProvider();

        bindSocketService();
    }

    private void bindSocketService() {
        Intent intent = new Intent(this, TCPServerService.class);
        startService(intent);

        mSocketHandler = new SocketHandler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    connectSocketServer();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private Socket mClientSocket;
    private PrintWriter mPrintWriter;
    private SocketHandler mSocketHandler;

    /**
     * 通过 Socket 连接服务端
     */
    private void connectSocketServer() throws IOException {
        Socket socket = null;
        while (socket == null) {
            try {
                socket = new Socket("localhost", ConfigHelper.TEST_SOCKET_PORT);
                mClientSocket = socket;
                mPrintWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            } catch (IOException e) {
                SystemClock.sleep(1_000);
            }
        }

        mSocketHandler.sendEmptyMessage(SocketHandler.CODE_SOCKET_CONNECT);

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        while (!isFinishing()) {
            final String msg = in.readLine();
            if (!TextUtils.isEmpty(msg)) {
                mSocketHandler.obtainMessage(SocketHandler.CODE_SOCKET_MSG,
                        "\n" + DateUtils.getCurrentTime() + "\nserver : " + msg)
                        .sendToTarget();
            }
            SystemClock.sleep(1_000);
        }

        System.out.println("Client quit....");
        mPrintWriter.close();
        in.close();
        socket.close();
    }

    @OnClick(R.id.bt_send_socket)
    public void sendMsgToSocketServer() {
        final String msg = mEtClientSocket.getText().toString();
        if (!TextUtils.isEmpty(msg) && mPrintWriter != null) {
            ThreadPoolManager.getInstance().addTask(new Runnable() {
                @Override
                public void run() {
                    mPrintWriter.println(msg);
                }
            });
            mEtClientSocket.setText("");
            mTvSocketMessage.setText(mTvSocketMessage.getText() + "\n" + DateUtils.getCurrentTime() + "\nclient : " + msg);
        }
    }

    private int id = 2;

    private void getContentFromContentProvider() {
        Uri uri = IPCPersonProvider.PERSON_CONTENT_URI;
        ContentValues contentValues = new ContentValues();
        contentValues.put("_id", id++);
        contentValues.put("name", "rourou" + DateUtils.getCurrentTime());
        contentValues.put("description", "beautiful girl");
        ContentResolver contentResolver = getContentResolver();
        contentResolver.insert(uri, contentValues);

        Cursor cursor = contentResolver.query(uri, new String[]{"name", "description"}, null, null, null, null);
        if (cursor == null) {
            return;
        }
        StringBuilder cursorResult = new StringBuilder("DB 查询结果：");
        while (cursor.moveToNext()) {
            String result = cursor.getString(0) + ", " + cursor.getString(1);
            LogUtils.d(TAG, "DB 查询结果：" + result);
            cursorResult.append("\n").append(result);
        }
        mTvCpResult.setText(cursorResult.toString());
        cursor.close();

    }


    @OnClick(R.id.btn_add_person_to_db)
    public void addPersonToDB() {
        getContentFromContentProvider();
    }

    private void bindMessengerService() {
        Intent intent = new Intent(this, MessengerService.class);
        bindService(intent, mMessengerConnection, BIND_AUTO_CREATE);
    }


    @OnClick(R.id.btn_send_msg)
    public void sendMsg() {
        String msgContent = mEtMsgContent.getText().toString();
        msgContent = TextUtils.isEmpty(msgContent) ? "默认消息" : msgContent;

        Message message = Message.obtain();
        message.arg1 = ConfigHelper.MSG_ID_CLIENT;
        Bundle bundle = new Bundle();
        bundle.putString(ConfigHelper.MSG_CONTENT, msgContent);
        message.setData(bundle);
        message.replyTo = mClientMessenger;     //指定回信人是客户端定义的

        try {
            mServerMessenger.send(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.btn_add_person)
    public void addPerson() {
        Random random = new Random();
        Person person = new Person("shixin" + random.nextInt(10));

        try {
            mAidl.addPerson(person);
            List<Person> personList = mAidl.getPersonList();
            mTvResult.setText(personList.toString());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    private void bindAIDLService() {
//        Intent intent = new Intent(this, MyAidlService.class);
//        Intent intent = new Intent("net.sxkeji.shixinandroiddemo2.service.MyAidlService").setPackage("net.sxkeji.shixinandroiddemo2");
//
//        String packageName = getPackageName();
//        String name = MyAidlService.class.getName();
//
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("top.shixinzhang.myapplication", "top.shixinzhang.myapplication.service.MyAidlService"));
        bindService(intent, mAIDLConnection, BIND_AUTO_CREATE);

        Intent intent1 = new Intent(getApplicationContext(), MyAidlService.class);
        bindService(intent1, mAIDLConnection, BIND_AUTO_CREATE);
    }

    private void starServiceWithBroadcast() {
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, new Intent(this, RepeatReceiver.class), 0);
        Bundle bundle = new Bundle();
        bundle.putParcelable("receiver", pendingIntent);
        Intent intent = new Intent(getApplicationContext(), MyAidlService.class);
        intent.putExtras(bundle);
        startService(intent);
    }

    private void startForegroundService() {
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mAIDLConnection);
        unbindService(mMessengerConnection);
//        stopService(new Intent(this, TCPServerService.class));
        try {
            if (mClientSocket != null) {
                mClientSocket.shutdownInput();
                mClientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initViews() {

    }

    @Override
    public void loadData() {

    }

    @Override
    public void addListeners() {

    }
}
