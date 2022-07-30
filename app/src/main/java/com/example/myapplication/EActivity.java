package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EActivity extends AppCompatActivity {
    EditText to,subject,message;
    Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eactivity);

        to = findViewById(R.id.To);
        subject = findViewById(R.id.subject);
        message = findViewById(R.id.message);
        send = findViewById(R.id.Sendemail);
//intent email data
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("mailto:" + to.getText().toString())
                        );
                intent.putExtra(Intent.EXTRA_SUBJECT,subject.getText().toString());
                intent.putExtra(Intent.EXTRA_TEXT,message.getText().toString());
                startActivity(intent);
            }
        });
    }
}