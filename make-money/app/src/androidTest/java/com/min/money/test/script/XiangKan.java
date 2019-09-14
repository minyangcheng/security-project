package com.min.money.test.script;

import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject2;

import com.blankj.utilcode.util.LogUtils;
import com.min.money.test.BaseAuto;
import com.min.money.test.util.Helper;

import java.util.ArrayList;
import java.util.List;

public class XiangKan extends BaseAuto {

    public XiangKan() {
        super("com.xiangkan.android");
    }

    @Override
    public void operate() {
        for (int i = 0; i < mOperateTimes; i++) {
            if (Helper.click(By.text("首页"))) {
                LogUtils.i("进入首页页面");
                newsPage();
            }
            if (Helper.click(By.text("我的"))) {
                LogUtils.i("进入我的页面");
                minePage();
            } else {
                Helper.pressBack();
            }
            if (Helper.click(By.text("围观"))) {
                LogUtils.i("进入围观页面");
                lookPage();
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
            UiObject2 uiObj = Helper.findObjectInCertainTime(By.res("com.xiangkan.android:id/coin_total_item").hasChild(By.text("我的金币")));
            uiObj = uiObj.findObject(By.res("com.xiangkan.android:id/label_tv"));
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
        dataList.add(Helper.click(By.res("com.xiangkan.android:id/ivClose")));

        Helper.recordLogHasDismissDialog(dataList, startTime);
    }

    public void newsPage() {
        int step = Helper.getRandomInRange(mMinCycleValue, mMaxCycleValue);
        for (int i = 0; i < step; i++) {
            handleDialog();

            List<UiObject2> uiObjList = Helper.findObjects(By.res("com.xiangkan.android:id/tvTitle"));
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

            if (Helper.clickRandom(By.res("com.xiangkan.android:id/tv_box_hint").text("签到"), 0.5f)) {
                Helper.click(By.res("com.xiangkan.android:id/tvSign"));
                handleDialog();
            }
            Helper.clickRandom(By.res("com.xiangkan.android:id/tv_box_time_new").text("领金币"), 0.5f);
            if (Helper.click(By.res("com.xiangkan.android:id/rec_task_btn").text("开福袋"))) {
                Helper.click(By.res("com.xiangkan.android:id/more_minute_btn").text("继续阅读"));
            }
        }
    }

    public void minePage() {
        handleDialog();
        String[] textArr = {"今日阅读", "今日金币", "我的金币", "金币提现"};
        Helper.openPageRandom(textArr);
    }

    public void newsDetailPage() {
        handleDialog();
        Helper.readPage();

    }

    public void lookPage() {
        handleDialog();
        Helper.slideVertical(0.4f, 0.2f);
        Helper.readTextShort();
        Helper.slideVertical(0.4f, 0.2f);
    }

    public void videoPage() {
        int step = Helper.getRandomInRange(mMinCycleValue + 20, mMaxCycleValue + 20);
        for (int i = 0; i < step; i++) {
            handleDialog();

            List<UiObject2> uiObjList = Helper.findObjects(By.res("com.xiangkan.android:id/video_item_play_btn"));
            for (UiObject2 uiObj : uiObjList) {
                try {
                    String tempStr = Helper.getText(uiObj.getParent(), By.res("com.xiangkan.android:id/video_item_title"));
                    if (checkItemHasOperate(tempStr)) {
                        continue;
                    }
                    if (Helper.clickRandom(uiObj, 1f)) {
                        LogUtils.i("点击观看视频：" + tempStr);
                        Helper.readVideoShort();
                        LogUtils.i("观看视频完毕");
                        Helper.pressBack();
                    }
                } catch (Exception e) {
                }
            }
            Helper.slideVerticalUp();
        }
    }

}