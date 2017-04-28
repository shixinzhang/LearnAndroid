package net.sxkeji.shixinandroiddemo2.activity;

import android.app.TimePickerDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import net.sxkeji.shixinandroiddemo2.BaseActivity;
import net.sxkeji.shixinandroiddemo2.R;
import net.sxkeji.shixinandroiddemo2.helper.ConfigHelper;
import net.sxkeji.shixinandroiddemo2.service.SxService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import top.shixinzhang.utils.DateFormatUtil;
import top.shixinzhang.utils.SpUtil;

/**
 * <br/> Description:定时服务
 * <p>
 * <br/> Created by shixinzhang on 16/4/19.
 * <p>
 * <br/> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class ServiceTestActivity extends BaseActivity {

    @BindView(R.id.btn_start_service)
    Button mBtnStartService;
    @BindView(R.id.btn_stop_service)
    Button mBtnStopService;
    @BindView(R.id.btn_bind_service)
    Button mBtnBindService;
    @BindView(R.id.btn_unbind_service)
    Button mBtnUnbindService;
    @BindView(R.id.btn_select_time)
    Button mBtnSelectTime;

    private SxService.SxBinder mBinder;

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinder = (SxService.SxBinder) service;
            if (mBinder != null) {
                toast(R.string.local_service_connected);
                mBinder.doSomething();
                mBinder.startRepeatTaskAtTime(repeatStartTimeMillis);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            showErrorLog(name + " onServiceDisconnected");
        }
    };
    private boolean mServiceBound;  //服务是否连接
    private long repeatStartTimeMillis;
    private long repeatStopTimeMillis;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_test);
        ButterKnife.bind(this);

        addListeners();
    }

    @Override
    public void initViews() {

    }

    @Override
    public void loadData() {

    }

    @OnClick(R.id.btn_select_stop_time)
    public void selectStop() {
        selectStopTime();
    }

    @Override
    public void addListeners() {
        mBtnStartService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ServiceTestActivity.this, SxService.class);
                startService(intent);

            }
        });

        mBtnStopService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ServiceTestActivity.this, SxService.class);
                stopService(intent);
            }
        });

        mBtnSelectTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectStartTime();
            }
        });

        mBtnBindService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (repeatStartTimeMillis < System.currentTimeMillis()) {    //选择时间
                    toast("请选择任务开始时间");
                    selectStartTime();
                    return;
                }
                Intent intent = new Intent(ServiceTestActivity.this, SxService.class);
                //BIND_AUTO_CREATE 会使得 Service 中的onCreate()方法得到执行，但onStartCommand()方法不会执行。
                bindService(intent, mServiceConnection, BIND_AUTO_CREATE);  //自动创建服务，只要绑定存在就存在
                mServiceBound = true;
            }

        });

        mBtnUnbindService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unbindService();
            }
        });
    }

    /**
     * 选择任务开始的时间
     */
    private void selectStartTime() {
        new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                repeatStartTimeMillis = DateFormatUtil.getTimeInMillisecond(hourOfDay, minute);
                SpUtil.saveData(ServiceTestActivity.this, ConfigHelper.SP_START_TIME, repeatStartTimeMillis);
            }
        }, 12, 0, true).show();
    }

    /**
     * 选择任务结束的时间
     */
    private void selectStopTime() {
        new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                repeatStopTimeMillis = DateFormatUtil.getTimeInMillisecond(hourOfDay, minute);
                SpUtil.saveData(ServiceTestActivity.this, ConfigHelper.SP_STOP_TIME, repeatStopTimeMillis);
            }
        }, 12, 0, true).show();
    }

    private void unbindService() {
        if (mServiceBound) {
            unbindService(mServiceConnection);
            mServiceBound = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService();
    }
}
