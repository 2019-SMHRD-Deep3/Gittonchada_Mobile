package org.techtown.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    EditText etId , etPw;
    Button btn_login,ggg;
    boolean isId, isPw;
    static RequestQueue requestQueue;
    StringRequest request;

    public class MyAsyncTask extends AsyncTask<Void, Integer, Boolean> {
        @Override
        protected Boolean doInBackground(Void... strings){
            if(requestQueue == null){
                requestQueue = Volley.newRequestQueue(getApplicationContext());
            }

            btn_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    testJson();
                }
            });

            return true;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected void onPostExecute(Boolean s) {
            super.onPostExecute(s);
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
        @Override
        protected void onCancelled(Boolean s) {
            super.onCancelled(s);
        }
    }



        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etId = findViewById(R.id.etId);
        etPw = findViewById(R.id.etPw);

        btn_login= findViewById(R.id.btn_login);

            MyAsyncTask asyncTask = new MyAsyncTask();
            asyncTask.execute();
    }

    private void checkEditTextBackground(boolean isCheck, EditText et) {
        if (isCheck) { // true
            et.setBackgroundResource(R.drawable.et_sucess_drawable);
        } else {
            et.setBackgroundResource(R.drawable.et_default_drawable);
        }
    }
    public void testJson(){
        String url = "http://192.168.56.1:8087/solgit/Login";
        request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (!error) {
                    } else {
                        Toast.makeText(Login.this,
                                "서버 오류", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                println(response);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", etId.getText().toString());
                params.put("pw", etPw.getText().toString());
                Log.v("valueSend",etId.getText().toString());
                return params;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);
    }

    public void println(String data) {
        Log.v("f",data);
        System.out.println("데이터 : " + data);


        if (!data.equals("false")) {
            Toast.makeText(Login.this,"로그인 성공", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Login.this, Main.class);
            startActivity(intent);
        } else {
            Toast.makeText(Login.this,"로그인 실패", Toast.LENGTH_SHORT).show();
        }
    }}
