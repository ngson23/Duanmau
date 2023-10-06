package com.example.myapplication.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.RecyclerViewInterface;
import com.example.myapplication.adapter.LoaiSachSpinnerAdapter;
import com.example.myapplication.adapter.SachAdapter;
import com.example.myapplication.database.LoaiSachDAO;
import com.example.myapplication.database.SachDAO;
import com.example.myapplication.model.LoaiSach;
import com.example.myapplication.model.PhieuMuon;
import com.example.myapplication.model.Sach;
import com.example.myapplication.model.ThanhVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class FrgSach extends Fragment implements RecyclerViewInterface {
    SachDAO sDAO;
    List<Sach> list;
    SachAdapter adapter;
    Sach sach;
    EditText txtGia, txtTenS, txtSoluongS;
    TextView title_sach;

    public FrgSach() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_frg_sach, container, false);
        RecyclerView rcvS = view.findViewById(R.id.rcvSach);
        FloatingActionButton flbSach = view.findViewById(R.id.flbSach);

        sDAO = new SachDAO(getContext());
        list = sDAO.selectAll();
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        rcvS.setLayoutManager(manager);
        adapter = new SachAdapter(getContext(), list, this);
        rcvS.setAdapter(adapter);

        flbSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(sach, 0);
            }
        });

        return view;
    }


    public void openDialog(Sach sach, final int type) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.add_sach, null);
        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        title_sach = view.findViewById(R.id.title_sach);
        txtTenS = view.findViewById(R.id.txtTenS);
        txtGia = view.findViewById(R.id.txtGia);
        txtSoluongS = view.findViewById(R.id.txtSoLuongSach);
        Button btnAddS, btnHuyS;
        btnAddS = view.findViewById(R.id.btnAddS);
        btnHuyS = view.findViewById(R.id.btnHuyS);
        Spinner spnLS = view.findViewById(R.id.spnLS);

        SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(), getDSLoaiSach(), android.R.layout.simple_list_item_1, new String[]{"tenloai"}, new int[]{android.R.id.text1});
        spnLS.setAdapter(simpleAdapter);


        if (type != 0) {
            title_sach.setText("Sửa Sách");
            txtTenS.setText(sach.getTenSach());
            txtGia.setText(String.valueOf(sach.getGiaThue()));
            txtSoluongS.setText(String.valueOf(sach.getSoLuong()));

            int index = 0;
            int position = -1;
            for (HashMap<String, Object> item : getDSLoaiSach()) {
                if ((int) item.get("maloai") == sach.getMaLoai()) {
                    position = index;

                }
                index++;
            }
            spnLS.setSelection(position);

        }


        btnAddS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                sach.setGiaThue(Integer.parseInt(txtGia.getText().toString()));
//                HashMap<String, Object> hm = (HashMap<String, Object>) spnLS.getSelectedItem();
//                sach.setMaLoai((Integer) hm.get("maloai"));
//                sach.setTenSach(txtTenS.getText().toString());

                String tenSach = txtTenS.getText().toString();
                int gia = Integer.parseInt(txtGia.getText().toString());
                HashMap<String, Object> hm = (HashMap<String, Object>) spnLS.getSelectedItem();
                int maloai = (int) hm.get("maloai");
                int soLuong = Integer.parseInt(txtSoluongS.getText().toString());

                if(validate()>0) {
                    if(type==0) {
                        if (sDAO.insert(tenSach, gia, maloai, soLuong)) {
                            Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                            alertDialog.dismiss();
                            list.clear();
                            list.addAll(sDAO.selectAll());
                            adapter.notifyDataSetChanged();

                        } else {
                            Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (sDAO.update(sach.getMaSach(), tenSach, gia, maloai, soLuong)) {
                            Toast.makeText(getContext(), "Sửa thành công", Toast.LENGTH_SHORT).show();
                            alertDialog.dismiss();
                            list.clear();
                            list.addAll(sDAO.selectAll());
                            adapter.notifyDataSetChanged();


                        } else {
                            Toast.makeText(getContext(), "Sửa thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                }




            }
        });

        btnHuyS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });


    }


    public List<HashMap<String, Object>> getDSLoaiSach() {
        LoaiSachDAO lsDAO = new LoaiSachDAO(getContext());
        List<LoaiSach> listLS = lsDAO.selectAll();
        List<HashMap<String, Object>> listHM = new ArrayList<>();

        for (LoaiSach loai : listLS) {
            HashMap<String, Object> hm = new HashMap<>();
            hm.put("maloai", loai.getMaLoai());
            hm.put("tenloai", loai.getTenLoai());
            listHM.add(hm);

        }


        return listHM;
    }

    public int validate() {
        int check = 1;
        if (txtTenS.getText().length() == 0 || txtGia.getText().length() == 0) {
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
        openDialog(sach, 1);
    }

    @Override
    public void onLongItemClickS(LoaiSach loaiSach) {
    }

    @Override
    public void onLongItemClickS(PhieuMuon pm) {

    }


//
}