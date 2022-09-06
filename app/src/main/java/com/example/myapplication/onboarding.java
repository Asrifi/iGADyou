package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class onboarding extends AppCompatActivity {

    ViewPager mSlideViewPager;
    LinearLayout mOutLayout;
    Button backbtn, nextbtn, skipbtn, getstarted;
    Animation btnanim;

    TextView[] dots;
    ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_onboarding);
        SharedPreferences preferences = getSharedPreferences("PREFERENCES", MODE_PRIVATE);
        String FirstTime = preferences.getString("FirstTimeInstall", "");

        if (FirstTime.equals("Yes")){

            Intent intent = new Intent(onboarding.this, MainActivity.class);
            startActivity(intent);

        }else{
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("FirstTimeInstall", "Yes");
            editor.apply();
        }

        getstarted = findViewById(R.id.getstarted);
        backbtn = findViewById(R.id.backbtn);
        nextbtn = findViewById(R.id.nextbtn);
        skipbtn = findViewById(R.id.skipButton);
        btnanim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.btn_anim);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getItem(0) > 0){
                    mSlideViewPager.setCurrentItem(getItem(-1),true);
                }

            }
        });

        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getItem(0) < 2){
                    mSlideViewPager.setCurrentItem(getItem(1), true);

                }else{
                    Intent i = new Intent(onboarding.this,MainActivity.class);
                    startActivity(i);
                    finish();
                }

            }
        });

        skipbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(onboarding.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        getstarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(onboarding.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        mSlideViewPager = (ViewPager) findViewById(R.id.slideViewPage);
        mOutLayout = (LinearLayout) findViewById(R.id.indicator_layout);

        viewPagerAdapter = new ViewPagerAdapter(this);

        mSlideViewPager.setAdapter(viewPagerAdapter);

        setUpindicator(0);
        mSlideViewPager.addOnPageChangeListener(viewlistener);

    }

    public void setUpindicator(int position){

        dots = new TextView[3];
        mOutLayout.removeAllViews();

        for (int i = 0 ; i < dots.length ; i++) {

            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.inactive,getApplicationContext().getTheme()));
            mOutLayout.addView(dots[i]);

        }

        dots[position].setTextColor(getResources().getColor(R.color.active,getApplicationContext().getTheme()));

    }

    ViewPager.OnPageChangeListener viewlistener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            setUpindicator(position);

            if (position == 2){

                nextbtn.setVisibility(View.INVISIBLE);
                getstarted.setVisibility(View.VISIBLE);
                skipbtn.setVisibility(View.INVISIBLE);
                mOutLayout.setVisibility(View.INVISIBLE);
                getstarted.setAnimation(btnanim);

            }else {

                nextbtn.setVisibility(View.VISIBLE);
                getstarted.setVisibility(View.INVISIBLE);
                skipbtn.setVisibility(View.VISIBLE);
                mOutLayout.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private int getItem (int i){
        return mSlideViewPager.getCurrentItem() + i;
    }
}