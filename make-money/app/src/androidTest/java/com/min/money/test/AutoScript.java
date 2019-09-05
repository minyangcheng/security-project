package com.min.money.test;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPStaticUtils;
import com.min.money.test.script.HuiTouTiao;
import com.min.money.test.script.QuTouTiao;
import com.min.money.test.script.ShuaBao;
import com.min.money.test.script.WeiLiKanKan;
import com.min.money.test.util.Helper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class AutoScript {

    @Before
    public void before() {
        String startTimeStr = Helper.nowTimeStr();
        SPStaticUtils.put("start_time", startTimeStr);
        SPStaticUtils.put("end_time", "");
        LogUtils.i("application start time:" + startTimeStr);
        init();
    }

    @Test
    public void startAutoScript() {
        while (true) {
            BaseAuto.start(new QuTouTiao());
            BaseAuto.start(new WeiLiKanKan());
            BaseAuto.start(new HuiTouTiao());
            BaseAuto.start(new ShuaBao());
            Helper.sleep(5000, 8000);
        }
    }

    @After
    public void after() {
        String finishTimeStr = Helper.nowTimeStr();
        SPStaticUtils.put("end_time", finishTimeStr);

        LogUtils.i("application finish time:" + finishTimeStr);
    }

    private void init() {
        UiDevice uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        Helper.setUiDevice(uiDevice);
    }

}