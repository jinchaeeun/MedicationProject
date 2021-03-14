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
import java.util.List;

public class FamilyList extends AppCompatActivity {

    //Member Variable ----------------------------------------
    // 디버깅을 위한 변수
    private final boolean D = true;
    private final String TAG = "FamilyList";

    //firebase
    private FirebaseDatabase mDatabase;

    //데이터베이스의 정보
    private DatabaseReference mReference;

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

    //하위 키 값 찾기
    List memberInfoList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.familylistlayout);
        initDatabase();
        init();
        cnt = getSharedPreferences(SharedPrefFile, MODE_PRIVATE);
        //get 메소드의 첫번째 인자는 key값 , 두번째 인자는 key값에 맞는 데이터가 없을 경우 기본값
        familyListCount = cnt.getInt("famCnt",0);
        //값 뿌려주기
        readUser(familyListCount);
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

    public void initDatabase() {
        //firebase 연결
        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference();

        /*
        현재 필요는 없음 하위 key 값 찾을 때 사용

        mReference.child("family_member").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot keySnapshot : snapshot.getChildren()){
                    //하위 키 값 가져오기
                    String str = keySnapshot.child("info").getValue(String.class);
                    Log.i("firebase", "OnDataChange  하위 키 값: "+str);

                    memberInfoList.add(str);
                    
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i("TAG: ", "Failed to read value");
            }
        });
        */

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
    }
    //Member Method - XML onClick Method----------------------------------

    public void click(View v) {

        switch (v.getId()) {
            case R.id.addBTN:
                // (1) EditText 3개 값 입력 여부 체크
                if (nameETXT.getText().length() > 0 && phoneETXT.getText().length() > 0 && emailETXT.getText().length() > 0) {
                    //firebase 저장 method 호출
                    writeNewUser(familyListCount++, nameETXT.getText().toString(), phoneETXT.getText().toString(), emailETXT.getText().toString());
                    Log.i("firebase", "add 패밀리리스트 값: "+familyListCount);
                    Log.i(TAG, "add => " + arrayList.size()); //현재 입력된 갯수

                    //adapter에 noti 날림
                    adapter.notifyDataSetChanged();

                } else {
                    // (2-2) 사용자에게 알림 띄우기
                    Toast.makeText(this, R.string.add_msg, Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.delBTN:
                if (arrayList.size() > 0) {
                    //firebase 가장 최근 삭제
                    removeUser(familyListCount-1, nameETXT.getText().toString(), phoneETXT.getText().toString(), emailETXT.getText().toString());
                    adapter.notifyDataSetChanged();
                    Log.i("firebase", "delete 패밀리리스트 값: "+familyListCount);
//                    int lastIdx = arrayList.size() - 1;
//                    arrayList.remove(lastIdx);
//
//                    //adapter에 noti 날림
                    adapter.notifyDataSetChanged();


                } else {
                    Toast.makeText(this, R.string.del_msg, Toast.LENGTH_LONG).show();
                }
                break;
        }
        famCntSave();
    }

    //ListView에 firebase 데이터 조회

    // firebase에 데이터 읽기
    private void readUser(final int position) {
        //새로 전체 다 뿌려주기 위해서 초기화하기
        arrayList.clear();
        if(position!= 0) {
            for (int i = 0; i < familyListCount; i++) {
                //데이터 한번 읽기 하나씩 가져왔음.
                mReference.child("family_member").child(String.valueOf(i)).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {
                            Log.i("firebase_onComplete", "Error getting data", task.getException());
                        } else {
                            Log.i("firebase_onComplete", String.valueOf(task.getResult().getValue()));
                            //listView에 값 뿌리기

                            //문자열 필요 없는 값 자르기
                            String readStr = String.valueOf(task.getResult().getValue());
                            int idx = readStr.indexOf("=");
                            String needStr = readStr.substring(idx + 1);                    //= 뒷부분만 추출
                            String reStr = needStr.substring(0, needStr.length() - 1);      //맨 마지막 } 값 삭제

                            String data[] = reStr.split(" - ");
                            map = new HashMap<String, String>();
                            map.put("name", data[0]);
                            map.put("phone", data[1]);
                            map.put("email", data[2]);
                            arrayList.add(map);
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
            }
        }else{
            Toast.makeText(FamilyList.this, "값이 없습니다.",Toast.LENGTH_SHORT).show();
        }
    }
    // firebase에 데이터 추가
    private void writeNewUser(final int position, String name, String phone, String email){
        FamilyAddress familyAdd = new FamilyAddress(name, phone, email);

        mReference.child("family_member").child(String.valueOf(position)).setValue(familyAdd).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //write was successful!
                //arrayList.add(map);

                //adapter.notifyDataSetChanged();
                readUser(familyListCount); //화면에 뿌리기
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
        Log.i("firebase", "delete "+ position);
        familyListCount--;
        mReference.child("family_member").child(String.valueOf(position)).removeValue();
        Log.i("firebase removeUser", "removeUser" + position);
        readUser(familyListCount);
    }
    
    //Member Method - Custom----------------------------------
    //3개 입력 필드 초기화
    private void initEXIT(){
        nameETXT.setText("");
        phoneETXT.setText("");
        emailETXT.setText("");
    }
}