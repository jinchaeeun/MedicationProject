package com.example.medicationproject;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static android.provider.AlarmClock.ACTION_SET_ALARM;

public class LayoutDrug3 extends AppCompatActivity {

    //Member Variable ===========================================================================
    private final String TAG = "LayoutDrug3";
    private final boolean D = true;

    private Button preBTN3, nextBTN3, regBTN1,regBTN2,regBTN3;


    private TextView timeETXT1, timeETXT2, timeETXT3;
    private EditText countETXT1, countETXT2, countETXT3;

    //날짜, 시간
    private int mHour1, mMinute1;
    private int mHour2, mMinute2;
    private int mHour3, mMinute3;


    AlarmManager alarm_manager;
    TimePicker alarm_timepicker;
    Context context;
    PendingIntent pendingIntent;

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Calendar 객체 생성
    final Calendar calendar = Calendar.getInstance();

    // 알람리시버 intent 생성
   // final Intent my_intent = new Intent(this.context, Alarm_Reciver.class);
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_druglist_3);

        init();

        this.context = this;
        // 알람매니저 설정
        alarm_manager = (AlarmManager) getSystemService(ALARM_SERVICE);



        //디버깅 확인 용
        if (D)
            Log.i(TAG, "LayoutDrug3 OK");

    }

    public void UpdateTimeNow1() {
        timeETXT1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(com.example.medicationproject.LayoutDrug3.this, mTimeSetListener1, mHour1, mMinute1, false).show();
                if (D) Log.i(TAG, "Time 값 변경됨 " + mHour1 + mMinute1);
            }
        });
        timeETXT1.setText(String.format("%02d:%02d", mHour1, mMinute1));
    }

    public void UpdateTimeNow2() {
        timeETXT2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(com.example.medicationproject.LayoutDrug3.this, mTimeSetListener2, mHour2, mMinute2, false).show();
                if (D) Log.i(TAG, "Time 값 변경됨 " + mHour2 + mMinute2);
            }
        });
        timeETXT2.setText(String.format("%02d:%02d", mHour2, mMinute2));
    }
    public void UpdateTimeNow3() {
        timeETXT3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(com.example.medicationproject.LayoutDrug3.this, mTimeSetListener3, mHour3, mMinute3, false).show();
                if (D) Log.i(TAG, "Time 값 변경됨 " + mHour3 + mMinute3);
            }
        });
        timeETXT3.setText(String.format("%02d:%02d", mHour3, mMinute3));
    }

    //측정 시간 대화상자 리스너 부분
    TimePickerDialog.OnTimeSetListener mTimeSetListener1 = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            //사용자가 입력한 값을 가져온 뒤
            mHour1 = hourOfDay;
            mMinute1 = minute;
            UpdateTimeNow1();

        }
    };

    TimePickerDialog.OnTimeSetListener mTimeSetListener2 = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            //사용자가 입력한 값을 가져온 뒤
            mHour2 = hourOfDay;
            mMinute2 = minute;
            UpdateTimeNow2();

        }
    };

    TimePickerDialog.OnTimeSetListener mTimeSetListener3 = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            //사용자가 입력한 값을 가져온 뒤
            mHour3 = hourOfDay;
            mMinute3 = minute;
            UpdateTimeNow3();
        }
    };


    //Memeber Method - Custome ====================================================
    //App 초기화 메소드
    private void init() {

        //초기화=======================================================================================


        preBTN3 = findViewById(R.id.preBTN3);   //이전 버튼 초기화------------------------
        nextBTN3 = findViewById(R.id.nextBTN3); //다음 버튼 초기화-----------------------


        regBTN1 = findViewById(R.id.regBTN1);
        regBTN2 = findViewById(R.id.regBTN2);
        regBTN3 = findViewById(R.id.regBTN3);


        timeETXT1 = findViewById(R.id.timeETXT1);
        countETXT1 = findViewById(R.id.countETXT1);
        timeETXT2 = findViewById(R.id.timeETXT2);
        countETXT2 = findViewById(R.id.countETXT2);
        timeETXT3 = findViewById(R.id.timeETXT3);
        countETXT3 = findViewById(R.id.countETXT3);

        //현재  시간을 가져오기 위한 Calender 인스턴스 선언
        Calendar cal = new GregorianCalendar();
        mHour1 = cal.get(Calendar.HOUR_OF_DAY);
        mMinute1 = cal.get(Calendar.MINUTE);

        mHour2 = cal.get(Calendar.HOUR_OF_DAY);
        mMinute2 = cal.get(Calendar.MINUTE);

        mHour3 = cal.get(Calendar.HOUR_OF_DAY);
        mMinute3 = cal.get(Calendar.MINUTE);

        UpdateTimeNow1();
        UpdateTimeNow2();
        UpdateTimeNow3();
    }



    public void next(View v) {


        Intent intent_3;
        intent_3 = new Intent(com.example.medicationproject.LayoutDrug3.this, LayoutDrug4.class);

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
        Log.i(TAG,"LayoutDrug3 ==> "+ msg +", "+every);


        //특정 일수 간격 버튼 눌렸을 시 특정 간격 전달 받기!!!!!!==========================================
        String get_specificBTN = rxintent.getStringExtra("SpecificDay");    //특정 일수 일 EditTXT 메시지 받기위한 변수
        String specific ="";
        specific = specific + get_specificBTN;
        Log.i(TAG,"LayoutDrug3 ==> "+ msg +", "+specific);


        String get_numETXT = rxintent.getStringExtra("Nums");
        String num = "";
        num = num + get_numETXT;
        Log.i(TAG,"LayoutDrug3 ==> "+ num );

        String get_quantityETXT = rxintent.getStringExtra("Quantities");
        String quantity = "";
        quantity = quantity + get_quantityETXT;
        Log.i(TAG,"LayoutDrug3 ==> "+ quantity );


        //체크박스에 요일 전달받기!!!!!!===============================================================================
        String check1_1 = "";
        String check1_2 = "";
        String check1_3 = "";
        String check1_4 = "";
        String check1_5 = "";
        String check1_6 = "";
        String check1_7 = "";
        check1_1 = check1_1+rxintent.getStringExtra("Monday");
        check1_2 = check1_2+rxintent.getStringExtra("Tuesday");
        check1_3 = check1_3+rxintent.getStringExtra("Wednesday");
        check1_4 = check1_4+rxintent.getStringExtra("Thursday");
        check1_5 = check1_5+rxintent.getStringExtra("Friday");
        check1_6 = check1_6+rxintent.getStringExtra("Saturday");
        check1_7 = check1_7+rxintent.getStringExtra("Sunday");

        Log.i(TAG, "Lay 3 날짜 받은거==> "+ check1_1 + check1_2 + check1_3 + check1_4 + check1_5 + check1_6 + check1_7 );



        //약종류 전달-----------------------------------------
        intent_3.putExtra("msg", msg);

        intent_3.putExtra("EveryDay", every);

        intent_3.putExtra("SpecificDay", specific);
        intent_3.putExtra("Monday", check1_1);
        intent_3.putExtra("Tuesday", check1_2);
        intent_3.putExtra("Wednesday", check1_3);
        intent_3.putExtra("Thursday", check1_4);
        intent_3.putExtra("Friday", check1_5);
        intent_3.putExtra("Saturday", check1_6);
        intent_3.putExtra("Sunday", check1_7);

        intent_3.putExtra("Quantities", get_quantityETXT);
        intent_3.putExtra("Nums", get_numETXT);



        String get_timeETXT1 = "";   //"약먹을 시간" 입력하기 위해 전달----------------------------------------
        String get_countETXT1 = "";  //시간에 맞는 "약 개수" 입력 위해 전달-------------------------------------------
        String get_timeETXT2 = "";   //"약먹을 시간" 입력하기 위해 전달----------------------------------------
        String get_countETXT2 = "";  //시간에 맞는 "약 개수" 입력 위해 전달-------------------------------------------
        String get_timeETXT3 = "";   //"약먹을 시간" 입력하기 위해 전달----------------------------------------
        String get_countETXT3 = "";  //시간에 맞는 "약 개수" 입력 위해 전달-------------------------------------------



        switch (v.getId()){


            case R.id.nextBTN3:
                get_timeETXT1 = timeETXT1.getText().toString();   //입력된 약먹을 시간
                get_countETXT1 =  countETXT1.getText().toString();    //입력된 약 개수
                get_timeETXT2 =  timeETXT2.getText().toString();   //입력된 약먹을 시간
                get_countETXT2 =  countETXT2.getText().toString();    //입력된 약 개수
                get_timeETXT3 =  timeETXT3.getText().toString();   //입력된 약먹을 시간
                get_countETXT3 =  countETXT3.getText().toString();    //입력된 약 개수

                if( ((get_timeETXT1.length() > 0) && (get_countETXT1.length() > 0)) || ((get_timeETXT2.length() > 0) && (get_countETXT2.length() > 0)) || ((get_timeETXT3.length() > 0) && (get_countETXT3.length() > 0)) ) {


                    intent_3.putExtra("Time1", get_timeETXT1);
                    intent_3.putExtra("Count1", get_countETXT1);

                    intent_3.putExtra("Time2", get_timeETXT2);
                    intent_3.putExtra("Count2", get_countETXT2);

                    intent_3.putExtra("Time3", get_timeETXT3);
                    intent_3.putExtra("Count3", get_countETXT3);


                    startActivity(intent_3);
                }
                else{
                    Toast.makeText(com.example.medicationproject.LayoutDrug3.this, "내용을 입력하세요.", Toast.LENGTH_SHORT).show();
                }


//                // calendar에 시간 셋팅
//                calendar.set(Calendar.HOUR_OF_DAY, alarm_timepicker.getHour());
//                calendar.set(Calendar.MINUTE, alarm_timepicker.getMinute());
//
//                // 시간 가져옴
//                int hour = alarm_timepicker.getHour();
//                int minute = alarm_timepicker.getMinute();
//                Toast.makeText(LayoutDrug3.this,"Alarm 예정 " + hour + "시 " + minute + "분",Toast.LENGTH_SHORT).show();
//
//                // reveiver에 string 값 넘겨주기
//                my_intent.putExtra("state","alarm on");
//
//                pendingIntent = PendingIntent.getBroadcast(LayoutDrug3.this, 0, my_intent,
//                        PendingIntent.FLAG_UPDATE_CURRENT);
//
//                // 알람셋팅
//                alarm_manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
//                        pendingIntent);

                startActivity(intent_3);
                break;

            case R.id.regBTN1:
                Intent i1;
                i1 = new Intent(com.example.medicationproject.LayoutDrug3.this, Alarm_Reciver.class);

                // calendar에 시간 셋팅
                calendar.set(Calendar.HOUR_OF_DAY, mHour1);
                calendar.set(Calendar.MINUTE, mMinute1);

                // 시간 가져옴
                int hour1 = mHour1;
                int minute1 = mMinute1;
                Toast.makeText(com.example.medicationproject.LayoutDrug3.this,"Alarm 예정 " + hour1 + "시 " + minute1 + "분",Toast.LENGTH_SHORT).show();

                i1.putExtra("state","alarm on");

                pendingIntent = PendingIntent.getBroadcast(com.example.medicationproject.LayoutDrug3.this, 0, i1,
                        PendingIntent.FLAG_UPDATE_CURRENT);
                alarm_manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                        pendingIntent);

                break;

            case R.id.regBTN2:
                Intent i2;
                i2 = new Intent(com.example.medicationproject.LayoutDrug3.this, Alarm_Reciver.class);

                // calendar에 시간 셋팅
                calendar.set(Calendar.HOUR_OF_DAY, mHour1);
                calendar.set(Calendar.MINUTE, mMinute1);

                // 시간 가져옴
                int hour2 = mHour2;
                int minute2 = mMinute2;
                Toast.makeText(com.example.medicationproject.LayoutDrug3.this,"Alarm 예정 " + hour2 + "시 " + minute2 + "분",Toast.LENGTH_SHORT).show();

                i2.putExtra("state","alarm on");

                pendingIntent = PendingIntent.getBroadcast(com.example.medicationproject.LayoutDrug3.this, 0, i2,
                        PendingIntent.FLAG_UPDATE_CURRENT);
                alarm_manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                        pendingIntent);
                break;

            case R.id.regBTN3:
                Intent i3;
                i3 = new Intent(com.example.medicationproject.LayoutDrug3.this, Alarm_Reciver.class);

                // calendar에 시간 셋팅
                calendar.set(Calendar.HOUR_OF_DAY, mHour1);
                calendar.set(Calendar.MINUTE, mMinute1);

                // 시간 가져옴
                int hour3 = mHour3;
                int minute3 = mMinute3;
                Toast.makeText(com.example.medicationproject.LayoutDrug3.this,"Alarm 예정 " + hour3 + "시 " + minute3 + "분",Toast.LENGTH_SHORT).show();

                i3.putExtra("state","alarm on");

                pendingIntent = PendingIntent.getBroadcast(com.example.medicationproject.LayoutDrug3.this, 0, i3,
                        PendingIntent.FLAG_UPDATE_CURRENT);
                alarm_manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                        pendingIntent);
                break;

            case R.id.preBTN3:
                finish();
                break;


        }

    }
}