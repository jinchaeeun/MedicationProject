package com.example.medicationproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {
    //View List
    private     Button      ok;
    private     Button      cancle;
    private     EditText    ID;
    private     EditText    Passward;
    private     String      TAG;
    private int num     =   0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        init();
    }

    //각 Button 클릭시 화면 전환
    public void OK(View v){
        Intent intent = new Intent (Login.this, MainActivity.class);
        Toast.makeText(getApplicationContext(),"OK():",Toast.LENGTH_LONG).show();
        Log.i(TAG,"enter():");
        startActivity(intent);
    }


    public void init(){
        ok          =   findViewById(R.id.OK);
        cancle      =   findViewById(R.id.cancle);
        ID          =   findViewById(R.id.id);
        Passward    =   findViewById(R.id.password);
    }

    public void finish(View view) {
        moveTaskToBack(true);						// 태스크를 백그라운드로 이동
        finishAndRemoveTask();						// 액티비티 종료 + 태스크 리스트에서 지우기
        android.os.Process.killProcess(android.os.Process.myPid());	// 앱 프로세스 종료
    }

    //회원가입 버튼이 눌리면
    public void Enter(View view) {
        Intent intent = new Intent (this, EnterGrup.class);
        Toast.makeText(getApplicationContext(),"go():",Toast.LENGTH_LONG).show();
        Log.i(TAG,"enter():");
        startActivity(intent);
    }
}
