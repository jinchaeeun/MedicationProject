package com.example.medicationproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class LayoutDrug1 extends AppCompatActivity {

    //Member Variable ==========================================================================
    private final String TAG = "LayoutDrug1";
    private final  boolean D = true;

    private EditText drugETXT;  //약 종류 입력받기---------------
    private Button nextBTN1;    //다음으로 가기 버튼-------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_druglist_1);

        //디버깅 확인 용
        if(D)
            Log.i(TAG, "LayoutDrug1 OK");

        //초기화 메서드
        init();

    }




    //Memeber Method - Custome ====================================================
    //App 초기화 메소드
    private void init() {


        drugETXT = findViewById(R.id.drugETXT); //약 종류 적기 위한 edittext
        nextBTN1 = findViewById(R.id.nextBTN1); //다음으로 넘어가기 위한 버튼




        nextBTN1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*화면 전환용 intent*/
                Intent intent_1;
                intent_1 = new Intent(com.example.firstproject.LayoutDrug1.this, com.example.firstproject.LayoutDrug2.class);


                String msg = "";    //edittext에 들어간 약 종류 입력값 읽기 위한 msg
                msg = msg + drugETXT.getText(); //msg에 drugETXT에 들어간 "약 이름" 갱신


                intent_1.putExtra("msg", msg);  //"msg"라는 이름의 msg데이터 보내기

                Log.i(TAG,"LAY 1 ==> "+ drugETXT.getText());

                startActivity(intent_1);    //화면전환 layout_druglist_2로 ㄱㄱㄱㄱㄱㄱ

            }
        });


    }



}