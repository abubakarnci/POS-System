package com.example.pos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.ramotion.paperonboarding.PaperOnboardingFragment;
import com.ramotion.paperonboarding.PaperOnboardingPage;

import java.util.ArrayList;

public class IntroActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;

    Button finish;
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        animation= AnimationUtils.loadAnimation(this, R.anim.bottom_anim);
        finish=findViewById(R.id.button);

        finish.setAnimation(animation);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        fragmentManager =getSupportFragmentManager();

        final PaperOnboardingFragment paperOnboardingFragment= PaperOnboardingFragment.newInstance(getDataForOnBoarding());

        FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, paperOnboardingFragment);
        fragmentTransaction.commit();

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(IntroActivity.this,HomeActivity.class);
                startActivity(i);
            }
        });

    }

    private ArrayList<PaperOnboardingPage> getDataForOnBoarding() {

        PaperOnboardingPage src1=new PaperOnboardingPage("Sales", "This app is offering very reliable sales features", Color.parseColor("#22eaaa"),
                R.drawable.cashier,R.drawable.cashier);

        PaperOnboardingPage src2=new PaperOnboardingPage("Inventories", "All stored items will be categorized for your convenience", Color.parseColor("#ffb174"),
                R.drawable.inventory,R.drawable.inventory);

        PaperOnboardingPage src3=new PaperOnboardingPage("Bills", "All invoices will be sorted by date created", Color.parseColor("#FFCC00"),
                R.drawable.bill,R.drawable.bill);
        PaperOnboardingPage src4=new PaperOnboardingPage("Payments", "We carefully verify all card details before taking payments", Color.parseColor("#FF9999"),
                R.drawable.payment,R.drawable.payment);

        ArrayList<PaperOnboardingPage> elements= new ArrayList<>();
        elements.add(src1);
        elements.add(src2);
        elements.add(src3);
        elements.add(src4);


        return elements;
    }
}