package com.jin.attendance_archive;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by JIN on 2/11/2019.
 */

public class NotfoundActivity extends AppCompatActivity {

    private Toast toast_back;
    private long lastTimeBackPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notfound);

        toast_back = Toast.makeText(this, "'뒤로' 버튼을 한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT);
    }

    @Override
    public void onBackPressed() {
        toast_back.show();

        if (System.currentTimeMillis() - lastTimeBackPressed < 1500) {
            toast_back.cancel();
            finish();
        }

        lastTimeBackPressed = System.currentTimeMillis();
    }
}