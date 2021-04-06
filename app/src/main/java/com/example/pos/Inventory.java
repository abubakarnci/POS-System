package com.example.pos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;

public class Inventory extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("items");
    InventObj inventObj=new InventObj();
    Button save, edit;
    EditText name,type,cPrice,sPrice,quantity,tax ;
    TextView profit;
    long itemNo = 0;
    DecimalFormat decimalFormat=new DecimalFormat("#.##");

    NavigationView nav;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        nav=(NavigationView)findViewById(R.id.navmenu);
        drawerLayout=(DrawerLayout)findViewById(R.id.drawer);
        toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open, R.string.close);

        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){

                    case R.id.menu_home:
                        Toast.makeText(getApplicationContext(),"Home Page",Toast.LENGTH_LONG).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        Intent h= new Intent(Inventory.this,HomeActivity.class);
                        startActivity(h);
                        break;

                    case R.id.menu_call:
                        Toast.makeText(getApplicationContext(),"Call Activity",Toast.LENGTH_LONG).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        Intent i= new Intent(Inventory.this,Dialer.class);
                        startActivity(i);
                        break;

                    case R.id.menu_profile:
                        Toast.makeText(getApplicationContext(),"Profile Activity",Toast.LENGTH_LONG).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        Intent j= new Intent(Inventory.this,Profile.class);
                        startActivity(j);
                        break;

                    case R.id.menu_alarm:
                        Toast.makeText(getApplicationContext(),"Alarm Activity",Toast.LENGTH_LONG).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        Intent k= new Intent(Inventory.this,MainAlarm.class);
                        startActivity(k);
                        break;
                    case R.id.menu_bot:
                        Toast.makeText(getApplicationContext(),"ChatBot",Toast.LENGTH_LONG).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        Intent b= new Intent(Inventory.this,ChatActivity.class);
                        startActivity(b);
                        break;

                    case R.id.menu_calculator:
                        Toast.makeText(getApplicationContext(),"Calculator",Toast.LENGTH_LONG).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        Intent c= new Intent(Inventory.this,CalculatorActivity.class);
                        startActivity(c);
                        break;
                }

                return true;
            }
        });




        save=findViewById(R.id.button);
        name=findViewById(R.id.itemName);
        type=findViewById(R.id.itemType);
        cPrice=findViewById(R.id.costPrice);
        sPrice=findViewById(R.id.sellPrice);
        quantity=findViewById(R.id.quantity);
        profit=findViewById(R.id.profit);
        tax=findViewById(R.id.tax);

        edit=findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(Inventory.this,Inventory2.class);
                startActivity(i);
            }
        });




        callOnClickListener();


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                itemNo= snapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void callOnClickListener() {
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String na,ty,sP,cP,qa,tx;
                na=String.valueOf(name.getText());
                ty=String.valueOf(type.getText());
                sP=(String.valueOf(sPrice.getText()));
                cP=(String.valueOf(cPrice.getText()));
                qa=(String.valueOf(quantity.getText()));
                tx=(String.valueOf(tax.getText()));

                if(na.isEmpty()){
                    name.setError("Please Enter Name");
                    name.requestFocus();
                }
                else if(ty.isEmpty()){
                    type.setError("Please Enter Type");
                    type.requestFocus();
                }
                else if(sP.isEmpty()){
                    sPrice.setError("Please Enter Selling Price");
                    sPrice.requestFocus();
                }
                else if(cP.isEmpty()){
                    cPrice.setError("Please Enter Cost Price");
                    cPrice.requestFocus();
                }
                else if(qa.isEmpty()){
                    quantity.setError("Please Enter Quantity");
                    quantity.requestFocus();
                }
                else if(tx.isEmpty()){
                    tax.setError("Please Enter Tax");
                    tax.requestFocus();
                }
               else {
                    inventObj.itemNo=itemNo+1;
                    inventObj.itemName= String.valueOf(name.getText());
                    inventObj.itemType= String.valueOf(type.getText());
                    inventObj.sellPrice= Double.parseDouble(String.valueOf(sPrice.getText()));
                    inventObj.costPrice= Double.parseDouble(String.valueOf(cPrice.getText()));
                    inventObj.quantity= Double.parseDouble(String.valueOf(quantity.getText()));
                    inventObj.tax= Double.parseDouble(String.valueOf(tax.getText()));
                    double cal1=((Double.parseDouble(String.valueOf(sPrice.getText()))) - (Double.parseDouble(String.valueOf(cPrice.getText()))));
                    double cal=(cal1 / (Double.parseDouble(String.valueOf(sPrice.getText()))))*100;
                    inventObj.profit=Double.valueOf(decimalFormat.format(cal));

                    profit.setText(inventObj.profit+"%");
                    myRef.child(String.valueOf(itemNo+1)).setValue(inventObj);
                    Toast.makeText(Inventory.this,"Item Uploaded",Toast.LENGTH_LONG).show();

                }




                 }
        });

    }


}