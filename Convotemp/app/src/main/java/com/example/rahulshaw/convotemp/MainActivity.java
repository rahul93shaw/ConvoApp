package com.example.rahulshaw.convotemp;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView brand;

    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        brand = (TextView) findViewById(R.id.brand);

        Typeface weissinit = Typeface.createFromAsset(getAssets(), "fonts/WeissInitialen.ttf");
        brand.setTypeface(weissinit);

        new Handler().postDelayed(new Runnable() {

            public void run(){
                Intent i = new Intent(MainActivity.this, Temperature.class);
                startActivity(i);

                finish();
            }

        },SPLASH_TIME_OUT);

    }
}
