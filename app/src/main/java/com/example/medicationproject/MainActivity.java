package com.example.medicationproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    //----------View List----------//
    private ImageButton TakeMe;
    private ImageButton Al_status;
    private ImageButton TakeList;
    private ImageButton BPlist;
    private ImageButton Enrollment;
    private ImageButton Diabet;
    private String TAG;
    /////////////////////////////////

    //back button 객체 선언

    //마지막 뒤로가기 버튼을 눌렀던 시간 저장
    private long backKeyPressedTime=0;

    //첫번째 뒤로가기 버튼을 누를때 표시
    private  Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }
    //각 Button 클릭시 화면 전환
    //---------------------------------------------------------------------------------------//
    public void GotoT(View v){
        Intent intent = new Intent (MainActivity.this,TimerAdd.class);
        Toast.makeText(getApplicationContext(),"onCreate():",Toast.LENGTH_LONG).show();
        startActivity(intent);
    }
    /////////////////////////////////////////////////////////////////////////////////////////

    //-----------------------------------------------------------------------------------
    public void GotoA(View v){
        Intent intent = new Intent (MainActivity.this,TimerAlam.class);
        Toast.makeText(getApplicationContext(),"alam",Toast.LENGTH_LONG).show();
        startActivity(intent);
    }
    //가족 등록
    public void GotoL(View v){
        Intent intent = new Intent (MainActivity.this,FamilyList.class);
        Toast.makeText(getApplicationContext(),"family:",Toast.LENGTH_LONG).show();
        startActivity(intent);
    }
    //복약기록
    public void GotoE(View v){
        Intent intent = new Intent (MainActivity.this,TimerAlam.class);
        Toast.makeText(getApplicationContext(),"onCreat()",Toast.LENGTH_LONG).show();
        startActivity(intent);
    }
    //혈당 측정
    public void GotoD(View v){
        Intent intent = new Intent (MainActivity.this,BloodSugar.class);
        Toast.makeText(getApplicationContext(),"bloodsugar",Toast.LENGTH_LONG).show();
        startActivity(intent);
    }
    //혈압 측정
    public void GotoB(View v){
        Intent intent = new Intent (MainActivity.this,BloodPressure.class);
        Toast.makeText(getApplicationContext(),"bloodpres",Toast.LENGTH_LONG).show();
        startActivity(intent);

    }

    public void init(){
        TakeList=findViewById(R.id.TakeList);
        TakeMe=findViewById(R.id.TakeMe);
        Al_status=findViewById(R.id.Al_status);
        BPlist=findViewById(R.id.OK);
        Enrollment=findViewById(R.id.Enrollment);
        Diabet=findViewById(R.id.Diabet);
    }

    public void  onBackPressed(){
        if(System.currentTimeMillis()>backKeyPressedTime+2000) {
            backKeyPressedTime = System.currentTimeMillis();
            toast = Toast.makeText(this, "\'뒤로\'버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
            toast.show();
            end();
            return;
        }
    }
    public void end(){
        //앱 끄기
        moveTaskToBack(true);                        // 태스크를 백그라운드로 이동
        finishAndRemoveTask();                        // 액티비티 종료 + 태스크 리스트에서 지우기
        android.os.Process.killProcess(android.os.Process.myPid());    // 앱 프로세스 종료
    }
    public void login(View v){
        Intent intent = new Intent (MainActivity.this,Login.class);
        Toast.makeText(getApplicationContext(),"Login():",Toast.LENGTH_LONG).show();
        Log.i(TAG,"Login:");
        startActivity(intent);
    }

}
