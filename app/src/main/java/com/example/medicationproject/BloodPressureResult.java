package com.example.medicationproject;

//데이터 넘길 BloodPre
public class BloodPressureResult {
    //Member variable ----------------------------
    private String date;
    private String spin;
    private String bPTime;
    private String bPETxt;

    public BloodPressureResult(String date, String spin, String bPTime, String bPETxt) {
        this.date = date;
        this.spin = spin;
        this.bPTime = bPTime;
        this.bPETxt = bPETxt;
    }

    @Override
    public String toString() {
        return "BloodPressureResult{" +
                "date=" + date +
                ", spin='" + spin + '\'' +
                ", bPTime='" + bPTime + '\'' +
                ", bPETxt='" + bPETxt + '\'' +
                '}';
    }
}
