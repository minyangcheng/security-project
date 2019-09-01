package com.min.money.test.script;

import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject2;

import com.blankj.utilcode.util.LogUtils;
import com.min.money.test.BaseAuto;
import com.min.money.test.util.Helper;
import com.nostra13.universalimageloader.utils.L;

import java.util.ArrayList;
import java.util.List;

public class DongFangTiao extends BaseAuto {

    public DongFangTiao() {
        super("com.songheng.eastnews");
    }

    @Override
    public void operate() {
        for (int i = 0; i < mOperateTimes; i++) {
            if (Helper.click(mDevice, By.text("新闻")) || Helper.waitUiObjAppear(mDevice, By.text("推荐"))) {
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

            if (Helper.click(mDevice, By.text("游戏"))) {
                LogUtils.i("进入游戏页面");
                gamePage();
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
            UiObject2 uiObj = Helper.findObjectInCertainTime(mDevice, By.res("com.songheng.eastnews:id/aoz"));
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
        if (!mDevice.hasObject(By.res("com.songheng.eastnews:id/aee")) && !mDevice.hasObject(By.res("cn.weli.story:id/tv_nav_title\n"))) {
            dataList.add(Helper.click(mDevice, By.res("com.songheng.eastnews:id/vf")));
            dataList.add(Helper.click(mDevice, By.res("com.songheng.eastnews:id/ua")));
            dataList.add(Helper.click(mDevice, By.res("com.songheng.eastnews:id/e4")));
            dataList.add(Helper.click(mDevice, By.res("com.songheng.eastnews:id/fq")));
            dataList.add(Helper.click(mDevice, By.res("com.songheng.eastnews:id/pu")));
            LogUtils.i("当前进行判断");
        }else{
            LogUtils.i("去掉进行判断");
        }
        Helper.recordLogHasDismissDialog(dataList, startTime);
    }

    public void newsPage() {
        int step = Helper.getRandomInRange(mMinCycleValue, mMaxCycleValue);
        for (int i = 0; i < step; i++) {
            removeRedundancyDialog();

            List<UiObject2> uiObjList = mDevice.findObjects(By.res("com.songheng.eastnews:id/asg"));
            for (UiObject2 uiObj : uiObjList) {
                try {
                    String tempStr = Helper.getText(uiObj.getParent().getParent(), By.res("com.songheng.eastnews:id/auu"));
                    if (checkItemHasOperate(tempStr)) {
                        continue;
                    }
                    if (Helper.clickRandom(uiObj, 1f)) {
                        LogUtils.i("点击进入标题页面：" + tempStr);
                        newsDetailPage();
                        mDevice.pressBack();
                        LogUtils.i("点击返回键退出标题页面");
                    }

                } catch (Exception e) {
                }
            }
            Helper.slideVerticalUp(mDevice);
            Helper.clickRandom(mDevice, By.res("com.songheng.eastnews:id/ajr").text("领取"), 0.5f);
        }
    }

    public void taskPage() {
        removeRedundancyDialog();
        Helper.slideVertical(mDevice, 0.5f, 0.3f);
        Helper.readTextShort();
    }

    public void newsDetailPage() {
        removeRedundancyDialog();
        Helper.readPage(mDevice);
    }

    public void minePage() {
        removeRedundancyDialog();
        String[] textArr = {"立即体现", "我的钱包", "邀请好友", "我的消息", "游戏中心"};
        Helper.openPageRandom(mDevice, textArr);
    }

    public void gamePage() {
        removeRedundancyDialog();
        Helper.readTextShort();
    }

    public void videoPage() {
        int step = Helper.getRandomInRange(mMinCycleValue, mMaxCycleValue);
        for (int i = 0; i < step; i++) {
            removeRedundancyDialog();

            List<UiObject2> uiObjList = mDevice.findObjects(By.res("com.songheng.eastnews:id/x7"));
            for (UiObject2 uiObj : uiObjList) {
                try {
                    String tempStr = Helper.getText(uiObj.getParent(), By.res("com.songheng.eastnews:id/auu"));
                    if (checkItemHasOperate(tempStr)) {
                        continue;
                    }
                    if (Helper.clickRandom(uiObj, 0.8f)) {
                        LogUtils.i("点击观看视频：" + tempStr);
                        Helper.readVideoShort();
                        mDevice.pressBack();
                        LogUtils.i("观看视频完毕");
                    }
                } catch (Exception e) {
                }
            }
            Helper.slideVerticalUp(mDevice);
        }
    }

}