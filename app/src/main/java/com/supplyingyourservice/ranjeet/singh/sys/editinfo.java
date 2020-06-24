package com.supplyingyourservice.ranjeet.singh.sys;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.supplyingyourservice.ranjeet.singh.sys.Home.Fragment.DashboardFragment;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;

public class editinfo extends AppCompatActivity{



    private Uri mCropImageUri;
    private FirebaseAuth mAuth;
    private DatabaseReference myref;
    private CircleImageView maddphoto;
    private EditText detail;
    private TextView email;
    private EditText name;
    private EditText username;
    private EditText phone;
    private FirebaseUser user;
    private TextView save;
    private RelativeLayout rel;
    private DatabaseReference shopref;
    private String usernam;
    private EditText address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editprofile);
         mAuth = FirebaseAuth.getInstance();

maddphoto=(CircleImageView)findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.profile_photo);
        maddphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    onSelectImageClick(view);

            }
        });
         myref = FirebaseDatabase.getInstance().getReference().child("customers").child(mAuth.getCurrentUser().getUid());
        rel=(RelativeLayout) findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.relLayout7);
        rel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent productdetailsintent = new Intent (editinfo.this,shop_map.class);
                startActivity(productdetailsintent);

            }
        });
        shopref = FirebaseDatabase.getInstance().getReference();
        FirebaseAuth auth = FirebaseAuth.getInstance();
         user = auth.getCurrentUser();
        detail= (EditText)findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.details);
        email= (TextView) findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.email);
        name=(EditText) findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.display_name);
        save=(TextView) findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.save);
        username=(EditText) findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.username);
        address=(EditText) findViewById(R.id.address);
        phone=(EditText) findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.phoneNumber);
        assert user != null;
        email.setText(user.getEmail());
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                shopref.child("customer_locations").child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            String detai= detail.getText().toString();
                            String nam=name.getText().toString();
                            final String use=username.getText().toString();
                            String phon=phone.getText().toString();
                            String add=address.getText().toString();
                            Map<String, Object> childUpdates = new HashMap<>();
                            childUpdates.put("detail",detai);
                            childUpdates.put("name",nam);

                            childUpdates.put("phone",phon);
                            childUpdates.put("address",add);

                            childUpdates.put("email",user.getEmail());



                            myref.updateChildren(childUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                   checkIfUsernameExists(use);
                                }
                            });

                        }else{
                             save.setBackgroundColor(Color.parseColor("#76818a"));
                            Toast.makeText(editinfo.this, "Save location First", Toast.LENGTH_SHORT).show();

                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });




            }
        });


        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               if(dataSnapshot.hasChild("customer_thumb")){
                  String dp= dataSnapshot.child("customer_thumb").getValue().toString();
                   Picasso.with(editinfo.this).load(dp).into(maddphoto);

               }else {
                   Picasso.with(editinfo.this).load(user.getPhotoUrl()).into(maddphoto);
               }
               if(dataSnapshot.hasChild("name")){
                   String nam=dataSnapshot.child("name").getValue().toString();
                   name.setText(nam);
                   }else {
                   name.setText(user.getDisplayName()); }
                if(dataSnapshot.hasChild("detail")){
                    String phon= dataSnapshot.child("detail").getValue().toString();
                    detail.setText(phon);

               }
                if(dataSnapshot.hasChild("username")){
                     usernam= dataSnapshot.child("username").getValue().toString();
                    username.setText(usernam);

                }
                if(dataSnapshot.hasChild("phone")){
                    String phon= dataSnapshot.child("phone").getValue().toString();
                    phone.setText(phon);

                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });









    }
    private void checkIfUsernameExists(final String username) {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference
                .child("customers")
                .orderByChild("username")
                .equalTo(username);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(!dataSnapshot.exists()){
                    if(usernam!=null){
                    if(!usernam.equalsIgnoreCase(username)) {
                    myref.child("username").setValue(username).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Intent productdetailsintent = new Intent(editinfo.this, DashboardFragment.class);
                            startActivity(productdetailsintent);
                            Toast.makeText(editinfo.this, "Saved", Toast.LENGTH_SHORT).show();
                        }
                    });


                for(DataSnapshot singleSnapshot: dataSnapshot.getChildren()){
                    if (singleSnapshot.exists()){

                        Toast.makeText(editinfo.this, "That username already exists.", Toast.LENGTH_SHORT).show();



                    }
                }}else {

                        Intent productdetailsintent = new Intent(editinfo.this, DashboardFragment.class);
                        startActivity(productdetailsintent);
                        Toast.makeText(editinfo.this, "Saved", Toast.LENGTH_SHORT).show();
                    }
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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
        CropImage.activity(imageUri).setFixAspectRatio(true).setAspectRatio(1,1).setCropShape(CropImageView.CropShape.OVAL)
                .setBorderLineColor(Color.RED)

                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .start(this);
    }

    private void savephoto(final Uri uri) {


        File thumb_filePath = new File(uri.getPath());
        Bitmap thumb_bitmap = null;
        byte[] thumb_byte = new byte[0];


            try {
                thumb_bitmap = new Compressor(this)



                        .setQuality(40)

                        .compressToBitmap(thumb_filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();


            thumb_bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

            thumb_byte = baos.toByteArray();





        savefiletopath(thumb_byte);


    }

    private void savefiletopath(final byte[] thumb_byte ) {
        StorageReference mstorage = FirebaseStorage.getInstance().getReference();


        final StorageReference thumb_filepath = mstorage.child("customers").child(mAuth.getCurrentUser().getUid()).child("customer_thumb" + ".jpg");

        final DatabaseReference myref = FirebaseDatabase.getInstance().getReference().child("customers").child(mAuth.getCurrentUser().getUid());






                        UploadTask uploadTask1 = thumb_filepath.putBytes(thumb_byte);

                        uploadTask1.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                final String thumb_downloadUrl = task.getResult().getDownloadUrl().toString();



                                            myref.child("customer_thumb").setValue(thumb_downloadUrl).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {

                                                        Toast.makeText(editinfo.this, "Profile Pic Updated", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });


                            }
                        });


                    }















    }
