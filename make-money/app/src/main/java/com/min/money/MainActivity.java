package com.min.money;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPStaticUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.min.money.helper.DialogHelper;
import com.min.money.helper.FileUtil;
import com.min.money.helper.PermissionHelper;
import com.min.money.helper.RootCmd;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PermissionHelper.requestAppPermission(new PermissionHelper.SimplePermissionCallback() {
            @Override
            public void grantSuccess() {

            }
        });
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_install_qutoutiao)
    void installQuTouTiao() {
        ToastUtils.showShort("准备安装中....");
        File destFile = new File(this.getExternalCacheDir(), "qutoutiao.apk");
        FileUtil.copyAssetsFileToStorage(this, "qutoutiao.apk", destFile.getAbsolutePath());
        LogUtils.d("copy qutoutiao apk success : " + destFile.getAbsolutePath());
        AppUtils.installApp(destFile.getAbsolutePath());
    }

    @OnClick(R.id.btn_install_weilikankan)
    void installWeiLiKanKan() {
        ToastUtils.showShort("准备安装中....");
        File destFile = new File(this.getExternalCacheDir(), "weilikankan.apk");
        FileUtil.copyAssetsFileToStorage(this, "weilikankan.apk", destFile.getAbsolutePath());
        LogUtils.d("copy qutoutiao apk success :" + destFile.getAbsolutePath());
        AppUtils.installApp(destFile.getAbsolutePath());
    }

    @OnClick(R.id.btn_install_huitoutiao)
    void installHuiTouTiao() {
        ToastUtils.showShort("准备安装中....");
        File destFile = new File(this.getExternalCacheDir(), "huitoutiao.apk");
        FileUtil.copyAssetsFileToStorage(this, "huitoutiao.apk", destFile.getAbsolutePath());
        LogUtils.d("copy huitoutiao apk success :" + destFile.getAbsolutePath());
        AppUtils.installApp(destFile.getAbsolutePath());
    }

    @OnClick(R.id.btn_install_shuabao)
    void installDongFangTouTiao() {
        ToastUtils.showShort("准备安装中....");
        File destFile = new File(this.getExternalCacheDir(), "shuabao.apk");
        FileUtil.copyAssetsFileToStorage(this, "shuabao.apk", destFile.getAbsolutePath());
        LogUtils.d("copy shuabao apk success :" + destFile.getAbsolutePath());
        AppUtils.installApp(destFile.getAbsolutePath());
    }

    @OnClick(R.id.btn_install_auto)
    void installAuto() {
        ToastUtils.showShort("准备安装中....");
        File destFile = new File(this.getExternalCacheDir(), "auto.apk");
        FileUtil.copyAssetsFileToStorage(this, "auto.apk", destFile.getAbsolutePath());
        LogUtils.d("copy auto apk success : " + destFile.getAbsolutePath());
        AppUtils.installApp(destFile.getAbsolutePath());
    }

    @OnClick(R.id.btn_start)
    void startAuto() {
        if (RootCmd.haveRoot()) {
            RootCmd.execRootCmd("am instrument -w -r -e debug false -e class com.min.money.test.AutoScript com.min.money.test/android.support.test.runner.AndroidJUnitRunner");
        } else {
            ToastUtils.showShort("由于启动自动化脚本，需要root权限，请下载king root应用软件进行root手机操作");
        }
    }

    @OnClick(R.id.btn_query_run_time)
    void queryRunTime() {
        String startTime = SPStaticUtils.getString("start_time");
        String endTime = SPStaticUtils.getString("end_time");
        if (!TextUtils.isEmpty(startTime)) {
            String s = "启动时间: " + startTime + "\n\n";
            s += "结束时间: " + endTime + "\n\n";
            DialogHelper.showAlertDialog(this, "上次启动情况", s);
        } else {
            ToastUtils.showShort("暂无数据....");
        }
    }

    @OnClick(R.id.btn_help)
    void help() {
        DialogHelper.showAlertDialog(this, "帮助", this.getString(R.string.more_help));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
