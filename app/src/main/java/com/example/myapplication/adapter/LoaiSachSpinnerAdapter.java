package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;
import com.example.myapplication.model.LoaiSach;

import java.util.ArrayList;
import java.util.List;

public class LoaiSachSpinnerAdapter extends ArrayAdapter<LoaiSach> {
    Context context;
    List<LoaiSach> lists;
    TextView tvMaLS, tvTenLS;

    public LoaiSachSpinnerAdapter(@NonNull Context context,  List<LoaiSach> lists) {
        super(context, 0, lists);
        this.context = context;
        this.lists = lists;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if(view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.loaisach_item_spinner, null);
        }

        final LoaiSach item = lists.get(position);
        if(item != null) {
            tvMaLS = view.findViewById(R.id.sp_tvMaLS);
            tvTenLS = view.findViewById(R.id.sp_tvTenLS);
            tvMaLS.setText(item.getMaLoai() + "");
            tvTenLS.setText(item.getTenLoai());
        }
        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if(view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.loaisach_item_spinner, null);
        }
        final LoaiSach item = lists.get(position);
        if(item != null) {
            tvMaLS = view.findViewById(R.id.sp_tvMaLS);
            tvTenLS = view.findViewById(R.id.sp_tvTenLS);
            tvMaLS.setText(item.getMaLoai() + "");
            tvTenLS.setText(item.getTenLoai());
        }


        return view;
    }
}
