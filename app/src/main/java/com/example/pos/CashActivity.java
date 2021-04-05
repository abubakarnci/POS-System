package com.example.pos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CashActivity extends AppCompatActivity {


    private ArrayList <String> data=new ArrayList<String>();
    private ArrayList <String> data1=new ArrayList<String>();
    private ArrayList <String> data2=new ArrayList<String>();
    private ArrayList <String> data3=new ArrayList<String>();

    private TableLayout table;

    EditText ed1, ed2, ed3;

    Button b1;

    Double subTotal;
    String name;
    String qty;
    String price;
    String total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash);

        ed1=findViewById(R.id.subTotal);
        ed2=findViewById(R.id.payment);
        ed3=findViewById(R.id.balance);
        b1=findViewById(R.id.cashPay);

        name=getIntent().getStringExtra("name");
        qty=getIntent().getStringExtra("qty");
        price=getIntent().getStringExtra("price");
        total=getIntent().getStringExtra("total");

        subTotal= Double.valueOf(getIntent().getStringExtra("subTotal"));
        ed1.setText(subTotal.toString());

        ed2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {

                double payment= Double.parseDouble(ed2.getText().toString());
                double bal= payment-subTotal;

                ed3.setText(String.valueOf(bal));

            }
        });




        fillTable();


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String check=ed2.getText().toString();
                if(check.isEmpty()){
                    ed2.setError("Please Enter Amount");
                    ed2.requestFocus();
                }

                else {
                    Toast.makeText(CashActivity.this, "Invoice is Saved & Uploaded", Toast.LENGTH_LONG).show();

                    Intent h = new Intent(CashActivity.this, HomeActivity.class);
                    startActivity(h);
                }
            }
        });


    }

    private void fillTable() {

        TableLayout table = (TableLayout) findViewById(R.id.tb1);


        String[] itemm = String.valueOf(name).split(",");
        String[] qtyy = String.valueOf(qty).split(",");
        String[] pricee = String.valueOf(price).split(",");
        String[] billl = String.valueOf(total).split(",");

        for (int i=0; i<itemm.length; i++){
            TableRow row=new TableRow(this);
            TextView t1=new TextView(this);
            TextView t2=new TextView(this);
            TextView t3=new TextView(this);
            TextView t4=new TextView(this);


            t1.setText(itemm[i]);
            t1.setTextColor(Color.BLACK);
            t1.setTextSize(17);

            t3.setText(qtyy[i]);
            t3.setTextColor(Color.BLACK);
            t3.setTextSize(17);

            t2.setText(pricee[i]);
            t2.setTextColor(Color.BLACK);
            t2.setTextSize(17);

            t4.setText(billl[i]);
            t4.setTextColor(Color.BLACK);
            t4.setTextSize(17);

            row.addView(t1);
            row.addView(t2);
            row.addView(t3);
            row.addView(t4);

            table.addView(row);

        }




    }


}