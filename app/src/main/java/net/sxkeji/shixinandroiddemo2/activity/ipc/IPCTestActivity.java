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
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
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
import net.sxkeji.shixinandroiddemo2.receiver.RepeatReceiver;
import net.sxkeji.shixinandroiddemo2.service.MessengerService;
import net.sxkeji.shixinandroiddemo2.service.MyAidlService;

import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import top.shixinzhang.sxframework.config.Config;
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
            if (msg != null && msg.arg1 == ConfigHelper.MSG_ID_SERVER){
                if (msg.getData() == null){
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
        }

        @Override
        public void onServiceDisconnected(final ComponentName name) {
            mServerMessenger = null;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl);
        ButterKnife.bind(this);
        bindAIDLService();
        bindMessengerService();
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
