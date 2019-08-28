package com.min.money.test.script;

import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject2;
import android.text.TextUtils;

import com.blankj.utilcode.util.LogUtils;
import com.min.money.test.BaseAuto;
import com.min.money.test.util.Utils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class QuTouTiao extends BaseAuto {

    Set<Integer> mHasClickSet = new HashSet<>();

    public QuTouTiao() {
        super("com.jifen.qukan");
    }

    @Override
    public void operate() {
        for (int i = 0; i < 2; i++) {
            LogUtils.i("进入头条页面");
            Utils.click(mDevice, By.text("头条"));
            Utils.sleepRandomSecond(2, 4);
            newsPage();

            LogUtils.i("进入我的页面");
            Utils.click(mDevice, By.text("我的"));
            Utils.sleepRandomSecond(2, 4);
            minePage();

            LogUtils.i("进入任务页面");
            Utils.click(mDevice, By.text("任务"));
            Utils.sleepRandomSecond(2, 4);
            taskPage();

            LogUtils.i("进入视频页面");
            Utils.click(mDevice, By.text("视频"));
            Utils.sleepRandomSecond(2, 4);
            videoPage();
        }
    }

    public void newsPage() {
        int step = Utils.getRandom(mMinCycleValue, mMaxCycleValue);
        for (int i = 0; i < step; i++) {
            List<UiObject2> uiObjList = mDevice.findObjects(By.res("com.jifen.qukan:id/mh"));
            for (UiObject2 uiObj : uiObjList) {
                try {
                    Utils.click(mDevice, By.res("com.jifen.qukan:id/p4"));
                    Utils.click(mDevice, By.res("com.jifen.qukan:id/pl"));
                    Utils.click(mDevice, By.res("com.jifen.qukan:id/og"));
                    Utils.click(mDevice, By.text("忽略"));

                    UiObject2 tempUiObj2 = uiObj.findObject(By.res("com.jifen.qukan:id/aag"));
                    if (tempUiObj2 == null) {
                        continue;
                    }
                    String tempStr = Utils.getText(uiObj, By.res("com.jifen.qukan:id/xx"));
                    if (TextUtils.isEmpty(tempStr)) {
                        continue;
                    }
                    if (mHasClickSet.contains(tempStr.hashCode())) {
                        continue;
                    }
                    mHasClickSet.add(tempStr.hashCode());
                    if (Utils.clickRandom(tempUiObj2, 1)) {
                        LogUtils.d("点击进入标题页面：" + tempStr);
                        Utils.sleepRandomSecond(2, 4);
                        newsDetailPage();
                        mDevice.pressBack();
                        LogUtils.d("点击退出标题页面");
                    }
                } catch (Exception e) {
                }
            }
            Utils.clickRandom(mDevice, By.res("com.jifen.qukan:id/sk").text("领取"));
            Utils.slideVerticalUp(mDevice);
            Utils.sleepRandomSecond(1);
        }
    }

    public void minePage() {
        Utils.click(mDevice, By.res("com.jifen.qukan:id/pl"));
        Utils.click(mDevice, By.res("com.jifen.qukan:id/nu"));

        String[] textArr = {"我的金币", "提现兑换", "提现记录", "历史记录"};
        Utils.openPageRandom(mDevice, textArr);
    }

    public void newsDetailPage() {
        Utils.click(mDevice, By.res("com.jifen.qukan:id/pl"));

        Utils.readPage(mDevice);
    }

    public void taskPage() {
        Utils.click(mDevice, By.res("com.jifen.qukan:id/pl"));

        Utils.slideVertical(mDevice, 0.4f, 0.2f);
        Utils.sleepRandomSecond(2, 4);
        Utils.slideVertical(mDevice, 0.4f, 0.2f);
    }

    public void videoPage() {
        int step = Utils.getRandom(mMinCycleValue, mMaxCycleValue);
        for (int i = 0; i < step; i++) {
            Utils.click(mDevice, By.res("com.jifen.qukan:id/pl"));

            List<UiObject2> uiObjList = mDevice.findObjects(By.res("com.jifen.qukan:id/mh"));
            for (UiObject2 uiObj : uiObjList) {
                try {
                    UiObject2 tempUiObj = uiObj.findObject(By.res("com.jifen.qukan:id/a3b"));
                    if (tempUiObj == null) {
                        continue;
                    }
                    String tempStr = Utils.getText(uiObj, By.res("com.jifen.qukan:id/a1a"));
                    if (TextUtils.isEmpty(tempStr)) {
                        continue;
                    }
                    if (mHasClickSet.contains(tempStr.hashCode())) {
                        continue;
                    }
                    mHasClickSet.add(tempStr.hashCode());
                    if (Utils.clickRandom(tempUiObj, 1)) {
                        LogUtils.d("点击观看视频：" + tempStr);
                        Utils.sleepRandomSecond(20, 30);
                    }
                } catch (Exception e) {
                }
            }
            Utils.slideVerticalUp(mDevice);
            Utils.sleepRandomSecond(1);
        }
    }

}