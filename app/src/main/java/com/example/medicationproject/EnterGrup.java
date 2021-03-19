package com.example.medicationproject;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
    //----------------------View List------------------------//
    private EditText nick;
    private EditText passwardcheck;
    private EditText phone;
    private EditText name;
    private EditText Passward;
    private static final String TAG = "RegisterActivity";
    private Button ok;
    private Button cancle;
    private EditText Email;

    private InputMethodManager imm;

    SQLiteDatabase newDB;
    DBOpenHelper helper;
    //////////////////////////////////////////////////////////


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entergrop);
        init();
        //nextEdit();
        hideKeyboard(Email);
    }

    //-----------------------초기화-------------------------//
    public void init() {
        helper = new DBOpenHelper(this, "new", null, 1);
        ok = findViewById(R.id.OK);
        cancle = findViewById(R.id.cancle);
        name = findViewById(R.id.Name);
        nick = findViewById(R.id.NickNa);
        phone = findViewById(R.id.PhonNO);
        Passward = findViewById(R.id.PWenter);
        passwardcheck = findViewById(R.id.PWcheck);
        Email = findViewById(R.id.email);
    }
    ////////////////////////////////////////////////////////

    //--------------------회원가입 버튼을 눌렀을때 메소드--------------------//
    public void OK(View v) {
        init();
        String id = nick.getText().toString().trim();
        String password = Passward.getText().toString().trim();

        if (id.length() < 5 || password.length() < 5) {
            Toast.makeText(this, "아이디 다섯 글자 이상 \n" +
                    "비밀번호 다섯 글자 이상" +
                    "\n 입력해주세요.", Toast.LENGTH_LONG).show();
        } else {
            insertData(id, password);
            setResult(Activity.RESULT_OK);
            finish();
        }

    }
    ///////////////////////////////////////////////////////////////////////

    //-------취소버튼을 눌렀을때 메소드-------//
    public void finish(View v) {
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
    /////////////////////////////////////////


    public void insertData(String id, String password) {
        newDB = helper.getWritableDatabase();

        String sql = ("insert into test(userId, password) values " +
                "(" + "'" + id + "'" + "," + "'" + password + "'" + ")");

        newDB.execSQL(sql);
    }
    //------------------------------소프트키 내리기----------------------------------//
    private void hideKeyboard(EditText email) {
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(nick.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(Passward.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(passwardcheck.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(name.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(phone.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(Email.getWindowToken(), 0);
        Email.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((v.getId() == R.id.email)
                        && (event.getAction() == KeyEvent.ACTION_DOWN)
                        && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    hideKeyboard(Email);
                    return true;
                }
                return false;
            }
        });
    }
    ///////////////////////////////////////////////////////////////////////////////////
}
