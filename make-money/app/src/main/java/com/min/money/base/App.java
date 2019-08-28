package com.min.money.base;

import android.app.Application;
import android.content.Context;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.PathUtils;

import java.io.File;


public class App extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        initUtil();
    }

    public static Context getContext() {
        return context;
    }

    protected void initUtil() {
        LogUtils.getConfig()
                .setLogSwitch(true)// 设置 log 总开关，包括输出到控制台和文件，默认开
                .setConsoleSwitch(Constants.DEBUG)// 设置是否输出到控制台开关，默认开
                .setGlobalTag(Constants.GLOBAL_LOG)// 设置 log 全局标签，默认为空
                .setLogHeadSwitch(false)// 设置 log 头信息开关，默认为开
                .setLog2FileSwitch(true)// 打印 log 时是否存到文件的开关，默认关
                .setDir(new File(PathUtils.getExternalAppCachePath()))// 当自定义路径为空时，写入应用的/cache/log/目录中
                .setFilePrefix("mm")// 当文件前缀为空时，默认为"com.min.common.util"，即写入文件为"com.min.common.util-yyyy-MM-dd.txt"
                .setBorderSwitch(false)// 输出日志是否带边框开关，默认开
                .setSingleTagSwitch(true)// 一条日志仅输出一条，默认开，为美化 AS 3.1 的 Logcat
                .setConsoleFilter(LogUtils.V)// log 的控制台过滤器，和 logcat 过滤器同理，默认 Verbose
                .setFileFilter(LogUtils.I)// log 文件过滤器，和 logcat 过滤器同理，默认 Verbose
                .setStackDeep(1)// log 栈深度，默认为 1
                .setStackOffset(0)// 设置栈偏移，比如二次封装的话就需要设置，默认为 0
                .setSaveDays(5);
    }

}
