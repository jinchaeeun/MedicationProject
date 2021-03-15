/*
package com.example.medicationproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class LayoutDrug2 extends AppCompatActivity {

    //Member Variable ==========================================================================
    private final String TAG = "LayoutDrug2";
    private final  boolean D = true;

    private EditText specificETXT;    //간 격 일수 입력 edittxt--------------------------------
    private Button preBTN2,nextBTN2, everyBTN, specificBTN, specific_daysBTN;   // 이전버튼, 다음버튼, 매일버튼, 특정일수간격버튼, 특정요일버튼--------

    private LinearLayout checkLAY;

    private CheckBox check1,check2,check3,check4,check5,check6,check7;  //차례대로 월화수목금토일 체크박스----------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_druglist_2);


        //디버깅 확인 용
        if(D)
            Log.i(TAG, "LayoutDrug2 OK");

        //초기화 메서드
        init();

    }


    //Memeber Method - Custome ====================================================
    //App 초기화 메소드
    private void init() {


        specificETXT = findViewById(R.id.specificETXT); //간격 일수 입력 edittext 초기화 ------------------------
        specificBTN = findViewById(R.id.specificBTN);   //특정 일수 간격 버튼 초기화--------------------------
        specific_daysBTN = findViewById(R.id.specific_daysBTN); //특정요일 버튼 초기화-------------------------

        preBTN2 = findViewById(R.id.preBTN2);   //이전 버튼 초기화------------------------
        nextBTN2 = findViewById(R.id.nextBTN2); //다음 버튼 초기화-----------------------
        everyBTN = findViewById(R.id.everyBTN); //매일 버튼 초기화---------------------

        checkLAY = findViewById(R.id.checkLAY);

        check1 = findViewById(R.id.check1); //월
        check2 = findViewById(R.id.check2); //화
        check3 = findViewById(R.id.check3); //수
        check4 = findViewById(R.id.check4); //목
        check5 = findViewById(R.id.check5); //금
        check6 = findViewById(R.id.check6); //토
        check7 = findViewById(R.id.check7); //일



        Intent rxintent = this.getIntent(); //intent 받아오기


        //전 페이지로 부터 받기위한 변수들======================================================
        String rxData = rxintent.getStringExtra("msg");     //String타입 rxIntent 받아오기   약종류 받아오기---------
        String msg = "";
        msg = msg + rxData; //전 페이지로부터 받은 "약 종류" 데이터

        Log.i(TAG,"LAY 2 ==> "+msg);    //약 종류 잘 들어왔나 로그로 확인위함


        //현 페이지로 부터 보내기위한 변수들====================================================

        //intent_2를 통해 현재 레이아웃과 전 레이아웃 다 합쳐서 다음 레이아웃으로 전달하기 위한 과정====================================
        Intent intent_2;    //다음 레이아웃으로 전환시켜주기 위한 intent_2
        intent_2 = new Intent(com.example.firstproject.LayoutDrug2.this, LayoutDrug3.class);

        intent_2.putExtra("msg", msg);  //받은 약종류 다음으로 전달----------------------------------




        //매일 버튼 눌렸을때===================================================================
        everyBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String get_everyBTN = "EveryDay";//"매일" 클릭을 전달하기 위한 변수
                String get_specificETXT = "";    //특정 일수 일 EditTXT 메시지 받기위한 변수

                intent_2.putExtra("EveryDay", get_everyBTN);    //"매일 버튼" 눌렸을 시 EveryDay 문자열 전달
                intent_2.putExtra("SpecificDay", get_specificETXT); //매일 버튼 눌렸기 때문에 get_specificETXT는 빈값을 전달한다.

                //매일 버튼 눌렸기때문에 요일 전체 전달!!!!
                String check1="Monday",check2="Tuesday",check3="Wednesday",check4="Thursday",check5="Friday",check6="Saturday",check7="Sunday";
                intent_2.putExtra("Monday", check1);
                intent_2.putExtra("Tuesday", check2);
                intent_2.putExtra("Wednesday", check3);
                intent_2.putExtra("Thursday", check4);
                intent_2.putExtra("Friday", check5);
                intent_2.putExtra("Saturday", check6);
                intent_2.putExtra("Sunday", check7);

            }
        });



        //특정 일수 간격버튼 눌렸을 때 ===================================================================================================
        specificBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String get_everyBTN = "";//"매일" 클릭을 전달하기 위한 변수
                String get_specificETXT = "";    //특정 일수 일 EditTXT 메시지 받기위한 변수
                get_specificETXT = get_specificETXT + specificETXT.getText();

                intent_2.putExtra("EveryDay", get_everyBTN);    //매일 버튼 눌렸기 때문에 빈 값전달
                intent_2.putExtra("SpecificDay", get_specificETXT); //간 격 일수 입력받은거 전달


                //EveryDay가 아니기 때문에 눌린 체크박스 전달!!!!!아마???...................
                String check1="Monday",check2="Tuesday",check3="Wednesday",check4="Thursday",check5="Friday",check6="Saturday",check7="Sunday";
                if(check1 != null){
                    intent_2.putExtra("Monday", check1);
                }
                else if(check2 != null){
                    intent_2.putExtra("Tuesday", check2);
                }
                else if(check3 != null){
                    intent_2.putExtra("Wednesday",  check3);
                }
                else if(check4 != null){
                    intent_2.putExtra("Thursday", check4);
                }
                else if(check5 != null){
                    intent_2.putExtra("Friday", check5);
                }
                else if(check6 != null){
                    intent_2.putExtra("Saturday",  check6);
                }
                else if(check7 != null){
                    intent_2.putExtra("Sunday", check7);
                }



            }
        });



        //다음 버튼 누르면 다음 화면 전환===========================================
        nextBTN2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(intent_2);

            }
        });




        //이전 버튼===============================================================
        preBTN2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });


    }


}*/
