package com.min.money.test.script;

import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject2;

import com.blankj.utilcode.util.LogUtils;
import com.min.money.test.BaseAuto;
import com.min.money.test.util.Helper;

import java.util.ArrayList;
import java.util.List;

public class QuTouTiao extends BaseAuto {

    public QuTouTiao() {
        super("com.jifen.qukan");
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
            } else {
                Helper.pressBack();
            }
            if (Helper.click(By.text("任务"))) {
                LogUtils.i("进入任务页面");
                taskPage();
            } else {
                Helper.pressBack();
            }
            if (Helper.click(By.text("视频"))) {
                LogUtils.i("进入视频页面");
                videoPage();
            } else {
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
            UiObject2 uiObj = Helper.findObjectInCertainTime(By.res("com.jifen.qukan:id/tx"));
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
        dataList.add(Helper.click(By.res("com.jifen.qukan:id/rb").text("以后更新")));
        dataList.add(Helper.click(By.res("com.jifen.qukan:id/a4j").text("先去逛逛")));
        dataList.add(Helper.click(By.res("com.jifen.qukan:id/p4")));
        dataList.add(Helper.click(By.res("com.jifen.qukan:id/pl")));
        dataList.add(Helper.click(By.res("com.jifen.qukan:id/og")));
        dataList.add(Helper.click(By.res("com.jifen.qukan:id/nu")));
        dataList.add(Helper.click(By.clickable(true).textContains("我知道")));

        Helper.recordLogHasDismissDialog(dataList, startTime);
    }

    public void newsPage() {
        int step = Helper.getRandomInRange(mMinCycleValue, mMaxCycleValue);
        for (int i = 0; i < step; i++) {
            handleDialog();

            List<UiObject2> uiObjList = Helper.findObjects(By.res("com.jifen.qukan:id/xx"));
            for (UiObject2 uiObj : uiObjList) {
                try {
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
                } catch (Exception e) {
                }
            }
            Helper.slideVerticalUp();
            Helper.clickRandom(By.res("com.jifen.qukan:id/sk").text("领取"), 0.5f);
        }
    }

    public void minePage() {
        handleDialog();
        String[] textArr = {"我的金币", "提现兑换", "提现记录", "历史记录"};
        Helper.openPageRandom(textArr);
    }

    public void newsDetailPage() {
        handleDialog();
        Helper.readPage();

    }

    public void taskPage() {
        handleDialog();
        Helper.slideVertical(0.4f, 0.2f);
        Helper.readTextShort();
        Helper.slideVertical(0.4f, 0.2f);
    }

    public void videoPage() {
        int step = Helper.getRandomInRange(mMinCycleValue, mMaxCycleValue);
        for (int i = 0; i < step; i++) {
            handleDialog();

            List<UiObject2> uiObjList = Helper.findObjects(By.res("com.jifen.qukan:id/a3b"));
            for (UiObject2 uiObj : uiObjList) {
                try {
                    String tempStr = Helper.getText(uiObj.getParent(), By.res("com.jifen.qukan:id/a1a"));
                    if (checkItemHasOperate(tempStr)) {
                        continue;
                    }
                    if (Helper.clickRandom(uiObj, 0.8f)) {
                        LogUtils.i("点击观看视频：" + tempStr);
                        Helper.readVideoShort();
                        LogUtils.i("观看视频完毕");
                    }
                } catch (Exception e) {
                }
            }
            Helper.slideVerticalUp();
        }
    }

}