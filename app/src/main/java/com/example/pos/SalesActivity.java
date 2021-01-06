package com.example.pos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class SalesActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    String s1[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);

        recyclerView= findViewById(R.id.recyclerView);
        s1=getResources().getStringArray(R.array.test);

        MyAdapter myAdapter=new MyAdapter(this, s1);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}