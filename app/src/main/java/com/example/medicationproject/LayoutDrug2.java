package com.example.medicationproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LayoutDrug2 extends AppCompatActivity {

    //Member Variable ==========================================================================
    private final String TAG = "LayoutDrug2";
    private final boolean D = true;

    private Button preBTN2, nextBTN2;
    private Button everyBTN, specificBTN;   // 이전버튼, 다음버튼, 매일버튼, 특정일수간격버튼, 특정요일버튼--------

    private EditText  numETXT, quantityETXT;
    private LinearLayout ckLAY;
    int on_off = 1;


    private CheckBox MonCK, TueCK, WedCK, ThurCK, FriCK, SatCK, SunCK;  //차례대로 월화수목금토일 체크박스----------

    Intent intent_2;    //다음 레이아웃으로 전환시켜주기 위한 intent_2

    String check1_1;
    String check1_2;
    String check1_3;
    String check1_4;
    String check1_5;
    String check1_6 ;
    String check1_7;

    Boolean day1;
    Boolean day2;
    Boolean day3;
    Boolean day4;
    Boolean day5;
    Boolean day6;
    Boolean day7;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_druglist_2);

        init();


        //디버깅 확인 용
        if (D)
            Log.i(TAG, "LayoutDrug2 OK");

        //초기화 메서드


    }



    //Memeber Method - Custome ====================================================
    //App 초기화 메소드
    private void init() {

        //초기화=======================================================================================
        everyBTN = findViewById(R.id.everyBTN);   //특정 일수 간격 버튼 초기화--------------------------
        specificBTN = findViewById(R.id.specificBTN); //특정요일 버튼 초기화-------------------------

        preBTN2 = findViewById(R.id.preBTN2);   //이전 버튼 초기화------------------------
        nextBTN2 = findViewById(R.id.nextBTN2); //다음 버튼 초기화-----------------------

        ckLAY = findViewById(R.id.ckLAY);
        MonCK = findViewById(R.id.MonCK); //월
        TueCK = findViewById(R.id.TueCK); //화
        WedCK = findViewById(R.id.WedCK); //수
        ThurCK = findViewById(R.id.ThurCK); //목
        FriCK = findViewById(R.id.FriCK); //금
        SatCK = findViewById(R.id.SatCK); //토
        SunCK = findViewById(R.id.SunCK); //일

        numETXT = findViewById(R.id.numETXT);
        quantityETXT = findViewById(R.id.quantityETXT);


        intent_2 = new Intent(com.example.firstproject_2.LayoutDrug2.this, LayoutDrug3.class);


        check1_1 = "Monday";
        check1_2 = "Tuesday";
        check1_3 = "Wednesday";
        check1_4 = "Thursday";
        check1_5 = "Friday";
        check1_6 = "Saturday";
        check1_7 = "Sunday";



    }


        //checkBox 확인
    public void check(){
        day1 = MonCK.isChecked();
        day2 = TueCK.isChecked();
        day3 = WedCK.isChecked();
        day4 = ThurCK.isChecked();
        day5 = FriCK.isChecked();
        day6 = SatCK.isChecked();
        day7 = SunCK.isChecked();

    }




    public void onClick(View v){

        init();

        Intent rxintent = this.getIntent(); //intent 받아오기


        //전 페이지로 부터 받기위한 변수들==================================================================================
        String rxData = rxintent.getStringExtra("msg");     //String타입 rxIntent 받아오기   약종류 받아오기---------
        String msg = "";
        msg = msg + rxData; //전 페이지로부터 받은 "약 종류" 데이터

        Log.i(TAG, "LAY 2 ==> " + msg);    //약 종류 잘 들어왔나 로그로 확인위함



        //현 페이지로 부터 보내기위한 변수들=====================================================================================

        //intent_2를 통해 현재 레이아웃과 전 레이아웃 다 합쳐서 다음 레이아웃으로 전달하기 위한 과정====================================
        //Intent intent_2;    //다음 레이아웃으로 전환시켜주기 위한 intent_2
        //intent_2 = new Intent(LayoutDrug2.this, LayoutDrug3.class);

        intent_2.putExtra("msg", msg);  //받은 약종류 다음으로 전달----------------------------------


        String get_everyBTN = "EveryDay";
        String get_specificBTN = "SepecificDay";
        String check1_1 = "Monday";
        String check1_2 = "Tuesday";
        String check1_3 = "Wednesday";
        String check1_4 = "Thursday";
        String check1_5 = "Friday";
        String check1_6 = "Saturday";
        String check1_7 = "Sunday";


        String get_daysETXT = "";
        String get_numETXT = "";
        String get_quantityETXT = "";


        switch (v.getId()){

            case R.id.everyBTN:
                Toast.makeText(com.example.firstproject_2.LayoutDrug2.this,"매일 클릭!", Toast.LENGTH_SHORT).show();
                check();
                day1 = true;
                day2 = true;
                day3 = true;
                day4 = true;
                day5 = true;
                day6 = true;
                day7 = true;
                break;

            // 체크박스 활성화 set visible
            case R.id.specificBTN:
                Toast.makeText(com.example.firstproject_2.LayoutDrug2.this,"특정 일 클릭!", Toast.LENGTH_SHORT).show();
                if(on_off == 1) {
                    ckLAY.setVisibility(View.VISIBLE);
                    on_off = 0;
                }
                else{
                    ckLAY.setVisibility(View.GONE);
                    on_off = 1;
                }
                 break;

            case R.id.nextBTN2:

                check(); //요일 전달
                if(day1 ==true) intent_2.putExtra("Monday", check1_1); else intent_2.putExtra("Monday", "");
                if(day2 == true) intent_2.putExtra("Tuesday", check1_2); else intent_2.putExtra("Tuesday", "");
                if(day3 == true) intent_2.putExtra("Wednesday", check1_3); else intent_2.putExtra("Wednesday", "");
                if(day4 == true) intent_2.putExtra("Thursday", check1_4); else intent_2.putExtra("Thursday", "");
                if(day5 == true) intent_2.putExtra("Friday", check1_5); else intent_2.putExtra("Friday", "");
                if(day6 == true) intent_2.putExtra("Saturday", check1_6); else intent_2.putExtra("Saturday", "");
                if(day7 == true) intent_2.putExtra("Sunday", check1_7); else intent_2.putExtra("Sunday", "");

                if(((day1 ==true) && (day2 == true) && (day3 == true) && (day4 == true) && (day5 == true) && (day6 == true)&& (day7 == true))
                ||((day1 ==false) && (day2 == false) && (day3 == false) && (day4 == false) && (day5 == false) && (day6 == false)&& (day7 == false))){

                    Log.i(TAG, "EVERY OK");
                    intent_2.putExtra("EveryDay", get_everyBTN);
                    intent_2.putExtra("SpecificDay", "");

                }
                else{
                    Log.i(TAG, "specificBTN OK");

                    intent_2.putExtra("EveryDay", "");
                    intent_2.putExtra("SpecificDay", get_specificBTN);
                }


                get_numETXT =  numETXT.getText().toString();  //"회"전달-----------------------------------
                get_quantityETXT = quantityETXT.getText().toString();//"일분" 전달 ------------------

                if((get_numETXT.length() > 0) && (get_quantityETXT.length() > 0)){

                    intent_2.putExtra("Nums", get_numETXT);
                    intent_2.putExtra("Quantities", get_quantityETXT);

                    startActivity(intent_2);

                }
                else{
                    Toast.makeText(com.example.firstproject_2.LayoutDrug2.this, "내용을 입력하세요.", Toast.LENGTH_SHORT).show();
                }

                break;


            case R.id.preBTN2:
                finish();
                break;
        }

    }


}