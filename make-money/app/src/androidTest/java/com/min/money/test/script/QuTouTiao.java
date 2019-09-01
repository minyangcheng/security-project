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
            if (Helper.click(mDevice, By.text("头条")) || Helper.waitUiObjAppear(mDevice, By.text("推荐"))) {
                LogUtils.i("进入头条页面");
                newsPage();
            }
            if (Helper.click(mDevice, By.text("我的"))) {
                LogUtils.i("进入我的页面");
                minePage();
            }
            if (Helper.click(mDevice, By.text("任务"))) {
                LogUtils.i("进入任务页面");
                taskPage();
            }
            if (Helper.click(mDevice, By.text("视频"))) {
                LogUtils.i("进入视频页面");
                videoPage();
            }
        }
    }

    @Override
    protected int getNowCoin() {
        if (Helper.click(mDevice, By.text("我的"))) {
            removeRedundancyDialog();
            Helper.slideVertical(mDevice, 0.2f, 0.9f);
            UiObject2 uiObj = Helper.findObjectInCertainTime(mDevice, By.res("com.jifen.qukan:id/tx"));
            if (uiObj != null) {
                String coinStr = Helper.getText(uiObj);
                return Helper.parseInt(Helper.contractIntStr(coinStr));
            }
        }
        return 0;
    }

    @Override
    protected void removeRedundancyDialog() {
        long startTime = System.currentTimeMillis();
        List<Boolean> dataList = new ArrayList<>();
        dataList.add(Helper.click(mDevice, By.res("com.jifen.qukan:id/rb").text("以后更新")));
        dataList.add(Helper.click(mDevice, By.res("com.jifen.qukan:id/a4j").text("先去逛逛")));
        dataList.add(Helper.click(mDevice, By.res("com.jifen.qukan:id/p4")));
        dataList.add(Helper.click(mDevice, By.res("com.jifen.qukan:id/pl")));
        dataList.add(Helper.click(mDevice, By.res("com.jifen.qukan:id/og")));
        dataList.add(Helper.click(mDevice, By.res("com.jifen.qukan:id/nu")));
        Helper.recordLogHasDismissDialog(dataList,startTime);
    }

    public void newsPage() {
        int step = Helper.getRandomInRange(mMinCycleValue, mMaxCycleValue);
        for (int i = 0; i < step; i++) {
            removeRedundancyDialog();

            List<UiObject2> uiObjList = mDevice.findObjects(By.res("com.jifen.qukan:id/xx"));
            for (UiObject2 uiObj : uiObjList) {
                try {
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
                } catch (Exception e) {
                }
            }
            Helper.slideVerticalUp(mDevice);
            Helper.clickRandom(mDevice, By.res("com.jifen.qukan:id/sk").text("领取"), 0.5f);
        }
    }

    public void minePage() {
        removeRedundancyDialog();
        String[] textArr = {"我的金币", "提现兑换", "提现记录", "历史记录"};
        Helper.openPageRandom(mDevice, textArr);
    }

    public void newsDetailPage() {
        removeRedundancyDialog();
        Helper.readPage(mDevice);
    }

    public void taskPage() {
        removeRedundancyDialog();
        Helper.slideVertical(mDevice, 0.4f, 0.2f);
        Helper.readTextShort();
        Helper.slideVertical(mDevice, 0.4f, 0.2f);
    }

    public void videoPage() {
        int step = Helper.getRandomInRange(mMinCycleValue, mMaxCycleValue);
        for (int i = 0; i < step; i++) {
            removeRedundancyDialog();

            List<UiObject2> uiObjList = mDevice.findObjects(By.res("com.jifen.qukan:id/a3b"));
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
            Helper.slideVerticalUp(mDevice);
        }
    }

}