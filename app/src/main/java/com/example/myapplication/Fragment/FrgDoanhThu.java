package com.example.myapplication.Fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.database.ThongKeDAO;

import java.util.Calendar;


public class FrgDoanhThu extends Fragment {



    public FrgDoanhThu() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_frg_doanh_thu, container, false);
        EditText txtStart = view.findViewById(R.id.txtStart);
        EditText txtEnd = view.findViewById(R.id.txtEnd);
        Button btnTK = view.findViewById(R.id.btnTK);
        TextView tvKQ = view.findViewById(R.id.tvKetQua);

        Calendar calendar = Calendar.getInstance();
        txtStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String ngay = "";
                        String thang = "";
                        if(dayOfMonth<10) {
                            ngay = "0"+dayOfMonth;
                        } else {
                            ngay = String.valueOf(dayOfMonth);
                        }

                        if((month+1)<10) {
                            thang = "0"+(month+1);
                        } else {
                            thang = String.valueOf((month+1));
                        }

                        txtStart.setText(year+"/"+thang+"/"+ngay);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

                datePickerDialog.show();
            }
        });


        txtEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String ngay = "";
                        String thang = "";
                        if(dayOfMonth<10) {
                            ngay = "0"+dayOfMonth;
                        } else {
                            ngay = String.valueOf(dayOfMonth);
                        }

                        if((month+1)<10) {
                            thang = "0"+(month+1);
                        } else {
                            thang = String.valueOf((month+1));
                        }

                        txtEnd.setText(year+"/"+thang+"/"+ngay);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

                datePickerDialog.show();
            }
        });

        btnTK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThongKeDAO tkDAO = new ThongKeDAO(getContext());
                String ngayBatDau = txtStart.getText().toString();
                String ngayKetThuc = txtEnd.getText().toString();
                int doanhThu = tkDAO.getDoanhThu(ngayBatDau, ngayKetThuc);
                tvKQ.setText(doanhThu+"VNĐ");
            }
        });

        return view;
    }
}