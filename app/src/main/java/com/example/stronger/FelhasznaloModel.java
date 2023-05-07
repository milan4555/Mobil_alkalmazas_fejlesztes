package com.example.stronger;

public class FelhasznaloModel {
    String felhasznalonev, email, jelszo, eletkor, suly, magassag;

    public FelhasznaloModel(String felhasznalonev, String email, String jelszo, String eletkor, String suly, String magassag) {
        this.felhasznalonev = felhasznalonev;
        this.email = email;
        this.jelszo = jelszo;
        this.eletkor = eletkor;
        this.suly = suly;
        this.magassag = magassag;
    }

    public String getFelhasznalonev() {
        return felhasznalonev;
    }

    public void setFelhasznalonev(String felhasznalonev) {
        this.felhasznalonev = felhasznalonev;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJelszo() {
        return jelszo;
    }

    public void setJelszo(String jelszo) {
        this.jelszo = jelszo;
    }

    public String getEletkor() {
        return eletkor;
    }

    public void setEletkor(String eletkor) {
        this.eletkor = eletkor;
    }

    public String getSuly() {
        return suly;
    }

    public void setSuly(String suly) {
        this.suly = suly;
    }

    public String getMagassag() {
        return magassag;
    }

    public void setMagassag(String magassag) {
        this.magassag = magassag;
    }
}
