package com.min.money.test.script;

import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject2;

import com.blankj.utilcode.util.LogUtils;
import com.min.money.test.BaseAuto;
import com.min.money.test.util.Helper;

import java.util.ArrayList;
import java.util.List;

public class ZhongQingKanDian extends BaseAuto {

    public ZhongQingKanDian() {
        super("cn.youth.news");
    }

    @Override
    public void operate() {
        for (int i = 0; i < mOperateTimes; i++) {
            if (Helper.click(By.res("cn.youth.news:id/tv_home_tab"))) {
                LogUtils.i("进入首页页面");
                newsPage();
            } else {
                Helper.pressBack();
            }
            if (Helper.click(By.res("cn.youth.news:id/tv_user_tab"))) {
                LogUtils.i("进入我的页面");
                minePage();
            } else {
                Helper.pressBack();
            }
            if (Helper.click(By.res("cn.youth.news:id/tv_find_tab"))) {
                LogUtils.i("进入榜单页面");
                rankPage();
            } else {
                Helper.pressBack();
            }
            if (Helper.click(By.res("cn.youth.news:id/tv_video_tab"))) {
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
            UiObject2 uiObj = Helper.findObjectInCertainTime(By.res("cn.youth.news:id/tv_today_douzi"));
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
        dataList.add(Helper.click(By.res("cn.youth.news:id/tv_pass")));
        dataList.add(Helper.click(By.res("cn.youth.news:id/iv_close")));
        Helper.recordLogHasDismissDialog(dataList, startTime);
    }

    public void newsPage() {
        int step = Helper.getRandomInRange(10, 20);
        for (int i = 0; i < step; i++) {
            handleDialog();

            List<UiObject2> uiObjList = Helper.findObjects(By.res("cn.youth.news:id/tv_invite_time"));
            for (UiObject2 uiObj : uiObjList) {
                try {
                    String tempStr = Helper.getText(uiObj.getParent(), By.res("cn.youth.news:id/tv_account_name"));
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
            Helper.click(By.res("cn.youth.news:id/tvTimeSatus").text("领取"));
        }
    }

    public void minePage() {
        handleDialog();
        String[] textArr = {"我的青豆", "提现兑换", "我的钱包", "今日青豆"};
        Helper.openPageRandom(textArr);
    }

    public void newsDetailPage() {
        handleDialog();
        Helper.readPage();
    }

    public void rankPage() {
        handleDialog();
        Helper.slideVertical(0.4f, 0.2f);
        Helper.readTextShort();
        Helper.slideVertical(0.4f, 0.2f);
    }

    public void videoPage() {
        int step = Helper.getRandomInRange(10, 20);
        for (int i = 0; i < step; i++) {
            handleDialog();

            List<UiObject2> uiObjList = Helper.findObjects(By.res("cn.youth.news:id/tv_video_time"));
            for (UiObject2 uiObj : uiObjList) {
                try {
                    String tempStr = Helper.getText(uiObj.getParent(), By.res("cn.youth.news:id/tv_video_title"));
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