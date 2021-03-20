package com.example.medicationproject;

public class itemData {

    //Member Variable===========================================================================
    private String drug;
    private String date;
    private Boolean on_off;

    public itemData(String drug, String date, Boolean on_off) {
        this.drug = drug;
        this.date = date;
        this.on_off = on_off;
    }

    public String getDrug() {
        return drug;
    }

    public void setDrug(String drug) {
        this.drug = drug;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Boolean getOn_off() {
        return on_off;
    }

    public void setOn_off(Boolean on_off) {
        this.on_off = on_off;
    }
}
