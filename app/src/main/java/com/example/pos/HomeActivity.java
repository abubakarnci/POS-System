package com.example.pos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.transition.Slide;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{


    ImageButton scanBtn, btnLogout;
    ImageButton saleBtn,items;
    ImageButton oldInvoice,btnMap;
    TextView date;

    private GoogleSignInClient mGoogleSignInClient;


    NavigationView nav;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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
                        break;

                    case R.id.menu_call:
                        Toast.makeText(getApplicationContext(),"Call Activity",Toast.LENGTH_LONG).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        Intent i= new Intent(HomeActivity.this,Dialer.class);
                        startActivity(i);
                        break;

                    case R.id.menu_profile:
                        Toast.makeText(getApplicationContext(),"Profile Activity",Toast.LENGTH_LONG).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        Intent j= new Intent(HomeActivity.this,Profile.class);
                        startActivity(j);
                        break;
                }

                return true;
            }
        });


        Date calendar= Calendar.getInstance().getTime();
        String currentTime= DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar);
        String currentDate= DateFormat.getDateInstance(DateFormat.LONG).format(calendar);


        items=findViewById(R.id.items);
        date=findViewById(R.id.date);
        date.setText(currentTime+": "+currentDate);
        scanBtn=findViewById(R.id.scan);
        scanBtn.setOnClickListener(this);
        btnLogout=findViewById(R.id.logout);
        oldInvoice=findViewById(R.id.oldInvoice);
        saleBtn=findViewById(R.id.sales);
        btnMap=findViewById(R.id.map);


        items.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(HomeActivity.this,Inventory.class);
                startActivity(i);
            }
        });


        saleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(HomeActivity.this,SalesActivity.class);
                startActivity(i);
            }
        });

        oldInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(HomeActivity.this,InvoiceActivity2.class);
                startActivity(i);
            }
        });

        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(HomeActivity.this, MapActivity.class);
                startActivity(i);
            }
        });
        GoogleSignInOptions gso= new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient= GoogleSignIn.getClient(this, gso);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                mGoogleSignInClient.signOut();
                Toast.makeText(HomeActivity.this,"You are Logged Out",Toast.LENGTH_SHORT).show();
                Intent inToMain=new Intent(HomeActivity.this, MainActivity.class);
                startActivity(inToMain);
            }
        });




    }

    @Override
    public void onClick(View view) {
        scanCode();
    }
    private void scanCode(){

        IntentIntegrator integrator=new IntentIntegrator(this);
        integrator.setCaptureActivity(CaptureAct.class);
        integrator.setOrientationLocked(false);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Scanning Code");
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data){

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode,data);
        if(result != null){
            if(result.getContents() !=null){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(result.getContents());
                builder.setTitle("Scanning Result");
                builder.setPositiveButton("Scan Again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        scanCode();
                    }
                }).setNegativeButton("finish", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
            else{
                Toast.makeText(this, "No Results", Toast.LENGTH_LONG).show();
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }



}