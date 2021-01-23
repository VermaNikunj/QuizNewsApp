package com.nikunjpc.quiznewsapp.TipOfTheDay;

public class TipBackupModelClass {

    int id;
    String tipBackupLine;

    public TipBackupModelClass(int id, String tipBackupLine) {
        this.id = id;
        this.tipBackupLine = tipBackupLine;
    }

    public TipBackupModelClass(String tipBackupLine) {
        this.tipBackupLine = tipBackupLine;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipBackupLine() {
        return tipBackupLine;
    }

    public void setTipBackupLine(String tipBackupLine) {
        this.tipBackupLine = tipBackupLine;
    }
}
