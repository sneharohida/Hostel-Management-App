package com.example.s3;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class ComplaintForm extends AppCompatActivity {

    TextInputLayout name;
    TextInputLayout num;
    TextInputLayout ot;
    String spi;
    String f="Anonymous";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_form);

        name = findViewById(R.id.fn);
        num = findViewById(R.id.rn);
        ot = findViewById(R.id.ot);

        name.setEnabled(false);
        name.setFocusable(false);
        num.setEnabled(false);
        num.setFocusable(false);
        ot.setEnabled(false);
        ot.setFocusable(false);

        Spinner sp=(Spinner)findViewById(R.id.spin);
        ArrayAdapter<String> ma=new ArrayAdapter<String>(ComplaintForm.this, android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.names));
        ma.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(ma);
       // spi=sp.getSelectedItem().toString();

sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
       // Toast.makeText(ComplaintForm.this,adapterView.getItemAtPosition(i).toString(),Toast.LENGTH_SHORT).show();
if(adapterView.getItemAtPosition(i).toString().equals("Other"))
{
    ot.setEnabled(true);
    ot.setFocusable(true);
}
else{
    ot.setEnabled(false);
    ot.setFocusable(false);
}
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        ot.setEnabled(false);
        ot.setFocusable(false);
    }
});

    }

    public void nammm(View view) {
        //Toast.makeText(Attendance.this,"Permission required",Toast.LENGTH_SHORT).show();
        name.setHint("Full Name");
        num.setHint("Room Number");
        name.setEnabled(true);
        name.setFocusable(true);
        num.setEnabled(true);
        num.setFocusable(true);
    }

    public void anon(View view) {
        name.setEnabled(false);
        name.setFocusable(false);
        num.setEnabled(false);
        num.setFocusable(false);
        name.setHint(f);
        num.setHint("NA");
    }
}