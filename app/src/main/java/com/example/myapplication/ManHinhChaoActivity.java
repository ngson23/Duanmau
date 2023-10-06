package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class ManHinhChaoActivity extends AppCompatActivity {
    TextView tvMasv;
    EditText txtMasv;
    Button btnMasv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_chao);
        tvMasv = findViewById(R.id.tvMasv);
        txtMasv = findViewById(R.id.txt_Masv);
        btnMasv = findViewById(R.id.btnMasv);

        btnMasv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String masv = txtMasv.getText().toString();
                if(masv.equals("ph20319")) {
                    Toast.makeText(ManHinhChaoActivity.this, "OK", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ManHinhChaoActivity.this, MainActivity.class));
                } else {
                    tvMasv.setText("Vui lòng nhập lại mã sinh viên");
                    tvMasv.setBackgroundColor(Color.RED);
                }
            }
        });

//        ImageView ivLogo = findViewById(R.id.iv_logo);
//
//        Glide.with(this).load(R.mipmap.logo_gif).into(ivLogo);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                startActivity(new Intent(ManHinhChaoActivity.this, LoginActivity.class));
//            }
//        }, 3000);
    }
}