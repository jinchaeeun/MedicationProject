package com.example.medicationproject;

import android.app.Application;

public class MyApplication extends Application {

    private int state;

    @Override
    public void onCreate() {
        //전역 변수 초기화
        state = 0;
        super.onCreate();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public void setState(int state){
        this.state = state;
    }

    public int getState(){
        return state;
    }
}