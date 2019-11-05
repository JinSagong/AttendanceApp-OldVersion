package com.jin.attendance_archive;

import android.app.Activity;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by JIN on 1/26/2019.
 */

public class LoginSharedPreference {
    public void setLogin(Activity ctx, String key, String name) {
        SharedPreferences.Editor editor = ctx.getSharedPreferences("login", MODE_PRIVATE).edit();
        editor.putString("key", key);
        editor.putString("name", name);
        editor.commit();
    }

    public void logout(Activity ctx) {
        SharedPreferences.Editor editor = ctx.getSharedPreferences("login", MODE_PRIVATE).edit();
        editor.remove("key");
        editor.remove("name");
        editor.commit();
    }

    public boolean isLogin(Activity ctx) {
        return ctx.getSharedPreferences("login", MODE_PRIVATE).getString("key", "") != "";
    }

    public String[] getValues(Activity ctx) {
        String[] values = new String[2];
        values[0] = ctx.getSharedPreferences("login", MODE_PRIVATE).getString("key", "");
        values[1] = ctx.getSharedPreferences("login", MODE_PRIVATE).getString("name", "");

        return values;
    }
}