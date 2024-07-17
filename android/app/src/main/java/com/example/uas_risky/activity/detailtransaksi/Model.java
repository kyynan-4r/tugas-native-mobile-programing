package com.example.uas_risky.activity.detailtransaksi;

public class Model {
    // TODO Edit This Scarlet
    public String id;
    public String transaksiid;
    public String produkid;
    public Integer hargasatuan;
    public Integer jumlah;
    public Integer subTotal;

    public Model(String transaksiid, String produkid, Integer jumlah, Integer hargasatuan, Integer subTotal) {
        this.transaksiid = transaksiid;
        this.produkid = produkid;
        this.jumlah = jumlah;
        this.hargasatuan = hargasatuan;
        this.subTotal = subTotal;
    }
}
