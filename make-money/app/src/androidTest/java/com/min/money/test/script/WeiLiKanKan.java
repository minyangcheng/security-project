package com.min.money.test.script;

import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject2;

import com.min.money.test.BaseAuto;
import com.min.money.test.util.Utils;

import java.util.List;

public class WeiLiKanKan extends BaseAuto {

    public WeiLiKanKan() {
        super("cn.weli.story");
    }

    @Override
    public void operate() throws Exception {
        for (int i = 0; i < 2; i++) {
            Utils.sleepRandomSecond(2, 4);
            newsPage();
            Utils.click(mDevice, By.text("我的"));
            Utils.sleepRandomSecond(2, 4);
            minePage();
//            Utils.click(mDevice, By.text("社区"));
//            Utils.sleepRandomSecond(2, 4);
//            socialPage();
//            Utils.click(mDevice, By.text("视频"));
//            Utils.sleepRandomSecond(2, 4);
//            videoPage();
            Utils.click(mDevice, By.text("头条"));
            Utils.sleepRandomSecond(2, 4);
        }
    }

    public void newsPage() throws Exception {
        int step = Utils.getRandom(mMinCycleValue, mMaxCycleValue);
        for (int i = 0; i < step; i++) {
            Utils.click(mDevice, By.res("cn.weli.story:id/iv_take"));
            Utils.click(mDevice, By.res("cn.weli.story:id/iv_close"));
            Utils.click(mDevice, By.res("cn.weli.story:id/bt_ok"));

            List<UiObject2> uiObjList = mDevice.findObjects(By.res("cn.weli.story:id/tv_title"));
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
            Utils.click(mDevice, By.res("cn.weli.story:id/iv_gold"));
            Utils.slideVerticalUp(mDevice);
            Utils.sleepRandomSecond(1);
        }
    }

    public void minePage() throws Exception {
        Utils.click(mDevice, By.res("cn.weli.story:id/image_close"));

        Utils.click(mDevice, By.textContains("立即签到"));
        Utils.sleepRandomSecond(1);
        Utils.click(mDevice, By.res("cn.weli.story:id/ic_close"));
        Utils.click(mDevice, 0.9f, 0.9f);

        String[] textArr = {"我的金币", "累计零钱", "零钱余额", "短信邀请"};
        Utils.openPageRandom(mDevice, textArr);
    }

    public void newsDetailPage() throws Exception {
        Utils.sleepRandomSecond(3);
        Utils.click(mDevice, By.res("cn.weli.story:id/bt_ok"));
        Utils.readPage(mDevice);
    }

    public void socialPage() throws Exception {
        int step = Utils.getRandom(1, 2);
        for (int i = 0; i < step; i++) {
            Utils.slideVertical(mDevice, 0.8f, 0.2f);
            Utils.sleepRandomSecond(2, 4);
            Utils.click(mDevice, 0.5f, 0.5f);
            Utils.sleepRandomSecond(2, 4);
            mDevice.pressBack();
        }
        Utils.click(mDevice, By.res("关注"));
        Utils.slideVertical(mDevice, 0.6f, 0.2f);
    }

    public void videoPage() throws Exception {
        int step = Utils.getRandom(mMinCycleValue, mMaxCycleValue);
        for (int i = 0; i < step; i++) {
            List<UiObject2> uiObjList = mDevice.findObjects(By.res("cn.weli.story:id/img_ic_play"));
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