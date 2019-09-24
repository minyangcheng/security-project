package com.min.money.test.script;

import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject2;
import android.text.TextUtils;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.min.money.test.BaseAuto;
import com.min.money.test.util.Helper;

import java.util.ArrayList;
import java.util.List;

public class MaYiTouTiao extends BaseAuto {

    public MaYiTouTiao() {
        super("com.ldzs.zhangxin");
    }

    @Override
    public void operate() {
        for (int i = 0; i < mOperateTimes; i++) {
            if (Helper.click(By.res("com.ldzs.zhangxin:id/tv_home_tab"))) {
                LogUtils.i("进入看点页面");
                newsPage();
            } else {
                Helper.pressBack();
            }
            if (Helper.click(By.res("com.ldzs.zhangxin:id/tv_user_tab"))) {
                LogUtils.i("进入我的页面");
                minePage();
            } else {
                Helper.pressBack();
            }
            if (Helper.click(By.res("com.ldzs.zhangxin:id/tv_task_tab"))) {
                LogUtils.i("进入任务页面");
                lookPage();
            } else {
                Helper.pressBack();
            }
            if (Helper.click(By.res("com.ldzs.zhangxin:id/tv_find_tab"))) {
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
            UiObject2 uiObj = Helper.findObjectInCertainTime(By.res("com.ldzs.zhangxin:id/user_today_coin"));
            if (uiObj != null) {
                String coinStr = uiObj.getContentDescription();
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
        dataList.add(Helper.click(By.res("com.ldzs.zhangxin:id/iv_close")));
        Helper.recordLogHasDismissDialog(dataList, startTime);
    }

    public void newsPage() {
        int step = Helper.getRandomInRange(2, 2);
        for (int i = 0; i < step; i++) {
            handleDialog();

            List<UiObject2> uiObjList = Helper.findObjects(By.res("com.ldzs.zhangxin:id/tv_read_count"));
            for (UiObject2 uiObj : uiObjList) {
                try {
                    String tempStr = uiObj.getText();
                    if (checkItemHasOperate(tempStr)) {
                        continue;
                    }
                    tempStr = Helper.getText(Helper.findObjectInParent(uiObj, By.clazz("android.widget.TextView"), 2));
                    if (!TextUtils.isEmpty(tempStr) && tempStr.contains("广告")) {
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

            if (Helper.click(By.res("com.ldzs.zhangxin:id/time_period_text").text("领取"))) {
                handleDialog();
            }
        }
    }

    public void minePage() {
        handleDialog();
        String[] textArr = {"金币余额", "今日金币", "提现兑换", "蚂蚁存钱"};
        Helper.openPageRandom(textArr);
    }

    public void newsDetailPage() {
        handleDialog();
        Helper.readPage();

    }

    public void lookPage() {
        handleDialog();
        Helper.click(150f / ScreenUtils.getScreenWidth(), 420f / ScreenUtils.getScreenHeight());
        if (!Helper.hasObject(By.res("com.ldzs.zhangxin:id/tv_home_tab"))) {
            Helper.pressBack();
        }
    }

    public void videoPage() {
        handleDialog();
        List<UiObject2> uiObjList = Helper.findObjects(By.res("com.ldzs.zhangxin:id/cpname"));
        for (UiObject2 uiObj : uiObjList) {
            if (!Helper.getText(uiObj).contains("广告")) {
                if (Helper.click(uiObj)) {
                    break;
                }
            }
        }
        int step = Helper.getRandomInRange(mMinCycleValue, mMaxCycleValue);
        for (int i = 0; i < step; i++) {
            try {
                if (!Helper.hasObject(By.textContains("下载")) && !Helper.hasObject(By.textContains("详情"))) {
                    String tempStr = Helper.getText(By.res("com.ldzs.zhangxin:id/title"));
                    LogUtils.i("点击观看视频：" + tempStr);
                    Helper.readVideoLittle();
                    LogUtils.i("观看视频完毕");
                }
                handleDialog();
                Helper.slideVerticalUp();
            } catch (Exception e) {
            }
        }
        Helper.pressBack();
    }

}