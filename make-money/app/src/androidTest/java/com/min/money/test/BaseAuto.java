package com.min.money.test;

import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.UiDevice;

import com.blankj.utilcode.util.LogUtils;
import com.min.money.test.util.Utils;

/**
 * Created by minych on 18-9-2.
 */

public abstract class BaseAuto {

    protected int mMinCycleValue = 10;
    protected int mMaxCycleValue = 20;

    protected String mPackageName;
    protected UiDevice mDevice;

    public BaseAuto(String packageName) {
        this.mPackageName = packageName;
    }

    public void startApp() throws Exception {
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        Utils.wakeUpDevice(mDevice);
        mDevice.pressHome();
        Utils.sleepRandomSecond(2);
        Utils.openAppSafe(mDevice, mPackageName);
    }

    /**
     * 启动操作
     */
    public void run() {
        try {
            LogUtils.i(String.format("app packageName=%s operate start, time=%s", mPackageName, Utils.nowTimeStr()));
            startApp();
            operate();
            LogUtils.i(String.format("app packageName=%s operate finish , time=%s", mPackageName, Utils.nowTimeStr()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 实现操作
     */
    public abstract void operate() throws Exception;

}
