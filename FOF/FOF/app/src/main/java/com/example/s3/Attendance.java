package com.example.s3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class Attendance extends AppCompatActivity {
    private static final int PERMISSION_READ_STATE = 1;
    private static final int CAMERA_REQUEST_CODE=101;

   CodeScanner mCodeScanner;
   CodeScannerView cdv;
    private TextView t1,t2;
    String di,id,r;
    private Button but;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

di= Settings.Secure.getString(this.getContentResolver(),Settings.Secure.ANDROID_ID);
        //t1=(TextView) findViewById(R.id.txt);
       // t1.setText(di.toString());

        //   StringBuilder sb =new StringBuilder();
//sb.append("IMEI: "+telephonyManager.getDeviceId() +"\n");
        //

        /*if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            // We do not have this permission. Let's ask the user
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, PERMISSION_READ_STATE);
        }

*/



         cdv = findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(this, cdv);
        t1=findViewById(R.id.res);

        t2=findViewById(R.id.res2);
       mCodeScanner.setDecodeCallback(new DecodeCallback() {
           @Override
           public void onDecoded(@NonNull final Result result) {
               runOnUiThread(new Runnable() {
                   @Override
                   public void run() {
                       t1.setText("Attendee -> "+result.getText());
                       t2.setText("Device id -> "+di.toString());
                       r=result.getText();
                   }
               });
           }
       });

cdv.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        mCodeScanner.startPreview();
    }
});



but=findViewById(R.id.but);
but.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        id=di.toString();
        t1.setText("Attendee -> "+r);
        t2.setText("Device id -> "+id);
        Intent i=new Intent(Attendance.this,NightOut.class);
        startActivity(i);
    }
});

    }
  /*  @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
       super.onRequestPermissionsResult(requestCode, permissions, grantResults);

            switch (requestCode) {
                case PERMISSION_READ_STATE: {
                    if (grantResults.length > 0
                            && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        // permission granted!
                        // you may now do the action that requires this permission
                        TelephonyManager telephonyManager;
                        telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
                        String deviceId = telephonyManager.getDeviceId();
                        String subscriberId = telephonyManager.getSubscriberId();

                        t2=(TextView)findViewById(R.id.txt2);
                        t1=(TextView) findViewById(R.id.txt);
                        t1.setText(deviceId);
                         t2.setText(subscriberId);
                    } else {
                        // permission denied
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, PERMISSION_READ_STATE);

                    }
                    return;
                }

            }
    }
*/

      @Override
  protected void onResume() {
        super.onResume();
        requestForCamera();
    }

    private void requestForCamera() {
        Dexter.withActivity(this).withPermission(Manifest.permission.CAMERA).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse response) {
                mCodeScanner.startPreview();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse response) {
Toast.makeText(Attendance.this,"Permission required",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
token.continuePermissionRequest();
            }
        }).check();
    }

}
