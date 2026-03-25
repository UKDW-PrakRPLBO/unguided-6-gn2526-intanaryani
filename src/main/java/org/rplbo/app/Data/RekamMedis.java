package org.rplbo.app.Data;

public class RekamMedis {
    // Attribute
    private String Namapasien;
    private String diagnosis;
    private String tanggal;
    private Integer id;
    private String Namadokter;


    // Constructor

    public RekamMedis(Integer id, String Namapasien, String diagnosis, String tanggal, String namaDokter) {
        this.id = id;
        this.Namapasien = Namapasien;
        this.diagnosis = diagnosis;
        this.tanggal = tanggal;
        this.Namadokter = namaDokter;
    }


    // Getter

    public String getNamapasien() {
        return Namapasien;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public String getTanggal() {
        return tanggal;
    }

    public Integer getId() {
        return id;
    }

    public String getNamadokter() {
        return Namadokter;
    }
}