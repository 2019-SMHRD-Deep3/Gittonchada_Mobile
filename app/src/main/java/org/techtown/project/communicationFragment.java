package org.techtown.project;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.gson.Gson;

import org.techtown.project.model.Predict;
import org.techtown.project.smp.smp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class communicationFragment extends Fragment {
    private RequestQueue requestQueue;
    ArrayList<Float> smplist = new ArrayList<>();
    Predict predict;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_communication, container, false);
    }
/*    private void smp_sendRequest(){
        String url = "http://172.30.1.43:9001/re/";
        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {  //응답을 문자열로 받아서 여기다 넣어달란말임(응답을 성공적으로 받았을 떄 이메소드가 자동으로 호출됨
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        smp[] smp = gson.fromJson(response, smp[].class);
                        List<smp> list = Arrays.asList((smp));
                        smplist.add(list.get(0).getPresent());
                        ArrayList<Entry> Value = new ArrayList<>();
                    }
                },
                new Response.ErrorListener(){ //에러발생시 호출될 리스너 객체
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.v("hhd","daily 에러 => "+ error.getNetworkTimeMs());
                        Log.v("hhd","daily 에러 => "+ String.valueOf(error));
                    }
                }
        );
        request.setShouldCache(false);
        request.setShouldRetryServerErrors(true);
        request.setRetryPolicy(new com.android.volley.DefaultRetryPolicy(

                30000 ,

                com.android.volley.DefaultRetryPolicy.DEFAULT_MAX_RETRIES,

                com.android.volley.DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        //requestQueue.add(request);
    }*/
    /*private void generation_sendRequest() {
        String url = "http://172.30.1.23:9000/re/5/6/1/14.07/0/0.93/4/81/5";
        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {  //응답을 문자열로 받아서 여기다 넣어달란말임(응답을 성공적으로 받았을 떄 이메소드가 자동으로 호출됨
                    @Override
                    public void onResponse(String response) {
                        Log.v("hhd", "응답 => " + response);
                        Log.v("hhd", "응답 => " + response.length());

                        try {
                            // Response Parse
                            predict = (new Gson()).fromJson(response, Predict.class);
                            if (predict == null) {
                                return;
                            }


                        } catch (Exception e) {
                            Log.v("hhd", "실패");
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() { //에러발생시 호출될 리스너 객체
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.v("hhd", "에러 => " + error.getNetworkTimeMs());
                        Log.v("hhd", "에러 => " + String.valueOf(error));
                    }
                }
        );
        requestQueue.add(request);
    }*/
}
