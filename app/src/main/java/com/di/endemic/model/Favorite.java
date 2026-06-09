package com.di.endemic.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorites")
public class Favorite {
    @PrimaryKey
    @NonNull
    private String id;
    private String nama;
    private String tipe;
    private String foto;
    private String asal;

    public Favorite() {}

    public Favorite(@NonNull String id, String nama, String tipe, String foto, String asal) {
        this.id = id;
        this.nama = nama;
        this.tipe = tipe;
        this.foto = foto;
        this.asal = asal;
    }

    @NonNull
    public String getId() { return id; }
    public void setId(@NonNull String id) { this.id = id; }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public String getTipe() { return tipe; }
    public void setTipe(String tipe) { this.tipe = tipe; }

    public String getFoto() { return foto; }
    public void setFoto(String foto) { this.foto = foto; }

    public String getAsal() { return asal; }
    public void setAsal(String asal) { this.asal = asal; }
}
