package com.example.laporamikom;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.laporamikom.adapter.AdapterLaporan;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Kehilangan extends Fragment {
    private RecyclerView rvLaporan;

    private DatabaseReference databaseReference;
    private FirebaseAuth auth;
    private AdapterLaporan adapterLaporan;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_kehilangan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapterLaporan = new AdapterLaporan(getActivity());
        databaseReference = FirebaseDatabase.getInstance().getReference("Lapor").child("Hilang");
        rvLaporan = view.findViewById(R.id.rvHilang);
        rvLaporan.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvLaporan.setAdapter(adapterLaporan);
    }

    @Override
    public void onStart() {
        super.onStart();
        listenData();
    }

    private void listenData(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Data> tempData = new ArrayList<>();
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    Data dataLaporan = data.getValue(Data.class);
                    tempData.add(dataLaporan);
                }

                adapterLaporan.setData(tempData);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getCurrentUser(){
        // TODO disni
    }
}
