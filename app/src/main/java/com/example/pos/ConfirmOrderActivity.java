package com.example.pos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import java.util.ArrayList;

public class ConfirmOrderActivity extends AppCompatActivity {

    RecyclerView orderRv;
    ConfirmOrderActivityAdapter confirmOrderActivityAdapter;
    ArrayList<String> input;
    Toolbar toolbar;

    Button back;

    Activity context;

    private ArrayList <String> name=new ArrayList<>();
    private ArrayList <String> qty=new ArrayList<>();
    private ArrayList <String> price=new ArrayList<>();
    private ArrayList <String> total=new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);

        input=new ArrayList<>();

        name=getIntent().getStringArrayListExtra("name");
        qty=getIntent().getStringArrayListExtra("qty");
        price=getIntent().getStringArrayListExtra("price");
        total=getIntent().getStringArrayListExtra("total");


        hello();

        back=findViewById(R.id.confirmBtn);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();

            }
        });




        toolbar =findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        orderRv=findViewById(R.id.order_rv);
        confirmOrderActivityAdapter= new ConfirmOrderActivityAdapter(context,name,price,qty,total);
        orderRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        orderRv.setAdapter(confirmOrderActivityAdapter);



    }

    private void hello() {

        for(int i=0; i<9; i++ ){
            input.add("Apple"+i);
        }

    }
}