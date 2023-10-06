package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Fragment.FragmentQLPM;
import com.example.myapplication.Fragment.FrgChangePass;
import com.example.myapplication.Fragment.FrgDoanhThu;
import com.example.myapplication.Fragment.FrgQLLS;
import com.example.myapplication.Fragment.FrgSach;
import com.example.myapplication.Fragment.FrgThanhVien;
import com.example.myapplication.Fragment.FrgTop10;
import com.example.myapplication.database.ThuThuDAO;
import com.example.myapplication.model.ThanhVien;
import com.example.myapplication.model.ThuThu;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ThuThuDAO ttDAO;
    RadioButton rdbAdmin, rdbTT;
    EditText txtUser;
    EditText txtTK;
    EditText txtPass;
    EditText txtRePass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        FrameLayout frameLayout = findViewById(R.id.framelayout);
        NavigationView nav = findViewById(R.id.nav);
        drawerLayout = findViewById(R.id.drawerLayout);
        View headrLayout = nav.getHeaderView(0);
        TextView tvHeader = headrLayout.findViewById(R.id.header_Tv);

        ttDAO = new ThuThuDAO(this);


        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24);

        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment frg;
                switch (item.getItemId()) {
                    case R.id.menuTaoTK:
                        openDialogTaoTK();

                    case R.id.menuPM:
                        frg = new FragmentQLPM();
                        break;
                    case R.id.menuLS:
                        frg = new FrgQLLS();
                        break;
                    case R.id.menuTV:
                        frg = new FrgThanhVien();
                        break;
                    case R.id.menuSach:
                        frg = new FrgSach();
                        break;

                    case R.id.menuTop10:
                        frg = new FrgTop10();
                        break;

                    case R.id.menuDT:
                        frg = new FrgDoanhThu();
                        break;

                    case R.id.menuDoiMK:
                        frg = new FrgChangePass();
                        break;

                    case R.id.menuExit:
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                    default:
                        frg = new FragmentQLPM();
                        break;
                }
                FragmentManager manager = getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.framelayout, frg).commit();

                drawerLayout.closeDrawer(GravityCompat.START);
                toolbar.setTitle(item.getTitle());
                return false;
            }
        });

        //hiển thị một số chức năng cho Admin
        SharedPreferences sharedPreferences = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        String loaiTk = sharedPreferences.getString("loaitk", "");
        if (!loaiTk.equals("Admin")) {
            Menu menu = nav.getMenu();
            menu.findItem(R.id.menuDT).setVisible(false);
            menu.findItem(R.id.menuTop10).setVisible(false);
            menu.findItem(R.id.menuTaoTK).setVisible(false);
        }
        String username = sharedPreferences.getString("username", "");
        tvHeader.setText("Xin chào, " + username);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }


    public void openDialogTaoTK() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.add_acount, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        txtUser = view.findViewById(R.id.txtUsername);
        txtTK = view.findViewById(R.id.txtTk);
        txtPass = view.findViewById(R.id.acount_txtPass);
        txtRePass = view.findViewById(R.id.acount_txtRepass);
        Button btnSave = view.findViewById(R.id.acount_btnSave);
        Button btnHuy = view.findViewById(R.id.acount_btnHuy);
        rdbAdmin = view.findViewById(R.id.rdbAdmin);
        rdbTT = view.findViewById(R.id.rdbTT);


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThuThu tt = new ThuThu();

                tt.setMaTT(txtTK.getText().toString());
                tt.setName(txtUser.getText().toString());
                tt.setMatKhau(txtPass.getText().toString());
                if (rdbAdmin.isChecked()) {
                    tt.setLoaiTk("Admin");
                }
                if (rdbTT.isChecked()) {
                    tt.setLoaiTk("ThuThu");
                }


                if (validate() > 0) {

                    if (ttDAO.insert(tt)) {
                        Toast.makeText(MainActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    } else {
                        Toast.makeText(MainActivity.this, "Thêm Thất Bại", Toast.LENGTH_SHORT).show();
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
        if (txtUser.getText().length() == 0 || txtTK.getText().length() == 0 || txtPass.getText().length() == 0 || txtRePass.getText().length() == 0) {
            Toast.makeText(this, "Bạn phải nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            check = -1;
        }

        if (!txtRePass.getText().toString().equals(txtPass.getText().toString())) {
            Toast.makeText(this, "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
            check = -1;
        }


        return check;

    }
}