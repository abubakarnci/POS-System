package com.example.pos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;


import java.util.ArrayList;

public class ConfirmOrderActivity extends AppCompatActivity {

    RecyclerView orderRv;
    ConfirmOrderActivityAdapter confirmOrderActivityAdapter;
    ArrayList<InventObj> input;
    Toolbar toolbar;

    Activity context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);

        input=new ArrayList<>();


        toolbar =findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        orderRv=findViewById(R.id.order_rv);
        confirmOrderActivityAdapter= new ConfirmOrderActivityAdapter(context);
        orderRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        orderRv.setAdapter(confirmOrderActivityAdapter);


    }
}