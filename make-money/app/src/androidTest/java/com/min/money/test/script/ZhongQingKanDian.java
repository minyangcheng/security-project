package com.min.money.test.script;

import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject2;

import com.min.money.test.BaseAuto;
import com.min.money.test.util.Utils;

import java.util.List;

public class ZhongQingKanDian extends BaseAuto {

    public ZhongQingKanDian() {
        super("cn.youth.news");
    }

    @Override
    public void operate() throws Exception {
        for (int i = 0; i < 2; i++) {
            Utils.sleepRandomSecond(2, 4);
            newsPage();
            Utils.click(mDevice, By.text("我的"));
            Utils.sleepRandomSecond(2, 4);
            minePage();
            Utils.click(mDevice, By.text("榜单"));
            Utils.sleepRandomSecond(2, 4);
            rankPage();
            Utils.click(mDevice, By.text("视频"));
            Utils.sleepRandomSecond(2, 4);
            videoPage();

            Utils.click(mDevice, By.text("首页"));
        }
    }

    public void newsPage() throws Exception {
        int step = Utils.getRandom(mMinCycleValue, mMaxCycleValue);
        for (int i = 0; i < step; i++) {
            Utils.click(mDevice, By.res("cn.youth.news:id/ge"));

            List<UiObject2> uiObjList = mDevice.findObjects(By.res("cn.youth.news:id/tv"));
            for (UiObject2 uiObj : uiObjList) {
                try {
                    boolean flag = Utils.clickRandom(uiObj, 0.85f);
                    if (flag) {
                        Utils.sleepRandomSecond(4, 6);
                        newsDetailPage();
                        mDevice.pressBack();
                    }
                } catch (Exception e) {
                }
            }
            Utils.slideVerticalUp(mDevice);
            Utils.sleepRandomSecond(1);
        }
    }

    public void minePage() throws Exception {
        Utils.click(mDevice, By.res("cn.youth.news:id/ge"));

        String[] textArr = {"关注", "收藏", "收入明细", "兑换提现"};
        Utils.openPageRandom(mDevice, textArr);
    }

    public void newsDetailPage() throws Exception {
        Utils.readPage(mDevice);
    }

    public void rankPage() throws Exception {
        int step = Utils.getRandom(mMinCycleValue, mMaxCycleValue);
        for (int i = 0; i < step; i++) {
            Utils.click(mDevice, By.res("cn.youth.news:id/ge"));

            List<UiObject2> uiObjList = mDevice.findObjects(By.res("cn.youth.news:id/tv"));
            for (UiObject2 uiObj : uiObjList) {
                try {
                    boolean flag = Utils.clickRandom(uiObj, 0.85f);
                    if (flag) {
                        Utils.sleepRandomSecond(4, 6);
                        newsDetailPage();
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
            Utils.click(mDevice, By.res("cn.youth.news:id/ge"));

            List<UiObject2> uiObjList = mDevice.findObjects(By.res("cn.youth.news:id/v9"));
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