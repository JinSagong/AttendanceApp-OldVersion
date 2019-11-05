package com.jin.attendance_archive;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by JIN on 6/21/2019.
 */

public class FpCheckActivity extends AppCompatActivity {

    long lastTimeButtonPressed;

    LinearLayout content_check;
    Toolbar toolbar;
    RelativeLayout[] rlo_list;
    TextView[] tv_list, tv_check;
    EditText[] et_list;
    ImageView[] iv_addbtn;
    ArrayList rlo_list_etc, tv_list_etc, iv_remove_etc, iv_line_etc, names_etc, rlo_list_search, tv_list_search, iv_remove_search, iv_line_search, names_search;
    EditText et_add, et_search;
    ImageView iv_add, iv_search;
    RelativeLayout r, rlo_add, rlo_search;
    TextView t;
    ImageView iv, iv_line_default;
    RelativeLayout.LayoutParams params1, params2, params3, params4, params5, params6, params7, params8, params_c;
    int[] index;
    int prev, index_number, key_number, add_index;
    String add_name, add_group;
    boolean duplication;

    ArrayList rlo_b, rlo_wm, tv_b, iv_b_btn, iv_b_line, tv_wm, iv_wm_btn, iv_wm_line, isOpen_b, isOpen_wm,
            b_preacher, b_believer, b_teacher, b_age, b_phone, b_remeet,
            wm_teacher, wm_believer, wm_frequency, wm_place;
    EditText et1, et2, et4, et5, et6;
    RelativeLayout rlo_b_plus, rlo_wm_plus;

    boolean att;

    Context context = this;
    Intent intent_get, intent;

    Client client;
    String[][] attendance, candidates, believer, wordmovement;
    String[] list, status, etc_attendance, search_attendance;
    boolean isDone;

    String TYPE, KEY, NAME, CHECK_TYPE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fpcheck);

        content_check = (LinearLayout) findViewById(R.id.content_fpcheck);
        toolbar = (Toolbar) findViewById(R.id.toolbar_fpcheck);

        intent_get = getIntent();

        TYPE = intent_get.getStringExtra("type");
        KEY = intent_get.getStringExtra("key");
        NAME = intent_get.getStringExtra("name");
        CHECK_TYPE = intent_get.getStringExtra("check_type");

        isDone = false;
        client = new Client(FpCheckActivity.this, "getList", TYPE);
        client.execute();

        while (!isDone) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
            isDone = client.isDone();
        }

        att = false;
        if (client.isAlive()) {
            list = client.getListValues();
            status = client.getSTATUS();
            for (int i = 0; i < list.length; i++) {
                if (CHECK_TYPE.equals(TYPE + (i + 1))) {
                    toolbar.setTitle(list[i]);
                    if (i % 2 == 0) {
                        att = true;
                    }
                    break;
                }
            }
        } else {
            intent = new Intent(this, NotfoundActivity.class);
            startActivity(intent);
            FpCheckActivity.this.finish();
        }
        setSupportActionBar(toolbar);

        init();
        if (att) {
            init1();
        } else {
            init2();
        }
    }

    public void init() {
        params1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        params_c = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        params_c.addRule(RelativeLayout.CENTER_VERTICAL);
        params_c.setMargins(getResources().getDimensionPixelSize(R.dimen.default_margin),
                getResources().getDimensionPixelSize(R.dimen.default_margin),
                getResources().getDimensionPixelSize(R.dimen.default_margin),
                getResources().getDimensionPixelSize(R.dimen.default_margin));

        params2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        params2.addRule(RelativeLayout.CENTER_VERTICAL);
        params2.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        params2.setMargins(getResources().getDimensionPixelSize(R.dimen.default_margin),
                getResources().getDimensionPixelSize(R.dimen.default_margin),
                getResources().getDimensionPixelSize(R.dimen.default_margin),
                getResources().getDimensionPixelSize(R.dimen.default_margin));

        params3 = new RelativeLayout.LayoutParams((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 150f, getResources().getDisplayMetrics()),
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        params3.addRule(RelativeLayout.CENTER_VERTICAL);
        params3.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        params3.setMargins(getResources().getDimensionPixelSize(R.dimen.default_margin),
                getResources().getDimensionPixelSize(R.dimen.default_margin),
                getResources().getDimensionPixelSize(R.dimen.default_margin),
                getResources().getDimensionPixelSize(R.dimen.default_margin));

        params4 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1.5f, getResources().getDisplayMetrics()));

        params5 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        params5.addRule(RelativeLayout.CENTER_VERTICAL);
        params5.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        params5.setMargins(getResources().getDimensionPixelSize(R.dimen.default_margin),
                getResources().getDimensionPixelSize(R.dimen.default_margin),
                getResources().getDimensionPixelSize(R.dimen.default_margin),
                getResources().getDimensionPixelSize(R.dimen.default_margin));

        params6 = new RelativeLayout.LayoutParams((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200f, getResources().getDisplayMetrics()),
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        params6.addRule(RelativeLayout.CENTER_VERTICAL);
        params6.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        params6.setMargins(getResources().getDimensionPixelSize(R.dimen.default_margin),
                getResources().getDimensionPixelSize(R.dimen.default_margin),
                getResources().getDimensionPixelSize(R.dimen.default_margin),
                getResources().getDimensionPixelSize(R.dimen.default_margin));

        params7 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        params7.addRule(RelativeLayout.CENTER_IN_PARENT);
        params7.setMargins(getResources().getDimensionPixelSize(R.dimen.default_margin),
                getResources().getDimensionPixelSize(R.dimen.default_margin),
                getResources().getDimensionPixelSize(R.dimen.default_margin),
                getResources().getDimensionPixelSize(R.dimen.default_margin));

        params8 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        params8.addRule(RelativeLayout.CENTER_IN_PARENT);
        params8.setMargins(getResources().getDimensionPixelSize(R.dimen.default_margin),
                getResources().getDimensionPixelSize(R.dimen.default_margin),
                getResources().getDimensionPixelSize(R.dimen.default_margin),
                getResources().getDimensionPixelSize(R.dimen.default_margin));
    }

    public void init1() {
        isDone = false;
        client = new Client(FpCheckActivity.this, "getFpAttendance", CHECK_TYPE);
        client.execute();

        while (!isDone) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
            isDone = client.isDone();
        }

        if (client.isAlive()) {
            attendance = client.getFpAttendance();
            etc_attendance = client.getFpEtcAttendance();
            search_attendance = client.getFpSearchATTENDANCE();

            rlo_list = new RelativeLayout[attendance[0].length];
            tv_list = new TextView[attendance[0].length];
            tv_check = new TextView[attendance[0].length];
            et_list = new EditText[attendance[0].length];
            index = new int[attendance[0].length];

            rlo_list_etc = new ArrayList<RelativeLayout>();
            tv_list_etc = new ArrayList<TextView>();
            iv_remove_etc = new ArrayList<ImageView>();
            iv_line_etc = new ArrayList<ImageView>();
            names_etc = new ArrayList<String>();
            rlo_list_search = new ArrayList<RelativeLayout>();
            tv_list_search = new ArrayList<TextView>();
            iv_remove_search = new ArrayList<ImageView>();
            iv_line_search = new ArrayList<ImageView>();
            names_search = new ArrayList<String>();

            for (int i = 0; i < attendance[0].length; i++) {
                rlo_list[i] = new RelativeLayout(this);
                rlo_list[i].setLayoutParams(params1);

                et_list[i] = new EditText(this);
                et_list[i].setLayoutParams(params3);
                et_list[i].setBackgroundResource(android.R.drawable.editbox_background_normal);
                if (attendance[4][i].equals("NULL")) {
                    et_list[i].setText("");
                } else {
                    et_list[i].setText(attendance[4][i]);
                }
                et_list[i].setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                et_list[i].setTextColor(getResources().getColor(R.color.BLACK));
                et_list[i].setSingleLine();
                et_list[i].setImeOptions(EditorInfo.IME_ACTION_DONE);

                tv_list[i] = new TextView(this);
                tv_list[i].setLayoutParams(params2);
                tv_list[i].setText(attendance[0][i]);
                tv_list[i].setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                tv_list[i].setTextColor(getResources().getColor(R.color.BLACK));

                if (attendance[5][i].equals("0.0")) {
                    index[i] = 0;
                    rlo_list[i].setBackgroundResource(R.color.WHITE);
                    tv_list[i].setText("[결석] " + attendance[0][i]);
                    et_list[i].setVisibility(EditText.VISIBLE);
                } else if (attendance[5][i].equals("1.0")) {
                    index[i] = 1;
                    rlo_list[i].setBackgroundResource(R.color.dedication);
                    tv_list[i].setText("[헌신] " + attendance[0][i]);
                    et_list[i].setVisibility(EditText.VISIBLE);
                } else if (attendance[5][i].equals("2.0") && attendance[3][i].equals(CHECK_TYPE)) {
                    index[i] = 2;
                    rlo_list[i].setBackgroundResource(R.color.attendance);
                    tv_list[i].setText("[출석] " + attendance[0][i]);
                    et_list[i].setVisibility(EditText.INVISIBLE);
                } else {
                    index[i] = 2;
                    rlo_list[i].setBackgroundResource(R.color.attendance);
                    tv_list[i].setText("[출석] " + attendance[0][i] + " <" + attendance[6][i] + " 출석>");
                    et_list[i].setVisibility(EditText.INVISIBLE);
                }

                rlo_list[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        for (int i = 0; i < rlo_list.length; i++) {
                            if (view == rlo_list[i]) {
                                if (index[i] == 2) {
                                    index[i] = 1;
                                    tv_list[i].setText("[헌신] " + attendance[0][i]);
                                    rlo_list[i].setBackgroundResource(R.color.dedication);
                                    et_list[i].setVisibility(EditText.VISIBLE);
                                } else if (index[i] == 0) {
                                    index[i] = 2;
                                    if (attendance[5][i].equals("2.0") && !attendance[3][i].equals(CHECK_TYPE)) {
                                        tv_list[i].setText("[출석] " + attendance[0][i] + " <" + attendance[6][i] + " 출석>");
                                    } else {
                                        tv_list[i].setText("[출석] " + attendance[0][i]);
                                    }
                                    rlo_list[i].setBackgroundResource(R.color.attendance);
                                    et_list[i].setVisibility(EditText.INVISIBLE);
                                } else {
                                    index[i] = 0;
                                    tv_list[i].setText("[결석] " + attendance[0][i]);
                                    rlo_list[i].setBackgroundResource(R.color.WHITE);
                                    et_list[i].setVisibility(EditText.VISIBLE);
                                }
                                break;
                            }
                        }
                    }
                });

                ImageView iv_line = new ImageView(this);
                iv_line.setLayoutParams(params4);
                iv_line.setBackgroundResource(R.color.colorPrimary);

                rlo_list[i].addView(tv_list[i]);
                rlo_list[i].addView(et_list[i]);
                content_check.addView(rlo_list[i]);
                content_check.addView(iv_line);
            }


            // ETC
            RelativeLayout rlo_c1 = new RelativeLayout(this);
            rlo_c1.setLayoutParams(params1);
            rlo_c1.setBackgroundResource(R.color.colorPrimary);
            TextView tv_category1 = new TextView(this);
            tv_category1.setLayoutParams(params_c);
            tv_category1.setText("유치/유년/초등/중등/고등/기타");
            tv_category1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
            tv_category1.setTextColor(getResources().getColor(R.color.WHITE));

            ImageView iv_line1 = new ImageView(this);
            iv_line1.setLayoutParams(params4);
            iv_line1.setBackgroundResource(R.color.colorPrimary);

            rlo_c1.addView(tv_category1);
            content_check.addView(rlo_c1);
            content_check.addView(iv_line1);

            for (int i = 0; i < etc_attendance.length; i++) {
                r = new RelativeLayout(this);
                r.setLayoutParams(params1);
                r.setBackgroundResource(R.color.WHITE);

                t = new TextView(this);
                t.setLayoutParams(params2);
                t.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                t.setTextColor(getResources().getColor(R.color.BLACK));

                iv = new ImageView(this);
                iv.setLayoutParams(params5);
                iv.setBackgroundResource(R.drawable.remove_btn);
                iv_remove_etc.add(iv);
                iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        for (int i = 0; i < iv_remove_etc.size(); i++) {
                            if (view == iv_remove_etc.get(i)) {
                                content_check.removeView((View) rlo_list_etc.get(i));
                                content_check.removeView((View) iv_line_etc.get(i));
                                rlo_list_etc.remove(i);
                                tv_list_etc.remove(i);
                                iv_remove_etc.remove(i);
                                iv_line_etc.remove(i);
                                names_etc.remove(i);
                            }
                        }
                    }
                });

                iv_line_default = new ImageView(this);
                iv_line_default.setLayoutParams(params4);
                iv_line_default.setBackgroundResource(R.color.colorPrimary);

                r.addView(t);
                r.addView(iv);

                t.setText(etc_attendance[i]);
                names_etc.add(etc_attendance[i]);
                rlo_list_etc.add(r);
                tv_list_etc.add(t);
                iv_line_etc.add(iv_line_default);
                content_check.addView((View) rlo_list_etc.get(i));
                content_check.addView((View) iv_line_etc.get(i));
            }

            rlo_add = new RelativeLayout(this);
            rlo_add.setLayoutParams(params1);
            rlo_add.setBackgroundResource(R.color.WHITE);
            et_add = new EditText(this);
            et_add.setLayoutParams(params6);
            et_add.setBackgroundResource(android.R.drawable.editbox_background_normal);
            et_add.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
            et_add.setTextColor(getResources().getColor(R.color.BLACK));
            et_add.setSingleLine();
            et_add.setImeOptions(EditorInfo.IME_ACTION_DONE);
            iv_add = new ImageView(this);
            iv_add.setLayoutParams(params5);
            iv_add.setBackgroundResource(R.drawable.add_btn);
            iv_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (view == iv_add) {
                        if (!et_add.getText().toString().equals("")) {
                            boolean dup = false;
                            for (int i = 0; i < names_etc.size(); i++) {
                                if (names_etc.get(i).equals(et_add.getText().toString())) {
                                    dup = true;
                                    break;
                                }
                            }
                            if (!dup) {
                                r = new RelativeLayout(context);
                                r.setLayoutParams(params1);
                                r.setBackgroundResource(R.color.WHITE);

                                t = new TextView(context);
                                t.setLayoutParams(params2);
                                t.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                                t.setTextColor(getResources().getColor(R.color.BLACK));

                                iv = new ImageView(context);
                                iv.setLayoutParams(params5);
                                iv.setBackgroundResource(R.drawable.remove_btn);
                                iv_remove_etc.add(iv);
                                iv.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        for (int i = 0; i < iv_remove_etc.size(); i++) {
                                            if (view == iv_remove_etc.get(i)) {
                                                content_check.removeView((View) rlo_list_etc.get(i));
                                                content_check.removeView((View) iv_line_etc.get(i));
                                                rlo_list_etc.remove(i);
                                                tv_list_etc.remove(i);
                                                iv_remove_etc.remove(i);
                                                iv_line_etc.remove(i);
                                                names_etc.remove(i);
                                            }
                                        }
                                    }
                                });

                                iv_line_default = new ImageView(context);
                                iv_line_default.setLayoutParams(params4);
                                iv_line_default.setBackgroundResource(R.color.colorPrimary);

                                r.addView(t);
                                r.addView(iv);

                                t.setText(et_add.getText());
                                names_etc.add(et_add.getText().toString());

                                rlo_list_etc.add(r);
                                tv_list_etc.add(t);
                                iv_line_etc.add(iv_line_default);
                                content_check.addView((View) rlo_list_etc.get(rlo_list_etc.size() - 1), content_check.indexOfChild(rlo_add));
                                content_check.addView((View) iv_line_etc.get(iv_line_etc.size() - 1), content_check.indexOfChild(rlo_add));

                                et_add.setText("");
                            } else {
                                new AlertDialog.Builder(FpCheckActivity.this)
                                        .setTitle("현장전도관리")
                                        .setMessage("중복되는 이름이 존재합니다.")
                                        .setPositiveButton("확인", null)
                                        .show();
                            }
                        }
                    }
                }
            });

            ImageView iv_line_add = new ImageView(this);
            iv_line_add.setLayoutParams(params4);
            iv_line_add.setBackgroundResource(R.color.colorPrimary);

            rlo_add.addView(et_add);
            rlo_add.addView(iv_add);
            content_check.addView(rlo_add);
            content_check.addView(iv_line_add);


            // SEARCHING
            RelativeLayout rlo_c2 = new RelativeLayout(this);
            rlo_c2.setLayoutParams(params1);
            rlo_c2.setBackgroundResource(R.color.colorPrimary);
            TextView tv_category2 = new TextView(this);
            tv_category2.setLayoutParams(params_c);
            tv_category2.setText("추가 출석체크");
            tv_category2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
            tv_category2.setTextColor(getResources().getColor(R.color.WHITE));

            ImageView iv_line2 = new ImageView(this);
            iv_line2.setLayoutParams(params4);
            iv_line2.setBackgroundResource(R.color.colorPrimary);

            rlo_c2.addView(tv_category2);
            content_check.addView(rlo_c2);
            content_check.addView(iv_line2);

            prev = 0;

            rlo_search = new RelativeLayout(this);
            rlo_search.setLayoutParams(params1);
            rlo_search.setBackgroundResource(R.color.search);
            et_search = new EditText(this);
            et_search.setLayoutParams(params6);
            et_search.setBackgroundResource(android.R.drawable.editbox_background_normal);
            et_search.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
            et_search.setTextColor(getResources().getColor(R.color.BLACK));
            et_search.setSingleLine();
            et_search.setHint("이름을 입력하세요.");
            et_search.setImeOptions(EditorInfo.IME_ACTION_DONE);

            iv_search = new ImageView(this);
            iv_search.setLayoutParams(params5);
            iv_search.setBackgroundResource(R.drawable.search_btn);
            iv_search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (view == iv_search) {
                        for (int k = 0; k < prev; k++) {
                            content_check.removeViewAt(content_check.indexOfChild(rlo_search) - 1);
                            content_check.removeViewAt(content_check.indexOfChild(rlo_search) - 1);
                        }
                        prev = 0;

                        if (et_search.getText().toString().equals("")) {
                            new AlertDialog.Builder(FpCheckActivity.this)
                                    .setTitle("현장전도관리")
                                    .setMessage("이름을 입력하세요.")
                                    .setPositiveButton("확인", null)
                                    .show();
                        } else {
                            isDone = false;
                            client = new Client(FpCheckActivity.this, "getSearchResult", et_search.getText().toString());
                            client.execute();

                            while (!isDone) {
                                try {
                                    Thread.sleep(100);
                                } catch (InterruptedException e) {
                                }
                                isDone = client.isDone();
                            }

                            if (client.isAlive()) {
                                candidates = client.getCANDIDATES();
                                prev = candidates[0].length;
                                iv_addbtn = new ImageView[prev];

                                if (prev == 0) {
                                    r = new RelativeLayout(context);
                                    r.setLayoutParams(params1);
                                    r.setBackgroundResource(R.color.search);

                                    t = new TextView(context);
                                    t.setLayoutParams(params2);
                                    t.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                                    t.setTextColor(getResources().getColor(R.color.GRAY));

                                    iv_line_default = new ImageView(context);
                                    iv_line_default.setLayoutParams(params4);
                                    iv_line_default.setBackgroundResource(R.color.colorPrimary);

                                    r.addView(t);

                                    t.setText("검색 결과가 없습니다.");
                                    content_check.addView(r, content_check.indexOfChild(rlo_search));
                                    content_check.addView(iv_line_default, content_check.indexOfChild(rlo_search));

                                    prev = 1;
                                } else {
                                    for (int j = 0; j < candidates[0].length; j++) {
                                        r = new RelativeLayout(context);
                                        r.setLayoutParams(params1);
                                        r.setBackgroundResource(R.color.search);

                                        t = new TextView(context);
                                        t.setLayoutParams(params2);
                                        t.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                                        t.setTextColor(getResources().getColor(R.color.GRAY));

                                        iv_addbtn[j] = new ImageView(context);
                                        iv_addbtn[j].setLayoutParams(params5);
                                        iv_addbtn[j].setBackgroundResource(R.drawable.add_btn);
                                        iv_addbtn[j].setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                for (int i = 0; i < iv_addbtn.length; i++) {
                                                    if (view == iv_addbtn[i]) {
                                                        if (candidates[3][i].equals(CHECK_TYPE)) {
                                                            new AlertDialog.Builder(FpCheckActivity.this)
                                                                    .setTitle("현장전도관리")
                                                                    .setMessage(candidates[0][i] + "[" + candidates[1][i] + "]님의 출석은\n위의 목록에서 관리할 수 있습니다.")
                                                                    .setPositiveButton("확인", null)
                                                                    .show();
                                                        } else {
                                                            boolean existed = false;
                                                            for (int k = 0; k < names_search.size(); k++) {
                                                                if (candidates[0][i].equals(names_search.get(k))) {
                                                                    existed = true;
                                                                    break;
                                                                }
                                                            }
                                                            if (existed) {
                                                                new AlertDialog.Builder(FpCheckActivity.this)
                                                                        .setTitle("현장전도관리")
                                                                        .setMessage(candidates[0][i] + "[" + candidates[1][i] + "]님은\n이미 추가되어 있습니다.")
                                                                        .setPositiveButton("확인", null)
                                                                        .show();
                                                            } else {
                                                                for (int k = 0; k < prev; k++) {
                                                                    content_check.removeViewAt(content_check.indexOfChild(rlo_search) - 1);
                                                                    content_check.removeViewAt(content_check.indexOfChild(rlo_search) - 1);
                                                                }
                                                                prev = 0;
                                                                et_search.setText("");

                                                                r = new RelativeLayout(context);
                                                                r.setLayoutParams(params1);
                                                                r.setBackgroundResource(R.color.WHITE);

                                                                t = new TextView(context);
                                                                t.setLayoutParams(params2);
                                                                t.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                                                                t.setTextColor(getResources().getColor(R.color.BLACK));

                                                                iv = new ImageView(context);
                                                                iv.setLayoutParams(params5);
                                                                iv.setBackgroundResource(R.drawable.remove_btn);
                                                                iv_remove_search.add(iv);
                                                                iv.setOnClickListener(new View.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(View view) {
                                                                        for (int i = 0; i < iv_remove_search.size(); i++) {
                                                                            if (view == iv_remove_search.get(i)) {
                                                                                content_check.removeView((View) rlo_list_search.get(i));
                                                                                content_check.removeView((View) iv_line_search.get(i));
                                                                                rlo_list_search.remove(i);
                                                                                tv_list_search.remove(i);
                                                                                iv_remove_search.remove(i);
                                                                                iv_line_search.remove(i);
                                                                                names_search.remove(i);
                                                                            }
                                                                        }
                                                                    }
                                                                });

                                                                iv_line_default = new ImageView(context);
                                                                iv_line_default.setLayoutParams(params4);
                                                                iv_line_default.setBackgroundResource(R.color.colorPrimary);

                                                                r.addView(t);
                                                                r.addView(iv);

                                                                t.setText(candidates[0][i]);
                                                                names_search.add(candidates[0][i]);
                                                                rlo_list_search.add(r);
                                                                tv_list_search.add(t);
                                                                iv_line_search.add(iv_line_default);
                                                                content_check.addView((View) rlo_list_search.get(rlo_list_search.size() - 1));
                                                                content_check.addView((View) iv_line_search.get(iv_line_search.size() - 1));
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        });

                                        iv_line_default = new ImageView(context);
                                        iv_line_default.setLayoutParams(params4);
                                        iv_line_default.setBackgroundResource(R.color.colorPrimary);

                                        r.addView(t);
                                        r.addView(iv_addbtn[j]);

                                        t.setText("[" + candidates[1][j] + "]  " + candidates[0][j] + "  <" + candidates[2][j] + ">");
                                        content_check.addView(r, content_check.indexOfChild(rlo_search));
                                        content_check.addView(iv_line_default, content_check.indexOfChild(rlo_search));
                                    }
                                }

                            } else {
                                intent = new Intent(context, NotfoundActivity.class);
                                startActivity(intent);
                                FpCheckActivity.this.finish();
                            }

                        }
                    }
                }
            });

            ImageView iv_line_search2 = new ImageView(this);
            iv_line_search2.setLayoutParams(params4);
            iv_line_search2.setBackgroundResource(R.color.colorPrimary);

            rlo_search.addView(et_search);
            rlo_search.addView(iv_search);
            content_check.addView(rlo_search);
            content_check.addView(iv_line_search2);

            for (int i = 0; i < search_attendance.length; i++) {
                r = new RelativeLayout(this);
                r.setLayoutParams(params1);
                r.setBackgroundResource(R.color.WHITE);

                t = new TextView(this);
                t.setLayoutParams(params2);
                t.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                t.setTextColor(getResources().getColor(R.color.BLACK));

                iv = new ImageView(this);
                iv.setLayoutParams(params5);
                iv.setBackgroundResource(R.drawable.remove_btn);
                iv_remove_search.add(iv);
                iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        for (int i = 0; i < iv_remove_search.size(); i++) {
                            if (view == iv_remove_search.get(i)) {
                                content_check.removeView((View) rlo_list_search.get(i));
                                content_check.removeView((View) iv_line_search.get(i));
                                rlo_list_search.remove(i);
                                tv_list_search.remove(i);
                                iv_remove_search.remove(i);
                                iv_line_search.remove(i);
                                names_search.remove(i);
                            }
                        }
                    }
                });

                iv_line_default = new ImageView(this);
                iv_line_default.setLayoutParams(params4);
                iv_line_default.setBackgroundResource(R.color.colorPrimary);

                r.addView(t);
                r.addView(iv);

                t.setText(search_attendance[i]);
                names_search.add(search_attendance[i]);
                rlo_list_search.add(r);
                tv_list_search.add(t);
                iv_line_search.add(iv_line_default);
                content_check.addView((View) rlo_list_search.get(i));
                content_check.addView((View) iv_line_search.get(i));
            }

        } else {
            intent = new Intent(this, NotfoundActivity.class);
            startActivity(intent);
            FpCheckActivity.this.finish();
        }
    }

    public void init2() {
        isDone = false;
        client = new Client(FpCheckActivity.this, "getFpFruits", CHECK_TYPE);
        client.execute();

        while (!isDone) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
            isDone = client.isDone();
        }

        if (client.isAlive()) {
            believer = client.getBeliever();
            wordmovement = client.getWordMovement();

            rlo_b = new ArrayList<RelativeLayout>();
            tv_b = new ArrayList<TextView>();
            iv_b_btn = new ArrayList<ImageView>();
            iv_b_line = new ArrayList<ImageView>();
            isOpen_b = new ArrayList<Boolean>();
            b_preacher = new ArrayList<String>();
            b_believer = new ArrayList<String>();
            b_teacher = new ArrayList<String>();
            b_age = new ArrayList<String>();
            b_phone = new ArrayList<String>();
            b_remeet = new ArrayList<String>();

            rlo_wm = new ArrayList<RelativeLayout>();
            tv_wm = new ArrayList<TextView>();
            iv_wm_btn = new ArrayList<ImageView>();
            iv_wm_line = new ArrayList<ImageView>();
            isOpen_wm = new ArrayList<Boolean>();
            wm_teacher = new ArrayList<String>();
            wm_believer = new ArrayList<String>();
            wm_frequency = new ArrayList<String>();
            wm_place = new ArrayList<String>();


            // Believer
            RelativeLayout rlo_c1 = new RelativeLayout(this);
            rlo_c1.setLayoutParams(params1);
            rlo_c1.setBackgroundResource(R.color.colorPrimary);
            TextView tv_category1 = new TextView(this);
            tv_category1.setLayoutParams(params_c);
            tv_category1.setText("영접자");
            tv_category1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
            tv_category1.setTextColor(getResources().getColor(R.color.WHITE));

            ImageView iv_line1 = new ImageView(this);
            iv_line1.setLayoutParams(params4);
            iv_line1.setBackgroundResource(R.color.colorPrimary);

            rlo_c1.addView(tv_category1);
            content_check.addView(rlo_c1);
            content_check.addView(iv_line1);

            prev = 0;

            for (int i = 0; i < believer[0].length; i++) {
                r = new RelativeLayout(this);
                r.setLayoutParams(params1);
                r.setBackgroundResource(R.color.WHITE);
                r.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        for (int i = 0; i < rlo_b.size(); i++) {
                            if (view == rlo_b.get(i)) {
                                if ((Boolean) isOpen_b.get(i)) {
                                    isOpen_b.set(i, false);
                                    for (int n = 0; n < prev + 6; n++) {
                                        content_check.removeViewAt(content_check.indexOfChild((View) rlo_b.get(i)) + 1);
                                    }
                                    prev = 0;
                                } else {
                                    for (int j = 0; j < isOpen_b.size(); j++) {
                                        if ((Boolean) isOpen_b.get(j)) {
                                            isOpen_b.set(j, false);
                                            for (int n = 0; n < prev + 6; n++) {
                                                content_check.removeViewAt(content_check.indexOfChild((View) rlo_b.get(j)) + 1);
                                            }
                                            prev = 0;
                                            break;
                                        }
                                    }
                                    for (int j = 0; j < isOpen_wm.size(); j++) {
                                        if ((Boolean) isOpen_wm.get(j)) {
                                            isOpen_wm.set(j, false);
                                            for (int n = 0; n < prev + 4; n++) {
                                                content_check.removeViewAt(content_check.indexOfChild((View) rlo_wm.get(j)) + 1);
                                            }
                                            prev = 0;
                                            break;
                                        }
                                    }
                                    isOpen_b.set(i, true);

                                    RelativeLayout r1 = new RelativeLayout(context);
                                    r1.setLayoutParams(params1);
                                    r1.setBackgroundResource(R.color.WHITE);
                                    TextView t1 = new TextView(context);
                                    t1.setLayoutParams(params2);
                                    t1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                                    t1.setTextColor(getResources().getColor(R.color.BLACK));
                                    t1.setText("전도자");
                                    et1 = new EditText(context);
                                    et1.setLayoutParams(params3);
                                    et1.setBackgroundResource(android.R.drawable.editbox_background_normal);
                                    et1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                                    et1.setTextColor(getResources().getColor(R.color.BLACK));
                                    et1.setSingleLine();
                                    et1.setImeOptions(EditorInfo.IME_ACTION_DONE);
                                    if (b_preacher.get(i).toString().equals("---")) {
                                        et1.setText("");
                                    } else {
                                        et1.setText((String) b_preacher.get(i));
                                    }
                                    et1.setId(98765432 + i);
                                    RelativeLayout.LayoutParams p1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                                            RelativeLayout.LayoutParams.WRAP_CONTENT);
                                    p1.addRule(RelativeLayout.CENTER_VERTICAL);
                                    p1.addRule(RelativeLayout.LEFT_OF, et1.getId());
                                    p1.setMargins(getResources().getDimensionPixelSize(R.dimen.default_margin),
                                            getResources().getDimensionPixelSize(R.dimen.default_margin),
                                            0,
                                            getResources().getDimensionPixelSize(R.dimen.default_margin));
                                    ImageView iv1 = new ImageView(context);
                                    iv1.setLayoutParams(p1);
                                    iv1.setBackgroundResource(R.drawable.search_btn);
                                    iv1.setId(987654321 + i);
                                    iv1.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            int key = (int) view.getId() - 987654321;
                                            key_number = key;
                                            for (int k = 0; k < prev; k++) {
                                                content_check.removeViewAt(content_check.indexOfChild((View) rlo_b.get(key)) + 1);
                                            }
                                            prev = 0;

                                            if (et1.getText().toString().equals("")) {
                                                new AlertDialog.Builder(FpCheckActivity.this)
                                                        .setTitle("현장전도관리")
                                                        .setMessage("이름을 입력하세요.")
                                                        .setPositiveButton("확인", null)
                                                        .show();
                                            } else {
                                                isDone = false;
                                                client = new Client(FpCheckActivity.this, "getSearchResultForFruits", CHECK_TYPE, et1.getText().toString());
                                                client.execute();

                                                while (!isDone) {
                                                    try {
                                                        Thread.sleep(100);
                                                    } catch (InterruptedException e) {
                                                    }
                                                    isDone = client.isDone();
                                                }

                                                if (client.isAlive()) {
                                                    candidates = client.getCANDIDATES();
                                                    prev = candidates[0].length;
                                                    iv_addbtn = new ImageView[prev];

                                                    if (prev == 0) {
                                                        r = new RelativeLayout(context);
                                                        r.setLayoutParams(params1);
                                                        r.setBackgroundResource(R.color.search);

                                                        t = new TextView(context);
                                                        t.setLayoutParams(params2);
                                                        t.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                                                        t.setTextColor(getResources().getColor(R.color.GRAY));

                                                        r.addView(t);

                                                        t.setText("출석 명단 검색 결과가 없습니다.");
                                                        content_check.addView(r, content_check.indexOfChild((View) rlo_b.get(key)) + 1);

                                                        prev = 1;
                                                    } else {
                                                        for (int j = 0; j < candidates[0].length; j++) {
                                                            r = new RelativeLayout(context);
                                                            r.setLayoutParams(params1);
                                                            r.setBackgroundResource(R.color.search);

                                                            t = new TextView(context);
                                                            t.setLayoutParams(params2);
                                                            t.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                                                            t.setTextColor(getResources().getColor(R.color.GRAY));

                                                            iv_addbtn[j] = new ImageView(context);
                                                            iv_addbtn[j].setLayoutParams(params5);
                                                            iv_addbtn[j].setBackgroundResource(R.drawable.add_btn);
                                                            iv_addbtn[j].setOnClickListener(new View.OnClickListener() {
                                                                @Override
                                                                public void onClick(View view) {
                                                                    for (int i = 0; i < iv_addbtn.length; i++) {
                                                                        if (view == iv_addbtn[i]) {
                                                                            for (int k = 0; k < prev; k++) {
                                                                                content_check.removeViewAt(content_check.indexOfChild((View) rlo_b.get(key_number)) + 1);
                                                                            }
                                                                            prev = 0;
                                                                            et1.setText(candidates[0][i]);
                                                                            b_preacher.set(key_number, candidates[0][i]);
                                                                            if (b_remeet.get(key_number).equals("1")) {
                                                                                ((TextView) tv_b.get(key_number)).setText("[" + b_preacher.get(key_number) + "]   " + b_believer.get(key_number) + "   <재만남>");
                                                                            } else {
                                                                                ((TextView) tv_b.get(key_number)).setText("[" + b_preacher.get(key_number) + "]   " + b_believer.get(key_number));
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            });

                                                            r.addView(t);
                                                            r.addView(iv_addbtn[j]);

                                                            t.setText("[" + candidates[1][j] + "]  " + candidates[0][j]);
                                                            content_check.addView(r, content_check.indexOfChild((View) rlo_b.get(key)) + 1);
                                                        }
                                                    }

                                                } else {
                                                    intent = new Intent(context, NotfoundActivity.class);
                                                    startActivity(intent);
                                                    FpCheckActivity.this.finish();
                                                }
                                            }
                                        }
                                    });
                                    r1.addView(t1);
                                    r1.addView(et1);
                                    r1.addView(iv1);

                                    RelativeLayout r2 = new RelativeLayout(context);
                                    r2.setLayoutParams(params1);
                                    r2.setBackgroundResource(R.color.WHITE);
                                    TextView t2 = new TextView(context);
                                    t2.setLayoutParams(params2);
                                    t2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                                    t2.setTextColor(getResources().getColor(R.color.BLACK));
                                    t2.setText("영접자");
                                    et2 = new EditText(context);
                                    et2.setLayoutParams(params3);
                                    et2.setBackgroundResource(android.R.drawable.editbox_background_normal);
                                    et2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                                    et2.setTextColor(getResources().getColor(R.color.BLACK));
                                    et2.setSingleLine();
                                    et2.setImeOptions(EditorInfo.IME_ACTION_NEXT);
                                    et2.setText((String) b_believer.get(i));
                                    et2.setId(200000000 + i);
                                    et2.addTextChangedListener(new TextWatcher() {
                                        @Override
                                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                        }

                                        @Override
                                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                            int key = (int) et2.getId() - 200000000;
                                            b_believer.set(key, charSequence.toString());
                                            if (b_remeet.get(key).equals("1")) {
                                                ((TextView) tv_b.get(key)).setText("[" + b_preacher.get(key) + "]   " + b_believer.get(key) + "   <재만남>");
                                            } else {
                                                ((TextView) tv_b.get(key)).setText("[" + b_preacher.get(key) + "]   " + b_believer.get(key));
                                            }
                                        }

                                        @Override
                                        public void afterTextChanged(Editable editable) {
                                        }
                                    });
                                    r2.addView(t2);
                                    r2.addView(et2);

                                    RelativeLayout r3 = new RelativeLayout(context);
                                    r3.setLayoutParams(params1);
                                    r3.setBackgroundResource(R.color.WHITE);
                                    TextView t3 = new TextView(context);
                                    t3.setLayoutParams(params2);
                                    t3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                                    t3.setTextColor(getResources().getColor(R.color.BLACK));
                                    t3.setText("재만남");
                                    CheckBox cb3 = new CheckBox(context);
                                    cb3.setLayoutParams(params5);
                                    cb3.setId(10000000 + i);
                                    if (b_remeet.get(i).equals("1")) {
                                        cb3.setChecked(true);
                                    } else {
                                        cb3.setChecked(false);
                                    }
                                    cb3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                        @Override
                                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                            int key = (int) compoundButton.getId() - 10000000;
                                            if (compoundButton.isChecked()) {
                                                b_remeet.set(key, "1");
                                                ((TextView) tv_b.get(key)).setText("[" + b_preacher.get(key) + "]   " + b_believer.get(key) + "   <재만남>");
                                            } else {
                                                b_remeet.set(key, "0");
                                                ((TextView) tv_b.get(key)).setText("[" + b_preacher.get(key) + "]   " + b_believer.get(key));
                                            }
                                        }
                                    });
                                    r3.addView(t3);
                                    r3.addView(cb3);

                                    RelativeLayout r4 = new RelativeLayout(context);
                                    r4.setLayoutParams(params1);
                                    r4.setBackgroundResource(R.color.WHITE);
                                    TextView t4 = new TextView(context);
                                    t4.setLayoutParams(params2);
                                    t4.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                                    t4.setTextColor(getResources().getColor(R.color.BLACK));
                                    t4.setText("사역자");
                                    et4 = new EditText(context);
                                    et4.setLayoutParams(params3);
                                    et4.setBackgroundResource(android.R.drawable.editbox_background_normal);
                                    et4.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                                    et4.setTextColor(getResources().getColor(R.color.BLACK));
                                    et4.setSingleLine();
                                    et4.setImeOptions(EditorInfo.IME_ACTION_NEXT);
                                    et4.setText((String) b_teacher.get(i));
                                    et4.setId(400000000 + i);
                                    et4.addTextChangedListener(new TextWatcher() {
                                        @Override
                                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                        }

                                        @Override
                                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                            int key = (int) et4.getId() - 400000000;
                                            b_teacher.set(key, charSequence.toString());
                                        }

                                        @Override
                                        public void afterTextChanged(Editable editable) {
                                        }
                                    });
                                    r4.addView(t4);
                                    r4.addView(et4);

                                    RelativeLayout r5 = new RelativeLayout(context);
                                    r5.setLayoutParams(params1);
                                    r5.setBackgroundResource(R.color.WHITE);
                                    TextView t5 = new TextView(context);
                                    t5.setLayoutParams(params2);
                                    t5.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                                    t5.setTextColor(getResources().getColor(R.color.BLACK));
                                    t5.setText("나이");
                                    et5 = new EditText(context);
                                    et5.setLayoutParams(params3);
                                    et5.setBackgroundResource(android.R.drawable.editbox_background_normal);
                                    et5.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                                    et5.setTextColor(getResources().getColor(R.color.BLACK));
                                    et5.setSingleLine();
                                    et5.setImeOptions(EditorInfo.IME_ACTION_NEXT);
                                    et5.setInputType(InputType.TYPE_CLASS_NUMBER);
                                    et5.setText((String) b_age.get(i));
                                    et5.setId(500000000 + i);
                                    et5.addTextChangedListener(new TextWatcher() {
                                        @Override
                                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                        }

                                        @Override
                                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                            int key = (int) et5.getId() - 500000000;
                                            b_age.set(key, charSequence.toString());
                                        }

                                        @Override
                                        public void afterTextChanged(Editable editable) {
                                        }
                                    });
                                    r5.addView(t5);
                                    r5.addView(et5);

                                    RelativeLayout r6 = new RelativeLayout(context);
                                    r6.setLayoutParams(params1);
                                    r6.setBackgroundResource(R.color.WHITE);
                                    TextView t6 = new TextView(context);
                                    t6.setLayoutParams(params2);
                                    t6.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                                    t6.setTextColor(getResources().getColor(R.color.BLACK));
                                    t6.setText("전화번호");
                                    et6 = new EditText(context);
                                    et6.setLayoutParams(params3);
                                    et6.setBackgroundResource(android.R.drawable.editbox_background_normal);
                                    et6.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                                    et6.setTextColor(getResources().getColor(R.color.BLACK));
                                    et6.setSingleLine();
                                    et6.setImeOptions(EditorInfo.IME_ACTION_DONE);
                                    et6.setInputType(InputType.TYPE_CLASS_NUMBER);
                                    et6.setText((String) b_phone.get(i));
                                    et6.setId(600000000 + i);
                                    et6.addTextChangedListener(new TextWatcher() {
                                        @Override
                                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                        }

                                        @Override
                                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                            int key = (int) et6.getId() - 600000000;
                                            b_phone.set(key, charSequence.toString());
                                        }

                                        @Override
                                        public void afterTextChanged(Editable editable) {
                                        }
                                    });
                                    r6.addView(t6);
                                    r6.addView(et6);

                                    content_check.addView(r6, content_check.indexOfChild((View) rlo_b.get(i)) + 1);
                                    content_check.addView(r5, content_check.indexOfChild((View) rlo_b.get(i)) + 1);
                                    content_check.addView(r4, content_check.indexOfChild((View) rlo_b.get(i)) + 1);
                                    content_check.addView(r3, content_check.indexOfChild((View) rlo_b.get(i)) + 1);
                                    content_check.addView(r2, content_check.indexOfChild((View) rlo_b.get(i)) + 1);
                                    content_check.addView(r1, content_check.indexOfChild((View) rlo_b.get(i)) + 1);
                                }
                                break;
                            }
                        }
                    }
                });

                t = new TextView(this);
                t.setLayoutParams(params2);
                t.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                t.setTextColor(getResources().getColor(R.color.BLACK));
                if (believer[5][i].equals("1.0")) {
                    t.setText("[" + believer[0][i] + "]   " + believer[1][i] + "   <재만남>");
                } else {
                    t.setText("[" + believer[0][i] + "]   " + believer[1][i]);
                }

                iv = new ImageView(this);
                iv.setLayoutParams(params5);
                iv.setBackgroundResource(R.drawable.remove_btn);
                iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        for (int i = 0; i < iv_b_btn.size(); i++) {
                            if (view == iv_b_btn.get(i)) {
                                index_number = i;
                                new AlertDialog.Builder(FpCheckActivity.this)
                                        .setTitle("현장전도관리")
                                        .setMessage("정말로 삭제하시겠습니까?")
                                        .setNegativeButton("취소", null)
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i2) {
                                                if ((Boolean) isOpen_b.get(index_number)) {
                                                    for (int n = 0; n < prev + 7; n++) {
                                                        content_check.removeViewAt(content_check.indexOfChild((View) rlo_b.get(index_number)) + 1);
                                                    }
                                                    content_check.removeViewAt(content_check.indexOfChild((View) rlo_b.get(index_number)));
                                                    prev = 0;
                                                } else {
                                                    content_check.removeView((View) rlo_b.get(index_number));
                                                    content_check.removeView((View) iv_b_line.get(index_number));
                                                }
                                                rlo_b.remove(index_number);
                                                tv_b.remove(index_number);
                                                iv_b_btn.remove(index_number);
                                                iv_b_line.remove(index_number);
                                                isOpen_b.remove(index_number);
                                                b_preacher.remove(index_number);
                                                b_believer.remove(index_number);
                                                b_teacher.remove(index_number);
                                                b_age.remove(index_number);
                                                b_phone.remove(index_number);
                                                b_remeet.remove(index_number);
                                            }
                                        })
                                        .show();
                                break;
                            }
                        }
                    }
                });

                iv_line_default = new ImageView(this);
                iv_line_default.setLayoutParams(params4);
                iv_line_default.setBackgroundResource(R.color.colorPrimary);

                r.addView(t);
                r.addView(iv);

                rlo_b.add(r);
                tv_b.add(t);
                iv_b_btn.add(iv);
                iv_b_line.add(iv_line_default);
                isOpen_b.add(false);
                b_preacher.add(believer[0][i]);
                b_believer.add(believer[1][i]);
                b_teacher.add(believer[2][i]);
                b_age.add(believer[3][i]);
                b_phone.add(believer[4][i]);
                b_remeet.add(believer[5][i]);

                content_check.addView((View) rlo_b.get(i));
                content_check.addView((View) iv_b_line.get(i));
            }

            rlo_b_plus = new RelativeLayout(this);
            rlo_b_plus.setLayoutParams(params1);
            rlo_b_plus.setBackgroundResource(R.color.WHITE);
            rlo_b_plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (int j = 0; j < isOpen_b.size(); j++) {
                        if ((Boolean) isOpen_b.get(j)) {
                            isOpen_b.set(j, false);
                            for (int n = 0; n < prev + 6; n++) {
                                content_check.removeViewAt(content_check.indexOfChild((View) rlo_b.get(j)) + 1);
                            }
                            prev = 0;
                            break;
                        }
                    }
                    for (int j = 0; j < isOpen_wm.size(); j++) {
                        if ((Boolean) isOpen_wm.get(j)) {
                            isOpen_wm.set(j, false);
                            for (int n = 0; n < prev + 4; n++) {
                                content_check.removeViewAt(content_check.indexOfChild((View) rlo_wm.get(j)) + 1);
                            }
                            prev = 0;
                            break;
                        }
                    }

                    r = new RelativeLayout(context);
                    r.setLayoutParams(params1);
                    r.setBackgroundResource(R.color.WHITE);
                    r.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            for (int i = 0; i < rlo_b.size(); i++) {
                                if (view == rlo_b.get(i)) {
                                    if ((Boolean) isOpen_b.get(i)) {
                                        isOpen_b.set(i, false);
                                        for (int n = 0; n < prev + 6; n++) {
                                            content_check.removeViewAt(content_check.indexOfChild((View) rlo_b.get(i)) + 1);
                                        }
                                        prev = 0;
                                    } else {
                                        for (int j = 0; j < isOpen_b.size(); j++) {
                                            if ((Boolean) isOpen_b.get(j)) {
                                                isOpen_b.set(j, false);
                                                for (int n = 0; n < prev + 6; n++) {
                                                    content_check.removeViewAt(content_check.indexOfChild((View) rlo_b.get(j)) + 1);
                                                }
                                                prev = 0;
                                                break;
                                            }
                                        }
                                        for (int j = 0; j < isOpen_wm.size(); j++) {
                                            if ((Boolean) isOpen_wm.get(j)) {
                                                isOpen_wm.set(j, false);
                                                for (int n = 0; n < prev + 4; n++) {
                                                    content_check.removeViewAt(content_check.indexOfChild((View) rlo_wm.get(j)) + 1);
                                                }
                                                prev = 0;
                                                break;
                                            }
                                        }
                                        isOpen_b.set(i, true);

                                        RelativeLayout r1 = new RelativeLayout(context);
                                        r1.setLayoutParams(params1);
                                        r1.setBackgroundResource(R.color.WHITE);
                                        TextView t1 = new TextView(context);
                                        t1.setLayoutParams(params2);
                                        t1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                                        t1.setTextColor(getResources().getColor(R.color.BLACK));
                                        t1.setText("전도자");
                                        et1 = new EditText(context);
                                        et1.setLayoutParams(params3);
                                        et1.setBackgroundResource(android.R.drawable.editbox_background_normal);
                                        et1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                                        et1.setTextColor(getResources().getColor(R.color.BLACK));
                                        et1.setSingleLine();
                                        et1.setImeOptions(EditorInfo.IME_ACTION_DONE);
                                        if (b_preacher.get(i).toString().equals("---")) {
                                            et1.setText("");
                                        } else {
                                            et1.setText((String) b_preacher.get(i));
                                        }
                                        et1.setId(98765432 + i);
                                        RelativeLayout.LayoutParams p1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                                                RelativeLayout.LayoutParams.WRAP_CONTENT);
                                        p1.addRule(RelativeLayout.CENTER_VERTICAL);
                                        p1.addRule(RelativeLayout.LEFT_OF, et1.getId());
                                        p1.setMargins(getResources().getDimensionPixelSize(R.dimen.default_margin),
                                                getResources().getDimensionPixelSize(R.dimen.default_margin),
                                                0,
                                                getResources().getDimensionPixelSize(R.dimen.default_margin));
                                        ImageView iv1 = new ImageView(context);
                                        iv1.setLayoutParams(p1);
                                        iv1.setBackgroundResource(R.drawable.search_btn);
                                        iv1.setId(987654321 + i);
                                        iv1.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                int key = (int) view.getId() - 987654321;
                                                key_number = key;
                                                for (int k = 0; k < prev; k++) {
                                                    content_check.removeViewAt(content_check.indexOfChild((View) rlo_b.get(key)) + 1);
                                                }
                                                prev = 0;

                                                if (et1.getText().toString().equals("")) {
                                                    new AlertDialog.Builder(FpCheckActivity.this)
                                                            .setTitle("현장전도관리")
                                                            .setMessage("이름을 입력하세요.")
                                                            .setPositiveButton("확인", null)
                                                            .show();
                                                } else {
                                                    isDone = false;
                                                    client = new Client(FpCheckActivity.this, "getSearchResultForFruits", CHECK_TYPE, et1.getText().toString());
                                                    client.execute();

                                                    while (!isDone) {
                                                        try {
                                                            Thread.sleep(100);
                                                        } catch (InterruptedException e) {
                                                        }
                                                        isDone = client.isDone();
                                                    }

                                                    if (client.isAlive()) {
                                                        candidates = client.getCANDIDATES();
                                                        prev = candidates[0].length;
                                                        iv_addbtn = new ImageView[prev];

                                                        if (prev == 0) {
                                                            r = new RelativeLayout(context);
                                                            r.setLayoutParams(params1);
                                                            r.setBackgroundResource(R.color.search);

                                                            t = new TextView(context);
                                                            t.setLayoutParams(params2);
                                                            t.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                                                            t.setTextColor(getResources().getColor(R.color.GRAY));

                                                            r.addView(t);

                                                            t.setText("출석 명단 검색 결과가 없습니다.");
                                                            content_check.addView(r, content_check.indexOfChild((View) rlo_b.get(key)) + 1);

                                                            prev = 1;
                                                        } else {
                                                            for (int j = 0; j < candidates[0].length; j++) {
                                                                r = new RelativeLayout(context);
                                                                r.setLayoutParams(params1);
                                                                r.setBackgroundResource(R.color.search);

                                                                t = new TextView(context);
                                                                t.setLayoutParams(params2);
                                                                t.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                                                                t.setTextColor(getResources().getColor(R.color.GRAY));

                                                                iv_addbtn[j] = new ImageView(context);
                                                                iv_addbtn[j].setLayoutParams(params5);
                                                                iv_addbtn[j].setBackgroundResource(R.drawable.add_btn);
                                                                iv_addbtn[j].setOnClickListener(new View.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(View view) {
                                                                        for (int i = 0; i < iv_addbtn.length; i++) {
                                                                            if (view == iv_addbtn[i]) {
                                                                                for (int k = 0; k < prev; k++) {
                                                                                    content_check.removeViewAt(content_check.indexOfChild((View) rlo_b.get(key_number)) + 1);
                                                                                }
                                                                                prev = 0;
                                                                                et1.setText(candidates[0][i]);
                                                                                b_preacher.set(key_number, candidates[0][i]);
                                                                                if (b_remeet.get(key_number).equals("1")) {
                                                                                    ((TextView) tv_b.get(key_number)).setText("[" + b_preacher.get(key_number) + "]   " + b_believer.get(key_number) + "   <재만남>");
                                                                                } else {
                                                                                    ((TextView) tv_b.get(key_number)).setText("[" + b_preacher.get(key_number) + "]   " + b_believer.get(key_number));
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                });

                                                                r.addView(t);
                                                                r.addView(iv_addbtn[j]);

                                                                t.setText("[" + candidates[1][j] + "]  " + candidates[0][j]);
                                                                content_check.addView(r, content_check.indexOfChild((View) rlo_b.get(key)) + 1);
                                                            }
                                                        }

                                                    } else {
                                                        intent = new Intent(context, NotfoundActivity.class);
                                                        startActivity(intent);
                                                        FpCheckActivity.this.finish();
                                                    }
                                                }
                                            }
                                        });
                                        r1.addView(t1);
                                        r1.addView(et1);
                                        r1.addView(iv1);

                                        RelativeLayout r2 = new RelativeLayout(context);
                                        r2.setLayoutParams(params1);
                                        r2.setBackgroundResource(R.color.WHITE);
                                        TextView t2 = new TextView(context);
                                        t2.setLayoutParams(params2);
                                        t2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                                        t2.setTextColor(getResources().getColor(R.color.BLACK));
                                        t2.setText("영접자");
                                        et2 = new EditText(context);
                                        et2.setLayoutParams(params3);
                                        et2.setBackgroundResource(android.R.drawable.editbox_background_normal);
                                        et2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                                        et2.setTextColor(getResources().getColor(R.color.BLACK));
                                        et2.setSingleLine();
                                        et2.setImeOptions(EditorInfo.IME_ACTION_NEXT);
                                        et2.setText((String) b_believer.get(i));
                                        et2.setId(200000000 + i);
                                        et2.addTextChangedListener(new TextWatcher() {
                                            @Override
                                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                            }

                                            @Override
                                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                                int key = (int) et2.getId() - 200000000;
                                                b_believer.set(key, charSequence.toString());
                                                if (b_remeet.get(key).equals("1")) {
                                                    ((TextView) tv_b.get(key)).setText("[" + b_preacher.get(key) + "]   " + b_believer.get(key) + "   <재만남>");
                                                } else {
                                                    ((TextView) tv_b.get(key)).setText("[" + b_preacher.get(key) + "]   " + b_believer.get(key));
                                                }
                                            }

                                            @Override
                                            public void afterTextChanged(Editable editable) {
                                            }
                                        });
                                        r2.addView(t2);
                                        r2.addView(et2);

                                        RelativeLayout r3 = new RelativeLayout(context);
                                        r3.setLayoutParams(params1);
                                        r3.setBackgroundResource(R.color.WHITE);
                                        TextView t3 = new TextView(context);
                                        t3.setLayoutParams(params2);
                                        t3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                                        t3.setTextColor(getResources().getColor(R.color.BLACK));
                                        t3.setText("재만남");
                                        CheckBox cb3 = new CheckBox(context);
                                        cb3.setLayoutParams(params5);
                                        cb3.setId(10000000 + i);
                                        if (b_remeet.get(i).equals("1")) {
                                            cb3.setChecked(true);
                                        } else {
                                            cb3.setChecked(false);
                                        }
                                        cb3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                            @Override
                                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                                int key = (int) compoundButton.getId() - 10000000;
                                                if (compoundButton.isChecked()) {
                                                    b_remeet.set(key, "1");
                                                    ((TextView) tv_b.get(key)).setText("[" + b_preacher.get(key) + "]   " + b_believer.get(key) + "   <재만남>");
                                                } else {
                                                    b_remeet.set(key, "0");
                                                    ((TextView) tv_b.get(key)).setText("[" + b_preacher.get(key) + "]   " + b_believer.get(key));
                                                }
                                            }
                                        });
                                        r3.addView(t3);
                                        r3.addView(cb3);

                                        RelativeLayout r4 = new RelativeLayout(context);
                                        r4.setLayoutParams(params1);
                                        r4.setBackgroundResource(R.color.WHITE);
                                        TextView t4 = new TextView(context);
                                        t4.setLayoutParams(params2);
                                        t4.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                                        t4.setTextColor(getResources().getColor(R.color.BLACK));
                                        t4.setText("사역자");
                                        et4 = new EditText(context);
                                        et4.setLayoutParams(params3);
                                        et4.setBackgroundResource(android.R.drawable.editbox_background_normal);
                                        et4.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                                        et4.setTextColor(getResources().getColor(R.color.BLACK));
                                        et4.setSingleLine();
                                        et4.setImeOptions(EditorInfo.IME_ACTION_NEXT);
                                        et4.setText((String) b_teacher.get(i));
                                        et4.setId(400000000 + i);
                                        et4.addTextChangedListener(new TextWatcher() {
                                            @Override
                                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                            }

                                            @Override
                                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                                int key = (int) et4.getId() - 400000000;
                                                b_teacher.set(key, charSequence.toString());
                                            }

                                            @Override
                                            public void afterTextChanged(Editable editable) {
                                            }
                                        });
                                        r4.addView(t4);
                                        r4.addView(et4);

                                        RelativeLayout r5 = new RelativeLayout(context);
                                        r5.setLayoutParams(params1);
                                        r5.setBackgroundResource(R.color.WHITE);
                                        TextView t5 = new TextView(context);
                                        t5.setLayoutParams(params2);
                                        t5.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                                        t5.setTextColor(getResources().getColor(R.color.BLACK));
                                        t5.setText("나이");
                                        et5 = new EditText(context);
                                        et5.setLayoutParams(params3);
                                        et5.setBackgroundResource(android.R.drawable.editbox_background_normal);
                                        et5.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                                        et5.setTextColor(getResources().getColor(R.color.BLACK));
                                        et5.setSingleLine();
                                        et5.setImeOptions(EditorInfo.IME_ACTION_NEXT);
                                        et5.setInputType(InputType.TYPE_CLASS_NUMBER);
                                        et5.setText((String) b_age.get(i));
                                        et5.setId(500000000 + i);
                                        et5.addTextChangedListener(new TextWatcher() {
                                            @Override
                                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                            }

                                            @Override
                                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                                int key = (int) et5.getId() - 500000000;
                                                b_age.set(key, charSequence.toString());
                                            }

                                            @Override
                                            public void afterTextChanged(Editable editable) {
                                            }
                                        });
                                        r5.addView(t5);
                                        r5.addView(et5);

                                        RelativeLayout r6 = new RelativeLayout(context);
                                        r6.setLayoutParams(params1);
                                        r6.setBackgroundResource(R.color.WHITE);
                                        TextView t6 = new TextView(context);
                                        t6.setLayoutParams(params2);
                                        t6.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                                        t6.setTextColor(getResources().getColor(R.color.BLACK));
                                        t6.setText("전화번호");
                                        et6 = new EditText(context);
                                        et6.setLayoutParams(params3);
                                        et6.setBackgroundResource(android.R.drawable.editbox_background_normal);
                                        et6.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                                        et6.setTextColor(getResources().getColor(R.color.BLACK));
                                        et6.setSingleLine();
                                        et6.setImeOptions(EditorInfo.IME_ACTION_DONE);
                                        et6.setInputType(InputType.TYPE_CLASS_NUMBER);
                                        et6.setText((String) b_phone.get(i));
                                        et6.setId(600000000 + i);
                                        et6.addTextChangedListener(new TextWatcher() {
                                            @Override
                                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                            }

                                            @Override
                                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                                int key = (int) et6.getId() - 600000000;
                                                b_phone.set(key, charSequence.toString());
                                            }

                                            @Override
                                            public void afterTextChanged(Editable editable) {
                                            }
                                        });
                                        r6.addView(t6);
                                        r6.addView(et6);

                                        content_check.addView(r6, content_check.indexOfChild((View) rlo_b.get(i)) + 1);
                                        content_check.addView(r5, content_check.indexOfChild((View) rlo_b.get(i)) + 1);
                                        content_check.addView(r4, content_check.indexOfChild((View) rlo_b.get(i)) + 1);
                                        content_check.addView(r3, content_check.indexOfChild((View) rlo_b.get(i)) + 1);
                                        content_check.addView(r2, content_check.indexOfChild((View) rlo_b.get(i)) + 1);
                                        content_check.addView(r1, content_check.indexOfChild((View) rlo_b.get(i)) + 1);
                                    }
                                    break;
                                }
                            }
                        }
                    });

                    t = new TextView(context);
                    t.setLayoutParams(params2);
                    t.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                    t.setTextColor(getResources().getColor(R.color.BLACK));
                    t.setText("[---]   ---");

                    iv = new ImageView(context);
                    iv.setLayoutParams(params5);
                    iv.setBackgroundResource(R.drawable.remove_btn);
                    iv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            for (int i = 0; i < iv_b_btn.size(); i++) {
                                if (view == iv_b_btn.get(i)) {
                                    index_number = i;
                                    new AlertDialog.Builder(FpCheckActivity.this)
                                            .setTitle("현장전도관리")
                                            .setMessage("정말로 삭제하시겠습니까?")
                                            .setNegativeButton("취소", null)
                                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i2) {
                                                    if ((Boolean) isOpen_b.get(index_number)) {
                                                        for (int n = 0; n < prev + 7; n++) {
                                                            content_check.removeViewAt(content_check.indexOfChild((View) rlo_b.get(index_number)) + 1);
                                                        }
                                                        content_check.removeViewAt(content_check.indexOfChild((View) rlo_b.get(index_number)));
                                                        prev = 0;
                                                    } else {
                                                        content_check.removeView((View) rlo_b.get(index_number));
                                                        content_check.removeView((View) iv_b_line.get(index_number));
                                                    }
                                                    rlo_b.remove(index_number);
                                                    tv_b.remove(index_number);
                                                    iv_b_btn.remove(index_number);
                                                    iv_b_line.remove(index_number);
                                                    isOpen_b.remove(index_number);
                                                    b_preacher.remove(index_number);
                                                    b_believer.remove(index_number);
                                                    b_teacher.remove(index_number);
                                                    b_age.remove(index_number);
                                                    b_phone.remove(index_number);
                                                    b_remeet.remove(index_number);
                                                }
                                            })
                                            .show();
                                    break;
                                }
                            }
                        }
                    });

                    iv_line_default = new ImageView(context);
                    iv_line_default.setLayoutParams(params4);
                    iv_line_default.setBackgroundResource(R.color.colorPrimary);

                    r.addView(t);
                    r.addView(iv);

                    rlo_b.add(r);
                    tv_b.add(t);
                    iv_b_btn.add(iv);
                    iv_b_line.add(iv_line_default);
                    isOpen_b.add(false);
                    b_preacher.add("---");
                    b_believer.add("");
                    b_teacher.add("");
                    b_age.add("");
                    b_phone.add("");
                    b_remeet.add("");

                    content_check.addView((View) rlo_b.get(rlo_b.size() - 1), content_check.indexOfChild(rlo_b_plus));
                    content_check.addView((View) iv_b_line.get(iv_b_line.size() - 1), content_check.indexOfChild(rlo_b_plus));

                }
            });
            TextView tv_b_plus = new TextView(this);
            tv_b_plus.setLayoutParams(params8);
            tv_b_plus.setText("+");
            tv_b_plus.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
            tv_b_plus.setTextColor(getResources().getColor(R.color.colorPrimary));

            ImageView iv_line_b_plus = new ImageView(this);
            iv_line_b_plus.setLayoutParams(params4);
            iv_line_b_plus.setBackgroundResource(R.color.colorPrimary);

            rlo_b_plus.addView(tv_b_plus);
            content_check.addView(rlo_b_plus);
            content_check.addView(iv_line_b_plus);


            // Word Movement
            RelativeLayout rlo_c2 = new RelativeLayout(this);
            rlo_c2.setLayoutParams(params1);
            rlo_c2.setBackgroundResource(R.color.colorPrimary);
            TextView tv_category2 = new TextView(this);
            tv_category2.setLayoutParams(params_c);
            tv_category2.setText("말씀운동");
            tv_category2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
            tv_category2.setTextColor(getResources().getColor(R.color.WHITE));

            ImageView iv_line2 = new ImageView(this);
            iv_line2.setLayoutParams(params4);
            iv_line2.setBackgroundResource(R.color.colorPrimary);

            rlo_c2.addView(tv_category2);
            content_check.addView(rlo_c2);
            content_check.addView(iv_line2);

            for (int i = 0; i < wordmovement[0].length; i++) {
                r = new RelativeLayout(this);
                r.setLayoutParams(params1);
                r.setBackgroundResource(R.color.WHITE);
                r.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        for (int i = 0; i < rlo_wm.size(); i++) {
                            if (view == rlo_wm.get(i)) {
                                if ((Boolean) isOpen_wm.get(i)) {
                                    isOpen_wm.set(i, false);
                                    for (int n = 0; n < prev + 4; n++) {
                                        content_check.removeViewAt(content_check.indexOfChild((View) rlo_wm.get(i)) + 1);
                                    }
                                    prev = 0;
                                } else {
                                    for (int j = 0; j < isOpen_b.size(); j++) {
                                        if ((Boolean) isOpen_b.get(j)) {
                                            isOpen_b.set(j, false);
                                            for (int n = 0; n < prev + 6; n++) {
                                                content_check.removeViewAt(content_check.indexOfChild((View) rlo_b.get(j)) + 1);
                                            }
                                            prev = 0;
                                            break;
                                        }
                                    }
                                    for (int j = 0; j < isOpen_wm.size(); j++) {
                                        if ((Boolean) isOpen_wm.get(j)) {
                                            isOpen_wm.set(j, false);
                                            for (int n = 0; n < prev + 4; n++) {
                                                content_check.removeViewAt(content_check.indexOfChild((View) rlo_wm.get(j)) + 1);
                                            }
                                            prev = 0;
                                            break;
                                        }
                                    }
                                    isOpen_wm.set(i, true);

                                    RelativeLayout r1 = new RelativeLayout(context);
                                    r1.setLayoutParams(params1);
                                    r1.setBackgroundResource(R.color.WHITE);
                                    TextView t1 = new TextView(context);
                                    t1.setLayoutParams(params2);
                                    t1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                                    t1.setTextColor(getResources().getColor(R.color.BLACK));
                                    t1.setText("사역자");
                                    et1 = new EditText(context);
                                    et1.setLayoutParams(params3);
                                    et1.setBackgroundResource(android.R.drawable.editbox_background_normal);
                                    et1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                                    et1.setTextColor(getResources().getColor(R.color.BLACK));
                                    et1.setSingleLine();
                                    et1.setImeOptions(EditorInfo.IME_ACTION_DONE);
                                    if (wm_teacher.get(i).toString().equals("---")) {
                                        et1.setText("");
                                    } else {
                                        et1.setText((String) wm_teacher.get(i));
                                    }
                                    et1.setId(9876543 + i);
                                    RelativeLayout.LayoutParams p1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                                            RelativeLayout.LayoutParams.WRAP_CONTENT);
                                    p1.addRule(RelativeLayout.CENTER_VERTICAL);
                                    p1.addRule(RelativeLayout.LEFT_OF, et1.getId());
                                    p1.setMargins(getResources().getDimensionPixelSize(R.dimen.default_margin),
                                            getResources().getDimensionPixelSize(R.dimen.default_margin),
                                            0,
                                            getResources().getDimensionPixelSize(R.dimen.default_margin));
                                    ImageView iv1 = new ImageView(context);
                                    iv1.setLayoutParams(p1);
                                    iv1.setBackgroundResource(R.drawable.search_btn);
                                    iv1.setId(987659876 + i);
                                    iv1.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            int key = (int) view.getId() - 987659876;
                                            key_number = key;
                                            for (int k = 0; k < prev; k++) {
                                                content_check.removeViewAt(content_check.indexOfChild((View) rlo_wm.get(key)) + 1);
                                            }
                                            prev = 0;

                                            if (et1.getText().toString().equals("")) {
                                                new AlertDialog.Builder(FpCheckActivity.this)
                                                        .setTitle("현장전도관리")
                                                        .setMessage("이름을 입력하세요.")
                                                        .setPositiveButton("확인", null)
                                                        .show();
                                            } else {
                                                isDone = false;
                                                client = new Client(FpCheckActivity.this, "getSearchResultForFruits", CHECK_TYPE, et1.getText().toString());
                                                client.execute();

                                                while (!isDone) {
                                                    try {
                                                        Thread.sleep(100);
                                                    } catch (InterruptedException e) {
                                                    }
                                                    isDone = client.isDone();
                                                }

                                                if (client.isAlive()) {
                                                    candidates = client.getCANDIDATES();
                                                    prev = candidates[0].length;
                                                    iv_addbtn = new ImageView[prev];

                                                    if (prev == 0) {
                                                        r = new RelativeLayout(context);
                                                        r.setLayoutParams(params1);
                                                        r.setBackgroundResource(R.color.search);

                                                        t = new TextView(context);
                                                        t.setLayoutParams(params2);
                                                        t.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                                                        t.setTextColor(getResources().getColor(R.color.GRAY));

                                                        r.addView(t);

                                                        t.setText("검색 결과가 없습니다.");
                                                        content_check.addView(r, content_check.indexOfChild((View) rlo_wm.get(key)) + 1);

                                                        prev = 1;
                                                    } else {
                                                        for (int j = 0; j < candidates[0].length; j++) {
                                                            r = new RelativeLayout(context);
                                                            r.setLayoutParams(params1);
                                                            r.setBackgroundResource(R.color.search);

                                                            t = new TextView(context);
                                                            t.setLayoutParams(params2);
                                                            t.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                                                            t.setTextColor(getResources().getColor(R.color.GRAY));

                                                            iv_addbtn[j] = new ImageView(context);
                                                            iv_addbtn[j].setLayoutParams(params5);
                                                            iv_addbtn[j].setBackgroundResource(R.drawable.add_btn);
                                                            iv_addbtn[j].setOnClickListener(new View.OnClickListener() {
                                                                @Override
                                                                public void onClick(View view) {
                                                                    for (int i = 0; i < iv_addbtn.length; i++) {
                                                                        if (view == iv_addbtn[i]) {
                                                                            for (int k = 0; k < prev; k++) {
                                                                                content_check.removeViewAt(content_check.indexOfChild((View) rlo_wm.get(key_number)) + 1);
                                                                            }
                                                                            prev = 0;
                                                                            et1.setText(candidates[0][i]);
                                                                            wm_teacher.set(key_number, candidates[0][i]);
                                                                            ((TextView) tv_wm.get(key_number)).setText("[" + wm_teacher.get(key_number) + "]   " + wm_believer.get(key_number));
                                                                        }
                                                                    }
                                                                }
                                                            });

                                                            r.addView(t);
                                                            r.addView(iv_addbtn[j]);

                                                            t.setText("[" + candidates[1][j] + "]  " + candidates[0][j]);
                                                            content_check.addView(r, content_check.indexOfChild((View) rlo_wm.get(key)) + 1);
                                                        }
                                                    }

                                                } else {
                                                    intent = new Intent(context, NotfoundActivity.class);
                                                    startActivity(intent);
                                                    FpCheckActivity.this.finish();
                                                }
                                            }
                                        }
                                    });
                                    r1.addView(t1);
                                    r1.addView(et1);
                                    r1.addView(iv1);

                                    RelativeLayout r2 = new RelativeLayout(context);
                                    r2.setLayoutParams(params1);
                                    r2.setBackgroundResource(R.color.WHITE);
                                    TextView t2 = new TextView(context);
                                    t2.setLayoutParams(params2);
                                    t2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                                    t2.setTextColor(getResources().getColor(R.color.BLACK));
                                    t2.setText("영접자");
                                    et2 = new EditText(context);
                                    et2.setLayoutParams(params3);
                                    et2.setBackgroundResource(android.R.drawable.editbox_background_normal);
                                    et2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                                    et2.setTextColor(getResources().getColor(R.color.BLACK));
                                    et2.setSingleLine();
                                    et2.setImeOptions(EditorInfo.IME_ACTION_NEXT);
                                    et2.setText((String) wm_believer.get(i));
                                    et2.setId(220000000 + i);
                                    et2.addTextChangedListener(new TextWatcher() {
                                        @Override
                                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                        }

                                        @Override
                                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                            int key = (int) et2.getId() - 220000000;
                                            wm_believer.set(key, charSequence.toString());
                                            ((TextView) tv_wm.get(key)).setText("[" + wm_teacher.get(key) + "]   " + wm_believer.get(key));
                                        }

                                        @Override
                                        public void afterTextChanged(Editable editable) {
                                        }
                                    });
                                    r2.addView(t2);
                                    r2.addView(et2);

                                    RelativeLayout r5 = new RelativeLayout(context);
                                    r5.setLayoutParams(params1);
                                    r5.setBackgroundResource(R.color.WHITE);
                                    TextView t5 = new TextView(context);
                                    t5.setLayoutParams(params2);
                                    t5.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                                    t5.setTextColor(getResources().getColor(R.color.BLACK));
                                    t5.setText("만난횟수");
                                    et5 = new EditText(context);
                                    et5.setLayoutParams(params3);
                                    et5.setBackgroundResource(android.R.drawable.editbox_background_normal);
                                    et5.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                                    et5.setTextColor(getResources().getColor(R.color.BLACK));
                                    et5.setSingleLine();
                                    et5.setImeOptions(EditorInfo.IME_ACTION_NEXT);
                                    et5.setInputType(InputType.TYPE_CLASS_NUMBER);
                                    et5.setText((String) wm_frequency.get(i));
                                    et5.setId(550000000 + i);
                                    et5.addTextChangedListener(new TextWatcher() {
                                        @Override
                                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                        }

                                        @Override
                                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                            int key = (int) et5.getId() - 550000000;
                                            wm_frequency.set(key, charSequence.toString());
                                        }

                                        @Override
                                        public void afterTextChanged(Editable editable) {
                                        }
                                    });
                                    r5.addView(t5);
                                    r5.addView(et5);

                                    RelativeLayout r6 = new RelativeLayout(context);
                                    r6.setLayoutParams(params1);
                                    r6.setBackgroundResource(R.color.WHITE);
                                    TextView t6 = new TextView(context);
                                    t6.setLayoutParams(params2);
                                    t6.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                                    t6.setTextColor(getResources().getColor(R.color.BLACK));
                                    t6.setText("장소");
                                    et6 = new EditText(context);
                                    et6.setLayoutParams(params3);
                                    et6.setBackgroundResource(android.R.drawable.editbox_background_normal);
                                    et6.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                                    et6.setTextColor(getResources().getColor(R.color.BLACK));
                                    et6.setSingleLine();
                                    et6.setImeOptions(EditorInfo.IME_ACTION_DONE);
                                    et6.setText((String) wm_place.get(i));
                                    et6.setId(660000000 + i);
                                    et6.addTextChangedListener(new TextWatcher() {
                                        @Override
                                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                        }

                                        @Override
                                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                            int key = (int) et6.getId() - 660000000;
                                            wm_place.set(key, charSequence.toString());
                                        }

                                        @Override
                                        public void afterTextChanged(Editable editable) {
                                        }
                                    });
                                    r6.addView(t6);
                                    r6.addView(et6);

                                    content_check.addView(r6, content_check.indexOfChild((View) rlo_wm.get(i)) + 1);
                                    content_check.addView(r5, content_check.indexOfChild((View) rlo_wm.get(i)) + 1);
                                    content_check.addView(r2, content_check.indexOfChild((View) rlo_wm.get(i)) + 1);
                                    content_check.addView(r1, content_check.indexOfChild((View) rlo_wm.get(i)) + 1);
                                }
                                break;
                            }
                        }
                    }
                });

                t = new TextView(this);
                t.setLayoutParams(params2);
                t.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                t.setTextColor(getResources().getColor(R.color.BLACK));
                t.setText("[" + wordmovement[0][i] + "]   " + wordmovement[1][i]);

                iv = new ImageView(this);
                iv.setLayoutParams(params5);
                iv.setBackgroundResource(R.drawable.remove_btn);
                iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        for (int i = 0; i < iv_wm_btn.size(); i++) {
                            if (view == iv_wm_btn.get(i)) {
                                index_number = i;
                                new AlertDialog.Builder(FpCheckActivity.this)
                                        .setTitle("현장전도관리")
                                        .setMessage("정말로 삭제하시겠습니까?")
                                        .setNegativeButton("취소", null)
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i2) {
                                                if ((Boolean) isOpen_wm.get(index_number)) {
                                                    for (int n = 0; n < prev + 5; n++) {
                                                        content_check.removeViewAt(content_check.indexOfChild((View) rlo_wm.get(index_number)) + 1);
                                                    }
                                                    content_check.removeViewAt(content_check.indexOfChild((View) rlo_wm.get(index_number)));
                                                    prev = 0;
                                                } else {
                                                    content_check.removeView((View) rlo_wm.get(index_number));
                                                    content_check.removeView((View) iv_wm_line.get(index_number));
                                                }
                                                rlo_wm.remove(index_number);
                                                tv_wm.remove(index_number);
                                                iv_wm_btn.remove(index_number);
                                                iv_wm_line.remove(index_number);
                                                isOpen_wm.remove(index_number);
                                                wm_teacher.remove(index_number);
                                                wm_believer.remove(index_number);
                                                wm_frequency.remove(index_number);
                                                wm_place.remove(index_number);
                                            }
                                        })
                                        .show();
                                break;
                            }
                        }
                    }
                });

                iv_line_default = new ImageView(this);
                iv_line_default.setLayoutParams(params4);
                iv_line_default.setBackgroundResource(R.color.colorPrimary);

                r.addView(t);
                r.addView(iv);

                rlo_wm.add(r);
                tv_wm.add(t);
                iv_wm_btn.add(iv);
                iv_wm_line.add(iv_line_default);
                isOpen_wm.add(false);
                wm_teacher.add(wordmovement[0][i]);
                wm_believer.add(wordmovement[1][i]);
                wm_frequency.add(wordmovement[2][i]);
                wm_place.add(wordmovement[3][i]);

                content_check.addView((View) rlo_wm.get(i));
                content_check.addView((View) iv_wm_line.get(i));
            }

            rlo_wm_plus = new RelativeLayout(this);
            rlo_wm_plus.setLayoutParams(params1);
            rlo_wm_plus.setBackgroundResource(R.color.WHITE);
            rlo_wm_plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (int j = 0; j < isOpen_b.size(); j++) {
                        if ((Boolean) isOpen_b.get(j)) {
                            isOpen_b.set(j, false);
                            for (int n = 0; n < prev + 6; n++) {
                                content_check.removeViewAt(content_check.indexOfChild((View) rlo_b.get(j)) + 1);
                            }
                            prev = 0;
                            break;
                        }
                    }
                    for (int j = 0; j < isOpen_wm.size(); j++) {
                        if ((Boolean) isOpen_wm.get(j)) {
                            isOpen_wm.set(j, false);
                            for (int n = 0; n < prev + 4; n++) {
                                content_check.removeViewAt(content_check.indexOfChild((View) rlo_wm.get(j)) + 1);
                            }
                            prev = 0;
                            break;
                        }
                    }

                    r = new RelativeLayout(context);
                    r.setLayoutParams(params1);
                    r.setBackgroundResource(R.color.WHITE);
                    r.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            for (int i = 0; i < rlo_wm.size(); i++) {
                                if (view == rlo_wm.get(i)) {
                                    if ((Boolean) isOpen_wm.get(i)) {
                                        isOpen_wm.set(i, false);
                                        for (int n = 0; n < prev + 4; n++) {
                                            content_check.removeViewAt(content_check.indexOfChild((View) rlo_wm.get(i)) + 1);
                                        }
                                        prev = 0;
                                    } else {
                                        for (int j = 0; j < isOpen_b.size(); j++) {
                                            if ((Boolean) isOpen_b.get(j)) {
                                                isOpen_b.set(j, false);
                                                for (int n = 0; n < prev + 6; n++) {
                                                    content_check.removeViewAt(content_check.indexOfChild((View) rlo_b.get(j)) + 1);
                                                }
                                                prev = 0;
                                                break;
                                            }
                                        }
                                        for (int j = 0; j < isOpen_wm.size(); j++) {
                                            if ((Boolean) isOpen_wm.get(j)) {
                                                isOpen_wm.set(j, false);
                                                for (int n = 0; n < prev + 4; n++) {
                                                    content_check.removeViewAt(content_check.indexOfChild((View) rlo_wm.get(j)) + 1);
                                                }
                                                prev = 0;
                                                break;
                                            }
                                        }
                                        isOpen_wm.set(i, true);

                                        RelativeLayout r1 = new RelativeLayout(context);
                                        r1.setLayoutParams(params1);
                                        r1.setBackgroundResource(R.color.WHITE);
                                        TextView t1 = new TextView(context);
                                        t1.setLayoutParams(params2);
                                        t1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                                        t1.setTextColor(getResources().getColor(R.color.BLACK));
                                        t1.setText("사역자");
                                        et1 = new EditText(context);
                                        et1.setLayoutParams(params3);
                                        et1.setBackgroundResource(android.R.drawable.editbox_background_normal);
                                        et1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                                        et1.setTextColor(getResources().getColor(R.color.BLACK));
                                        et1.setSingleLine();
                                        et1.setImeOptions(EditorInfo.IME_ACTION_DONE);
                                        if (wm_teacher.get(i).toString().equals("---")) {
                                            et1.setText("");
                                        } else {
                                            et1.setText((String) wm_teacher.get(i));
                                        }
                                        et1.setId(9876543 + i);
                                        RelativeLayout.LayoutParams p1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                                                RelativeLayout.LayoutParams.WRAP_CONTENT);
                                        p1.addRule(RelativeLayout.CENTER_VERTICAL);
                                        p1.addRule(RelativeLayout.LEFT_OF, et1.getId());
                                        p1.setMargins(getResources().getDimensionPixelSize(R.dimen.default_margin),
                                                getResources().getDimensionPixelSize(R.dimen.default_margin),
                                                0,
                                                getResources().getDimensionPixelSize(R.dimen.default_margin));
                                        ImageView iv1 = new ImageView(context);
                                        iv1.setLayoutParams(p1);
                                        iv1.setBackgroundResource(R.drawable.search_btn);
                                        iv1.setId(987659876 + i);
                                        iv1.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                int key = (int) view.getId() - 987659876;
                                                key_number = key;
                                                for (int k = 0; k < prev; k++) {
                                                    content_check.removeViewAt(content_check.indexOfChild((View) rlo_wm.get(key)) + 1);
                                                }
                                                prev = 0;

                                                if (et1.getText().toString().equals("")) {
                                                    new AlertDialog.Builder(FpCheckActivity.this)
                                                            .setTitle("현장전도관리")
                                                            .setMessage("이름을 입력하세요.")
                                                            .setPositiveButton("확인", null)
                                                            .show();
                                                } else {
                                                    isDone = false;
                                                    client = new Client(FpCheckActivity.this, "getSearchResultForFruits", CHECK_TYPE, et1.getText().toString());
                                                    client.execute();

                                                    while (!isDone) {
                                                        try {
                                                            Thread.sleep(100);
                                                        } catch (InterruptedException e) {
                                                        }
                                                        isDone = client.isDone();
                                                    }

                                                    if (client.isAlive()) {
                                                        candidates = client.getCANDIDATES();
                                                        prev = candidates[0].length;
                                                        iv_addbtn = new ImageView[prev];

                                                        if (prev == 0) {
                                                            r = new RelativeLayout(context);
                                                            r.setLayoutParams(params1);
                                                            r.setBackgroundResource(R.color.search);

                                                            t = new TextView(context);
                                                            t.setLayoutParams(params2);
                                                            t.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                                                            t.setTextColor(getResources().getColor(R.color.GRAY));

                                                            r.addView(t);

                                                            t.setText("검색 결과가 없습니다.");
                                                            content_check.addView(r, content_check.indexOfChild((View) rlo_wm.get(key)) + 1);

                                                            prev = 1;
                                                        } else {
                                                            for (int j = 0; j < candidates[0].length; j++) {
                                                                r = new RelativeLayout(context);
                                                                r.setLayoutParams(params1);
                                                                r.setBackgroundResource(R.color.search);

                                                                t = new TextView(context);
                                                                t.setLayoutParams(params2);
                                                                t.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                                                                t.setTextColor(getResources().getColor(R.color.GRAY));

                                                                iv_addbtn[j] = new ImageView(context);
                                                                iv_addbtn[j].setLayoutParams(params5);
                                                                iv_addbtn[j].setBackgroundResource(R.drawable.add_btn);
                                                                iv_addbtn[j].setOnClickListener(new View.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(View view) {
                                                                        for (int i = 0; i < iv_addbtn.length; i++) {
                                                                            if (view == iv_addbtn[i]) {
                                                                                for (int k = 0; k < prev; k++) {
                                                                                    content_check.removeViewAt(content_check.indexOfChild((View) rlo_wm.get(key_number)) + 1);
                                                                                }
                                                                                prev = 0;
                                                                                et1.setText(candidates[0][i]);
                                                                                wm_teacher.set(key_number, candidates[0][i]);
                                                                                ((TextView) tv_wm.get(key_number)).setText("[" + wm_teacher.get(key_number) + "]   " + wm_believer.get(key_number));
                                                                            }
                                                                        }
                                                                    }
                                                                });

                                                                r.addView(t);
                                                                r.addView(iv_addbtn[j]);

                                                                t.setText("[" + candidates[1][j] + "]  " + candidates[0][j]);
                                                                content_check.addView(r, content_check.indexOfChild((View) rlo_wm.get(key)) + 1);
                                                            }
                                                        }

                                                    } else {
                                                        intent = new Intent(context, NotfoundActivity.class);
                                                        startActivity(intent);
                                                        FpCheckActivity.this.finish();
                                                    }
                                                }
                                            }
                                        });
                                        r1.addView(t1);
                                        r1.addView(et1);
                                        r1.addView(iv1);

                                        RelativeLayout r2 = new RelativeLayout(context);
                                        r2.setLayoutParams(params1);
                                        r2.setBackgroundResource(R.color.WHITE);
                                        TextView t2 = new TextView(context);
                                        t2.setLayoutParams(params2);
                                        t2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                                        t2.setTextColor(getResources().getColor(R.color.BLACK));
                                        t2.setText("영접자");
                                        et2 = new EditText(context);
                                        et2.setLayoutParams(params3);
                                        et2.setBackgroundResource(android.R.drawable.editbox_background_normal);
                                        et2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                                        et2.setTextColor(getResources().getColor(R.color.BLACK));
                                        et2.setSingleLine();
                                        et2.setImeOptions(EditorInfo.IME_ACTION_NEXT);
                                        et2.setText((String) wm_believer.get(i));
                                        et2.setId(220000000 + i);
                                        et2.addTextChangedListener(new TextWatcher() {
                                            @Override
                                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                            }

                                            @Override
                                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                                int key = (int) et2.getId() - 220000000;
                                                wm_believer.set(key, charSequence.toString());
                                                ((TextView) tv_wm.get(key)).setText("[" + wm_teacher.get(key) + "]   " + wm_believer.get(key));
                                            }

                                            @Override
                                            public void afterTextChanged(Editable editable) {
                                            }
                                        });
                                        r2.addView(t2);
                                        r2.addView(et2);

                                        RelativeLayout r5 = new RelativeLayout(context);
                                        r5.setLayoutParams(params1);
                                        r5.setBackgroundResource(R.color.WHITE);
                                        TextView t5 = new TextView(context);
                                        t5.setLayoutParams(params2);
                                        t5.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                                        t5.setTextColor(getResources().getColor(R.color.BLACK));
                                        t5.setText("만난횟수");
                                        et5 = new EditText(context);
                                        et5.setLayoutParams(params3);
                                        et5.setBackgroundResource(android.R.drawable.editbox_background_normal);
                                        et5.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                                        et5.setTextColor(getResources().getColor(R.color.BLACK));
                                        et5.setSingleLine();
                                        et5.setImeOptions(EditorInfo.IME_ACTION_NEXT);
                                        et5.setInputType(InputType.TYPE_CLASS_NUMBER);
                                        et5.setText((String) wm_frequency.get(i));
                                        et5.setId(550000000 + i);
                                        et5.addTextChangedListener(new TextWatcher() {
                                            @Override
                                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                            }

                                            @Override
                                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                                int key = (int) et5.getId() - 550000000;
                                                wm_frequency.set(key, charSequence.toString());
                                            }

                                            @Override
                                            public void afterTextChanged(Editable editable) {
                                            }
                                        });
                                        r5.addView(t5);
                                        r5.addView(et5);

                                        RelativeLayout r6 = new RelativeLayout(context);
                                        r6.setLayoutParams(params1);
                                        r6.setBackgroundResource(R.color.WHITE);
                                        TextView t6 = new TextView(context);
                                        t6.setLayoutParams(params2);
                                        t6.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                                        t6.setTextColor(getResources().getColor(R.color.BLACK));
                                        t6.setText("장소");
                                        et6 = new EditText(context);
                                        et6.setLayoutParams(params3);
                                        et6.setBackgroundResource(android.R.drawable.editbox_background_normal);
                                        et6.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                                        et6.setTextColor(getResources().getColor(R.color.BLACK));
                                        et6.setSingleLine();
                                        et6.setImeOptions(EditorInfo.IME_ACTION_DONE);
                                        et6.setText((String) wm_place.get(i));
                                        et6.setId(660000000 + i);
                                        et6.addTextChangedListener(new TextWatcher() {
                                            @Override
                                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                            }

                                            @Override
                                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                                int key = (int) et6.getId() - 660000000;
                                                wm_place.set(key, charSequence.toString());
                                            }

                                            @Override
                                            public void afterTextChanged(Editable editable) {
                                            }
                                        });
                                        r6.addView(t6);
                                        r6.addView(et6);

                                        content_check.addView(r6, content_check.indexOfChild((View) rlo_wm.get(i)) + 1);
                                        content_check.addView(r5, content_check.indexOfChild((View) rlo_wm.get(i)) + 1);
                                        content_check.addView(r2, content_check.indexOfChild((View) rlo_wm.get(i)) + 1);
                                        content_check.addView(r1, content_check.indexOfChild((View) rlo_wm.get(i)) + 1);
                                    }
                                    break;
                                }
                            }
                        }
                    });

                    t = new TextView(context);
                    t.setLayoutParams(params2);
                    t.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                    t.setTextColor(getResources().getColor(R.color.BLACK));
                    t.setText("[---]   ---");

                    iv = new ImageView(context);
                    iv.setLayoutParams(params5);
                    iv.setBackgroundResource(R.drawable.remove_btn);
                    iv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            for (int i = 0; i < iv_wm_btn.size(); i++) {
                                if (view == iv_wm_btn.get(i)) {
                                    index_number = i;
                                    new AlertDialog.Builder(FpCheckActivity.this)
                                            .setTitle("현장전도관리")
                                            .setMessage("정말로 삭제하시겠습니까?")
                                            .setNegativeButton("취소", null)
                                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i2) {
                                                    if ((Boolean) isOpen_wm.get(index_number)) {
                                                        for (int n = 0; n < prev + 5; n++) {
                                                            content_check.removeViewAt(content_check.indexOfChild((View) rlo_wm.get(index_number)) + 1);
                                                        }
                                                        content_check.removeViewAt(content_check.indexOfChild((View) rlo_wm.get(index_number)));
                                                        prev = 0;
                                                    } else {
                                                        content_check.removeView((View) rlo_wm.get(index_number));
                                                        content_check.removeView((View) iv_wm_line.get(index_number));
                                                    }
                                                    rlo_wm.remove(index_number);
                                                    tv_wm.remove(index_number);
                                                    iv_wm_btn.remove(index_number);
                                                    iv_wm_line.remove(index_number);
                                                    isOpen_wm.remove(index_number);
                                                    wm_teacher.remove(index_number);
                                                    wm_believer.remove(index_number);
                                                    wm_frequency.remove(index_number);
                                                    wm_place.remove(index_number);
                                                }
                                            })
                                            .show();
                                    break;
                                }
                            }
                        }
                    });

                    iv_line_default = new ImageView(context);
                    iv_line_default.setLayoutParams(params4);
                    iv_line_default.setBackgroundResource(R.color.colorPrimary);

                    r.addView(t);
                    r.addView(iv);

                    rlo_wm.add(r);
                    tv_wm.add(t);
                    iv_wm_btn.add(iv);
                    iv_wm_line.add(iv_line_default);
                    isOpen_wm.add(false);
                    wm_teacher.add("---");
                    wm_believer.add("");
                    wm_frequency.add("");
                    wm_place.add("");

                    content_check.addView((View) rlo_wm.get(rlo_wm.size() - 1), content_check.indexOfChild(rlo_wm_plus));
                    content_check.addView((View) iv_wm_line.get(iv_wm_line.size() - 1), content_check.indexOfChild(rlo_wm_plus));
                }
            });

            TextView tv_wm_plus = new TextView(this);
            tv_wm_plus.setLayoutParams(params8);
            tv_wm_plus.setText("+");
            tv_wm_plus.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
            tv_wm_plus.setTextColor(getResources().getColor(R.color.colorPrimary));

            ImageView iv_line_wm_plus = new ImageView(this);
            iv_line_wm_plus.setLayoutParams(params4);
            iv_line_wm_plus.setBackgroundResource(R.color.colorPrimary);

            rlo_wm_plus.addView(tv_wm_plus);
            content_check.addView(rlo_wm_plus);
            content_check.addView(iv_line_wm_plus);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (att) {
            getMenuInflater().inflate(R.menu.menu_fpcheck1, menu);
        } else {
            getMenuInflater().inflate(R.menu.menu_fpcheck2, menu);
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
        if (id == R.id.send_fpcheck) {
            if (System.currentTimeMillis() - lastTimeButtonPressed > 1500) {
                if (att) {
                    boolean empty = false;
                    for (int k = 0; k < index.length; k++) {
                        if (index[k] == 1 && et_list[k].getText().toString().equals("")) {
                            empty = true;
                            break;
                        }
                    }
                    if (empty) {
                        new AlertDialog.Builder(FpCheckActivity.this)
                                .setTitle("현장전도관리")
                                .setMessage("헌신사유를 모두 작성하신 후\n결과를 전송해주세요.")
                                .setPositiveButton("확인", null)
                                .show();
                    } else {
                        // dialog box
                        new AlertDialog.Builder(FpCheckActivity.this)
                                .setTitle("현장전도관리")
                                .setMessage("결과를 전송합니다.")
                                .setNegativeButton("취소", null)
                                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        String[] contents = new String[attendance[0].length];
                                        for (int k = 0; k < contents.length; k++) {
                                            contents[k] = et_list[k].getText().toString();
                                        }

                                        isDone = false;
                                        client = new Client(FpCheckActivity.this, "setFpAttendance", KEY, CHECK_TYPE, attendance[0], contents, index, names_etc, names_search);
                                        client.execute();

                                        while (!isDone) {
                                            try {
                                                Thread.sleep(100);
                                            } catch (InterruptedException e) {
                                            }
                                            isDone = client.isDone();
                                        }

                                        if (client.isAlive()) {
                                            intent = new Intent(FpCheckActivity.this, SelectionActivity.class);

                                            if (status[Integer.valueOf(CHECK_TYPE.substring(2, CHECK_TYPE.length()))].equals("TRUE")) {
                                                isDone = false;
                                                client = new Client(FpCheckActivity.this, "setStatus", CHECK_TYPE);
                                                client.execute();

                                                while (!isDone) {
                                                    try {
                                                        Thread.sleep(100);
                                                    } catch (InterruptedException e) {
                                                    }
                                                    isDone = client.isDone();
                                                }

                                                if (client.isAlive()) {
                                                    intent.putExtra("consideration", list[Integer.valueOf(CHECK_TYPE.substring(2, CHECK_TYPE.length())) - 1] + "관리 결과가 변경되었습니다.\n열매관리를 한 번 더 확인해주세요.");
                                                } else {
                                                    intent = new Intent(FpCheckActivity.this, NotfoundActivity.class);
                                                    startActivity(intent);
                                                    FpCheckActivity.this.finish();
                                                }
                                            }

                                            // dialog box
                                            new AlertDialog.Builder(FpCheckActivity.this)
                                                    .setTitle("현장전도관리")
                                                    .setMessage("결과전송을 완료하였습니다.")
                                                    .setOnCancelListener(new DialogInterface.OnCancelListener() {
                                                        @Override
                                                        public void onCancel(DialogInterface dialogInterface) {
                                                            intent.putExtra("type", TYPE);
                                                            intent.putExtra("key", KEY);
                                                            intent.putExtra("name", NAME);
                                                            startActivity(intent);
                                                            FpCheckActivity.this.finish();
                                                        }
                                                    })
                                                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialogInterface, int i) {
                                                            intent.putExtra("type", TYPE);
                                                            intent.putExtra("key", KEY);
                                                            intent.putExtra("name", NAME);
                                                            startActivity(intent);
                                                            FpCheckActivity.this.finish();
                                                        }
                                                    })
                                                    .show();
                                        } else {
                                            intent = new Intent(FpCheckActivity.this, NotfoundActivity.class);
                                            startActivity(intent);
                                            FpCheckActivity.this.finish();
                                        }
                                    }
                                })
                                .show();
                    }

                } else {
                    boolean empty1 = false;
                    boolean empty2 = false;
                    boolean wrong = false;
                    for (int k = 0; k < b_preacher.size(); k++) {
                        if (b_preacher.get(k).equals("---") || b_believer.get(k).equals("")) {
                            empty1 = true;
                            break;
                        }
                    }
                    for (int k = 0; k < wm_teacher.size(); k++) {
                        if (wm_teacher.get(k).equals("---") || wm_believer.get(k).equals("")) {
                            empty2 = true;
                            break;
                        }
                    }
                    for (int k = 0; k < b_believer.size(); k++) {
                        for (int l = 0; l < b_believer.get(k).toString().length(); l++) {
                            if (b_believer.get(k).toString().substring(l, l + 1).equals(",")) {
                                wrong = true;
                                break;
                            }
                        }
                        if (wrong) {
                            break;
                        }
                    }
                    if (!wrong) {
                        for (int k = 0; k < wm_believer.size(); k++) {
                            for (int l = 0; l < wm_believer.get(k).toString().length(); l++) {
                                if (wm_believer.get(k).toString().substring(l, l + 1).equals(",")) {
                                    wrong = true;
                                    break;
                                }
                            }
                            if (wrong) {
                                break;
                            }
                        }
                    }

                    if (empty1) {
                        new AlertDialog.Builder(FpCheckActivity.this)
                                .setTitle("현장전도관리")
                                .setMessage("영접자현황 관리시 전도자와 영접자가\n모두 정확히 기록되어야 합니다.")
                                .setPositiveButton("확인", null)
                                .show();
                    } else if (empty2) {
                        new AlertDialog.Builder(FpCheckActivity.this)
                                .setTitle("현장전도관리")
                                .setMessage("말씀운동현황 관리시 사역자와 영접자가\n모두 정확히 기록되어야 합니다.")
                                .setPositiveButton("확인", null)
                                .show();
                    } else if (wrong) {
                        new AlertDialog.Builder(FpCheckActivity.this)
                                .setTitle("현장전도관리")
                                .setMessage("말씀운동현황 관리시 영접자는\n한 칸에 한 명만 입력해주세요.")
                                .setPositiveButton("확인", null)
                                .show();
                    } else {
                        String[] consideration = new String[3];

                        isDone = false;
                        client = new Client(FpCheckActivity.this, "getConsideration", CHECK_TYPE, b_preacher, wm_teacher);
                        client.execute();

                        while (!isDone) {
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                            }
                            isDone = client.isDone();
                        }

                        if (client.isAlive()) {
                            consideration = client.getCONSIDERATION();
                        } else {
                            intent = new Intent(FpCheckActivity.this, NotfoundActivity.class);
                            startActivity(intent);
                            FpCheckActivity.this.finish();
                        }

                        if (consideration[2].equals("0")) {
                            // dialog box
                            new AlertDialog.Builder(FpCheckActivity.this)
                                    .setTitle("현장전도관리")
                                    .setMessage("결과를 전송합니다.")
                                    .setNegativeButton("취소", null)
                                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            String[][] b_output = new String[6][b_preacher.size()];
                                            String[][] wm_output = new String[4][wm_teacher.size()];
                                            for (int k = 0; k < b_output[0].length; k++) {
                                                b_output[0][k] = (String) b_preacher.get(k);
                                                b_output[1][k] = (String) b_believer.get(k);
                                                b_output[2][k] = (String) b_teacher.get(k);
                                                b_output[3][k] = (String) b_age.get(k);
                                                b_output[4][k] = (String) b_phone.get(k);
                                                b_output[5][k] = (String) b_remeet.get(k);
                                            }
                                            for (int k = 0; k < wm_output[0].length; k++) {
                                                wm_output[0][k] = (String) wm_teacher.get(k);
                                                wm_output[1][k] = (String) wm_believer.get(k);
                                                wm_output[2][k] = (String) wm_frequency.get(k);
                                                wm_output[3][k] = (String) wm_place.get(k);
                                            }

                                            isDone = false;
                                            client = new Client(FpCheckActivity.this, "setFpFruits", KEY, CHECK_TYPE, b_output, wm_output);
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
                                                new AlertDialog.Builder(FpCheckActivity.this)
                                                        .setTitle("현장전도관리")
                                                        .setMessage("결과전송을 완료하였습니다.")
                                                        .setOnCancelListener(new DialogInterface.OnCancelListener() {
                                                            @Override
                                                            public void onCancel(DialogInterface dialogInterface) {
                                                                intent = new Intent(FpCheckActivity.this, SelectionActivity.class);
                                                                intent.putExtra("type", TYPE);
                                                                intent.putExtra("key", KEY);
                                                                intent.putExtra("name", NAME);
                                                                startActivity(intent);
                                                                FpCheckActivity.this.finish();
                                                            }
                                                        })
                                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                                intent = new Intent(FpCheckActivity.this, SelectionActivity.class);
                                                                intent.putExtra("type", TYPE);
                                                                intent.putExtra("key", KEY);
                                                                intent.putExtra("name", NAME);
                                                                startActivity(intent);
                                                                FpCheckActivity.this.finish();
                                                            }
                                                        })
                                                        .show();
                                            } else {
                                                intent = new Intent(FpCheckActivity.this, NotfoundActivity.class);
                                                startActivity(intent);
                                                FpCheckActivity.this.finish();
                                            }
                                        }
                                    })
                                    .show();
                        } else if (consideration[1].equals("believer")) {
                            new AlertDialog.Builder(FpCheckActivity.this)
                                    .setTitle("현장전도관리")
                                    .setMessage("출석관리 결과가 변경되어\n'" + consideration[0] + "'님을 전도자로 기록할 수 없습니다.")
                                    .setPositiveButton("확인", null)
                                    .show();
                        } else {
                            new AlertDialog.Builder(FpCheckActivity.this)
                                    .setTitle("현장전도관리")
                                    .setMessage("출석관리 결과가 변경되어\n'" + consideration[0] + "'님을 사역자로 기록할 수 없습니다.")
                                    .setPositiveButton("확인", null)
                                    .show();
                        }
                    }
                }

                lastTimeButtonPressed = System.currentTimeMillis();
            }
        } else if (id == R.id.add_fp) {
            if (System.currentTimeMillis() - lastTimeButtonPressed > 1500) {
                new AlertDialog.Builder(FpCheckActivity.this)
                        .setTitle("출석 명단 추가")
                        .setMessage("새로 출석 명단을 추가하시겠습니까?\n한 번 추가하시면 삭제가 불가능합니다.")
                        .setNegativeButton("취소", null)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                View digView = View.inflate(context, R.layout.dialog_add, null);
                                add_index = 1;
                                EditText et_addName = digView.findViewById(R.id.edittext_add);
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
                                Spinner sp_addGroup = digView.findViewById(R.id.spinner_add);
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

                                new AlertDialog.Builder(FpCheckActivity.this)
                                        .setTitle("출석 명단 추가")
                                        .setView(digView)
                                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                new AlertDialog.Builder(FpCheckActivity.this)
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
                                                        client = new Client(FpCheckActivity.this, "getFpDuplication", add_name);
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
                                                            intent = new Intent(FpCheckActivity.this, NotfoundActivity.class);
                                                            startActivity(intent);
                                                            FpCheckActivity.this.finish();
                                                        }
                                                    }
                                                    switch (add_index) {
                                                        case 1:
                                                            msg = "직분을 선택해주세요.\n다시 처음부터 시도해주세요.";
                                                            break;
                                                        case 2:
                                                            msg = "이름은 띄어쓰기/특수문자를 포함할 수 없습니다.\n다시 처음부터 시도해주세요.";
                                                            break;
                                                        case 3:
                                                            msg = "중복된 이름이 존재합니다.\n다시 처음부터 시도해주세요.";
                                                    }
                                                    if (add_index != 0) {
                                                        new AlertDialog.Builder(FpCheckActivity.this)
                                                                .setTitle("출석 명단 추가")
                                                                .setMessage(msg)
                                                                .setPositiveButton("확인", null)
                                                                .show();
                                                    } else {
                                                        new AlertDialog.Builder(FpCheckActivity.this)
                                                                .setTitle("출석 명단 추가")
                                                                .setMessage(msg)
                                                                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                                        new AlertDialog.Builder(FpCheckActivity.this)
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
                                                                        client = new Client(FpCheckActivity.this, "addFpAttendance", KEY, CHECK_TYPE, add_name, add_group);
                                                                        client.execute();

                                                                        while (!isDone) {
                                                                            try {
                                                                                Thread.sleep(100);
                                                                            } catch (InterruptedException e) {
                                                                            }
                                                                            isDone = client.isDone();
                                                                        }

                                                                        if (client.isAlive()) {
                                                                            new AlertDialog.Builder(FpCheckActivity.this)
                                                                                    .setTitle("출석 명단 추가")
                                                                                    .setMessage("출석 명단 추가가 완료되었습니다.\n재접속이 필요합니다.\n현장전도관리 목록으로 돌아갑니다.")
                                                                                    .setOnCancelListener(new DialogInterface.OnCancelListener() {
                                                                                        @Override
                                                                                        public void onCancel(DialogInterface dialogInterface) {
                                                                                            intent = new Intent(FpCheckActivity.this, SelectionActivity.class);
                                                                                            intent.putExtra("type", TYPE);
                                                                                            intent.putExtra("key", KEY);
                                                                                            intent.putExtra("name", NAME);
                                                                                            startActivity(intent);
                                                                                            FpCheckActivity.this.finish();
                                                                                        }
                                                                                    })
                                                                                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                                                                        @Override
                                                                                        public void onClick(DialogInterface dialogInterface, int i) {
                                                                                            intent = new Intent(FpCheckActivity.this, SelectionActivity.class);
                                                                                            intent.putExtra("type", TYPE);
                                                                                            intent.putExtra("key", KEY);
                                                                                            intent.putExtra("name", NAME);
                                                                                            startActivity(intent);
                                                                                            FpCheckActivity.this.finish();
                                                                                        }
                                                                                    })
                                                                                    .show();
                                                                        } else {
                                                                            intent = new Intent(FpCheckActivity.this, NotfoundActivity.class);
                                                                            startActivity(intent);
                                                                            FpCheckActivity.this.finish();
                                                                        }
                                                                    }
                                                                }).show();
                                                    }
                                                } else {
                                                    new AlertDialog.Builder(FpCheckActivity.this)
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
        new AlertDialog.Builder(FpCheckActivity.this)
                .setTitle("현장전도관리")
                .setMessage("작성내용이 저장되지 않습니다.")
                .setNegativeButton("취소", null)
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        intent = new Intent(FpCheckActivity.this, SelectionActivity.class);
                        intent.putExtra("type", TYPE);
                        intent.putExtra("key", KEY);
                        intent.putExtra("name", NAME);
                        startActivity(intent);
                        FpCheckActivity.this.finish();
                    }
                })
                .show();
    }
}
