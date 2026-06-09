package com.di.endemic.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "endemic")
public class Endemic {
    @PrimaryKey
    @NonNull
    @SerializedName("id")
    private String id;

    @SerializedName("tipe")
    private String tipe;

    @SerializedName("nama")
    private String nama;

    @SerializedName("nama_latin")
    private String namaLatin;

    @SerializedName("famili")
    private String famili;

    @SerializedName("genus")
    private String genus;

    @SerializedName("deskripsi")
    private String deskripsi;

    @SerializedName("asal")
    private String asal;

    @SerializedName("sebaran")
    private String sebaran;

    @SerializedName("foto")
    private String foto;

    @SerializedName("sumber_foto")
    private String sumberFoto;

    @SerializedName("vidio")
    private String vidio;

    @SerializedName("sumber_vidio")
    private String sumberVidio;

    @SerializedName("status")
    private String status;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTipe() { return tipe; }
    public void setTipe(String tipe) { this.tipe = tipe; }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public String getNamaLatin() { return namaLatin; }
    public void setNamaLatin(String namaLatin) { this.namaLatin = namaLatin; }

    public String getFamili() { return famili; }
    public void setFamili(String famili) { this.famili = famili; }

    public String getGenus() { return genus; }
    public void setGenus(String genus) { this.genus = genus; }

    public String getDeskripsi() { return deskripsi; }
    public void setDeskripsi(String deskripsi) { this.deskripsi = deskripsi; }

    public String getAsal() { return asal; }
    public void setAsal(String asal) { this.asal = asal; }

    public String getSebaran() { return sebaran; }
    public void setSebaran(String sebaran) { this.sebaran = sebaran; }

    public String getFoto() { return foto; }
    public void setFoto(String foto) { this.foto = foto; }

    public String getSumberFoto() { return sumberFoto; }
    public void setSumberFoto(String sumberFoto) { this.sumberFoto = sumberFoto; }

    public String getVidio() { return vidio; }
    public void setVidio(String vidio) { this.vidio = vidio; }

    public String getSumberVidio() { return sumberVidio; }
    public void setSumberVidio(String sumberVidio) { this.sumberVidio = sumberVidio; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
