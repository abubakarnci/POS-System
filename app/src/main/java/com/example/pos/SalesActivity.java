package com.example.pos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SalesActivity extends AppCompatActivity {



    RecyclerView recyclerView;
    ArrayList<InventObj> input;
    DatabaseReference retriveRef;
    EditText name;
    DataObj dataObj=new DataObj();
    Button saveAndPrintButton;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("invoice");
    ArrayAdapter<String> adapter;
    long invoiceNo = 0;
    DecimalFormat decimalFormat=new DecimalFormat("#.##");
    SimpleDateFormat dateFormat=new SimpleDateFormat("dd-MM-yyyy hh:mm a");
    EditText item,price,qty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);

        callFindViewById();
        callOnClickListener();




        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                invoiceNo= snapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //callFindViewById();

    }





    private void callOnClickListener() {


        saveAndPrintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataObj.invoiceNo=invoiceNo +1;
                dataObj.customerName= String.valueOf(name.getText());
                //dataObj.qty.add( Double.parseDouble(String.valueOf(qty.getText())));

              /*  for(int i=0; i<10; i++) {
                    dataObj.price.add(Double.parseDouble(String.valueOf(price.getText())));

                }*/
                //dataObj.test=(Double.parseDouble(String.valueOf(price.getText())));
                //dataObj.amount=Double.valueOf(decimalFormat.format(dataObj.getFuelQty()*itemPrice[spinner.getSelectedItemPosition()]));
                // dataObj.amount=Double.valueOf(decimalFormat.format(dataObj.getFuelQty()*dataObj.getPrice()));
                // dataObj.fuelType= spinner.getSelectedItem().toString();
                dataObj.date= new Date().getTime();


                myRef.child(String.valueOf(invoiceNo+1)).setValue(dataObj);
                Toast.makeText(SalesActivity.this,"Invoice is Saved & Uploaded",Toast.LENGTH_LONG).show();

                // printPDF();
            }
        });



    }



    private void callFindViewById() {

        //View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.my_row, null, false);

        input=new ArrayList<>();
        name=findViewById(R.id.editTextName);
        recyclerView= findViewById(R.id.recyclerView);
        saveAndPrintButton=findViewById(R.id.btnSave);
        //name = (EditText) findViewById(R.id.person_name);
        price= (EditText) findViewById(R.id.price);
        item=findViewById(R.id.item);
        qty=findViewById(R.id.qty);
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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        MyAdapter myAdapter=new MyAdapter(this, input);
//       name.setText(nuum);
        //Log.e("Test",nuum+" gkgkgkjgjgkgjkgkjg");
       // System.out.println(nuum+"   jfjfjhfhjfjgdyfyidufiyduofuiduifyuudiydyidiydi");
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



    }


}