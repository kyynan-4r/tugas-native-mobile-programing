package com.example.uas_risky.activity.pembelian;

public class Model {
    // TODO Edit This Scarlet
    public String id;
    public String pemasokid;
    public String tanggal;
    public Integer total;

    public Model(String pemasokid, String tanggal, Integer total) {
        this.pemasokid = pemasokid;
        this.tanggal = tanggal;
        this.total = total;
    }
}

