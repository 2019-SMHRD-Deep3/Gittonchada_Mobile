package org.techtown.project.smp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;
import org.techtown.project.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class PriceFragment extends Fragment {
    ArrayList<String> array = new ArrayList<>();
    private RequestQueue requestQueue = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.example, container, false);

        if (this.getContext() != null) {
            requestQueue = Volley.newRequestQueue(this.getContext());
        }

        Bundle bundle = new Bundle();
        for(int i=0; i<array.size();i++){
        //array.add(bundle.getSerializable("future2"));
        Log.v("hhd",  "hi");}

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        fetchPriceData();
    }

    private void fetchPriceData() {

        String url = "http://localhost:9001/example/";
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("dsf","sdf");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("dsf","sdf");
                    }
                });

        request.setShouldCache(false);   // 누를 때마다 새로운 정보를 출력해서 보여줌
        requestQueue.add(request);
    }
    private void sendRequest(){
        String url = "http://172.30.1.43:9001/re/";
        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {  //응답을 문자열로 받아서 여기다 넣어달란말임(응답을 성공적으로 받았을 떄 이메소드가 자동으로 호출됨
                    @Override
                    public void onResponse(String response) {

                        Log.v("hhd","응답 => " + response);
                        Log.v("hhd","응답 => " + response.length());
                        /* String chValue = response.replace("\"","");
                        chValue = chValue.replace("\\","");
                        */

                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            Log.v("hhd", "array length : " + jsonArray.length());

                            for(int i = 0; i<jsonArray.length();i++){
                                JSONObject smp = jsonArray.getJSONObject(i);
                                Log.v("hhd","future2"+smp.getString("future2"));
                            }


                            /*jsonObject = jsonArray.getJSONObject(0);
                            Log.v("myValueTest",jsonArray.length()+"");
                            Log.v("myValueTest",jsonObject.toString());
*/
                        }catch (Exception e){
                            Log.v("hhd","실패");
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){ //에러발생시 호출될 리스너 객체
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.v("hhd","에러 => "+ error.getNetworkTimeMs());
                        Log.v("hhd","에러 => "+ String.valueOf(error));
                    }
                }
        );
        request.setShouldCache(false);
        request.setShouldRetryServerErrors(true);
        request.setRetryPolicy(new com.android.volley.DefaultRetryPolicy(

                50000 ,

                com.android.volley.DefaultRetryPolicy.DEFAULT_MAX_RETRIES,

                com.android.volley.DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);
    }
}

