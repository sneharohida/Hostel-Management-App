package com.example.s3;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.io.File;
import java.io.IOException;

public class MainActivity extends Fragment {
    public static final int CAMERA_PERM_CODE = 101;
    public static final int CAMERA_REQUEST_CODE = 102;
    public static final int GALLERY_REQUEST_CODE = 105;
    TextInputLayout name, num, em, pa, ge, bg, na, fn, fno, mn, mno, lg, lgno, addr, bd;
    Button go;
    String currentPhotoPath;
    String selectedDate;
    ImageButton selectedImage;
    private OnDateSetListener mDateSetListener;
    public static final int REQUEST_CODE = 11;

    final String warden = "9834950004";



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.activity_main, container, false);
        num = root.findViewById(R.id.no);
        name = root.findViewById(R.id.name);
        em = root.findViewById(R.id.email);
        pa = root.findViewById(R.id.password);
        ge = root.findViewById(R.id.Gender);
        bg = root.findViewById(R.id.Blood);
        na = root.findViewById(R.id.nationality);
        fn = root.findViewById(R.id.Fname);
        fno = root.findViewById(R.id.Fno);
        mn = root.findViewById(R.id.Mname);
        mno = root.findViewById(R.id.MNum);
        lg = root.findViewById(R.id.guardian);
        lgno = root.findViewById(R.id.gnum);
        addr = root.findViewById(R.id.Paddress);
        bd = root.findViewById(R.id.Bd);

        final FragmentManager fm = ((AppCompatActivity) getActivity()).getSupportFragmentManager();

        go = root.findViewById(R.id.signup);
        selectedImage = (ImageButton) root.findViewById(R.id.image_button_android);

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String snumber = num.getEditText().getText().toString();
                String sname = name.getEditText().getText().toString();
                SmsManager mySmsManager = SmsManager.getDefault();
                String Selfmessage = "Welcome to the Hostel " + sname + " .Thanks for registering.";
                String Wardenmessage = "" + sname + " has registered for the hostel sucessfully.";

                mySmsManager.sendTextMessage(snumber, null, Selfmessage, null, null);
                mySmsManager.sendTextMessage(warden, null, Wardenmessage, null, null);

                Intent i=new Intent(getActivity(),NoticeBoard.class);
                startActivity(i);


            }
        });


        selectedImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog dialog = new AlertDialog.Builder(getActivity()).setTitle("Choose Option")
                        .setPositiveButton("Gallery", null)
                        .setNegativeButton("Camera", null)
                        .show();

                Button camera = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                camera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        askCameraPermissions();

                    }
                });

                Button gallery = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                gallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(gallery, GALLERY_REQUEST_CODE);
                        //Toast.makeText(getActivity(), "Gallery", Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });

        return root;


    }

    private void askCameraPermissions() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, CAMERA_PERM_CODE);
        } else {
            dispatchTakePictureIntent();
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CAMERA_PERM_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dispatchTakePictureIntent();
            } else {
                Toast.makeText(getActivity(), "Camera Permission is Required to Use camera.", Toast.LENGTH_SHORT).show();

            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Bitmap b = (Bitmap) data.getExtras().get("data");
                selectedImage.setImageBitmap(b);
                File f = new File(currentPhotoPath);
                // selectedImage.setImageURI(Uri.fromFile(f));
                Log.d("tag", "ABsolute Url of Image is " + Uri.fromFile(f));
                Log.d("tag", "ABsolute Url of Image is ");


                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri contentUri = Uri.fromFile(f);
                mediaScanIntent.setData(contentUri);

                getActivity().sendBroadcast(mediaScanIntent);

            }
        }


        if (requestCode == GALLERY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Uri contentUri = data.getData();
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String imageFileName = "JPEG_" + timeStamp + "." + getFileExt(contentUri);
                Log.d("tag", "onActivityResult: Gallery Image Uri:  " + imageFileName);
                selectedImage.setImageURI(contentUri);
            }

        }


    }

    private String getFileExt(Uri contentUri) {
        ContentResolver c = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(c.getType(contentUri));
    }


    public void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
        // if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
        // Create the File where the photo should go

        File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        File imageFile = new File(pictureDirectory, "kkk");
        // Uri pictureUri = Uri.fromFile(imageFile);
        Uri pictureUri = FileProvider.getUriForFile(getActivity(),
                "com.example.android.fileprovider",
                imageFile);
        currentPhotoPath = imageFile.getAbsolutePath();
        // takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, pictureUri);
        startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
         /*   try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getActivity(),
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
            }
*/

        //  }

    }


     /*   private File createImageFile() throws IOException {
            // Create an image file name
                File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            Toast.makeText(getActivity(), "error1", Toast.LENGTH_SHORT).show();

            File image = File.createTempFile(
                    "Mahima",
                    ".jpg",
                    storageDir
            );

            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = image.getAbsolutePath();
            return image;
        }*/

/*
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
      //  if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getActivity(),
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
            }
       // }
    }
*/



}