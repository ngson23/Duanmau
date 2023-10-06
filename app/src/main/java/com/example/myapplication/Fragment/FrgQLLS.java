package com.example.myapplication.Fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.RecyclerViewInterface;
import com.example.myapplication.adapter.LoaiSachAdapter;
import com.example.myapplication.adapter.SachAdapter;
import com.example.myapplication.database.LoaiSachDAO;
import com.example.myapplication.model.LoaiSach;
import com.example.myapplication.model.PhieuMuon;
import com.example.myapplication.model.Sach;
import com.example.myapplication.model.ThanhVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class FrgQLLS extends Fragment implements RecyclerViewInterface {
    LoaiSachDAO lsDAO;
    List<LoaiSach> list;
    LoaiSachAdapter adapter;
    LoaiSach loaiSach;
    EditText txtTenLoai;
    TextView title_loaiSach;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_qlls, container, false);
        RecyclerView rcvLs = view.findViewById(R.id.rcvLS);
        FloatingActionButton fltLS = view.findViewById(R.id.fltLS);

        lsDAO = new LoaiSachDAO(getContext());
        list = lsDAO.selectAll();
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        rcvLs.setLayoutManager(manager);
        adapter = new LoaiSachAdapter(getContext(), list, this);
        rcvLs.setAdapter(adapter);
        loaiSach = new LoaiSach();

        fltLS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(loaiSach, 0);
            }
        });

        return view;
    }

    public void openDialog(LoaiSach loaiSach, final int type) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.add_loaisach, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();
        txtTenLoai = view.findViewById(R.id.addTenLS);
        Button btnAdd = view.findViewById(R.id.ls_btnAdd);
        Button btnHuy = view.findViewById(R.id.ls_btnHuy);
        title_loaiSach = view.findViewById(R.id.title_ls);

        //kiểm tra type bằng 0(insert) hay 1(update)
        if(type != 0) {
            title_loaiSach.setText("Sửa Thành Viên");
            txtTenLoai.setText(loaiSach.getTenLoai());
        }

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loaiSach.setTenLoai(txtTenLoai.getText().toString());

                if(validate()>0) {
                    //type=0 (insert)
                    if(type==0) {
                        if (lsDAO.insert(loaiSach)) {
                            Toast.makeText(getActivity(), "Thêm Thành Công", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            list.clear();
                            list.addAll(lsDAO.selectAll());
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getActivity(), "Thêm Thất Bại", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        //type = -1(update)
                        if(lsDAO.update(loaiSach)) {
                            Toast.makeText(getActivity(), "Sửa thành công", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            list.clear();
                            list.addAll(lsDAO.selectAll());
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getActivity(), "Sửa thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }

                }


            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


    }

    public int validate() {
        int check = 1;
        if(txtTenLoai.getText().length()==0) {
            Toast.makeText(getContext(), "Bạn phải nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            check = -1;
        }
        return check;

    }

    @Override
    public void onItemClick(ThanhVien tv) {
    }

    @Override
    public void onLongItemClick(ThanhVien tv) {
    }

    @Override
    public void onLongItemClickS(Sach sach) {
    }

    @Override
    public void onLongItemClickS(LoaiSach loaiSach) {
        openDialog(loaiSach, 1);
    }

    @Override
    public void onLongItemClickS(PhieuMuon pm) {

    }
}
