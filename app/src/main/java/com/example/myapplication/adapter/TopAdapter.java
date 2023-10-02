package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.Fragment.FrgTop10;
import com.example.myapplication.R;
import com.example.myapplication.model.Sach;
import com.example.myapplication.model.Top;

import java.util.ArrayList;
import java.util.List;

public class TopAdapter extends ArrayAdapter<Sach> {
    private Context context;
    FrgTop10 frgTop10;
    List<Sach> list;
    TextView tvSach, tvSoLuong;

    public TopAdapter(@NonNull Context context,  FrgTop10 frgTop10, List<Sach> list) {
        super(context,0, list);
        this.context = context;
        this.frgTop10 = frgTop10;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if(view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_top10, null);
        }

        final Sach sach = list.get(position);
        if(sach != null) {
            tvSach = view.findViewById(R.id.top_tvSach);
            tvSoLuong = view.findViewById(R.id.top_tvSoLuong);

            tvSach.setText("Sách: "+ sach.getTenSach());
            tvSoLuong.setText("Số lượng: "+ sach.getSoLuong());
        }


        return view;
    }
}
