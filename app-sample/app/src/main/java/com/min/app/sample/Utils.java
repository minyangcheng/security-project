package com.min.app.sample;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by minych on 2019-08-07 11:35
 */
public class Utils {

    public static void showToast(Context context,String message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }

}
