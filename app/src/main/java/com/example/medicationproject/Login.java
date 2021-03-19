package com.example.medicationproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

//import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {
    //---------View List---------------
    private Button ok;
    private Button cancle;
    private EditText ID;
    private EditText Passward;
    private String TAG;
    private int num = 0;
    private LinearLayout linear;
    ///////////////////////////////////


    //---SQLite를 이용하기 위한 setup---//
    SQLiteDatabase newDB;
    Button button;
    String userId;
    boolean userExist;
    DBOpenHelper helper;
    private InputMethodManager imm;
    ////////////////////////////////////


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        init();
        if (newDB == null) {
            String dbName = "new";
            openDatabase(dbName);
        } else {
            Toast.makeText(this, "test", Toast.LENGTH_LONG).show();
        }
        nextEdit();
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        hideKeyboard(Passward);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101 && resultCode == RESULT_OK) {
            String userId = data.getStringExtra("userId");

            Intent intent = new Intent(getApplicationContext(), EnterGrup.class);
            intent.putExtra("userId", userId);
            startActivityForResult(intent, 102);
        } else {
            ok.setText("로그인에 실패했습니다.\n 다시 로그인 해주세요");
            return;
        }
    }

    public void openDatabase(String dbName) {
        DBOpenHelper helper = new DBOpenHelper(this, dbName, null, 1);
        newDB = helper.getWritableDatabase();
    }


    // --------------------------Login버튼 클릭시 화면 전환----------------------------//
    public void OK(View v) {
        DBinit();
        Intent intent = new Intent(Login.this, MainActivity.class);
        String isId = ID.getText().toString().trim();
        String isPass = Passward.getText().toString().trim();
        if (isId.length() > 4 && isPass.length() > 4)
            searchData(isId, isPass);
        else
            Toast.makeText(this, "입력이 잘못되었습니다.", Toast.LENGTH_SHORT);

        if (userExist) {
            intent.putExtra("userId", userId);
            setResult(Activity.RESULT_OK, intent);
            finish();
        } else {
            Toast.makeText(this, "아이디 혹은 비밀번호가" +
                    "없거나 잘못되었습니다.", Toast.LENGTH_SHORT).show();
            return;
        }
        startActivity(intent);
    }
    ////////////////////////////////////////////////////////////////////////////////////


    public void searchData(String isId, String isPass) {
        DBinit();
        newDB = helper.getReadableDatabase();
        String sql = ("select userId, password from test");
        Cursor cursor = newDB.rawQuery(sql, null);

        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToNext();
            String id = cursor.getString(0);
            String password = cursor.getString(1);
            if (id.equals(isId) && password.equals(isPass)) {
                userId = id;
                userExist = true;
                break;
            }
        }
    }


    //---------------버튼 및 EditView초기화---------------//
    public void init() {
        ok = findViewById(R.id.OK);
        cancle = findViewById(R.id.cancle);
        ID = findViewById(R.id.id);
        Passward = findViewById(R.id.password);
        button = (Button) findViewById(R.id.EG);
        linear = (LinearLayout) findViewById(R.id.linear);
    }
    ///////////////////////////////////////////////////////

    //---------------------------------데이터베이스 setup------------------------------------//
    public void DBinit() {
        helper = new DBOpenHelper(this, "new", null, 1);
    }
    /////////////////////////////////////////////////////////////////////////////////////////

    //-----------------------------------Cancle버튼 눌렀을때 메소드-------------------------//
    public void finish(View view) {
        moveTaskToBack(true);                        // 태스크를 백그라운드로 이동
        finishAndRemoveTask();                        // 액티비티 종료 + 태스크 리스트에서 지우기
        android.os.Process.killProcess(android.os.Process.myPid());    // 앱 프로세스 종료
    }
    ////////////////////////////////////////////////////////////////////////////////////////

    //-----------------------------------회원가입 버튼이 눌리면------------------------------//
    public void Enter(View view) {
        Intent intent = new Intent(this, EnterGrup.class);
        Toast.makeText(getApplicationContext(), "go():", Toast.LENGTH_LONG).show();
        Log.i(TAG, "enter():");
        startActivity(intent);
    }
    ///////////////////////////////////////////////////////////////////////////////////////

    //------------------------------------소프트키보드 내리기------------------------------------//
    private void hideKeyboard(EditText passward) {
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(ID.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(Passward.getWindowToken(), 0);
        Passward.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((v.getId() == R.id.password)
                        && (event.getAction() == KeyEvent.ACTION_DOWN)
                        && (keyCode == KeyEvent.KEYCODE_ENTER))
                {
                    hideKeyboard(Passward);
                    return true;
                }

                return false;
            }
        });
    }
    /////////////////////////////////////////////////////////////////////////////////////////////


    //-----------------------------커서를 다음 Edit으로 넘겨주는 함수-------------------------------//
    public void nextEdit() {
        init();
        ID.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((v.getId() == R.id.id) && (event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    Passward.requestFocus();
                    return true;
                }

                return false;
            }
        });
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////

}

