package com.aisyah;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        List<MainModel> itemList = new ArrayList<>();

        // TODO EDIT
        itemList.add(new MainModel(
                new com.aisyah.models.siswa.ModelFragment(),
                R.drawable.baseline_account_circle_24,
                "Siswa",
                "siswa",
                "Ini adalah table Siswa"
        ));
        itemList.add(new MainModel(
                new com.aisyah.models.guru.ModelFragment(),
                R.drawable.baseline_supervised_user_circle_24,
                "Guru",
                "guru",
                "Ini adalah table Guru"
        ));

        itemList.add(new MainModel(
                new com.aisyah.models.kelas.ModelFragment(),
                R.drawable.baseline_book_24,
                "Kelas",
                "kelas",
                "Ini adalah table Kelas"
        ));

        itemList.add(new MainModel(
                new com.aisyah.models.mapel.ModelFragment(),
                R.drawable.baseline_library_books_24,
                "Mapel",
                "mapel",
                "Ini adalah table Mapel"
        ));

        itemList.add(new MainModel(
                new com.aisyah.models.nilai.ModelFragment(),
                R.drawable.baseline_delete_forever_24,
                "Nilai",
                "nilai",
                "Ini adalah table Nilai"
        ));

        itemList.add(new MainModel(
                new com.aisyah.models.pelanggaran.ModelFragment(),
                R.drawable.baseline_warning_amber_24,
                "Pelanggaran",
                "pelanggaran",
                "Ini adalah table Pelanggaran"
        ));
        //

        TextView tableTitle = view.findViewById(R.id.tableTitle);
        tableTitle.setText(String.format("Table List (%d):", itemList.size()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new MainAdapter(itemList, requireActivity()));
        return view;
    }
}
