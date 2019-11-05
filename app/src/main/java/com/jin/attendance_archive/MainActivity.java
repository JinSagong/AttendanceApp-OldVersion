package com.jin.attendance_archive;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Toast toast_back;
    private long lastTimeBackPressed;
    private long lastTimeButtonPressed;
    private long lastTimeHiddenPressed;

    LinearLayout llo_main;
    TextView tv_info;
    ImageView[] iv_list;
    ImageView iv_fab;
    Toolbar toolbar;
    Intent intent_get, intent;

    LoginSharedPreference sp;

    Client client;
    boolean isDone;
    String[] status;

    String KEY, NAME;
    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toast_back = Toast.makeText(this, "'뒤로' 버튼을 한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT);

        llo_main = (LinearLayout) findViewById(R.id.content_main);
        tv_info = (TextView) findViewById(R.id.main_info);
        iv_list = new ImageView[4];
        iv_list[0] = (ImageView) findViewById(R.id.list1);
        iv_list[1] = (ImageView) findViewById(R.id.list2);
        iv_list[2] = (ImageView) findViewById(R.id.list3);
        iv_list[3] = (ImageView) findViewById(R.id.list4);
        iv_fab = (ImageView) findViewById(R.id.fab);
        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        intent_get = getIntent();

        KEY = intent_get.getStringExtra("key");
        NAME = intent_get.getStringExtra("name");

        setInfo();
        getStatus();

    }

    public void onClick(View view) {
        if (view.getId() == R.id.l_main1) {
            if (System.currentTimeMillis() - lastTimeButtonPressed > 1500) {
                if (!KEY.equals("fpadmin")) {
                    intent = new Intent(this, SelectionActivity.class);
                    intent.putExtra("type", "general");
                    intent.putExtra("key", KEY);
                    intent.putExtra("name", NAME);
                    startActivity(intent);
                    MainActivity.this.finish();
                } else {
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("출석체크")
                            .setMessage("전교인 출석을 관리하는 곳으로\n접근이 제한될 수 있습니다.")
                            .setPositiveButton("확인", null)
                            .show();
                }

                lastTimeButtonPressed = System.currentTimeMillis();
            }

        } else if (view.getId() == R.id.l_main2) {
            if (System.currentTimeMillis() - lastTimeButtonPressed > 1500) {
                if (!KEY.equals("fpadmin")) {
                    intent = new Intent(this, SelectionActivity.class);
                    intent.putExtra("type", "group");
                    intent.putExtra("key", KEY);
                    intent.putExtra("name", NAME);
                    startActivity(intent);
                    MainActivity.this.finish();
                } else {
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("출석체크")
                            .setMessage("전교인 출석을 관리하는 곳으로\n접근이 제한될 수 있습니다.")
                            .setPositiveButton("확인", null)
                            .show();
                }

                lastTimeButtonPressed = System.currentTimeMillis();
            }

        } else if (view.getId() == R.id.l_main3) {
            if (System.currentTimeMillis() - lastTimeButtonPressed > 1500) {
                if (!KEY.equals("fpadmin")) {
                    intent = new Intent(this, SelectionActivity.class);
                    intent.putExtra("type", "bc");
                    intent.putExtra("key", KEY);
                    intent.putExtra("name", NAME);
                    startActivity(intent);
                    MainActivity.this.finish();
                } else {
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("출석체크")
                            .setMessage("전교인 출석을 관리하는 곳으로\n접근이 제한될 수 있습니다.")
                            .setPositiveButton("확인", null)
                            .show();
                }

                lastTimeButtonPressed = System.currentTimeMillis();
            }

        } else if (view.getId() == R.id.l_main4) {
            if (System.currentTimeMillis() - lastTimeButtonPressed > 1500) {
                if (KEY.equals("jin") || KEY.equals("fpadmin") || KEY.equals("bc1") || KEY.equals("bc2") || KEY.equals("bc3") || KEY.equals("bc4") || KEY.equals("bc5") || KEY.equals("bc6") || KEY.equals("bc7") || KEY.equals("bc8") || KEY.equals("bc9") || KEY.equals("bc10") || KEY.equals("bc11") || KEY.equals("bc12")) {
                    intent = new Intent(this, SelectionActivity.class);
                    intent.putExtra("type", "fp");
                    intent.putExtra("key", KEY);
                    intent.putExtra("name", NAME);
                    startActivity(intent);
                    MainActivity.this.finish();
                } else {
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("출석체크")
                            .setMessage("현장전도 출석/열매을 관리하는 곳으로\n접근이 제한될 수 있습니다.")
                            .setPositiveButton("확인", null)
                            .show();
                }

                lastTimeButtonPressed = System.currentTimeMillis();
            }

        } else if (view.getId() == R.id.fab) {
            if (KEY.equals("jin")) {
                if (System.currentTimeMillis() - lastTimeHiddenPressed > 1000) {
                    count = 0;
                }
                if (count < 5) {
                    count++;
                    System.out.println("count: " + count);
                } else {
                    count = 0;
                    intent = new Intent(this, LogsActivity.class);
                    intent.putExtra("key", KEY);
                    intent.putExtra("name", NAME);
                    startActivity(intent);
                    MainActivity.this.finish();
                }

                lastTimeHiddenPressed = System.currentTimeMillis();
            }
        }

    }

    public void setInfo() {
        switch (KEY) {
            case "jin":
                tv_info.setText(NAME + " 계정입니다.\n전체 시스템을 관리하실 수 있습니다.");
                break;
            case "supervisor":
                tv_info.setText(NAME + " 계정입니다.\n전체 출석을 관리하실 수 있습니다.");
                break;
            case "generaladmin":
                tv_info.setText(NAME + " 계정입니다.\n일반남여전도회 출석을 관리하실 수 있습니다.");
                break;
            case "groupadmin":
                tv_info.setText(NAME + " 계정입니다.\n기관 출석을 관리하실 수 있습니다.");
                break;
            case "bcadmin":
                tv_info.setText(NAME + " 계정입니다.\n지교회 출석을 관리하실 수 있습니다.");
                break;
            case "fpadmin":
                tv_info.setText(NAME + " 계정입니다.\n현장전도 출석/열매를 관리하실 수 있습니다.");
                break;
            default:
                tv_info.setText(NAME + " 계정입니다.\n관련부분 출석을 관리하실 수 있습니다.");
                break;
        }
    }

    public void getStatus() {
        isDone = false;
        client = new Client(MainActivity.this, "getStatus", "main");
        client.execute();

        while (!isDone) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
            isDone = client.isDone();
        }

        if (client.isAlive()) {
            status = client.getSTATUS();
            for (int i = 0; i < status.length; i++)
                if (status[i].equals("TRUE")) {
                    iv_list[i].setBackgroundResource(R.drawable.status_true);
                } else if (status[i].equals("ONGOING")) {
                    iv_list[i].setBackgroundResource(R.drawable.status_ongoing);
                } else {
                    iv_list[i].setBackgroundResource(R.drawable.status_false);
                }
        } else {
            intent = new Intent(this, NotfoundActivity.class);
            startActivity(intent);
            MainActivity.this.finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.refresh_main) {
            if (System.currentTimeMillis() - lastTimeButtonPressed > 1500) {
                getStatus();
                Snackbar.make(llo_main, "새로고침 되었습니다.", Snackbar.LENGTH_SHORT).show();

                lastTimeButtonPressed = System.currentTimeMillis();
            }

        } else if (id == R.id.log_out) {
            if (System.currentTimeMillis() - lastTimeButtonPressed > 1500) {
                // dialog box
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("로그아웃")
                        .setMessage("로그아웃 하시겠습니까?")
                        .setNegativeButton("취소", null)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                isDone = false;
                                client = new Client(MainActivity.this, "LogOut", KEY);
                                client.execute();

                                while (!isDone) {
                                    try {
                                        Thread.sleep(100);
                                    } catch (InterruptedException e) {
                                    }
                                    isDone = client.isDone();
                                }

                                if (client.isAlive()) {
                                    sp = new LoginSharedPreference();
                                    sp.logout(MainActivity.this);
                                    intent = new Intent(MainActivity.this, LoginActivity.class);
                                } else {
                                    intent = new Intent(MainActivity.this, NotfoundActivity.class);
                                }
                                startActivity(intent);
                                MainActivity.this.finish();
                            }
                        })
                        .show();

                lastTimeButtonPressed = System.currentTimeMillis();
            }
        }

        return super.onOptionsItemSelected(item);
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
