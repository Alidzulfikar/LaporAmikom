package com.example.laporamikom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class LaporRusakActivity extends AppCompatActivity {

    Button ivAddAttachment;
    ImageView ivAttachment;
    private static final int PIC_ID = 1;
    Button dashboard, profile, btnSend;

    EditText title, loc, cap, phone;
    Data data;
    DatabaseReference reff;
    StorageReference mStorageRef;

    public Uri imgUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_lapor_rusak);

        dashboard = findViewById(R.id.btnDash);
        profile = findViewById(R.id.btnProfile);

        dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LaporRusakActivity.this, DashboardActivity.class));
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LaporRusakActivity.this, ProfileActivity.class));
            }
        });

        title = findViewById(R.id.txtTitle);
        loc = findViewById(R.id.txtLocation);
        cap = findViewById(R.id.txtCaption);
        phone = findViewById(R.id.txtPhone);
        btnSend = findViewById(R.id.btnSend);
        ivAddAttachment = findViewById(R.id.iv_add_attachment);
        ivAttachment = findViewById(R.id.iv_attachment);
        data = new Data();
        reff = FirebaseDatabase.getInstance().getReference().child("Lapor").child("Rusak");
        mStorageRef = FirebaseStorage.getInstance().getReference("Images").child("Rusak");

        ivAddAttachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileChooser();
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FileUploader();
                Toast.makeText(LaporRusakActivity.this, "Send successful", Toast.LENGTH_SHORT).show();
            }
        });



    }

    private void FileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imgUri = data.getData();
            ivAttachment.setVisibility(View.VISIBLE);
            ivAttachment.setImageURI(imgUri);
        }
    }

    private String getExtension(Uri uri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }

    private void FileUploader() {
        final String imageId;
        imageId = System.currentTimeMillis()+"."+getExtension(imgUri);

        data.setTitle(title.getText().toString().trim());
        data.setLoc(loc.getText().toString().trim());
        data.setCap(cap.getText().toString().trim());
        data.setPhone(phone.getText().toString());


        StorageReference Ref = mStorageRef.child(imageId );
        Ref.putFile(imgUri)
                .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if(!task.isSuccessful())  {
                            // TODO tampilkan error
                            return;
                        }

                        // get image Url
                        task.getResult().getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String imageUrl = uri.toString();
                                data.setImageId(imageUrl);
                                reff.push().setValue(data);


                            }
                        });
                    }
                });

    }
}
