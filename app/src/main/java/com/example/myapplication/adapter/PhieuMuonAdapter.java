package com.example.myapplication.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
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
import com.example.myapplication.database.PhieuMuonDAO;
import com.example.myapplication.model.PhieuMuon;

import java.util.List;

public class PhieuMuonAdapter extends RecyclerView.Adapter<PhieuMuonAdapter.ViewHolder> {
    private Context context;
    private List<PhieuMuon> list;
    PhieuMuonDAO pmDAO;
    RecyclerViewInterface viewInterface;

    public PhieuMuonAdapter(Context context, List<PhieuMuon> list,  RecyclerViewInterface viewInterface) {
        this.context = context;
        this.list = list;
        this.viewInterface = viewInterface;
        pmDAO = new PhieuMuonDAO(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_qlpm, parent, false);

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

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvMaPM, tvTenTV, tvTenS, tvTien, tvTraS, tvNgay;
        ImageView ivXoa;
        PhieuMuon pm;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    viewInterface.onLongItemClickS(pm);
                    return false;
                }
            });

            tvMaPM = itemView.findViewById(R.id.tvMaPM);
            tvTenTV = itemView.findViewById(R.id.tvTentv);
            tvTenS = itemView.findViewById(R.id.pm_tvTenS);
            tvTien = itemView.findViewById(R.id.tvTien);
            tvTraS = itemView.findViewById(R.id.tvTraS);
            tvNgay = itemView.findViewById(R.id.tvNgay);
            ivXoa = itemView.findViewById(R.id.ivXoaPM);


        }

        public void bindData(PhieuMuon pm) {
            this.pm = pm;
            tvMaPM.setText("Mã Phiếu mượn: " + pm.getMaPM());
            tvTenTV.setText("Tên thành viên: " + pm.getTenTv());
            tvTenS.setText("Tên sách: " + pm.getTenSach());
            tvTien.setText("Giá tiền: " + pm.getTienThue());

            if (pm.getTraSach() == 1) {
                tvTraS.setTextColor(Color.BLUE);
                tvTraS.setText("Đã trả sách");

            } else {
                tvTraS.setTextColor(Color.RED);
                tvTraS.setText("Chưa trả sách");
            }
            tvNgay.setText("Ngày mượn: " + pm.getNgay());

            ivXoa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                    dialog.setTitle("Xóa Phiếu Mượn");
                    dialog.setMessage("Bạn có muốn xóa không");
                    dialog.setCancelable(true);

                    dialog.setNegativeButton(
                            "Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    if (pmDAO.delete(pm.getMaPM())) {
                                        Toast.makeText(context, "Xóa Thành Công", Toast.LENGTH_SHORT).show();
                                        list.clear();
                                        list.addAll(pmDAO.selectAll());
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
