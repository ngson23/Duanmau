package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.database.ThuThuDAO;
import com.example.myapplication.model.ThuThu;

public class LoginActivity extends AppCompatActivity {
    EditText txtUser, txtPass;
    CheckBox chkLuuMK;
    Button btnlogin, btnHuy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("ĐĂNG NHẬP");
        txtUser = findViewById(R.id.txtUser);
        txtPass = findViewById(R.id.txtPass);
        btnlogin  = findViewById(R.id.btnLogin);
        btnHuy = findViewById(R.id.login_btnHuy);
        chkLuuMK = findViewById(R.id.chkLuuMK);

        ThuThuDAO ttDAO = new ThuThuDAO(this);

        SharedPreferences share = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        String user = share.getString("USERNAME", "");
        String pass = share.getString("PASSWORD", "");
        Boolean rem = share.getBoolean("REMEMBER", false);

        txtUser.setText(user);
        txtPass.setText(pass);
        chkLuuMK.setChecked(rem);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = txtUser.getText().toString();
                String pass = txtPass.getText().toString();
                if(user.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Tên đăng Nhập và mật khẩu không được để trống", Toast.LENGTH_SHORT).show();
                } else if(ttDAO.checkLogin(user, pass)) {
                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    remember(user, pass, chkLuuMK.isChecked());
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();

                } else {
                    Toast.makeText(LoginActivity.this, "Tên đăng Nhập và mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtUser.setText("");
                txtPass.setText("");
            }
        });
    }

    public void remember(String u, String p, boolean status) {
        SharedPreferences share = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        SharedPreferences.Editor editor = share.edit();
        if(!status) {
//            xóa tình trạng lưu trữ trước đó
            editor.clear();
        }  else {
//            lưu dữ liêu
            editor.putString("MATT", u);
            editor.putString("USERNAME", u);
            editor.putString("PASSWORD", p);
            editor.putBoolean("REMEMBER", status);
        }
        editor.commit();
    }
}