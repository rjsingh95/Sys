package com.supplyingyourservice.ranjeet.singh.sys.books;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import id.zelory.compressor.Compressor;
import com.supplyingyourservice.ranjeet.singh.sys.R;


public class addabook extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    private Spinner spinbook;
    private String item;
    byte[] thumb_byte = new byte[0];


    private Uri mCropImageUri;
    private DatabaseReference myref;
    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addabook);
        spinbook=(Spinner)findViewById(R.id.spin);
        spinbook.setOnItemSelectedListener(this);

        final List<String> categories = new ArrayList<String>();
        categories.add("Select a Category");
        categories.add("Entrance Exams");
        categories.add("Academics");
        categories.add("Literature & Fiction");

        categories.add("Indian Writing");
        
        categories.add("Children");
        categories.add("Business");
        categories.add("Self Help");
        categories.add("Non-Fiction");


         myref = FirebaseDatabase.getInstance().getReference().child("books");
        key= myref.push().getKey();



        ImageView maddphoto = (ImageView) findViewById(R.id.bookphoto);
        maddphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onSelectImageClick(view);

            }
        });
        final EditText title=(EditText)findViewById(R.id.booktitle);
        final EditText author=(EditText)findViewById(R.id.author);

        final TextView save=(TextView)findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save.setEnabled(false);
                if(!item.equals("Select a Category")) {
                    if (thumb_byte != null) {
                        savefiletopath(thumb_byte);
                        String t = title.getText().toString();
                        String a = author.getText().toString();
                        Map chatAddMap = new HashMap();
                        chatAddMap.put("title", t);
                        chatAddMap.put("author", a);
                        chatAddMap.put("book_id", key);
                        chatAddMap.put("category",item);







                        myref.child(key).updateChildren(chatAddMap);

                    }

                }else{
                    Toast.makeText(addabook.this, "Select Category", Toast.LENGTH_SHORT).show();
                }





            }
        });








        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinbook.setAdapter(dataAdapter);


    }

    public void onSelectImageClick(View view) {
        CropImage.startPickImageActivity(this);
    }


    @Override
    @SuppressLint("NewApi")
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // handle result of pick image chooser
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri imageUri = CropImage.getPickImageResultUri(this, data);

            // For API >= 23 we need to check specifically that we have permissions to read external storage.
            if (CropImage.isReadExternalStoragePermissionsRequired(this, imageUri)) {
                // request permissions and handle the result in onRequestPermissionsResult()
                mCropImageUri = imageUri;
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);

            } else {
                // no permissions required or already grunted, can start crop image activity
                startCropImageActivity(imageUri);
            }
        }

        // handle result of CropImageActivity
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            final CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {


                Uri photoUri = result.getUri();

                savephoto(photoUri);


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }


    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (mCropImageUri != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // required permissions granted, start crop image activity
            startCropImageActivity(mCropImageUri);

        } else {
            Toast.makeText(this, "Cancelling, required permissions are not granted", Toast.LENGTH_LONG).show();
        }
    }
    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri).setFixAspectRatio(true).setAspectRatio(3,4)

                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .start(this);
    }

    private void savephoto(final Uri uri) {


        File thumb_filePath = new File(uri.getPath());
        Bitmap thumb_bitmap = null;


        try {
            thumb_bitmap = new Compressor(this)



                    .setQuality(60)

                    .compressToBitmap(thumb_filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();


        thumb_bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);

        thumb_byte = baos.toByteArray();








    }

    private void savefiletopath(final byte[] thumb_byte ) {
        StorageReference mstorage = FirebaseStorage.getInstance().getReference();




        final StorageReference thumb_filepath = mstorage.child("book_image").child(key + ".jpg");


        UploadTask uploadTask1 = thumb_filepath.putBytes(thumb_byte);

        uploadTask1.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                final String thumb_downloadUrl = task.getResult().getDownloadUrl().toString();




           myref.child(key).child("book_image").setValue(thumb_downloadUrl).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(addabook.this, "Profile Pic Updated", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                });


            }
        });


    }



    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        item = adapterView.getItemAtPosition(i).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
