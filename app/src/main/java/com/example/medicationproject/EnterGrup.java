package com.example.medicationproject;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


public class EnterGrup extends AppCompatActivity {
    //View List
    private                 EditText    nick;
    private                 EditText    passwardcheck;
    private                 EditText    phone;
    private                 EditText    name;
    private                 EditText    Passward;
    private static final    String      TAG="RegisterActivity";
    private                 Button      ok;
    private                 Button      cancle;
    private                 EditText    Email;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entergrop);
        init();
    }
    public void init(){
        ok                  =   findViewById(R.id.OK);
        cancle              =   findViewById(R.id.cancle);
        name                =   findViewById(R.id.Name);
        nick                =   findViewById(R.id.NickNa);
        phone               =   findViewById(R.id.PhonNO);
        Passward            =   findViewById(R.id.PWenter);
        passwardcheck       =   findViewById(R.id.PWcheck);
    }
    public void OK(View v){
        finish();
    }
    public void finish(View v){
        finish();
    }
}
