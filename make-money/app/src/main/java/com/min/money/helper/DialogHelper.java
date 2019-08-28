package com.min.money.helper;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.blankj.utilcode.util.AntiShakeUtils;
import com.blankj.utilcode.util.ConvertUtils;

/**
 * Created by minych on 18-2-27.
 */

public class DialogHelper {

    public static Dialog showConfirmDialog(Context context, String message, DialogInterface.OnClickListener sureListener) {
        return showAlertDialog(context, "提示", message, "取消", "确认", null, sureListener, true);
    }

    public static Dialog showConfirmDialog(Context context, String title, String message, DialogInterface.OnClickListener sureListener) {
        return showAlertDialog(context, title, message, "取消", "确认", null, sureListener, true);
    }

    public static Dialog showTipsDialog(Context context, String message) {
        return showAlertDialog(context, "提示", message, null, "确认", null, null, true);
    }

    public static void showAlertDialog(Context context, String title, String message) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
    }

    public static Dialog showAlertDialog(Context context, String title, String message, String cancel, String sure, DialogInterface.OnClickListener cancelListener, DialogInterface.OnClickListener sureListener, boolean cancelable) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        if (!TextUtils.isEmpty(cancel)) {
            builder.setNegativeButton(cancel, cancelListener);
        }
        if (!TextUtils.isEmpty(sure)) {
            builder.setPositiveButton(sure, sureListener);
        }
        Dialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(cancelable);
        dialog.show();
        return dialog;
    }

    public static Dialog showEditDialog(Context context, String title, OnEditDialogListener listener) {
        return showEditDialog(context, title, null, null, listener);
    }

    public static Dialog showEditDialog(Context context, String title, String value, OnEditDialogListener listener) {
        return showEditDialog(context, title, value, null, listener);
    }

    public static Dialog showEditDialog(Context context, String title, String value, String placeholder,final OnEditDialogListener listener) {
        FrameLayout frameLayout = new FrameLayout(context);
        int padding = ConvertUtils.dp2px(20);
        int topPadding = ConvertUtils.dp2px(10);
        frameLayout.setPadding(padding, topPadding, padding, 0);
        final EditText editText = new EditText(context);
        editText.setTextColor(Color.parseColor("#333333"));
        editText.setTextSize(14);
        editText.setHint(placeholder);
        if (!TextUtils.isEmpty(value)) {
            editText.setText(value);
            editText.setSelection(value.length());
        }
        frameLayout.addView(editText);

        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(title)
                .setView(frameLayout)
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", null);
        final AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!AntiShakeUtils.isValid(v)) {
                    return;
                }
                if (listener != null) {
                    listener.onSureEdit(dialog, editText.getText().toString().trim());
                }
            }
        });
        return dialog;
    }

    public interface OnEditDialogListener {
        void onSureEdit(Dialog dialog, String value);
    }

}
