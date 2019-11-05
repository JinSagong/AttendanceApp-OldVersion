package com.jin.attendance_archive;

import android.app.Activity;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by JIN on 2/16/2019.
 */

public class AddressSharedPreference {
    public void setAddress(Activity ctx, int index) {
        SharedPreferences.Editor editor = ctx.getSharedPreferences("address", MODE_PRIVATE).edit();
        editor.putInt("index", index);
        editor.commit();
    }

    public int getAddress(Activity ctx) {
        int value = ctx.getSharedPreferences("address", MODE_PRIVATE).getInt("index", 0);

        return value;
    }
}
