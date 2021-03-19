package com.example.medicationproject;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Rect;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

public class BloodPressure extends AppCompatActivity {
    //Member Variable -----------------------------------------
    // 디버깅을 위한 변수
    private final boolean D = true;
    private final String TAG = "BloodPressure";

    // List 관련
    private ArrayList<HashMap<String, String>> arrayList; //정보 담을 객체
    private SimpleAdapter adapter;                        // 데이터 넣어줄 어댑터
    private HashMap<String, String> map;

    //날짜, 시간
    private int mYear, mMonth, mDay, mHour, mMinute;


    //ui
    private TextView mTxtDate;      //오늘 날짜
    private Spinner mor_eve_spinner; //셀렉트 박스
    private TextView bPrTimeTXT;  //측정 시간
    private EditText bPrETXT;  //측정 혈당
    private CalendarView BPresscalendarView;

    //아래 ui 값
    private TextView morTimeTXT_show;   //아침 측정 시간
    private TextView morBPresTXT_show;  //아침 측정 혈압
    private TextView eveTimeTXT_show;   //저녁 측정 시간
    private TextView eveBPresTXT_show;  //저녁 측정 혈압

    private TextView select_date_TXT;   //선택한 날짜
    private String spinner_text = "아침";        //spinner선택한

    private Calendar pointDate;     //캘린더 뷰 선택된 날짜 값 변경을 위해서.

    private int sugDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bloodpreslayout);
        init();
        TableUpdate();
        if (D) Log.i(TAG, "onCreate()");

        //CalenderView 선택한 날짜
        BPresscalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                mTxtDate.setText(String.format(" %04d"+"년 "+ "%02d"+"월 "+"%02d"+"일 ", year, month +1, dayOfMonth));
                select_date_TXT.setText(String.format(" %04d" + "년 " + "%02d" + "월 " + "%02d" + "일 ", year, month + 1, dayOfMonth));
                if (D)
                    Log.i(TAG, "혈압 측정 기록에서 캘린더뷰 선택 날짜- " + String.valueOf(year) + "년 " + String.valueOf(month + 1) + "월 " + String.valueOf(dayOfMonth) + "일 ");
                Log.i(TAG, "캘린더뷰 BPresscalendarView 날짜- " + BPresscalendarView.getDate());
                Log.i(TAG, "캘린더뷰 bSuCalendarView 날짜- " + year + "" + (month + 1) + "" + dayOfMonth);

                // 캘린더뷰 선택 값 변경
                pointDate = Calendar.getInstance();
                pointDate.set(year, month, dayOfMonth);
                String sugarDate = (String.format("%02d%02d%02d", year, month + 1, dayOfMonth));
                sugDate = Integer.valueOf(sugarDate);
                Log.i(TAG, "setOnDateChangeListener() sugarDate => " + sugDate);
                //테이블 값 다시 출력
                if (DBInfo.PRESS_DB_ADAPTER != null)
                    DBInfo.PRESS_DB_ADAPTER.getSelectRow(sugDate);   //->PressDBAdapter
                else
                    Log.i(TAG, "DBInfo.PRESS_DB_ADAPTER  => NULL ");
                TableUpdate();
            }
        });

        //Spinner 값 변경
        mor_eve_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String meal = (String) mor_eve_spinner.getSelectedItem();
                if (D) Log.i(TAG, "Spinner 값 변경됨 " + meal);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }


    //오늘 날짜
    public void UpdateDateNow() {
        //오늘 날짜 옆 텍스트뷰 클릭 시 날짜 변경 가능하게 호출
        mTxtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (D) Log.i(TAG, "UpdateDateNow() onClick 오늘 날짜: " + mTxtDate.getText()); //2021년 03월 17일
                new DatePickerDialog(BloodPressure.this, mDateSetListener, mYear, mMonth, mDay).show();
            }
        });
        //TableUpdate(); --> DatePickerDialog 클릭 이벤트에서 처리해야함
        Log.i(TAG, "UpdateDateNow()에서 TableUpdate() 완료");
        mTxtDate.setText(String.format(" %04d" + "년 " + "%02d" + "월 " + "%02d" + "일 ", mYear, mMonth + 1, mDay));
        // onCreate() 시 select_date_TXT에 출력해줄 값
        select_date_TXT.setText(String.format(" %d" + "년 " + "%d" + "월 " + "%d" + "일 ", mYear, mMonth + 1, mDay));
        // 캘린더뷰 선택 값 변경
        pointDate = Calendar.getInstance();
        pointDate.set(mYear, mMonth, mDay);
        String sugarDate = (String.format("%02d%02d%02d", mYear, mMonth + 1, mDay));
        sugDate = Integer.valueOf(sugarDate);
        Log.i(TAG, "UpdateDateNow() sugarDate => " + sugDate);
        //테이블 값 다시 출력
        if (DBInfo.PRESS_DB_ADAPTER != null)
            DBInfo.PRESS_DB_ADAPTER.getSelectRow(sugDate);   //->PressDBAdapter
        else
            Log.i(TAG, "DBInfo.PRESS_DB_ADAPTER  => NULL ");

        BPresscalendarView.setDate(pointDate.getTimeInMillis()); //초 변환
        //BPresscalendarView.setFocusedMonthDateColor(Color.BLUE); //색상 변경
        Log.i(TAG, "캘린더 뷰 포인트 지정" + pointDate.getTimeInMillis());



    }


    public void UpdateMorTimeNow() {
        //텍스트뷰 클릭 시 시간 변경 가능하게 호출
        bPrTimeTXT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(BloodPressure.this, mTimeSetListener, mHour, mMinute, false).show();

            }
        });
        bPrTimeTXT.setText(String.format("%02d:%02d", mHour, mMinute));
        //선택하고나서 아침인지 저녁인지 구분되게
        switch (mHour){
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
                spinner_text = "아침";
                mor_eve_spinner.setSelection(0);
                Log.i(TAG, "UpdateMorTimeNow() 아침 =>" + spinner_text + ", " + mor_eve_spinner.getSelectedItem() );
                break;
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
                spinner_text = "저녁";
                mor_eve_spinner.setSelection(1);
                Log.i(TAG, "UpdateMorTimeNow() 저녁 =>" + spinner_text + ", " + mor_eve_spinner.getSelectedItem() );
                break;
            default:
                spinner_text = "아침";
                mor_eve_spinner.setSelection(0);
                Log.i(TAG, "UpdateMorTimeNow() default 걸렸음 =>" + spinner_text + ", " + mor_eve_spinner.getSelectedItem() );
                break;
        }
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
            //날짜에 따른 값 가져오기
            TableUpdate();
        }
    };

    //아침 측정 시간 대화상자 리스너 부분
    TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            //사용자가 입력한 값을 가져온 뒤
            mHour = hourOfDay;
            mMinute = minute;
            //오전, 오후 값

            //텍스트뷰 값 업데이트
            UpdateMorTimeNow();
        }
    };


    //현재 Focus를 받고 있는 View 영역이 아닌 다른 곳 터치 시 소프트키보드 내리기
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View focusView = getCurrentFocus();
        if (focusView != null) {
            Rect rect = new Rect();
            focusView.getGlobalVisibleRect(rect);
            int x = (int) ev.getX(), y = (int) ev.getY();
            if (!rect.contains(x, y)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(focusView.getWindowToken(), 0);
                }
                focusView.clearFocus();
            }
        }
        return super.dispatchTouchEvent(ev);
    }


    //onCreate 시 초기화 코드 ---------------------------------------------------
    private void init() {
        //DB 생성
        DBInfo.PRESS_DB_ADAPTER = new PressDBAdapter(this);

        //xml 요소 연결
        mTxtDate = findViewById(R.id.todayDateTXT);
        bPrTimeTXT = findViewById(R.id.bPrTimeTXT);
        bPrETXT = findViewById(R.id.bPrETXT);
        mor_eve_spinner = findViewById(R.id.mor_eve_spinner);       //spinner (셀렉트박스) strings.xml과  연결
        BPresscalendarView = findViewById(R.id.BPresscalendarView);
        //화면 아래쪽 xml 요소 연결
        select_date_TXT = findViewById(R.id.select_date_TXT);
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
        ArrayAdapter adapter = new ArrayAdapter(getBaseContext(), R.layout.spinner_item, kinds1);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        mor_eve_spinner.setAdapter(adapter);
    }


    //저장 버튼 클릭
    public void click(View v) {
        if (!bPrETXT.getText().toString().equals("")) { //측정 혈압 입력 값이 비어있는지 확인.
            // 선택한 Spinner 값( 아침/저녁 )
            spinner_text = mor_eve_spinner.getSelectedItem().toString();

            // 삽일할 데이터 객체 생성
            ContentValues newData = new ContentValues();
            newData.put(DBInfo.MOR_EVE_SPINNER, spinner_text);      //  아침, 저녁 구분
            newData.put(DBInfo.PRESS_TIME, bPrTimeTXT.getText().toString());        //  측정시간
            newData.put(DBInfo.PRESS_MEASURE, bPrETXT.getText().toString());        //  측정혈압
            newData.put(DBInfo.PMEASURE_DATE, sugDate);                             //  측정일자
            //DB에 값 삽입하기
            DBInfo.PRESS_DB_ADAPTER.insertRow(DBInfo.TABLE_BLOOD_PRESSURE, newData);


            if (D)
                Log.i(TAG, "아침/저녁: " + spinner_text + "측정시간: " + bPrTimeTXT.getText() + "\n 측정 혈압: " + bPrETXT.getText());
        } else {
            Toast.makeText(BloodPressure.this, "측정하신 혈압을 입력해주세요.", Toast.LENGTH_SHORT).show();
        }
        TableUpdate();
        initEXIT();
    }

    public void initTextView(){
        //아래 텍스트 뷰 초기화
        morTimeTXT_show.setText("");
        morBPresTXT_show.setText("");
        eveTimeTXT_show.setText("");
        eveBPresTXT_show.setText("");
        Log.i(TAG, "initTextView()");
    }

    private void TableUpdate() {
        initTextView();

        //DB 조회하기
        //long time=  pointDate.getTimeInMillis();
        Cursor cursor = DBInfo.PRESS_DB_ADAPTER.getSelectRow(sugDate);
        int  dataCount = cursor.getCount();
        if (D) Log.i(TAG, "TableUpdate() 커서 갯수: " + dataCount);
        //해당 날짜에서만!

        // while문이 안찍힐 땐 cursor가 첫번째로 안돌아가는 경우
//        if(cursor.isLast()){
//            cursor.moveToFirst();
//            if (D) Log.i(TAG, "cursor is LAST: " + cursor.isLast());
//        }

        // while (cursor.moveToNext())
        //커서가 자꾸 Last로 가버려서 어쩔 수 없이 카운트 0이 아닐 때만 도는 do(무조건 실행) while문(이후 조건 맞추는) 실행
        if(dataCount>0) {
            do {
                if (D) Log.i(TAG, "TableUpdate() while 시작");
                String mor_eve = cursor.getString(cursor.getColumnIndex(DBInfo.MOR_EVE_SPINNER));
                String pressMeasure = cursor.getString(cursor.getColumnIndex(DBInfo.PRESS_MEASURE));
                String pressTime = cursor.getString(cursor.getColumnIndex(DBInfo.PRESS_TIME));

                if (mor_eve.equals("아침")) {
                    morTimeTXT_show.setText(pressTime); //측정 시간
                    morBPresTXT_show.setText(pressMeasure);   //측정 혈압
                    if (D)
                        Log.i(TAG, "TableUpdate() 아침 측정시간: " + pressMeasure + ", 측정 혈압: " + pressTime);
                } else if (mor_eve.equals("저녁")) {
                    eveTimeTXT_show.setText(pressTime);
                    eveBPresTXT_show.setText(pressMeasure);
                    if (D)
                        Log.i(TAG, "TableUpdate() 저녁 측정시간: " + pressMeasure + ", 측정 혈압: " + pressTime);
                }
            } while (cursor.moveToNext());
        }
    }

    //입력 값 초기화
    private void initEXIT(){
        bPrETXT.setText("");
    }
}
