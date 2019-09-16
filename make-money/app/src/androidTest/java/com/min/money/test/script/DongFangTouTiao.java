package com.min.money.test.script;

import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject2;

import com.blankj.utilcode.util.LogUtils;
import com.min.money.test.BaseAuto;
import com.min.money.test.util.Helper;

import java.util.ArrayList;
import java.util.List;

public class DongFangTouTiao extends BaseAuto {

    public DongFangTouTiao() {
        super("com.songheng.eastnews");
    }

    @Override
    public void operate() {
        for (int i = 0; i < mOperateTimes; i++) {
            if (Helper.click(By.text("新闻")) || Helper.waitUiObjAppear(By.text("推荐"))) {
                LogUtils.i("进入头条页面");
                newsPage();
            }

            if (Helper.click(By.text("我的"))) {
                LogUtils.i("进入我的页面");
                minePage();
            }

            if (Helper.click(By.text("任务"))) {
                LogUtils.i("进入任务页面");
                taskPage();
            }

            if (Helper.click(By.text("游戏"))) {
                LogUtils.i("进入游戏页面");
                gamePage();
            }

            if (Helper.click(By.text("视频"))) {
                LogUtils.i("进入视频页面");
                videoPage();
            }
        }
    }

    @Override
    protected int recordCoin() {
        if (Helper.click(By.text("我的"))) {
            LogUtils.i("进入我的页面查看当前金币");
            handleDialog();
            Helper.slideVertical(0.2f, 0.9f);
            UiObject2 uiObj = Helper.findObjectInCertainTime(By.res("com.songheng.eastnews:id/aoz"));
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
        if (!Helper.hasObject(By.res("com.songheng.eastnews:id/a6_")) && !Helper.hasObject(By.res("com.songheng.eastnews:id/yt"))) {
            dataList.add(Helper.click(By.res("com.songheng.eastnews:id/vf")));
            dataList.add(Helper.click(By.res("com.songheng.eastnews:id/ua")));
            dataList.add(Helper.click(By.res("com.songheng.eastnews:id/e4")));
            Helper.brightScreen();
            dataList.add(Helper.click(By.res("com.songheng.eastnews:id/fq")));
            dataList.add(Helper.click(By.res("com.songheng.eastnews:id/pu")));
        }
        Helper.recordLogHasDismissDialog(dataList, startTime);
        Helper.brightScreen();
    }

    public void newsPage() {
        int step = Helper.getRandomInRange(mMinCycleValue, mMaxCycleValue);
        for (int i = 0; i < step; i++) {
            handleDialog();

            List<UiObject2> uiObjList = Helper.findObjects(By.res("com.songheng.eastnews:id/asg"));
            for (UiObject2 uiObj : uiObjList) {
                try {
                    String tempStr = Helper.getText(Helper.findObjectInParent(uiObj, By.res("com.songheng.eastnews:id/auu"), 2));
                    if (checkItemHasOperate(tempStr) || tempStr.contains("六张海报")) {
                        continue;
                    }
                    if (Helper.clickRandom(uiObj, 0.8f)) {
                        LogUtils.i("点击进入标题页面：" + tempStr);
                        newsDetailPage();
                        Helper.pressBack();
                        LogUtils.i("点击返回键退出标题页面");
                    }

                } catch (Exception e) {
                }
            }
            Helper.slideVerticalUp();
            Helper.clickRandom(By.res("com.songheng.eastnews:id/ajr").text("领取"), 0.5f);
        }
    }

    public void taskPage() {
        handleDialog();
        Helper.slideVertical(0.5f, 0.3f);
        Helper.readTextShort();
    }

    public void newsDetailPage() {
        handleDialog();
        Helper.readPage();
    }

    public void minePage() {
        handleDialog();
        String[] textArr = {"立即体现", "我的钱包", "邀请好友", "我的消息", "游戏中心"};
        Helper.openPageRandom(textArr);
    }

    public void gamePage() {
        handleDialog();
        Helper.readTextShort();
    }

    public void videoPage() {
        int step = Helper.getRandomInRange(mMinCycleValue, mMaxCycleValue);
        for (int i = 0; i < step; i++) {
            handleDialog();

            List<UiObject2> uiObjList = Helper.findObjects(By.res("com.songheng.eastnews:id/x7"));
            for (UiObject2 uiObj : uiObjList) {
                try {
                    String tempStr = Helper.getText(uiObj.getParent(), By.res("com.songheng.eastnews:id/auu"));
                    if (checkItemHasOperate(tempStr)) {
                        continue;
                    }
                    if (Helper.clickRandom(uiObj, 0.8f)) {
                        LogUtils.i("点击观看视频：" + tempStr);
                        Helper.readVideoShort();
                        Helper.pressBack();
                        LogUtils.i("观看视频完毕");
                    }
                } catch (Exception e) {
                }
            }
            Helper.slideVerticalUp();
        }
    }

}
