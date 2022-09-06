package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ProgressBar;

public class signing_in extends AppCompatActivity {

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signing_in);

        progressBar = findViewById(R.id.progressBar3);

        progressBar.setMax(100);
        progressBar.setScaleY(1f);

        progressAnim();
    }
    public void progressAnim(){
        progressbaranim anim = new progressbaranim(this, progressBar, 0f, 100f);
        anim.setDuration(6000);
        progressBar.setAnimation(anim);
    }
}