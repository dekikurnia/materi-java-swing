/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dekikurnia.rentalmobil.model;

/**
 *
 * @author deki kurnia
 */
public class Pelanggan {
    
    private String noKtp;
    private String namaPelanggan;    
    private String alamatPelanggan;
    private String telpPelanggan;

    /**
     * @return the noKtp
     */
    public String getNoKtp() {
        return noKtp;
    }

    /**
     * @param noKtp the noKtp to set
     */
    public void setNoKtp(String noKtp) {
        this.noKtp = noKtp;
    }

    /**
     * @return the namaPelanggan
     */
    public String getNamaPelanggan() {
        return namaPelanggan;
    }

    /**
     * @param namaPelanggan the namaPelanggan to set
     */
    public void setNamaPelanggan(String namaPelanggan) {
        this.namaPelanggan = namaPelanggan;
    }

    /**
     * @return the alamatPelanggan
     */
    public String getAlamatPelanggan() {
        return alamatPelanggan;
    }

    /**
     * @param alamatPelanggan the alamatPelanggan to set
     */
    public void setAlamatPelanggan(String alamatPelanggan) {
        this.alamatPelanggan = alamatPelanggan;
    }

    /**
     * @return the telpPelanggan
     */
    public String getTelpPelanggan() {
        return telpPelanggan;
    }

    /**
     * @param telpPelanggan the telpPelanggan to set
     */
    public void setTelpPelanggan(String telpPelanggan) {
        this.telpPelanggan = telpPelanggan;
    }

}
