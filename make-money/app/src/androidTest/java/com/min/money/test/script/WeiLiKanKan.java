package com.min.money.test.script;

import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject2;

import com.blankj.utilcode.util.LogUtils;
import com.min.money.test.BaseAuto;
import com.min.money.test.util.Helper;

import java.util.ArrayList;
import java.util.List;

public class WeiLiKanKan extends BaseAuto {

    public WeiLiKanKan() {
        super("cn.weli.story");
    }

    @Override
    public void operate() {
        for (int i = 0; i < mOperateTimes; i++) {
            if (Helper.click(mDevice, By.text("头条")) || Helper.waitUiObjAppear(mDevice, By.text("推荐"))) {
                LogUtils.i("进入头条页面");
                newsPage();
            }
            if (Helper.click(mDevice, By.text("我的"))) {
                LogUtils.i("进入我的页面");
                minePage();
            }
            if (Helper.click(mDevice, By.text("社区"))) {
                LogUtils.i("进入社区页面");
                socialPage();
            }
        }
    }

    @Override
    protected void recordCoin() {
        if (Helper.click(mDevice, By.text("我的"))) {
            LogUtils.i("查看金币，进入我的页面");
            removeRedundancyDialog();
            Helper.recordLogCoin(mTag, Helper.getText(mDevice, By.res("cn.weli.story:id/text_today_coin")));
        }
    }

    @Override
    protected void removeRedundancyDialog() {
        List<Boolean> dataList = new ArrayList<>();
        dataList.add(Helper.click(mDevice, By.res("cn.weli.story:id/iv_take")));
        dataList.add(Helper.click(mDevice, By.res("cn.weli.story:id/iv_close")));
        dataList.add(Helper.click(mDevice, By.res("cn.weli.story:id/ic_close")));
        dataList.add(Helper.click(mDevice, By.res("cn.weli.story:id/bt_ok")));
        dataList.add(Helper.click(mDevice, By.res("cn.weli.story:id/image_close")));
        dataList.add(Helper.click(mDevice, By.res("cn.weli.story:id/text_ok")));
        Helper.recordLogHasDismissDialog(dataList);
    }

    public void newsPage() {
        int step = Helper.getRandomInRange(mMinCycleValue, mMaxCycleValue);
        for (int i = 0; i < step; i++) {
            removeRedundancyDialog();

            List<UiObject2> uiObjList = mDevice.findObjects(By.res("cn.weli.story:id/tv_title"));
            for (UiObject2 uiObj : uiObjList) {
                try {
                    if (uiObj.getParent().findObject(By.res("cn.weli.story:id/ll_open")) != null) {
                        Helper.click(uiObj);
                        removeRedundancyDialog();
                    } else {
                        String tempStr = uiObj.getText();
                        if (checkItemHasOperate(tempStr)) {
                            continue;
                        }
                        if (Helper.clickRandom(uiObj, 0.8f)) {
                            LogUtils.i("点击进入标题页面：" + tempStr);
                            newsDetailPage();
                            mDevice.pressBack();
                            LogUtils.i("点击返回键退出标题页面");
                        }
                    }
                } catch (Exception e) {
                }
            }
            Helper.slideVerticalUp(mDevice);
            if (Helper.click(mDevice, By.res("cn.weli.story:id/tv_treasure_count"))) {
                LogUtils.i("点击领取时段奖励");
            }
        }
    }

    public void minePage() {
        removeRedundancyDialog();

        if (Helper.click(mDevice, By.textContains("立即签到"))) {
            Helper.click(mDevice, By.clazz("android.widget.Button").text("立即签到"));
            mDevice.pressBack();
        }
        String[] textArr = {"我的金币", "累计零钱", "零钱余额", "短信邀请"};
        Helper.openPageRandom(mDevice, textArr);
    }

    public void newsDetailPage() {
        removeRedundancyDialog();
        Helper.readPage(mDevice);
    }

    public void socialPage() {
        int step = Helper.getRandomInRange(1, 2);
        for (int i = 0; i < step; i++) {
            Helper.slideVerticalUp(mDevice);
            Helper.click(mDevice, 0.5f, 0.5f);
            Helper.readTextShort();
            mDevice.pressBack();
        }
        Helper.click(mDevice, By.res("关注"));
        Helper.slideVertical(mDevice, 0.6f, 0.2f);
    }

    public void videoPage() {
        int step = Helper.getRandomInRange(mMinCycleValue, mMaxCycleValue);
        for (int i = 0; i < step; i++) {
            List<UiObject2> uiObjList = mDevice.findObjects(By.res("cn.weli.story:id/img_ic_play"));
            for (UiObject2 uiObj : uiObjList) {
                try {
                    if (Helper.clickRandom(uiObj, 0.8f)) {
                        Helper.readVideoShort();
                        mDevice.pressBack();
                    }
                } catch (Exception e) {
                }
            }
            Helper.slideVerticalUp(mDevice);
        }
    }

}