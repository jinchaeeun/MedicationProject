package com.example.medicationproject;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.io.IOException;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class TimerAlam extends AppCompatActivity {
    //private Context context;
    private String TAG;
    //View List
    //시간을 캡처하기 위한 setup
    private         TextView                RealTime;
    private         TextView                kind_of_Medical;

    //카메라 및 다시 알람을 위한 setup
    private         Button                  Camera;
    private         Button                  Realam;

    //종료
    private         Button                  Theend;

    //------마지막 뒤로가기 버튼을 눌렀던 시간 저장-------//
    private long            backKeyPressedTime  =   0;

    //-------------------Camera 연동---------------------//
    Button                  btn                 =        null;
    ImageView               iv                  =        null;

    Uri fileUri;

    //surface view virsion
    private         SurfaceHolder       mHolder;
    private         Camera              mCamera         =    null;

    //uri version
    private Uri photoUri;
    //실제 사진 파일 경로
    private String currentPhotoPath;

    private final int       CAMERA_CODE      =    1111;
    private final int       GALLERY_CODE     =    1112;

    MediaPlayer mp = new MediaPlayer();
    public static Context stop_media_play;
    //이미지 이름-
    String mImageCaptureName;

    //imagefile 저장을 위한 setup
    File file;

    //Music관련


    // DG. Activity Result

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.timeralam);
        init();
        Log.i("TimerAlarm", "onCreate()");
        // Camera 권한
        requestPermissionCamera();
        Intent intent = getIntent();
        String  strKind_of_Medical = intent.getStringExtra("Medical_name");
        Log.i("TimerAlarm", "넘어왔는가? " + strKind_of_Medical);
        kind_of_Medical.setText(strKind_of_Medical);
        stop_media_play=this;

    }

    //setup
    public void init() {
        RealTime            =    findViewById(R.id.Real_Time);
        kind_of_Medical     =    findViewById(R.id.Kind_of_medical);
        Camera              =    findViewById(R.id.Camera);
        Realam              =    findViewById(R.id.ReAlam);
        Theend              =    findViewById(R.id.Theend);

        Theend.setEnabled(false);
    }

    // Camera permission
    public boolean requestPermissionCamera() {
        int sdkVersion = Build.VERSION.SDK_INT;
        if (sdkVersion >= Build.VERSION_CODES.M) {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(TimerAlam.this,
                        new String[]{Manifest.permission.CAMERA},
                        100); // 100 is RESULT_PERMISSIONS
            }
            else{
                init();
            }
        }
        else {  // version 6 이하일때
            init();
            return true;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (100 == requestCode) {

            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 권한 허가시
                init();
            } else {
                // 권한 거부시
            }
            return;
        }
    }

    //Realtime print
    public void time() {

        //현재시간 출력
        long mNow                   = System.currentTimeMillis();
        Date mReDate                = new Date(mNow);
        SimpleDateFormat mFormat    = new SimpleDateFormat("HH시 mm분");
        String formatDate           = mFormat.format(mReDate);

        RealTime.setText("사진 찍은 시간은 " + formatDate);

    }

    //Button Click Method

    //Camera setup
    private void setup() {
        btn     =       (Button) findViewById(R.id.Camera);
        iv      =       (ImageView) findViewById(R.id.CaIMG);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_CODE);
            }
        });
    }

    //Camera 연동
    //촬영 버튼 메소드
    public void CameraStart(View v) {
        init();

        //현재시간 캡처 및 출력
        time();
        //카메라 버튼 및 이미지뷰 세팅
        setup();

        //미디어 플레이 정지

        //촬영 버튼 눌렀을 때 메소드
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_CODE);
    }
    protected void onActivityResult(int requestCode,int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_CODE && resultCode == RESULT_OK) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            iv.setImageBitmap(bitmap);
        }
        Theend.setEnabled(true);
        //촬영 버튼 누른 다음 누를 수 있음, 앱 종료
        Theend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveTaskToBack(true);                        // 태스크를 백그라운드로 이동
                finishAndRemoveTask();                        // 액티비티 종료 + 태스크 리스트에서 지우기
                android.os.Process.killProcess(android.os.Process.myPid());    // 앱 프로세스 종료
            }
        });
    }


    //10분 뒤 알림
    //딜레이 메소드
    public void Realam(View v) {
        mp.stop();

        Toast.makeText(getApplicationContext(),"10분 후 계속",Toast.LENGTH_LONG).show();

        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                //Intent intent = new Intent(TimerAlam.this, Test1.class); //화면 전환
                //startActivity(intent);
                finish();

            }
        }, 600000); //딜레이 타임 600초
    }

    //알람 소리 끄기
    public MediaPlayer stop_media_play(View v) {
        mp = new MediaPlayer();
        mp.stop();
        return mp;
    }


    public void  onBackPressed(){
        Intent intent = new Intent (this, MainActivity.class);

        startActivity(intent);
    }
    //이미지뷰를 눌렀을때 메인화면으로
    public void gomain(View v) {
        Intent intent = new Intent(TimerAlam.this, MainActivity.class);
        startActivity(intent);
    }


}