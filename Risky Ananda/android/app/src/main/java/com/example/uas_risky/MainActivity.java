package com.example.uas_risky;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        List<Model> itemList = new ArrayList<>();

        // TODO Edit This Too
        itemList.add(new Model(
                new Intent(this, com.example.uas_risky.activity.pengguna.Activity.class),
                "Pengguna",
                "/api/rest/pengguna"
        ));
        itemList.add(new Model(
                new Intent(this, com.example.uas_risky.activity.produk.Activity.class),
                "Produk",
                "/api/rest/produk"
        ));
        itemList.add(new Model(
                new Intent(this, com.example.uas_risky.activity.pemasok.Activity.class),
                "Pemasok",
                "/api/rest/pemasok"
        ));
        itemList.add(new Model(
                new Intent(this, com.example.uas_risky.activity.detailpembelian.Activity.class),
                "Detail Pembelian",
                "/api/rest/detailpembelian"
        ));
        itemList.add(new Model(
                new Intent(this, com.example.uas_risky.activity.detailtransaksi.Activity.class),
                "Detail Transaksi",
                "/api/rest/detailtransaksi"
        ));
        itemList.add(new Model(
                new Intent(this, com.example.uas_risky.activity.kurir.Activity.class),
                "Kurir",
                "/api/rest/kurir"
        ));
        itemList.add(new Model(
                new Intent(this, com.example.uas_risky.activity.transaksi.Activity.class),
                "Transaksi",
                "/api/rest/transaksi"
        ));
        itemList.add(new Model(
                new Intent(this, com.example.uas_risky.activity.pembelian.Activity.class),
                "Pembelian",
                "/api/rest/pembelian"
        ));

        TextView tableTitle = findViewById(R.id.tableTitle);
        tableTitle.setText(String.format("Table List (%d):", itemList.size()));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MainAdapter(itemList, this));
    }
}
