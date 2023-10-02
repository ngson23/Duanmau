package com.example.myapplication.adapter;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Fragment.FrgSach;
import com.example.myapplication.R;
import com.example.myapplication.RecyclerViewInterface;
import com.example.myapplication.database.LoaiSachDAO;
import com.example.myapplication.database.SachDAO;
import com.example.myapplication.model.LoaiSach;
import com.example.myapplication.model.Sach;
import com.example.myapplication.model.ThanhVien;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SachAdapter extends RecyclerView.Adapter<SachAdapter.ViewHolder> {
    private Context context;
    private List<Sach> list;
    private RecyclerViewInterface viewInterface;
    SachDAO sachDAO;


    public SachAdapter(Context context, List<Sach> list, RecyclerViewInterface viewInterface) {
        this.context = context;
        this.list = list;
        this.viewInterface = viewInterface;
        sachDAO = new SachDAO(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_sach, null);
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
        TextView tvMaS, tvTenS, tvGia, tvTenLS, tvSoLuong;
        ImageView ivXoaS;
        Sach sach;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    viewInterface.onLongItemClickS(sach);
                    return false;
                }
            });




            tvMaS = itemView.findViewById(R.id.tvMaS);
            tvTenS = itemView.findViewById(R.id.tvSach);
            tvGia = itemView.findViewById(R.id.tvGiaS);
            tvTenLS = itemView.findViewById(R.id.tvLoaiS);
            tvSoLuong = itemView.findViewById(R.id.tvSoLuongSach);
            ivXoaS = itemView.findViewById(R.id.ivXoa_sach);
        }

        private void bindData(Sach sach) {
            this.sach = sach;
            tvMaS.setText("Mã sách: "+ sach.getMaSach());
            tvTenS.setText("Tên sách: "+ sach.getTenSach());
            tvGia.setText("Giá thuê: "+ sach.getGiaThue());
            tvTenLS.setText("Tên loại: "+ sach.getTenLoai());
            tvSoLuong.setText("Số lượng: " + sach.getSoLuong());

            ivXoaS.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                    dialog.setTitle("Xóa Sách");
                    dialog.setMessage("Bạn có muốn xóa không");
                    dialog.setCancelable(true);

                    dialog.setNegativeButton(
                            "Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    int check = sachDAO.delete(sach.getMaSach());
                                    switch (check) {
                                        case 1:
                                            Toast.makeText(context, "Xóa Thành Công", Toast.LENGTH_SHORT).show();
                                            list.clear();
                                            list.addAll(sachDAO.selectAll());
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
