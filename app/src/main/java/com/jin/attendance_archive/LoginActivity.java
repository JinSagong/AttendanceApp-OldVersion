package com.jin.attendance_archive;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by JIN on 1/26/2019.
 */

public class LoginActivity extends AppCompatActivity {

    private Toast toast_back;
    private long lastTimeBackPressed;
    private long lastTimeButtonPressed;

    EditText et_id;
    Button btn_logIn;
    Intent intent;

    LoginSharedPreference sp;

    Client client;
    boolean isDone;
    String[] people;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        toast_back = Toast.makeText(this, "'뒤로' 버튼을 한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT);

        et_id = (EditText) findViewById(R.id.login_id);
        btn_logIn = (Button) findViewById(R.id.log_in);
    }

    public void onClick(View view) {
        if (view.getId() == R.id.log_in) {
            if (System.currentTimeMillis() - lastTimeButtonPressed > 1500) {

                if (!et_id.getText().toString().equals("")) {
                    people = new String[2];
                    isDone = false;
                    client = new Client(LoginActivity.this, "LogIn", et_id.getText().toString(), people);
                    client.execute();

                    while (!isDone) {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                        }
                        isDone = client.isDone();
                    }

                    if (client.isAlive()) {
                        if (!people[0].equals("FALSE")) {
                            sp = new LoginSharedPreference();
                            sp.setLogin(LoginActivity.this, people[0], people[1]);
                            intent = new Intent(this, MainActivity.class);
                            intent.putExtra("key", people[0]);
                            intent.putExtra("name", people[1]);
                            startActivity(intent);
                            LoginActivity.this.finish();
                        } else {
                            // msg false 아이디가 올바르지 않습니다.
                            new AlertDialog.Builder(LoginActivity.this)
                                    .setTitle("로그인")
                                    .setMessage("아이디가 올바르지 않습니다.")
                                    .setPositiveButton("확인", null)
                                    .show();
                        }
                    } else {
                        intent = new Intent(this, NotfoundActivity.class);
                        startActivity(intent);
                        LoginActivity.this.finish();
                    }
                } else {
                    // msg 아이디를 입력해주세요.
                    new AlertDialog.Builder(LoginActivity.this)
                            .setTitle("로그인")
                            .setMessage("아이디를 입력해주세요.")
                            .setPositiveButton("확인", null)
                            .show();
                }

                lastTimeButtonPressed = System.currentTimeMillis();
            }
        }
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
