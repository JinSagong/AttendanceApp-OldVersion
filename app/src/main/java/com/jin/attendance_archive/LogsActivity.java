package com.jin.attendance_archive;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * Created by JIN on 2/11/2019.
 */

public class LogsActivity extends AppCompatActivity {

    private long lastTimeButtonPressed;

    Toolbar toolbar;

    ScrollView sv_logs;
    LinearLayout llo_logs;
    Intent intent_get, intent;

    String KEY, NAME;

    Client client;
    boolean isDone;
    String[] logs;

    int nLogs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logs);

        toolbar = (Toolbar) findViewById(R.id.toolbar_logs);
        setSupportActionBar(toolbar);

        sv_logs = (ScrollView) findViewById(R.id.content_logs);
        llo_logs = (LinearLayout) findViewById(R.id.logs);
        intent_get = getIntent();

        KEY = intent_get.getStringExtra("key");
        NAME = intent_get.getStringExtra("name");

        nLogs = 0;
        getLogs();
    }

    public void getLogs() {
        isDone = false;
        client = new Client(LogsActivity.this, "getLogs");
        client.execute();

        while (!isDone) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
            isDone = client.isDone();
        }

        if (client.isAlive()) {
            logs = client.getLogs();

            for (int i = 0; i < nLogs; i++) {
                llo_logs.removeViewAt(0);
            }

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            for (String log : logs) {
                TextView tv_log = new TextView(this);
                tv_log.setLayoutParams(params);
                tv_log.setTextColor(getResources().getColor(R.color.BLACK));
                tv_log.setText(log);
                llo_logs.addView(tv_log);
            }

            nLogs = logs.length;

        } else {
            intent = new Intent(this, NotfoundActivity.class);
            startActivity(intent);
            LogsActivity.this.finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_logs, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.refresh_logs) {
            if (System.currentTimeMillis() - lastTimeButtonPressed > 1500) {
                getLogs();
                Snackbar.make(sv_logs, "새로고침 되었습니다.", Snackbar.LENGTH_SHORT).show();

                lastTimeButtonPressed = System.currentTimeMillis();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        intent = new Intent(this, MainActivity.class);
        intent.putExtra("key", KEY);
        intent.putExtra("name", NAME);
        startActivity(intent);
        LogsActivity.this.finish();
    }
}