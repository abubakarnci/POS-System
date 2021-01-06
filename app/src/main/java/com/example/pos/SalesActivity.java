package com.example.pos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SalesActivity extends AppCompatActivity {


    RecyclerView recyclerView;

    ArrayList<InventObj> input;

    DatabaseReference retriveRef;
    EditText name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);

        input=new ArrayList<>();
        name=findViewById(R.id.editTextName);
        recyclerView= findViewById(R.id.recyclerView);



        retriveRef = FirebaseDatabase.getInstance().getReference("items");

        retriveRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                input.clear();
                for(DataSnapshot dss : snapshot.getChildren()) {
                    InventObj inventObj= dss.getValue(InventObj.class);
                    inventObj.setItemNo(Long.parseLong(dss.getKey()));
                   // String itemName = (String) snapshot.child("itemName").getValue();
                    input.add(inventObj);
                }


                //    sellPrice = Double.parseDouble(String.valueOf(snapshot.child("sellPrice").getValue()));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });







        MyAdapter myAdapter=new MyAdapter(this, input);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}