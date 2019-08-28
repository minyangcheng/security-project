package com.min.money.test;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPStaticUtils;
import com.min.money.test.script.QuTouTiao;
import com.min.money.test.util.Utils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class AutoScript {

    @Before
    public void before() {
        String startTimeStr = Utils.nowTimeStr();
        SPStaticUtils.put("start_time", startTimeStr);
        SPStaticUtils.put("end_time", "");

        LogUtils.i("application start time : " + startTimeStr);
    }

    @Test
    public void startAutoScript() {
//        while (true) {
//            try {
//                LogUtils.d("运行");
//                once();
//                Utils.sleepRandomSecond(10, 20);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
        once();
    }

    public void once() {
        UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        BaseAuto auto = new QuTouTiao();
        auto.run();
        device.pressHome();
        Utils.sleepRandomSecond(3, 6);

//        try {
//            BaseAuto auto = null;
//            check();
//            auto = new QuTouTiao();
//            auto.run();
//            mDevice.pressHome();
//            Utils.sleepRandomSecond(3, 6);
//
//            check();
//            auto = new DongFangTiao();
//            auto.run();
//            mDevice.pressHome();
//            Utils.sleepRandomSecond(3, 6);
//
//            check();
//            auto = new WeiLiKanKan();
//            auto.run();
//            mDevice.pressHome();
//            Utils.sleepRandomSecond(3, 6);
//
//            check();
//            auto = new QuKanDian();
//            auto.run();
//            mDevice.pressHome();
//            Utils.sleepRandomSecond(3, 6);
//
//            check();
//            auto = new ZhongQingKanDian();
//            auto.run();
//            mDevice.pressHome();
//            Utils.sleepRandomSecond(3, 6);
//
//            check();
//            auto = new YueTouTiao();
//            auto.run();
//            mDevice.pressHome();
//            Utils.sleepRandomSecond(3, 6);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    public void check() {
        while (!Utils.checkRightTime()) {
            Utils.sleepRandomSecond(60);
        }
    }

    @After
    public void after() {
        String finishTimeStr = Utils.nowTimeStr();
        SPStaticUtils.put("end_time", finishTimeStr);

        LogUtils.i("application finish time : "+ finishTimeStr);
    }

}