package com.example.medicationproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
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
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

public class BloodSugar extends AppCompatActivity {
    //Member Variable ----------------------------------------
    // 디버깅을 위한 변수
    private final boolean D = true;
    private final String TAG = "BloodSugar";


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
    private Spinner  meal_spinner; //셀렉트 박스
    private TextView bSuTimeTXT;  //측정 시간
    private EditText bSugarETXT;  //측정 혈압
    private CalendarView bSuCalendarView;

    //아래 ui 값
    private TextView before_bfTimeTXT_show;   //아침 전 측정 시간
    private TextView before_bfBSTXT_show;     //아침 전 측정 혈당
    private TextView after_bfTimeTXT_show;    //아침 후 측정 시간
    private TextView after_bfBSTXT_show;      //아침 후 측정 혈당

    private TextView before_lunchTimeTXT_show;   //점심 전 측정 시간
    private TextView before_lunchBSTXT_show;     //점심 전 측정 혈당
    private TextView after_lunchTimeTXT_show;    //점심 후 측정 시간
    private TextView after_lunchBSTXT_show;      //점심 후 측정 혈당

    private TextView before_dnTimeTXT_show;   //저녁 전 측정 시간
    private TextView before_dnBSTXT_show;     //저녁 전 측정 혈당
    private TextView after_dnTimeTXT_show;    //저녁 후 측정 시간
    private TextView after_dnBSTXT_show;      //저녁 후 측정 혈당

    private TextView select_date_TXT;   //선택한 날짜
    private String spinner_text;        //spinner선택한

    private Calendar pointDate;     //캘린더 뷰 선택된 날짜 값 변경을 위해서.
    //하위 키 값 찾기
    List memberInfoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bloodsugarlayout2);
        init();
        if (D) Log.i(TAG, "onCreate()");

        //CalenderView 선택한 날짜
        bSuCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                select_date_TXT.setText(String.format(" %04d"+"년 "+ "%02d"+"월 "+"%02d"+"일 ", year, month +1, dayOfMonth));

                //------------
              /*  Instant instant = Instant.ofEpochMilli(Long.parseLong(dateString));
                System.out.println("유닉스로 변환 " + instant);*/


                //--------------
                



                Log.i (TAG, "캘린더뷰 bSuCalendarView 날짜- " + bSuCalendarView.getDate());
                Log.i (TAG, "캘린더뷰 bSuCalendarView 날짜- " + year + "" + (month +1) +"" + dayOfMonth);
                if (D) Log.i(TAG, "혈당 측정 기록에서 캘린더뷰 선택 날짜- " + String.valueOf(year) + "년 " + String.valueOf(month+1) + "월 "  + String.valueOf(dayOfMonth) + "일 " );
                //저장하기
            }

        });
    

        //Spinner 값 변경
        meal_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String meal = (String) meal_spinner.getSelectedItem();
                if (D) Log.i(TAG, "Spinner 값 변경됨 " + meal);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setTime(int position) {
        UpdateTimeNow();
    }


    //오늘 날짜
    public void UpdateDateNow(){
        //텍스트뷰 클릭 시 날짜 변경 가능하게 호출
        mTxtDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (D) Log.i(TAG, "오늘 날짜: " + mTxtDate.getText());
                new DatePickerDialog(BloodSugar.this, mDateSetListener, mYear, mMonth, mDay).show();

            }
        });

        mTxtDate.setText(String.format(" %04d"+"년 "+ "%02d"+"월 "+"%02d"+"일 ", mYear, mMonth +1, mDay));
        // onCreate() 시 select_date_TXT에 출력해줄 값
        select_date_TXT.setText(String.format(" %d"+"년 "+ "%d"+"월 "+"%d"+"일 ", mYear, mMonth +1, mDay));

        // 캘린더뷰 선택 값 변경
        pointDate = Calendar.getInstance();
        pointDate.set(mYear, mMonth, mDay);
        bSuCalendarView.setDate(pointDate.getTimeInMillis()); //초 변환
        //bSuCalendarView.setFocusedMonthDateColor(Color.BLUE); //색상 변경
        Log.i(TAG, "캘린더 뷰 포인트 지정" + pointDate.getTimeInMillis());

    }

    public void UpdateTimeNow() {
        //텍스트뷰 클릭 시 시간 변경 가능하게 호출
        bSuTimeTXT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new TimePickerDialog(BloodSugar.this, mTimeSetListener, mHour, mMinute, false).show();
            }
        });
        bSuTimeTXT.setText(String.format("%02d:%02d", mHour, mMinute));
        if (D) Log.i(TAG, "Time 값 변경됨 " + mHour + mMinute);
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


    //측정 시간 대화상자 리스너 부분
    TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            //사용자가 입력한 값을 가져온 뒤
            mHour = hourOfDay;
            mMinute = minute;

            //텍스트뷰 값 업데이트
            UpdateTimeNow();
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
        mTxtDate = findViewById(R.id.todayDateTXT); //오늘 날짜 값
        bSuTimeTXT = findViewById(R.id.bSuTimeTXT);
        bSugarETXT = findViewById(R.id.bSugarETXT);
        meal_spinner = findViewById(R.id.meal_spinner);       //spinner (셀렉트박스) strings.xml과  연결
        bSuCalendarView = findViewById(R.id.bSuCalendarView);   //캘린더
        //화면 아래쪽 xml 요소 연결
        select_date_TXT = findViewById(R.id.select_date_TXT);   //캘린더 뷰 아래에 출력해주는 날짜.
        //아침 전
        before_bfTimeTXT_show = findViewById(R.id.before_bfTimeTXT_show);
        before_bfBSTXT_show = findViewById(R.id.before_bfBSTXT_show);
        //아침 후
        after_bfTimeTXT_show = findViewById(R.id.after_bfTimeTXT_show);
        after_bfBSTXT_show = findViewById(R.id.after_bfBSTXT_show);
        //점심 전
        before_lunchTimeTXT_show = findViewById(R.id.before_lunchTimeTXT_show);
        before_lunchBSTXT_show = findViewById(R.id.before_lunchBSTXT_show);
        //점심 후
        after_lunchTimeTXT_show = findViewById(R.id.after_lunchTimeTXT_show);
        after_lunchBSTXT_show = findViewById(R.id.after_lunchBSTXT_show);
        //저녁 전
        before_dnTimeTXT_show = findViewById(R.id.before_dnTimeTXT_show);
        before_dnBSTXT_show = findViewById(R.id.before_dnBSTXT_show);
        //저녁 후
        after_dnTimeTXT_show = findViewById(R.id.after_dnTimeTXT_show);
        after_dnBSTXT_show = findViewById(R.id.after_dnBSTXT_show);


        //현재 날짜와 시간을 가져오기 위한 Calender 인스턴스 선언
        Calendar cal = new GregorianCalendar();
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);
        mHour = cal.get(Calendar.HOUR_OF_DAY);
        mMinute = cal.get(Calendar.MINUTE);

        // 화면 텍스트뷰에 업데이트
        UpdateDateNow();
        UpdateTimeNow();

        //xml에 만들어둔 array.xml값을 불러와 String[], 또는 List로 만들기
        //String 배열
        String[] kinds1 = getResources().getStringArray(R.array.select_meal);

        //Spinner 넣을 ArryaAdapter 객체 생성
        ArrayAdapter adapter = new ArrayAdapter(getBaseContext(),R.layout.spinner_item, kinds1);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        meal_spinner.setAdapter(adapter);
        
        //DB 생성
        DBInfo.DB_ADAPTER = new DBAdapter(this);
    }




    //저장 버튼 클릭
    public void click(View v) {
        //오늘 날짜 값 타임밀리즈.
        final long time = pointDate.getTimeInMillis();
        //캘린더뷰 날짜 선택 시 DB 아래 보여주는거 바꾸기 ----------
        //selectedItem에서 바꾸기
        //---------------------------------------------

        if (!bSugarETXT.getText().toString().equals("")) { //측정 혈당 입력 값이 비어있는지 확인.
            // 선택한 Spinner 값( 아침/저녁 )
            spinner_text = meal_spinner.getSelectedItem().toString();

            // 삽일할 데이터 객체 생성
            ContentValues newData = new ContentValues();
            newData.put(DBInfo.MEAL_SPINNER, spinner_text);      //  식전, 식후 구분
            newData.put(DBInfo.SUGAR_TIME, bSuTimeTXT.getText().toString());        //  측정시간
            newData.put(DBInfo.SUGAR_MEASURE, bSugarETXT.getText().toString());     //  측정혈당
            newData.put(DBInfo.SMEASURE_DATE, time);     //  측정일자

            //DB에 값 삽입하기
            DBInfo.DB_ADAPTER.insertRow(DBInfo.TABLE_BLOOD_SUGAR, newData);

            if (spinner_text.equals("아침 전")) {
                // 측정시간 bPrTimeTXT 값 아래 textView에 넣기.
                before_bfTimeTXT_show.setText(bSuTimeTXT.getText());
                // 측정 혈당 bPrETXT 값 아래 TextView에 넣기
                before_bfBSTXT_show.setText(bSugarETXT.getText());
            }else if (spinner_text.equals("아침 후")) {
                after_bfTimeTXT_show.setText(bSuTimeTXT.getText());
                after_bfBSTXT_show.setText(bSugarETXT.getText());
            }
            else if (spinner_text.equals("점심 전")) {
                before_lunchTimeTXT_show.setText(bSuTimeTXT.getText());
                before_lunchBSTXT_show.setText(bSugarETXT.getText());
            }else if (spinner_text.equals("점심 후")) {
                after_lunchTimeTXT_show.setText(bSuTimeTXT.getText());
                after_lunchBSTXT_show.setText(bSugarETXT.getText());
            }

            else if (spinner_text.equals("저녁 전")) {
                before_dnTimeTXT_show.setText(bSuTimeTXT.getText());
                before_dnBSTXT_show.setText(bSugarETXT.getText());
            }else if (spinner_text.equals("저녁 후")) {
                after_dnTimeTXT_show.setText(bSuTimeTXT.getText());
                after_dnBSTXT_show.setText(bSugarETXT.getText());
            }
            if (D) Log.i(TAG, "아침/저녁: " + spinner_text + "측정시간: " + bSuTimeTXT.getText() + "\n 측정 혈압: " + bSugarETXT.getText());
            Toast.makeText(BloodSugar.this, "혈당 정보를 저장했습니다.", Toast.LENGTH_SHORT).show();
            initEXIT(); //bSuTime 값 초기화
        }
        else{
            Toast.makeText(BloodSugar.this, "측정하신 혈당을 입력해주세요.", Toast.LENGTH_SHORT).show();
        }
    }

    //Member Method - Custom----------------------------------
    //3개 입력 필드 초기화
    private void initEXIT(){
        bSugarETXT.setText("");
    }


}