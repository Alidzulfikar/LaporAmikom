package com.example.laporamikom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class LaporRusakActivity extends AppCompatActivity {

    Button ivAddAttachment;
    ImageView ivAttachment;
    private static final int PIC_ID = 1;
    Button dashboard;
    Button profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_lapor_rusak);

        ivAddAttachment = findViewById(R.id.iv_add_attachment);
        ivAttachment = findViewById(R.id.iv_attachment);
        dashboard = findViewById(R.id.btnDash);
        profile = findViewById(R.id.btnProfile);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ivAddAttachment.setEnabled(false);
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
        }

        ivAddAttachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent camera_intent
                        = new Intent(MediaStore
                        .ACTION_IMAGE_CAPTURE);

                startActivityForResult(camera_intent, PIC_ID);
            }
        });

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
    }

    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PIC_ID) {
            Bitmap photo = (Bitmap) data.getExtras()
                    .get("data");

            ivAttachment.setVisibility(View.VISIBLE);
            ivAttachment.setImageBitmap(photo);
        }
    }
}
