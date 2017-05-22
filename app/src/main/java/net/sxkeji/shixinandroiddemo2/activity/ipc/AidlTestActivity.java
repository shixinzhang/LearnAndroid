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

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.TextView;

import net.sxkeji.shixinandroiddemo2.BaseActivity;
import net.sxkeji.shixinandroiddemo2.IMyAidl;
import net.sxkeji.shixinandroiddemo2.R;
import net.sxkeji.shixinandroiddemo2.bean.Person;
import net.sxkeji.shixinandroiddemo2.service.MyAidlService;

import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Description:
 * <br>
 * <p>
 * <br> Created by shixinzhang on 17/5/19.
 * <p>
 * <br> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class AidlTestActivity extends BaseActivity {
    @BindView(R.id.tv_result)
    TextView mTvResult;
    @BindView(R.id.btn_add_person)
    Button mBtnAddPerson;

    private IMyAidl mAidl, mAidl2;

    private ServiceConnection mConnection = new ServiceConnection() {
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl);
        ButterKnife.bind(this);
        bindService();
    }

    private void bindService() {
//        Intent intent = new Intent(this, MyAidlService.class);
        Intent intent = new Intent("net.sxkeji.shixinandroiddemo2.service.MyAidlService").setPackage("net.sxkeji.shixinandroiddemo2");
//
//        String packageName = getPackageName();
//        String name = MyAidlService.class.getName();
//
//        intent.setComponent(new ComponentName("net.sxkeji.shixinandroiddemo2", "net.sxkeji.shixinandroiddemo2.service.MyAidlService"));
//        bindService(intent, mConnection, BIND_AUTO_CREATE);

        Intent intent1 = new Intent(getApplicationContext(), MyAidlService.class);
        bindService(intent1, mConnection, BIND_AUTO_CREATE);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mConnection);
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
