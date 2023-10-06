package com.example.myapplication;

import com.example.myapplication.model.LoaiSach;
import com.example.myapplication.model.PhieuMuon;
import com.example.myapplication.model.Sach;
import com.example.myapplication.model.ThanhVien;

public interface RecyclerViewInterface {
    void onItemClick(ThanhVien tv);
    void onLongItemClick(ThanhVien tv);
    void onLongItemClickS(Sach sach);
    void onLongItemClickS(LoaiSach loaiSach);
    void onLongItemClickS(PhieuMuon pm);

}
