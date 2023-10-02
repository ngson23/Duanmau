package com.example.myapplication.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.PhieuMuon;

import java.util.ArrayList;
import java.util.List;

public class PMAdapter extends RecyclerView.Adapter<PMAdapter.ViewHolder> {

    private List<PhieuMuon> list;
    private Context context;

    public PMAdapter(List<PhieuMuon> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_qlpm, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtMaPM.setText("Mã Phiếu: "+ list.get(position).getMaPM());
        holder.txtTenTV.setText("Tên: "+ list.get(position).getTenTv());
        holder.txtTenS.setText("Sách: "+ list.get(position).getTenSach());
        holder.txtTien.setText("Giá: "+ list.get(position).getTienThue());

        String trangthai = "";
        if(list.get(position).getTraSach() == 1) {
            trangthai = "Đã trả sách";
        } else {
            trangthai = "Chưa trả sách";
        }
        holder.txtTraS.setText("Trạng Thái: "+ trangthai);
        holder.txtNgay.setText("Ngày: "+ list.get(position).getNgay());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtMaPM, txtTenTV, txtTenS, txtTien, txtTraS, txtNgay;
        ImageView ivXoa;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMaPM = itemView.findViewById(R.id.tvMaPM);
            txtTenTV = itemView.findViewById(R.id.tvTentv);
            txtTenS = itemView.findViewById(R.id.pm_tvTenS);
            txtTien = itemView.findViewById(R.id.tvTien);
            txtTraS = itemView.findViewById(R.id.tvTraS);
            txtNgay = itemView.findViewById(R.id.tvNgay);
        }
    }
}
