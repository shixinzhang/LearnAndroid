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

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import net.sxkeji.shixinandroiddemo2.IMyAidl;
import net.sxkeji.shixinandroiddemo2.bean.Person;

import java.util.List;

/**
 * Description:
 * <br>
 * <p>
 * <br> Created by shixinzhang on 17/5/30.
 * <p>
 * <br> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public interface IMyAidlDiy extends IInterface {
    public static abstract class Stub extends Binder implements IMyAidlDiy {

        private static final String DESCRIPTOR = "net.sxkeji.shixinandroiddemo2.activity.ipc.IMyAidlDiy";

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        @Override
        public IBinder asBinder() {
            return this;
        }

        public static IMyAidlDiy asInterface(IBinder binder) {
            if (binder == null) {
                return null;
            }

            IInterface localInterface = binder.queryLocalInterface(DESCRIPTOR);
            if (localInterface != null && localInterface instanceof IMyAidlDiy) {
                return (IMyAidlDiy) localInterface;
            } else {
                return new Stub.Proxy(binder);
            }
        }

        @Override
        protected boolean onTransact(final int code, final Parcel data, final Parcel reply, final int flags) throws RemoteException {
            switch (code) {
                case TRANSACTION_addPerson:
                    data.enforceInterface(DESCRIPTOR);
                    Person _arg0;
                    if (data.readInt() != 0) {
                        _arg0 = Person.CREATOR.createFromParcel(data);  //反序列化参数
                    } else {
                        _arg0 = null;
                    }
                    this.addPerson(_arg0);
                    reply.writeNoException();
                    return true;
                case TRANSACTION_getPersonList:
                    data.enforceInterface(DESCRIPTOR);
                    List<Person> personList = this.getPersonList();
                    reply.writeNoException();
                    reply.writeTypedList(personList);
                    break;
            }
            return super.onTransact(code, data, reply, flags);
        }

        private static class Proxy implements IMyAidlDiy {
            private IBinder mRemote;

            public Proxy(final IBinder obj) {
                mRemote = obj;
            }

            public java.lang.String getInterfaceDescriptor() {  //伪装的和真的 Binder 名一样
                return DESCRIPTOR;
            }

            @Override
            public void addPerson(final Person person) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();

                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    if (person != null) {
                        _data.writeInt(1);
                        person.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    mRemote.transact(TRANSACTION_addPerson, _data, _reply, 0);  //这里调用实际的实现
                    _reply.readException();

                } finally {
                    _data.recycle();
                    _reply.recycle();
                }
            }

            @Override
            public List<Person> getPersonList() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                List<Person> _result;
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    mRemote.transact(TRANSACTION_getPersonList, _data, _reply, 0);
                    _reply.readException();
                    _result = _reply.createTypedArrayList(Person.CREATOR);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
                return _result;
            }

            @Override
            public IBinder asBinder() {
                return mRemote;
            }
        }
    }

    static final int TRANSACTION_addPerson = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
    static final int TRANSACTION_getPersonList = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);

    public void addPerson(Person person) throws RemoteException;

    public List<Person> getPersonList() throws RemoteException;
}
