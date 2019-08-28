package com.min.money.test.util;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;

import com.blankj.utilcode.util.LogUtils;
import com.min.money.helper.RootCmd;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Utils {

    private static SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 唤醒屏幕
     */
    public static void wakeUpDevice(UiDevice device) throws Exception {
        if (!device.isScreenOn()) {
            device.wakeUp();
        }
        String launcherPackage = device.getLauncherPackageName();
        if (!device.hasObject(By.pkg(launcherPackage).depth(0))) {
            slideVerticalUp(device);
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
    }

    /**
     * 安全的方式打开app
     */
    public static void openAppSafe(UiDevice device, String packageName) {
        while (!device.hasObject(By.pkg(packageName))) {
            Utils.openApp(packageName);
            sleep(5000);
        }
    }

    /**
     * 随机休眠
     */
    public static void sleepRandomSecond(int from) {
        long coreSecond = from * 1000;
        long randomSecond = 1 * 1000;
        sleep(coreSecond + (int) (Math.random() * randomSecond));
    }

    /**
     * 随机休眠
     */
    public static void sleepRandomSecond(int from, int to) {
        long coreSecond = from * 1000;
        long randomSecond = (to - from) * 1000;
        if (randomSecond < 0) {
            randomSecond = 0;
        }
        sleep(coreSecond + (int) (Math.random() * randomSecond));
    }

    /**
     * 判断是否禁止操作时间段 23:00 - 7:00
     */
    public static boolean checkRightTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("H");
        String hourStr = sdf.format(new Date());
        int hour = Integer.parseInt(hourStr);
        if (hour <= 6 || hour == 23) {
            LogUtils.i("非正常时间运行 : ", Utils.nowTimeStr());
            return false;
        } else {
            return true;
        }
    }

    /**
     * 点击
     */
    public static void click(UiDevice device, float x, float y) {
        int width = device.getDisplayWidth();
        int height = device.getDisplayHeight();
        device.click((int) (width * x), (int) (height * y));
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
            UiObject2 uiObj = device.findObject(selector);
            if (uiObj != null) {
                uiObj.click();
                flag = true;
            }
        } catch (Exception e) {
        }
        return flag;
    }

    /**
     * 随机点击
     *
     * @param device
     * @param selector
     * @return
     */
    public static boolean clickRandom(UiDevice device, BySelector selector) {
        boolean flag = false;
        try {
            UiObject2 uiObj = device.findObject(selector);
            if (uiObj != null && Math.random() <= 0.5) {
                uiObj.click();
                flag = true;
            }
        } catch (Exception e) {
        }
        return flag;
    }

    /**
     * 随机点击
     * 0-100%
     *
     * @param uiObj
     * @return
     */
    public static boolean clickRandom(UiObject2 uiObj, float percent) {
        boolean flag = false;
        try {
            if (Math.random() <= percent) {
                uiObj.click();
                flag = true;
            }
        } catch (Exception e) {
        }
        return flag;
    }

    /**
     * 随机点击
     * 50%概率
     *
     * @param uiObj
     * @return
     */
    public static boolean clickRandom(UiObject2 uiObj) {
        return clickRandom(uiObj, 0.5f);
    }

    /**
     * 阅读页面
     *
     * @param device
     */
    public static void readPage(UiDevice device) {
        int step = getRandom(3, 5);
        for (int i = 0; i < step; i++) {
            Utils.slideVertical(device, 0.6f, 0.2f);
            Utils.sleepRandomSecond(2, 4);

            if (device.hasObject(By.desc("展开全文 Link"))) {
                Utils.slideVertical(device, 0.4f, 0.2f);
                Utils.click(device, By.desc("展开全文 Link"));
            }
            if (device.hasObject(By.text("展开查看全文"))) {
                Utils.slideVertical(device, 0.4f, 0.2f);
                Utils.click(device, By.text("展开查看全文"));
            }

            if (device.hasObject(By.res("com.zhangku.qukandian:id/header_information_read_all_btn"))) {
                Utils.slideVertical(device, 0.4f, 0.2f);
                Utils.click(device, By.res("com.zhangku.qukandian:id/header_information_read_all_btn"));
            }
            if (Math.random() < 0.1) {
                Utils.slideVertical(device, 0.6f, 0.8f);
                Utils.sleepRandomSecond(1, 3);
            }
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
        int times = random.nextInt(3);
        for (int i = 0; i <= times; i++) {
            if (Utils.click(device, By.text(pageTexts[random.nextInt(pageTexts.length)]))) {
                Utils.sleepRandomSecond(2, 4);
                device.pressBack();
            }
            Utils.slideVertical(device, 0.3f, 0.2f);
        }
    }

    public static void slideHorizontalRight(UiDevice device) {
        slideHorizontal(device, 0.2f, 0.8f);
    }

    public static void slideHorizontal(UiDevice device, float from, float to) {
        LogUtils.d("operate :  slideHorizontal");
        int width = device.getDisplayWidth();
        int height = device.getDisplayHeight();
        device.swipe((int) (width * from), height / 2, (int) (width * to), height / 2, 100);
    }

    public static void slideVerticalUp(UiDevice device) {
        slideVertical(device, 0.8f, 0.2f);
    }

    public static void slideVertical(UiDevice device, float from, float to) {
        LogUtils.d("operate :  slideVertical");
        int width = device.getDisplayWidth();
        int height = device.getDisplayHeight();
        device.swipe(width / 2, (int) (height * from), width / 2, (int) (height * to), 100);
    }

    public static void closeAPP(String packageName) {
        RootCmd.execRootCmdSilent("am force-stop " + packageName);
    }

    public static String getText(UiObject2 parentUiObj, BySelector selector) {
        try {
            UiObject2 textUiObj2 = parentUiObj.findObject(selector);
            return textUiObj2.getText();
        } catch (Exception e) {
        }
        return "";
    }

    public static UiObject2 findObject(UiDevice device, BySelector selector) {
        UiObject2 object = null;
        int timeout = 6000;
        int delay = 1500;
        long time = System.currentTimeMillis();
        while (object == null) {
            object = device.findObject(selector);
            if (object == null) {
                sleep(delay);
                if (System.currentTimeMillis() - timeout > time) {
                    break;
                }
            }
        }
        return object;
    }

    public static List<UiObject2> findObjects(UiDevice device, BySelector selector) {
        List<UiObject2> objects = null;
        int timeout = 6000;
        int delay = 1500;
        long time = System.currentTimeMillis();
        while (objects == null) {
            objects = device.findObjects(selector);
            if (objects == null) {
                sleep(delay);
                if (System.currentTimeMillis() - timeout > time) {
                    break;
                }
            }
        }
        return objects;
    }

    public static int getRandom(int from, int to) {
        Random random = new Random();
        int temp = from + random.nextInt(to - from + 1);
        return temp;
    }

    public static String nowTimeStr() {
        String str = "";
        try {
            str = SDF.format(new Date());
        } catch (Exception e) {
        }
        return str;
    }

    public static long nowTime() {
        long time = 0;
        try {
            time = (new Date()).getTime();
        } catch (Exception e) {
        }
        return time;
    }

    public static void sleep(long million) {
        try {
            Thread.sleep(million);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
