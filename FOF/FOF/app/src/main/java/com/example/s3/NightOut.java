package com.example.s3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputLayout;

public class NightOut extends AppCompatActivity {

    Button go;
    TextInputLayout name;
    ImageView nxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_night_out);
name=findViewById(R.id.name);
        nxt=findViewById(R.id.r);

        go = findViewById(R.id.Submit);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SmsManager mySmsManager = SmsManager.getDefault();
                String sname = name.getEditText().getText().toString();

                String Selfmessage = "Your child  " + sname + " is requesting for Night Out. Requesting for a call to grant permission";
                //String Wardenmessage = "" + sname + " has registered for the hostel sucessfully.";

                String snumber="9834950004";
                mySmsManager.sendTextMessage(snumber, null, Selfmessage, null, null);

            }
        });
        nxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(NightOut.this,ComplaintForm.class);
                startActivity(i);
            }
        });

    }
}