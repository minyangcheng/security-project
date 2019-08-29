package com.min.money.test;

import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.UiDevice;
import android.text.TextUtils;

import com.blankj.utilcode.util.LogUtils;
import com.min.money.test.util.Helper;

import java.util.HashSet;
import java.util.Set;

public abstract class BaseAuto {

    protected String mTag;

    protected int mMinCycleValue = 10;
    protected int mMaxCycleValue = 20;
    protected int mOperateTimes = 1;

    protected String mPackageName;
    protected UiDevice mDevice;

    protected Set<Integer> mRecordSet = new HashSet<>();

    public BaseAuto(String packageName) {
        this.mPackageName = packageName;
        this.mTag = "app " + mPackageName;
    }

    public static void start(BaseAuto autoScript) {
        autoScript.run();
    }

    protected void open() throws Exception {
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        Helper.wakeUpDevice(mDevice);
        Helper.openAppSafe(mDevice, mPackageName);
    }

    protected void run() {
        try {
            checkRightTime();
            LogUtils.i(String.format("%s operate start, time=%s", mTag, Helper.nowTimeStr()));
            open();
            removeRedundancyDialog();
            operate();
            LogUtils.i(String.format("%s operate finish , time=%s", mTag, Helper.nowTimeStr()));
            mDevice.pressBack();
            Helper.sleep(500);
            mDevice.pressBack();
        } catch (Exception e) {
            LogUtils.e(e);
        }
    }

    protected abstract void operate() throws Exception;

    protected void removeRedundancyDialog() {
    }

    protected void checkRightTime() {
        while (!Helper.checkRightTime()) {
            Helper.sleep(60000);
        }
    }

    protected boolean checkItemHasOperate(String str) {
        if (TextUtils.isEmpty(str)) {
            return true;
        }
        if (mRecordSet.contains(str.hashCode())) {
            return true;
        }
        mRecordSet.add(str.hashCode());
        return false;
    }

}
