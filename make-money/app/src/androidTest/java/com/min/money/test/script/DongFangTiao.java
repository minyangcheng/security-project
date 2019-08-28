package com.min.money.test.script;

import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject2;

import com.min.money.test.BaseAuto;
import com.min.money.test.util.Utils;

import java.util.List;

public class DongFangTiao extends BaseAuto {

    public DongFangTiao() {
        super("com.songheng.eastnews");
    }

    @Override
    public void operate() throws Exception {
        for (int i = 0; i < 2; i++) {
            Utils.sleepRandomSecond(2, 4);
            newsPage();

            Utils.click(mDevice, By.text("任务"));
            Utils.sleepRandomSecond(2, 4);
            taskPage();

//            Utils.click(mDevice, By.text("小视频"));
//            Utils.sleepRandomSecond(2, 4);
//            smallVideo();

            Utils.click(mDevice, 0.5f, 0.9f);
            Utils.sleepRandomSecond(2, 4);
            Utils.slideVertical(mDevice, 0.8f, 0.5f);
            Utils.sleepRandomSecond(1, 3);
            mDevice.pressBack();

            Utils.click(mDevice, By.text("视频"));
            Utils.sleepRandomSecond(2, 4);
            videoPage();

            Utils.click(mDevice, By.text("新闻"));
            Utils.sleepRandomSecond(2, 4);
        }
    }

    public void newsPage() throws Exception {
        int step = Utils.getRandom(mMinCycleValue, mMaxCycleValue);
        for (int i = 0; i < step; i++) {
            Utils.click(mDevice, By.res("com.songheng.eastnews:id/vf"));

            List<UiObject2> uiObjList = mDevice.findObjects(By.res("com.songheng.eastnews:id/a1z"));
            for (UiObject2 uiObj : uiObjList) {
                try {
                    boolean flag = Utils.clickRandom(uiObj, 0.85f);
                    if (flag) {
                        Utils.sleepRandomSecond(4, 6);
                        newsDetailPage();

                        Utils.click(mDevice, By.res("com.songheng.eastnews:id/vf"));
                        mDevice.pressBack();
                    }
                } catch (Exception e) {
                }
            }
            Utils.slideVerticalUp(mDevice);
            Utils.sleepRandomSecond(1);
        }
    }

    public void taskPage() throws Exception {
        Utils.click(mDevice, By.res("com.songheng.eastnews:id/vf"));
        Utils.click(mDevice, By.res("com.songheng.eastnews:id/ug"));

        Utils.slideVertical(mDevice, 0.5f, 0.3f);
        Utils.slideVertical(mDevice, 0.5f, 0.3f);
        Utils.slideVertical(mDevice, 0.3f, 0.6f);
    }

    public void newsDetailPage() throws Exception {
        Utils.click(mDevice, By.res("com.songheng.eastnews:id/vf"));
        Utils.readPage(mDevice);
    }

    public void smallVideo() throws Exception {
        int step = Utils.getRandom(mMinCycleValue, mMaxCycleValue);
        for (int i = 0; i < step; i++) {
            Utils.click(mDevice, By.res("com.songheng.eastnews:id/vf"));
            Utils.click(mDevice, By.res("com.songheng.eastnews:id/a8l"));

            List<UiObject2> uiObjList = mDevice.findObjects(By.res("com.songheng.eastnews:id/a76"));
            for (UiObject2 uiObj : uiObjList) {
                try {
                    if (Utils.clickRandom(uiObj, 0.9f)) {
                        Utils.sleepRandomSecond(15, 30);
                        mDevice.pressBack();
                    }
                } catch (Exception e) {
                }
            }
            Utils.slideVerticalUp(mDevice);
            Utils.sleepRandomSecond(1);
        }
    }

    public void videoPage() throws Exception {
        int step = Utils.getRandom(mMinCycleValue, mMaxCycleValue);
        for (int i = 0; i < step; i++) {
            Utils.click(mDevice, By.res("com.songheng.eastnews:id/vf"));

            List<UiObject2> uiObjList = mDevice.findObjects(By.res("com.songheng.eastnews:id/nm"));
            for (UiObject2 uiObj : uiObjList) {
                try {
                    if (Utils.clickRandom(uiObj, 0.85f)) {
                        Utils.sleepRandomSecond(20, 30);
                        Utils.slideVertical(mDevice, 0.8f, 0.6f);
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