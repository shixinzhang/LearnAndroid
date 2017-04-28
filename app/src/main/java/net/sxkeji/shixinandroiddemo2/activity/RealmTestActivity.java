package net.sxkeji.shixinandroiddemo2.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import net.sxkeji.shixinandroiddemo2.BaseActivity;
import net.sxkeji.shixinandroiddemo2.bean.Dog;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * <br/> Description: Realm 的练习
 * 官方文档：
 *      https://realm.io/docs/java/latest/
 *      https://github.com/realm/realm-java
 *
    数据库 Realm，用来替代 SQLite 的一种解决方案，它有一套自己的数据库存储引擎
    比 SQLite 更轻量级，拥有更快的速度
    并且具有很多现代数据库的特性，比如支持 JSON，流式 API，数据变更通知，自动数据同步,简单身份验证，访问控制，事件处理
        **最重要的是跨平台，目前已有 Java，Objective C，Swift，React-Native，Xamarin 这五种实现。
 *
 * <p>
 * <p>
 * <br/> Created by shixinzhang on 16/12/15.
 * <p>
 * <br/> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class RealmTestActivity extends BaseActivity {
    private final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //新增一个对象
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        final Dog dog = realm.createObject(Dog.class);
        dog.setName("shirley");
        dog.setId("123456");
        realm.commitTransaction();

        //复制一个对象
        realm.beginTransaction();
        realm.copyToRealm(dog);
        realm.commitTransaction();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(dog);
            }
        });

        //删除一个对象
        final RealmResults<Dog> dogs = realm.where(Dog.class).findAll();
        realm.beginTransaction();
        dogs.deleteFirstFromRealm();
        realm.commitTransaction();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Dog dog1 = dogs.get(0);
                dog1.deleteFromRealm();

                dogs.deleteFirstFromRealm();
                dogs.deleteLastFromRealm();
                dogs.deleteFromRealm(0);
                dogs.deleteAllFromRealm();
            }
        });


        //修改一个对象
        final Dog dog1 = realm.where(Dog.class).equalTo("id", "123456").findFirst();
        realm.beginTransaction();
        dog1.setName("Jack");
        realm.commitTransaction();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                dog1.setName("JackFromTransaction");
            }
        });
    }

    /**
     * 查询全部
     *
     * @return
     */
    private List<Dog> queryAllDog() {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Dog> dogs = realm.where(Dog.class).findAll();
                //cannot return value in inner class
            }
        });

        realm.beginTransaction();
        RealmResults<Dog> dogs = realm.where(Dog.class).findAll();
        //对查询结果排序，默认升序
        dogs.sort("id");
        //降序
        dogs.sort("id", Sort.DESCENDING);
        return realm.copyFromRealm(dogs);
    }

    /**
     * 条件查询
     */
    private Dog queryDogById(String id) {
        final String QUERY_FIELD_ID = "id";
        Realm realm = Realm.getDefaultInstance();
        //各种查询条件，可以叠加
        realm.where(Dog.class)
                .between(QUERY_FIELD_ID, 1, 100)
                .greaterThan(QUERY_FIELD_ID, 22)
                .lessThan(QUERY_FIELD_ID, 55)
                .greaterThanOrEqualTo(QUERY_FIELD_ID, 66)
                .notEqualTo(QUERY_FIELD_ID, 33)
                .contains(QUERY_FIELD_ID, id)
                .beginsWith(QUERY_FIELD_ID, "1")
                .endsWith(QUERY_FIELD_ID, "2")
                .isNull(QUERY_FIELD_ID)
                .isNotNull(QUERY_FIELD_ID)
                .isEmpty(QUERY_FIELD_ID)
                .isNotEmpty(QUERY_FIELD_ID);


        return realm.where(Dog.class).equalTo(QUERY_FIELD_ID, id).findFirst();
    }

    /**
     * 得到 RealmResult 后可以进行筛选，比如求平均值、最大值、总数等
     *
     * @return
     */
    private double queryDogAverageAge() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Dog> dogRealmResults = realm.where(Dog.class).findAll();
        Number sum = dogRealmResults.sum("age");
        dogRealmResults.max("age");

        return dogRealmResults.average("age");
    }

    RealmAsyncTask mAddRealmAsyncTask;
    /**
     * 复杂的操作可以异步进行，不过记得在 onDestory 时销毁异步任务
     */
    private void addAlotDog(final List<Dog> dogs) {
        Realm realm = Realm.getDefaultInstance();
        mAddRealmAsyncTask = realm.executeTransactionAsync(
                new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.copyToRealm(dogs);

                        for (Dog dog : dogs) {      //worse than above
                            realm.copyToRealm(dog);
                        }
                    }
                }, new Realm.Transaction.OnSuccess() {  //成功回调
                    @Override
                    public void onSuccess() {
                        Log.d(TAG, "添加成功");
                    }
                }, new Realm.Transaction.OnError() {
                    @Override
                    public void onError(Throwable error) {
                        Log.d(TAG, "添加失败");
                    }
                }
        );
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

    public void jump2Market() {
        Uri uri = Uri.parse("market://details?id=" + getPackageName());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mAddRealmAsyncTask != null && !mAddRealmAsyncTask.isCancelled()){
            mAddRealmAsyncTask.cancel();
        }
    }
}
