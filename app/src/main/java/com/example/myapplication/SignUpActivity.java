package com.example.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {

    ImageView imageView;
    ImageButton button,buttonBack;
    Button createAccount;
    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;
    Database1 DB;
    EditText fullname, username, password, comfirmpassword, phonenumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        imageView = findViewById(R.id.imageView);
        button  =findViewById(R.id.imageButton);
        buttonBack = findViewById(R.id.btnBack);
        fullname = findViewById(R.id.fullnamesignup);
        username = findViewById(R.id.usernamesignup);
        password = findViewById(R.id.passwordsignup);
        password.setTransformationMethod(PasswordTransformationMethod.getInstance());
        comfirmpassword = findViewById(R.id.comfirmsignup);
        comfirmpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        phonenumber = findViewById(R.id.phonenumbersignup);

        createAccount = findViewById(R.id.createaccount);

        DB = new Database1(this);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);

            }
        });
        //insert user data

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String full = fullname.getText().toString();
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String repass = comfirmpassword.getText().toString();
                String phone = phonenumber.getText().toString();

                if(user.equals("")||pass.equals("")||repass.equals("")||phone.equals("")||full.equals(""))
                    Toast.makeText(SignUpActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                else {
                    if(pass.equals(repass)){
                        Boolean checkuser = DB.checkusername(user);
                        if(checkuser == false){
                            Boolean insert = DB.insertData(user,pass,full,phone);

                            if(insert == true){
                                Toast.makeText(SignUpActivity.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(SignUpActivity.this,"Registered failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Toast.makeText(SignUpActivity.this,"user already exists!please sign in", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(SignUpActivity.this,"Password not matching", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                    if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_DENIED){

                        String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};

                        requestPermissions(permission,PERMISSION_CODE);
                    }
                    else {

                        pickImage();
                }


            }
                else {
                    pickImage();

                }
        };


    })
;}

    //chose image

    private void pickImage() {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case PERMISSION_CODE:{
                if(grantResults.length > 0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    pickImage();
                }
                else {
                    Toast.makeText(this, "Permisson denied....!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            imageView.setImageURI(data.getData());

        }
    }
}