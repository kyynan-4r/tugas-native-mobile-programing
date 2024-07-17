package com.example.uas_risky.activity.transaksi;

public class Model {
    // TODO Edit This Scarlet
    public String id;
    public String penggunaid;
    public String tanggal;
    public Integer total;

    public Model(String penggunaid, String tanggal, Integer total) {
        this.penggunaid = penggunaid;
        this.tanggal = tanggal;
        this.total = total;
    }
}
