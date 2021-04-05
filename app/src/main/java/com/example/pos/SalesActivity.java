package com.example.pos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

public class SalesActivity extends AppCompatActivity implements PaymentResultListener {

    private static final String TAG = "PdfCreatorActivity";
    final private int REQUEST_CODE_ASK_PERMISSIONS = 111;
    private File pdfFile;
    Context context;


    private RecyclerView recyclerView;
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager mlayoutManager;
    ArrayList<InventObj> input;
    ArrayList<DataObj> array;
    DatabaseReference retriveRef;
    EditText name;
    DataObj dataObj=new DataObj();
    Button payButton;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("invoice");
    ArrayAdapter<String> adapter;
    long invoiceNo = 0;
    DecimalFormat decimalFormat=new DecimalFormat("#.##");
    SimpleDateFormat dateFormat=new SimpleDateFormat("dd-MM-yyyy hh:mm a");
    EditText item,price,qty;
    EditText insert;
    Button bInset;

    String iName="";

    Double subTotal;
    String iQty;
    String iPrice;
    String iTotal;


    Dialog myDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);

        myDialog = new Dialog(this);



        Checkout.preload(getApplicationContext());

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

    public void ShowPopup(View v){

        TextView txtclose;
        Button btnCash;
        Button btnCard;
        myDialog.setContentView(R.layout.paymentpopup);
        txtclose =(TextView) myDialog.findViewById(R.id.txtclose);

        btnCash=(Button) myDialog.findViewById(R.id.cash);
        btnCard=(Button) myDialog.findViewById(R.id.card);

        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.show();


        btnCash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                dataObj.invoiceNo=invoiceNo +1;
                dataObj.customerName= String.valueOf(name.getText());
                for(int i=0; i<mAdapter.dataObj.size(); i++) {
                    dataObj.item = mAdapter.dataObj.get(i).getItem();
                    iName=(mAdapter.dataObj.get(i).getItem().toString());

                    Log.e("Test: ",iName);
                    System.out.println(iName);

                    dataObj.qty=mAdapter.dataObj.get(i).getQty();
                    iQty=mAdapter.dataObj.get(i).getQty().toString();

                    dataObj.price=mAdapter.dataObj.get(i).getPrice();
                    iPrice=mAdapter.dataObj.get(i).getPrice().toString();

                    dataObj.bill=mAdapter.dataObj.get(i).getBill();
                    iTotal=mAdapter.dataObj.get(i).getBill().toString();

                    dataObj.tBill=mAdapter.dataObj.get(i).tBill;
                    subTotal=mAdapter.dataObj.get(i).tBill;
                }



                dataObj.date= new Date().getTime();



                myRef.child(String.valueOf(invoiceNo+1)).setValue(dataObj);
                //Toast.makeText(SalesActivity.this,"Invoice is Saved & Uploaded",Toast.LENGTH_LONG).show();

                try {
                    createPdfWrapper();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (DocumentException e) {
                    e.printStackTrace();
                }


                Intent p= new Intent(SalesActivity.this,CashActivity.class);
                p.putExtra("name",iName);
                p.putExtra("qty",iQty);
                p.putExtra("price",iPrice);
                p.putExtra("total",iTotal);
                p.putExtra("subTotal",subTotal.toString());
                startActivity(p);
                // printPDF();
            }
        });





        btnCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataObj.invoiceNo=invoiceNo +1;
                dataObj.customerName= String.valueOf(name.getText());
                for(int i=0; i<mAdapter.dataObj.size(); i++) {
                    dataObj.item = mAdapter.dataObj.get(i).getItem();
                    dataObj.qty=mAdapter.dataObj.get(i).getQty();
                    dataObj.price=mAdapter.dataObj.get(i).getPrice();
                    dataObj.bill=mAdapter.dataObj.get(i).getBill();
                    dataObj.tBill=mAdapter.dataObj.get(i).tBill;
                }

                dataObj.date= new Date().getTime();


                makepayment();




                myRef.child(String.valueOf(invoiceNo+1)).setValue(dataObj);
                //Toast.makeText(SalesActivity.this,"Invoice is Saved & Uploaded",Toast.LENGTH_LONG).show();

                try {
                    createPdfWrapper();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (DocumentException e) {
                    e.printStackTrace();
                }


                // printPDF();
            }
        });




    }




    private void callOnClickListener() {


        bInset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String row=insert.getText().toString();

                if(row.isEmpty()){
                    insert.setError("Please Enter valid number");
                    insert.requestFocus();
                }
                else {
                    int position = Integer.parseInt(row);

                    input.add(position, new InventObj("New Line Added at " + position, 0.0));
                    mAdapter.notifyItemInserted(position);
                }

            }
        });



    }

    private void makepayment() {

        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_rheSHzRKHKOYZI");

        checkout.setImage(R.drawable.pos2);

        /**
         * Reference to current activity
         */
        final Activity activity = this;


        try {
            JSONObject options = new JSONObject();


            options.put("name", "Gill's POS");
            options.put("description", "Reference No. #123456");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
           // options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
            options.put("theme.color", "#3399cc");
            options.put("currency", "EUR");
            options.put("amount", dataObj.tBill*100);//pass amount in currency subunits
            options.put("prefill.email", "email@example.com");
            options.put("prefill.contact","9988776655");
            checkout.open(activity, options);
            Toast.makeText(SalesActivity.this,"Invoice is Saved & Uploaded",Toast.LENGTH_LONG).show();

        } catch(Exception e) {
            Log.e("TAG", "Error in starting Razorpay Checkout", e);
        }
    }



    private void createPdfWrapper() throws FileNotFoundException, DocumentException {
        int hasWriteStoragePermission = ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasWriteStoragePermission != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!shouldShowRequestPermissionRationale(Manifest.permission.WRITE_CONTACTS)) {
                    showMessageOKCancel("You need to allow access to Storage",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                                REQUEST_CODE_ASK_PERMISSIONS);
                                    }
                                }
                            });
                    return;
                }
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_CODE_ASK_PERMISSIONS);
            }
            return;
        } else {
            createPdf();
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private void createPdf() throws FileNotFoundException, DocumentException {

        File docsFolder = new File(Environment.getExternalStorageDirectory() + "/Invoices");
        if (!docsFolder.exists()) {
            docsFolder.mkdir();
            Log.i(TAG, "Created a new directory for PDF");
        }
        String pdfname = (invoiceNo+1)+" Gill's POS.pdf";
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
        String[] itemm = String.valueOf(mAdapter.dataObj.get(0).getItem()).split(",");
        String[] qtyy = String.valueOf(mAdapter.dataObj.get(0).getQty()).split(",");
        String[] pricee = String.valueOf(mAdapter.dataObj.get(0).getPrice()).split(",");
        String[] billl = String.valueOf(mAdapter.dataObj.get(0).getBill()).split(",");
        amount= amount + mAdapter.dataObj.get(itemm.length-1).tBill;

        for (int x=0; x<itemm.length; x++) {
           // Log.e("Sales: ",result[x]);
           // System.out.println(result[x]);

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
        document.add(new Paragraph("Invoice No: "+(invoiceNo+1), c));
        document.add(new Paragraph("Customer: "+name.getText()+"\n\n", c));
        document.add(table);
        document.add(new Paragraph("\nTotal Bill: € "+amount, b));
        document.add(new Paragraph("\n              Thank You!",f));


        document.close();


    }




   /* private void printPDF() {

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
        canvas.drawText("Items: " , 20, 110, paint);
        canvas.drawText("Quantity: " , 120, 110, paint);
        canvas.drawText("Extended Value: " , 210, 110, paint);
        double amount=0.0;
        for(int i=0; i<mAdapter.dataObj.size(); i++) {

            canvas.drawText(String.valueOf(mAdapter.dataObj.get(i).getItem()), 20, 135, paint);
            canvas.drawText(mAdapter.dataObj.get(i).getQty().toString() , 120, 135, paint);
            canvas.drawText(mAdapter.dataObj.get(i).getBill().toString() , 230, 135, paint);
            //dataObj.price=mAdapter.dataObj.get(i).getPrice();
            //dataObj.tBill=mAdapter.dataObj.get(i).tBill;
            amount= dataObj.tBill=mAdapter.dataObj.get(i).tBill;
        }

        String[] itemm = String.valueOf(mAdapter.dataObj.get(0).getItem()).split("\\s");
        String[] qtyy = String.valueOf(mAdapter.dataObj.get(0).getQty()).split("\\s");
        String[] billl = String.valueOf(mAdapter.dataObj.get(0).getBill()).split("\\s");
        int y=0;
        for (int x=0; x<itemm.length; x++) {
            // Log.e("Sales: ",result[x]);
            // System.out.println(result[x]);

             y=y+10;
            canvas.drawText(itemm[x], 20, 135+y, paint);


        }


       // double amount= itemPrice[spinner.getSelectedItemPosition()]*Double.parseDouble(qty.getText().toString());

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
        File file= new File(this.getExternalFilesDir("/"),"Gill's POS "+invoiceNo+".pdf");
        try {
            myPdfDocument.writeTo(new FileOutputStream(file));
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        myPdfDocument.close();


    }*/






    private void callFindViewById() {

        //View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.my_row, null, false);



        bInset=findViewById(R.id.bInset);
        insert=findViewById(R.id.insert);
        input=new ArrayList<>();
        array=new ArrayList<>();
        name=findViewById(R.id.editTextName);
        recyclerView= findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        mlayoutManager=new LinearLayoutManager(this);
        context = this;


        payButton=findViewById(R.id.btnSave);
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
        mAdapter= new MyAdapter(this, input);
        //MyAdapter myAdapter=new MyAdapter(this, input);
//       name.setText(nuum);
        //Log.e("Test",nuum+" gkgkgkjgjgkgjkgkjg");
       // System.out.println(nuum+"   jfjfjhfhjfjgdyfyidufiyduofuiduifyuudiydyidiydi");
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(mlayoutManager);


        mAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(int position) {
                input.get(position).changeText("Clicked");
                mAdapter.notifyItemChanged(position);
            }

            @Override
            public void OnDeleteClick(int position) {
                input.remove(position);
                mAdapter.notifyItemRemoved(position);
            }
        });

    }


    @Override
    public void onPaymentSuccess(String s) {

        Toast.makeText(SalesActivity.this,"Successful payment ID: "+s,Toast.LENGTH_LONG).show();

    }

    @Override
    public void onPaymentError(int i, String s) {

        Toast.makeText(SalesActivity.this,"Failed and cause is: "+s,Toast.LENGTH_LONG).show();

    }
}