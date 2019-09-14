package com.min.money.test.script;

import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject2;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.min.money.test.BaseAuto;
import com.min.money.test.util.Helper;

import java.util.ArrayList;
import java.util.List;

public class ShuaBao extends BaseAuto {

    public ShuaBao() {
        super("com.jm.video");
    }

    @Override
    public void operate() {
        for (int i = 0; i < mOperateTimes; i++) {
            if (Helper.click(By.text("首页"))) {
                LogUtils.i("进入首页页面");
                mainPage();
            }
            if (Helper.click(By.text("我"))) {
                LogUtils.i("进入我页面");
                minePage();
            } else {
                Helper.pressBack();
            }
            if (Helper.click(By.text("任务"))) {
                LogUtils.i("进入任务页面");
                taskPage();
            } else {
                Helper.pressBack();
            }
            if (Helper.click(By.text("直播"))) {
                LogUtils.i("进入直播页面");
                livePage();
            } else {
                Helper.pressBack();
            }
        }
    }

    @Override
    protected int recordCoin() {
        if (Helper.click(By.text("我"))) {
            LogUtils.i("进入我页面查看当前金币");
            handleDialog();
            Helper.slideVertical(0.2f, 0.9f);
            UiObject2 uiObj = Helper.findObjectInCertainTime(By.res("com.jm.video:id/tv_gold_num"));
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
        dataList.add(Helper.click(By.res("com.jm.video:id/imgClose")));
        Helper.recordLogHasDismissDialog(dataList, startTime);
    }

    public void mainPage() {
        int step = Helper.getRandomInRange(mMinCycleValue + 20, mMaxCycleValue + 20);
        for (int i = 0; i < step; i++) {
            try {
                if (!Helper.hasObject(By.textContains("下载"))) {
                    String tempStr = Helper.getText(By.res("com.jm.video:id/desc"));
                    LogUtils.i("点击观看视频：" + tempStr);
                    Helper.readVideoLittle();
                    if (Helper.clickRandom(By.res("com.jm.video:id/praise"), 0.4f)) {
                        LogUtils.i("点赞...");
                    }
                    LogUtils.i("观看视频完毕");
                }
                Helper.slideVerticalUp();
            } catch (Exception e) {
            }
        }
    }

    public void minePage() {
        String[] textArr = {"关注", "粉丝", "当前余额(元)", "查看更多"};
        Helper.openPageRandom(textArr);
    }

    public void taskPage() {
        handleDialog();
        Helper.click(850f / ScreenUtils.getScreenWidth(), 490f / ScreenUtils.getScreenHeight());
        if (Helper.hasObject(By.res("com.jm.video:id/title_bar"))) {
            Helper.pressBack();
        } else {
            Helper.click(575f / ScreenUtils.getScreenWidth(), 1275f / ScreenUtils.getScreenHeight());
            if (!Helper.hasObject(By.res("com.jm.video:id/tabLayout"))) {
                UiObject2 tempObj = null;
                while ((tempObj = Helper.findObject(By.res("com.jm.video:id/tt_video_ad_close"), 10 * 1000)) == null) {
                    Helper.readVideoShort();
                }
                tempObj.click();
                handleDialog();
            }
        }

    }

    public void livePage() {
        Helper.slideVerticalUp();
        if (Helper.click(0.3f, 0.5f)) {
            Helper.readVideoShort();
            handleDialog();
            Helper.pressBack();
        }
    }

}