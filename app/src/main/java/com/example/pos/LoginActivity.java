package com.example.pos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {



    TextInputEditText emailId, passwordId;
    Button btnSignIn, forgot;
    TextView tvSignUp;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    //Testing

    //if @ is present
    public boolean validEmail(String email){

        boolean hasAtSign = email.indexOf("@")>-1;
        return hasAtSign;
    }

    // length of local part of email
    public int getLocalLength(String email){

        int at=email.indexOf("@");
        String localPart =email.substring(0,at);
        return localPart.length();
    }

    // password
    public boolean validPassword(String password){

        boolean pass=false;
        if(password.length()>=8){
             pass=true;
        }

        return pass;
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFirebaseAuth=FirebaseAuth.getInstance();
        emailId=findViewById(R.id.email);
        passwordId=findViewById(R.id.password);
        btnSignIn=findViewById(R.id.button);
        tvSignUp=findViewById(R.id.textView);
        forgot=findViewById(R.id.forgot);

        mAuthStateListener= new FirebaseAuth.AuthStateListener() {


            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if(mFirebaseUser != null){
                    Toast.makeText(LoginActivity.this,"You are logged In", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(i);
                }

            }
        };
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=emailId.getText().toString();
                String pwd=passwordId.getText().toString();
                if(email.isEmpty()){
                    emailId.setError("Please Enter Email id");
                    emailId.requestFocus();
                }
                else if(pwd.isEmpty()){
                    passwordId.setError("Please Enter Password");
                    passwordId.requestFocus();
                }
                else if(email.isEmpty() && pwd.isEmpty()){
                    Toast.makeText(LoginActivity.this,"Fields are Empty",Toast.LENGTH_SHORT).show();

                }
                else if(!(email.isEmpty() && pwd.isEmpty())){
                    mFirebaseAuth.signInWithEmailAndPassword(email,pwd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(LoginActivity.this,"Login Error, Please Login Again",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Intent intToHome= new Intent(LoginActivity.this,HomeActivity.class);
                                startActivity(intToHome);

                            }
                        }
                    });
                }
                else{
                    Toast.makeText(LoginActivity.this,"Error Occurred!!",Toast.LENGTH_SHORT).show();

                }
            }
        });
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intSignUp= new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intSignUp);
            }
        });

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final EditText resetMail =new EditText(v.getContext());

                AlertDialog.Builder resetDialog= new AlertDialog.Builder(v.getContext());

                resetDialog.setTitle("Reset Password?");
                resetDialog.setMessage("Enter Your Email To Receive Reset Link.");

                resetDialog.setView(resetMail);

                resetDialog.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // extracting email and sending link to that email

                        String mail = resetMail.getText().toString();

                        if (mail.isEmpty()) {
                            Toast.makeText(LoginActivity.this,"Please Enter Email Before Clicking Submit", Toast.LENGTH_LONG).show();

                        } else{
                            mFirebaseAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    Toast.makeText(LoginActivity.this, "Reset Link Sent To Your Email", Toast.LENGTH_LONG).show();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(LoginActivity.this, "Error!!! " + e.getMessage(), Toast.LENGTH_LONG).show();

                                }
                            });
                    }
                    }
                });

                resetDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Closing view

                    }
                });

                resetDialog.create().show();


            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }


}