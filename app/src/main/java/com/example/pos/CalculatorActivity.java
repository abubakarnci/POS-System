package com.example.pos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import org.mariuszgromada.math.mxparser.*;

public class CalculatorActivity extends AppCompatActivity {

    private EditText display;

    NavigationView nav;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

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
                        Intent h= new Intent(CalculatorActivity.this,HomeActivity.class);
                        startActivity(h);
                        break;

                    case R.id.menu_call:
                        Toast.makeText(getApplicationContext(),"Call Activity",Toast.LENGTH_LONG).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        Intent i= new Intent(CalculatorActivity.this,Dialer.class);
                        startActivity(i);
                        break;

                    case R.id.menu_profile:
                        Toast.makeText(getApplicationContext(),"Profile Activity",Toast.LENGTH_LONG).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        Intent j= new Intent(CalculatorActivity.this,Profile.class);
                        startActivity(j);
                        break;

                    case R.id.menu_alarm:
                        Toast.makeText(getApplicationContext(),"Alarm Activity",Toast.LENGTH_LONG).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        Intent k= new Intent(CalculatorActivity.this,MainAlarm.class);
                        startActivity(k);
                        break;

                    case R.id.menu_bot:
                        Toast.makeText(getApplicationContext(),"Live Help",Toast.LENGTH_LONG).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        Intent p= new Intent(CalculatorActivity.this,ChatActivity.class);
                        startActivity(p);
                        break;

                    case R.id.menu_calculator:
                        Toast.makeText(getApplicationContext(),"Calculator",Toast.LENGTH_LONG).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        Intent c= new Intent(CalculatorActivity.this,CalculatorActivity.class);
                        startActivity(c);
                        break;

                }

                return true;
            }
        });





        display=findViewById(R.id.result);
        display.setShowSoftInputOnFocus(false);

        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getString(R.string.display).equals(display.getText().toString())){

                    display.setText("");
                }
            }
        });

    }

    private void updateText(String strToAdd){
        String oldStr=display.getText().toString();

        int cursorPos=display.getSelectionStart();
        String leftStr=oldStr.substring(0,cursorPos);
        String rightStr=oldStr.substring(cursorPos);

        if(getString(R.string.display).equals(display.getText().toString())){

            display.setText(strToAdd);
            display.setSelection(cursorPos+1);


        }
        else{

            display.setText(String.format("%s%s%s", leftStr,strToAdd, rightStr));
            display.setSelection(cursorPos+1);
        }


    }

    public void zeroBTN(View view){

        updateText("0");
    }
    public void oneBTN(View view){

        updateText("1");

    }
    public void twoBTN(View view){

        updateText("2");

    }
    public void threeBTN(View view){

        updateText("3");

    }
    public void fourBTN(View view){

        updateText("4");

    }
    public void fiveBTN(View view){

        updateText("5");

    }
    public void sixBTN(View view){

        updateText("6");

    }
    public void sevenBTN(View view){

        updateText("7");

    }
    public void eightBTN(View view){
        updateText("8");
    }
    public void nineBTN(View view){

        updateText("9");

    }
    public void multiplyBTN(View view){
        updateText("×");


    }
    public void subtractBTN(View view){
        updateText("-");


    }
    public void addBTN(View view){

        updateText("+");

    }
    public void divideBTN(View view){
        updateText("÷");


    }
    public void parBTN(View view){

        int cursorPos=display.getSelectionStart();
        int openPar=0;
        int closePar=0;
        int textLen=display.getText().length();

        for(int i=0; i<cursorPos; i++){

            if(display.getText().toString().substring(i, i+1).equals("(")){
                openPar +=1;
            }
            else if(display.getText().toString().substring(i, i+1).equals(")")){
                closePar +=1;
            }

        }
        if(openPar == closePar ||  display.getText().toString().substring(textLen-1, textLen).equals("(")){

            updateText("(");

        }

        else if(closePar < openPar &&  !display.getText().toString().substring(textLen-1, textLen).equals("(")){

            updateText(")");
        }
        display.setSelection(cursorPos + 1);


    }
    public void plusMinusBTN(View view){

        updateText("-");

    }
    public void decimalBTN(View view){
        updateText(".");


    }
    public void backBTN(View view){

        int cursorPos=display.getSelectionStart();
        int textLen = display.getText().length();

        if(cursorPos != 0 && textLen != 0){

            SpannableStringBuilder selection = (SpannableStringBuilder) display.getText();
            selection.replace(cursorPos-1, cursorPos, "");
            display.setText(selection);
            display.setSelection(cursorPos-1);

        }

    }
    public void equalBTN(View view){

        String userExp = display.getText().toString();
        userExp = userExp.replaceAll("÷", "/");
        userExp = userExp.replaceAll("×", "*");

        Expression exp = new Expression(userExp);
        String result = String.valueOf(exp.calculate());
        display.setText(result);
        display.setSelection(result.length());

    }
    public void clearBTN(View view){
        display.setText("");

    }
    public void expBTN(View view){

        updateText("^");

    }




}