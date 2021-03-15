package com.example.medicationproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LayoutAlarmList extends AppCompatActivity {

    //Member Variable ==========================================================================
    private final String TAG = "LayoutAlarmList";
    private final  boolean D = true;

    private TextView resdayTXT;     //날짜 출력 위함----------------------

    private Switch alarmSW;         //알람 스위치------------------------

    private TextView reseveryTXT;   //"매일" 의미함을 위한 텍스트-----------
    private TextView resintervalTXT;    //특정 일 표시 위한 텍스트----------
    private TextView rescountTXT;   // 며칠 분인지 표시 위한 텍스트 ---------

    private TextView restimeTXT;    //먹을 시간 띄워주는 텍스트--------------
    private TextView resnumTXT;      //먹을 약 개수 띄워주는 텍스트----------

    private TextView resdrugTXT;    //약 종류 띄워주기 위한 텍스트------------

    private ListView resLST;        //결과들 모으기 위한 리스트!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_alarm);


        //초기화 메서드
        init();

        //디버깅 확인 용
        if(D)
            Log.i(TAG, "LayoutAlarmList OK");

    }


    //Memeber Method - Custome ====================================================
    //App 초기화 메소드
    private void init() {
        if(D)
            Log.i(TAG, "init OK");


        resdayTXT = findViewById(R.id.resdayTXT);     //날짜 출력 위함----------------------
        resdrugTXT = findViewById(R.id.resdrugTXT);   //약 종류 띄워주기 위한 텍스트------------

        alarmSW = findViewById(R.id.alarmSW);         //알람 스위치------------------------

        reseveryTXT = findViewById(R.id.reseveryTXT);  //"매일" 의미함을 위한 텍스트-----------
        resintervalTXT = findViewById(R.id.resintervalTXT);   //특정 일 표시 위한 텍스트----------
        rescountTXT = findViewById(R.id.rescountTXT);   // 며칠 분인지 표시 위한 텍스트 ---------

        restimeTXT = findViewById(R.id.restimeTXT);    //먹을 시간 띄워주는 텍스트--------------
        resnumTXT = findViewById(R.id.resnumTXT);     //먹을 약 개수 띄워주는 텍스트----------

        resLST = findViewById(R.id.resLST);       //결과들 모으기 위한 리스트!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!




        //전 페이지로 부터 받기 위한 변수들==============================================================
        //현재 Acitivity 실행한 Intent 객체 가져오기
        Intent rxintent = this.getIntent(); //받은거 읽는다.

        String rxData = rxintent.getStringExtra("msg");
        String msg = "";
        msg = msg + rxData;


        String get_everyBTN = rxintent.getStringExtra("EveryDay");//"매일" 클릭을 전달하기 위한 변수
        String every="";
        every = every + get_everyBTN;

        Log.i(TAG,"LAY 3 ==> "+ msg +", "+get_everyBTN);


        String get_specificETXT = rxintent.getStringExtra("SpecificDay");    //특정 일수 일 EditTXT 메시지 받기위한 변수
        String specific ="";
        specific = specific + get_specificETXT;

        String[] checkDay  = {"","","","","","",""};
        checkDay[0] = checkDay[0]+rxintent.getStringExtra("Monday");
        checkDay[1] = checkDay[1]+rxintent.getStringExtra("Tuesday");
        checkDay[2] = checkDay[2]+rxintent.getStringExtra("Wednesday");
        checkDay[3] = checkDay[3]+rxintent.getStringExtra("Thursday");
        checkDay[4] = checkDay[4]+rxintent.getStringExtra("Friday");
        checkDay[5] = checkDay[5]+rxintent.getStringExtra("Saturday");
        checkDay[6] = checkDay[6]+rxintent.getStringExtra("Sunday");

        String get_daysETXT = rxintent.getStringExtra("Days");
        String days = "";
        days = days + get_daysETXT;

        String get_numETXT = rxintent.getStringExtra("Nums");
        String num = "";
        num = num + get_numETXT;

        String get_quantityETXT = rxintent.getStringExtra("Quantities");
        String quantity = "";
        quantity = quantity + get_quantityETXT;


        String get_timeETXT = rxintent.getStringExtra("Time");
        String time = "";
        time = time + get_timeETXT;


        String get_countETXT = rxintent.getStringExtra("Count");
        String count = "";
        count = count + get_countETXT;



        //TextView에 출력하는 부분 ==========================================================================================
        //현재시간 출력
        long mNow;
        Date mDate;
        SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);

        resdayTXT.setText( mFormat.format(mDate));
        resdrugTXT.setText(msg);

        if((every != null) && (specific == null)){
            reseveryTXT.setText(every); //매일 먹을 때 EveryDay라고 뜸!!!!
            resintervalTXT.setText(" ");
            rescountTXT.setText(checkDay[0]);

            restimeTXT.setText(time);   //먹을 시간!!!
            resnumTXT.setText(num); //약 개수!!!

        }
        else if((get_everyBTN == null) && (get_specificETXT != null)){

            reseveryTXT.setText(" ");
            resintervalTXT.setText(specific);   //며칠 마다 먹어야한다~~
            rescountTXT.setText(checkDay[0]);

            restimeTXT.setText(time);   //먹을 시간!!!
            resnumTXT.setText(num); //약 개수!!!!
        }




    }

    //Xml onClick's method========================================================
    public void onClick(View v){
        // 현재 Activity 종료
        finish();

    }

}