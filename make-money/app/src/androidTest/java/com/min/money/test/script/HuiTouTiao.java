package com.min.money.test.script;

import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject2;

import com.blankj.utilcode.util.LogUtils;
import com.min.money.test.BaseAuto;
import com.min.money.test.util.Helper;

import java.util.ArrayList;
import java.util.List;

public class HuiTouTiao extends BaseAuto {

    public HuiTouTiao() {
        super("com.cashtoutiao");
    }

    @Override
    public void operate() {
        for (int i = 0; i < mOperateTimes; i++) {
            if (Helper.click(By.text("头条"))) {
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
            if (Helper.click(By.text("任务中心"))) {
                LogUtils.i("进入任务中心页面");
                taskPage();
            } else {
                Helper.pressBack();
            }
            if (Helper.click(By.text("小视频"))) {
                LogUtils.i("进入小视频页面");
                smallVideoPage();
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
            if (Helper.click(By.text("兑换提现"))) {
                UiObject2 uiObj = Helper.findObjectInCertainTime(By.res("com.cashtoutiao:id/tv_tips_gold_coin"));
                if (uiObj != null) {
                    String coinStr = Helper.getText(uiObj);
                    coinStr = Helper.contractIntStr(coinStr);
                    LogUtils.i("当前金币为：" + coinStr);
                    Helper.pressBack();
                    return Helper.parseInt(coinStr);
                }
            }
        }
        return 0;
    }

    @Override
    protected void handleDialog() {
        long startTime = System.currentTimeMillis();
        List<Boolean> dataList = new ArrayList<>();
        if (!Helper.hasObject(By.res("com.cashtoutiao:id/tabs").depth(5)) && !Helper.hasObject(By.res("com.cashtoutiao:id/iv_back"))) {
            dataList.add(Helper.click(By.res("com.cashtoutiao:id/iv_close")));
            dataList.add(Helper.click(By.res("com.cashtoutiao:id/img_close")));
            dataList.add(Helper.click(By.res("com.cashtoutiao:id/tv_left").text("忽略")));
            dataList.add(Helper.click(By.res("com.cashtoutiao:id/tv_know").text("我知道了")));
            dataList.add(Helper.click(By.res("com.cashtoutiao:id/iv_btn_cancel")));
            dataList.add(Helper.click(By.res("com.cashtoutiao:id/close_update_btn")));
        }
        Helper.recordLogHasDismissDialog(dataList, startTime);
    }

    public void newsPage() {
        handleDialog();
        if (Helper.click(By.res("com.cashtoutiao:id/count_down_tv").text("点击领取"))) {
            handleDialog();
        }
        Helper.slideVerticalUp();
        List<UiObject2> uiObjList = Helper.findObjects(By.res("com.cashtoutiao:id/tv_timeline"));
        for (UiObject2 uiObj : uiObjList) {
            try {
                String tempStr = Helper.getText(Helper.findObjectInParent(uiObj, By.res("com.cashtoutiao:id/tv_title"), 2));
                if (checkItemHasOperate(tempStr)) {
                    continue;
                }
                if (Helper.clickRandom(uiObj, 1f)) {
                    LogUtils.i("点击进入标题页面：" + tempStr);
                    newsDetailPage();
                    Helper.pressBack();
                    LogUtils.i("点击返回键退出标题页面");
                }
            } catch (Exception e) {
            }
        }
    }

    public void minePage() {
        handleDialog();
        String[] textArr = {"今日收益(金币)", "我的金币", "兑换提现", "我的订单", "收益明细"};
        Helper.openPageRandom(textArr);
    }

    public void newsDetailPage() {
        handleDialog();
        Helper.readPage();
    }

    public void taskPage() {
        handleDialog();
        if (Helper.click(By.res("com.cashtoutiao:id/sign_btn_container"))) {
            handleDialog();
            Helper.pressBack();
        }
    }

    public void videoPage() {
        handleDialog();
        Helper.slideVerticalUp();
        List<UiObject2> uiObjList = Helper.findObjects(By.res("com.cashtoutiao:id/tv_list_video_duration"));
        for (UiObject2 uiObj : uiObjList) {
            try {
                String tempStr = Helper.getText(Helper.findObjectInParent(uiObj, By.res("com.cashtoutiao:id/title"), 1));
                if (checkItemHasOperate(tempStr)) {
                    continue;
                }
                if (Helper.clickRandom(uiObj, 1f)) {
                    LogUtils.i("点击观看视频：" + tempStr);
                    Helper.readVideoShort();
                    Helper.pressBack();
                    LogUtils.i("观看视频完毕");
                }
            } catch (Exception e) {
            }
        }
    }

    public void smallVideoPage() {
        handleDialog();
        if (Helper.click(By.res("com.cashtoutiao:id/rl_bg").hasDescendant(By.res("com.cashtoutiao:id/iv_like")))) {
            int step = Helper.getRandomInRange(mMinCycleValue + 50, mMaxCycleValue + 50);
            for (int i = 0; i < step; i++) {
                try {
                    if (!Helper.hasObject(By.textContains("下载")) && !Helper.hasObject(By.textContains("详情"))) {
                        String tempStr = Helper.getText(By.res("com.cashtoutiao:id/tv_introduce"));
                        LogUtils.i("点击观看视频：" + tempStr);
                        Helper.readVideoLittle();
                        if (Helper.clickRandom(By.res("com.cashtoutiao:id/ll_like"), 0.1f)) {
                            LogUtils.i("点赞...");
                        }
                        LogUtils.i("观看视频完毕");

                    }
                    Helper.slideVerticalUp();
                } catch (Exception e) {
                }
            }
        }
        Helper.pressBack();
    }
}