package com.example.laporamikom;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.io.Serializable;

public class LaporanDetail extends AppCompatActivity {
    private TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laporan_detail);
        tvTitle = findViewById(R.id.tvTitle);
        Serializable data = getIntent().getSerializableExtra("LAPORAN");
        Data dataFInal = (Data) data;
        tvTitle.setText(dataFInal.getTitle());
    }
}
