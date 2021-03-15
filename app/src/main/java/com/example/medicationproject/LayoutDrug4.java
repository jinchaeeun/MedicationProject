/*
package com.example.medicationproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LayoutDrug4 extends AppCompatActivity {

    //Member Variable ==========================================================================
    private final String TAG = "LayoutDrug4";
    private final  boolean D = true;

    private EditText timeETXT,countETXT;    //약 먹을 "시간"입력 위함, 그 시간의 약 "개수"입력 위함
    private ListView listLST;   //??????????????????
    private Button preBTN4, nextBTN4;   //이전 버튼, 다음 버튼
    Intent intent;

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


        preBTN4 = findViewById(R.id.preBTN4);
        nextBTN4 = findViewById(R.id.nextBTN4);

        timeETXT = findViewById(R.id.timeETXT);
        countETXT = findViewById(R.id.countETXT);
        listLST = findViewById(R.id.listLST);

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
        specific = specific + get_everyBTN;

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


        Intent intent_4;
        intent_4 = new Intent(com.example.firstproject.LayoutDrug4.this, LayoutAlarmList.class);


        if((timeETXT.getText() != null) && (countETXT.getText() != null)){
            if(every != null) {
                intent_4.putExtra("msg", msg);
                intent_4.putExtra("EveryDay", get_everyBTN);

                intent_4.putExtra("Days", get_daysETXT);
                intent_4.putExtra("Quantities", get_quantityETXT);
                intent_4.putExtra("Nums", get_numETXT);

            }
            else if((get_everyBTN == null) && (get_specificETXT != null)){


                intent_4.putExtra("SpecificDay", get_specificETXT);
                intent_4.putExtra("Days", get_daysETXT);
                intent_4.putExtra("Quantities", get_quantityETXT);
                intent_4.putExtra("Nums", get_numETXT);


                if(checkDay[0] != null){
                    intent_4.putExtra("Monday", checkDay[0]);
                }
                else if(checkDay[1] != null){
                    intent_4.putExtra("Tuesday", checkDay[1]);
                }
                else if(checkDay[2] != null){
                    intent_4.putExtra("Wednesday", checkDay[2]);
                }
                else if(checkDay[3] != null){
                    intent_4.putExtra("Thursday", checkDay[3]);
                }
                else if(checkDay[4] != null){
                    intent_4.putExtra("Friday", checkDay[4]);
                }
                else if(checkDay[5] != null){
                    intent_4.putExtra("Saturday", checkDay[5]);
                }
                else if(checkDay[6] != null){
                    intent_4.putExtra("Sunday", checkDay[6]);
                }


            }
        }
        else{
            Toast.makeText(com.example.firstproject.LayoutDrug4.this, "복약 시간과 복약할 약 개수를 입력하세요.", Toast.LENGTH_SHORT).show();
        }




        //현 레이아웃에서 다음 레이아웃으로 전달하기 위한 변수들 =============================================
        nextBTN4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String get_timeETXT = "";   //"약먹을 시간" 입력하기 위해 전달----------------------------------------
                String get_countETXT = "";  //시간에 맞는 "약 개수" 입력 위해 전달-------------------------------------------

                get_timeETXT = get_timeETXT + timeETXT.getText();   //입력된 약먹을 시간
                get_countETXT = get_countETXT + countETXT.getText();    //입력된 약 개수


                intent_4.putExtra("Time", get_timeETXT);    //시간 전달!!!!!!!!!!!!!!!!!!!!!!!!!!!
                intent_4.putExtra("Count", get_countETXT);  //약 개수 전달!!!!!!!!!!!!!!!!!!!!!!!

                startActivity(intent_4);

            }
        });


        preBTN4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });

    }

}*/
