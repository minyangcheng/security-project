package com.min.money.test;

import android.text.TextUtils;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPStaticUtils;
import com.min.money.test.util.Helper;
import com.nostra13.universalimageloader.utils.L;

import java.util.HashSet;
import java.util.Set;

public abstract class BaseAuto {

    protected String mTag;

    protected int mMinCycleValue = 30;
    protected int mMaxCycleValue = 50;
    protected int mOperateTimes = 1;

    protected String mPackageName;

    protected int mNowCoin;
    protected int mWorkCoin;

    protected Set<Integer> mRecordSet = new HashSet<>();

    public BaseAuto(String packageName) {
        this.mPackageName = packageName;
        this.mTag = "app-" + mPackageName;
    }

    public static void start(BaseAuto autoScript) {
        autoScript.run();
    }

    protected void run() {
        checkRightTime();

        LogUtils.i(String.format("%s operate start, time:%s", mTag, Helper.nowTimeStr()));
        long startTime = System.currentTimeMillis();
        doWork();
        long workingTime = System.currentTimeMillis() - startTime;
        String fitWorkingTimeStr = ConvertUtils.millis2FitTimeSpan(workingTime, 4);
        LogUtils.i(String.format("%s operate finish, time:%s workTime:%s nowCoin:%s workCoin:%s",
                mTag, Helper.nowTimeStr(), fitWorkingTimeStr, mNowCoin, mWorkCoin));


    }

    private void doWork() {
        try {
            Helper.wakeUpDevice();
            Helper.openAppSafe(mPackageName);
            handleDialog();
            operate();
            mNowCoin = recordCoin(); // 获取当前coin值
            mWorkCoin = calculateWorkCoin(); //计算此次工作生产出的coin
            SPStaticUtils.put(mPackageName + "-coin", mNowCoin); //记录当前coin到文件中保存
            backAppToHome();
        } catch (Exception e) {
            LogUtils.e(e);
        }
    }

    private void backAppToHome() {
        Helper.pressBack();
        Helper.pressBack();
        Helper.pressBack();
        Helper.closeApp(mPackageName);
    }

    protected abstract void operate();

    protected void handleDialog() {
    }

    protected int recordCoin() {
        return -1;
    }

    private int calculateWorkCoin() {
        int lastCoin = SPStaticUtils.getInt(mPackageName + "-coin", 0);
        int workCoin = 0;
        if (lastCoin > 0 && mNowCoin > 0 && mNowCoin > lastCoin) {
            workCoin = mNowCoin - lastCoin;
        }
        return workCoin;
    }

    protected void checkRightTime() {
        boolean flag = !Helper.checkRightTime();
        if (flag) {
            L.i("当前属于非法时间段暂停运行,time:" + Helper.nowTimeStr() + " app:" + mPackageName);
        }
        while (flag) {
            Helper.sleep(60000 * 30);
            flag = !Helper.checkRightTime();
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
