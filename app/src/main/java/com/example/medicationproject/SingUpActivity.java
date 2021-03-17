package com.example.medicationproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SingUpActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private EditText editTextEmail, editTextPassword, editTextName;
    private Button buttonJoin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup);

        firebaseAuth = FirebaseAuth.getInstance();

        editTextEmail = findViewById(R.id.editText_email);
        editTextPassword = findViewById(R.id.editText_passWord);
        editTextName = findViewById(R.id.editText_name);

        buttonJoin = findViewById(R.id.btn_join);
        buttonJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //이메일과 비밀번호 공백 아니면
                if(!editTextEmail.getText().toString().equals("")&&!editTextPassword.getText().toString().equals("")){
                    createUser(editTextEmail.getText().toString(), editTextPassword.getText().toString(), editTextName.getText().toString());
                }
                else{
                    //공백이면
                    Toast.makeText(SingUpActivity.this, "공백입니다", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void createUser(String email, String password, String name) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(SingUpActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                    //계정 중복
                    Toast.makeText(SingUpActivity.this, "이미 있어요..", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}