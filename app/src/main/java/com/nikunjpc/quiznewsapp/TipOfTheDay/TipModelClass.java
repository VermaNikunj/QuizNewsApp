package com.nikunjpc.quiznewsapp.TipOfTheDay;

public class TipModelClass {
    int id;
    String tipDate;
    String tipLine;

    public TipModelClass(int id, String tipDate, String tipLine) {
        this.id = id;
        this.tipDate = tipDate;
        this.tipLine = tipLine;
    }

    public TipModelClass(String tipDate, String tipLine) {
        this.tipDate = tipDate;
        this.tipLine = tipLine;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipDate() {
        return tipDate;
    }

    public void setTipDate(String tipDate) {
        this.tipDate = tipDate;
    }

    public String getTipLine() {
        return tipLine;
    }

    public void setTipLine(String tipLine) {
        this.tipLine = tipLine;
    }
}
