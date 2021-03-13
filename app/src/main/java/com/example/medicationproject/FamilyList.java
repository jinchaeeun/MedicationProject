package com.example.medicationproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medicationproject.R;

import java.util.ArrayList;
import java.util.HashMap;

public class FamilyList extends AppCompatActivity {
    //Member Variable ----------------------------------------
    // 디버깅을 위한 변수
    private final boolean       D           = true;
    private final String        TAG         = "FamilyList";

    // data
    private EditText            nameETXT, phoneETXT, emailETXT;

    //View object 관련
    private ListView            listView;

    // List 관련
    private ArrayList<HashMap<String, String>>  arrayList;
    private SimpleAdapter                       adapter;
    private HashMap<String, String>             map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.familylistlayout);
        init();

        if(D) Log.i(TAG, "onCreate()");
    }

    private void init(){
        //XML View 객체 가져오기
        nameETXT = findViewById(R.id.nameETXT);
        phoneETXT = findViewById(R.id.phoneETXT);
        emailETXT = findViewById(R.id.emailETXT);

        listView  = findViewById(R.id.addressLST);

        //List 데이터 준비
        arrayList = new ArrayList<HashMap<String, String>>();
        /*
        map = new HashMap<>();
        map.put("name", "Hong Gil Dong");
        map.put("phone", "010-1234-5678");
        map.put("email", "abc123@naver.com");
        arrayList.add(map);
         */

        adapter = new SimpleAdapter(FamilyList.this, arrayList, R.layout.item_layout, new String[]{"name", "phone", "email"}, new int[]{R.id.item_nameTxt, R.id.item_phoneTxt, R.id.item_emailTxt});
        //ListView에 List 설정
        listView.setAdapter(adapter);
    }
    //Member Method - XML onClick Method----------------------------------

    public void click(View v){
        switch (v.getId()){
            case R.id.addBTN:
                // 3개의 EditText 값 읽어서 Address 객체 생성 및 추가
                // (1) EditText 3개 값 입력 여부 체크

                if(nameETXT.getText().length()>0 && phoneETXT.getText().length()>0 && emailETXT.getText().length()>0){
                    // (2-1) Address 객체 생성 및 ArrayList 추가
                    map = new HashMap<String, String>();
                    map.put("name", nameETXT.getText().toString());
                    map.put("phone", phoneETXT.getText().toString());
                    map.put("email", emailETXT.getText().toString());
                    arrayList.add(map);

                    Log.i(TAG, "add => "+ arrayList.size()); //현재 입력된 갯수

                    // 3개 입력 필드 초기화(지우기)
                    initEXIT();

                    //TextView에 데이터 출력
                    //displayAddress();
                    adapter.notifyDataSetChanged();

                }else{
                    // (2-2) 사용자에게 알림 띄우기
                    Toast.makeText(this, R.string.add_msg, Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.delBTN:
                // 모두 삭제 또는 가장 최근에 추가한 데이터 삭제
                //가장 최근 삭제

                if(arrayList.size()>0) {
                    int lastIdx = arrayList.size() - 1;
                    arrayList.remove(lastIdx);

                    //TextView에 Address 데이터 출력
                    //displayAddress();
                    adapter.notifyDataSetChanged();

                }else{
                    Toast.makeText(this, R.string.del_msg, Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
    //Member Method - Custom----------------------------------
    //3개 입력 필드 초기화
    private void initEXIT(){
        nameETXT.setText("");
        phoneETXT.setText("");
        emailETXT.setText("");
    }
}