package com.jin.attendance_archive;

/**
 * Created by JIN on 1/15/2019.
 */

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class SplashScreen extends Activity {

    Toast autoLoginToast;
    Intent intent;
    LoginSharedPreference sp;

    Client client;
    boolean isDone;


    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }

    /**
     * Called when the activity is first created.
     */
    Thread splashTread;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        autoLoginToast = Toast.makeText(SplashScreen.this, "자동으로 로그인 되었습니다.", Toast.LENGTH_SHORT);
        StartAnimations();
    }

    private void StartAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
        LinearLayout l = (LinearLayout) findViewById(R.id.lin_lay);
        l.clearAnimation();
        l.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        ImageView iv = (ImageView) findViewById(R.id.splash);
        iv.clearAnimation();
        iv.startAnimation(anim);

        splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    sp = new LoginSharedPreference();

                    if (sp.isLogin(SplashScreen.this)) {
                        String[] values = sp.getValues(SplashScreen.this);
                        isDone = false;
                        client = new Client(SplashScreen.this, "AutoLogIn", values[0]);
                        client.execute();

                        // Splash screen pause time
                        while (!isDone) {
                            sleep(100);
                            isDone = client.isDone();
                        }

                        if (client.isAlive()) {
                            intent = new Intent(SplashScreen.this, MainActivity.class);
                            intent.putExtra("key", values[0]);
                            intent.putExtra("name", values[1]);
                            autoLoginToast.show();
                        } else {
                            intent = new Intent(SplashScreen.this, NotfoundActivity.class);
                        }
                    } else {
                        isDone = false;
                        client = new Client(SplashScreen.this, "isAlive");
                        client.execute();

                        // Splash screen pause time
                        while (!isDone) {
                            sleep(100);
                            isDone = client.isDone();
                        }

                        if (client.isAlive()) {
                            intent = new Intent(SplashScreen.this, LoginActivity.class);
                        } else {
                            intent = new Intent(SplashScreen.this, NotfoundActivity.class);
                        }
                    }

                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    SplashScreen.this.finish();

                } catch (InterruptedException e) {
                    // do nothing
                } finally {
                    SplashScreen.this.finish();
                }
            }
        };

        splashTread.start();
    }

    @Override
    public void onBackPressed() {
        // do nothing
    }
}