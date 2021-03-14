package com.example.medicationproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.HashMap;

public class FamilyList extends AppCompatActivity {

    //Member Variable ----------------------------------------
    // 디버깅을 위한 변수
    private final boolean D = true;
    private final String TAG = "FamilyList";

    //firebase
    private FirebaseDatabase database;

    //데이터베이스의 정보
    DatabaseReference ref;

    private int familyListCount = 0;

    // data
    private EditText nameETXT, phoneETXT, emailETXT;

    //View object 관련, 화면에 뿌려줄 뷰
    private ListView listView;

    // List 관련
    private ArrayList<HashMap<String, String>> arrayList; //정보 담을 객체
    private SimpleAdapter adapter;                        // 데이터 넣어줄 어댑터
    private HashMap<String, String> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.familylistlayout);
        init();

        //firebase 연결
        database = FirebaseDatabase.getInstance();

        //firebase에서 레퍼런스 가져오기
        ref = database.getReference();
        // ref = database.getReference("family_member");

        //firebase에서 데이터를 가져오기


        if (D) Log.i(TAG, "onCreate()");
    }

    private void init() {
        //XML View 객체 가져오기
        nameETXT = findViewById(R.id.nameETXT);
        phoneETXT = findViewById(R.id.phoneETXT);
        emailETXT = findViewById(R.id.emailETXT);

        listView = findViewById(R.id.addressLST);

        //List 데이터 준비
        arrayList = new ArrayList<HashMap<String, String>>();

        adapter = new SimpleAdapter(FamilyList.this, arrayList, R.layout.item_layout, new String[]{"name", "phone", "email"}, new int[]{R.id.item_nameTxt, R.id.item_phoneTxt, R.id.item_emailTxt});
        //ListView에 List 설정
        listView.setAdapter(adapter);
    }

    //Member Method - XML onClick Method----------------------------------

    public void click(View v) {
        switch (v.getId()) {
            case R.id.addBTN:
                // 3개의 EditText 값 읽어서 Address 객체 생성 및 추가
                // (1) EditText 3개 값 입력 여부 체크

                if (nameETXT.getText().length() > 0 && phoneETXT.getText().length() > 0 && emailETXT.getText().length() > 0) {
                    // (2-1) Address 객체 생성 및 ArrayList 추가
                    map = new HashMap<String, String>();
                    map.put("name", nameETXT.getText().toString());
                    map.put("phone", phoneETXT.getText().toString());
                    map.put("email", emailETXT.getText().toString());
                    //arrayList.add(map);

                    Log.i(TAG, "add => " + arrayList.size()); //현재 입력된 갯수

                    //firebase 저장 method 호출
                    writeNewUser(familyListCount++, nameETXT.getText().toString(), phoneETXT.getText().toString(), emailETXT.getText().toString());

                    // 3개 입력 필드 초기화(지우기)
                    //initEXIT();

                    //adapter에 noti 날림
                    //adapter.notifyDataSetChanged();

                } else {
                    // (2-2) 사용자에게 알림 띄우기
                    Toast.makeText(this, R.string.add_msg, Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.delBTN:
                // 모두 삭제 또는 가장 최근에 추가한 데이터 삭제
                //가장 최근 삭제
                if (arrayList.size() > 0) {
                    int lastIdx = arrayList.size() - 1;
                    arrayList.remove(lastIdx);

                    //adapter에 noti 날림
                    adapter.notifyDataSetChanged();

                } else {
                    Toast.makeText(this, R.string.del_msg, Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    //ListView에 firebase 데이터 조회



    // firebase에 데이터 추가
    private void writeNewUser(final int position, String name, String phone, String email){
        FamilyAddress familyAdd = new FamilyAddress(name, phone, email);

        ref.child("family_member").child(String.valueOf(position)).setValue(familyAdd).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //write was successful!
                arrayList.add(map);

                adapter.notifyDataSetChanged();

                Toast.makeText(FamilyList.this, "입력하신 정보를 저장했습니다.", Toast.LENGTH_LONG).show();

                Log.i(TAG, "onSuccess");

                // 3개 입력 필드 초기화(지우기)
                initEXIT();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //write Failed
                Toast.makeText(FamilyList.this, "저장에 실패하였습니다. 다시 시도해주세요.", Toast.LENGTH_LONG).show();
                Log.i(TAG, "onFailure");
            }
        });
    }

    //firebase 데이터 읽어서 ListView에 출력하기

    
    //Member Method - Custom----------------------------------
    //3개 입력 필드 초기화
    private void initEXIT(){
        nameETXT.setText("");
        phoneETXT.setText("");
        emailETXT.setText("");
    }
}