/*
package com.example.medicationproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class LayoutDrug3 extends AppCompatActivity {

    //Member Variable ==========================================================================
    private final String TAG = "LayoutDrug3";
    private final  boolean D = true;

    private EditText daysETXT, numETXT, quantityETXT;
    private Button preBTN3,nextBTN3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_druglist_3);

        //초기화 메서드
        init();

        //디버깅 확인 용
        if(D)
            Log.i(TAG, "LayoutDrug3 OK");

    }


    //Memeber Method - Custome ====================================================
    //App 초기화 메소드
    private void init() {

        preBTN3 = findViewById(R.id.preBTN3);
        nextBTN3 = findViewById(R.id.nextBTN3);

        daysETXT = findViewById(R.id.daysETXT);
        numETXT = findViewById(R.id.numETXT);
        quantityETXT = findViewById(R.id.quantityETXT);



        //전 페이지로 부터 받기 위한 변수들==============================================================
        //현재 Acitivity 실행한 Intent 객체 가져오기
        Intent rxintent = this.getIntent(); //받은거 읽는다.

        //약 종류 전달 받기=================================================================================
        String rxData = rxintent.getStringExtra("msg");
        String msg = "";
        msg = msg + rxData;


        //매일 버튼 클릭시 EveryDay 문자열 전달 받기=======================================================
        String get_everyBTN = rxintent.getStringExtra("EveryDay");//"매일" 클릭을 전달하기 위한 변수
        String every="";
        every = every + get_everyBTN;
        Log.i(TAG,"LAY 3-1 ==> "+ msg +", "+every);


        //특정 일수 간격 버튼 눌렸을 시 특정 간격 전달 받기!!!!!!==========================================
        String get_specificETXT = rxintent.getStringExtra("SpecificDay");    //특정 일수 일 EditTXT 메시지 받기위한 변수
        String specific ="";
        specific = specific + get_specificETXT;
        Log.i(TAG,"LAY 3-2 ==> "+ msg +", "+specific);


        //체크박스에 요일 전달받기!!!!!!===============================================================================
        String[] checkDay  = {"","","","","","",""};
        checkDay[0] = checkDay[0]+rxintent.getStringExtra("Monday");
        checkDay[1] = checkDay[1]+rxintent.getStringExtra("Tuesday");
        checkDay[2] = checkDay[2]+rxintent.getStringExtra("Wednesday");
        checkDay[3] = checkDay[3]+rxintent.getStringExtra("Thursday");
        checkDay[4] = checkDay[4]+rxintent.getStringExtra("Friday");
        checkDay[5] = checkDay[5]+rxintent.getStringExtra("Saturday");
        checkDay[6] = checkDay[6]+rxintent.getStringExtra("Sunday");


        Intent intent_3;
        intent_3 = new Intent(com.example.firstproject.LayoutDrug3.this, com.example.firstproject.LayoutDrug4.class);


        //약종류 전달-----------------------------------------
        intent_3.putExtra("msg", msg);

        //에브리 버튼이 눌렸을때!!!
        if(every != null){

            intent_3.putExtra("EveryDay", get_everyBTN);

        }
        // 특정 간격 버튼이 눌렸을 때!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        else if((every == null) && (specific != null)){


            intent_3.putExtra("SpecificDay", specific);


            if(checkDay[0] != null){
                intent_3.putExtra("Monday", checkDay[0]);
            }
            else if(checkDay[1] != null){
                intent_3.putExtra("Tuesday", checkDay[1]);
            }
            else if(checkDay[2] != null){
                intent_3.putExtra("Wednesday", checkDay[2]);
            }
            else if(checkDay[3] != null){
                intent_3.putExtra("Thursday", checkDay[3]);
            }
            else if(checkDay[4] != null){
                intent_3.putExtra("Friday", checkDay[4]);
            }
            else if(checkDay[5] != null){
                intent_3.putExtra("Saturday", checkDay[5]);
            }
            else if(checkDay[6] != null){
                intent_3.putExtra("Sunday", checkDay[6]);
            }

        }



    //다음 버튼 눌렸을때 ---------------------------------------------------------------------------------------
        nextBTN3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //현 레이아웃에서 다음 레이아웃으로 전달하기 위한 변수들 =============================================
                String get_daysETXT = "";
                String get_numETXT = "";
                String get_quantityETXT = "";

                get_daysETXT = get_daysETXT + daysETXT.getText();   //"일" 전달----------------------------
                get_numETXT = get_numETXT + numETXT.getText();  //"회"전달-----------------------------------
                get_quantityETXT = get_quantityETXT + quantityETXT.getText();//"일분" 전달 ------------------


                intent_3.putExtra("Days", get_daysETXT);
                intent_3.putExtra("Nums", get_numETXT);
                intent_3.putExtra("Quantities", get_quantityETXT);


                startActivity(intent_3);

            }
        });



        preBTN3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });


    }


}*/
