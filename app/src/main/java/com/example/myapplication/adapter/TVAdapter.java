package com.example.myapplication.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.RecyclerViewInterface;
import com.example.myapplication.database.ThanhVienDAO;
import com.example.myapplication.model.ThanhVien;

import java.util.List;

public class TVAdapter extends RecyclerView.Adapter<TVAdapter.ViewHolder>{
   
    private List<ThanhVien> list;
    private Context context;
    ThanhVienDAO tvdao;
    private RecyclerViewInterface listener;

    public TVAdapter(List<ThanhVien> list, Context context, RecyclerViewInterface listener) {
        this.list = list;
        this.context = context;
        this.listener = listener;
        tvdao = new ThanhVienDAO(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tv, null);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.bindData(list.get(position));


        
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tvName, tvDate, tvGioiTinh;
        public ImageView ivxoa ;
        public ThanhVien tv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.onLongItemClick(tv);
                    return false;
                }
            });
            
            tvName = itemView.findViewById(R.id.tvName);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvGioiTinh = itemView.findViewById(R.id.tvGioiTinh);
            ivxoa = itemView.findViewById(R.id.iv_xoa);
        }
        private void bindData(ThanhVien tv) {
            this.tv = tv;
            tvName.setText("Tên thành viên: "+ tv.getName());
            tvDate.setText("Năm sinh: "+ tv.getNamSinh());
            if(tv.getGioitinh() == 0) {
                tvGioiTinh.setText("Giới tính: Nam");
            } else {
                tvGioiTinh.setText("Giới tính: Nữ");
                tvGioiTinh.setTextColor(Color.RED);

            }

            ivxoa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                    dialog.setTitle("Xóa thành viên");
                    dialog.setMessage("Bạn có muốn xóa không");
                    dialog.setCancelable(true);

                    dialog.setNegativeButton(
                            "Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if(tvdao.delete(tv.getMaTv())) {
                                        Toast.makeText(context, "Xóa Thành Công", Toast.LENGTH_SHORT).show();
                                        list.clear();
                                        list.addAll(tvdao.selectAll());
                                        notifyDataSetChanged();
                                    } else {
                                        Toast.makeText(context, "Xóa Thất Bại", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                    );

                    dialog.setPositiveButton(
                            "No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            }
                    );

                    AlertDialog alertDialog = dialog.create();
                    alertDialog.show();


                }
            });

        }

    }


}
