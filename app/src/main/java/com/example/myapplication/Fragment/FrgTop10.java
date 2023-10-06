package com.example.myapplication.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.TopAdapter;
import com.example.myapplication.database.PhieuMuonDAO;
import com.example.myapplication.database.ThongKeDAO;
import com.example.myapplication.model.Sach;
import com.example.myapplication.model.Top;

import java.util.ArrayList;
import java.util.List;


public class FrgTop10 extends Fragment {
    ListView lv;
    List<Sach> list;
    TopAdapter adapter;



    public FrgTop10() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_frg_top10, container, false);
        lv = view.findViewById(R.id.lvTop);
        ThongKeDAO tkDAO = new ThongKeDAO(getContext());
        list = tkDAO.getTop10();
        adapter = new TopAdapter(getContext(), this, list);
        lv.setAdapter(adapter);

        return view;
    }
}