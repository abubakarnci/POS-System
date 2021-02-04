package com.example.pos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
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
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import ir.androidexception.datatable.DataTable;
import ir.androidexception.datatable.model.DataTableHeader;
import ir.androidexception.datatable.model.DataTableRow;

public class InvoiceActivity2 extends AppCompatActivity {


    private static final String TAG = "PdfCreatorActivity";
    private File pdfFile;
    Context context;


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
    String item, qty,price,bill, tBill;

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

                        item =  snapshot.child("item").getValue().toString();
                    //Log.e("Test",item+" gkgkgkjgjgkgjkgkjg");
                     //System.out.println(item+"   jfjfjhfhjfjgdyfyidufiyduofuiduifyuudiydyidiydi");
                        qty = (String.valueOf(snapshot.child("qty").getValue()));
                        bill = (String.valueOf(snapshot.child("bill").getValue()));
                       tBill = (String.valueOf(snapshot.child("tBill").getValue()));
                        price = (String.valueOf(snapshot.child("price").getValue()));

                    try {
                        createPdfWrapper();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (DocumentException e) {
                        e.printStackTrace();
                    }


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


    private void createPdfWrapper() throws FileNotFoundException, DocumentException {
            createPdf();

    }


    private void createPdf() throws FileNotFoundException, DocumentException {

        File docsFolder = new File(Environment.getExternalStorageDirectory() + "/Invoices");
        if (!docsFolder.exists()) {
            docsFolder.mkdir();
            Log.i(TAG, "Created a new directory for PDF");
        }
        String pdfname = (invoiceNo)+" Gill's POS.pdf";
        pdfFile = new File(docsFolder.getAbsolutePath(), pdfname);
        OutputStream output = new FileOutputStream(pdfFile);
        Document document = new Document(PageSize.A4);
        PdfPTable table = new PdfPTable(new float[]{5, 5, 5,5});
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.getDefaultCell().setFixedHeight(50);
        table.setTotalWidth(PageSize.A4.getWidth());
        table.setWidthPercentage(100);
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        Font f = new Font(Font.FontFamily.TIMES_ROMAN, 45.0f, Font.BOLDITALIC, BaseColor.RED);
        Font g = new Font(Font.FontFamily.HELVETICA, 25.0f, Font.NORMAL, BaseColor.BLACK);
        Font c = new Font(Font.FontFamily.HELVETICA, 21.0f, Font.UNDERLINE, BaseColor.BLACK);
        Font h = new Font(Font.FontFamily.HELVETICA, 19.0f, Font.BOLDITALIC, BaseColor.BLACK);
        Font i = new Font(Font.FontFamily.HELVETICA, 17.0f, Font.NORMAL, BaseColor.BLACK);
        Font b = new Font(Font.FontFamily.HELVETICA, 21.0f, Font.UNDERLINE, BaseColor.RED);
        table.addCell((new Phrase("Items",h)));
        table.addCell((new Phrase("Quantity",h)));
        table.addCell((new Phrase("Price Per Unit",h)));
        table.addCell((new Phrase("Extended Value",h)));
        table.setHeaderRows(1);
        PdfPCell[] cells = table.getRow(0).getCells();
        for (int j = 0; j < cells.length; j++) {
            cells[j].setBackgroundColor(BaseColor.LIGHT_GRAY);
        }


        double amount=0.0;
        String[] itemm = String.valueOf(item).split(",");
        String[] qtyy = String.valueOf(qty).split(",");
        String[] pricee = String.valueOf(price).split(",");
        String[] billl = String.valueOf(bill).split(",");


        for (int x=0; x<itemm.length; x++) {
            // Log.e("Sales: ",itemm[x]);
            // System.out.println(itemm[x]);

            table.addCell((new Phrase(itemm[x],i)));
            table.addCell((new Phrase(qtyy[x],i)));
          table.addCell((new Phrase(pricee[x],i)));
         table.addCell((new Phrase(billl[x],i)));

        }


        PdfWriter.getInstance(document, output);
        document.open();

        document.add(new Paragraph("              Gill's Shop \n", f));
        document.add(new Paragraph("                   Unit 2, Trinity CourtYard", g));
        document.add(new Paragraph("                        Clondalkin, Dublin", g));
        document.add(new Paragraph("            Email:- Gillventuresltd@gmail.com", g));
        document.add(new Paragraph("                        Tel:- 014542844 \n\n", g));
        document.add(new Paragraph("Invoice No: "+(invoiceNo), c));
        document.add(new Paragraph("Customer: "+customerName+"\n\n", c));
        document.add(table);
        document.add(new Paragraph("\nTotal Bill: € "+tBill, b));
        document.add(new Paragraph("\n              Thank You!",f));


        document.close();

        Toast.makeText(InvoiceActivity2.this,"Invoice is Saved & Printed ",Toast.LENGTH_LONG).show();
        oldPrintEditText.setText(null);


    }





}