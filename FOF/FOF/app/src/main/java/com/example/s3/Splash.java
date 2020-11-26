package com.example.s3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class Splash extends AppCompatActivity {

    public static int SPLASH_TIME_OUT = 5000;
    View f,s,t,fo,fi,si;
    TextView a,slogan;
    Animation top,bot,mid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        top= AnimationUtils.loadAnimation(this,R.anim.top_anim);
        bot= AnimationUtils.loadAnimation(this,R.anim.bot);
        mid= AnimationUtils.loadAnimation(this,R.anim.mid);

        f=findViewById(R.id.first);
        s=findViewById(R.id.sec);
        t=findViewById(R.id.third);
        fo=findViewById(R.id.forth);
        fi=findViewById(R.id.fifth);
        si=findViewById(R.id.sixth);

        a=findViewById(R.id.logo);
        slogan=findViewById(R.id.tagLine);

        a.setAnimation(mid);
        slogan.setAnimation(bot);

        f.setAnimation(top);
        s.setAnimation(top);
        t.setAnimation(top);
        fo.setAnimation(top);
        fi.setAnimation(top);
        si.setAnimation(top);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i=new Intent(Splash.this,Login.class);
                startActivity(i);
                finish();
            }
        },SPLASH_TIME_OUT);

    }
}