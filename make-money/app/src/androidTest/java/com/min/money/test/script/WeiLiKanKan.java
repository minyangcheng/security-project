package com.min.money.test.script;

import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject2;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ScreenUtils;
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
            if (Helper.click(By.text("头条")) || Helper.waitUiObjAppear(By.text("推荐"))) {
                LogUtils.i("进入头条页面");
                newsPage();
            }else {
                Helper.pressBack();
            }
            if (Helper.click(By.text("我的"))) {
                LogUtils.i("进入我的页面");
                minePage();
            }else {
                Helper.pressBack();
            }
            if (Helper.click(By.text("社区"))) {
                LogUtils.i("进入社区页面");
                socialPage();
            }else {
                Helper.pressBack();
            }
        }
    }

    @Override
    protected int recordCoin() {
        if (Helper.click(By.text("我的"))) {
            LogUtils.i("进入我的页面查看当前金币");
            handleDialog();
            Helper.slideVertical(0.2f, 0.9f);
            UiObject2 uiObj = Helper.findObjectInCertainTime(By.res("cn.weli.story:id/text_today_coin"));
            if (uiObj != null) {
                String coinStr = Helper.getText(uiObj);
                coinStr = Helper.contractIntStr(coinStr);
                LogUtils.i("当前金币为：" + coinStr);
                return Helper.parseInt(coinStr);
            }
        }
        return 0;
    }

    @Override
    protected void handleDialog() {
        long startTime = System.currentTimeMillis();
        List<Boolean> dataList = new ArrayList<>();
        dataList.add(Helper.click(By.res("cn.weli.story:id/iv_take")));
        dataList.add(Helper.click(By.res("cn.weli.story:id/iv_close")));
        dataList.add(Helper.click(By.res("cn.weli.story:id/ic_close")));
        dataList.add(Helper.click(By.res("cn.weli.story:id/bt_ok")));
        dataList.add(Helper.click(By.res("cn.weli.story:id/image_close")));
        dataList.add(Helper.click(By.res("cn.weli.story:id/text_ok")));
        Helper.recordLogHasDismissDialog(dataList, startTime);
    }

    public void newsPage() {
        int step = Helper.getRandomInRange(mMinCycleValue, mMaxCycleValue);
        for (int i = 0; i < step; i++) {
            handleDialog();

            List<UiObject2> uiObjList = Helper.findObjects(By.res("cn.weli.story:id/tv_title"));
            for (UiObject2 uiObj : uiObjList) {
                try {
                    if (uiObj.getParent().findObject(By.res("cn.weli.story:id/ll_open")) != null) {
                        Helper.click(uiObj);
                        handleDialog();
                    } else {
                        String tempStr = uiObj.getText();
                        if (checkItemHasOperate(tempStr)) {
                            continue;
                        }
                        if (Helper.clickRandom(uiObj, 0.8f)) {
                            LogUtils.i("点击进入标题页面：" + tempStr);
                            newsDetailPage();
                            Helper.pressBack();
                            LogUtils.i("点击返回键退出标题页面");
                        }
                    }
                } catch (Exception e) {
                }
            }
            Helper.slideVerticalUp();
            if (Helper.click(By.res("cn.weli.story:id/tv_treasure_count"))) {
                LogUtils.i("点击领取时段奖励");
            }
        }
    }

    public void minePage() {
        handleDialog();

        if (Helper.click(By.res("cn.weli.story:id/ll_not_sign"))) {
            Helper.click(490f/ ScreenUtils.getScreenWidth(),570f/ScreenUtils.getScreenHeight());
            Helper.pressBack();
        }
        String[] textArr = {"我的金币", "累计零钱", "零钱余额", "短信邀请"};
        Helper.openPageRandom(textArr);
    }

    public void newsDetailPage() {
        handleDialog();
        Helper.readPage();
    }

    public void socialPage() {
        int step = Helper.getRandomInRange(1, 2);
        for (int i = 0; i < step; i++) {
            Helper.slideVerticalUp();
            Helper.click(0.5f, 0.5f);
            Helper.readTextShort();
            Helper.pressBack();
        }
        Helper.click(By.res("关注"));
        Helper.slideVertical(0.6f, 0.2f);
    }

    public void videoPage() {
        int step = Helper.getRandomInRange(mMinCycleValue, mMaxCycleValue);
        for (int i = 0; i < step; i++) {
            List<UiObject2> uiObjList = Helper.findObjects(By.res("cn.weli.story:id/img_ic_play"));
            for (UiObject2 uiObj : uiObjList) {
                try {
                    if (Helper.clickRandom(uiObj, 0.8f)) {
                        Helper.readVideoShort();
                        Helper.pressBack();
                    }
                } catch (Exception e) {
                }
            }
            Helper.slideVerticalUp();
        }
    }

}