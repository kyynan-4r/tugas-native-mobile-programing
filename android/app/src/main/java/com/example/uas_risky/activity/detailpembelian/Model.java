package com.example.uas_risky.activity.detailpembelian;

public class Model {
    // TODO Edit This Scarlet
    public String id;
    public String pembelianid;
    public String produkid;
    public String kuririd;
    public Integer hargasatuan;
    public Integer jumlah;
    public Integer subtotal;

    public Model(String pembelianid, String produkid, String kuririd, Integer jumlah, Integer hargasatuan, Integer subtotal) {
        this.pembelianid = pembelianid;
        this.produkid = produkid;
        this.kuririd = kuririd;
        this.jumlah = jumlah;
        this.hargasatuan = hargasatuan;
        this.subtotal = subtotal;
    }
}
