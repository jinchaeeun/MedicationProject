package com.example.medicationproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class FamilyList extends AppCompatActivity {

    //Member Variable ----------------------------------------
    // 디버깅을 위한 변수
    private final boolean D = true;
    private final String TAG = "FamilyList";

    //firebase
    private FirebaseDatabase mDatabase;

    //데이터베이스의 정보
    private DatabaseReference mReference;

    private ChildEventListener mChild;
    


    //데이터 값 저장
    private String SharedPrefFile = "com.example.medicationproject"; //파일 이름
    private SharedPreferences cnt;
    private int familyListCount;
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
        initDatabase();

        cnt = getSharedPreferences(SharedPrefFile, MODE_PRIVATE);
        //get 메소드의 첫번째 인자는 key값 , 두번째 인자는 key값에 맞는 데이터가 없을 경우 기본값
        familyListCount = cnt.getInt("famCnt",0);
        Log.i("firebase", "on Create() 패밀리리스트 값: "+familyListCount);
        if (D) Log.i(TAG, "onCreate()");
    }

    //SharedPreferences에 데이터를 넣기
    // onPause를 자동으로 못 실행하는지 값을 못가져오길래 ClickEvent 마지막에 실행해줬음

    protected void famCntSave(){
        SharedPreferences.Editor preferencesEditor = cnt.edit();
        Log.i("firebase", "famCntSave() 패밀리리스트 값: "+familyListCount);
        //데이터입력
        preferencesEditor.putInt("famCnt",familyListCount);
        Log.i("FireBase famCntSave()", "putInt 값 : " + familyListCount);
        //적용
        preferencesEditor.apply();
    }



    private void init() {
        //XML View 객체 가져오기
        nameETXT = findViewById(R.id.nameETXT);
        phoneETXT = findViewById(R.id.phoneETXT);
        emailETXT = findViewById(R.id.emailETXT);

        listView = findViewById(R.id.addressLST);

        //List 데이터 준비
        arrayList = new ArrayList<HashMap<String, String>>();
        
        //Apdater 연결
        adapter = new SimpleAdapter(FamilyList.this, arrayList, R.layout.item_layout, new String[]{"name", "phone", "email"}, new int[]{R.id.item_nameTxt, R.id.item_phoneTxt, R.id.item_emailTxt});
        //ListView에 List 설정
        listView.setAdapter(adapter);
    }

    public void initDatabase(){
        //firebase 연결
        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference();
        //firebase에서 레퍼런스 가져오기
        /*
        mChild = new ChildEventListener() {
            //항목 목록 검색, 항목 목록에 대한 추가를 수신 대기
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }
            //목록의 항목에 대한 변경 수신 대기
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }
            // 목록의 항목 삭제 수신대기
            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }
            // 순서 있는 목록의 항목 순서 변경을 수신대기기
            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        mReference.addChildEventListener(mChild);*/
    }





    /*
    mReference = mDatabase.getReference("family_member");   //변경 값 확인할 child
    mReference.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            //adapter.clear();
            for (DataSnapshot messageData : dataSnapshot.getChildren()) {
                //child 내에 있는 데이터만큼 반복
                String name = messageData.getValue().toString();
                String phone = messageData.getValue().toString();
                String email = messageData.getValue().toString();

                map = new HashMap<String, String>();
                map.put("name", nameETXT.getText().toString());
                map.put("phone", phoneETXT.getText().toString());
                map.put("email", emailETXT.getText().toString());
                arrayList.add(map);

            }
            //리스트뷰 갱신하고 마지막 위치를 카운트해서 보내줌
            adapter.notifyDataSetChanged();
            listView.setSelection(adapter.getCount() - 1);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });*/

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
                    Log.i("firebase", "add 패밀리리스트 값: "+familyListCount);
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
                //firebase 가장 최근 삭제
                removeUser(familyListCount, nameETXT.getText().toString(), phoneETXT.getText().toString(), emailETXT.getText().toString());
                /*
                if (arrayList.size() > 0) {
                    int lastIdx = arrayList.size() - 1;
                    arrayList.remove(lastIdx);

                    //adapter에 noti 날림
                    adapter.notifyDataSetChanged();

                    //마지막 포지션 값 지우기
                    removeUser(familyListCount, nameETXT.getText().toString(), phoneETXT.getText().toString(), emailETXT.getText().toString());

                } else {
                    Toast.makeText(this, R.string.del_msg, Toast.LENGTH_LONG).show();
                }*/
                Log.i("firebase", "delete 패밀리리스트 값: "+familyListCount);
                break;
        }
        famCntSave();
    }

    //ListView에 firebase 데이터 조회


    // firebase에 데이터 읽기
    private void readUser(final int position, String name, String phone, String email) {
        FamilyAddress famAddr = new FamilyAddress(name, phone, email);

        //지정 위치에서 모든 데이터를 다시 덮어씀
        //mReference.child("family_member").child(String.valueOf(position)).setValue(famAddr);

        //데이터 한번 읽기 하나씩 가져왔음.
        mReference.child("family_member").child(String.valueOf(position)).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.i("if_firebase_onComplete", "Error getting data", task.getException());
                } else {
                    Log.i("else_firebase_onComplete", String.valueOf(task.getResult().getValue()));
                }
            }
        });
    }
    // firebase에 데이터 추가
    private void writeNewUser(final int position, String name, String phone, String email){
        FamilyAddress familyAdd = new FamilyAddress(name, phone, email);

        mReference.child("family_member").child(String.valueOf(position)).setValue(familyAdd).addOnSuccessListener(new OnSuccessListener<Void>() {
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

    // firebase 데이터 삭제하기
    private void removeUser(final int position, String name, String phone, String email){
        if(position != familyListCount){
            Toast.makeText(FamilyList.this, "삭제하시려면 한번 더 누르세요.", Toast.LENGTH_SHORT).show();
        }
        Log.i("firebase", "delete "+ position);
        familyListCount--;
        mReference.child("family_member").child(String.valueOf(position)).removeValue();
        Log.i("firebase removeUser", "removeUser" + position);
    }

    
    //Member Method - Custom----------------------------------
    //3개 입력 필드 초기화
    private void initEXIT(){
        nameETXT.setText("");
        phoneETXT.setText("");
        emailETXT.setText("");
    }
}