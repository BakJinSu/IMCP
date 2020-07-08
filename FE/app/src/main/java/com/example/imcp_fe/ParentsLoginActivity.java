package com.example.imcp_fe;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.imcp_fe.Network.AppHelper;

import java.util.HashMap;
import java.util.Map;

public class ParentsLoginActivity extends AppCompatActivity {

    private char[] id;
    private char[] pw;
    private Intent intent;
    private SharedPreferences login_preference;
    private Button btn_parents_login, btn_parents_signup, btn_parents_findid, btn_parents_findpw;
    private String url = "tomcat.comstering.synology.me/IMCP_Server/parentLogin.jsp";

    private EditText sign_id, sign_pw;

    // 부모 로그인 창
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parents_login);

        login_preference = getSharedPreferences("Login",MODE_PRIVATE);
        sign_id = findViewById(R.id.et_parents_id);
        sign_pw = findViewById(R.id.et_parents_pw);
        btn_parents_login = (Button)findViewById(R.id.btn_parents_login);
        btn_parents_signup = (Button)findViewById(R.id.btn_parents_signup);
        btn_parents_findid = (Button)findViewById(R.id.btn_parents_findid);
        btn_parents_findpw = (Button)findViewById(R.id.btn_parents_findpw);

        //부모 로그인
        btn_parents_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id = sign_id.getText().toString().toCharArray();
                pw = sign_pw.getText().toString().toCharArray();

                if(TextUtils.isEmpty(String.valueOf(id))){
                    Toast.makeText(getApplicationContext(), "아이디를 입력하세요.", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(String.valueOf(pw))) {
                    Toast.makeText(getApplicationContext(), "비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show();
                }else if(String.valueOf(id).equals(login_preference.getString("id",""))==false){
                    Toast.makeText(getApplicationContext(), "아이디를 다시 입력해주세요.", Toast.LENGTH_SHORT).show();
                }else if(String.valueOf(pw).equals(login_preference.getString("pw",""))==false){
                    Toast.makeText(getApplicationContext(), "비밀번호를 다시 입력해주세요.", Toast.LENGTH_SHORT).show();
                }else if(String.valueOf(id).equals(login_preference.getString("id",""))==true &&String.valueOf(pw).equals(login_preference.getString("pw",""))==true){
                    LoginRequest(url);
                    intent = new Intent(getApplicationContext(), Parents_main.class);
                    startActivity(intent);
                }


            }
        });

        // 부모 회원가입
        btn_parents_signup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                intent = new Intent(getApplicationContext(),ParentsSignup.class);
                startActivity(intent);
//                id = sign_id.getText().toString().toCharArray();
//                pw = sign_pw.getText().toString().toCharArray();
            }
        });

        // 부모 아이디 찾기
        btn_parents_findid.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
               intent = new Intent(getApplicationContext(),ParentsFindId.class);
                startActivity(intent);
            }
        });

        // 부모 비밀번호 찾기
        btn_parents_findpw.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
               intent = new Intent(getApplicationContext(),ParentsFindPw.class);
                startActivity(intent);
            }
        });
    }
    public void LoginRequest(String url) {

        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        switch (response){
                            case "LoginSucess":
                                Toast.makeText(getApplicationContext(), "로그인 성공",Toast.LENGTH_SHORT).show();
                                LoginPass();
                                break;
                            case "LoginFail":
                                Toast.makeText(getApplicationContext(), "로그인 실패",Toast.LENGTH_SHORT).show();
                                break;
                            case "NoID":
                                Toast.makeText(getApplicationContext(), "등록된 아이디가 아닙니다. ",Toast.LENGTH_SHORT).show();

                                break;
                            case "DBError":
                                Toast.makeText(getApplicationContext(), "Error",Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                Log.e("volley", response);
                                break;
                        }
                    }
                },
                new Response.ErrorListener() { //에러발생시 호출될 리스너 객체
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("ID",id.toString());
                params.put("password",pw.toString());
                return params;
            }
        };

        request.setShouldCache(false);
        AppHelper.requestQueue = Volley.newRequestQueue(this);
        AppHelper.requestQueue.add(request);
    }

    public void LoginPass(){
        intent = new Intent(getApplicationContext(), Parents_main.class);
        startActivity(intent);
    }
}
