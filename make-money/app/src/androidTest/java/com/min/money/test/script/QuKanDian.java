package com.min.money.test.script;

import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject2;

import com.min.money.test.BaseAuto;
import com.min.money.test.util.Utils;

import java.util.List;

public class QuKanDian extends BaseAuto {

    public QuKanDian() {
        super("com.zhangku.qukandian");
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
            videoPage();

            Utils.click(mDevice, By.text("新闻"));
        }
    }

    public void newsPage() throws Exception {
        int step = Utils.getRandom(mMinCycleValue, mMaxCycleValue);
        for (int i = 0; i < step; i++) {
            Utils.click(mDevice, By.res("com.zhangku.qukandian:id/dialog_operate_close"));
            Utils.click(mDevice, By.res("com.zhangku.qukandian:id/closeBtn"));

            List<UiObject2> uiObjList = mDevice.findObjects(By.res("com.zhangku.qukandian:id/item_information_adapter_one_pic_number"));
            for (UiObject2 uiObj : uiObjList) {
                try {
                    boolean flag = Utils.clickRandom(uiObj, 0.9f);
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
        Utils.click(mDevice, By.res("com.zhangku.qukandian:id/dialog_operate_close"));
        Utils.click(mDevice, By.res("com.zhangku.qukandian:id/closeBtn"));

        String[] textArr = {"我的金币", "我的徒弟", "我的收藏", "收入排行榜", "我要提现"};
        Utils.openPageRandom(mDevice, textArr);
    }

    public void newsDetailPage() throws Exception {
        Utils.click(mDevice, By.res("com.zhangku.qukandian:id/dialog_operate_close"));
        Utils.click(mDevice, By.res("com.zhangku.qukandian:id/closeBtn"));

        Utils.readPage(mDevice);
    }

    public void taskPage() throws Exception {
        Utils.click(mDevice, By.res("com.zhangku.qukandian:id/dialog_operate_close"));
        Utils.click(mDevice, By.res("com.zhangku.qukandian:id/closeBtn"));

        Utils.click(mDevice, By.res("com.zhangku.qukandian:id/signTV"));
        Utils.sleepRandomSecond(1);
        Utils.click(mDevice, By.res("com.zhangku.qukandian:id/sign_cancel_btn"));
    }

    public void videoPage() throws Exception {
        int step = Utils.getRandom(mMinCycleValue, mMaxCycleValue);
        for (int i = 0; i < step; i++) {
            Utils.click(mDevice, By.res("com.zhangku.qukandian:id/dialog_operate_close"));
            Utils.click(mDevice, By.res("com.zhangku.qukandian:id/closeBtn"));

            List<UiObject2> uiObjList = mDevice.findObjects(By.res("com.zhangku.qukandian:id/item_video_fragment_adapter_view_img"));
            for (UiObject2 uiObj : uiObjList) {
                try {
                    if (Utils.clickRandom(uiObj, 0.9f)) {
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