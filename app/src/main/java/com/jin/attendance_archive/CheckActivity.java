package com.jin.attendance_archive;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by JIN on 2/11/2019.
 */

public class CheckActivity extends AppCompatActivity {

    long lastTimeButtonPressed;

    LinearLayout content_check;
    Toolbar toolbar;
    RelativeLayout[] rlo_list;
    TextView[] tv_list;
    CheckBox[] cb_list;
    EditText[] et_list;
    Intent intent_get, intent;
    Context context = this;
    int add_index;
    String add_name, add_group;
    boolean duplication;

    Client client;
    boolean isDone;
    String[][] attendance;
    String[][] o_attendance;

    String TYPE, KEY, NAME, CHECK_TYPE;
    int CATEGORY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);

        content_check = (LinearLayout) findViewById(R.id.content_check);
        toolbar = (Toolbar) findViewById(R.id.toolbar_check);

        intent_get = getIntent();

        TYPE = intent_get.getStringExtra("type");
        KEY = intent_get.getStringExtra("key");
        NAME = intent_get.getStringExtra("name");
        CHECK_TYPE = intent_get.getStringExtra("check_type");

        isDone = false;
        client = new Client(CheckActivity.this, "getList", TYPE);
        client.execute();

        while (!isDone) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
            isDone = client.isDone();
        }

        String[] list;
        if (client.isAlive()) {
            list = client.getListValues();
            for (int i = 0; i < list.length; i++) {
                if (CHECK_TYPE.equals(TYPE + (i + 1))) {
                    toolbar.setTitle(list[i]);
                    break;
                }
            }
        } else {
            intent = new Intent(this, NotfoundActivity.class);
            startActivity(intent);
            CheckActivity.this.finish();
        }
        setSupportActionBar(toolbar);

        init();
    }

    public void init() {
        isDone = false;
        client = new Client(CheckActivity.this, "getAttendance", CHECK_TYPE);
        client.execute();

        while (!isDone) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
            isDone = client.isDone();
        }

        if (client.isAlive()) {
            attendance = client.getAttendance();

            rlo_list = new RelativeLayout[attendance[0].length];
            tv_list = new TextView[attendance[0].length];
            cb_list = new CheckBox[attendance[0].length];
            et_list = new EditText[attendance[0].length];

            RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);

            RelativeLayout.LayoutParams params_c = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            params_c.addRule(RelativeLayout.CENTER_VERTICAL);
            params_c.setMargins(getResources().getDimensionPixelSize(R.dimen.default_margin),
                    getResources().getDimensionPixelSize(R.dimen.default_margin),
                    getResources().getDimensionPixelSize(R.dimen.default_margin),
                    getResources().getDimensionPixelSize(R.dimen.default_margin));

            RelativeLayout.LayoutParams params_cb = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            params_cb.addRule(RelativeLayout.CENTER_VERTICAL);
            params_cb.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            params_cb.setMargins(getResources().getDimensionPixelSize(R.dimen.default_margin),
                    0, 0, 0);

            RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            params2.addRule(RelativeLayout.CENTER_VERTICAL);
            params2.setMargins(getResources().getDimensionPixelSize(R.dimen.default_margin),
                    getResources().getDimensionPixelSize(R.dimen.default_margin),
                    getResources().getDimensionPixelSize(R.dimen.default_margin),
                    getResources().getDimensionPixelSize(R.dimen.default_margin));

            RelativeLayout.LayoutParams params3
                    = new RelativeLayout.LayoutParams((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 150f, getResources().getDisplayMetrics()),
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            params3.addRule(RelativeLayout.CENTER_VERTICAL);
            params3.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            params3.setMargins(getResources().getDimensionPixelSize(R.dimen.default_margin),
                    getResources().getDimensionPixelSize(R.dimen.default_margin),
                    getResources().getDimensionPixelSize(R.dimen.default_margin),
                    getResources().getDimensionPixelSize(R.dimen.default_margin));

            RelativeLayout.LayoutParams params4 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1.5f, getResources().getDisplayMetrics()));

            CATEGORY = 0;

            for (int i = 0; i < attendance[0].length; i++) {
                rlo_list[i] = new RelativeLayout(this);
                rlo_list[i].setLayoutParams(params1);

                if (attendance[0][i].equals("CATEGORY")) {
                    CATEGORY++;

                    rlo_list[i].setBackgroundResource(R.color.colorPrimary);
                    TextView tv_category = new TextView(this);
                    tv_category.setLayoutParams(params_c);
                    tv_category.setText(attendance[1][i]);
                    tv_category.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                    tv_category.setTextColor(getResources().getColor(R.color.WHITE));

                    ImageView iv_line = new ImageView(this);
                    iv_line.setLayoutParams(params4);
                    iv_line.setBackgroundResource(R.color.colorPrimary);

                    rlo_list[i].addView(tv_category);
                    content_check.addView(rlo_list[i]);
                    content_check.addView(iv_line);

                } else {
                    et_list[i] = new EditText(this);
                    et_list[i].setLayoutParams(params3);
                    et_list[i].setBackgroundResource(android.R.drawable.editbox_background_normal);
                    if (attendance[2][i].equals("NULL")) {
                        et_list[i].setText("");
                    } else {
                        et_list[i].setText(attendance[2][i]);
                    }
                    et_list[i].setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                    et_list[i].setTextColor(getResources().getColor(R.color.BLACK));
                    et_list[i].setSingleLine();
                    et_list[i].setImeOptions(EditorInfo.IME_ACTION_DONE);

                    cb_list[i] = new CheckBox(this);
                    cb_list[i].setLayoutParams(params_cb);
                    cb_list[i].setId(10000000 + i);
                    if (attendance[1][i].equals("○")) {
                        cb_list[i].setChecked(true);
                        et_list[i].setVisibility(EditText.INVISIBLE);
                    } else {
                        cb_list[i].setChecked(false);
                        et_list[i].setVisibility(EditText.VISIBLE);
                    }
                    cb_list[i].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            for (int i = 0; i < attendance[0].length; i++) {
                                try {
                                    if (compoundButton == cb_list[i]) {
                                        if (compoundButton.isChecked()) {
                                            et_list[i].setVisibility(EditText.INVISIBLE);
                                        } else {
                                            et_list[i].setVisibility(EditText.VISIBLE);
                                        }
                                        break;
                                    }
                                } catch (NullPointerException e) {
                                }
                            }
                        }
                    });

                    tv_list[i] = new TextView(this);
                    params2.addRule(RelativeLayout.RIGHT_OF, cb_list[i].getId());
                    tv_list[i].setLayoutParams(params2);
                    tv_list[i].setText(attendance[0][i]);
                    tv_list[i].setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                    tv_list[i].setTextColor(getResources().getColor(R.color.BLACK));

                    ImageView iv_line = new ImageView(this);
                    iv_line.setLayoutParams(params4);
                    iv_line.setBackgroundResource(R.color.colorPrimary);

                    rlo_list[i].addView(cb_list[i]);
                    rlo_list[i].addView(tv_list[i]);
                    rlo_list[i].addView(et_list[i]);
                    content_check.addView(rlo_list[i]);
                    content_check.addView(iv_line);
                }
            }
        } else {
            intent = new Intent(this, NotfoundActivity.class);
            startActivity(intent);
            CheckActivity.this.finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (KEY.equals("jin") || KEY.equals("supervisor")) {
            getMenuInflater().inflate(R.menu.menu_check1, menu);
        } else {
            getMenuInflater().inflate(R.menu.menu_check2, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.send_check) {
            if (System.currentTimeMillis() - lastTimeButtonPressed > 1500) {
                boolean empty = true;
                for (int j = 0; j < attendance[0].length; j++) {
                    if (!attendance[0][j].equals("CATEGORY") && cb_list[j].isChecked()) {
                        empty = false;
                    }
                }
                if (empty) {
                    new AlertDialog.Builder(CheckActivity.this)
                            .setTitle("출석체크")
                            .setMessage("출석체크를 모두 마친 후\n출석체크 버튼을 눌러주세요.")
                            .setPositiveButton("확인", null)
                            .show();
                } else {
                    // dialog box
                    new AlertDialog.Builder(CheckActivity.this)
                            .setTitle("출석체크")
                            .setMessage("출석체크를 완료합니다.")
                            .setNegativeButton("취소", null)
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    o_attendance = new String[3][attendance[0].length - CATEGORY];
                                    CATEGORY = 0;
                                    for (int j = 0; j < attendance[0].length; j++) {
                                        if (attendance[0][j].equals("CATEGORY")) {
                                            CATEGORY++;
                                        } else {
                                            o_attendance[0][j - CATEGORY] = tv_list[j].getText().toString();
                                            if (cb_list[j].isChecked()) {
                                                o_attendance[1][j - CATEGORY] = "TRUE";
                                                o_attendance[2][j - CATEGORY] = "NULL";
                                            } else {
                                                o_attendance[1][j - CATEGORY] = "FALSE";
                                                o_attendance[2][j - CATEGORY] = et_list[j].getText().toString();
                                            }
                                        }
                                    }

                                    isDone = false;
                                    client = new Client(CheckActivity.this, "setAttendance", KEY, CHECK_TYPE, o_attendance);
                                    client.execute();

                                    while (!isDone) {
                                        try {
                                            Thread.sleep(100);
                                        } catch (InterruptedException e) {
                                        }
                                        isDone = client.isDone();
                                    }

                                    if (client.isAlive()) {
                                        // dialog box
                                        new AlertDialog.Builder(CheckActivity.this)
                                                .setTitle("출석체크")
                                                .setMessage("출석체크가 완료되었습니다.")
                                                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                                                    @Override
                                                    public void onCancel(DialogInterface dialogInterface) {
                                                        intent = new Intent(CheckActivity.this, SelectionActivity.class);
                                                        intent.putExtra("type", TYPE);
                                                        intent.putExtra("key", KEY);
                                                        intent.putExtra("name", NAME);
                                                        startActivity(intent);
                                                        CheckActivity.this.finish();
                                                    }
                                                })
                                                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        intent = new Intent(CheckActivity.this, SelectionActivity.class);
                                                        intent.putExtra("type", TYPE);
                                                        intent.putExtra("key", KEY);
                                                        intent.putExtra("name", NAME);
                                                        startActivity(intent);
                                                        CheckActivity.this.finish();
                                                    }
                                                })
                                                .show();
                                    } else {
                                        intent = new Intent(CheckActivity.this, NotfoundActivity.class);
                                        startActivity(intent);
                                        CheckActivity.this.finish();
                                    }
                                }
                            })
                            .show();
                }

                lastTimeButtonPressed = System.currentTimeMillis();
            }

        } else if (id == R.id.add_att) {
            if (System.currentTimeMillis() - lastTimeButtonPressed > 1500) {
                new AlertDialog.Builder(CheckActivity.this)
                        .setTitle("출석 명단 추가")
                        .setMessage("새로 출석 명단을 추가하시겠습니까?\n한 번 추가하시면 삭제가 불가능합니다.")
                        .setNegativeButton("취소", null)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                View digView;
                                EditText et_addName;
                                Spinner sp_addGroup;
                                if (CHECK_TYPE.equals("group1")) {
                                    digView = View.inflate(context, R.layout.dialog_add1, null);
                                    et_addName = digView.findViewById(R.id.edittext_add1);
                                    sp_addGroup = digView.findViewById(R.id.spinner_add1);
                                } else if (CHECK_TYPE.equals("group2")) {
                                    digView = View.inflate(context, R.layout.dialog_add2, null);
                                    et_addName = digView.findViewById(R.id.edittext_add2);
                                    sp_addGroup = digView.findViewById(R.id.spinner_add2);
                                } else if (CHECK_TYPE.equals("group3") || CHECK_TYPE.equals("group4") || CHECK_TYPE.equals("group6")) {
                                    digView = View.inflate(context, R.layout.dialog_add3, null);
                                    et_addName = digView.findViewById(R.id.edittext_add3);
                                    sp_addGroup = digView.findViewById(R.id.spinner_add3);
                                } else if (CHECK_TYPE.equals("group5")) {
                                    digView = View.inflate(context, R.layout.dialog_add4, null);
                                    et_addName = digView.findViewById(R.id.edittext_add4);
                                    sp_addGroup = digView.findViewById(R.id.spinner_add4);
                                } else if (CHECK_TYPE.equals("group7")) {
                                    digView = View.inflate(context, R.layout.dialog_add5, null);
                                    et_addName = digView.findViewById(R.id.edittext_add5);
                                    sp_addGroup = digView.findViewById(R.id.spinner_add5);
                                } else {
                                    digView = View.inflate(context, R.layout.dialog_add6, null);
                                    et_addName = digView.findViewById(R.id.edittext_add6);
                                    sp_addGroup = digView.findViewById(R.id.spinner_add6);
                                }
                                add_index = 1;
                                add_name = "";
                                et_addName.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                    }

                                    @Override
                                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                        add_name = charSequence.toString();
                                    }

                                    @Override
                                    public void afterTextChanged(Editable editable) {
                                    }
                                });
                                sp_addGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        add_group = (String) adapterView.getItemAtPosition(i);
                                        if (add_group.equals("선택")) {
                                            add_index = 1;
                                        } else {
                                            add_index = 0;
                                        }
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {
                                    }
                                });

                                new AlertDialog.Builder(CheckActivity.this)
                                        .setTitle("출석 명단 추가")
                                        .setView(digView)
                                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                new AlertDialog.Builder(CheckActivity.this)
                                                        .setTitle("출석 명단 추가")
                                                        .setMessage("취소되었습니다.")
                                                        .setPositiveButton("확인", null)
                                                        .show();
                                            }
                                        })
                                        .setPositiveButton("완료", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                if (!add_name.equals("")) {
                                                    String msg = add_name + " [" + add_group + "] 님을\n정말로 추가하시겠습니까?";
                                                    for (int n = 0; n < add_name.length(); n++) {
                                                        if (add_name.substring(n, n + 1).equals(" ") || add_name.substring(n, n + 1).equals("!")
                                                                || add_name.substring(n, n + 1).equals("@") || add_name.substring(n, n + 1).equals("#")
                                                                || add_name.substring(n, n + 1).equals("$") || add_name.substring(n, n + 1).equals("%")
                                                                || add_name.substring(n, n + 1).equals("^") || add_name.substring(n, n + 1).equals("&")
                                                                || add_name.substring(n, n + 1).equals("*") || add_name.substring(n, n + 1).equals("(")
                                                                || add_name.substring(n, n + 1).equals(")") || add_name.substring(n, n + 1).equals("-")
                                                                || add_name.substring(n, n + 1).equals("=") || add_name.substring(n, n + 1).equals("+")
                                                                || add_name.substring(n, n + 1).equals("?") || add_name.substring(n, n + 1).equals("/")
                                                                || add_name.substring(n, n + 1).equals("<") || add_name.substring(n, n + 1).equals(">")
                                                                || add_name.substring(n, n + 1).equals(",") || add_name.substring(n, n + 1).equals(".")
                                                                || add_name.substring(n, n + 1).equals(";") || add_name.substring(n, n + 1).equals(":")
                                                                || add_name.substring(n, n + 1).equals("'") || add_name.substring(n, n + 1).equals("\"")
                                                                || add_name.substring(n, n + 1).equals("_") || add_name.substring(n, n + 1).equals("\\")) {
                                                            add_index = 2;
                                                            break;
                                                        }
                                                    }
                                                    if (add_index == 0) {
                                                        isDone = false;
                                                        client = new Client(CheckActivity.this, "getDuplication", add_name);
                                                        client.execute();

                                                        while (!isDone) {
                                                            try {
                                                                Thread.sleep(100);
                                                            } catch (InterruptedException e) {
                                                            }
                                                            isDone = client.isDone();
                                                        }

                                                        if (client.isAlive()) {
                                                            duplication = client.getDUPLICATION();
                                                            if (duplication) {
                                                                add_index = 3;
                                                            }
                                                        } else {
                                                            intent = new Intent(CheckActivity.this, NotfoundActivity.class);
                                                            startActivity(intent);
                                                            CheckActivity.this.finish();
                                                        }
                                                    }
                                                    switch (add_index) {
                                                        case 1:
                                                            msg = "구분을 선택해주세요.\n다시 처음부터 시도해주세요.";
                                                            break;
                                                        case 2:
                                                            msg = "이름은 띄어쓰기/특수문자를 포함할 수 없습니다.\n다시 처음부터 시도해주세요.";
                                                            break;
                                                        case 3:
                                                            msg = "중복된 이름이 존재합니다.\n다시 처음부터 시도해주세요.";
                                                    }
                                                    if (add_index != 0) {
                                                        new AlertDialog.Builder(CheckActivity.this)
                                                                .setTitle("출석 명단 추가")
                                                                .setMessage(msg)
                                                                .setPositiveButton("확인", null)
                                                                .show();
                                                    } else {
                                                        new AlertDialog.Builder(CheckActivity.this)
                                                                .setTitle("출석 명단 추가")
                                                                .setMessage(msg)
                                                                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                                        new AlertDialog.Builder(CheckActivity.this)
                                                                                .setTitle("출석 명단 추가")
                                                                                .setMessage("취소되었습니다.")
                                                                                .setPositiveButton("확인", null)
                                                                                .show();
                                                                    }
                                                                })
                                                                .setPositiveButton("완료", new DialogInterface.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                                        isDone = false;
                                                                        client = new Client(CheckActivity.this, "addAttendance", KEY, CHECK_TYPE, add_name, add_group);
                                                                        client.execute();

                                                                        while (!isDone) {
                                                                            try {
                                                                                Thread.sleep(100);
                                                                            } catch (InterruptedException e) {
                                                                            }
                                                                            isDone = client.isDone();
                                                                        }

                                                                        if (client.isAlive()) {
                                                                            new AlertDialog.Builder(CheckActivity.this)
                                                                                    .setTitle("출석 명단 추가")
                                                                                    .setMessage("출석 명단 추가가 완료되었습니다.\n재접속이 필요합니다.\n출석체크 목록으로 돌아갑니다.")
                                                                                    .setOnCancelListener(new DialogInterface.OnCancelListener() {
                                                                                        @Override
                                                                                        public void onCancel(DialogInterface dialogInterface) {
                                                                                            intent = new Intent(CheckActivity.this, SelectionActivity.class);
                                                                                            intent.putExtra("type", TYPE);
                                                                                            intent.putExtra("key", KEY);
                                                                                            intent.putExtra("name", NAME);
                                                                                            startActivity(intent);
                                                                                            CheckActivity.this.finish();
                                                                                        }
                                                                                    })
                                                                                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                                                                        @Override
                                                                                        public void onClick(DialogInterface dialogInterface, int i) {
                                                                                            intent = new Intent(CheckActivity.this, SelectionActivity.class);
                                                                                            intent.putExtra("type", TYPE);
                                                                                            intent.putExtra("key", KEY);
                                                                                            intent.putExtra("name", NAME);
                                                                                            startActivity(intent);
                                                                                            CheckActivity.this.finish();
                                                                                        }
                                                                                    })
                                                                                    .show();
                                                                        } else {
                                                                            intent = new Intent(CheckActivity.this, NotfoundActivity.class);
                                                                            startActivity(intent);
                                                                            CheckActivity.this.finish();
                                                                        }
                                                                    }
                                                                }).show();
                                                    }
                                                } else {
                                                    new AlertDialog.Builder(CheckActivity.this)
                                                            .setTitle("출석 명단 추가")
                                                            .setMessage("이름을 입력해주세요.\n다시 처음부터 시도해주세요.")
                                                            .setPositiveButton("확인", null)
                                                            .show();
                                                }
                                            }
                                        }).show();
                            }
                        }).show();

                lastTimeButtonPressed = System.currentTimeMillis();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // dialog box
        new AlertDialog.Builder(CheckActivity.this)
                .setTitle("출석체크")
                .setMessage("출석내용이 저장되지 않습니다.")
                .setNegativeButton("취소", null)
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        intent = new Intent(CheckActivity.this, SelectionActivity.class);
                        intent.putExtra("type", TYPE);
                        intent.putExtra("key", KEY);
                        intent.putExtra("name", NAME);
                        startActivity(intent);
                        CheckActivity.this.finish();
                    }
                })
                .show();
    }
}