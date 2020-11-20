package com.example.pos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import java.text.SimpleDateFormat;
import java.util.Date;

public class InvoiceActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("invoice");
    DataObj dataObj=new DataObj();
    Button saveAndPrintButton, printButton;
    EditText name, qty;
    Spinner spinner;
    String[] itemList;
    double [] itemPrice;
    ArrayAdapter <String> adapter;
    long invoiceNo = 0;
    DecimalFormat decimalFormat=new DecimalFormat("#.##");
    SimpleDateFormat dateFormat=new SimpleDateFormat("dd-MM-yyyy hh:mm a");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);

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
    }

    private void callOnClickListener() {

        saveAndPrintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataObj.invoiceNo=invoiceNo +1;
                dataObj.customerName= String.valueOf(name.getText());
                dataObj.fuelQty= Double.parseDouble(String.valueOf(qty.getText()));
                dataObj.amount=Double.valueOf(decimalFormat.format(dataObj.getFuelQty()*itemPrice[spinner.getSelectedItemPosition()]));
                dataObj.fuelType= spinner.getSelectedItem().toString();
                dataObj.date= new Date().getTime();

                myRef.child(String.valueOf(invoiceNo+1)).setValue(dataObj);
                Toast.makeText(InvoiceActivity.this,"Invoice is Saved & Uploaded",Toast.LENGTH_LONG).show();

                printPDF();
            }
        });


        printButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(InvoiceActivity.this,InvoiceActivity2.class);
                startActivity(i);
            }
        });
    }

    private void printPDF() {

        PdfDocument myPdfDocument= new PdfDocument();
        Paint paint =new Paint();
        Paint forLine=new Paint();
        forLine.setColor(Color.rgb(0,50,150));

        PdfDocument.PageInfo myPageInfo=new PdfDocument.PageInfo.Builder(250,350, 1).create();
        PdfDocument.Page myPage =myPdfDocument.startPage(myPageInfo);

        Canvas canvas =myPage.getCanvas();
        paint.setTextSize(15.5f);
        paint.setColor(Color.rgb(0,50,250));
        canvas.drawText("Gill's Shop", 20,20,paint);
        paint.setTextSize(8.5f);
        canvas.drawText("Unit 2, Trinity CourtYard",20,40,paint);
        canvas.drawText(" Clondalkin, Dublin",20,55,paint);

        forLine.setStyle(Paint.Style.STROKE);
        forLine.setPathEffect(new DashPathEffect(new float[]{5,5},0));
        forLine.setStrokeWidth(2);
        canvas.drawLine(20,65,230,65, forLine);

        canvas.drawText("Customer Name: "+ name.getText(),20,80, paint);
        canvas.drawLine(20,90,230,90, forLine);
        canvas.drawText("Purchase: " , 20, 105, paint);
        canvas.drawText(spinner.getSelectedItem().toString(),20,135,paint);
        canvas.drawText(qty.getText()+" ltr",120,135,paint);

        double amount= itemPrice[spinner.getSelectedItemPosition()]*Double.parseDouble(qty.getText().toString());
        paint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText(String.valueOf(decimalFormat.format(amount)),230,135, paint);
        paint.setTextAlign(Paint.Align.LEFT);

        canvas.drawText("+%",20,175, paint);
        canvas.drawText("Tax 5%",120,175, paint);
        paint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText(decimalFormat.format(amount*5/100),230,175, paint);
        paint.setTextAlign(Paint.Align.LEFT);

        canvas.drawLine(20,210,230,210, forLine);
        paint.setTextSize(10f);
        canvas.drawText("Total", 120,225, paint);
        paint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText(decimalFormat.format((amount*5/100)+amount),230,225, paint);

        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTextSize(8.5f);
        canvas.drawText("Date: "+dateFormat.format(new Date().getTime()), 20,260, paint);
        canvas.drawText("Invoice No: "+String.valueOf(invoiceNo+1), 20,275, paint);
        canvas.drawText("Payment Method: Cash", 20,290, paint);

        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(12f);
        canvas.drawText("Thank You!", canvas.getWidth()/2,320, paint);

        myPdfDocument.finishPage(myPage);
        File file= new File(this.getExternalFilesDir("/"),"Gill's POS.pdf");
        try {
            myPdfDocument.writeTo(new FileOutputStream(file));
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        myPdfDocument.close();


    }

    private void callFindViewById() {
        saveAndPrintButton=findViewById(R.id.btnSaveAndPrint);
        printButton=findViewById(R.id.btnPrint);
        name=findViewById(R.id.editTextName);
        qty=findViewById(R.id.editTextQty);
        spinner=findViewById(R.id.spinner);
        itemList=new String []{"Petrol", "Diesel"};
        itemPrice=new double []{72.67, 36.97};
        adapter= new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, itemList);
        spinner.setAdapter(adapter);

    }
}