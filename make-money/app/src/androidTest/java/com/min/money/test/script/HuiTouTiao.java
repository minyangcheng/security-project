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
        this.mMinCycleValue = 2;
        this.mMaxCycleValue = 3;
        mDevice.setCompressedLayoutHeirarchy(true);
    }

    @Override
    public void operate() {
        for (int i = 0; i < mOperateTimes; i++) {
            if (Helper.click(mDevice, By.text("头条"))) {
                LogUtils.i("进入头条页面");
                newsPage();
            }
            if (Helper.click(mDevice, By.text("我的"))) {
                LogUtils.i("进入我的页面");
                minePage();
            }
            if (Helper.click(mDevice, By.text("任务中心"))) {
                LogUtils.i("进入任务中心页面");
                taskPage();
            }
            if (Helper.click(mDevice, By.text("视频"))) {
                LogUtils.i("进入视频页面");
                videoPage();
            }
            if (Helper.click(mDevice, By.text("小视频"))) {
                LogUtils.i("进入视频页面");
                smallVideoPage();
            }
        }
    }

    @Override
    protected int recordCoin() {
        if (Helper.click(mDevice, By.text("我的"))) {
            LogUtils.i("进入我的页面查看当前金币");
            handleDialog();
            if (Helper.click(mDevice, By.text("兑换提现"))) {
                UiObject2 uiObj = Helper.findObjectInCertainTime(mDevice, By.res("com.cashtoutiao:id/tv_tips_gold_coin"));
                if (uiObj != null) {
                    String coinStr = Helper.getText(uiObj);
                    coinStr = Helper.contractIntStr(coinStr);
                    LogUtils.i("当前金币为：" + coinStr);
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
        if (!mDevice.hasObject(By.res("com.cashtoutiao:id/tabs")) || !mDevice.hasObject(By.res("com.cashtoutiao:id/iv_back"))) {
            LogUtils.i("进入判断");
            dataList.add(Helper.click(mDevice, By.res("com.cashtoutiao:id/iv_close")));
            dataList.add(Helper.click(mDevice, By.res("com.cashtoutiao:id/img_close")));
            dataList.add(Helper.click(mDevice, By.res("com.cashtoutiao:id/tv_left").text("忽略")));
            dataList.add(Helper.click(mDevice, By.res("com.cashtoutiao:id/tv_know").text("我知道了")));
            dataList.add(Helper.click(mDevice, By.res("com.cashtoutiao:id/iv_btn_cancel")));
        } else {
            LogUtils.i("未进入判断");
        }
        Helper.recordLogHasDismissDialog(dataList, startTime);
    }

    public void newsPage() {
        int step = Helper.getRandomInRange(mMinCycleValue, mMaxCycleValue);
        for (int i = 0; i < step; i++) {
            handleDialog();

            List<UiObject2> uiObjList = mDevice.findObjects(By.res("com.cashtoutiao:id/tv_timeline"));
            for (UiObject2 uiObj : uiObjList) {
                try {
                    String tempStr = Helper.getText(uiObj.getParent().getParent(), By.res("com.cashtoutiao:id/tv_title"));
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
            Helper.clickRandom(mDevice, By.res("com.cashtoutiao:id/count_down_tv").text("点击领取"), 0.5f);
        }
    }

    public void minePage() {
        handleDialog();
        String[] textArr = {"今日收益(金币)", "我的金币", "兑换提现", "我的订单", "收益明细"};
        Helper.openPageRandom(mDevice, textArr);
    }

    public void newsDetailPage() {
        handleDialog();
        Helper.readPage(mDevice);
    }

    public void taskPage() {
        handleDialog();
        if (Helper.click(mDevice, By.res("com.cashtoutiao:id/sign_btn_container"))) {
            handleDialog();
            mDevice.pressBack();
        }
    }

    public void videoPage() {
        int step = Helper.getRandomInRange(mMinCycleValue, mMaxCycleValue);
        for (int i = 0; i < step; i++) {
            handleDialog();

            List<UiObject2> uiObjList = mDevice.findObjects(By.res("com.cashtoutiao:id/tv_list_video_duration"));
            for (UiObject2 uiObj : uiObjList) {
                try {
                    String tempStr = Helper.getText(uiObj.getParent(), By.res("com.cashtoutiao:id/title"));
                    if (checkItemHasOperate(tempStr)) {
                        continue;
                    }
                    if (Helper.clickRandom(uiObj, 1f)) {
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

    public void smallVideoPage() {
        handleDialog();
        if (Helper.click(mDevice, By.res("com.cashtoutiao:id/tv_like_num"))) {
            int step = Helper.getRandomInRange(mMinCycleValue, mMaxCycleValue);
            for (int i = 0; i < step; i++) {
                String tempStr = Helper.getText(mDevice, By.res("com.cashtoutiao:id/tv_introduce"));
                if (checkItemHasOperate(tempStr)) {
                    Helper.slideVerticalUp(mDevice);
                } else {
                    LogUtils.i("点击观看视频：" + tempStr);
                    Helper.readVideoShort();
                    LogUtils.i("观看视频完毕");
                }
            }
        }
    }

}