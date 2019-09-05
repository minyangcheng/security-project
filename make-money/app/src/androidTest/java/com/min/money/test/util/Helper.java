package com.min.money.test.util;

import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.Until;
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

    private static UiDevice mDevice;

    public static void setUiDevice(UiDevice device) {
        mDevice = device;
    }

    /**
     * 唤醒屏幕
     */
    public static void wakeUpDevice() {
        brightScreen();
        mDevice.pressHome();
        String launcherPackage = mDevice.getLauncherPackageName();
        if (!mDevice.hasObject(By.pkg(launcherPackage).depth(0))) {
            slideVertical(0.8f, 0.1f);//上滑解锁
            sleep(1500);
            LogUtils.i("operate : unlock mobile");
        }
        mDevice.pressHome();
    }

    public static void brightScreen() {
        try{
            if(mDevice.isScreenOn()){
                mDevice.wakeUp();
                sleep(1000);
            }
        }catch (Exception e){
        }
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        boolean screenOn = pm.isScreenOn();
        if (!screenOn) {
            PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "bright");
            wl.acquire(10000);
            wl.release();
            sleep(1000);
            LogUtils.i("operate : wake up screen");
        }
    }

    private static void openApp(String packageName) {
        Context context = InstrumentationRegistry.getContext();
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    /**
     * 安全的方式打开app
     */
    public static void openAppSafe(String packageName) {
        while (!mDevice.hasObject(By.pkg(packageName).depth(0))) {
            openApp(packageName);
            sleep(10000);
            LogUtils.i("operate : open app " + packageName);
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

    public static boolean click(float x, float y) {
        int width = mDevice.getDisplayWidth();
        int height = mDevice.getDisplayHeight();
        boolean flag = mDevice.click((int) (width * x), (int) (height * y));
        if (flag) {
            sleep(OPERATE_CLICK_MIN_WAIT_TIME, OPERATE_CLICK_MAX_WAIT_TIME);
            LogUtils.i("operate : click");
        }
        return flag;
    }

    public static boolean click(UiObject2 uiObj) {
        try {
            if (uiObj != null) {
                uiObj.click();
                sleep(OPERATE_CLICK_MIN_WAIT_TIME, OPERATE_CLICK_MAX_WAIT_TIME);
                LogUtils.i("operate : click");
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    public static boolean click(BySelector selector) {
        UiObject2 uiObj = findObject(selector);
        return click(uiObj);
    }

    public static boolean clickRandom(UiObject2 uiObj, float percent) {
        return Math.random() <= percent && click(uiObj);
    }

    public static boolean clickRandom(BySelector selector, float percent) {
        UiObject2 uiObj = findObject(selector);
        return clickRandom(uiObj, percent);
    }

    public static UiObject2 findObject(BySelector selector) {
        long startTime = System.currentTimeMillis();
        UiObject2 uiObj = mDevice.findObject(selector);
//        LogUtils.i("operate : findObject " + selector.toString() + ", waste " + (System.currentTimeMillis() - startTime) + ", obj " + uiObj);
        brightScreen();
        return uiObj;
    }

    public static List<UiObject2> findObjects(BySelector selector) {
        long startTime = System.currentTimeMillis();
        List<UiObject2> uiObjs = mDevice.findObjects(selector);
//        LogUtils.i("operate : findObjects " + selector.toString() + ", waste " + (System.currentTimeMillis() - startTime) + ", obj " + uiObjs);
        brightScreen();
        return uiObjs;
    }

    public static UiObject2 findObjectInParent(UiObject2 uiObj, BySelector selector, int generationNum) {
        long startTime = System.currentTimeMillis();
        while (generationNum > 0) {
            uiObj = uiObj.getParent();
            generationNum--;
        }
        UiObject2 destUiObj = uiObj.findObject(selector);
//        LogUtils.i("operate : findObjectInParent " + selector.toString() + ", waste " + (System.currentTimeMillis() - startTime) + ", obj " + destUiObj);
        brightScreen();
        return destUiObj;
    }

    public static boolean hasObject(BySelector selector) {
        long startTime = System.currentTimeMillis();
        boolean flag = mDevice.hasObject(selector);
        LogUtils.i("operate : hasObject " + selector.toString() + ", waste " + (System.currentTimeMillis() - startTime) + ", result " + flag);
        brightScreen();
        return flag;
    }

    public static void slideHorizontalRight() {
        slideHorizontal(0.2f, 0.8f);
    }

    public static void slideHorizontal(float start, float end) {
        int width = mDevice.getDisplayWidth();
        int height = mDevice.getDisplayHeight();
        mDevice.swipe((int) (width * start), height / 2, (int) (width * end), height / 2, 100);
        sleep(OPERATE_SLIDE_MIN_WAIT_TIME, OPERATE_SLIDE_MAX_WAIT_TIME);
        LogUtils.i("operate :  slideHorizontal");
    }

    public static void slideVerticalUp() {
        slideVertical(0.8f, 0.2f);
    }

    public static void slideVertical(float start, float end) {
        int width = mDevice.getDisplayWidth();
        int height = mDevice.getDisplayHeight();
        mDevice.swipe(width / 2, (int) (height * start), width / 2, (int) (height * end), 100);
        sleep(OPERATE_SLIDE_MIN_WAIT_TIME, OPERATE_SLIDE_MAX_WAIT_TIME);
        LogUtils.i("operate :  slideVertical");
    }

    public static void closeApp(String packageName) {
        RootCmd.execRootCmdSilent("am force-stop " + packageName);
        LogUtils.i("operate : close app " + packageName);
    }

    public static String getText(UiObject2 uiObj, BySelector selector) {
        UiObject2 textUiObj = uiObj.findObject(selector);
        return getText(textUiObj);
    }

    public static String getText(BySelector selector) {
        UiObject2 uiObj = mDevice.findObject(selector);
        return getText(uiObj);
    }

    public static String getText(UiObject2 uiObj) {
        try {
            if (uiObj != null) {
                return uiObj.getText();
            }
        } catch (Exception e) {
        }
        return "";
    }

    public static UiObject2 findObjectInCertainTime(BySelector selector) {
        return mDevice.wait(Until.findObject(selector), 8000);
    }

    public static boolean waitUiObjAppear(BySelector selector) {
        return findObjectInCertainTime(selector) != null;
    }

    public static boolean pressBack() {
        return mDevice.pressBack();
    }

    public static int getRandomInRange(int min, int max) {
        Random random = new Random();
        int temp = min + random.nextInt(max - min + 1);
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

    public static void readPage() {
        int times = getRandomInRange(3, 5);
        for (int i = 0; i < times; i++) {
            slideVertical(0.6f, 0.2f);
            click(By.text("展开查看全文").clazz(TextView.class));
            click(By.text("点击阅读全文").clazz(TextView.class));
            readTextLong();
        }
    }

    public static void openPageRandom(String[] pageTexts) {
        Random random = new Random();
        int times = random.nextInt(4);
        for (int i = 0; i < times; i++) {
            int temp = random.nextInt(pageTexts.length);
            if (click(By.text(pageTexts[temp]))) {
                LogUtils.i("点击进入页面：" + pageTexts[temp]);
                readTextShort();
                mDevice.pressBack();
                LogUtils.i("点击返回键退出页面");
            }
            slideVertical(0.3f, 0.2f);
        }
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

    public static void recordLogHasDismissDialog(List<Boolean> dataList, long startTime) {
        if (dataList != null) {
            for (boolean flag : dataList) {
                if (flag) {
                    LogUtils.i("operate : handle dialog success waste time:" + (System.currentTimeMillis() - startTime));
                    return;
                }
            }
        }
        LogUtils.i("operate : handle dialog nothing waste " + (System.currentTimeMillis() - startTime));
    }

}
