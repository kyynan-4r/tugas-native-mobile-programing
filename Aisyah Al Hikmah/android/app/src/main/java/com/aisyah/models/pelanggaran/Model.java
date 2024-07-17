package com.aisyah.models.pelanggaran;

public class Model {
    // TODO EDIT
    public Integer id;
    public String detailPelanggaran;
    public String poinPelanggaran;
    public String tanggal;
    public Integer siswaId;
    public Integer kelasId;

    public Model(String detailPelanggaran, String poinPelanggaran, String tanggal, Integer siswaId, Integer kelasId) {
        this.detailPelanggaran = detailPelanggaran;
        this.poinPelanggaran = poinPelanggaran;
        this.tanggal = tanggal;
        this.siswaId = siswaId;
        this.kelasId = kelasId;
    }

//
}
