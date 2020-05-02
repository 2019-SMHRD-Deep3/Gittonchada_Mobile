package org.techtown.project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.regex.Pattern;

public class Login1 extends AppCompatActivity {

    EditText etId , etPw;
    Button btn_login;
    boolean isId, isPw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etId = findViewById(R.id.etId);
        etPw = findViewById(R.id.etPw);
        btn_login = findViewById(R.id.btn_login);

        etId.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                String id = etId.getText().toString();
                // 불리언 선언을 위에서 하고 옴
                // id -> a~z, A~Z, 0~9 만 들어가야함      ----> Pattern.matches() 패턴 매칭을 설정해둠
                isId = id.length() > 0 && id.length()<= 10 && Pattern.matches("^[a-zA-Z0-9]+$", id);
                //  EditText et = etId;
                checkEditTextBackground(isId, etId);   // 메소드 생성
                btn_login.setEnabled(isId && isPw);
                //btnLogin.setBackgroundResource(R.drawable.btn_login_drawable);
                return false;
            }
        });


        etPw.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                int len = etPw.getText().length();
                isPw = (len >= 8);
                checkEditTextBackground(isPw, etPw);
                btn_login.setEnabled(isId && isPw);


                return false;
            }
        });
    }

    private void checkEditTextBackground(boolean isCheck, EditText et) {
        if (isCheck) { // true
            et.setBackgroundResource(R.drawable.et_sucess_drawable);
        } else {
            et.setBackgroundResource(R.drawable.et_default_drawable);
        }
    }
}

