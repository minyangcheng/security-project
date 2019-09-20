package com.min.money.test.script;

import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject2;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.min.money.test.BaseAuto;
import com.min.money.test.util.Helper;

import java.util.ArrayList;
import java.util.List;

public class JuKanDian extends BaseAuto {

    public JuKanDian() {
        super("com.xiangzi.jukandian");
    }

    @Override
    public void operate() {
        for (int i = 0; i < mOperateTimes; i++) {
            if (Helper.click(By.res("com.xiangzi.jukandian:id/tv_tab1"))) {
                LogUtils.i("进入看点页面");
                newsPage();
            } else {
                Helper.pressBack();
            }
            if (Helper.click(By.res("com.xiangzi.jukandian:id/tv_tab4"))) {
                LogUtils.i("进入我的页面");
                minePage();
            } else {
                Helper.pressBack();
            }
            if (Helper.click(By.res("com.xiangzi.jukandian:id/tv_tab3"))) {
                LogUtils.i("进入任务中心页面");
                taskPage();
            } else {
                Helper.pressBack();
            }
            if (Helper.click(By.res("com.xiangzi.jukandian:id/tv_tab2"))) {
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
            UiObject2 uiObj = Helper.findObjectInCertainTime(By.res("com.xiangzi.jukandian:id/goldValue"));
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
        if (!Helper.hasObject(By.res("com.xiangzi.jukandian:id/main_tab_left"))
                && !Helper.hasObject(By.res("com.xiangzi.jukandian:id/tv_tool_bar_menu1_text"))
                && !Helper.hasObject(By.res("com.xiangzi.jukandian:id/v2_video_detail_bottom_comment_finish"))) {
            dataList.add(Helper.click(By.res("com.xiangzi.jukandian:id/cancel_quit")));
            dataList.add(Helper.click(By.res("com.xiangzi.jukandian:id/image_user_task_pop_close")));
            dataList.add(Helper.click(By.res("com.xiangzi.jukandian:id/dismisstv")));
            dataList.add(Helper.click(By.res("com.xiangzi.jukandian:id/mine_starlert_close")));
            dataList.add(Helper.click(By.res("com.xiangzi.jukandian:id/dialog_close")));
            dataList.add(Helper.click(By.res("com.xiangzi.jukandian:id/iv_cancel")));
            dataList.add(Helper.click(By.res("com.xiangzi.jukandian:id/v2_sign_close_button")));
            dataList.add(Helper.click(By.res("com.xiangzi.jukandian:id/iv_close")));
            dataList.add(Helper.click(By.res("com.xiangzi.jukandian:id/close_img_layout")));
            dataList.add(Helper.click(By.res("com.xiangzi.jukandian:id/lingqu_dismiss")));
        }

        Helper.recordLogHasDismissDialog(dataList, startTime);
    }

    public void newsPage() {
        int step = Helper.getRandomInRange(mMinCycleValue, mMaxCycleValue);
        for (int i = 0; i < step; i++) {
            handleDialog();

            List<UiObject2> uiObjList = Helper.findObjects(By.res("com.xiangzi.jukandian:id/item_artical_three_title_tv"));
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
            Helper.click(By.res("com.xiangzi.jukandian:id/icon_home_left_timer_lq"));
        }
    }

    public void minePage() {
        handleDialog();
        String[] textArr = {"今日金币", "零钱（元）", "累计（元）", "钱包/提现", "消息中心"};
        Helper.openPageRandom(textArr);
    }

    public void newsDetailPage() {
        handleDialog();
        Helper.readPage();
        handleDialog();
    }

    public void taskPage() {
        handleDialog();
        Helper.click(560f / ScreenUtils.getScreenWidth(), 290f / ScreenUtils.getScreenHeight());
        Helper.click(560f / ScreenUtils.getScreenWidth(), 290f / ScreenUtils.getScreenHeight());
        Helper.readTextShort();
        Helper.slideVertical(0.4f, 0.2f);
    }

    public void videoPage() {
        int step = Helper.getRandomInRange(mMinCycleValue, mMaxCycleValue);
        for (int i = 0; i < step; i++) {
            handleDialog();

            List<UiObject2> uiObjList = Helper.findObjects(By.res("com.xiangzi.jukandian:id/item_video_play_time"));
            for (UiObject2 uiObj : uiObjList) {
                try {
                    String tempStr = Helper.getText(uiObj.getParent(), By.res("com.xiangzi.jukandian:id/item_video_title"));
                    if (checkItemHasOperate(tempStr)) {
                        continue;
                    }
                    if (Helper.clickRandom(uiObj, 0.8f)) {
                        LogUtils.i("点击观看视频：" + tempStr);
                        Helper.readVideoShort();
                        handleDialog();
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