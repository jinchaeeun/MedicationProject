package com.example.medicationproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LayoutDrug4 extends AppCompatActivity {

    //Member Variable ==========================================================================
    private final String TAG = "LayoutDrug4";
    private final  boolean D = true;


    private TextView resdrugTXT;    //약 종류 띄워주기 위한 텍스트------------
    private TextView resdayTXT;     //날짜 출력 위함----------------------

    private Switch alarmSW;         //알람 스위치------------------------

    private TextView reseveryTXT;   //"매일" 의미함을 위한 텍스트-----------
    private TextView resintervalTXT;    //특정 일 표시 위한 텍스트----------
    private TextView rescountTXT;   // 며칠 분인지 표시 위한 텍스트 ---------

    private TextView restimeTXT;    //먹을 시간 띄워주는 텍스트--------------
    private TextView resnumTXT;      //먹을 약 개수 띄워주는 텍스트----------


    private Button preBTN4;
    private Button nextBTN4;


    Intent intent_4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_druglist_4);


        //초기화 메서드
        init();

        //디버깅 확인 용
        if(D)
            Log.i(TAG, "LayoutDrug4 OK");


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


        preBTN4 = findViewById(R.id.preBTN4);
        nextBTN4 = findViewById(R.id.nextBTN4);




        intent_4 = new Intent(com.example.medicationproject.LayoutDrug4.this, LayoutDrug5.class);


        //전 페이지로 부터 받기 위한 변수들==============================================================
        //현재 Acitivity 실행한 Intent 객체 가져오기
        Intent rxintent = this.getIntent(); //받은거 읽는다.

        String rxData = rxintent.getStringExtra("msg");
        String msg = "";
        msg = msg + rxData;
        Log.i(TAG,"LayoutDrug4 msg ==> "+ msg);


        String get_everyBTN = rxintent.getStringExtra("EveryDay");//"매일" 클릭을 전달하기 위한 변수
        String every="";
        every = every + get_everyBTN;
        Log.i(TAG,"LayoutDrug4 EveryDay==> "+every);


        String get_specificETXT = rxintent.getStringExtra("SpecificDay");    //특정 일수 일 EditTXT 메시지 받기위한 변수
        String specific ="";
        specific = specific + get_specificETXT;
        Log.i(TAG,"LayoutDrug4 SpecificDay==> "+specific);


        String[] checkDay  = {"","","","","","",""};
        checkDay[0] = checkDay[0]+rxintent.getStringExtra("Monday");
        checkDay[1] = checkDay[1]+rxintent.getStringExtra("Tuesday");
        checkDay[2] = checkDay[2]+rxintent.getStringExtra("Wednesday");
        checkDay[3] = checkDay[3]+rxintent.getStringExtra("Thursday");
        checkDay[4] = checkDay[4]+rxintent.getStringExtra("Friday");
        checkDay[5] = checkDay[5]+rxintent.getStringExtra("Saturday");
        checkDay[6] = checkDay[6]+rxintent.getStringExtra("Sunday");
        Log.i(TAG, checkDay[0]+checkDay[1]+checkDay[2]+checkDay[3]+checkDay[4]+checkDay[5]+checkDay[6]);





        String get_numETXT = rxintent.getStringExtra("Nums");
        String num = "";
        num = get_numETXT;
        Log.i(TAG,"LayoutDrug4 num==> "+ num) ;

        String get_quantityETXT = rxintent.getStringExtra("Quantities");
        String quantity = "";
        quantity =  get_quantityETXT;
        Log.i(TAG,"LayoutDrug4 quantity==> "+ quantity) ;



        String get_timeETXT1 = rxintent.getStringExtra("Time1");
        String time1 = "";
        time1 =  get_timeETXT1;
        Log.i(TAG,"LayoutDrug4 quantity==> "+ time1) ;

        String get_countETXT1 = rxintent.getStringExtra("Count1");
        String count1 = "";
        count1 =  get_countETXT1;
        Log.i(TAG,"LayoutDrug4 quantity==> "+ count1) ;


        String get_timeETXT2 = rxintent.getStringExtra("Time2");
        String time2 = "";
        time2 =  get_timeETXT2;
        Log.i(TAG,"LayoutDrug4 quantity==> "+ time2) ;

        String get_countETXT2 = rxintent.getStringExtra("Count2");
        String count2 = "";
        count2 =  get_countETXT2;
        Log.i(TAG,"LayoutDrug4 quantity==> "+ count2) ;

        String get_timeETXT3 = rxintent.getStringExtra("Time3");
        String time3 = "";
        time3 = get_timeETXT3;
        Log.i(TAG,"LayoutDrug4 quantity==> "+ time3) ;

        String get_countETXT3 = rxintent.getStringExtra("Count3");
        String count3 = "";
        count3 = get_countETXT3;
        Log.i(TAG,"LayoutDrug4 quantity==> "+ count3) ;



        //약종류 전달-----------------------------------------
        intent_4.putExtra("msg", msg);
        intent_4.putExtra("EveryDay", every);

        intent_4.putExtra("SpecificDay", specific);
        intent_4.putExtra("Monday", checkDay[0]);
        intent_4.putExtra("Tuesday", checkDay[1]);
        intent_4.putExtra("Wednesday", checkDay[2]);
        intent_4.putExtra("Thursday", checkDay[3]);
        intent_4.putExtra("Friday", checkDay[4]);
        intent_4.putExtra("Saturday", checkDay[5]);
        intent_4.putExtra("Sunday", checkDay[6]);

        intent_4.putExtra("Quantities", get_quantityETXT);
        intent_4.putExtra("Nums", get_numETXT);


        intent_4.putExtra("Time1", get_timeETXT1);
        intent_4.putExtra("Count1", get_countETXT1);
        intent_4.putExtra("Time2", get_timeETXT2);
        intent_4.putExtra("Count2", get_countETXT2);
        intent_4.putExtra("Time3", get_timeETXT3);
        intent_4.putExtra("Count3", get_countETXT3);




        //TextView에 출력하는 부분 ==========================================================================================
        //현재시간 출력
        long mNow;
        Date mDate;
        SimpleDateFormat mFormat = new SimpleDateFormat("현재 시간 : \n yyyy / MM / dd => hh:mm:ss");

        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);

        resdayTXT.setText( mFormat.format(mDate));  //현재 시간 출력--------------------------------

        resdrugTXT.setText("약 이름 : \n"+ msg);    //약 종류 출력================================================



        if((every.toString().length() <= 0 ) && (specific.toString().length() > 0)){

            reseveryTXT.setText("먹는 주기 : " + specific); //매일 먹을 때 EveryDay라고 뜸!!!!

            String add_yoil = "";
            for(int i=0; i< checkDay.length ; i++){

                if (checkDay[i] != null  ){

                    add_yoil = add_yoil + checkDay[i] +"\n";

                }
                else {
                    checkDay[i] = "";
                    add_yoil = add_yoil + checkDay[i] +"\n";
                }

            }
            resintervalTXT.setText("먹는 요일 : \n"+add_yoil);

            rescountTXT.setText("먹는 횟수 : \n" + quantity + "일 치");


            String add_time="";
            if(time1.length() > 0){
                add_time = add_time + time1 + "시 \n";
            }
            else{
                add_time = add_time + time1 ;
            }

            if(time2.length() > 0){
                add_time = add_time + time2 + "시 \n";
            }
            else{
                add_time = add_time + time2 ;
            }

            if(time3.length() > 0){
                add_time = add_time + time3 + "시 \n";
            }
            else{
                add_time = add_time + time3 ;
            }
            restimeTXT.setText("먹는 시간 : \n" + add_time);

            String add_count="";
            if(count1.length() > 0){
                add_count = add_count + count1 + "개 \n";
            }
            else{
                add_count = add_count + count1 ;
            }

            if(count2.length() > 0){
                add_count = add_count + count2 + "개 \n";
            }
            else{
                add_count = add_count + count2 ;
            }

            if(count3.length() > 0){
                add_count = add_count + count3 + "개 \n";
            }
            else{
                add_count = add_count + count3 ;
            }
            resnumTXT.setText("먹는 개수 : \n" + add_count); //약 개수!!!

        }
        else {
            reseveryTXT.setText("먹는 주기 : \n" + every); //매일 먹을 때 EveryDay라고 뜸!!!!


            resintervalTXT.setText("먹는 요일 : \n"+"Monday\n"+"Tuesday\n"+"Wednesday\n"+"Thursday\n"+"Friday\n"+"Saturday\n"+"Sunday\n");

            rescountTXT.setText("먹는 횟 : \n" + quantity + "일 치");

            String add_time="";
            if(time1.length() > 0){
                add_time = add_time + time1 + "시 \n";
            }
            else{
                add_time = add_time + time1 ;
            }

            if(time2.length() > 0){
                add_time = add_time + time2 + "시 \n";
            }
            else{
                add_time = add_time + time2 ;
            }

            if(time3.length() > 0){
                add_time = add_time + time3 + "시 \n";
            }
            else{
                add_time = add_time + time3 ;
            }
            restimeTXT.setText("먹는 시간 : \n" + add_time);


            String add_count="";
            if(count1.length() > 0){
                add_count = add_count + count1 + "개 \n";
            }
            else{
                add_count = add_count + count1 ;
            }

            if(count2.length() > 0){
                add_count = add_count + count2 + "개 \n";
            }
            else{
                add_count = add_count + count2 ;
            }

            if(count3.length() > 0){
                add_count = add_count + count3 + "개 \n";
            }
            else{
                add_count = add_count + count3 ;
            }
            resnumTXT.setText("먹는 개수 : \n" + add_count); //약 개수!!!

        }

    }


    public void storage(View v){


        switch (v.getId()){
            case R.id.nextBTN4:

                startActivity(intent_4);

                break;


            case R.id.preBTN4:

                finish();
                break;


        }

    }

}