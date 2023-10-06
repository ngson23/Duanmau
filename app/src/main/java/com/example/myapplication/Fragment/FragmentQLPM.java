package com.example.myapplication.Fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.RecyclerViewInterface;
import com.example.myapplication.adapter.PMAdapter;
import com.example.myapplication.adapter.PhieuMuonAdapter;
import com.example.myapplication.database.PhieuMuonDAO;
import com.example.myapplication.database.SachDAO;
import com.example.myapplication.database.ThanhVienDAO;
import com.example.myapplication.model.LoaiSach;
import com.example.myapplication.model.PhieuMuon;
import com.example.myapplication.model.Sach;
import com.example.myapplication.model.ThanhVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class FragmentQLPM extends Fragment implements RecyclerViewInterface {

    PhieuMuonDAO pmDAO;
    List<PhieuMuon> list;
    PhieuMuonAdapter adapter;
    PhieuMuon pm;
    CheckBox chkPm;
    TextView title_pm;
    EditText txtNgay;


    public FragmentQLPM() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_qlpm, container, false);
        RecyclerView rcv = view.findViewById(R.id.rcvPm);
        FloatingActionButton fltBtn = view.findViewById(R.id.fltBtn);

        //data
         pmDAO = new PhieuMuonDAO(getContext());
         list = pmDAO.selectAll();
         LinearLayoutManager manager = new LinearLayoutManager(getContext());
         rcv.setLayoutManager(manager);
         adapter = new PhieuMuonAdapter(getContext(), list, this);
         rcv.setAdapter(adapter);
         pm = new PhieuMuon();


        fltBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(pm, 0);
            }
        });







        return view;
    }

    public void openDialog(PhieuMuon pm, final int type) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.add_pm, null);
        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

         title_pm = view.findViewById(R.id.title_pm);
         txtNgay = view.findViewById(R.id.txtNgay);
         chkPm = view.findViewById(R.id.chkTraS);
         Button btnAdd, btnHuy;
         btnAdd = view.findViewById(R.id.btnAddPM);
         btnHuy = view.findViewById(R.id.btnHuyPM);
        Spinner spnTV = view.findViewById(R.id.pm_spTV);
        Spinner spnS = view.findViewById(R.id.pm_spTenS);
        TextInputLayout ivDate = view.findViewById(R.id.ivDate);

        ivDate.setStartIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance(); //lấy ngày giờ hệ thống
                DatePickerDialog dpkdialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        txtNgay.setText(dayOfMonth+"-"+month+"-"+year);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                dpkdialog.show();

            }
        });


        getDataTV(spnTV);
        getDataSach(spnS);

        if (type != 0) {
            title_pm.setText("Sửa Phiếu Mượn");
            txtNgay.setText(String.valueOf(pm.getNgay()));
            int indexTV = 0;
            int indexSa = 0;
            int positionTV = -1;
            int positionSa = -1;
            for (HashMap<String, Object> item : getDataTV(spnTV)) {
                if ((int) item.get("matv") == pm.getMaTV()) {
                    positionTV = indexTV;

                }
                indexTV++;
            }
            spnTV.setSelection(positionTV);

            for (HashMap<String, Object> item : getDataSach(spnS)) {
                if ((int) item.get("masach") == pm.getMaSach()) {
                    positionSa = indexSa;

                }
                indexSa++;
            }
            spnS.setSelection(positionSa);

            if(pm.getTraSach()==1) {
                chkPm.setChecked(true);
            } else {
                chkPm.setChecked(false);
            }

        }


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //lấy mã tv
                HashMap<String, Object> hsTV = (HashMap<String, Object>) spnTV.getSelectedItem();
                int maTV = (int) hsTV.get("matv");
                //lấy mã sách
                HashMap<String, Object> hsS = (HashMap<String, Object>) spnS.getSelectedItem();
                int maS = (int) hsS.get("masach");
                int gia = (int)  hsS.get("giathue");

                //lấy mã thủ thư
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
                String matt = sharedPreferences.getString("MATT", "");

                Date calendar = Calendar.getInstance().getTime();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());


                String ngay = simpleDateFormat.format(calendar);


                pm.setMaTV(maTV);
                pm.setMaTT(matt);
                pm.setMaSach(maS);
                pm.setTienThue(gia);
                pm.setNgay(ngay);
                if(chkPm.isChecked()) {
                    pm.setTraSach(1);
                } else {
                    pm.setTraSach(0);
                }

                if(validate()>0) {
                    if(type==0) {
                        if (pmDAO.insert(pm)) {
                            Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                            alertDialog.dismiss();
                            list.clear();
                            list.addAll(pmDAO.selectAll());
                            adapter.notifyDataSetChanged();

                        } else {
                            Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                        }
                   }
                    else {
                        if (pmDAO.update(pm)) {
                            Toast.makeText(getContext(), "Sửa thành công", Toast.LENGTH_SHORT).show();
                            alertDialog.dismiss();
                            list.clear();
                            list.addAll(pmDAO.selectAll());
                            adapter.notifyDataSetChanged();

                        } else {
                            Toast.makeText(getContext(), "Sửa thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });




    }

    private List<HashMap<String, Object>> getDataTV(Spinner spnTV) {
        ThanhVienDAO tvDAO = new ThanhVienDAO(getContext());
        List<ThanhVien> list = tvDAO.selectAll();
        List<HashMap<String, Object>> listHM = new ArrayList<>();
        for (ThanhVien tv : list) {
            HashMap<String, Object> hs = new HashMap<>();
            hs.put("matv", tv.getMaTv());
            hs.put("tentv", tv.getName());
            listHM.add(hs);
        }

        SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(), listHM, android.R.layout.simple_list_item_1, new String[]{"tentv"}, new int[]{android.R.id.text1});
        spnTV.setAdapter(simpleAdapter);

        return listHM;
    }


    private List<HashMap<String, Object>> getDataSach(Spinner spnS) {
        SachDAO sDAO = new SachDAO(getContext());
        List<Sach> list = sDAO.selectAll();
        List<HashMap<String, Object>> listHM = new ArrayList<>();
        for (Sach s : list) {
            HashMap<String, Object> hs = new HashMap<>();
            hs.put("masach", s.getMaSach());
            hs.put("tensach", s.getTenSach());
            hs.put("giathue", s.getGiaThue());
            listHM.add(hs);
        }

        SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(), listHM, android.R.layout.simple_list_item_1, new String[]{"tensach"}, new int[]{android.R.id.text1});
        spnS.setAdapter(simpleAdapter);
        return listHM;
    }


    public int validate() {
        int check = 1;
        if (txtNgay.getText().length() == 0) {
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

    }

    @Override
    public void onLongItemClickS(PhieuMuon pm) {
        openDialog(pm, 1);
    }
}
