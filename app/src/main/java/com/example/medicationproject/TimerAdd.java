package com.example.medicationproject;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class TimerAdd extends AppCompatActivity {

    // 디버깅을 위한 변수
    private final boolean D = true;
    private final String TAG = "MainActivity";

    MediaPlayer mp = new MediaPlayer();
    SurfaceHolder holder;
    public static Context play;

    //날짜, 시간
    private int mHour, mMinute;

    //ui
    private Spinner meal_spinner; //셀렉트 박스
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


    private String spinner_text= "아침";        //spinner선택한 값

    private int sugDate;

    private EditText MediEatETXT;
    private EditText sugarETXT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_add);
        play=this;
        init();



//        final TimePicker picker = (TimePicker) findViewById(R.id.timePicker);
//        //타임피커 값 설정
//        picker.setIs24HourView(true);
//        play=this;

        // 앞서 설정한 값으로 보여주기
        // 없으면 디폴트 값은 현재시간
        SharedPreferences sharedPreferences = getSharedPreferences("daily alarm", MODE_PRIVATE);
        long millis = sharedPreferences.getLong("nextNotifyTime", Calendar.getInstance().getTimeInMillis());

        Calendar nextNotifyTime = new GregorianCalendar();
        nextNotifyTime.setTimeInMillis(millis);

        Date nextDate = nextNotifyTime.getTime();
        String date_text = new java.text.SimpleDateFormat("yyyy년 MM월 dd일 EE요일 a hh시 mm분 ", Locale.getDefault()).format(nextDate);
        //Toast.makeText(getApplicationContext(), "[처음 실행시] 다음 알람은 " + date_text + "으로 알람이 설정되었습니다!", Toast.LENGTH_SHORT).show();


        // 이전 설정값으로 TimePicker 초기화
        Date currentTime = nextNotifyTime.getTime();
        java.text.SimpleDateFormat HourFormat = new java.text.SimpleDateFormat("kk", Locale.getDefault());
        java.text.SimpleDateFormat MinuteFormat = new java.text.SimpleDateFormat("mm", Locale.getDefault());

        int pre_hour = Integer.parseInt(HourFormat.format(currentTime));
        int pre_minute = Integer.parseInt(MinuteFormat.format(currentTime));

/*
       if (Build.VERSION.SDK_INT >= 23) {
           picker.setHour(pre_hour);
           picker.setMinute(pre_minute);
        } else {
            picker.setCurrentHour(pre_hour);
            picker.setCurrentMinute(pre_minute);
        }*/

    }

    private void init() {
        //xml 요소 연결
        bSuTimeTXT = findViewById(R.id.bSuTimeTXT);
        bSugarETXT = findViewById(R.id.bSugarETXT);
        meal_spinner = findViewById(R.id.meal_spinner);       //spinner (셀렉트박스) strings.xml과  연결
        //화면 아래쪽 xml 요소 연결
        //아침 전
        before_bfTimeTXT_show = findViewById(R.id.before_bfTimeTXT_show);
        before_bfBSTXT_show = findViewById(R.id.before_bfBSTXT_show);
        //아침 후
        after_bfTimeTXT_show = findViewById(R.id.after_bfTimeTXT_show);
        after_bfBSTXT_show = findViewById(R.id.after_bfBSTXT_show);
        //점심 전
        before_lunchTimeTXT_show = findViewById(R.id.before_lunchTimeTXT_show);
        before_lunchBSTXT_show = findViewById(R.id.before_lunchBSTXT_show);


        //현재 날짜와 시간을 가져오기 위한 Calender 인스턴스 선언
        Calendar cal = new GregorianCalendar();
        mHour = cal.get(Calendar.HOUR_OF_DAY);
        mMinute = cal.get(Calendar.MINUTE);

        // 화면 텍스트뷰에 업데이트
        UpdateTimeNow();

        //spinner
        String[] kinds1 = getResources().getStringArray(R.array.select_m_l_e);

        //Spinner 넣을 ArryaAdapter 객체 생성
        ArrayAdapter adapter = new ArrayAdapter(getBaseContext(),R.layout.spinner_item, kinds1);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        meal_spinner.setAdapter(adapter);


    }


    public void UpdateTimeNow() {
        //텍스트뷰 클릭 시 시간 변경 가능하게 호출
        bSuTimeTXT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(com.example.medicationproject.TimerAdd.this, mTimeSetListener, mHour, mMinute, false).show();
            }
        });
        bSuTimeTXT.setText(String.format("%02d:%02d", mHour, mMinute));

        Log.i("UpdateTimeNow()", "Time 값 변경됨 " + mHour + mMinute);
    }




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

    public void click(View v) {
        medimemo();
        mediNum();
        if (!bSugarETXT.getText().toString().equals("")) { //측정 혈압 입력 값이 비어있는지 확인.
            // 선택한 Spinner 값( 아침/저녁 )
            // 저장 버튼 누른 후 오늘 날짜 mTxtDate를 캘린더뷰 년도, 월, 일 선택되게하고 그 값 select date에 넣기
            spinner_text = meal_spinner.getSelectedItem().toString();
            if (D) Log.i(TAG, "아침/점심/저녁: " + spinner_text);
            if (spinner_text.equals("아침")) {
                // 측정시간 bPrTimeTXT 값 아래 textView에 넣기.
                before_bfTimeTXT_show.setText(bSuTimeTXT.getText());
                // 측정 혈압 bPrETXT 값 아래 TextView에 넣기
                before_bfBSTXT_show.setText(bSugarETXT.getText());
                if (D) Log.i(TAG, "측정시간: " + bSuTimeTXT.getText() + "\n 측정 혈압: " + bSugarETXT.getText());
            }
            else if (spinner_text.equals("점심")) {
                // 측정시간 bPrTimeTXT 값 아래 textView에 넣기.
                after_bfTimeTXT_show.setText(bSuTimeTXT.getText());
                // 측정 혈압 bPrETXT 값 아래 TextView에 넣기
                after_bfBSTXT_show.setText(bSugarETXT.getText());
                if (D) Log.i(TAG, "측정시간: " + bSuTimeTXT.getText() + "\n 측정 혈압: " + bSugarETXT.getText());
            }
            else if (spinner_text.equals("저녁")) {
                // 측정시간 bPrTimeTXT 값 아래 textView에 넣기.
                before_lunchTimeTXT_show.setText(bSuTimeTXT.getText());
                // 측정 혈압 bPrETXT 값 아래 TextView에 넣기
                before_lunchBSTXT_show.setText(bSugarETXT.getText());
                if (D) Log.i(TAG, "측정시간: " + bSuTimeTXT.getText() + "\n 측정 혈압: " + bSugarETXT.getText());
            }
        }
        else{
            Toast.makeText(com.example.medicationproject.TimerAdd.this, "측정하신 혈압을 입력해주세요.", Toast.LENGTH_SHORT).show();
        }


        //기존 코드----------------------------------------------------------------------------------------------
        int hour, hour_24, minute;
        String am_pm;

                if (Build.VERSION.SDK_INT >= 23) {
                    hour_24 = mHour;
                    minute = mMinute;
                } else {
                    hour_24 = mHour;
                    minute = mMinute;
                }

                if (hour_24 > 12) {
                    am_pm = "PM";
                    hour = hour_24 - 12;
                } else {
                    hour = hour_24;
                    am_pm = "AM";
                }

        // 현재 지정된 시간으로 알람 시간 설정
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour_24);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        // 이미 지난 시간을 지정했다면 다음날 같은 시간으로 설정
        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DATE, 1);
        }


        //시간 형식 바꿔서 토스트메시지 띄우기
        Date currentDateTime = calendar.getTime();
        String date_text = new java.text.SimpleDateFormat("yyyy년 MM월 dd일 EE요일 a hh시 mm분 ", Locale.getDefault()).format(currentDateTime);
        Toast.makeText(getApplicationContext(), date_text + "으로 알람이 설정되었습니다!", Toast.LENGTH_SHORT).show();

        //아래 텍스트뷰에 출력해주기


        //  Preference에 설정한 값 저장
        SharedPreferences.Editor editor = getSharedPreferences("daily alarm", MODE_PRIVATE).edit();
        editor.putLong("nextNotifyTime", (long) calendar.getTimeInMillis());
        editor.apply();
//        ((TimerAlam) TimerAlam.stop_media_play).stop_media_play(v);


        diaryNotification(calendar);
    }



//--------------------------------------------------------------



    void diaryNotification(Calendar calendar) {
//        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
//        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
//        Boolean dailyNotify = sharedPref.getBoolean(SettingsActivity.KEY_PREF_DAILY_NOTIFICATION, true);
        Boolean dailyNotify = true; // 무조건 알람을 사용

        PackageManager pm = this.getPackageManager();
        ComponentName receiver = new ComponentName(this, DeviceBootReceiver.class);
        Intent alarmIntent = new Intent(this, com.example.medicationproject.AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);


        // 사용자가 매일 알람을 허용했다면
        if (dailyNotify) {


            if (alarmManager != null) {

                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                        AlarmManager.INTERVAL_DAY, pendingIntent);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                }
            }

            // 부팅 후 실행되는 리시버 사용가능하게 설정
            pm.setComponentEnabledSetting(receiver,
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                    PackageManager.DONT_KILL_APP);

        }
    }

    public MediaPlayer play() {
        mp = new MediaPlayer();
        mp = MediaPlayer.create(com.example.medicationproject.TimerAdd.this, R.raw.alam);
        mp.setLooping(true);
        mp.start();
        return mp;
    }


    public  void medimemo(){

        Intent intent = new Intent(this,TimerAlam.class);
        MediEatETXT=(EditText)findViewById(R.id.MediEatETXT);
        intent.putExtra("Medical_name",MediEatETXT.getText().toString());
        intent.putExtra("Medic_name",MediEatETXT.getText().toString());
        startActivity(intent);
    }
    public  void mediNum(){
        sugarETXT=(EditText)findViewById(R.id.bSugarETXT);
        Intent intent = new Intent(this,TimerAlam.class);
        sugarETXT=(EditText)findViewById(R.id.bSugarETXT);
        intent.putExtra("Medical_Number",bSugarETXT.getText().toString());
        startActivity(intent);
    }
}
