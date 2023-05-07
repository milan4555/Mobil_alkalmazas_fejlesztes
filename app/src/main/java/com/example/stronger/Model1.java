package com.example.stronger;

public class Model1
{
    String Id, Gyakorlat, Rep, Set, Suly;

    public Model1() {
    }
    public Model1(String id, String gyakorlat, String rep, String set, String suly) {
        Id = id;
        Gyakorlat = gyakorlat;
        Rep = rep;
        Set = set;
        Suly = suly;
    }

    @Override
    public String toString() {
        return "Model1{" +
                "Id='" + Id + '\'' +
                ", Gyakorlat='" + Gyakorlat + '\'' +
                ", Rep='" + Rep + '\'' +
                ", Set='" + Set + '\'' +
                ", Suly='" + Suly + '\'' +
                '}';
    }

    public String getGyakorlat() {
        return Gyakorlat;
    }
    public void setGyakorlat(String gyakorlat) {
        Gyakorlat = gyakorlat;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }
    public String getRep() {
        return Rep;
    }

    public void setRep(String rep) {
        Rep = rep;
    }

    public String getSet() {
        return Set;
    }

    public void setSet(String set) {
        Set = set;
    }

    public String getSuly() {
        return Suly;
    }

    public void setSuly(String suly) {
        Suly = suly;
    }
}
