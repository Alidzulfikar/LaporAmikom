package com.example.laporamikom.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.laporamikom.Data;
import com.example.laporamikom.KehilanganDetailActivity;
import com.example.laporamikom.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterLaporan extends RecyclerView.Adapter<AdapterLaporan.ViewHolder> {
    private List<Data> dataList;
    private Context context;

    public AdapterLaporan(Context context) {
        this.context = context;
        dataList = new ArrayList<>();
    }

    public void setData(List<Data> dataList){
        this.dataList.clear();
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_display, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindView(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvTitle, tvDescription;

        public ImageView getImgLaporan() {
            return imgLaporan;
        }

        private ImageView imgLaporan;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.lvTitle);
            tvDescription = itemView.findViewById(R.id.lvDesc);
            imgLaporan = itemView.findViewById(R.id.lvImg);
        }

        private void bindView(final Data data){
            tvTitle.setText(data.getTitle());
            tvDescription.setText(data.getCap());
            Picasso.get().load(data.getImageId()).into(imgLaporan);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, KehilanganDetailActivity.class);
                    intent.putExtra("LAPORAN", data);
                    context.startActivity(intent);
                }
            });
        }
    }
}
