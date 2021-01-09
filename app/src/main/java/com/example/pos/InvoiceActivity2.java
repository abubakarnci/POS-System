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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
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
import java.util.ArrayList;
import java.util.Date;

import ir.androidexception.datatable.DataTable;
import ir.androidexception.datatable.model.DataTableHeader;
import ir.androidexception.datatable.model.DataTableRow;

public class InvoiceActivity2 extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("invoice");
    DatabaseReference retriveRef;
    Button oldPrintBtn;
    EditText oldPrintEditText;
    DataTable dataTable;
    DataTableHeader header;
    SimpleDateFormat dateFormat=new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat timeFormat=new SimpleDateFormat("hh:mm a");
    DecimalFormat decimalFormat=new DecimalFormat("#.##");
    ArrayList<DataTableRow> rows=new ArrayList<>();
    long invoiceNo;
    String customerName;
    long databaseDate;
    String fuelType;
    Double fuelQty;
    double amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice2);

        oldPrintBtn=findViewById(R.id.oldPrintBtn);
        oldPrintEditText=findViewById(R.id.oldPrintEditText);
        dataTable=findViewById(R.id.data_table);

        header=new DataTableHeader.Builder()
                .item("Invoice No.",5)
                .item("Customer Name",5)
                .item("Date",5)
                .item("Time",5)
                .item("Amount",5)
                .build();
        loadTable();
        listeners();


    }

    private void listeners() {
        oldPrintBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                printPdf(oldPrintEditText.getText().toString());

            }
        });
    }

    private void printPdf(final String toString) {

        if (toString.isEmpty()) {
            oldPrintEditText.setError("Please Enter valid invoice number");
            oldPrintEditText.requestFocus();
        }

        else {

            retriveRef = FirebaseDatabase.getInstance().getReference().child("invoice").child(toString);
            retriveRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    invoiceNo = (long) snapshot.child("invoiceNo").getValue();

                        databaseDate = (long) snapshot.child("date").getValue();
                        customerName = (String) snapshot.child("customerName").getValue();
                        fuelType = (String) snapshot.child("fuelType").getValue();
                        fuelQty = Double.parseDouble(String.valueOf(snapshot.child("fuelQty").getValue()));
                        amount = Double.parseDouble(String.valueOf(snapshot.child("amount").getValue()));


                        PdfDocument myPdfDocument = new PdfDocument();
                        Paint paint = new Paint();
                        Paint forLine = new Paint();
                        forLine.setColor(Color.rgb(0, 50, 150));

                        PdfDocument.PageInfo myPageInfo = new PdfDocument.PageInfo.Builder(250, 350, 1).create();
                        PdfDocument.Page myPage = myPdfDocument.startPage(myPageInfo);

                        Canvas canvas = myPage.getCanvas();
                        paint.setTextSize(15.5f);
                        paint.setColor(Color.rgb(0, 50, 250));
                        canvas.drawText("Gill's Shop", 20, 20, paint);
                        paint.setTextSize(8.5f);
                        canvas.drawText("Unit 2, Trinity CourtYard", 20, 40, paint);
                        canvas.drawText(" Clondalkin, Dublin", 20, 55, paint);

                        forLine.setStyle(Paint.Style.STROKE);
                        forLine.setPathEffect(new DashPathEffect(new float[]{5, 5}, 0));
                        forLine.setStrokeWidth(2);
                        canvas.drawLine(20, 65, 230, 65, forLine);

                        canvas.drawText("Customer Name: " + customerName, 20, 80, paint);
                        canvas.drawLine(20, 90, 230, 90, forLine);
                        canvas.drawText("Purchase: ", 20, 105, paint);
                        canvas.drawText(fuelType, 20, 135, paint);
                        canvas.drawText(fuelQty + " ltr", 120, 135, paint);

                        paint.setTextAlign(Paint.Align.RIGHT);
                        canvas.drawText(String.valueOf(decimalFormat.format(amount)), 230, 135, paint);
                        paint.setTextAlign(Paint.Align.LEFT);

                        canvas.drawText("+%", 20, 175, paint);
                        canvas.drawText("Tax 5%", 120, 175, paint);
                        paint.setTextAlign(Paint.Align.RIGHT);
                        canvas.drawText(decimalFormat.format(amount * 5 / 100), 230, 175, paint);
                        paint.setTextAlign(Paint.Align.LEFT);

                        canvas.drawLine(20, 210, 230, 210, forLine);
                        paint.setTextSize(10f);
                        canvas.drawText("Total", 120, 225, paint);
                        paint.setTextAlign(Paint.Align.RIGHT);
                        canvas.drawText(decimalFormat.format((amount * 5 / 100) + amount), 230, 225, paint);

                        paint.setTextAlign(Paint.Align.LEFT);
                        paint.setTextSize(8.5f);
                        canvas.drawText("Date: " + dateFormat.format(new Date(databaseDate).getTime()), 20, 260, paint);
                        canvas.drawText("Invoice No: " + String.valueOf(invoiceNo), 20, 275, paint);
                        canvas.drawText("Payment Method: Cash", 20, 290, paint);

                        paint.setTextAlign(Paint.Align.CENTER);
                        paint.setTextSize(12f);
                        canvas.drawText("Thank You!", canvas.getWidth() / 2, 320, paint);

                        myPdfDocument.finishPage(myPage);
                        File file = new File(InvoiceActivity2.this.getExternalFilesDir("/"), invoiceNo + ".pdf");
                        try {
                            myPdfDocument.writeTo(new FileOutputStream(file));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        Toast.makeText(InvoiceActivity2.this, "Invoice is Saved", Toast.LENGTH_LONG).show();
                        myPdfDocument.close();


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
    }
    private void loadTable() {

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot myDataSnapshot : snapshot.getChildren()){
                    DataTableRow row =new DataTableRow.Builder()
                            .value(String.valueOf(myDataSnapshot.child("invoiceNo").getValue()))
                            .value(String.valueOf(myDataSnapshot.child("customerName").getValue()))
                            .value(dateFormat.format(myDataSnapshot.child("date").getValue()))
                            .value(timeFormat.format(myDataSnapshot.child("date").getValue()))
                            .value(String.valueOf(myDataSnapshot.child("tBill").getValue()))
                            .build();
                    rows.add(row);
                }
                dataTable.setHeader(header);
                dataTable.setRows(rows);
                dataTable.inflate(InvoiceActivity2.this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}