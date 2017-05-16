package net.sxkeji.shixinandroiddemo2.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import net.sxkeji.shixinandroiddemo2.BaseActivity;
import net.sxkeji.shixinandroiddemo2.MainActivity;
import net.sxkeji.shixinandroiddemo2.test.ipc.GroupBean;

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
public class IPCTestActivity extends BaseActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GroupBean groupBean = new GroupBean();
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("key", groupBean);
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
