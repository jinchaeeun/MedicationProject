package com.example.medicationproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {
    Handler handler = new Handler(); // Thread 에서 화면에 그리기 위해서 필요
    int value = 0; // progressBar 값
    int add = 1; // 증가량, 방향


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);
        // 앱시작시, Thread 를 시작해서 ProgressBar 를 증가시키기=
        // Thread 내부에서 화면에 작업을 하려면 Handler 를 사용해야한다
        final ProgressBar pb = (ProgressBar) findViewById(R.id.progressBar);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() { // Thread 로 작업할 내용을 구현
                while (true) {
                    value = value + add;
                    if (value >= 100 || value <= 0) {
                        add = -add;
                        if(value==100)
                        {
                            Intent intent = new Intent(getBaseContext(), MainActivity.class);  // Intent 선언
                            startActivity(intent);   // Intent 시작
                            break;
                        }
                    }

                    handler.post(new Runnable() {
                        @Override
                        public void run() { // 화면에 변경하는 작업을 구현
                            pb.setProgress(value);
                        }
                    });
                    try {
                        Thread.sleep(20); // 시간지연
                    } catch (InterruptedException e) {

                    }
                } // end of while
            }
        });
        t.start(); // 쓰레드 시작-
    } // end of onCreate
} // end of class


