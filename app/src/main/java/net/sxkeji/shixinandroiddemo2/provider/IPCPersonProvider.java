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

package net.sxkeji.shixinandroiddemo2.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

import net.sxkeji.shixinandroiddemo2.activity.ipc.DbOpenHelper;

import top.shixinzhang.sxframework.utils.LogUtils;

/**
 * Description:
 * <br> 测试 ContentProvider 跨进程
 * <p>
 * <br> Created by shixinzhang on 17/6/1.
 * <p>
 * <br> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class IPCPersonProvider extends ContentProvider {
    private final String TAG = this.getClass().getSimpleName();
    private static final UriMatcher mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    public static final String AUTHORITY = "net.sxkeji.shixinandroiddemo2.provider.IPCPersonProvider";  //授权
    public static final Uri PERSON_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/person");

    private SQLiteDatabase mDatabase;
    private Context mContext;
    private String mTable;

    private static final int TABLE_CODE_PERSON = 2;

    static {
        //关联不同的 URI 和 code，便于后续 getType
        mUriMatcher.addURI(AUTHORITY, "person", TABLE_CODE_PERSON);
    }


    @Override
    public boolean onCreate() {
        initProvider();
        return false;
    }

    /**
     * 初始化时清楚旧数据，插入一条数据
     */
    private void initProvider() {
        mTable = DbOpenHelper.TABLE_NAME;
        mContext = getContext();
        mDatabase = new DbOpenHelper(mContext).getWritableDatabase();

        new Thread(new Runnable() {
            @Override
            public void run() {
                mDatabase.execSQL("delete from " + mTable);
                mDatabase.execSQL("insert into " + mTable + " values(1,'shixinzhang','handsome boy')");
            }
        }).start();
    }

    @Nullable
    @Override
    public Cursor query(final Uri uri, final String[] projection, final String selection, final String[] selectionArgs, final String sortOrder) {
        String tableName = getTableName(uri);
        showLog(tableName + " 查询数据" );
        return mDatabase.query(tableName, projection, selection, selectionArgs, null, sortOrder, null);
    }

    @Nullable
    @Override
    public Uri insert(final Uri uri, final ContentValues values) {
        String tableName = getTableName(uri);
        showLog(tableName + " 插入数据");
        mDatabase.insert(tableName, null, values);
        mContext.getContentResolver().notifyChange(uri, null);
        return null;
    }

    @Override
    public int delete(final Uri uri, final String selection, final String[] selectionArgs) {
        String tableName = getTableName(uri);
        showLog(tableName + " 删除数据");
        int deleteCount = mDatabase.delete(tableName, selection, selectionArgs);
        if (deleteCount > 0) {
            mContext.getContentResolver().notifyChange(uri, null);
        }
        return deleteCount;
    }

    @Override
    public int update(final Uri uri, final ContentValues values, final String selection, final String[] selectionArgs) {
        String tableName = getTableName(uri);
        showLog(tableName + " 更新数据");
        int updateCount = mDatabase.update(tableName, values, selection, selectionArgs);
        if (updateCount > 0) {
            mContext.getContentResolver().notifyChange(uri, null);
        }
        return updateCount;
    }

    /**
     * CRUD 的参数是 Uri，根据 Uri 获取对应的表名
     *
     * @param uri
     * @return
     */
    private String getTableName(final Uri uri) {
        String tableName = "";
        int match = mUriMatcher.match(uri);
        switch (match){
            case TABLE_CODE_PERSON:
                tableName = DbOpenHelper.TABLE_NAME;
        }
        showLog("UriMatcher " + uri.toString() + ", result: " + match);
        return tableName;
    }

    @Nullable
    @Override
    public String getType(final Uri uri) {
        return null;
    }

    private void showLog(final String s) {
        LogUtils.d(TAG, s + "***** @ " + Thread.currentThread().getName());
    }
}
