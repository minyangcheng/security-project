package com.min.money.test.util;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.min.money.helper.RootCmd;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Helper {

    private static int OPERATE_CLICK_MIN_WAIT_TIME = 2500;
    private static int OPERATE_CLICK_MAX_WAIT_TIME = 3500;

    private static int OPERATE_SLIDE_MIN_WAIT_TIME = 1000;
    private static int OPERATE_SLIDE_MAX_WAIT_TIME = 1500;


    private static SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 唤醒屏幕
     */
    public static void wakeUpDevice(UiDevice device) throws Exception {
        device.pressHome();
        if (!device.isScreenOn()) {
            LogUtils.i("operate : wake up screen");
            device.wakeUp();
        }
        String launcherPackage = device.getLauncherPackageName();
        if (!device.hasObject(By.pkg(launcherPackage).depth(0))) {
            slideVertical(device, 0.8f, 0.1f);
            LogUtils.i("operate : unlock mobile");
            sleep(1500);
        }
    }

    /**
     * 打开app
     */
    public static void openApp(String packageName) {
        Context context = InstrumentationRegistry.getContext();
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
        LogUtils.i("operate : open app " + packageName);
    }

    /**
     * 安全的方式打开app
     */
    public static void openAppSafe(UiDevice device, String packageName) {
        while (!device.hasObject(By.pkg(packageName))) {
            openApp(packageName);
            sleep(6000);
        }
    }

    /**
     * 休眠
     */
    public static void sleep(int million) {
        try {
            int temp = million + (int) (Math.random() * 1000);
            Thread.sleep(temp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 随机休眠
     */
    public static void sleep(int minMillion, int maxMillion) {
        long tempMillion = maxMillion - minMillion;
        if (tempMillion < 0) {
            tempMillion = 0;
        }
        sleep(minMillion + (int) (Math.random() * tempMillion));
    }

    /**
     * 判断是否禁止操作时间段 23:00 - 7:00
     */
    public static boolean checkRightTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("H");
        String hourStr = sdf.format(new Date());
        int hour = Integer.parseInt(hourStr);
        if (hour <= 6 || hour == 23) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 点击
     */
    public static boolean click(UiDevice device, float x, float y) {
        int width = device.getDisplayWidth();
        int height = device.getDisplayHeight();
        boolean flag = device.click((int) (width * x), (int) (height * y));
        if (flag) {
            LogUtils.i("operate : click");
            sleep(OPERATE_CLICK_MIN_WAIT_TIME, OPERATE_CLICK_MAX_WAIT_TIME);
        }
        return flag;
    }

    /**
     * 点击
     */
    public static boolean click(UiObject2 uiObj) {
        boolean flag = false;
        try {
            if (uiObj != null) {
                LogUtils.i("operate : click");
                uiObj.click();
                flag = true;
                sleep(OPERATE_CLICK_MIN_WAIT_TIME, OPERATE_CLICK_MAX_WAIT_TIME);
            }
        } catch (Exception e) {
        }
        return flag;
    }

    /**
     * 点击
     *
     * @param device
     * @param selector
     */
    public static boolean click(UiDevice device, BySelector selector) {
        boolean flag = false;
        try {
            if (device.hasObject(selector)) {
                UiObject2 uiObj = device.findObject(selector);
                return click(uiObj);
            }
        } catch (Exception e) {
        }
        return flag;
    }

    /**
     * 随机点击
     * 0-1
     *
     * @param uiObj
     * @return
     */
    public static boolean clickRandom(UiObject2 uiObj, float percent) {
        boolean flag = false;
        try {
            if (Math.random() <= percent) {
                flag = click(uiObj);
            }
        } catch (Exception e) {
        }
        return flag;
    }

    public static boolean clickRandom(UiDevice device, BySelector selector, float percent) {
        boolean flag = false;
        try {
            UiObject2 uiObj = device.findObject(selector);
            return clickRandom(uiObj, percent);
        } catch (Exception e) {
        }
        return flag;
    }

    /**
     * 阅读页面
     *
     * @param device
     */
    public static void readPage(UiDevice device) {
        int times = getRandomInRange(3, 5);
        for (int i = 0; i < times; i++) {
            slideVertical(device, 0.6f, 0.2f);
            click(device, By.text("展开查看全文").clazz(TextView.class));
            click(device, By.text("点击阅读全文").clazz(TextView.class));
            readTextLong();
        }
    }

    /**
     * 随机打开页面
     *
     * @param device
     * @param pageTexts
     */
    public static void openPageRandom(UiDevice device, String[] pageTexts) {
        Random random = new Random();
        int times = random.nextInt(4);
        LogUtils.i("随机打开页面开始，次数：" + times);
        for (int i = 0; i < times; i++) {
            int temp = random.nextInt(pageTexts.length);
            if (click(device, By.text(pageTexts[temp]))) {
                LogUtils.i("点击进入页面：" + pageTexts[temp]);
                readTextShort();
                device.pressBack();
                LogUtils.i("点击返回键退出页面");
            }
            slideVertical(device, 0.3f, 0.2f);
        }
        LogUtils.i("随机打开页面完毕");
    }

    public static void slideHorizontalRight(UiDevice device) {
        slideHorizontal(device, 0.2f, 0.8f);
    }

    public static void slideHorizontal(UiDevice device, float start, float end) {
        LogUtils.i("operate :  slideHorizontal");
        int width = device.getDisplayWidth();
        int height = device.getDisplayHeight();
        device.swipe((int) (width * start), height / 2, (int) (width * end), height / 2, 100);
        sleep(OPERATE_SLIDE_MIN_WAIT_TIME, OPERATE_SLIDE_MAX_WAIT_TIME);
    }

    public static void slideVerticalUp(UiDevice device) {
        slideVertical(device, 0.8f, 0.2f);
    }

    public static void slideVertical(UiDevice device, float start, float end) {
        LogUtils.i("operate :  slideVertical");
        int width = device.getDisplayWidth();
        int height = device.getDisplayHeight();
        device.swipe(width / 2, (int) (height * start), width / 2, (int) (height * end), 100);
        sleep(OPERATE_SLIDE_MIN_WAIT_TIME, OPERATE_SLIDE_MAX_WAIT_TIME);
    }

    public static void closeAPP(String packageName) {
        RootCmd.execRootCmdSilent("am force-stop " + packageName);
        LogUtils.i("operate : close app " + packageName);
    }

    public static String getText(UiObject2 uiObj, BySelector selector) {
        try {
            UiObject2 textUiObj2 = uiObj.findObject(selector);
            return textUiObj2.getText();
        } catch (Exception e) {
        }
        return "";
    }

    public static String getText(UiDevice device, BySelector selector) {
        try {
            UiObject2 textUiObj2 = device.findObject(selector);
            return textUiObj2.getText();
        } catch (Exception e) {
        }
        return "";
    }

    public static String getText(UiObject2 uiObj) {
        try {
            return uiObj.getText();
        } catch (Exception e) {
        }
        return "";
    }

    public static boolean judgeBrotherNode(UiDevice device, BySelector selfSelector, BySelector brotherSelector) {
        try {
            if (device.hasObject(selfSelector)) {
                UiObject2 uiObj = device.findObject(selfSelector);
                if (uiObj.getParent().hasObject(brotherSelector)) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean arrivePageSafe(UiDevice device, BySelector selector) {
        UiObject2 object = null;
        int timeout = 8000;
        int delay = 2000;
        long time = System.currentTimeMillis();
        while (object == null) {
            object = device.findObject(selector);
            if (object == null) {
                if (System.currentTimeMillis() - time > timeout) {
                    break;
                }
                device.pressBack();
                sleep(delay);
            }
        }
        return object != null;
    }

    public static UiObject2 findObjectInCertainTime(UiDevice device, BySelector selector) {
        UiObject2 object = null;
        int timeout = 8000;
        int delay = 2000;
        long time = System.currentTimeMillis();
        while (object == null) {
            object = device.findObject(selector);
            if (object == null) {
                sleep(delay);
                if (System.currentTimeMillis() - time > timeout) {
                    break;
                }
            }
        }
        return object;
    }

    public static boolean waitUiObjAppear(UiDevice device, BySelector selector) {
        return findObjectInCertainTime(device, selector) != null;
    }

    public static int getRandomInRange(int min, int max) {
        Random random = new Random();
        int temp = min + random.nextInt(max - min + 1);
        return 2;
    }

    public static void readTextShort() {
        Helper.sleep(1000, 2000);
    }

    public static void readTextLong() {
        Helper.sleep(2000, 3000);
    }

    public static void readVideoShort() {
        Helper.sleep(20000, 30000);
    }

    public static void readVideoLong() {
        Helper.sleep(20000, 60000);
    }

    public static String nowTimeStr() {
        String str = "";
        try {
            str = SDF.format(new Date());
        } catch (Exception e) {
        }
        return str;
    }

    public static void recordLogHasDismissDialog(List<Boolean> dataList,long startTime) {
        LogUtils.i("opetate : removeRedundancyDialog waste " + (System.currentTimeMillis() - startTime));
        if (dataList != null) {
            for (boolean flag : dataList) {
                if (flag) {
                    LogUtils.i("operate : remove dialog");
                    break;
                }
            }
        }
    }

    public static String contractIntStr(String s) {
        s = s.replaceAll(",", "");
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(s);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

    public static int parseInt(String s) {
        try {
            return Integer.parseInt(s);
        } catch (Exception e) {
        }
        return 0;
    }

}
