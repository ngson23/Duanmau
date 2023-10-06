package com.example.myapplication.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.RecyclerViewInterface;
import com.example.myapplication.adapter.TVAdapter;
import com.example.myapplication.database.ThanhVienDAO;
import com.example.myapplication.model.LoaiSach;
import com.example.myapplication.model.PhieuMuon;
import com.example.myapplication.model.Sach;
import com.example.myapplication.model.ThanhVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class FrgThanhVien extends Fragment implements RecyclerViewInterface {
    RecyclerView rcvTV;
    TVAdapter adapter;
    List<ThanhVien> list = new ArrayList<>();
    ThanhVienDAO tvdao;
    FloatingActionButton fltAdd;
    ThanhVien tv;
    EditText txtName, txtDate;
    TextView title_tv;


    public FrgThanhVien() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_frg_thanh_vien, container, false);
        rcvTV = view.findViewById(R.id.rcvTV);
        tvdao = new ThanhVienDAO(getActivity());
        list = tvdao.selectAll();
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        rcvTV.setLayoutManager(manager);
        adapter = new TVAdapter(list, getActivity(), this);
        rcvTV.setAdapter(adapter);
        fltAdd = view.findViewById(R.id.fltTV);
        tv = new ThanhVien();

        fltAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openDialogThem(tv, 0);
            }
        });


        return view;
    }

    public void openDialogThem(ThanhVien tv, final int type) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.add_tv, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();
        txtName = view.findViewById(R.id.addname);
        txtDate = view.findViewById(R.id.addDate);
        title_tv = view.findViewById(R.id.title_tv);
        Button btnAdd = view.findViewById(R.id.btnAdd);
        Button btnHuy = view.findViewById(R.id.btnHuy);
        RadioButton rdbNam, rdbNu;
        rdbNam = view.findViewById(R.id.rdbNam);
        rdbNu = view.findViewById(R.id.rdbNu);

        //kiểm tra type bằng 0(insert) hay 1(update)
        if (type != 0) {
            title_tv.setText("Sửa Thành Viên");
            txtName.setText(tv.getName());
            txtDate.setText(tv.getNamSinh());
            if(tv.getGioitinh() == 0) {
                rdbNam.setChecked(true);
            } else {
                rdbNu.setChecked(true);
            }
        }

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tv.setName(txtName.getText().toString());
                tv.setNamSinh(txtDate.getText().toString());
                if (rdbNam.isChecked()) {
                    tv.setGioitinh(0);
                }
                if (rdbNu.isChecked()) {
                    tv.setGioitinh(1);
                }

                if (validate() > 0) {
                    //type=0 (insert)
                    if (type == 0) {
                        if (tvdao.insert(tv)) {
                            Toast.makeText(getActivity(), "Thêm Thành Công", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            list.clear();
                            list.addAll(tvdao.selectAll());
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getActivity(), "Thêm Thất Bại", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        //type = -1(update)
                        if (tvdao.update(tv)) {
                            Toast.makeText(getActivity(), "Sửa thành công", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            list.clear();
                            list.addAll(tvdao.selectAll());
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
        if (txtName.getText().length() == 0 || txtDate.getText().length() == 0) {
            Toast.makeText(getContext(), "Bạn phải nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            check = -1;
        }
        return check;

    }


    @Override
    public void onItemClick(ThanhVien tv) {

    }

    public void showToast(String mess) {
        Toast.makeText(getActivity(), mess, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLongItemClick(ThanhVien tv) {
        openDialogThem(tv, 1);

    }

    @Override
    public void onLongItemClickS(Sach sach) {
    }

    @Override
    public void onLongItemClickS(LoaiSach loaiSach) {
    }

    @Override
    public void onLongItemClickS(PhieuMuon pm) {

    }


}