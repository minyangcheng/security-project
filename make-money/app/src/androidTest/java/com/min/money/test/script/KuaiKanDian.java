package com.min.money.test.script;

import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject2;

import com.blankj.utilcode.util.LogUtils;
import com.min.money.test.BaseAuto;
import com.min.money.test.util.Helper;

import java.util.ArrayList;
import java.util.List;

public class KuaiKanDian extends BaseAuto {

    public KuaiKanDian() {
        super("com.yuncheapp.android.pearl");
    }

    @Override
    public void operate() {
        for (int i = 0; i < mOperateTimes; i++) {
            if (Helper.click(By.text("首页"))) {
                LogUtils.i("进入首页页面");
                newsPage();
            } else {
                Helper.pressBack();
            }
            if (Helper.click(By.text("任务")) || Helper.click(By.res("com.yuncheapp.android.pearl:id/tab_tv").textContains(":"))) {
                LogUtils.i("进入任务页面");
                minePage();
            } else {
                Helper.pressBack();
            }
            if (Helper.click(By.text("小视频"))) {
                LogUtils.i("进入小视频页面");
                smallVideoPage();
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
        if (Helper.click(By.text("任务")) || Helper.click(By.res("com.yuncheapp.android.pearl:id/tab_tv").textContains(":"))) {
            LogUtils.i("进入我的页面查看当前金币");
            handleDialog();
            UiObject2 uiObj = Helper.findObjectInCertainTime(By.res("com.yuncheapp.android.pearl:id/my_gold"));
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
        dataList.add(Helper.click(By.res("com.yuncheapp.android.pearl:id/tv_close")));
        dataList.add(Helper.click(By.res("com.yuncheapp.android.pearl:id/close_iv")));
        Helper.recordLogHasDismissDialog(dataList, startTime);
    }

    public void newsPage() {
        int step = Helper.getRandomInRange(mMinCycleValue, mMaxCycleValue);
        for (int i = 0; i < step; i++) {
            handleDialog();

            List<UiObject2> uiObjList = Helper.findObjects(By.res("com.yuncheapp.android.pearl:id/title"));
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
        }
    }

    public void minePage() {
        handleDialog();
        String[] textArr = {"我的金币", "今日金币", "今日阅读", "历史记录"};
        Helper.openPageRandom(textArr);

        Helper.click(Helper.findObject(By.res("com.yuncheapp.android.pearl:id/tv_time").text("领取")));
    }

    public void newsDetailPage() {
        handleDialog();
        Helper.readPage();

    }

    public void smallVideoPage() {
        if (Helper.click(By.res("com.yuncheapp.android.pearl:id/title"))) {
            int step = Helper.getRandomInRange(mMinCycleValue, mMaxCycleValue);
            for (int i = 0; i < step; i++) {
                try {
                    if (!Helper.hasObject(By.textContains("下载")) && !Helper.hasObject(By.textContains("详情"))) {
                        String tempStr = Helper.getText(By.res("com.yuncheapp.android.pearl:id/user_caption"));
                        LogUtils.i("点击观看小视频：" + tempStr);
                        Helper.readVideoLittle();
                        LogUtils.i("观看小视频完毕");

                    }
                    Helper.slideVerticalUp();
                } catch (Exception e) {
                }
            }
        }
        Helper.pressBack();
    }

    public void videoPage() {
        int step = Helper.getRandomInRange(mMinCycleValue, mMaxCycleValue);
        for (int i = 0; i < step; i++) {
            handleDialog();

            List<UiObject2> uiObjList = Helper.findObjects(By.res("com.yuncheapp.android.pearl:id/initpanel_video_length"));
            for (UiObject2 uiObj : uiObjList) {
                try {
                    String tempStr = Helper.getText(uiObj.getParent(), By.res("com.yuncheapp.android.pearl:id/initpanel_title"));
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