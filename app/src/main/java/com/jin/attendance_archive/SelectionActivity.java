package com.jin.attendance_archive;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by JIN on 2/11/2019.
 */

public class SelectionActivity extends AppCompatActivity {

    long lastTimeButtonPressed;

    LinearLayout content_selection;
    Toolbar toolbar;
    RelativeLayout[] rlo_list;
    ImageView[] iv_list;
    Intent intent_get, intent;

    Client client;
    boolean isDone;
    String[] list, status;

    String TYPE, KEY, NAME, CONSIDERATION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);

        content_selection = (LinearLayout) findViewById(R.id.content_selection);
        toolbar = (Toolbar) findViewById(R.id.toolbar_selection);

        intent_get = getIntent();

        TYPE = intent_get.getStringExtra("type");
        KEY = intent_get.getStringExtra("key");
        NAME = intent_get.getStringExtra("name");
        CONSIDERATION = intent_get.getStringExtra("consideration");

        switch (TYPE) {
            case "general":
                toolbar.setTitle("일반남여전도회 출석체크");
                break;
            case "group":
                toolbar.setTitle("기관 출석체크");
                break;
            case "bc":
                toolbar.setTitle("지교회 출석체크");
                break;
            case "fp":
                toolbar.setTitle("현장전도 출석/열매 관리");
                break;
        }
        setSupportActionBar(toolbar);

        init();

        try {
            if (!CONSIDERATION.equals("null")) {
                new AlertDialog.Builder(SelectionActivity.this)
                        .setTitle("현장전도관리")
                        .setMessage(CONSIDERATION)
                        .setPositiveButton("확인", null)
                        .show();
            }
        } catch (NullPointerException e) {
        }
    }

    public void init() {
        isDone = false;
        client = new Client(SelectionActivity.this, "getList", TYPE);
        client.execute();

        while (!isDone) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
            isDone = client.isDone();
        }

        if (client.isAlive()) {
            list = client.getListValues();
            status = client.getSTATUS();

            rlo_list = new RelativeLayout[status.length];
            iv_list = new ImageView[status.length];

            RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);

            RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            params2.addRule(RelativeLayout.CENTER_VERTICAL);
            params2.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            params2.setMargins(getResources().getDimensionPixelSize(R.dimen.default_margin),
                    getResources().getDimensionPixelSize(R.dimen.default_margin),
                    getResources().getDimensionPixelSize(R.dimen.default_margin),
                    getResources().getDimensionPixelSize(R.dimen.default_margin));

            RelativeLayout.LayoutParams params3 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            params3.addRule(RelativeLayout.CENTER_VERTICAL);
            params3.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            params3.setMargins(getResources().getDimensionPixelSize(R.dimen.default_margin),
                    getResources().getDimensionPixelSize(R.dimen.default_margin),
                    getResources().getDimensionPixelSize(R.dimen.default_margin),
                    getResources().getDimensionPixelSize(R.dimen.default_margin));

            RelativeLayout.LayoutParams params4 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1.5f, getResources().getDisplayMetrics()));

            for (int i = 0; i < status.length; i++) {
                rlo_list[i] = new RelativeLayout(this);
                rlo_list[i].setLayoutParams(params1);
                rlo_list[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (System.currentTimeMillis() - lastTimeButtonPressed > 1500) {
                            for (int i = 0; i < status.length; i++) {
                                if (view == rlo_list[i]) {
                                    if (!TYPE.equals("fp")) {
                                        if (KEY.equals(TYPE + (i + 1)) || KEY.equals(TYPE + "admin") || KEY.equals("supervisor") || KEY.equals("jin")) {
                                            intent = new Intent(SelectionActivity.this, CheckActivity.class);
                                            String type = TYPE + (i + 1);
                                            intent.putExtra("type", TYPE);
                                            intent.putExtra("key", KEY);
                                            intent.putExtra("name", NAME);
                                            intent.putExtra("check_type", type);
                                            if (status[i].equals("TRUE")) {
                                                // dialog box
                                                new AlertDialog.Builder(SelectionActivity.this)
                                                        .setTitle("출석체크")
                                                        .setMessage("이미 출석이 완료되었습니다.\n수정하시겠습니까?")
                                                        .setNegativeButton("취소", null)
                                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                                startActivity(intent);
                                                                SelectionActivity.this.finish();
                                                            }
                                                        })
                                                        .show();
                                            } else {
                                                startActivity(intent);
                                                SelectionActivity.this.finish();
                                            }

                                        } else {
                                            // dialog box
                                            new AlertDialog.Builder(SelectionActivity.this)
                                                    .setTitle("출석체크")
                                                    .setMessage(getInfo())
                                                    .setPositiveButton("확인", null)
                                                    .show();
                                        }
                                    } else {
                                        if (i < 14) {
                                            if (KEY.equals("fpadmin") || KEY.equals("jin")) {
                                                if (i % 2 == 1 && !status[i - 1].equals("TRUE")) {
                                                    new AlertDialog.Builder(SelectionActivity.this)
                                                            .setTitle("현장전도관리")
                                                            .setMessage("열매관리는 출석관리를 완료하신 후에\n관리하실 수 있습니다.")
                                                            .setPositiveButton("확인", null)
                                                            .show();
                                                } else {
                                                    intent = new Intent(SelectionActivity.this, FpCheckActivity.class);
                                                    String type = TYPE + (i + 1);
                                                    intent.putExtra("type", TYPE);
                                                    intent.putExtra("key", KEY);
                                                    intent.putExtra("name", NAME);
                                                    intent.putExtra("check_type", type);
                                                    String msg;
                                                    if (i % 2 == 0) {
                                                        msg = "이미 출석관리가 완료되었습니다.\n수정하시겠습니까?";
                                                    } else {
                                                        msg = "이미 열매관리가 완료되었습니다.\n수정하시겠습니까?";
                                                    }
                                                    if (status[i].equals("TRUE")) {
                                                        // dialog box
                                                        new AlertDialog.Builder(SelectionActivity.this)
                                                                .setTitle("현장전도관리")
                                                                .setMessage(msg)
                                                                .setNegativeButton("취소", null)
                                                                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                                        startActivity(intent);
                                                                        SelectionActivity.this.finish();
                                                                    }
                                                                })
                                                                .show();
                                                    } else {
                                                        startActivity(intent);
                                                        SelectionActivity.this.finish();
                                                    }
                                                }

                                            } else {
                                                new AlertDialog.Builder(SelectionActivity.this)
                                                        .setTitle("현장전도관리")
                                                        .setMessage(NAME + " 계정입니다.\n관련부분 출석/열매를 관리하실 수 있습니다.")
                                                        .setPositiveButton("확인", null)
                                                        .show();
                                            }

                                        } else {
                                            if (KEY.equals("bc" + (i - 12) / 2) || KEY.equals("fpadmin") || KEY.equals("jin")) {
                                                if (i % 2 == 1 && !status[i - 1].equals("TRUE")) {
                                                    new AlertDialog.Builder(SelectionActivity.this)
                                                            .setTitle("현장전도관리")
                                                            .setMessage("열매관리는 출석관리를 완료하신 후에\n관리하실 수 있습니다.")
                                                            .setPositiveButton("확인", null)
                                                            .show();
                                                } else {
                                                    intent = new Intent(SelectionActivity.this, FpCheckActivity.class);
                                                    String type = TYPE + (i + 1);
                                                    intent.putExtra("type", TYPE);
                                                    intent.putExtra("key", KEY);
                                                    intent.putExtra("name", NAME);
                                                    intent.putExtra("check_type", type);
                                                    String msg;
                                                    if (i % 2 == 0) {
                                                        msg = "이미 출석관리가 완료되었습니다.\n수정하시겠습니까?";
                                                    } else {
                                                        msg = "이미 열매관리가 완료되었습니다.\n수정하시겠습니까?";
                                                    }
                                                    if (status[i].equals("TRUE")) {
                                                        // dialog box
                                                        new AlertDialog.Builder(SelectionActivity.this)
                                                                .setTitle("현장전도관리")
                                                                .setMessage(msg)
                                                                .setNegativeButton("취소", null)
                                                                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                                        startActivity(intent);
                                                                        SelectionActivity.this.finish();
                                                                    }
                                                                })
                                                                .show();
                                                    } else {
                                                        startActivity(intent);
                                                        SelectionActivity.this.finish();
                                                    }
                                                }

                                            } else {
                                                new AlertDialog.Builder(SelectionActivity.this)
                                                        .setTitle("현장전도관리")
                                                        .setMessage(NAME + " 계정입니다.\n관련부분 출석/열매를 관리하실 수 있습니다.")
                                                        .setPositiveButton("확인", null)
                                                        .show();
                                            }
                                        }
                                    }

                                    break;
                                }
                            }

                            lastTimeButtonPressed = System.currentTimeMillis();
                        }
                    }
                });

                TextView tv_list = new TextView(this);
                tv_list.setLayoutParams(params2);
                tv_list.setText(list[i]);
                tv_list.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                tv_list.setTextColor(getResources().getColor(R.color.BLACK));

                iv_list[i] = new ImageView(this);
                iv_list[i].setLayoutParams(params3);
                if (status[i].equals("TRUE")) {
                    iv_list[i].setBackgroundResource(R.drawable.status_true);
                } else {
                    iv_list[i].setBackgroundResource(R.drawable.status_false);
                }

                ImageView iv_line = new ImageView(this);
                iv_line.setLayoutParams(params4);
                iv_line.setBackgroundResource(R.color.colorPrimary);

                rlo_list[i].addView(tv_list);
                rlo_list[i].addView(iv_list[i]);
                content_selection.addView(rlo_list[i]);
                content_selection.addView(iv_line);
            }

        } else {
            intent = new Intent(this, NotfoundActivity.class);
            startActivity(intent);
            SelectionActivity.this.finish();
        }
    }

    public String getInfo() {
        String info;
        switch (KEY) {
            case "supervisor":
                info = NAME + " 계정입니다.\n전체 출석을 관리하실 수 있습니다.";
                break;
            case "generaladmin":
                info = NAME + " 계정입니다.\n일반남여전도회 출석을 관리하실 수 있습니다.";
                break;
            case "groupadmiin":
                info = NAME + " 계정입니다.\n기관 출석을 관리하실 수 있습니다.";
                break;
            case "bcadmin":
                info = NAME + " 계정입니다.\n지교회 출석을 관리하실 수 있습니다.";
                break;
            case "fpadmin":
                info = NAME + " 계정입니다.\n현장전도 출석/열매을 관리하실 수 있습니다.";
                break;
            default:
                info = NAME + " 계정입니다.\n관련부분 출석을 관리하실 수 있습니다.";
                break;
        }

        return info;
    }

    public void getStatus() {
        isDone = false;
        client = new Client(SelectionActivity.this, "getStatus", TYPE);
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
            for (int i = 0; i < status.length; i++) {
                if (status[i].equals("TRUE")) {
                    iv_list[i].setBackgroundResource(R.drawable.status_true);
                } else {
                    iv_list[i].setBackgroundResource(R.drawable.status_false);
                }
            }
        } else {
            intent = new Intent(this, NotfoundActivity.class);
            startActivity(intent);
            SelectionActivity.this.finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_selection, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.refresh_selection) {
            if (System.currentTimeMillis() - lastTimeButtonPressed > 1500) {
                getStatus();
                Snackbar.make(content_selection, "새로고침 되었습니다.", Snackbar.LENGTH_SHORT).show();

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
        SelectionActivity.this.finish();
    }

}