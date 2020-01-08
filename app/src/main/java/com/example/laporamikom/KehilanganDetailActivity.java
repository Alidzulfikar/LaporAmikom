package com.example.laporamikom;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.laporamikom.adapter.AdapterLaporan;

import java.io.Serializable;


public class KehilanganDetailActivity extends AppCompatActivity {

    TextView tvTitle, tvLokasi, tvCap;
    ImageView ivHilang;
    Button btnWa;
    AdapterLaporan.ViewHolder view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kehilangan_detail);
        tvTitle = findViewById(R.id.tvTitle);
        tvLokasi = findViewById(R.id.tvLokasi);
        tvCap = findViewById(R.id.tvCap);
        ivHilang = findViewById(R.id.ivHilang);
        btnWa = findViewById(R.id.btnWa);
        Serializable data = getIntent().getSerializableExtra("LAPORAN");
        final Data dataFinal = (Data) data;
        tvTitle.setText(dataFinal.getTitle());
        ivHilang.setImageURI(Uri.parse(dataFinal.getImageId()));
        tvLokasi.setText(dataFinal.getLoc());
        tvCap.setText(dataFinal.getCap());
        btnWa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String wa = dataFinal.getPhone();

                String url = "https://api.whatsapp.com/send?phone=" + wa;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }


    private void showUpdateDialog(String desc){

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText editDecs = dialogView.findViewById(R.id.txtDesc);
        final Button btnUpdate = dialogView.findViewById(R.id.btnUpdate);

        dialogBuilder.setTitle("Update successful");

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

    }
}
