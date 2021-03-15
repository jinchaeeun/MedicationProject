package com.example.medicationproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

public class BloodPressure extends AppCompatActivity {
    //Member Variable ----------------------------------------
    // 디버깅을 위한 변수
    private final boolean D = true;
    private final String TAG = "FamilyList";

    //firebase
    private FirebaseDatabase mDatabase;

    //데이터베이스의 정보
    private DatabaseReference mReference;

    // data
    private EditText nameETXT, phoneETXT, emailETXT;

    //View object 관련, 화면에 뿌려줄 뷰
    private ListView listView;

    // List 관련
    private ArrayList<HashMap<String, String>> arrayList; //정보 담을 객체
    private SimpleAdapter adapter;                        // 데이터 넣어줄 어댑터
    private HashMap<String, String> map;

    //날짜, 시간
    private int mYear, mMonth, mDay, mHour, mMinute;

    //ui
    private TextView mTxtDate;      //오늘 날짜
    private Spinner mor_eve_spinner; //셀렉트 박스
    private TextView bPrTimeETXT;  //측정 시간
    private EditText bPrETXT;  //측정 혈압
    private CalendarView BPresscalendarView;

    //아래 ui 값
    private TextView morTimeTXT_show;   //아침 측정 시간
    private TextView morBPresTXT_show;  //아침 측정 혈압
    private TextView eveTimeTXT_show;   //저녁 측정 시간
    private TextView eveBPresTXT_show;  //저녁 측정 혈압

    //하위 키 값 찾기
    List memberInfoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bloodpreslayout);
        initDatabase();
        init();
        if (D) Log.i(TAG, "onCreate()");

        //CalenderView 선택한 날짜
       BPresscalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                if (D) Log.i(TAG, "혈압 측정 기록에서 캘린더뷰 선택 날짜- " + String.valueOf(year) + "년 " + String.valueOf(month+1) + "월 "  + String.valueOf(dayOfMonth) + "일 " );
                //저장하기

            }
        });
    }


    public void UpdateDateNow(){
        //텍스트뷰 클릭 시 날짜 변경 가능하게 호출
        mTxtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(BloodPressure.this, mDateSetListener, mYear, mMonth, mDay).show();
            }
        });
        mTxtDate.setText(String.format(" %d"+"년 "+ "%d"+"월 "+"%d"+"일", mYear, mMonth +1, mDay));
    }

    public void UpdateMorTimeNow(){
        //텍스트뷰 클릭 시 날짜 변경 가능하게 호출
        bPrTimeETXT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(BloodPressure.this, mTimeSetListener, mHour, mMinute, false).show();
            }
        });
        bPrTimeETXT.setText(String.format("%d:%d", mHour, mMinute));
    }

    //날짜 대화상자 리스너 부분
    DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            //사용자가 입력한 값을 가져온뒤
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;

            //텍스트뷰 값 업데이트
            UpdateDateNow();
        }
    };

    //아침 측정 시간 대화상자 리스너 부분
    TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            //사용자가 입력한 값을 가져온 뒤
            mHour = hourOfDay;
            mMinute = minute;

            //텍스트뷰 값 업데이트
            UpdateMorTimeNow();
        }
    };



    //현재 Focus를 받고 있는 View 영역이 아닌 다른 곳 터치 시 소프트키보드 내리기
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View focusView = getCurrentFocus();
        if(focusView != null){
            Rect rect = new Rect();
            focusView.getGlobalVisibleRect(rect);
            int x = (int) ev.getX(), y = (int) ev.getY();
            if(!rect.contains(x, y)){
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                if(imm != null){
                    imm.hideSoftInputFromWindow(focusView.getWindowToken(), 0);
                }
                focusView.clearFocus();
            }
        }
        return super.dispatchTouchEvent(ev);
    }



    //onCreate 시 초기화 코드 ---------------------------------------------------
    private void init() {
        //xml 요소 연결
        mTxtDate = findViewById(R.id.todayDateTXT);
        bPrTimeETXT = findViewById(R.id.bPrTimeETXT);
        bPrETXT = findViewById(R.id.bPrETXT);
        mor_eve_spinner = findViewById(R.id.mor_eve_spinner);       //spinner (셀렉트박스) strings.xml과  연결
        BPresscalendarView = findViewById(R.id.BPresscalendarView);
        //화면 아래쪽 xml 요소 연결
        morTimeTXT_show = findViewById(R.id.morTimeTXT_show);
        morBPresTXT_show = findViewById(R.id.morBPresTXT_show);
        eveTimeTXT_show = findViewById(R.id.eveTimeTXT_show);
        eveBPresTXT_show = findViewById(R.id.eveBPresTXT_show);


        //현재 날짜와 시간을 가져오기 위한 Calender 인스턴스 선언
        Calendar cal = new GregorianCalendar();
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);
        mHour = cal.get(Calendar.HOUR_OF_DAY);
        mMinute = cal.get(Calendar.MINUTE);

        // 화면 텍스트뷰에 업데이트
        UpdateDateNow();
        UpdateMorTimeNow();

        //xml에 만들어둔 array.xml값을 불러와 String[], 또는 List로 만들기
        //String 배열
        String[] kinds1 = getResources().getStringArray(R.array.select_mor_or_eve);

        //Spinner 넣을 ArryaAdapter 객체 생성
        ArrayAdapter adapter = new ArrayAdapter(getBaseContext(),R.layout.spinner_item, kinds1);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        mor_eve_spinner.setAdapter(adapter);
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

    //저장 버튼 클릭
    public void click(View v) {
        if (!bPrETXT.getText().toString().equals("")) { //측정 혈압 입력 값이 비어있는지 확인.
            // 선택한 Spinner 값( 아침/저녁 )
            String text = mor_eve_spinner.getSelectedItem().toString();
            if (D) Log.i(TAG, "아침/저녁: " + text);
            if (text.equals("아침")) {
                // 측정시간 bPrTimeETXT 값 아래 textView에 넣기.
                morTimeTXT_show.setText(bPrTimeETXT.getText());
                // 측정 혈압 bPrETXT 값 아래 TextView에 넣기
                morBPresTXT_show.setText(bPrETXT.getText());
                if (D) Log.i(TAG, "측정시간: " + bPrTimeETXT.getText() + "\n 측정 혈압: " + bPrETXT.getText());
            } else if (text.equals("저녁")) {
                // 측정시간 bPrTimeETXT 값 아래 textView에 넣기.
                eveTimeTXT_show.setText(bPrTimeETXT.getText());
                // 측정 혈압 bPrETXT 값 아래 TextView에 넣기
                eveBPresTXT_show.setText(bPrETXT.getText());
                if (D) Log.i(TAG, "측정시간: " + bPrTimeETXT.getText() + "\n 측정 혈압: " + bPrETXT.getText());
            }
        }
        else{
            Toast.makeText(BloodPressure.this, "측정하신 혈압을 입력해주세요.", Toast.LENGTH_SHORT).show();
        }
    }
    //Member Method - XML onClick Method----------------------------------
/*
    public void click(View v) {

        switch (v.getId()) {
            case R.id.addBTN:
                // (1) EditText 3개 값 입력 여부 체크
                if (nameETXT.getText().length() > 0 && phoneETXT.getText().length() > 0 && emailETXT.getText().length() > 0) {
                    //firebase 저장 method 호출
                    writeNewUser(0, nameETXT.getText().toString(), phoneETXT.getText().toString(), emailETXT.getText().toString());
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
            Toast.makeText(BloodPressure.this, "값이 없습니다.",Toast.LENGTH_SHORT).show();
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
                Toast.makeText(BloodPressure.this, "입력하신 정보를 저장했습니다.", Toast.LENGTH_LONG).show();

                Log.i(TAG, "onSuccess");

                // 3개 입력 필드 초기화(지우기)
                initEXIT();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //write Failed
                Toast.makeText(BloodPressure.this, "저장에 실패하였습니다. 다시 시도해주세요.", Toast.LENGTH_LONG).show();
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

 */
}