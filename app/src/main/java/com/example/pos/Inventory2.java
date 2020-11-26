package com.example.pos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Date;

public class Inventory2 extends AppCompatActivity {


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("items");
    DatabaseReference retriveRef;
    InventObj inventObj=new InventObj();
    Button save,get;
    EditText name,type,cPrice,sPrice,quantity,id ;
    TextView profit,n,t,c,s,q;
    DecimalFormat decimalFormat=new DecimalFormat("#.##");

    long itemNo=0;
    String itemName;
    String itemType;
    Double costPrice;
    Double sellPrice;
    Double prof;
    Double quant;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory2);

        save=findViewById(R.id.button);
        id=findViewById(R.id.id);
        get=findViewById(R.id.get);
        name=findViewById(R.id.itemName);
        type=findViewById(R.id.itemType);
        cPrice=findViewById(R.id.costPrice);
        sPrice=findViewById(R.id.sellPrice);
        quantity=findViewById(R.id.quantity);
        profit=findViewById(R.id.profit);


        n=findViewById(R.id.nameTv);
        t=findViewById(R.id.typeTv);
        c=findViewById(R.id.costTv);
        s=findViewById(R.id.sellTv);
        q=findViewById(R.id.quaTv);


        profit.setVisibility(View.INVISIBLE);
        name.setVisibility(View.INVISIBLE);
        type.setVisibility(View.INVISIBLE);
        cPrice.setVisibility(View.INVISIBLE);
        sPrice.setVisibility(View.INVISIBLE);
        quantity.setVisibility(View.INVISIBLE);
        save.setVisibility(View.INVISIBLE);

        n.setVisibility(View.INVISIBLE);
        t.setVisibility(View.INVISIBLE);
        c.setVisibility(View.INVISIBLE);
        s.setVisibility(View.INVISIBLE);
        q.setVisibility(View.INVISIBLE);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                itemNo= snapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        listeners();
        callOnClickListener();


    }

    private void listeners() {
        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editItem(id.getText().toString());

            }
        });

    }

    private void editItem(final String num) {

        if (num.isEmpty()) {
            id.setError("Please Enter valid Item number");
            id.requestFocus();
        }
        else{

            profit.setVisibility(View.VISIBLE);
            name.setVisibility(View.VISIBLE);
            type.setVisibility(View.VISIBLE);
            cPrice.setVisibility(View.VISIBLE);
            sPrice.setVisibility(View.VISIBLE);
            quantity.setVisibility(View.VISIBLE);
            save.setVisibility(View.VISIBLE);

            n.setVisibility(View.VISIBLE);
            t.setVisibility(View.VISIBLE);
            c.setVisibility(View.VISIBLE);
            s.setVisibility(View.VISIBLE);
            q.setVisibility(View.VISIBLE);



            retriveRef = FirebaseDatabase.getInstance().getReference().child("items").child(num);

            retriveRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    itemName = (String) snapshot.child("itemName").getValue();

                    itemType = (String) snapshot.child("itemType").getValue();
                    costPrice = Double.parseDouble(String.valueOf(snapshot.child("costPrice").getValue()));
                    sellPrice = Double.parseDouble(String.valueOf(snapshot.child("sellPrice").getValue()));
                    quant = Double.parseDouble(String.valueOf(snapshot.child("quantity").getValue()));
                    prof = Double.parseDouble(String.valueOf(snapshot.child("profit").getValue()));


                    name.setText(itemName);
                    type.setText(itemType);
                    cPrice.setText(costPrice+"");
                    sPrice.setText(sellPrice+"");
                    quantity.setText(quant+"");
                    profit.setText(prof+"%");

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });




        }


    }


    private void callOnClickListener() {
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                inventObj.itemNo=Long.parseLong(id.getText().toString());
                inventObj.itemName= String.valueOf(name.getText());
                inventObj.itemType= String.valueOf(type.getText());
                inventObj.sellPrice= Double.parseDouble(String.valueOf(sPrice.getText()));
                inventObj.costPrice= Double.parseDouble(String.valueOf(cPrice.getText()));
                inventObj.quantity= Double.parseDouble(String.valueOf(quantity.getText()));
                double cal1=((Double.parseDouble(String.valueOf(sPrice.getText()))) - (Double.parseDouble(String.valueOf(cPrice.getText()))));
                double cal=(cal1 / (Double.parseDouble(String.valueOf(sPrice.getText()))))*100;
                inventObj.profit=Double.valueOf(decimalFormat.format(cal));

                profit.setText(inventObj.profit+"%");
                myRef.child(String.valueOf(inventObj.itemNo)).setValue(inventObj);
                Toast.makeText(Inventory2.this,"Item Uploaded",Toast.LENGTH_LONG).show();
            }
        });

    }




}