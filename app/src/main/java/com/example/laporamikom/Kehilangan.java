package com.example.laporamikom;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Kehilangan extends Fragment {

    private View HilangView;
    private RecyclerView listItem;

    private DatabaseReference Reff;
    private FirebaseAuth mAuth;
    private String currentId;
    private String uid;

    public Kehilangan() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        HilangView = inflater.inflate(R.layout.fragment_kehilangan, container, false);

        listItem = HilangView.findViewById(R.id.rvHilang);
        listItem.setLayoutManager(new LinearLayoutManager(getContext()));
        Reff = FirebaseDatabase.getInstance().getReference("Lapor");

        return HilangView;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<ListItem>()
                .setQuery(Reff, ListItem.class)
                .build();

        FirebaseRecyclerAdapter<ListItem, ListViewHolder> adapter = new FirebaseRecyclerAdapter<ListItem, ListViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final ListViewHolder holder, int position, @NonNull ListItem model) {
                final List<Data> data = new ArrayList<>();
                Reff.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        data.clear();
                        for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                            Data lapor = postSnapshot.getValue(Data.class);
                            data.add(lapor);

                            // here you can access to name property like university.name
                            String judul = snapshot.child("title").getValue().toString();
                            String isi = snapshot.child("cap").getValue().toString();

                            holder.title.setText(judul);
                            holder.desc.setText(isi);

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @NonNull
            @Override
            public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_display, parent, false);
                ListViewHolder vh = new ListViewHolder(view);
                return vh;
            }
        };

        listItem.setAdapter(adapter);
        adapter.startListening();
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder{

        TextView title, desc;
        ImageView img;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.lvTitle);
            desc = itemView.findViewById(R.id.lvDesc);
            img = itemView.findViewById(R.id.lvImg);
        }
    }
}
