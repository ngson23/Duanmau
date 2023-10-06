package com.example.myapplication.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.LoginActivity;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.database.ThuThuDAO;
import com.example.myapplication.model.ThuThu;


public class FrgChangePass extends Fragment {
    EditText txtPassOld, txtNewPass, txtRePass;
    Button btnSave, btnHuy;
    ThuThuDAO dao;

    public FrgChangePass() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_frg_change_pass, container, false);
        
        txtPassOld = view.findViewById(R.id.txtPassOld);
        txtNewPass = view.findViewById(R.id.txtNewPass);
        txtRePass = view.findViewById(R.id.txtRepass);
        btnSave = view.findViewById(R.id.change_btnLuu);
        btnHuy = view.findViewById(R.id.change_btnHuy);

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtPassOld.setText("");
                txtNewPass.setText("");
                txtRePass.setText("");
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getActivity().getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
                String user = pref.getString("MATT", "");
                String passold = txtPassOld.getText().toString();
                String newpass = txtNewPass.getText().toString();
                if(validate()>0 ){
                    dao = new ThuThuDAO(getActivity());
                    boolean check = dao.updatePass(user, passold, newpass);
                    if(check) {
                        Toast.makeText(getActivity(), "Cập nhật mật khẩu thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "Cập nhật mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        

        return view;
    }
    
    public  int validate() {
        int check = 1;
        if(txtPassOld.getText().length()==0 || txtNewPass.getText().length()==0 || txtRePass.getText().length()==0) {
            Toast.makeText(getContext(), "Bạn phải nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            check = -1;
        } else {
            SharedPreferences share = getActivity().getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
            String passold = share.getString("PASSWORD", "");
            String newpass = txtNewPass.getText().toString();
            String repass = txtRePass.getText().toString();
            
            if(!passold.equals(txtPassOld.getText().toString())) {
                Toast.makeText(getContext(), "Mật khẩu cũ sai", Toast.LENGTH_SHORT).show();
                check = -1;
            }
            if(!newpass.equals(repass)) {
                Toast.makeText(getContext(), "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                check = -1;
            }
        }
        return check;
    }
}