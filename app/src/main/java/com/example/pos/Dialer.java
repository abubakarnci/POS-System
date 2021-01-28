package com.example.pos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Dialer extends AppCompatActivity {

    EditText mNumber;
    Button mBtn;

    String number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialer);

        mNumber=findViewById(R.id.numberEt);
        mBtn=findViewById(R.id.dialBt);

        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number = mNumber.getText().toString().trim();
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+ Uri.encode(number)));
                startActivity(intent);
            }
        });


    }
}