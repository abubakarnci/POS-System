package com.example.pos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.PermissionRequest;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;

import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Profile extends AppCompatActivity {

    ImageView uimage;
    TextInputEditText uname,phoneNo,address;
    Button btnupdate, btndelete;

    DatabaseReference dbreference;
    StorageReference storageReference;

    Uri filepath;
    Bitmap bitmap;
    String UserID="";

    NavigationView nav;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;

    TimePicker timePicker;

    FirebaseAuth auth;
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

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
                        Intent h= new Intent(Profile.this,HomeActivity.class);
                        startActivity(h);
                        break;

                    case R.id.menu_call:
                        Toast.makeText(getApplicationContext(),"Call Activity",Toast.LENGTH_LONG).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        Intent i= new Intent(Profile.this,Dialer.class);
                        startActivity(i);
                        break;

                    case R.id.menu_profile:
                        Toast.makeText(getApplicationContext(),"Profile Activity",Toast.LENGTH_LONG).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        Intent j= new Intent(Profile.this,Profile.class);
                        startActivity(j);
                        break;

                    case R.id.menu_alarm:
                        Toast.makeText(getApplicationContext(),"Alarm Activity",Toast.LENGTH_LONG).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        Intent k= new Intent(Profile.this,MainAlarm.class);
                        startActivity(k);
                        break;
                    case R.id.menu_bot:
                        Toast.makeText(getApplicationContext(),"ChatBot",Toast.LENGTH_LONG).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        Intent b= new Intent(Profile.this,ChatActivity.class);
                        startActivity(b);
                        break;

                    case R.id.menu_calculator:
                        Toast.makeText(getApplicationContext(),"Calculator",Toast.LENGTH_LONG).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        Intent c= new Intent(Profile.this,CalculatorActivity.class);
                        startActivity(c);
                        break;
                }

                return true;
            }
        });


        auth= FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        uimage=(ImageView)findViewById(R.id.uimage);
        uname= findViewById(R.id.uname);
        address= findViewById(R.id.address);
        phoneNo= findViewById(R.id.phoneNo);

        btnupdate=(Button)findViewById(R.id.btnupdate);
        btndelete=(Button)findViewById(R.id.btndelete);

        final FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        UserID=user.getUid();

        dbreference= FirebaseDatabase.getInstance().getReference().child("userprofile");
        storageReference= FirebaseStorage.getInstance().getReference();

        uimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Dexter.withContext(getApplicationContext())
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                Intent intent=new Intent();
                                intent.setType("image/*");
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(Intent.createChooser(intent,"Please Select File"),101);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(com.karumi.dexter.listener.PermissionRequest permissionRequest, PermissionToken permissionToken) {

                                permissionToken.continuePermissionRequest();

                            }

                        }).check();

            }
        });


        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatetofirebase();
            }
        });

        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(Profile.this);
                dialog.setTitle("Are you sure?");
                dialog.setMessage("Deleting this account will result in completely removing your account from the system and you won't be able to access the app");

                dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        final ProgressDialog pd = new ProgressDialog(Profile.this);
                        pd.setTitle("Deleting");
                        pd.show();
                        pd.setMessage("Email: " + user.getEmail());
                        user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                pd.dismiss();
                                if (task.isSuccessful()){
                                    Toast.makeText(Profile.this,"Account Deleted", Toast.LENGTH_LONG).show();

                                    Intent inToMain=new Intent(Profile.this, MainActivity.class);
                                    inToMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    inToMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(inToMain);

                                }
                                else{
                                    Toast.makeText(Profile.this,task.getException().getMessage(), Toast.LENGTH_LONG).show();

                                }
                            }
                        });

                    }
                });

                dialog.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {

                        dialog.dismiss();

                    }
                });

                AlertDialog alertDialog=dialog.create();
                alertDialog.show();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==101 && resultCode==RESULT_OK)
        {
            filepath=data.getData();
            try {
                InputStream inputStream=getContentResolver().openInputStream(filepath);
                bitmap= BitmapFactory.decodeStream(inputStream);
                uimage.setImageBitmap(bitmap);
            }catch (Exception ex)
            {
                Toast.makeText(getApplicationContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
            }
        }
    }

    public void updatetofirebase() {


        final StorageReference uploader = storageReference.child("profileimages/" + "img" + System.currentTimeMillis());

        if (filepath==null) {
            Toast.makeText(Profile.this, "Please select picture", Toast.LENGTH_LONG).show();

        }

        else if (filepath!=null){

            final ProgressDialog pd = new ProgressDialog(this);
            pd.setTitle("File Uploader");
            pd.show();

        uploader.putFile(filepath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                final Map<String, Object> map = new HashMap<>();
                                map.put("uimage", uri.toString());
                                map.put("uname", uname.getText().toString());
                                map.put("address", address.getText().toString());
                                map.put("phoneNo", phoneNo.getText().toString());

                                dbreference.child(UserID).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if (snapshot.exists())
                                            dbreference.child(UserID).updateChildren(map);
                                        else
                                            dbreference.child(UserID).setValue(map);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                    }
                                });

                                pd.dismiss();
                                Toast.makeText(getApplicationContext(), "Updated Successfully", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        float percent = (100 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                        pd.setMessage("Uploaded :" + (int) percent + "%");
                    }
                });

    }
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        UserID=user.getUid();
        dbreference.child(UserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    uname.setText(snapshot.child("uname").getValue().toString());
                    address.setText(snapshot.child("address").getValue().toString());
                    phoneNo.setText(snapshot.child("phoneNo").getValue().toString());
                    Glide.with(getApplicationContext()).load(snapshot.child("uimage").getValue().toString()).into(uimage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}