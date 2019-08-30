package com.min.money.helper;

import android.Manifest;
import android.annotation.SuppressLint;

import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ToastUtils;

import java.util.List;

/**
 * Created by minyangcheng on 2019/4/11.
 */

public class PermissionHelper {

    public static final String[] APP_PERMISSIONS = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE
    };

    public static final String[] CAMERA_PERMISSIONS = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    public static final String[] VIDEO_PERMISSIONS = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO
    };

    public static final String[] AUDIO_PERMISSIONS = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO
    };

    public static final String[] LOCATION_PERMISSIONS = new String[]{
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    public static final String[] CONTACT_PERMISSIONS = new String[]{
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS
    };

    /**
     * 整个app的权限申请
     */
    public static void requestAppPermission(SimplePermissionCallback simplePermissionCallback) {
        requestPermissions(simplePermissionCallback, APP_PERMISSIONS);
    }

    /**
     * 照相机权限检测
     */
    public static void requestCameraPermission(SimplePermissionCallback simplePermissionCallback) {
        requestPermissions(simplePermissionCallback, CAMERA_PERMISSIONS);
    }

    /**
     * 录像权限检测
     */
    public static void requestVideoPermission(SimplePermissionCallback simplePermissionCallback) {
        requestPermissions(simplePermissionCallback, VIDEO_PERMISSIONS);
    }

    /**
     * 录音权限检测
     */
    public static void requestAudioPermission(SimplePermissionCallback simplePermissionCallback) {
        requestPermissions(simplePermissionCallback, AUDIO_PERMISSIONS);
    }

    /**
     * 定位权限申请
     */
    public static void requestLocationPermission(SimplePermissionCallback simplePermissionCallback) {
        requestPermissions(simplePermissionCallback, LOCATION_PERMISSIONS);
    }

    /**
     * 读取通讯录权限
     */
    public static void requestContactPermission(SimplePermissionCallback simplePermissionCallback) {
        requestPermissions(simplePermissionCallback, CONTACT_PERMISSIONS);
    }

    @SuppressLint("WrongConstant")
    public static void requestPermissions(final OnPermissionListener permissionListener, String... permissions) {
        PermissionUtils.permission(permissions)
                .callback(new PermissionUtils.FullCallback() {
                    @Override
                    public void onGranted(List<String> permissionsGranted) {
                        permissionListener.grantSuccess();
                    }

                    @Override
                    public void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied) {
                        permissionListener.grantFail();
                    }
                }).request();
    }

    public static abstract class SimplePermissionCallback implements OnPermissionListener {

        private String tips = "缺少相应的运行权限，请在设置中授权";

        @Override
        public void grantFail() {
            ToastUtils.showLong(tips);
        }

        public void setTips(String tips) {
            this.tips = tips;
        }

    }

    public interface OnPermissionListener {

        void grantSuccess();

        void grantFail();

    }

}
