package com.example.myapplication.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.RecyclerViewInterface;
import com.example.myapplication.database.LoaiSachDAO;
import com.example.myapplication.model.LoaiSach;

import java.util.List;

public class LoaiSachAdapter extends RecyclerView.Adapter<LoaiSachAdapter.ViewHolder> {

    private Context context;
    private List<LoaiSach> list;
    LoaiSachDAO lsDAO;
    RecyclerViewInterface viewInterface;

    public LoaiSachAdapter(Context context, List<LoaiSach> list,  RecyclerViewInterface viewInterface) {
        this.context = context;
        this.list = list;
        this.viewInterface = viewInterface;
        lsDAO = new LoaiSachDAO(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_loaisach, null);
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

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvMaLS, tvTenLS;
        ImageView ivXoaLS;
        public LoaiSach loaiSach;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    viewInterface.onLongItemClickS(loaiSach);
                    return false;
                }
            });

            tvMaLS = itemView.findViewById(R.id.tvMaLS);
            tvTenLS = itemView.findViewById(R.id.tvTenLS);
            ivXoaLS = itemView.findViewById(R.id.ivXoa_LoaiSach);
        }

        public void bindData(LoaiSach loaiSach) {
            this.loaiSach = loaiSach;
            tvMaLS.setText("Mã loại sách: "+ loaiSach.getMaLoai());
            tvTenLS.setText("Tên loại sách: "+ loaiSach.getTenLoai());

            ivXoaLS.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                    dialog.setTitle("Xóa Loại Sách");
                    dialog.setMessage("Bạn có muốn xóa không");
                    dialog.setCancelable(true);

                    dialog.setNegativeButton(
                            "Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    int check = lsDAO.delete(loaiSach.getMaLoai());
                                    switch (check) {
                                        case 1:
                                            Toast.makeText(context, "Xóa Thành Công", Toast.LENGTH_SHORT).show();
                                            list.clear();
                                            list.addAll(lsDAO.selectAll());
                                            notifyDataSetChanged();
                                            break;
                                        case 0:
                                            Toast.makeText(context, "Xóa Thất Bại", Toast.LENGTH_SHORT).show();
                                            break;
                                        case -1:
                                            Toast.makeText(context, "Không thể xóa sách này vì sách có trong phiếu mượn", Toast.LENGTH_SHORT).show();
                                            break;
                                        default:
                                            break;

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
