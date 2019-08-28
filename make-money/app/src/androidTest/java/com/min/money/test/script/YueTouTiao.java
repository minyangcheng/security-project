package com.min.money.test.script;

import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject2;

import com.min.money.test.BaseAuto;
import com.min.money.test.util.Utils;

import java.util.List;

public class YueTouTiao extends BaseAuto {

    public YueTouTiao() {
        super("com.expflow.reading");
    }

    @Override
    public void operate() throws Exception {
        for (int i = 0; i < 2; i++) {
            Utils.sleepRandomSecond(2, 4);
            newsPage();
            Utils.click(mDevice, By.text("我的"));
            Utils.sleepRandomSecond(2, 4);
            minePage();
            Utils.click(mDevice, By.text("任务"));
            Utils.sleepRandomSecond(2, 4);
            taskPage();
            Utils.click(mDevice, By.text("视频"));
            Utils.sleepRandomSecond(2, 4);
//            videoPage();

            Utils.click(mDevice, By.text("头条"));
        }
    }

    public void newsPage() throws Exception {
        int step = Utils.getRandom(mMinCycleValue, mMaxCycleValue);
        for (int i = 0; i < step; i++) {
            Utils.click(mDevice, By.res("com.expflow.reading:id/tv_read_time_rule_know"));

            List<UiObject2> uiObjList = mDevice.findObjects(By.res("com.expflow.reading:id/txt_time"));
            for (UiObject2 uiObj : uiObjList) {
                try {
                    boolean flag = Utils.clickRandom(uiObj, 0.85f);
                    if (flag) {
                        Utils.sleepRandomSecond(4, 6);
                        newsDetailPage();
                        Utils.click(mDevice,By.res("com.expflow.reading:id/iv_close"));
                    }
                } catch (Exception e) {
                }
            }
            Utils.slideVerticalUp(mDevice);
            Utils.sleepRandomSecond(1);
        }
    }

    public void minePage() throws Exception {
        Utils.click(mDevice, By.res("com.jifen.qukan:id/pl"));

        String[] textArr = {"今日金币", "提现中心", "提现记录", "我的消息"};
        Utils.openPageRandom(mDevice, textArr);
    }

    public void newsDetailPage() throws Exception {
        Utils.click(mDevice, By.res("com.jifen.qukan:id/pl"));

        Utils.readPage(mDevice);
    }

    public void taskPage() throws Exception {
        Utils.click(mDevice, By.res("com.jifen.qukan:id/pl"));

        Utils.slideVertical(mDevice, 0.4f, 0.2f);
        Utils.sleepRandomSecond(2, 4);
        Utils.slideVertical(mDevice, 0.4f, 0.2f);
    }

    public void videoPage() throws Exception {
        int step = Utils.getRandom(mMinCycleValue, mMaxCycleValue);
        for (int i = 0; i < step; i++) {
            Utils.click(mDevice, By.res("com.jifen.qukan:id/pl"));

            List<UiObject2> uiObjList = mDevice.findObjects(By.res("com.expflow.reading:id/image_first"));
            for (UiObject2 uiObj : uiObjList) {
                try {
                    if (Utils.clickRandom(uiObj, 0.85f)) {
                        Utils.sleepRandomSecond(20, 30);
                        mDevice.pressBack();
                    }
                } catch (Exception e) {
                }
            }
            Utils.slideVerticalUp(mDevice);
            Utils.sleepRandomSecond(1);
        }
    }

}