package com.aisyah.models.nilai;

public class Model {
    // TODO EDIT
    public Integer id;
    public Integer nilaiSiswa;
    public String tanggal;
    public Integer mapelId;
    public Integer siswaId;
    public Integer guruId;

    public Model(Integer guruId, Integer siswaId, Integer mapelId, String tanggal, Integer nilaiSiswa) {
        this.guruId = guruId;
        this.siswaId = siswaId;
        this.mapelId = mapelId;
        this.tanggal = tanggal;
        this.nilaiSiswa = nilaiSiswa;
    }
    //
}
