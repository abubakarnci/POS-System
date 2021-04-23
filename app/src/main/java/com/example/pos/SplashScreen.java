package com.example.pos;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {

    TextView text;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        text=findViewById(R.id.intro4);
        image=findViewById(R.id.imageView);


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashScreen.this,MainActivity.class);

                Pair[] pairs= new Pair[2];
                pairs[0] = new Pair<View, String>(image,"logo_image");
                pairs[1] = new Pair<View, String>(text,"logo_text");

                ActivityOptions options= ActivityOptions.makeSceneTransitionAnimation(SplashScreen.this, pairs);
                startActivity(i,options.toBundle());
                finish();
            }
        }, 3000);


    }
}